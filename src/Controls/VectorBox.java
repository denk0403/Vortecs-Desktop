package Controls;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Matrices.Matrix2D;
import Vectors.CartesianVector;
import Vectors.Vector;

// box for user inputs for vectors
// responsible for manipulating vector
public class VectorBox extends JPanel implements InputBox {

	Vector vector; // vector represented by vector box
	CloseBoxButton closeButton;

	// creates a vector box with the zero vector
	public VectorBox() {
		this.vector = new CartesianVector(0, 0);
		this.closeButton = new CloseBoxButton(this);
		this.initComponents();
		this.add(new JTextField("Test"));
	}
	
	// creates a vector box with the given vector
	public VectorBox(Vector v) {
		this.vector = v;
		this.closeButton = new CloseBoxButton(this);
		this.initComponents();
		this.add(new JTextField("Test"));
	}

	// initiates some components
	private void initComponents() {
		this.closeButton.addMouseListener(new MouseListener() {

			// deletes this vector
			public void mousePressed(MouseEvent e) {
				VectorBox.this.closeSelf();
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	// closes this vector
	private void closeSelf() {
		this.closeButton.close();
	}

	// draws the box's item onto the given graphics object
	public void drawItem(Graphics g, Matrix2D m) {
		//System.out.println("Drawing Vector!");
		vector.drawSelf(g, m);
	}

	// updates the x-component of the corresponding vector
	void changeXTo(double newX) {
		this.vector = this.vector.changeXTo(newX);
	}

	// updates the y-component of the corresponding vector
	void changeYTo(double newY) {
		this.vector = this.vector.changeYTo(newY);
	}
	
	// returns the input
	public Vector getInput() {
		return this.vector;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}

}
