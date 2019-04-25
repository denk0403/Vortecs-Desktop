import java.awt.Graphics;

public interface Displayable {

	// displays this object onto the given graphics object
	public void display(Graphics g);
	
	public <T> T accept(DisplayableVisitor<T> visitor);

}
