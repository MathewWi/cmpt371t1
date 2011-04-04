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
 * Use case testing
 * @author Amara Daal
 * NOTE: Since Luminance doesn't use android.widget none of the buttons 
 * will be detected by robotium
 */
public class LuminanceTest extends ActivityInstrumentationTestCase2<Luminance> {
	private Solo solo;
    //private static final //logger //logger = //loggerFactory.get//logger(LuminanceTest.class);
    
	// Screen size
	float screenWidth = 320;
	float screenHeight = 430; // default setting


	int timeBetweenOperations = 3000;

	// Offset of the matrix
	float screenOffsetY = 25;
	Vector2f matrixLocation;
	float SPACE_BTWN_SQUARES = 29;
	
	float xCoordView = (SPACE_BTWN_SQUARES/2); //Start in the middle of the square
	float yCoordView = (SPACE_BTWN_SQUARES/2);
	Instrumentation _inst;

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
	 * Calculate the global grid square positions callsed at the start of each level
	 * @param newMatrixLocatin the upper left corner of the grid
	 * @param matrixWidth cols
	 * @param matrixHeight rows
	 */
	public void calculateGridPositions(Vector2f newMatrixLocation, int matrixHeight , int matrixWidth){
		//logger.info("LuminanceTestSuite: UseCase: Calculating grid squarePositions");
		
		matrix = new Vector2f[matrixHeight][matrixWidth];
		xCoordView = (SPACE_BTWN_SQUARES/2);
		yCoordView = (SPACE_BTWN_SQUARES/2);
		for (int row = 0; row < matrixHeight; row++) {

			for (int col = 0; col < matrixWidth; col++) {
				matrix[row][col] = new Vector2f(xCoordView+newMatrixLocation.x, yCoordView+newMatrixLocation.y);
				//*****
				//***->
				// adjust x to match next square
				// start in the middle of the square
				xCoordView += SPACE_BTWN_SQUARES;


			}
			// reset x
			xCoordView = (SPACE_BTWN_SQUARES/2);
			yCoordView += SPACE_BTWN_SQUARES;
		}
	}
	
	
	/**
	 * Setup robotium to test Luminance activity
	 */
	public void setUp() throws Exception {
		super.setUp();
		this.solo = new Solo(this.getInstrumentation(), this.getActivity());

	}

	/**
	 * Start the first level of the game
	 */
	public void startGame() {	
		solo.sleep(1000);
		float startButtonX = 183;
		float startButtonY = 232;
		//logger.info("LuminanceTestSuite: UseCase: Starting Game");
		
		calculateGridPositions(new Vector2f(29, 226), 3, 9);
		// 320x480
		screenHeight = (float) Engine.getInstance().getViewHeight();
		screenWidth = (float) Engine.getInstance().getViewWidth();

		long downTime = SystemClock.uptimeMillis();
		// event time MUST be retrieved only by this way!
		long eventTime = SystemClock.uptimeMillis();
		
		
		MotionEvent event = MotionEvent.obtain(downTime, eventTime,
												MotionEvent.ACTION_DOWN,
												startButtonX, 
												startButtonY, 
												0);
		_inst.sendPointerSync(event);
		eventTime = SystemClock.uptimeMillis() + 300;
		
		event = MotionEvent.obtain(downTime, eventTime,
									MotionEvent.ACTION_UP,
									startButtonX, 
									startButtonY, 
									0);
		_inst.sendPointerSync(event);
		
		//for(int i = 0; i < 130; i++){
	//		solo.clickOnScreen(startButtonX, startButtonY);
	//	}

		
		solo.sleep(5000);
	}

	/**
	 * Pauses level
	 */
	public void pause() {
		
		//logger.info("LuminanceTestSuite: UseCase: Pausing Game");
		 solo.clickOnScreen(295, 447);
			solo.sleep(5000);
	}

	/**
	 * Restarts level
	 */
	public void restart() {
		//logger.info("LuminanceTestSuite: UseCase: Restarting Level");
	}

	/**
	 * Places prism at given coordinates
	 * 
	 * @param row
	 * @param col
	 */
	public void placePrism(int row, int col) {
		row-=1;
		col-=1;
		//logger.info("LuminanceTestSuite: UseCase: Placing prism at "+ "(" + row + "," + col+")");
		solo.clickOnScreen(94, 443);
		solo.sleep(timeBetweenOperations);
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.sleep(timeBetweenOperations);
	}

