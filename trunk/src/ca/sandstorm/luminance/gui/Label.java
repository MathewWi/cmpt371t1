package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;

/**
 * Standard label for showing static text.
 * 
 * @author Kumaran Vijayan
 *
 */
public class Label implements IWidget
{

    private String _title;
    
    /**
     * Constructor for creating a Label.
     * @param title String to use for the Label.
     */
    public Label(String title)
    {
	_title = title;
    }
    
    /**
     * Get the String title for the label.
     * @return the String that is being used for the content of the Label.
     */
    public String getTitle()
    {
	return _title;
    }
    
    @Override
    public float getX()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setX(float x)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public float getY()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setY(float y)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public float getWidth()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setWidth(float width)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public float getHeight()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setHeight(float height)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public void draw(GL10 gl)
    {
	// TODO Auto-generated method stub

    }

}
