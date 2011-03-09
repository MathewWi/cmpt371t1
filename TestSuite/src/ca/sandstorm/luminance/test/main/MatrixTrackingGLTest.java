/**
 * 
 */
package ca.sandstorm.luminance.test.main;

import static org.mockito.Mockito.mock;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.MatrixTrackingGL;

import android.test.AndroidTestCase;


/**
 * Creating token test for MatrixGL
 * @author Amara Daal
 *
 */
public class MatrixTrackingGLTest extends AndroidTestCase
{

    MatrixTrackingGL matrixTrackingGL;
    GL10 mockGl;
    
    /**
     * @param name
     */
    public MatrixTrackingGLTest(String name)
    {
	super();

    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
	super.setUp();
	mockGl = mock(GL10.class);
	matrixTrackingGL= new MatrixTrackingGL(mockGl);
	assertNotNull(matrixTrackingGL);
    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
	super.tearDown();
    }

}
