

# Peer deskchecks #
Personal code reviews were done on team members own time.  The process was to review code that was not written by them, and log issues they saw here.

## Martina ##
**Zenja's code**

  * Box.java:
    * Line 36: Why are values hard coded for rotation?

  * GameObject.java:
    * Lines 70-78: Commented out code.  Is this still needed?  If not, delete it.

  * Grid.java:
    * Line 79, 107, 302, 306, 317: Commented out code.
    * Line 82: Not very clear what this loop does.  Consider adding comment.

  * Mirror.java:
    * Lines 29 and 30: Why are these values hard coded?

  * SkyBox.java:
    * Lines 75, 81, 87: Magic numbers.  What do these values represent?

**Last minute code review before deliverable 3**

- GameRenderer.java does not an author

- LightPath.java has zero comments, also sans author

-MenuState.java has few comments, no author, and generally looks messy with things commented out.

- Box.java missing some comments on methods

- GameObject.java does not have an author or class comment

- Emitter.java zero comments and no author

- Light.java has zero comments and no author

- Mirror.java has zero comments and no author

- Prism.java has zero comments and no author

- Eraser.java has zero comments and no author

- MirrorTool.java has no comments and no author

- PrismTool.java has no comments and no author

-PrimitivePrism.java has no author

- PrimitiveQuad.java has no comments and no author

- Colliders.java missing some comments and no author

- Plane.java no comments and no author

- Ray.java has no comments and no author

- Sphere.java has no comments and no author


## Stephen Damm ##
**ALL IF STATEMENTS NEED BRACERS**

Under all circumstances, please put braces around all if statements, regardless of how many lines they are. Please run Eclipse -> Project -> Source -> Format on your files before commit.

**ResourceManager()**

The word resource should not appear in the function names, since it is a ResourceManager.  Something like loadTexture() is more appropriate. The AudioManager is separate but an instance will be maintained by Engine and talked to by resourceManager via the engine.


**InputSystem()**

The scaleX, scaleY was not being set correctly, Luminance.java should calculate the proper values like ReplicaIsland does and pass it on to Engine deviceChagne.


**GUI()**

