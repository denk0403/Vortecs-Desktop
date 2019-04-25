import java.awt.Color;

// represents a polar vector
public class PolarVector extends Vector {

	// creates a polar vector with a random color
	public PolarVector(double length, double theta) {
		super(length * Math.cos(theta), length * Math.sin(theta), length, theta,
				new Color((int) (190 * Math.random()) + 45, (int) (190 * Math.random()) + 45,
						(int) (190 * Math.random()) + 45));
	}

	// creates a polar vector from length and theta values
	public PolarVector(double length, double theta, Color color) {
		super(length * Math.cos(theta), length * Math.sin(theta), length, theta, color);
	}

	// convenience constructor for forcing values within vector
	public PolarVector(double xComponent, double yComponent, double length, double theta,
			Color color) {
		super(xComponent, yComponent, length, theta, color);
	}

	// returns the negation of this vector
	public PolarVector negate() {
		return new PolarVector(-this.xComponent, -this.yComponent, this.length, -this.theta,
				this.color);
	}

	// returns the PI/2 (90 degree) rotation of this vector
	public PolarVector orthogonalize() {
		return new PolarVector(-this.yComponent, this.xComponent, this.length,
				this.theta + Math.PI / 2, this.color);
	}

	// changes the X value to the given one
	public Vector changeXTo(double newX) {
		return new CartesianVector(newX, this.yComponent, this.color);
	}

	// changes the Y value to the given one
	public Vector changeYTo(double newY) {
		return new CartesianVector(this.xComponent, newY, this.color);
	}

	// changes the Length value to the given one
	public Vector changeLengthTo(double newLength) {
		return new PolarVector(newLength, this.theta, this.color);
	}

	// changes the Theta value to the given one
	public Vector changeThetaTo(double newTheta) {
		return new PolarVector(this.length, newTheta, this.color);
	}

}
