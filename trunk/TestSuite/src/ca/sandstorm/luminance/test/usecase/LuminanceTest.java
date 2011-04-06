package ca.sandstorm.luminance.test.usecase;

import javax.vecmath.Vector2f;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.Luminance;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import ca.sandstorm.luminance.R;

//import org.slf4j.//logger;
//import org.slf4j.//loggerFactory;
import com.jayway.android.robotium.solo.Solo;

/**
 * Use case based GUI testing
 * 
 * @author Amara Daal NOTE: Since Luminance doesn't use android.widget none of
 *         the buttons will be detected by robotium
 */
public class LuminanceTest extends ActivityInstrumentationTestCase2<Luminance> {

	// Robotium
	private Solo solo;

	// Levels
	Level levelArray[];

	// Tested levels
	int numberOfTestedLevels = 10;

	// Level names
	int mirrorBasics = 0;
	int zigZag = 1;
	int justLikeChristmas = 2;
	int thinkAboutIt = 3;
	int yourBrainOnRGB = 4;
	int entryLevel = 5;
	int doubleSided = 6;
	int thePrism = 7;
	int morePrism = 8;
	int firstBlood = 9;

	// Screen size 320x430
	int timeBetweenOperations = 3000;

	// Time between click commands
	int timeBetweenClicks = 200;

	// Offset of the matrix from top left corner of the screen
	Vector2f matrixLocation;

	// Space between grid squares
	float spaceBtwnSquares = 29;

	// Start in the middle of the square
	float xCoordView = (spaceBtwnSquares / 2);
	float yCoordView = (spaceBtwnSquares / 2);

	// Instrumentation used to send touch events directly to the engine
	Instrumentation _inst;

	// Package we are testing
	private static final String TARGET_PACKAGE_ID = "ca.sandstorm.luminance";

	/**
	 * A matrix with centre positions for every square used for easily placing
	 * objects.
	 */
	Vector2f[][] matrix;

	/**
	 * Construct Usecase tests
	 */
	public LuminanceTest() {
		super(TARGET_PACKAGE_ID, Luminance.class);

	}

	/**
	 * Calculate the global grid square positions called at the start of each
	 * level
	 * 
	 * @precondition newMatrixLocation is not null
	 * @postcondition A symbolic representation of the grid is created
	 * 
	 * @param newMatrixLocatin
	 *            the upper left corner of the grid
	 * @param matrixWidth
	 *            cols
	 * @param matrixHeight
	 *            rows
	 */
	public void calculateGridPositions(Vector2f newMatrixLocation,
			int gotSpaceBtwnSquares, int matrixHeight, int matrixWidth) {
		// logger.info("LuminanceTestSuite: UseCase: Calculating grid squarePositions");
		spaceBtwnSquares = gotSpaceBtwnSquares;
		matrix = new Vector2f[matrixHeight][matrixWidth];
		xCoordView = (spaceBtwnSquares / 2);
		yCoordView = (spaceBtwnSquares / 2);
		for (int row = 0; row < matrixHeight; row++) {

			for (int col = 0; col < matrixWidth; col++) {
				matrix[row][col] = new Vector2f(xCoordView
						+ newMatrixLocation.x, yCoordView + newMatrixLocation.y);
				// *****
				// ***->
				// adjust x to match next square
				// start in the middle of the square
				xCoordView += spaceBtwnSquares;

			}
			// reset x
			xCoordView = (spaceBtwnSquares / 2);
			yCoordView += spaceBtwnSquares;
		}
	}

	/**
	 * Setup robotium to test Luminance activity
	 * 
	 * @precondition All your new levels were added to the array here requires
	 *               that a private class is created which implements Level
	 *               interface
	 * 
	 */
	public void setUp() throws Exception {
		super.setUp();
		this.solo = new Solo(this.getInstrumentation(), this.getActivity());

		// Create and order tests
		levelArray = new Level[numberOfTestedLevels];

		// Loading Levels here
		levelArray[mirrorBasics] = new MirrorBasics();
		levelArray[zigZag] = new ZigZag();
		levelArray[justLikeChristmas] = new JustLikeChristmas();
		levelArray[thinkAboutIt] = new ThinkAboutIt();
		levelArray[yourBrainOnRGB] = new YourBrainOnRGB();
		levelArray[entryLevel] = new EntryLevel();
		levelArray[doubleSided] = new DoubleSided();
		levelArray[thePrism] = new ThePrism();
		levelArray[morePrism] = new MorePrism();
		levelArray[firstBlood] = new FirstBlood();

	}

