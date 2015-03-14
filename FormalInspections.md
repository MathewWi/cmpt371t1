

# Milestone 3: Inspection of the resource package #

**Moderator:** Martina

**Author:** Zenja

**Presenter /Reader:** Zenja

**Inspectors:** Martina, Zenja, Jonny, Kum

**Recorder:** Martina

### Inspection Meeting: ###
  * Defect 1:
    * Location: (ResourceManager.java, 72)
    * Summary: Probably don't need an exception here.
    * Inspectors who discovered it: Zenja and Kum agreed with him
    * Decision: could just be an assert
  * Defect 2:
    * Location: (ResourceManager.java, 90 and 128)
    * Summary: Almost an exact copy of loadResource function.  Simply take different type for parameter.
    * Inspector who discovered it: Zenja
    * Decision: Leave it for now.  It's convenient and both are still useful.
  * Defect 3:
    * Location: (ResourceManager.java, loadTexture())
    * Summary: Should this be in here?  It could be in graphics.
    * Inspectors who discovered it: Zenja, Stephen (not present)
    * Decision: It does belong here.
  * Defect 4:
    * Location (ResourceManager.java, loadSound())
    * Not currently working
    * Inspector who discovered it: Everybody.  We're all aware of this defect.
    * Decision: It's not a priority for this deliverable, but will be fixed in later ones.  Leave for now.
  * Defect 5:
    * Location: (ResourceManager.java, 255)
    * Summary: Dead code.  The way the method is set up, if a file that is attempted to be loaded doesn't exist, and exception is thrown.  Currently, there is a line of code after this that checks stream.available() returns -1, which also may mean the file doesn't exist.  Nobody is sure what this for sure.
    * Martina, Jonny
    * Do some research on it to figure out what exactly its doing.
  * Defect 6:
    * Location (IResource.java)
    * Summary: Should specify access modifiers
    * Inspectors: Jonny, Kum
    * Should be consistent, so we should put modifiers in this interface as other interfaces use them.
  * Defect 7:
    * Location (Resource.java, 67)
    * Summary: Is this needed anymore (getAssetId())
    * Jonny, Zenja
    * Decision: currently the sound resource doesn't work, and this method is used for debugging.  Will leave for now.
    * Future recommendations
  * Defect 8:
    * Location (ImageResource.java)
    * Needs to be formatted
    * Martina, Zenja
    * Decision: needs to be formatted!!

### Follow-Up Results ###
Follow-up from Zenja:
  * Defect 1: Changed to be an assert.
  * Defect 2: Left alone; only 2-3 lines are duplicated and there would be some issues with trying to create a common method.
  * Defect 3: Left alone since they could be considered a resource and it's very convenient to allow any part of the project to quickly get a texture.
  * Defect 4: The resource manager part of this is actually probably correct. The reason sound doesn't play is elsewhere.
  * Defect 5: It does seem like it was a useless check; it was removed.
  * Defect 6: Interfaces MUST have public methods. No other access modifier would work. It's acceptable to leave them out, and other interfaces I've seen in the project do so too. We should discuss as a group a confirmation to leave the "public" out everywhere, or put them everywhere possibly.
  * Defect 7: Left alone because it may be needed in the near future for sound playback. Even if not, it may be useful for the game systems to have access to a file descriptor.
  * Defect 8: It was formatted all along. Some javadoc comments look funky but that's because the auto-formatter in use throughout the project makes them that way.

Follow-up from Martina:
  * I checked out the code shortly after these changes.  After a brief visual inspection, I ran the code on the emulator to ensure it still worked as it should.  After probing the program for a bit, I determined the changes did not break anything.
  * I then checked out and some of the tests in the tests suite, and nothing unusual occurred.
  * I believe it is safe to say that these changes were made successfully.

# Milestone 4: Inspection of the GameState class #
**Moderator:** Martina

**Author:** Stephen

**Reader:** Stephen

**Inspectors:** Martina, Stephen, Sten, Zenja, Kum, Johnny, Amara

**Recorder:** Sten

