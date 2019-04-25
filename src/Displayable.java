import java.awt.Graphics;

public interface Displayable {

	// displays this object onto the given graphics object
	public void display(Graphics g);
	
	// displays this object onto the given graphics object relative
	// to where the given camera is positioned
	default void display(Graphics g, Camera camera) {
		this.display(g);
	}
	
	// visitor for displayable objects
	public <T> T accept(DisplayableVisitor<T> visitor);

}
