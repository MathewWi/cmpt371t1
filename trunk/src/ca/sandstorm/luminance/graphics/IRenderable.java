package ca.sandstorm.luminance.graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public interface IRenderable
{
    FloatBuffer getVertexBuffer();
    FloatBuffer getNormalBuffer();
    ByteBuffer getIndexBuffer();
}