	/**
	 * Places mirror at given coordinates
	 * 
	 * @param row
	 * @param col
	 */
	public void placeMirror(int row, int col) {
		row-=1;
		col-=1;
		//logger.info("LuminanceTestSuite: UseCase: Placing Mirror at "+ "(" + row + "," + col+")");
		//solo.clickOnScreen(31, 431);

		solo.sleep(timeBetweenOperations);
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.sleep(timeBetweenOperations);
	}

	/**
	 * Rotates mirror based on numberTaps
	 * 
	 * @param row
	 * @param col
	 * @param numberTaps
	 */
	public void rotateMirror(int row, int col) {
		row-=1;
		col-=1;
		//logger.info("LuminanceTestSuite: UseCase: Rotating mirror at "+ "(" + row + "," + col+")");
		// Double tap,
		solo.sleep(timeBetweenOperations);
		
		long downTime = SystemClock.uptimeMillis();
		// event time MUST be retrieved only by this way!
		long eventTime = SystemClock.uptimeMillis();
		
		
		MotionEvent event = MotionEvent.obtain(downTime, eventTime,
												MotionEvent.ACTION_DOWN,
												matrix[row][col].x, 
												matrix[row][col].y, 
												0);
		_inst.sendPointerSync(event);
		//eventTime = SystemClock.uptimeMillis() + 10;
		
		event = MotionEvent.obtain(downTime, eventTime,
									MotionEvent.ACTION_UP,
									matrix[row][col].x, 
									matrix[row][col].y, 
									0);
		_inst.sendPointerSync(event);
		
		event = MotionEvent.obtain(downTime, eventTime,
									MotionEvent.ACTION_DOWN,
									matrix[row][col].x, 
									matrix[row][col].y, 
									0);
		_inst.sendPointerSync(event);
		
		event = MotionEvent.obtain(downTime, eventTime,
									MotionEvent.ACTION_UP,
									matrix[row][col].x, 
									matrix[row][col].y, 
									0);
		_inst.sendPointerSync(event);
		//solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		//solo.sleep(1);//500, 100,300
		//solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.sleep(timeBetweenOperations);

		//cause exception
		//solo.clickOnScreen(matrix[row][50].x, matrix[row][col].y);
	}

//	public void testDisplayBlackBox() throws Exception {
//		completeMirrorBasics();
//		completeMirrorBasics();
//	}

	/**
	 * Erases Object at location
	 * 
	 * @param row
	 * @param col
	 */
	public void erase(int row, int col) {
		row-=1;
		col-=1;
		//logger.info("LuminanceTestSuite: UseCase: Erasing object at "+ "(" + row + "," + col+")");
		 solo.clickOnScreen(159, 434);
		solo.sleep(timeBetweenOperations);
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.sleep(timeBetweenOperations);
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
		
		//logger.info("LuminanceTestSuite: UseCase: Test Case One");
		//logger.info("LuminanceTestSuite: UseCase: Matrix Located at "+ "(" + 29 + "," + 226+")");
		
		matrixLocation = new Vector2f(29, 226);
		_inst = getInstrumentation();
		
		startGame();
		placeMirror(1,1);
		rotateMirror(1,1);
		//completeMirrorBasics();
		//completeDoubleSided();
/*		placeMirror(6, 1);
		// User chooses mirror
		// User places mirror(1)
		// White Light collides with mirror
		// White Light reflects south
		// User chooses mirror

		//placeMirror(6, 5);
		// User places mirror(2)
		// Light collides with mirror
		//placePrism(8, 5);
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

		rotateMirror(6, 5);
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
		rotateMirror(4, 6);
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
		rotateMirror(4, 6);
		// User rotates mirror(4)
		// Green light collides with mirror
		// Green light connects with green orb
		placeMirror(8, 6);
		// User places mirror (7) at (8, 6)
		// Blue light collides with mirror
		// Blue light connects with blue orb
		// User completes level
*/
	}