	/**
	 * Completes all test levels solved for the game
	 * 
	 * @precondition Luminance must be on first level in game
	 * @postcondition All test levels are solved,
	 */
	public void completeAllLevels() {
		for (int i = 0; i < numberOfTestedLevels; i++) {
			levelArray[i].complete();
		}
	}

	/**
	 * Completes level specified
	 * 
	 * @precondition User has already reached the level in game
	 * @postcondition The level is completed and the next level starts
	 * @param level
	 */
	public void completeLevel(int level) {
		levelArray[level].complete();
	}

	/**
	 * Reaches level specified to bypass prior levels used for testing a certain
	 * level
	 * 
	 * @precondition Luminance must be on first level in game
	 * @postcondition all levels prior to "level" is completed
	 * @param level
	 */
	public void reachLevel(int level) {
		for (int i = 0; i < level; i++) {
			levelArray[i].complete();
		}
	}

	/**
	 * Start the first level of the game by pressing the start button
	 * 
	 * @precondition Luminance is on the menu screen
	 * @postcondition First level is started for the user NOTE: Robotium fails
	 *                at clicking the "Start" button commented out code shows
	 *                130 clicks were sent still registration was inconsistent.
	 *                We resorted to sending manual events to the game engine
	 */
	public void startGame() {
		solo.sleep(10000);

		// Location of the start button from the top left
		float startButtonX = 183;
		float startButtonY = 232;

		int startMatrixRows = 3;
		int startMatrixCols = 9;

		Vector2f startMatrixLocation = new Vector2f(29, 226);

		// logger.info("LuminanceTestSuite: UseCase: Starting Game");

		// Calculate the grid positions
		calculateGridPositions(startMatrixLocation, 29, startMatrixRows,
				startMatrixCols);

		/*
		 * Simulate touch event, inform the engine that a motion event has
		 * occured
		 */
		long downTime = SystemClock.uptimeMillis();
		// event time MUST be retrieved only by this way!
		long eventTime = SystemClock.uptimeMillis();

		// Loops and presses start button 10 times instead of just 1, behaves
		// differently
		// on different systems needed to ensure this registers on all systems
		for (int i = 0; i < 10; i++) {

			MotionEvent event = MotionEvent.obtain(downTime, eventTime,
					MotionEvent.ACTION_DOWN, startButtonX, startButtonY, 0);
			_inst.sendPointerSync(event);
			eventTime = SystemClock.uptimeMillis() + 200;

			event = MotionEvent.obtain(downTime, eventTime,
					MotionEvent.ACTION_UP, startButtonX, startButtonY, 0);
			_inst.sendPointerSync(event);
			solo.sleep(500);
		}

		// Previous way of clicking the start button
		// for(int i = 0; i < 130; i++){
		// solo.clickOnScreen(startButtonX, startButtonY);
		// }

		solo.sleep(5000);
	}

	/**
	 * Pauses level
	 * 
	 * @precondition Luminance is in game
	 * @postcondition Luminance is paused
	 */
	public void pause() {

		// logger.info("LuminanceTestSuite: UseCase: Pausing Game");
		solo.clickOnScreen(295, 447);
		solo.sleep(5000);
	}

	/**
	 * Restarts level
	 * 
	 * @precondition Luminance is in a level
	 * @postcondition the level is restarted
	 * @param from
	 *            either ingame or inmenu
	 */
	public void restart(String from) {
		if (from.equalsIgnoreCase("ingame")) {
			solo.clickOnScreen(296, 441);
			solo.sleep(timeBetweenClicks);
			solo.clickOnScreen(142, 241);

		} else if (from.equalsIgnoreCase("inmenu")) {
			solo.clickOnScreen(142, 241);
		}

	}

