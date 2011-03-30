package ca.sandstorm.luminance.gameobject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Point2i;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.math.Colliders;
import ca.sandstorm.luminance.math.Plane;


/**
 * Grid class
 * 
 * @author halsafar
 * 
 */
public class Grid
{
    private static final Logger _logger = LoggerFactory.getLogger(Grid.class);

    // vertex and index buffers
    private FloatBuffer _vertexBuffer;
    private ShortBuffer _indexBuffer;

    // total indice count for rendering
    private int _totalIndices;

    // temp vectors to avoid mallocs
    private Vector3f _position;
    private Vector3f _gridCenter;
    private Vector3f _tmpCellCenter;
    private Point2i _tmpGridPos;

    // grid properties
    private int _cols;
    private int _rows;

    private float _cellWidth;
    private float _cellHeight;

    // plane of the grid
    private Plane _plane; 

    /**
     * Constructor.
     * 
     * @param rows
     *            The number rows in the grid
     * @param cols
     *            The number cols in the grid
     * @param cellWidth
     *            The width of each grid cell
     * @param cellHeight
     *            The height of each grid cell
     * @precon rows != 0, cols != 0, cellWidth != 0, cellHeight != 0
     * @precon (rows+1)*cols*2*2 <= 65535
     * @postcon _vertexBuffer != null, _indexBuffer != null, _totalIndices > 0
     */
    public Grid(int rows, int cols, float cellWidth, float cellHeight)
    {
	_logger.debug("Grid(" + rows + ", " + cols + ", " + cellWidth + ", " +
		      cellHeight + ")");
	
	// create plane
	_plane = new Plane(0, 0, 0, 0, 1, 0);

	// calculate vertices
	float x = 0.0f;
	float z = 0.0f;
	// int numVertices = (rows + 1) * (cols + 1) * 3;
	float[] vertices = null;
	Vector<Float> vVertices = new Vector<Float>();

	int tmpIndex = 0;
	for (int i = 0; i < rows + 1; i++) {
	    for (int j = 0; j < cols + 1; j++) {
		if (i == 0 || j == 0 || j == cols || i == rows) {
		    x = j * cellWidth;
		    z = i * cellHeight;

		    vVertices.add(x);
		    vVertices.add(0.0f);
		    vVertices.add(z);
		}
	    }
	}

	vertices = new float[vVertices.size()];
	for (int i = 0; i < vVertices.size(); i++) {
	    vertices[i] = vVertices.get(i);
	}

	ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	_vertexBuffer = byteBuf.asFloatBuffer();
	_vertexBuffer.put(vertices);
	_vertexBuffer.position(0);

	// calculate indices
	// _totalIndices = (rows + 1) * cols * 2 * 2;
	_totalIndices = ((rows + 1) + (cols + 1)) * 2;
	short[] indices = new short[_totalIndices];


	// horizontal line indices
	short index1 = 0;
	short index2 = 0;
	tmpIndex = 0;
	for (int i = 0; i < rows + 1; i++) {
	    if (i == 0) {
		index1 = 0;
		index2 = (short) cols;
	    } else {
		index1 = (short) (i + (i * cols) - ((i - 1) * (cols - 1)));

		if (i == rows) {
		    index2 = (short) ((vertices.length / 3) - 1);
		} else {
		    index2 = (short) (index2 + 2);
		}
	    }

	    indices[tmpIndex++] = index1;
	    indices[tmpIndex++] = index2;
	}

	// vertical line indices
	index1 = 0;
	index2 = (short) ((vertices.length / 3) - cols - 1);
	for (int i = 0; i < cols + 1; i++) {
	    indices[tmpIndex++] = (short) (i);
	    indices[tmpIndex++] = index2++;
	}

	byteBuf = ByteBuffer.allocateDirect(indices.length * 2);
	byteBuf.order(ByteOrder.nativeOrder());
	_indexBuffer = byteBuf.asShortBuffer();
	_indexBuffer.put(indices);
	_indexBuffer.position(0);

	// store / precalc some important values
	_cellWidth = cellWidth;
	_cellHeight = cellHeight;

	_rows = rows;
	_cols = cols;

	_position = new Vector3f();
	_position.set(0, 0, 0);
	
	_gridCenter = new Vector3f();
	_gridCenter.set(this.getTotalWidth() / 2.0f, 0, this.getTotalHeight() / 2.0f);

	_tmpCellCenter = new Vector3f(0, 0, 0);
	_tmpGridPos = new Point2i(0, 0);
    }


