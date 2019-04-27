package Controls;
import java.awt.Graphics;

// represents a box for user inputs onto a plane
public interface InputBox {

	// draws the box's item onto the given graphics object
	public void drawItem(Graphics g);
	
	// returns the input
	public <T> T getInput();

}
