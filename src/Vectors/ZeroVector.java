package Vectors;
import java.awt.Color;
import java.awt.Graphics;

// represents a zero vector
@Deprecated
public class ZeroVector extends Vector {

	ZeroVector() {
		this(new Color((int) (190 * Math.random()) + 45, (int) (190 * Math.random()) + 45,
				(int) (190 * Math.random()) + 45));
	}

	ZeroVector(Color color) {
		super(0, 0, 0, 0, color);
	}

	private ZeroVector(double theta, Color color) {
		super(0, 0, 0, theta, color);
	}

	// returns the negation of this vector
	public Vector negate() {
		return new ZeroVector(-this.theta, this.color);
	}

	// returns the PI/2 (90 degree) rotation of this vector
	public Vector orthogonalize() {
		return new ZeroVector(this.theta + Math.PI / 2, this.color);
	}

	// changes the X value to the given one
	public Vector changeXTo(double newX) {
		return new CartesianVector(newX, 0, this.color);
	}

	// changes the Y value to the given one
	public Vector changeYTo(double newY) {
		return new CartesianVector(0, newY, this.color);
	}

	// changes the Length value to the given one
	public Vector changeLengthTo(double newLength) {
		return new PolarVector(newLength, this.theta, this.color);
	}

	// changes the Theta value to the given one
	public Vector changeThetaTo(double newTheta) {
		return new ZeroVector(newTheta, this.color);
	}
	
	// does not draw self since there is essentially no vector to draw
	protected void drawSelf(Graphics g) {
		return;
	}

}
