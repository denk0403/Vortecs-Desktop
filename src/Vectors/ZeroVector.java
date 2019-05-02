package Vectors;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a zero vector
 * 
 * @author Dennis Kats
 *
 */
@Deprecated
public class ZeroVector extends Vector {

	/**
	 * Creates a zero vector with all attributes equal to zero and a random color
	 */
	public ZeroVector() {
		super(0, 0, 0, 0);
	}

	/**
	 * Creates a zero vector with all attributes equal to zero and the given color
	 * <p>
	 * Note however that the color is not visible as a zero vector since the zero
	 * vector cannot display itself, but is only provided for when this vector
	 * becomes a different kind of vector
	 * </p>
	 * 
	 * @param color The color of the vector
	 */
	public ZeroVector(Color color) {
		super(0, 0, 0, 0, color);
	}

	/**
	 * Creates a zero vector with an x-component, y-component, and length of zero,
	 * and the given angle and color
	 * <p>
	 * Note however that the color is not visible as a zero vector since the zero
	 * vector cannot display itself, but is only provided for when this vector
	 * becomes a different kind of vector. Moreover, the angle of this vector can't
	 * be displayed either, but is useful in coverting this vector into another kind
	 * of vector.
	 * </p>
	 * 
	 * @param theta The angle (in radians) of the vector
	 * @param color The color of the vector
	 */
	private ZeroVector(double theta, Color color) {
		super(0, 0, 0, theta, color);
	}

	@Override
	public Vector negate() {
		return new ZeroVector(-this.theta, this.color);
	}

	@Override
	public Vector orthogonalize() {
		return new ZeroVector(this.theta + Math.PI / 2, this.color);
	}

	@Override
	public Vector changeXTo(double newX) {
		return new CartesianVector(newX, 0, this.color);
	}

	@Override
	public Vector changeYTo(double newY) {
		return new CartesianVector(0, newY, this.color);
	}

	@Override
	public Vector changeLengthTo(double newLength) {
		return new PolarVector(newLength, this.theta, this.color);
	}

	@Override
	public Vector changeThetaTo(double newTheta) {
		return new ZeroVector(newTheta, this.color);
	}

	@Override
	protected void drawSelf(Graphics g) {
		return;
	}

}
