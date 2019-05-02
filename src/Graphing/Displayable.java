package Graphing;
import java.awt.Graphics;

/**
 * Represents a Displayable object to interact with the Display class
 * @author Dennis Kats
 *
 */
public interface Displayable {

	// displays this object onto the given graphics object
	@ Deprecated
	public void display(Graphics g);
	
	/**
	 *  Displays this object onto the given graphics object relative
	 *  to where the given camera is positioned, on the absolute coordinate
	 *  system of the given Display
	 * @param g The graphics object to draw onto
	 * @param camera The camera object representing where the graphics object is
	 * @param display The display with this Displayable
	 */
	public void display(Graphics g, Camera camera, Display display);

}
