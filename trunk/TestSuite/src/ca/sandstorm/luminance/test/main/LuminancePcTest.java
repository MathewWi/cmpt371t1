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

    LuminancePc luminancePc;
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
	luminancePc = new LuminancePc();
	assertNotNull(luminancePc);
    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
	super.tearDown();
    }

}