	/**
	 * Places prism at given coordinates Grid starts at (1,1)
	 * 
	 * @precondition Luminance is in game
	 * @postcondition the prism is placed
	 * @param row
	 *            start from 1
	 * @param col
	 *            start from 1
	 */
	public void placePrism(int row, int col) {
		// Offset user choice to vector index
		row -= 1;
		col -= 1;
		// logger.info("LuminanceTestSuite: UseCase: Placing prism at "+ "(" +
		// row + "," + col+")");

		// press on toolbar
		solo.clickOnScreen(94, 443);
		// place object
		solo.sleep(timeBetweenOperations);
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.sleep(timeBetweenOperations);
	}

	/**
	 * Places mirror at given coordinates Grid starts at (1,1)
	 * 
	 * @precondition Luminance is in game
	 * @postcondition the mirror is placed
	 * @param row
	 *            start from 1
	 * @param col
	 *            start from 1
	 */
	public void placeMirror(int row, int col) {
		// Offset user choice to vector index
		row -= 1;
		col -= 1;
		// logger.info("LuminanceTestSuite: UseCase: Placing Mirror at "+ "(" +
		// row + "," + col+")");
		// solo.clickOnScreen(31, 431);

		solo.sleep(timeBetweenOperations);
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.sleep(timeBetweenOperations);
	}

	/**
	 * Rotates mirror by double tap Grid starts at (1,1)
	 * 
	 * @precondition Luminance is in game, the object must rotatable
	 * @postcondition the object at the location is rotated
	 * @param row
	 *            start from 1
	 * @param col
	 *            start from 1
	 */
	public void rotate(int row, int col) {
		// Offset user choice to vector index
		row -= 1;
		col -= 1;
		// logger.info("LuminanceTestSuite: UseCase: Rotating mirror at "+ "(" +
		// row + "," + col+")");

		// Double tap,
		solo.sleep(timeBetweenOperations);

		long downTime = SystemClock.uptimeMillis();
		// event time MUST be retrieved only by this way!
		long eventTime = SystemClock.uptimeMillis();

		MotionEvent event = MotionEvent.obtain(downTime, eventTime,
				MotionEvent.ACTION_DOWN, matrix[row][col].x,
				matrix[row][col].y, 0);
		_inst.sendPointerSync(event);
		// eventTime = SystemClock.uptimeMillis() + 10;

		event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP,
				matrix[row][col].x, matrix[row][col].y, 0);
		_inst.sendPointerSync(event);

		event = MotionEvent.obtain(downTime, eventTime,
				MotionEvent.ACTION_DOWN, matrix[row][col].x,
				matrix[row][col].y, 0);
		_inst.sendPointerSync(event);

