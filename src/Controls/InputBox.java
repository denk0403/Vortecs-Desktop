package Controls;
import java.awt.Graphics;

import Matrices.Matrix2D;

// represents a box for user inputs onto a plane
public interface InputBox {

	// draws the box's item onto the given graphics object
	public void drawItem(Graphics g, Matrix2D m);
	
	// returns the input
	public <T> T getInput();

}