    /**
     * GetColumnCount()
     * 
     * @return
     */
    public int getColumnCount()
    {
	return _cols;
    }


    /**
     * GetRowCount()
     * 
     * @return
     */
    public int getRowCount()
    {
	return _rows;
    }


    /**
     * Get the cell width.
     * 
     * @return
     */
    public float getCellWidth()
    {
	return _cellWidth;
    }


    /**
     * Get the cell height.
     * 
     * @return
     */
    public float getCellHeight()
    {
	return _cellHeight;
    }


    /**
     * Derives the total grid width
     * 
     * @return
     */
    public float getTotalWidth()
    {
	return (_cellWidth * getColumnCount());
    }


    /**
     * Derives the total grid height
     * 
     * @return
     */
    public float getTotalHeight()
    {
	return (_cellHeight * getRowCount());
    }
    
    
    /**
     * Get the object's plane.
     * @return
     */
    public Plane getPlane()
    {
	return _plane;
    }


    /**
     * Get the center of a cell in world space.
     * 
     * @param row
     *            The row index to center on.
     * @param col
     *            The column index to center on.
     * @return The world position for the center of that particular cell.
     * @precon row >= 0 && row < _rows, col >= 0 && col < _cols
     * @postcon _tmpCellCenter != null
     */
    public Vector3f getCellCenter(int row, int col)
    {
	float xOffset = _cellWidth / 2.0f;
	float zOffset = _cellHeight / 2.0f;

	float x = xOffset + (col * _cellWidth);
	float y = 0.0f;
	float z = zOffset + (row * _cellHeight);

	_tmpCellCenter.set(x, y, z);

	return _tmpCellCenter;
    }

    
    public Vector3f getGridCenter()
    {
	return _gridCenter;
    }

    /**
     * Converts a world position into a grid based cell position. This will
     * return a vector containing the column,row the world position matches.
     * Note, even though y is accepted as a param it is ignored.
     * 
     * @param x
     *            World x coordinate
     * @param y
     *            World y coordinate (ignored)
     * @param z
     *            World z coordinate
     * @return Null if not on grid; grid coordinates otherwise
     */
    public Point2i getGridPosition(float x, float y, float z)
    {	
	float gridX = x / _cellWidth;
	float gridZ = z / _cellHeight;
	
	// Check if position is within grid -zenja
	if (gridX < 0 || gridZ < 0 ||
	    gridX > getColumnCount() || gridZ > getRowCount()) {
	    return null;
	}
	
	// Check if position is not NaN
	if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z)){
	    return null;
	}

	_tmpGridPos.set((int)(gridX + Colliders.EPSILON), (int)(gridZ + Colliders.EPSILON));

	return _tmpGridPos;
    }


    /**
     * Draw the grid.
     * 
     * @param gl
     *            OpenGL context
     */
    public void draw(GL10 gl)
    {
	// Set the face rotation
	gl.glFrontFace(GL10.GL_CW);

	// Point to our buffers
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);

	// Enable the vertex and color state
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

	// Set color
	gl.glColor4f(1f, 1f, 1f, 1f);

	// Draw the vertices as triangles, based on the Index Buffer information
	gl.glDrawElements(GL10.GL_LINES, _totalIndices, GL10.GL_UNSIGNED_SHORT,
			  _indexBuffer);

	// Disable the client state before leaving
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
