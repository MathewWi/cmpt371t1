

# Deliverable 5 #
## High Level Analysis ##

Currently the project builds a binary and runs the release of our game.

The application can be loaded onto an Android phone and run the same as on the AVD.

There is also a smoke test implemented and can be run to ensure project quality before commit.

### Features Implemented ###

Build completes.

Program runs correctly.

The game is fully functional.  There is a large number of levels which can be played.  These can now be selected from the level menu, which is a new feature of this deliverable.

There is also a level editor implemented, although it was solely for our purposes.

There is also the inclusion of the in-game help menu in this deliverable.

### Features to Be Implemented in the future ###

There is a handful of us that wish to work on this project further past its due date.

This would include all the new game mechanics we had to drop for this project.  In addition, we would like to implement a scoreboard, a ranking system, a random level generator, and a level editor which the user could use themselves.

### STI/Defect Information ###

Defects and bugs can be viewed here: http://code.google.com/p/cmpt371t1/issues/list

Common problems of the development environment can be found here: CommonIssues

## Specific Status Report ##
**Resource Manager**
  * Capable of loading and managing all necessary resources.
  * Creates OpenGL textures out of images.

**Music**
  * Background music and sound effects fully functional.

**Input**
  * Use of ReplicaIsland open source code.
  * Touch and drag inputs are currently stable.
  * Pinch for zooming are now working.
  * Tapping objects on the toolbelt will allow you to tap the grid in order to place them.
  * A double tap on a mirror object will rotate it by 90 degrees.
  * Improved from last deliverable.

**GUI**
  * All menu states fully functional, including the newly added level menu state.

**Level Loader**
  * Level information stored in XML files.
  * Loader takes in an XML file and parses it to create an object containing all level information.
  * Loader currently stable.
  * Will load levels sequentially.  If a level is passed, the game will move onto the next one.
  * Now implemented to take a list of levels and execute them in order.

**Graphics**
  * Basic primitives (sphere, box, etc.).
  * Full grid, background and general play space implemented.
  * Basic textures load onto play tools.
  * Would like to implemented some special effects to make it nicer to look at.

**Game**
  * Improved light beam graphics from the last deliverable.
  * As well, the grid draws nicer.

**Smoke Test**
  * Currently runs on the TeamCity server.
  * Tests all source classes using the TestSuite.

**GUI Testing**
  * Use of Robotium.
  * Fully functional.

**Unit Tests**
  * Complete with respect to the current source code completion.

**Coverage Testing**
  * Use of EMMA to do coverage testing.
  * Fully functional and produces many statistics and information.

**Mock Testing**
  * An abundant amount of time was spent on this, and it was ultimately unsuccessful.  Our testing lead, Jeff, gave up after spending 30+ hours on it.
  * Testing stubs currently in test suite under the ca.sandstorm.luminance.mock package, but do nothing.

## Overall Status of Game ##

> We are happy to say we have a fully playable game that we all are proud of!


# Deliverable 4 #
## High Level Analysis ##

Currently the project builds a binary and runs the beta of our game.

The application can be loaded onto an Android phone and run the same as on the AVD.

There is also a smoke test implemented and can be run to ensure project quality before commit.

### Features Implemented ###

Build completes.

Program runs correctly.

Loads the grid play area and first level, which can be completed. User then progresses through the following levels.

Touch features such as pinch and swipe work well.  Swipe moves the camera around the grid, while pinch zooms in and out.  The input is at a point where we believe it is satisfactory.

Toolbelt is fully functional and can be interacted with.  Objects can be selected from the toolbelt and placed on the grid.

Mirrors that are placed on the grid will bound the light off at a 90 degree angle.  If double tapped, it will flip the mirror so that the light may bounce off in the opposite direction.

Prisms that are placed on the grid split the light into three colours: red, blue and green.  These three separate light beam act independently and react to mirrors the same as the white light.

Light that reaches a goal that is the same colour as that goal will activate it.  if all goals in a level are activated, the level is complete.

Once a level is complete, the game will move onto the next level.  There are currently 5 levels available for play.

Level loader is fully functional.

Fully intractable GUI is implemented.  There is currently a start menu which works fully, and a pause menu which is integrated but still need a little work.

### Features to Be Implemented ###

Enhanced graphics - simple graphics currently implemented.

Full audio capabilities - audio currently works, and we have both sound effects and music at our disposal.  It is just a matter of integrating them into the game.

50+ levels.

Level Editor.

### STI/Defect Information ###

Defects and bugs can be viewed here: http://code.google.com/p/cmpt371t1/issues/list

Common problems of the development environment can be found here: CommonIssues