		event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP,
				matrix[row][col].x, matrix[row][col].y, 0);
		_inst.sendPointerSync(event);

		solo.sleep(timeBetweenOperations);

	}

	// public void testDisplayBlackBox() throws Exception {
	// completeMirrorBasics();
	// completeMirrorBasics();
	// }

	/**
	 * Erases Object at location Grid starts at (1,1)
	 * 
	 * @precondition Luminance is in game, the object is erasable
	 * @postcondition the object is erased
	 * @param row
	 *            start from 1
	 * @param col
	 *            start from 1
	 */
	public void erase(int row, int col) {
		row -= 1;
		col -= 1;
		// logger.info("LuminanceTestSuite: UseCase: Erasing object at "+ "(" +
		// row + "," + col+")");
		solo.clickOnScreen(159, 434);
		solo.sleep(timeBetweenOperations);
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.sleep(timeBetweenOperations);
	}

	private void reduceMemory() {

	}

	/**
	 * Starts the tutorial for the user
	 * 
	 * @precondition the user is in menu
	 * @postcodition the tutorial is started
	 */
	private void tutorial() {
		// TODO Auto-generated method stub
		solo.clickOnScreen(22, 441);
	}

	/**
	 * Brings the user back to the main menu
	 * 
	 * @precondition the user is in game playing a level
	 * @postcondition the user is brought back to the main menu
	 */
	private void mainMenu() {
		// TODO Auto-generated method stub
		solo.clickOnScreen(145, 318);

	}

	/**
	 * Resumes the game
	 * 
	 * @precondition the in game menu has been pulled up
	 * @postcondition the game is resumed
	 * @param state
	 *            either ingame or inmenu
	 */
	private void resume(String state) {
		if (state.equalsIgnoreCase("ingame")) {
			solo.clickOnScreen(299, 444);
			solo.sleep(timeBetweenClicks);
			solo.clickOnScreen(162, 173);

		} else if (state.equalsIgnoreCase("inmenu")) {
			solo.clickOnScreen(162, 173);
		}

	}

	/**
	 * Moves onto the next level
	 * 
	 * @precondition the level complete graphic is on screen
	 * @postcondition the game moves onto the next level
	 */
	private void nextLevel() {
		solo.clickOnScreen(161, 266);
	}

	/**
	 * Runs white box tests
	 */
	public void testDisplayWhiteBox() {
		assertTrue(true);
	}

	/**
	 * Toggles sound on or off
	 * 
	 * @precondition none
	 * @postcondition the sound is either turned on or off
	 * @param state
	 */
	private void toggleSound(String state) {
		if (state.equalsIgnoreCase("ingame")) {
			solo.clickOnScreen(297, 443);
			solo.sleep(timeBetweenClicks);
			solo.clickOnScreen(143, 318);
			solo.sleep(timeBetweenClicks);
			solo.clickOnScreen(302, 442);

		} else if (state.equalsIgnoreCase("inmenu")) {
			solo.clickOnScreen(302, 442);
		}
		// TODO Auto-generated method stub

	}

	/**
	 * NOTE: There may be no way to implement this without sending command line
	 * args to emulator
	 */
	private void openLuminance() {
		// TODO Auto-generated method stub

	}

	/**
	 * Brings user back to main menu
	 */
	private void exit() {
		solo.clickOnScreen(297, 443);
		solo.sleep(timeBetweenClicks);
		solo.clickOnScreen(143, 318);
		// TODO Auto-generated method stub

	}

	/**
	 * Chooses the help option
	 * 
	 * @param from
	 *            either ingame or inmenu
	 */
	private void help(String from) {
		if (from.equalsIgnoreCase("ingame")) {
			solo.clickOnScreen(297, 443);
			solo.sleep(timeBetweenClicks);
			solo.clickOnScreen(143, 318);
			solo.sleep(timeBetweenClicks);

			solo.clickOnScreen(161, 308);
		} else if (from.equalsIgnoreCase("inmenu")) {
			solo.clickOnScreen(161, 308);
		}

	}

	/**
	 * Alters the emulator orientation
	 * 
	 * @precondition The emulator is on
	 * @postcondtion The orientation of the emulator is swapped
	 * @param orientation
	 *            either landscape or portrait
	 */
	private void alterOrientation(String orientation) {
		if (orientation.equalsIgnoreCase("landscape")) {
			solo.setActivityOrientation(solo.LANDSCAPE);
		} else if (orientation.equalsIgnoreCase("portrait")) {
			solo.setActivityOrientation(solo.PORTRAIT);
		}

	}

	/**
	 * NOTE: May not be implemented
	 */
	private void stateCallRecieved() {
		// TODO Auto-generated method stub

	}

	/**
	 * Test Case (1): Light to obstacle collision
	 * 
	 * Purpose: To test and verify connections to objects are being handled
	 * appropriately.
	 * 
	 * Tests Level: "The Prism"
	 * 
	 * Grid Top Left(29, 226)
	 */
	public void testCaseOne() {

		// logger.info("LuminanceTestSuite: UseCase: Test Case One");
		// logger.info("LuminanceTestSuite: UseCase: Matrix Located at "+ "(" +
		// 29 + "," + 226+")");

		matrixLocation = new Vector2f(29, 226);
		_inst = getInstrumentation();

		startGame();
		reachLevel(thePrism);

		// placeMirror(1,1);
		// rotateMirror(1,1);
		// completeMirrorBasics();
		// completeDoubleSided();
		placeMirror(6, 1);
		// User chooses mirror
		// User places mirror(1)
		// White Light collides with mirror
		// White Light reflects south
		// User chooses mirror

		// placeMirror(6, 5);
		// User places mirror(2)
		// Light collides with mirror
		// placePrism(8, 5);
		// User chooses prism
		// User places Prism
		// System refracts light into RGB
		// System deactivates prism from toolbar
		// Light collides with outer boundary
		// prism -> outer boundary
		// Light collides with differing color orb
		// prism -> wrong orb
		// Light collides with mirror bounces back to prism
		// prism -> mirror -> prism

		placeMirror(1, 8);
		// User places mirror(3)
		// Light collides with mirror
		// Light collides with Wall obstacle
		// prism -> brick

		rotate(6, 5);
		// User Rotates mirror(2)
		// Light reflects to a different angle
		// Prism no longer refracts light
		// All coloured lights dissapear

		erase(6, 5);
		// User erases mirror(2)
		erase(1, 8);
		// User erases mirror(3)
		erase(8, 5);
		// User erases prism
		placePrism(5, 6);
		// User chooses prism
		// User places prism (5, 6)
		// System refracts light into RGB
		// System deactivates prism from toolbar
		// Green light collides with outer boundary
		// Blue light collides with wall obstacle
		// Red light intersects with red orb
		placeMirror(4, 6);
		// User chooses mirror
		// User places mirror(4) at (4, 6)
		// Green light collides with mirror
		// green light connects with green orb
		rotate(4, 6);
		// User Rotates mirror(4)
		// Light reflects south
		placeMirror(4, 7);
		// User chooses mirror
		// User places mirror (5) at (4, 7)
		// Green light reflects to east
		// Light intersects with different light
		erase(4, 7);
		// User chooses eraser
		// User erases mirror(5)
		placeMirror(4, 8);
		// User chooses mirror
		// User places mirror (6) at (4, 8)
		// Light collides with mirror
		// two different lights into one orb
		// prism -> mirror -> mirror -> wrong orb
		erase(4, 8);
		// User chooses eraser
		// User erases mirror(6)
		rotate(4, 6);
		// User rotates mirror(4)
		// Green light collides with mirror
		// Green light connects with green orb
		placeMirror(8, 6);
		// User places mirror (7) at (8, 6)
		// Blue light collides with mirror
		// Blue light connects with blue orb
		// User completes level

	}

	/**
	 * Tear down method to dispose robotium
	 */
	public void tearDown() throws Exception {
		try {
			this.solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}

	/*
	 * || || 1 || 2 || 3 || 4 || 5 || 6 || 7 || 8 || || 1 ||(W)|| || || || || ||
	 * B || || || 2 || || B || || || || || || || || 3 || || || B || || || || ||
	 * || || 4 || || || || B || || || || || || 5 || || || ||(G)|| B || || || ||
	 * || 6 || || || || || || || || || || 7 || B || || || || || || || || || 8 ||
	 * || || || || ||(R)|| ||(B)||
	 */

	/**
	 * Test Case (2): Object interaction/function
	 * 
	 * Purpose: To test and verify that objects unique, functional, and interact
	 * with each other.
	 * 
	 * Tests Level: "More Prism"
	 */
	public void testCaseTwo() {
		// logger.info("LuminanceTestSuite: UseCase: Test Case Two");
		// logger.info("LuminanceTestSuite: UseCase: Matrix Located at "+ "(" +
		// 29 + "," + 226+")");
		matrixLocation = new Vector2f(29, 226);

		startGame();
		reachLevel(morePrism);
		// completeDoubleSided();
		// User starts game
		// Game view is shown
		// prism -> mirror -> orb
		placeMirror(6, 0);
		// User chooses mirror
		// User places mirror(1)
		// White Light collides with mirror
		// White Light reflects south
		// User chooses mirror

		placeMirror(6, 5);
		// User places mirror(2)
		// Light collides with mirror
		placePrism(8, 5);
		// User chooses prism
		// User places Prism
		// System refracts light into RGB
		// System deactivates prism from toolbar
		// Light collides with outer boundary
		// prism -> outer boundary
		// Light collides with differing color orb
		// prism -> wrong orb
		// Light collides with mirror bounces back to prism
		// prism -> mirror -> prism
		// placeMirror()
		// User chooses mirror
		// User places mirror
		// Light collides with mirror
		// User chooses eraser
		erase(8, 5);
		// User erases prism
		// Prism disappears
		// RGB disappears
		// System reactivates prism in toolbar
		// White light collides with mirror
		placePrism(8, 5);
		// User chooses prism
		// User places prism
		// System refracts light into RGB
		// System deactivates prism from toolbar
		// Lights reflects off of mirror

		rotate(6, 0);
		// User Rotates mirror
		// Light reflects to a different angle
		erase(6, 0);
		// User chooses eraser
		// User erases mirror
		// Light continues on with no obstacle in the way
		pause();
		// User presses pause
		// System brings up pop up menu
		restart("inmenu");
		// User presses restart
		// System brings up the same map reset

	}

	/**
	 * Test Case (3): Object to obstacle collision
	 * 
	 * Purpose: To test and verify that objects unique, functional, and interact
	 * with each other.
	 * 
	 * Tests Level: "More Prism"
	 */
	public void testCaseThree() {
		startGame();
		reachLevel(morePrism);

		// completeDoubleSided();
		// prism -> mirror -> orb
		placePrism(4, 5);
		// User chooses prism
		// User places Prism
		// System refracts light into RGB
		// System deactivates prism from toolbar
		placeMirror(4, 6);
		// User chooses mirror
		// User places mirror
		// Light collides with mirror
		erase(3, 4);
		// User chooses eraser
		// User tries to erase wall
		rotate(4, 6);
		// User Rotates mirror
		// Light reflects to a different angle
		placeMirror(4, 6);
		// User chooses mirror
		// User tries to place a mirror on top of another mirror
		placeMirror(3, 4);
		// User tries to place a mirror on top of a Wall
		placeMirror(4, 5);
		// User tries to place a mirror on top of prism
		rotate(4, 6);
		placeMirror(1, 5);
		placeMirror(6, 5);
	}

	/**
	 * Test Case (4): User statistics registration
	 * 
	 * Purpose: To test and verify that toolbar/high score is correctly updated
	 * after each object use
	 * 
	 * Tests Level: "More Prism"
	 */
	public void testCaseFour() {
		startGame();
		reachLevel(morePrism);

		// completeDoubleSided();

		placePrism(4, 5);
		// User chooses prism
		// User places prism in empty grid square
		// Prism count is reduced in toolbar
		erase(4, 6);
		// User chooses eraser
		// User erases prism
		// System increments number of prisms
		placePrism(4, 5);
		placeMirror(4, 6);
		rotate(4, 6);
		placeMirror(1, 5);
		placeMirror(6, 5);
		rotate(6, 5);
		// User beats game
		// System updates high score with time
		// User chooses main menu
		// User selects high score bored
		// User sees high score bored is updated
	}

	/**
	 * Test Case (5): User transition between states, saved menu, resume
	 * 
	 * Purpose: To test and verify that state changes are taking place and are
	 * handled appropriately
	 * 
	 * Tests Level: "More Prism"
	 */
	public void testCaseFive() {
		startGame();

		reachLevel(morePrism);

		// completeDoubleSided();

		placePrism(4, 5);
		// User chooses prism
		// User places Prism
		// System refracts light into RGB
		// System deactivates prism from toolbar
		placeMirror(1, 6);
		// User chooses mirror
		// User places mirror
		// Light collides with mirror
		stateCallRecieved();
		// User receives a call
		// Game sound is automatically turned off
		alterOrientation("landscape");
		alterOrientation("portrait");
		// User rotates phone
		// Phones changes orientation
		// Game is automatically paused
		exit();
		// User exits the Luminance
		openLuminance();
		// User reenters Luminance
		// System shows pop up menu screen
		toggleSound("inmenu");
		// User chooses sound on
		// User chooses resume
		startGame();
		// resume();
		// System reloads all previously made moves
		pause();
		// User chooses pause
		// System loads pop up menu
		mainMenu();
		// User chooses main menu
		// System loads main menu
	}

	/**
	 * Test Case (6): Stress test
	 * 
	 * Purpose: To test and verify that is able to cope under stranious
	 * conditions
	 * 
	 * Tests Level: "Tutorial"
	 */
	public void testCaseSix() {
		startGame();
		// User starts game
		// Android's Luminance memory is reduced
		tutorial();
		// User chooses first level play through tutorial
		reduceMemory();
		// Android slowly reduces memory for Luminance to 0
		// Luminance system realizes memory is too low returns error exits
		reduceMemory();
		// Android's Luminance memory is reset to normal
		// Android's hard drive space is set to very low
		startGame();
		// User starts game
		tutorial();
		// User chooses first level play through tutorial
		// Luminance gives error hard drive space is too low
		// Luminance exits
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class MirrorBasics implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			// logger.info("LuminanceTestSuite: UseCase: Completing level 1 ");

			calculateGridPositions(new Vector2f(29, 226), 29, 3, 9);
			placeMirror(1, 1);
			rotate(1, 1);
			placeMirror(1, 5);
			placeMirror(3, 5);

			placeMirror(3, 7);
			rotate(3, 7);
			placeMirror(1, 7);
			rotate(1, 7);

		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class ZigZag implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			calculateGridPositions(new Vector2f(29, 140), 52, 5, 5);
			rotate(1, 1);
			rotate(5, 5);
			nextLevel();

			// TODO Auto-generated method stub

		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class JustLikeChristmas implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			calculateGridPositions(new Vector2f(110, 140), 29, 8, 3);
			placeMirror(1, 3);
			placeMirror(3, 3);
			rotate(3, 3);
			placeMirror(3, 1);
			rotate(3, 1);
			placeMirror(5, 1);
			placeMirror(5, 3);
			placeMirror(7, 3);
			rotate(7, 3);
			placeMirror(7, 2);
			rotate(7, 2);
			nextLevel();

			// TODO Auto-generated method stub

		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class ThinkAboutIt implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			calculateGridPositions(new Vector2f(29, 140), 52, 5, 5);
			placeMirror(2, 4);
			rotate(2, 4);
			placeMirror(3, 4);
			placePrism(3, 3);
			nextLevel();

		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class ThePrism implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			calculateGridPositions(new Vector2f(29, 140), 52, 6, 6);
			placePrism(5, 4);
			placeMirror(6, 4);
			rotate(6, 4);
			placeMirror(5, 6);
			rotate(5, 6);
			placeMirror(5, 1);
			nextLevel();
		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class MorePrism implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			calculateGridPositions(new Vector2f(29, 140), 33, 8, 8);
			placeMirror(1, 6);
			placePrism(7, 6);
			placeMirror(7, 4);
			placeMirror(7, 8);
			nextLevel();
		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class FirstBlood implements Level {
		/**
		 * Completes this level TODO:
		 */
		public void complete() {

		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class DoubleSided implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			calculateGridPositions(new Vector2f(29, 140), 52, 5, 5);
			placeMirror(4, 1);
			placeMirror(4, 5);
			rotate(4, 5);
			placeMirror(4, 3);
			nextLevel();
		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class EntryLevel implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			calculateGridPositions(new Vector2f(29, 140), 52, 5, 5);
			rotate(3, 3);
			rotate(3, 3);
			placePrism(3, 2);
			placeMirror(1, 2);
			rotate(1, 2);
			placeMirror(5, 2);
			nextLevel();
		}
	}

	/**
	 * Level in Luminance
	 * 
	 * @author Amara Daal
	 * 
	 */
	private class YourBrainOnRGB implements Level {
		/**
		 * Completes this level
		 */
		public void complete() {
			calculateGridPositions(new Vector2f(29, 140), 44, 6, 6);
			// Rotate red emmitter
			rotate(2, 6);
			rotate(2, 6);
			// Rotate blue emmitter
			rotate(3, 5);
			// Rotate green emitter
			rotate(4, 6);
			rotate(4, 6);
			placeMirror(4, 1);
			placeMirror(2, 2);
			placeMirror(1, 5);
			nextLevel();
		}
	}
}
