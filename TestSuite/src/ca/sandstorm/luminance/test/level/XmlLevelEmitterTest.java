package ca.sandstorm.luminance.test.level;

import java.util.Vector;

import ca.sandstorm.luminance.level.XmlLevelEmitter;
import android.test.AndroidTestCase;

public class XmlLevelEmitterTest extends AndroidTestCase{
	XmlLevelEmitter _xle;
	
	protected void setUp() throws Exception {
		Vector<Float> pos = new Vector<Float>();
		Vector<Float> rot = new Vector<Float>();
		pos.add(0.0f);
		pos.add(0.0f);
		rot.add(0.0f);
		rot.add(0.0f);
		rot.add(0.0f);
		_xle = new XmlLevelEmitter("White", pos, rot);
		super.setUp();
	}
	
	public void testXmlLevelEmitter() throws Exception {
		assertNotNull(_xle);
	}
}
