package ca.sandstorm.luminance.test.level;

import java.util.Vector;

import ca.sandstorm.luminance.level.XmlLevelEmitter;
import android.test.AndroidTestCase;

public class XmlLevelEmitterTest extends AndroidTestCase{
	XmlLevelEmitter xle;
	
	protected void setUp() throws Exception {
		xle = new XmlLevelEmitter("White", new Vector<Float>(), new Vector<Float>());
		super.setUp();
	}
	
	public void testXmlLevelEmitter() throws Exception {
		assertNotNull(xle);
	}
}