## Specific Status Report ##
**Resource Manager**
  * Capable of loading and managing all necessary resources.
  * Creates OpenGL textures out of images.

**Music**
  * Two song composed for in-game music.
  * Simple sound effects are ready to be integrated.

**Input**
  * Use of ReplicaIsland open source code.
  * Touch and drag inputs are currently stable.
  * Pinch for zooming are now working.
  * Tapping objects on the toolbelt will allow you to tap the grid in order to place them.
  * A double tap on a mirror object will rotate it by 90 degrees.

**GUI**
  * Rendered using OpenGL quads.
  * Menu primitives robust
  * GUIManager created; responsible for managing buttons and labels
  * Buttons created, can perform action on tap.
  * Start menu fully functional
  * In-game pause menu will allow you to pause the game, and resume it upon pressing a button.  We would like to incorporate functionality into the menu to allow the user to restart the level if they feel like they messed up badly.

**Level Loader**
  * Level information stored in XML files.
  * Loader takes in an XML file and parses it to create an object containing all level information.
  * Loader currently stable.
  * Will load levels sequentially.  If a level is passed, the game will move onto the next one.

**Graphics**
  * Basic primitives (sphere, box, etc.).
  * Full grid, background and general play space implemented.
  * Basic textures load onto play tools.
  * Would like to implemented some special effects to make it nicer to look at.

**Game**
  * Currently draws a grid and loads the first level.
  * User is able to interact with the game.  They are able to place tools from the toolbelt on the grid.  These tools work the way that they should.  The light interacts with the tools logically and correctly.
  * Toolbelt is implemented and working, but may benefit from refactoring some functionality to a higher level

**Smoke Test**
  * Currently runs on the TeamCity server.
  * Tests all source classes using the TestSuite.

**GUI Testing**
  * Use of Robotium.
  * Full test suite under construction.

**Unit Tests**
  * Complete with respect to the current source code completion.
  * Will be updated as more source is changed/added.

**Coverage Testing**
  * Use of EMMA to do coverage testing.
  * Fully functional and produces many statistics and information.

**Mock Testing**
  * Basic Mocking underway
  * Currently incomplete.

## Overall Status of Game ##
> Currently loads the grid that will be where the levels will be created to be played.  The SkyBox that is used to display the background works on both the emulator and the android phone now.  All textures load properly as well.  A simple level is loaded and is fully playable, though more functionality and levels are planned to be added.

> The user can interact with the level as we had intended.  They can touch and swipe to move the camera around and pinch for zooming.  They can also tap the toolbelt to select a tool they wish to place on the grid, and are able to do so.

> We currently have a fully playable game.  All that we really need now - aside from polishing development we would like to do - is to add enough levels to make it challenging and fun.

# Deliverable 3 #
## High Level Analysis ##

### State of Project Build ###

Currently the project builds a binary and runs the alpha of our game.

The application can be loaded onto an Android phone and run the same as on the AVD.

There is also a smoke test implemented and can be run to ensure project quality before commit.

### Features Implemented ###

Build completes.

Program runs correctly.

Loads the grid play area and simple game level.

Touch features such as pinch and swipe work well.  Swipe moves the camera around the grid, while pinch zooms in and out.  Some refinement may still need to be done.

Game tools logically work i.e. prisms split light, mirrors bounce light etc.

Toolbelt is fully functional and can be interacted with.  Objects can be selected from the toolbelt and placed on the grid.

Essentially a basic level is loaded and can be interacted with.

Level loader is fully functional.

Fully intractable GUI is implemented, though not integrated as of yet.

### Features to Be Implemented ###

Touch screen GUI - GUI is designed, needs to be integrated into the application.

Enhanced graphics - simple graphics currently implemented.

Full audio capabilities.

100+ levels.

Random level generator.

### STI/Defect Information ###

Defects and bugs can be viewed here: http://code.google.com/p/cmpt371t1/issues/list

Common problems of the development environment can be found here: CommonIssues

## Specific Status Report ##
**Resource Manager**
  * Capable of loading and managing all necessary resources.
  * Creates OpenGL textures out of images.
  * Audio resources are capable of loading, but playback (in other system) still broken.

**Music**
  * One song composed for in-game music.
  * Hasn't been mixed or mastered yet, but it's good enough for use.

**Input**
  * Use of ReplicaIsland open source code.
  * Touch and drag inputs are currently stable.
  * Pinch for zooming are now working.

**GUI**
  * Rendered using OpenGL quads.
  * Menu primitives robust
  * GUIManager created; responsible for managing buttons and labels
  * Buttons created, can perform action on tap.

**Level Loader**
  * Level information stored in XML files.
  * Loader takes in an XML file and parses it to create an object containing all level information.
  * Loader currently stable.