	private void completeMirrorBasics() {
		//logger.info("LuminanceTestSuite: UseCase: Completing level 1 ");
		
		calculateGridPositions(new Vector2f(29, 226), 3, 9);
		placeMirror(1, 1);
		rotateMirror(1,1);
		placeMirror(1,5);
		placeMirror(3, 5);
		
		placeMirror(3, 7);
		rotateMirror(3, 7);
		placeMirror(1, 7);
		rotateMirror(1, 7);
		
	}

	public void tearDown() throws Exception {
		try {
			this.solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}
//	/*
//	 * || || 1 || 2 || 3 || 4 || 5 || 6 || 7 || 8 || || 1 ||(W)|| || || || || ||
//	 * B || || || 2 || || B || || || || || || || || 3 || || || B || || || || ||
//	 * || || 4 || || || || B || || || || || || 5 || || || ||(G)|| B || || || ||
//	 * || 6 || || || || || || || || || || 7 || B || || || || || || || || || 8 ||
//	 * || || || || ||(R)|| ||(B)||
//	 */
//
//	/**
//	 * Test Case (2): Object interaction/function
//	 * 
//	 * Purpose: To test and verify that objects unique, functional, and interact
//	 * with each other.
//	 * 
//	 * Tests Level: "More Prism"
//	 */
//	public void testCaseTwo() {
//		//logger.info("LuminanceTestSuite: UseCase: Test Case Two");
//		//logger.info("LuminanceTestSuite: UseCase: Matrix Located at "+ "(" + 29 + "," + 226+")");
//		matrixLocation = new Vector2f(29, 226);
//		
//		startGame();
//		completeMirrorBasics();
//		completeDoubleSided();
//		// User starts game
//		// Game view is shown
//		// prism -> mirror -> orb
//		placeMirror(6, 0);
//		// User chooses mirror
//		// User places mirror(1)
//		// White Light collides with mirror
//		// White Light reflects south
//		// User chooses mirror
//
//		placeMirror(6, 5);
//		// User places mirror(2)
//		// Light collides with mirror
//		placePrism(8, 5);
//		// User chooses prism
//		// User places Prism
//		// System refracts light into RGB
//		// System deactivates prism from toolbar
//		// Light collides with outer boundary
//		// prism -> outer boundary
//		// Light collides with differing color orb
//		// prism -> wrong orb
//		// Light collides with mirror bounces back to prism
//		// prism -> mirror -> prism
//		// placeMirror()
//		// User chooses mirror
//		// User places mirror
//		// Light collides with mirror
//		// User chooses eraser
//		erase(8, 5);
//		// User erases prism
//		// Prism disappears
//		// RGB disappears
//		// System reactivates prism in toolbar
//		// White light collides with mirror
//		placePrism(8, 5);
//		// User chooses prism
//		// User places prism
//		// System refracts light into RGB
//		// System deactivates prism from toolbar
//		// Lights reflects off of mirror
//
//		rotateMirror(6, 0);
//		// User Rotates mirror
//		// Light reflects to a different angle
//		erase(6, 0);
//		// User chooses eraser
//		// User erases mirror
//		// Light continues on with no obstacle in the way
//		pause();
//		// User presses pause
//		// System brings up pop up menu
//		restart();
//		// User presses restart
//		// System brings up the same map reset
//
//	}
//
//	/**
//	 * Test Case (3): Object to obstacle collision
//	 * 
//	 * Purpose: To test and verify that objects unique, functional, and interact
//	 * with each other.
//	 * 
//	 * Tests Level: "Double Sided"
//	 */
//	public void testCaseThree() {
//		startGame();
//		completeMirrorBasics();
//		completeDoubleSided();
//		// prism -> mirror -> orb
//		placePrism(4, 5);
//		// User chooses prism
//		// User places Prism
//		// System refracts light into RGB
//		// System deactivates prism from toolbar
//		placeMirror(4, 6);
//		// User chooses mirror
//		// User places mirror
//		// Light collides with mirror
//		erase(3, 4);
//		// User chooses eraser
//		// User tries to erase wall
//		rotateMirror(4, 6);
//		// User Rotates mirror
//		// Light reflects to a different angle
//		placeMirror(4, 6);
//		// User chooses mirror
//		// User tries to place a mirror on top of another mirror
//		placeMirror(3, 4);
//		// User tries to place a mirror on top of a Wall
//		placeMirror(4, 5);
//		// User tries to place a mirror on top of prism
//		rotateMirror(4, 6);
//		placeMirror(1, 5);
//		placeMirror(6, 5);
//	}

//	/**
//	 * Test Case (4): User statistics registration
//	 * 
//	 * Purpose: To test and verify that toolbar/high score is correctly updated
//	 * after each object use
//	 * 
//	 * Tests Level: "Double Sided"
//	 */
//	public void testCaseFour() {
//		startGame();
//		completeMirrorBasics();
//		completeDoubleSided();
//		
//		placePrism(4, 5);
//		// User chooses prism
//		// User places prism in empty grid square
//		// Prism count is reduced in toolbar
//		erase(4, 6);
//		// User chooses eraser
//		// User erases prism
//		// System increments number of prisms
//		placePrism(4, 5);
//		placeMirror(4, 6);
//		rotateMirror(4, 6);
//		placeMirror(1, 5);
//		placeMirror(6, 5);
//		rotateMirror(6, 5);
//		// User beats game
//		// System updates high score with time
//		// User chooses main menu
//		// User selects high score bored
//		// User sees high score bored is updated
//	}

//	/**
//	 * Test Case (5): User transition between states, saved menu, resume
//	 * 
//	 * Purpose: To test and verify that state changes are taking place and are
//	 * handled appropriately
//	 * 
//	 * Tests Level: "Double Sided"
//	 */
//	public void testCaseFive() {
//		startGame();
//		
//		completeMirrorBasics();
//		completeDoubleSided();
//		
//		placePrism(4, 5);
//		// User chooses prism
//		// User places Prism
//		// System refracts light into RGB
//		// System deactivates prism from toolbar
//		placeMirror(1, 6);
//		// User chooses mirror
//		// User places mirror
//		// Light collides with mirror
//		stateCallRecieved();
//		// User receives a call
//		// Game sound is automatically turned off
//		alterOrientation("landscape");
//		alterOrientation("portrait");
//		// User rotates phone
//		// Phones changes orientation
//		// Game is automatically paused
//		exit();
//		// User exits the Luminance
//		openLuminance();
//		// User reenters Luminance
//		// System shows pop up menu screen
//		turnOnSound();
//		// User chooses sound on
//		// User chooses resume
//		resume();
//		// System reloads all previously made moves
//		pause();
//		// User chooses pause
//		// System loads pop up menu
//		mainMenu();
//		// User chooses main menu
//		// System loads main menu
//	}

//	/**
//	 * Test Case (6): Stress test
//	 * 
//	 * Purpose: To test and verify that is able to cope under stranious
//	 * conditions
//	 * 
//	 * Prereq: level1
//	 */
//	public void testCaseSix() {
//		startGame();
//		// User starts game
//		// Android's Luminance memory is reduced
//		tutorial();
//		// User chooses first level play through tutorial
//		reduceMemory();
//		// Android slowly reduces memory for Luminance to 0
//		// Luminance system realizes memory is too low returns error exits
//		reduceMemory();
//		// Android's Luminance memory is reset to normal
//		// Android's hard drive space is set to very low
//		startGame();
//		// User starts game
//		tutorial();
//		// User chooses first level play through tutorial
//		// Luminance gives error hard drive space is too low
//		// Luminance exits
//	}

	private void reduceMemory() {


	}

	private void tutorial() {
		// TODO Auto-generated method stub

	}

	private void mainMenu() {
		// TODO Auto-generated method stub

	}

	private void resume() {
		 solo.clickLongOnScreen(162, 173);


	}

	private void turnOnSound() {
		// TODO Auto-generated method stub

	}

	private void openLuminance() {
		// TODO Auto-generated method stub

	}

	private void exit() {
		// TODO Auto-generated method stub

	}

	private void alterOrientation(String orientation) {
		if (orientation.equalsIgnoreCase("landscape")) {
			solo.setActivityOrientation(solo.LANDSCAPE);
		} else if (orientation.equalsIgnoreCase("portrait")) {
			solo.setActivityOrientation(solo.PORTRAIT);
		}

	}

	private void stateCallRecieved() {
		// TODO Auto-generated method stub

	}



	private void completeDoubleSided() {
		calculateGridPositions(new Vector2f(29, 226), 3, 9);
		// TODO Auto-generated method stub

	}

	public void testDisplayWhiteBox() {
		assertTrue(true);
	}
}
