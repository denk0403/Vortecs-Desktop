package Vectors;

import java.awt.Color;

/**
 * Represents a cartesian vector
 * 
 * @author Dennis Kats
 *
 */
public class CartesianVector extends Vector {

	/**
	 * Creates a cartesian vector with a random color
	 * 
	 * @param xComponent The x-component of the vector
	 * @param yComponent The y-component of the vector
	 */
	public CartesianVector(double xComponent, double yComponent) {
		super(xComponent, yComponent, Math.hypot(xComponent, yComponent),
				Math.atan2(yComponent, xComponent), Vector.generateRandomColor());
	}

	/**
	 * Creates a cartesian vector with the given color
	 * 
	 * @param xComponent The x-component of the vector
	 * @param yComponent The y-component of the vector
	 * @param color      The color of this vector
	 */
	public CartesianVector(double xComponent, double yComponent, Color color) {
		super(xComponent, yComponent, Math.hypot(xComponent, yComponent),
				Math.atan2(yComponent, xComponent), color);
	}

	/**
	 * Creates a cartesian vector with forced values
	 * <p>
	 * Only to be used by other classes in this package
	 * </p>
	 * 
	 * @param xComponent The x-component of this vector
	 * @param yComponent The y-component of this vector
	 * @param length     The length of this vector
	 * @param theta      The angle (in radians) of this vector
	 * @param color      The color of this vector
	 */
	protected CartesianVector(double xComponent, double yComponent, double length, double theta,
			Color color) {
		super(xComponent, yComponent, length, theta, color);
	}
	
	/**
	 * Constructs a copy of the given vector
	 * 
	 * @param v The vector to be copied
	 */
	protected CartesianVector(Vector v) {
		super(v);
	}

	@Override
	public CartesianVector negate() {
		return new CartesianVector(-this.xComponent, -this.yComponent, this.length, this.theta + Math.PI,
				this.color);
	}

	/**
	 * Returns the cartesian vector given by the components (-y, x)
	 */
	@Override
	public CartesianVector orthogonalize() {
		return new CartesianVector(-this.yComponent, this.xComponent, this.length,
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
