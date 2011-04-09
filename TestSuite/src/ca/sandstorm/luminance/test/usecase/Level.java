/**
 * 
 */
package ca.sandstorm.luminance.test.usecase;

/**
 * Inteface for Level
 * 
 * @author Amara Daal
 * 
 */
public interface Level {

	/**
	 * Completes this level
	 */
	public void complete();

	/**
	 * Calculate the coordinates of touch in this architecture,
	 * and make a grid of them for easier input
	 */
	public void setGrid();

}
