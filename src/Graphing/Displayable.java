package Graphing;
import java.awt.Graphics;

public interface Displayable {

	// displays this object onto the given graphics object
	public void display(Graphics g);
	
	// displays this object onto the given graphics object relative
	// to where the given camera is positioned, on the absolute coordinate
	// system of the given Display
	default void display(Graphics g, Camera camera, Display display) {
		this.display(g);
	}

}
