/*package ca.sandstorm.luminance.test.usecase;

import javax.vecmath.Vector2f;

import android.test.ActivityInstrumentationTestCase2;
import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.Luminance;

import com.jayway.android.robotium.solo.Solo;

public class LuminanceTest extends ActivityInstrumentationTestCase2<Luminance> {
	private Solo solo;

	// Screen size
	float screenWidth = 200;
	float screenHeight = 300; // default setting

	// Origin top left corner
	int MATRIX_WIDTH = 8;
	int MATRIX_HEIGHT = 8;

	int xCoordView = 0;
	int yCoordView = 0;

	*//**
	 * [0:55:15] Jonny: use engine.getinstance().getstatusbar() +
	 * engine.getinstance().gettitlebar() for geting the y
	 *//*
	// Offset of the matrix
	float yOffsetMatrix = 50;
	Vector2f matrixLocation = new Vector2f(0, yOffsetMatrix);
	float SPACE_BTWN_SQUARES = 5;

	*//**
	 * A matrix with centre positions for every square used for easily placing
	 * objects.
	 *//*
	Vector2f[][] matrix = new Vector2f[MATRIX_HEIGHT][MATRIX_WIDTH];

	public LuminanceTest() {
		super("ca.sandstorm.luminance", Luminance.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		this.solo = new Solo(getInstrumentation(), getActivity());

		for (int row = 0; row < MATRIX_HEIGHT; row++) {

			for (int col = 0; col < MATRIX_WIDTH; col++) {

				// adjust x to match next square
				xCoordView += SPACE_BTWN_SQUARES;

				matrix[row][col] = new Vector2f(xCoordView, yCoordView);
			}
			// reset x
			xCoordView = 0;
			yCoordView += SPACE_BTWN_SQUARES;
		}

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

	*//**
	 * Start the first level of the game
	 *//*
	public void startGame() {
		// 320x480
		screenHeight = (float) Engine.getInstance().getViewHeight();
		screenWidth = (float) Engine.getInstance().getViewWidth();

		assertTrue(screenHeight > 0);
		float x = 0.175f * screenWidth;
		float y = 0.350f * screenHeight;
		float width = 0.650f * screenWidth;
		float height = 0.100f * screenHeight;
		solo.clickLongOnScreen(x + (width / 2), y + (width / 2));
		solo.clickLongOnScreen(320 / 3, (480 / 2) + 30);
		for (int i = 1; i < 320; i++) {
			for (int y2 = 1; y2 < 480; y2++) {
				solo.clickOnScreen(x, y2);
			}
		}
		// solo.clickOnButton("Start");
		// assertTrue(solo.searchText("Start"));
	}

	*//**
	 * Pauses level
	 *//*
	public void pause() {
		solo.clickOnButton("Pause");
	}

	*//**
	 * Restarts level
	 *//*
	public void restart() {
		solo.clickOnButton("restart");
	}

	*//**
	 * Places prism at given coordinates
	 * 
	 * @param row
	 * @param col
	 *//*
	public void placePrism(int row, int col) {
		solo.clickOnButton("prism");
		assertTrue(solo.searchButton("prism"));

		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
	}

	*//**
	 * Places mirror at given coordinates
	 * 
	 * @param row
	 * @param col
	 *//*
	public void placeMirror(int row, int col) {
		solo.clickOnButton("mirror");
		assertTrue(solo.searchButton("mirror"));

		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
	}

	*//**
	 * Rotates mirror based on numberTaps
	 * 
	 * @param row
	 * @param col
	 * @param numberTaps
	 *//*
	public void rotateMirror(int row, int col) {

		// Double tap,
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);

	}

	public void testDisplayBlackBox() throws Exception {

	}

	*//**
	 * Erases Object at location
	 * 
	 * @param row
	 * @param col
	 *//*
	public void erase(int row, int col) {
		solo.clickOnButton("eraser");
		assertTrue(solo.searchButton("eraser"));

		solo.clickOnScreen(matrix[row][col].x, matrix[row][col].y);
	}

	*//**
	 * Test Case (1): Light to obstacle collision
	 * 
	 * Purpose: To test and verify connections to objects are being handled
	 * appropriately.
	 * 
	 * Prerequisite: A game level has been started
	 *//*
	public void testCaseOne() {
		startGame();
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

		placeMirror(0, 8);
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
		erase(0, 8);
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

	}

	
	 * || || 1 || 2 || 3 || 4 || 5 || 6 || 7 || 8 || || 1 ||(W)|| || || || || ||
	 * B || || || 2 || || B || || || || || || || || 3 || || || B || || || || ||
	 * || || 4 || || || || B || || || || || || 5 || || || ||(G)|| B || || || ||
	 * || 6 || || || || || || || || || || 7 || B || || || || || || || || || 8 ||
	 * || || || || ||(R)|| ||(B)||
	 

	*//**
	 * Test Case (2): Object interaction/function
	 * 
	 * Purpose: To test and verify that objects unique, functional, and interact
	 * with each other.
	 * 
	 * Prerequisite: A game level has been started
	 *//*
	public void testCaseTwo() {

		startGame();
		// User starts game
		// User chooses random level
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

		rotateMirror(6, 0);
		// User Rotates mirror
		// Light reflects to a different angle
		erase(6, 0);
		// User chooses eraser
		// User erases mirror
		// Light continues on with no obstacle in the way
		pause();
		// User presses pause
		// System brings up pop up menu
		restart();
		// User presses restart
		// System brings up the same map reset

	}

	*//**
	 * Test Case (3): Object to obstacle collision
	 * 
	 * Purpose: To test and verify that objects unique, functional, and interact
	 * with each other.
	 * 
	 * Prerequisite: level 2
	 *//*
	public void testCaseThree() {

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
		rotateMirror(4, 6);
		// User Rotates mirror
		// Light reflects to a different angle
		placeMirror(4, 6);
		// User chooses mirror
		// User tries to place a mirror on top of another mirror
		placeMirror(3, 4);
		// User tries to place a mirror on top of a Wall
		placeMirror(4, 5);
		// User tries to place a mirror on top of prism
		rotateMirror(4, 6);
		placeMirror(1, 5);
		placeMirror(6, 5);
	}

	*//**
	 * Test Case (4): User statistics registration
	 * 
	 * Purpose: To test and verify that toolbar/high score is correctly updated
	 * after each object use
	 * 
	 * Prerequisite: Level 2
	 *//*
	public void testCaseFour() {
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
		rotateMirror(4, 6);
		placeMirror(1, 5);
		placeMirror(6, 5);
		rotateMirror(6, 5);
		// User beats game
		// System updates high score with time
		// User chooses main menu
		// User selects high score bored
		// User sees high score bored is updated
	}

	*//**
	 * Test Case (5): User transition between states, saved menu, resume
	 * 
	 * Purpose: To test and verify that state changes are taking place and are
	 * handled appropriately
	 * 
	 * Prerequisite: Level 2
	 *//*
	public void testCaseFive() {
		startGame();
		completeLevel1();
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
		// User rotates phone
		// Phones changes orientation
		// Game is automatically paused
		exit();
		// User exits the Luminance
		openLuminance();
		// User reenters Luminance
		// System shows pop up menu screen
		turnOnSound();
		// User chooses sound on
		// User chooses resume
		resume();
		// System reloads all previously made moves
		pause();
		// User chooses pause
		// System loads pop up menu
		mainMenu();
		// User chooses main menu
		// System loads main menu
	}

	*//**
	 * Test Case (6): Stress test
	 * 
	 * Purpose: To test and verify that is able to cope under stranious
	 * conditions
	 * 
	 * Prereq: level1
	 *//*
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

	private void reduceMemory() {
		// TODO Auto-generated method stub

	}

	private void tutorial() {
		// TODO Auto-generated method stub

	}

	private void mainMenu() {
		// TODO Auto-generated method stub

	}

	private void resume() {
		// TODO Auto-generated method stub

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

	private void completeLevel1() {
		// TODO Auto-generated method stub

	}

	private void completeLevel2() {
		// TODO Auto-generated method stub

	}

	public void testDisplayWhiteBox() {
		assertTrue(true);
	}
}
*/