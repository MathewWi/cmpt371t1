/**
 * 
 */
package ca.sandstorm.luminance.test.main;

import ca.sandstorm.luminance.LuminancePc;
import android.test.AndroidTestCase;


/**
 * Testing LuminancePC functionality
 * @author Amara Daal
 *
 */
public class LuminancePcTest extends AndroidTestCase
{

    LuminancePc _luminancePc;
    /**
     * Constructor
     */
    public LuminancePcTest()
    {
	super();
    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
	super.setUp();
	_luminancePc = new LuminancePc();
	assertNotNull(_luminancePc);
    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
	super.tearDown();
    }

}