**Graphics**
  * Basic primitives (sphere, box, etc.).
  * Full grid, background and general play space implemented.
  * Basic textures load onto play tools.

**Game**
  * Currently draws a grid and loads a pre-defined level.
  * User is able to interact with the game.  They are able to place tools from the toolbelt on the grid.  These tools work the way that they should.  The light interacts with the tools logically and correctly.
  * Toolbelt is implemented and working, but may benefit from refactoring some functionality to a higher level

**Smoke Test**
  * Currently runs on the TeamCity server.
  * Tests all source classes using the TestSuite.

**GUI Testing**
  * Use of Robotium.
  * Full test suite under construction.

**Unit Tests**
  * Complete with respect to the current source code completion.
  * Will be updated as more source is changed/added.

**Coverage Testing**
  * Use of EMMA to do coverage testing.
  * Fully functional and produces many statistics and information.

**Mock Testing**
  * Basic Mocking underway
  * Currently incomplete.

## Overall Status of Game ##
> Currently loads the grid that will be where the levels will be created to be played.  The SkyBox that is used to display the background works on both the emulator and the android phone now.  All textures load properly as well.  A simple level is loaded and is fully playable, though more functionality and levels are planned to be added.

> The user can interact with the level as we had intended.  They can touch and swipe to move the camera around and pinch for zooming.  They can also tap the toolbelt to select a tool they wish to place on the grid, and are able to do so.  With this, there is currently the issue of
if the user goes to move the camera with an object selected in the toolbelt, the tool will be placed on the grid.

> The project can also be loaded into a Menu state if you un-comment line 65 Luminance.java in the method OnCreate.  From here, two buttons are loaded.  Every time the menu is clicked, a log message is sent to the logger as "Menu has been touched".  If a button is tapped, then "Button has been tapped" is sent to the logger, therefore showing these buttons work.

# Deliverable 2 #
## High Level Analysis ##

### State of Project Build ###

Currently the project builds a binary and runs a simple version of our game.

The application can be loaded onto an Android phone and run the same as on the AVD.

There is also a simple and as of yet incomplete smoke test implemented and can be run to ensure project quality before commit.

### Features Implemented ###

Build completes.

Program runs correctly.

Loads the grid play area, as well as multiple boxes and spheres to represent a level.

Touch features such as pinch and swipe work well.  Some refinement may still need to be done.

### Features to Be Implemented ###

Touch screen GUI - basic elements created & fleshed out, buttons tappable.

Enhanced graphics - simple graphics currently implemented.

Full audio capabilities.

100+ levels.

Random level generator.

### STI/Defect Information ###

Currently there are no known STI's or defects within the code.  The test environment was not complete up until this point.  When more tests are implemented, defects and STI's will become more apparent.

Common problems of the development environment can be found here: CommonIssues

## Specific Status Report ##
**Resource Manager**
  * Capable of loading and managing most resources.
  * Simple textures loading.
  * Sound/music implementation understood, although currently not working.

**Input**
  * Use of ReplicaIsland open source code.
  * Touch and drag inputs are currently stable, although need refining.
  * Pinch(zoom) are now working.

**GUI**
  * Creating our own custom menu primitives.
  * Menu primitives robust
  * GUIManager created; responsible for managing buttons
  * Buttons created, can perform action on tap

**Level Loader**
  * Use of XML to store level information.
  * Loader currently stable.

**Graphics**
  * Basic primitives (sphere, box, etc.).
  * Full grid, background and general play space implemented.

**Game**
  * Currently draws a grid and loads a pre-defined level.
  * Does not have any playable functionality.

**Smoke Test**
  * Currently runs on the TeamCity server.
  * It has "three thumbs up" tests currently.
    * Tests suite includes Resource Manager and Input classes.

**GUI Testing**
  * Use of Robotium.
  * Full test suite under construction.

**Unit Tests**
  * Currently underway.
  * Unit Tests that are completed work well; have already caught bugs.

## Overall Status of Game ##
> Currently loads the grid that will be where the levels will be created to be played.  It also loads a simple box and sphere, to show that our primitive objects are working.  The SkyBox that represents the background works on the emulator, but not on an android phone.  This issue will be need to be resolved.

> When loaded on the phone, touch, swipe and pinch inputs work, although will need to be refined for precision and smoothness.

> The project can also be loaded into a Menu state if you un-comment line 65 Luminance.java in the method OnCreate.  From here, two buttons are loaded.  Every time the menu is clicked, a log message is sent to the logger as "Menu has been touched".  If a button is tapped, then "Button has been tapped" is sent to the logger, therefore showing these buttons work.  This currently only works on the emulator.