Keep class names generic, add a Interface (IWidget).  Here is a helpful link: (http://wiki.allegro.cc/index.php?title=2D_using_OpenGL) it is for OpenGL but most applies to OpenGL ES (except the glVertex() calls, see Box/SkyBox for how to translate it to OpenGL ES).


**Renderer()**

Should be a container for renderable objects, will manage matrix multiplications and then ask objects to render themselves.


**PrimitiveSphere()**

Should use a sphere generation algorithm (this is a classic 3D problem and task given to students). Here is a long OpenGL version I wrote for my summer work last year in IMG Lab. Convert to OpenGL ES fairly easily. Although it uses QUAD\_STRIPS which are a bit slower (TRIANGLE\_STRIP is the fastest by far). The hardest part of these algorithms is making sure texture coords wrap properly.

`void drawSphere(double r, int lats, int longs)`

`{`

`int i, j;`

`for(i = 0; i <= lats; i++)`

`{`

`double lat0 = PI * (-0.5 + (double) (i - 1) / lats);`

`double z0 = sin(lat0);`

`double zr0 = cos(lat0);`

`double lat1 = PI * (-0.5 + (double) i / lats);`

`double z1 = sin(lat1);`

`double zr1 = cos(lat1);`

`double tx1 = 0;`

`double ty1 = 0;`

`double tx2 = 0;`

`double ty2 = 0;`

`glBegin(GL_QUAD_STRIP);`

`for(j = 0; j <= longs; j++)`

`{`

`double lng = 2 * PI * (double) (j - 1) / longs;`

`double x = cos(lng);`

`double y = sin(lng);`

`tx1 = atan2(x * zr0, z0) / (2. * PI) + 0.5;`

`ty1 = asin(y * zr0) / PI + .5;`

`if(tx1 < 0.75 && tx2 > 0.75)`

`{`

`tx1 += 1.0;`

`}`

`else if(tx1 > 0.75 && tx2 < 0.75)`

`{`

`tx1 -= 1.0;`

`}`

`tx2 = atan2(x * zr1, z1) / (2. * PI) + 0.5;`

`ty2 = asin(y * zr1) / PI + .5;`

`if(tx2 < 0.75 && tx1 > 0.75)`

`{`

`tx2 += 1.0;`

`}`

`else if(tx2 > 0.75 && tx1 < 0.75)`

`{`

`tx2 -= 1.0;`

`}`

`glTexCoord2f(tx1, ty1);`

`glNormal3f(x * zr0, y * zr0, z0);`

`glVertex3f(x * zr0 * r, y * zr0 * r, z0 * r);`

`glTexCoord2f(tx2, ty2);`

`glNormal3f(x * zr1, y * zr1, z1);`

`glVertex3f(x * zr1 * r, y * zr1 * r, z1 * r);`

`}`

`glEnd();`

`}`

`}`


## Jonny ##
**On Kum's code**
  * Label.java
    * draw() function in the label should be implemented using quads like button.draw() instead of using primitive box.

## Chet Collins ##
**Zenja's and Stephen's code**
  * Sphere.java
    * No comments
  * Ray.java
    * No comments
  * Plane.java
    * No comments
  * MatrixStack.java
    * Minimal comments, more detail would help
  * Lerp.java
    * No comments
  * Colliders.java
    * Looks good

# Peer Reviews #
## March 14, 2011 ##
### PrimitiveBox.java ###
**Stephen's code**
> - Stephen explained _vertices and_indices matrices, as well as _textureCoords_

Line 105 and 112: Could change 4 to a const called sizeOf(int) or something

Zenja wondered about VBO's and wondering why were aren't using them.  Stephen explained that there is no performance gained using them.

Line 143: why is it _indexBuffer.limit() in draw function.
> - should be tested.  May be lucking out in that the buffer returned is the exact size of the buffer we need.
> - should probably use something else_

### PrimitivePrism.java ###
Texture does not work currently.  Is rendered as a pyramid, just a matter of making the texture fit it.

Basically the same class as PrimitiveBox, only different vertices etc.


### PrimitiveQuad.java ###
What is quad?
> - vertices are created at runtime
> - had to do this because we need to create buttons on the fly.

Line 17, 18: why are they static?
> - they could be declared locally in the constructor.  They really don't need to be fields.  If we move it it would probably improve performance slightly.

Line 72: why is there a parameter of _vertices.length/3?
> - without the division, it would still work, but it would keep going and render a bunch of nothing.  This increases performance._

### PrimitiveSphere.java ###
A billion lines of vertices!!!
> - should use a function to do this!!
> > - Although this would be a performance hit.

> - probably don't need so many vertices

Should also use an index array for the vertices.
> - we will only get this if we generate in the algorithm

### For all the previous classes ###
Since all these primitives are using the same constructor, there could be a superclass for these classes.

We currently don't account for the fact we may have to modify the vertices.  This would be a huge performance hit.  We can get by for now, but should be considered.

Also, when we want to start with special effects such as lighting, we're going to have to implement our objects with more triangles in order for it to look good.

## March 7, 2011 ##
### Toolbelt.java ###
**Zenja's code**
Line 26: _GRIDPOINT\_ERROR - What is this? Needs to be made smaller.  Used as an epsilon for constraining floating point errors._

Line 30: Game state should not be a storage object.  It should be a container for all the other classes; act as a controller.  This causes coupling.  We should try to get this with the toolbelt not talking to game state.

Line 34: List of tools used to loop through to see objects on the grid.  Not necessary.  Be careful with usage.

Line 39 and 40: These are used to indicate a load of a button texture after the parsing of a level file.  Perhaps load all at startup instead of at runtime.

### Test classes ###
_In this peer review, the whole test team sat down and looked at all of the test classes together_

**Chet's code**

AndroidSoundPlayerTest.java
  * streamId: why is this 0?  If this is zero, it indicates failure, since player.play returns an int.

**Jeff's code**

GameRendererTest.java
  * constructor is no longer valid, due to dev team changes.
  * Should be comments on methods

**Martina's code**

BoxTest.java
  * Looks good

GridTest.java
  * Looks good

LightTest.java
  * Need to fill in with proper tests

MirrorTest.java
  * Looks good

EraserTest.java, LightPathTests.java, MirrorToolTest.java, PrismToolTest.java
  * Look good.  Not much there yet as source class isn't complete

**Jeff's code**

ToolBeltTest.java
  * Needs to say who wrote it.
  * Otherwise looks good

**Amara's code**

ButtonTest.java
  * No description of the class.

!GUIManagerTest.java
  * No description of the class.
  * testGetNumberOfButtions() currently doesn't work and is commented out.  Don't know why as of yet.
  * testAddButtons() weird comment.  Meant to represent the way the buttons are added in the test, but is unclear.

LabelTest.java
  * testLabel() doesn't really do anything other than create an object.  No asserts or anything in the function.
  * get rid of testDraw function.  We're not going to be testing OpenGL functions.

InputButtonTest.java
  * Looks good

InputKeyboardTest.java
  * Very robust!

InputSystemTest.java
  * Needs class description
  * testKeyUp(): magic number in for loops (what does 10 represent?)
  * Very robust!

ALL XML TEST CLASSES NEED COMMENTS

**Jeff's code**

XmlLevelGoalTest.java
  * Needs description and who wrote it
  * why are the parameters passed to the constructor all messed up? i.e. why is the string "rEd" or "WhitE" being passed?

XmlLevelTest.java
  * Looks good

**Chet's code**

CollidersTest.java
  * Looks good

LerpTest.java
  * Looks good

PlaneTest.java
  * Looks good

RayTest.java
  * testSetDirection isn't technically testing anything, is just setting values.

ImageResourceTest.java
  * looks good

ResourceManagerTest.java
  * shouldn't have the assert in the catch portion of a try-catch.  They will never be called unless there's an exception.

ResourceTest.java
  * Looks good.

SoundResourceTest.java
  * Looks good.

TextResourceTest.java
  * Looks good.

TextureResourceTest.java
  * Looks good.

**Martina's code**

TimeSystemTest.java
  * copied and pasted throws clause when they may not be necessary.
  * some tests are commented out, need to fix and make them work.

**Amara's code**

LuminanceTest.java
  * needs comments and who wrote it.
  * tests aren't written yet since the game has not been in a 'playable' state yet.