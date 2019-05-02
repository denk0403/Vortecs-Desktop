package Vectors;
import java.awt.Color;

/**
 * Represents a polar vector
 * 
 * @author Dennis Kats
 *
 */
public class PolarVector extends Vector {

	/**
	 *  Creates a polar vector with a random color
	 * @param length The length of the vector
	 * @param theta The angle in radians (from the positive x-axis) of the vector
	 */
	public PolarVector(double length, double theta) {
		super(length * Math.cos(theta), length * Math.sin(theta), length, theta,
				Vector.generateRandomColor());
	}

	/**
	 *  Creates a polar vector with the given color
	 * @param length The length of the vector
	 * @param theta The angle in radians (from the positive x-axis) of the vector
	 * @param color The color of the vector
	 */
	public PolarVector(double length, double theta, Color color) {
		super(length * Math.cos(theta), length * Math.sin(theta), length, theta, color);
	}

	/**
	 *  Creates a polar vector with forced values
	 *  <p>
	 *  Only to be used by other classes in this package
	 *  </p>
	 * @param xComponent The x-component of this vector
	 * @param yComponent The y-component of this vector
	 * @param length The length of this vector
	 * @param theta The angle (in radians) of this vector
	 * @param color The color of this vector
	 */
	protected PolarVector(double xComponent, double yComponent, double length, double theta,
			Color color) {
		super(xComponent, yComponent, length, theta, color);
	}
	
	/**
	 * Constructs a copy of the given vector
	 * 
	 * @param v The vector to be copied
	 */
	protected PolarVector(Vector v) {
		super(v);
	}

	@Override
	public PolarVector negate() {
		return new PolarVector(-this.xComponent, -this.yComponent, this.length, this.theta + Math.PI,
				this.color);
	}

	@Override
	public PolarVector orthogonalize() {
		return new PolarVector(-this.yComponent, this.xComponent, this.length,
				this.theta + Math.PI / 2, this.color);
	}

	@Override
	public Vector changeXTo(double newX) {
		return new CartesianVector(newX, this.yComponent, this.color);
	}

	@Override
	public Vector changeYTo(double newY) {
		return new CartesianVector(this.xComponent, newY, this.color);
	}

	@Override
	public Vector changeLengthTo(double newLength) {
		return new PolarVector(newLength, this.theta, this.color);
	}

	@Override
	public Vector changeThetaTo(double newTheta) {
		return new PolarVector(this.length, newTheta, this.color);
	}

}