### Inspection Meeting ###
  * Defect 1:
    * Location: GameState.java line 78
    * Summary: Inconsistency with our naming conventions.  Private variable tempPoint should be called `_`tempPoint.
    * Discovered by: Zenja
  * Defect 2:
    * Location: GameState.java lines 169 & 178
    * Summary: beam.clear() is called in these two places and we know that one of them is causing problems with the light staying on the screen after object deletion.
    * Discovered by: Zenja
  * Defect 3:
    * Location: GameState.java lines 181-184
    * Summary: if-statement is in the wrong place in the code and needs to be moved.
    * Discovered by: Stephen
  * Defect 4:
    * Location: GameState.java lines 254-304
    * Summary: Zenja was wondering if using a for-each loop would be faster or more efficient.  We concluded that it shouldn't make a difference.
  * Defect 5:
    * Location: GameState.java lines 295-302
    * Summary: Light is always created going in the same direction.
    * Discovered by: Stephen
  * Defect 6:
    * Location: GameState.java lines 353-369
    * Summary: pause() and resume() should be renamed to systemPause() and systemResume() or something similar.
    * Discovered by: Stephen
  * Defect 7:
    * Location: GameState.java init function
    * Summary: Our try-catch blocks print the stack trace but don't actually handle the errors.  We should make a new exception type that bubbles up to the top of the game and is handled by giving the user a nice error message and quitting the game in a controlled fashion.
    * Discovered by: Martina and Stephen
  * Defect 8:
    * Location: GameState.java lines 472-492
    * Summary: Stephen wondered if these one-line methods were being used at all.  Zenja says that they are.
  * Defect 9:
    * Location: GameState.java line 637
    * Summary: Sky box is scaled improperly.  Looks like it has a low resolution but it doesn't.
    * Discovered by: Zenja
  * Defect 10:
    * Location: GameState.java line 702-706
    * Summary: glHints might be causing the problem with the disappearing light.
    * Discovered by: Zenja
  * Defect 11:
    * Location: GameState.java line 725-736
    * Summary: messageReceived() and isActive() are not needed and should be removed.
    * Discovered by: Stephen

### Follow-up Results ###

Follow-up from Martina:
  * I checked out the code shortly after these changes.  After a brief visual inspection, I ran the code on the emulator to ensure it still worked as it should.  After probing the program for a bit, I determined the changes did not break anything.
  * I later tried out a version on the phone.  It plays fantastic!
  * I believe it is safe to say that these changes were made successfully.

# Milestone 5: Inspection of the Level package #
**Moderator:** Martina

**Author:** Sten

**Reader:** Sten

**Inspectors:** Zenja, Jeff, Martina, Sten

**Recorder:** Zenja

### Inspection Meeting ###
The entire Level package was examined but not all of the classes were found to have defects.
  * Defect 1:
    * Location: XmlLevel line 22-23
    * Summary: Are width and height necessary? They seem unused.
    * Discovered by: Sten
  * Defect 2: **(Fixed)**
    * Location: XmlLevel
    * Summary: All the getters have logger messages which results in a lot of logger spam on every load. They can probably be removed now.
    * Discovered by: Zenja
  * Defect 3: **(Fixed)**
    * Location: XmlLevel: getObjects() and getTools()
    * Summary: These return mutable references and the internal lists can be modified through them. Should they perhaps guard against this?
    * Discovered by: Jeff
  * Defect 4: **(Fixed)**
    * Location: XmlLevelObject fields
    * Summary: Position and rotation members should maybe be changed to vectors instead of a bunch of floats
    * Discovered by: Martina
  * Defect 5: **(Fixed)**
    * Location: XmlLevelObject
    * Summary: Constructor could perhaps take in the positions/rotations instead of or inaddition to having many setter methods
    * Discovered by: Martina
  * Defect 6: **(Fixed)**
    * Location: XmlLevelBuilder
    * Summary: Saving to file needs to be implemented.
    * Discovered by: Sten
  * Defect 7: **(Fixed)**
    * Location: XmlLevelBuilder
    * Summary: If the level editor is a standalone program, this can be removed from the project and moved into the editor
    * Discovered by: Zenja
  * Defect 8: **(Checked)**
    * Location: XmlLevelBuilder
    * Summary: Is our style guideline to put an underscore in front of private method names, or just private fields? There is inconsistency in the project.
    * Discovered by: Zenja
  * Defect 9: **(Fixed)**
    * Location: XmlLevelBuilder - createObject()
    * Summary: The switch statements for getting a color string are repeated twice and could be extracted into a method.
    * Discovered by: Zenja
  * Defect 10: **(Fixed)**
    * Location: XmlLevelEmitter, XmlLevelGoal
    * Summary: These both have identical ways of dealing with color, so they could be moved into a superclass which these can derive from.
    * Discovered by: Zenja
  * Defect 11: **(Fixed)**
    * Location: Level package
    * Summary: Inconsistent brace style - if statements should have opening one on same line, for example
    * Discovered by: Martina
  * Defect 12: **(Refactored)**
    * Location: XmlLevelParser - parse()
    * Summary: There is a much easier way to parse using the built-in XML methods. This was discovered after this code was written though, and likely isn't worth changing.
    * Discovered by: Sten
  * Defect 13: **(Fixed)**
    * Location: XmlLevelParser - parse()
    * Summary: There are some System.out.println() calls that were used for testing but are unnecessary now.

