package Vectors;
import java.awt.Color;

// represents a cartesian vector
public class CartesianVector extends Vector {

	//creates a cartesian vector with a random color
	public CartesianVector(double xComponent, double yComponent) {
		super(xComponent, yComponent, Math.hypot(xComponent, yComponent),
				Math.atan2(yComponent, xComponent), Vector.generateRandomColor());
	}

	//convenience constructors
	public CartesianVector(double xComponent, double yComponent, Color color) {
		super(xComponent, yComponent, Math.hypot(xComponent, yComponent),
				Math.atan2(yComponent, xComponent), color);
	}

	protected CartesianVector(double xComponent, double yComponent, double length, double theta,
			Color color) {
		super(xComponent, yComponent, length, theta, color);
	}

	// returns the negation of this vector
	public CartesianVector negate() {
		return new CartesianVector(-this.xComponent, -this.yComponent, this.length, -this.theta,
				this.color);
	}

	// returns the PI/2 (90 degree) rotation of this vector
	// Also (-y, x)
	public CartesianVector orthogonalize() {
		return new CartesianVector(-this.yComponent, this.xComponent, this.length,
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