### Follow-up Results ###

Follow-up from Sten:
  * All defects corrected or investigated
  * The method of easier parsing mentioned in defect  12 turns out to not be supported until Android 2.3
    * Ended up just refactoring the parsing.  It is now much easier to read.

Follow-up from Martina:
  * Ran the application on the emulator after these changes.  Nothing appears to be broken.

# Milestone 5: Inspection of the GUI package #
**Moderator:** Martina

**Author:** Kum

**Reader:** Kum

**Inspectors:** Zenja, Chet, Kum, Martina, Sten

**Recorder:** Martina

### Inspection Meeting ###
The entire GUI package was examined but not all of the classes were found to have defects.
  * Defect 1 **(Fixed)**:
    * Location: IWidget line 106
    * Summary: Why does the draw method not have a javadoc comment?
    * Discovered by: Martina
    * Decision: Added JavaDoc to draw(GL10) method
  * Defect 2 **(Fixed)**:
    * Location: Label line 21
    * Summary: The `_`text variable is used as a label name or id, instead of the text it contains, as the variable name implies.  Should change this to be more intuitive.
    * Discovered by: Martina
    * Decision: Changed field name to `_`identifier and changed method names to getIdentifier() and setIdentifier(String)
  * Defect 3:
    * Location: Button
    * Summary: `_`tappedTexture and `_`texture a little ambiguous as to the difference.  Should at least specify somewhere that `_`texture will always be set, and `_`tappedTexture may not.
  * Defect 4 **(Fixed)**:
    * Location: Button
    * Summary: Missing javadoc on some methods
    * Discovered by: Zenja
    * Discovered by: Martina
    * Decision: JavaDoc added to setCalleeAndMethod(Object, String) and setCalleeAndMethodWithParameter(Object, String)
  * Defect 5 **(Fixed)**:
    * Location: Button
    * Summary: This class could easily be a subclass of the Label class, as label is basically a button, only you can`t click it.
    * Discovered by: Zenja
    * Decision: Button is now a subclass of Label.
  * Defect 6:
    * Location: GUIManager line 30
    * Summary: The widgets array should really be a list or some other data structure that can grow in size.
    * Discovered by: Zenja
    * Decision: Unfinished. Insufficient time.
  * Defect 7 **(Fixed)**:
    * Location: GUIManager line 78
    * Summary: Missing javadoc on initialize method
    * Discovered by: Martina
    * Decision: Added JavaDoc to initialize().
  * Defect 8:
    * Location: GUIManager line 85
    * Summary: Texture loading should be moved from GameState to GUIManager to be consistent.
    * Discovered by: Zenja
    * Decision: Unfinished. Insufficient time.
  * Defect 9:
    * Location: GUIManager line 96
    * Summary: Does not return the number of objects in the array, as it should.  Should be fixed or removed.
    * Discovered by: Kum
    * Decision: Unfinished. Insufficient time.
  * Defect 10:
    * Location: GUIManager
    * Summary: Somewhere, `_`compensatedY is being used too much and messing up the coordinates in the Pause menu.  Should investigate this further.
    * Discovered by: Jonny
    * Decision: The issue was found (the GUIManager had not been initialized), but was not fixed due to insufficient time.
  * Defect 11:
    * Location: GUIManager line 226
    * Summary: the method tapped() could be renamed to something more clear.
    * Discovered by: Martina, Zenja
    * Decision: Unfinished. Insufficient time.
  * Defect 12:
    * Location: GUIManager line 246
    * Summary: This ìf-statement should be removed as it's not being used anymore.
    * Discovered by: Zenja
    * Decision: Unfinished. Insufficient time.
  * Defect 13:
    * Location: GUIManger line 221
    * Summary: Method name isn't very descriptive.
    * Discovered by: Martina
    * Decision: Unfinished. Insufficient time.

### Follow-up Results ###

Follow-up from Martina:
  * I was never informed of these changes, so I was unable to do the follow-up immediately after the changes were made.  However, I have since run the application and it runs as expected.

# Milestone 5: Inspection of Jeff's classes #
**Moderator:** Martina

**Author:** Jeff

**Reader:** Jeff

**Inspectors:** Kum, Martina, Amara, Jeff, Jonny, Sten, Zenja

**Recorder:** Martina

### Inspection Meeting ###
ToolBeltTest.java
  * Defect 1:
    * Location: line 32
    * Summary: This test for testProcessClick doesn't do anything.  Should probably check with developer as to how to write a test for this method.
    * Discovered by: Martina, Jeff
  * Defect 2:
    * Location: line 49 on
    * Summary: The case where placing an eraser or no tool is not handled in the source code, therefore these tests don't work.  This is an issue with the source, not the test.
    * Discovered by: Martina, Jeff
  * Defect 3:
    * Location: line 70
    * Summary: Tests commented out because they don't belong in the testAddToolStock method.  Should be removed.
    * Discovered by: Martina, Jeff
  * Defect 4:
    * Location: line 80
    * Summary: testEraseTool method isn't done.  Should be fixed.
    * Discovered by: Jeff

XMLLevelBrickTest, XmlLevelPrismTest, XmlLevelMirrorTest
  * Looks good :D

XmlLevelParser
  * Defect 5:
    * Location: line 20
    * Summary: This test is finished.  Remove this comment.
    * Discovered by: Martina, Jeff
  * Defect 6:
    * Summary: Needs comments
    * Discovered by: Martina, Jeff
  * Otherwise, looks good :)

XmlLevelTest
  * Missing comments, but looks good

CameraTest
  * Defect 7:
    * Location: line 33 on
    * Summary: Commented out things that are unnecessary because they are pre-set things that don't change, therefore testing is moot.  Could be deleted.
    * Discovered by: Martina, Zenja
  * Defect 8:
    * Location: line 136
    * Summary: Ambiguity as to how to test this, may not be valid.
    * Discovered by: Zenja

XmlLevelGoalTest
  * Defect 9:
    * Location: line 51
    * Summary: These tests will now fail since Sten changed some things in isValidColour.
    * Discovered by: Sten
  * Defect 10:
    * Location: line 50
    * Summary: This test should pass now because of previously mentioned changes Sten made.  Could be uncommented.
    * Discovered by: Sten

XmlLevelObjectTest
  * Defect 11:
    * Summary: Requires mock objects, which is in progress right now.  This needs to be completed.
    * Discovered by: Martina, Jeff

XmlLevelToolTest
  * Defect 12:
    * Location: line 35
    * Summary: Could use equalsIgnoreCase
    * Discovered by: Martina, Sten
  * Defect 13:
    * Location: line 22
    * Summary: Could also include tests for the first argument being invalid, while the second argument is, and vice versa.
    * Discovered by: Martina

### Follow-up Results ###

Follow-up from Martina:
  * Again, I was not informed when the changes were made, so I was unable to do the follow up immediately after.  However, I ran his test classes and they all passed as expected.

# Milestone 5: Inspection of the input classes and menu state class #
**Moderator:** Martina

**Author:** Jonny

**Reader:** Jonny

**Inspectors:** Zenja, Kum, Martina, Sten, Amara, Jonny

**Recorder:** Martina

### Inspection Meeting ###
InputTouchScreen.java
  * Defect 1:
    * Location: line 24
    * Summary: Should create an enum for all these static final integers.
    * Discovered by: Zenja
  * Defect 2:
    * Location: line 33
    * Summary: Could be Vector2f's, instead of separate variables for distancex, distancey etc.
    * Discovered by: Zenja
  * Defect 3:
    * Location: line 24
    * Summary: Should create an enum for all these static final integers.
    * Discovered by: Zenja

GameStateInput.java
  * Defect 4:
    * Summary: Zenja just pointed out zoom is touchy.  Jonny explained it might have to be changed.
    * Discovered by: Zenja
  * Defect 5:
    * Location: line 35
    * Summary: More static final's that could be enums
    * Discovered by: Jonny
  * Defect 6:
    * Summary: Double tap input signals will be fired continuously if the user doubletaps and drags their finger around on the screen.
    * Discovered by: Zenja
  * Defect 7:
    * Location: line 246
    * Summary: Could get rid of this commented out stuff
    * Discovered by: Jonny
  * Defect 8:
    * Location: line 249
    * Summary: Should move the touchOccurred call to the previous function.
    * Discovered by: Zenja
  * Defect 9:
    * Location: line 259, 281
    * Summary: If Ray r is null, this mouseClick method returns.  We don't know why this happens.  We should figure out this logic.
    * Discovered by: Zenja, Jonny
  * Defect 10:
    * Summary: Needs comments.  Having trouble figuring out what's going on.
    * Discovered by: Zenja, Jonny
  * Defect 11:
    * Location: line 303, 310 and 316, 323
    * Summary: The exact same function call is happening twice in a row.  This is redundant, and probably isn't necessary.  One should be removed.
    * Edit: One of the function calls are made negative.  Not sure why.  Stephen made some changes and we're not sure what its supposed to do.
    * Discovered by: Jonny
  * Defect 12:
    * Location: 395
    * Summary: Missing javadoc on flingGesture method
    * Discovered by: Martina
  * Defect 13:
    * Location: calculateFlingMove method
    * Summary: Instead of having all these if statements, could just calculate the values and set the opposite value should be handled on its own.
    * Discovered by: Zenja
  * Defect 14:
    * Location:
    * Summary: A lot of fields could be made into Vector2f's
    * Discovered by: Martina

MenuState.java - this code was also written by Kum
  * Defect 15:
    * Location: line 46
    * Summary: Bad variable name.  We don't know what `_`quad means!  Should be more appropriately named.
    * Discovered by: Zenja
  * Defect 16:
    * Location: line 70
    * Summary: This method test() should be renamed to start() to make more sense.
    * Discovered by: Jonny
  * Defect 17:
    * Location: line 83, 104, 240
    * Summary: javadoc missing from showLevelMenu(), addWidgets() and loadTexures()
    * Discovered by: Jonny
  * Defect 18:
    * Location: line 104
    * Summary: addWidgets() method unnecessary now.  Should be removed.
    * Discovered by: Zenja, Kum
  * Defect 19:
    * Location: line 153
    * Summary: init() method really ugly.  Needs comments!
    * Discovered by: Zenja, Jonny
  * Defect 20:
    * Location: line 203
    * Summary: This commented out code should be removed and moved to the LevelState
    * Discovered by: Kum, Jonny
  * Defect 21:
    * Location: line 211
    * Summary: Sound button currently doesn't do anything.  Also needs a new texture to indicate the sound is off.
    * Discovered by: Martina, Jonny

### Follow-up Results ###

Follow-up from Jonny:
  * Defect 1: Decided to leave this using static final for now.
  * Defect 2: changed to Vector2f.
  * Defect 3: this is the same with defect 1.
  * Defect 4: Cannot do anything with this without android phone to test (emulator doesn't support multi touch).
  * Defect 5: Decided to leave this using static final for now.
  * Defect 6: Once again, cannot do anything without phone to test the code / gesture.
  * Defect 7: Clean up the commented code.
  * Defect 8: Moved.
  * Defect 9: Passed it to Stephen to answer this question (his code).
  * Defect 10: More comments added.
  * Defect 11: Once again, passed to Stephen to answer this question (he added the coded in this part).
  * Defect 12: Javadoc added.
  * Defect 13: Once again, I hesitate to change it since I don't have phone to test the result, and current code is working fine, change the logic might break the code without testing it properly (emulator doesn't really able to produce proper fling gesture due to non multitouch supported).
  * Defect 14: Change variables to Vector2f done.
  * Defect 15: change `_`quad to `_`backgroundQuad.
  * Defect 16: Kum already put a comment on it saying this method is for testing purpose and the existance is temporary.
  * Defect 17: Add javadoc to showLevelMenu(), addWidgets() and loadTexures().
  * Defect 18: I left this for Kum to remove since he create this method and he might still use it.
  * Defect 19: I clean up some unused comment.
  * Defect 20: I believe this has already been moved by Kum.
  * Defect 21: This issue already solved. Sounds button is working now and it new texture already there.


Follow-up from Martina:
  * I checked out the code shortly after these changes.  I ran the application on the emulator and all seems to be in order.

# Milestone 5: Inspection of the resources test, math test package #
**Moderator:** Martina

**Author:** Chet

**Reader:** Chet

**Inspectors:** Zenja, Martina, Jonny, Jeff

**Recorder:** Jonny

### Inspection Meeting ###
AndroidSoundPlayerTest.java
**No defect. :D**

!All the primitive object test
**Looks good.**

ImageResourceTest.java, MusicResourcesTest.java
**No defects.**

ResourceManagerTest.java
  * Defect 1:
    * Location: General resourceManagerTest class
    * Summary: Instead of loading properly, could add test asset within manager if it is still correct.
    * Discovered by: Zenja

  * Defect 2:
    * Location: General resourceManagerTest class
    * Summary: Could check the ID of the textures.
    * Discovered by: Zenja

ResourceTest.java, SoundResourceTest.java, TextResourceTest.java
**Good**

TextureResourceTest.java
  * Defect 3:
    * Location: line 45
    * Summary: testGetSound() should be renamed
    * Discovered by: Zenja

CollidersTest.java
  * Defect 4:
    * Location: line 103
    * Summary: testIntersionPlane() not sure why assert(true), need to look at the code.
    * Discovered by: Chet

LerpTest.java, PlaneTest.java
**No problem**

RayTest.java
  * Defect 5:
    * Location: RayTest
    * Summary: Not sure why some codes have been commenting out. Need to look into it.
    * Discovered by: Martina

Follow-up from Chet:
  * Defect 1: Checking that the contents of a Resource are correct is done in the TextResourceTest class
  * Defect 2: Added a test that checks to ensure the id is what is expected
  * Defect 3: Renamed method to testGetTexture()
  * Defect 4: Code is never used, so no need to test it
  * Defect 5: Uncommented code, tests pass

Follow-up from Martina:
  * I ran these tests after these changes, and all seems to be well.

# Milestone 5: Inspection of test classes for GameObject system and TimeSystem #
**Moderator:** Martina

**Author:** Martina

**Reader:** Martina

**Inspectors:** Zenja, Martina, Chet, Jonny, Jeff

**Recorder:** Zenja

### Inspection Meeting ###
BoxTest.java: no defects. Perhaps double-check that beamInteract is tested sufficiently.

  * Defect 1:
    * Location: EmitterTest - testBeamInteract()
    * Summary: No test written, but there should be one.
    * Discovered by: Martina
  * Defect 2:
    * Location: GameObjectTest
    * Summary: This test file should either be scrapped (since it's an abstract class) or somehow improved to test the subclassing aspect better. Currently it's the same as a Box test.
    * Discovered by: Martina
  * Defect 3:
    * Location: GridTest - testGetCellHeight()
    * Summary: Makes a call to getCellWidth instead of getCellHeight (mindless copy/paste error)
    * Discovered by: Martina
  * Defect 4:
    * Location: GridTest - testGetGridPosition()
    * Summary: Commented out. Restore it and fix it.
    * Discovered by: Everyone
  * Defect 5:
    * Location: LightTest - various methods
    * Summary: Commented out for an unknown reason.
    * Discovered by: Everyone
  * Defect 6:
    * Location: MirrorTest, PrismTest - testBeamInteract()
    * Summary: Can be more comprehensive. It could check to make sure that a new beam is created after bouncing off the mirror for example.
    * Discovered by: Martina
  * Defect 7:
    * Location: PrismTest - testGetCollisionSphere()
    * Summary: The X coordinate is checked 3 times instead of X,Y,Z.
    * Discovered by: Martina
  * Defect 8:
    * Location: SkyboxTest
    * Summary: Everything is commented out.
    * Discovered by: Everyone
  * Defect 9:
    * Location: TimeSystemTest - testFreeze()
    * Summary: Could implement a real test rather than the current ones which don't really do anything
    * Discovered by: Everyone

Follow-up from Martina:
  * Defect 1: Added to this test case and ran the test class.  Test passed as expected.
  * Defect 2: My rationale is that if I change it to incorporate all the subclasses, it's just redundant.  So I removed the class altogether.
  * Defect 3: Changed and re-ran test.  Works fine.
  * Defect 4: Fixed this test to actually test the getGridPosition() method.  Ran the test and it passes.
  * Defect 5: The getRotation method for Light.java doesn't do anything significant; it just returns null.  I changed this test to ensure that.  Same with the getScale method.
  * Defect 6: Added asserts to ensure there is more than one beam of light now in the LightBeamCollection for both test classes.
  * Defect 7: Changed this to be the appropriate values.  Tests pass.
  * Defect 8: Looked into the source code for Skybox.java, and there is currently no way to test it.  All its fields are private, and its methods do not provide a way to access them to see if their values are correct.  Leaving this as is for now, and will inform the developer who wrote it of this.
  * Defect 9: Added tests for getter methods.  However, the freeze and applyScale methods modify other fields that do not have getter methods and are private.  This makes this impossible to test.  Will speak with developer on this issue.

# Milestone 5: Inspection of Amara's Test Code #
**Moderator:** Martina

**Author:** Amara

**Reader:** Amara

**Inspectors:** Zenja, Martina, Sten, Amara, Jeff, Jonny

**Recorder:** Jeff

### Inspection Meeting ###
!GUIManagerTest

  * Defect 1:
    * Location: GUIManagerTest- testTouchOccuredMotionEvent()
    * Summary: No test written, Discuess with Jonny; A wrapper on TouchOccuredFloatFloat
    * Discovered by: Amara
    * Decision: Added comment and redirected to internal method

  * Defect 2:
    * Location: All TestSuite
    * Summary: Need explicit private declaration and underscore in front so that it adheres to our naming convention
    * Discovered by: Jonny
    * Decision: yes consistency!

InputButtonTest

  * Defect 3:
    * Location: whole class
    * Summary: Verify what 'magnitude' means, add comment. REPLICA ISLAND code. No need for change.
    * Discovered by: Martina
    * Decision: Replica Island code added comment

LuminanceTest

  * Defect 4:
    * Location: Whole class
    * Summary: Document that 1x1 extrapolates to 1x1 on the board and not 0->(n -1). Actual is 1 -> n
    * Discovered by: Martina, Amara
    * Decision: Comment added to clarify offset

  * Defect 5:
    * Location: testCaseSix()
    * Summary: Not yet implemented
    * Discovered by: Amara, Jeff
    * Decision: Will be implemented

LabelTest

  * Defect 6:
    * Location: testLabel()
    * Summary: Instantiating label should be gone. Have test of nullidity instead
    * Discovered by: Amara, Jeff, Martina
    * Decision: Will eliminate redundancy

ButtonTest

  * Defect 7:
    * Location: Whole class
    * Summary: A bit redundant on getters and setters. Isolate getters and setters
    * Discovered by: Martina
    * Decision: Will combine getters and setters

### Follow-Up Results ###
Follow-up from Amara:
  * Defect 1: Added comment and redirected to internal method
  * Defect 2: Left alone; only 2-3 lines are duplicated and there would be some issues with trying to create a common method.
  * Defect 3: Magnitude boundaries are unknown not documented anywhere,
the test itself does test a single case of magnitude
  * Defect 4: added comment to notify of offset
  * Defect 5: Implemented test case 5
  * Defect 6: Moved construction into constructor from setup
  * Defect 7: Moved construction into constructor from setup, deleted get tests and renamed set functions to set-get.

Follow-up from Martina:
  * Ran his tests after he made his changes.  All seems to be well.