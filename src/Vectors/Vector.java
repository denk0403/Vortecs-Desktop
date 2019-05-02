package Vectors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.math.BigDecimal;

import Matrices.Matrix2D;

/**
 * Represents a Vector
 * 
 * @author denniskats
 *
 */
public abstract class Vector {

	public static final Vector ZERO_VECTOR = new CartesianVector(0, 0, 0, 0, Color.BLACK);
	public static final Vector E1 = new CartesianVector(1, 0, 1, 0, Color.BLACK);
	public static final Vector E2 = new CartesianVector(0, 1, 1, Math.PI / 2, Color.BLACK);

	private static final double ARROW_TO_ARROW_HEAD_SCALE = 1 / 8.0;

	protected final Color color;
	protected final double xComponent;
	protected final double yComponent;
	protected final double length;
	protected final double theta; // in radians

	private final double arrowHeadLength;

	/**
	 * Constructs a vector with the given values
	 * 
	 * @param xComponent The x-component of this vector
	 * @param yComponent The y-component of this vector
	 * @param length     The length of this vector
	 * @param theta      The angle (in radians) of this vector
	 * @param color      The color of this vector
	 */
	public Vector(double xComponent, double yComponent, double length, double theta, Color color) {
		this.xComponent = this.fixIfZero(xComponent);
		this.yComponent = this.fixIfZero(yComponent);
		this.length = this.fixIfZero(length);
		this.theta = this.normalizeAngle(theta);
		this.color = color;
		this.arrowHeadLength = this.length * ARROW_TO_ARROW_HEAD_SCALE;
	}

	/**
	 * Constructs a copy of the given vector
	 * 
	 * @param v The vector to be copied
	 */
	protected Vector(Vector v) {
		this.xComponent = v.getXComponent();
		this.yComponent = v.getYComponent();
		this.length = v.getLength();
		this.theta = v.getAngle();
		this.color = generateRandomColor();
		this.arrowHeadLength = this.length * ARROW_TO_ARROW_HEAD_SCALE;
	}

	/**
	 * @return Returns the negation of this vector
	 */
	public abstract Vector negate();

	/**
	 * @return Returns the PI/2 (90 degree) rotation of this vector
	 */
	public abstract Vector orthogonalize();

	/**
	 * 
	 * @param newX The new x-component
	 * @return Changes the X value to the given one
	 */
	public abstract Vector changeXTo(double newX);

	/**
	 * 
	 * @param newY The new y-component
	 * @return Changes the Y value to the given one
	 */
	public abstract Vector changeYTo(double newY);

	/**
	 * 
	 * @param newLength The new length
	 * @return Changes the length value to the given one
	 */
	public abstract Vector changeLengthTo(double newLength);

	/**
	 * 
	 * @param newTheta The new angle
	 * @return Changes the theta value to the given one
	 */
	public abstract Vector changeThetaTo(double newTheta);

	/**
	 * Normalizes the given angle between -PI and PI
	 * 
	 * @param theta The angle to be normalized
	 * @return The angle between -PI and PI
	 */
	private double normalizeAngle(double theta) {
		if (theta >= Math.PI) {
			return this.normalizeAngle(theta - 2 * Math.PI);
		} else if (theta < -Math.PI) {
			return this.normalizeAngle(theta + 2 * Math.PI);
		} else {
			return theta;
		}
	}

	/**
	 * Draws this vector onto center of given graphics object
	 * 
	 * @param g The graphics object
	 */
	public void drawSelf(Graphics g) {

		// doesn't draw anything for the zero vector
		if (this.isZeroVector()) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g;
		float scale = (float) Math.sqrt(Math.abs(g2.getTransform().getDeterminant()));

		// sets paintbrush to this vector's corresponding characteristics
		g2.setColor(this.color);
		g2.setStroke(new BasicStroke(2.5f / scale));

		// draws arrow stem
		g2.draw(new Line2D.Double(0, 0, (this.xComponent * scale), (this.yComponent * scale)));

		// draws arrow head PI/8 radians from terminal line
		g2.draw(new Line2D.Double((this.xComponent * scale), (this.yComponent * scale),
				((this.xComponent * scale)
						+ scale * arrowHeadLength * Math.cos(this.theta + Math.PI - (Math.PI / 8))),
				((this.yComponent * scale) + scale * arrowHeadLength
						* Math.sin(this.theta + Math.PI - (Math.PI / 8)))));
		g2.draw(new Line2D.Double((this.xComponent * scale), (this.yComponent * scale),
				((this.xComponent * scale)
						+ scale * arrowHeadLength * Math.cos(this.theta + Math.PI + (Math.PI / 8))),
				((this.yComponent * scale) + scale * arrowHeadLength
						* Math.sin(this.theta + Math.PI + (Math.PI / 8)))));
	}

	/**
	 * Draws this vector with the given matrix applied onto center of given graphics
	 * object
	 * 
	 * @param g The graphics object
	 * @param m The matrix to apply on the vector
	 */
	public void drawSelf(Graphics g, Matrix2D m) {
		m.transform(this).drawSelf(g);
	}

	/**
	 * @return Returns the x-component of this vector
	 */
	public double getXComponent() {
		return this.xComponent;
	}

	/**
	 * @return Returns the y-component of this vector
	 */
	public double getYComponent() {
		return this.yComponent;
	}

	/**
	 * @return Returns the length of this vector
	 */
	public double getLength() {
		return this.length;
	}

	/**
	 * @return Returns the angle (in radians) of this vector
	 */
	public double getAngle() {
		return this.theta;
	}

	/**
	 * @return Returns the color of this vector
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @return Returns a random "nice" color
	 */
	protected static Color generateRandomColor() {
		return new Color((int) (190 * Math.random()) + 45, (int) (190 * Math.random()) + 45,
				(int) (190 * Math.random()) + 45);
	}

	/**
	 * Generates a random vector between the given minimum size and maximum size
	 * 
	 * @param minSize The minimum size of the vector to produce
	 * @param maxSize The maximum size of the vector to produce
	 * @return A random vector
	 */
	public static Vector generateRandomVector(int minSize, int maxSize) {
		return new PolarVector((maxSize - minSize) * Math.random() + minSize,
				2 * Math.PI * Math.random());
	}

	/**
	 * Returns the dot product of this vector and a given vector
	 * 
	 * @param v The vector to find the dot product with
	 * @return The dot product
	 */
	public double dotProduct(Vector v) {
		if (this.isZeroVector() || v.isZeroVector()) {
			return +0.0;
		}
		return (this.getXComponent() * v.getXComponent())
				+ (this.getYComponent() * v.getYComponent());
	}

	/**
	 * Returns the vector sum of this vector and the given vector
	 * 
	 * @param v The vector to add this vector to
	 * @return The vector sum
	 */
	public Vector add(Vector v) {
		if (this.isZeroVector()) {
			return new CartesianVector(v);
		} else if (v.isZeroVector()) {
			return new CartesianVector(this);
		}
		return new CartesianVector(this.getXComponent() + v.getXComponent(),
				this.getYComponent() + v.getYComponent());
	}

	/**
	 * Returns a new vector given by scaling this vector by the given scalar
	 * 
	 * @param scalar The scaling factor
	 * @return The vector scaled by the given amount
	 */
	public Vector scale(double scalar) {
		if (this.isZeroVector() || scalar == 0) {
			return new PolarVector(0, 0, 0, 0, Vector.generateRandomColor());
		}
		if (scalar < 0) {
			return this.negate().scale(-scalar);
		}
		return new PolarVector(this.getXComponent() * scalar, this.getYComponent() * scalar,
				this.getLength() * scalar, this.getAngle(), Vector.generateRandomColor());
	}

	/**
	 * Returns the vector in the same direction but with a length of 1
	 * 
	 * @return the unit vector
	 * @throws Exception If the length is zero
	 */
	public Vector getUnitVector() throws Exception {
		if (this.isZeroVector()) {
			throw new Exception("The Zero Vector has no unit vector");
		}
		return this.scale(1 / this.getLength());
	}

	/**
	 * @return The vector given by switching the x and y components
	 */
	public Vector flipXY() {
		return new CartesianVector(this.getYComponent(), this.getXComponent());
	}

	/**
	 * @return The matrix of the orthogonal projection onto the line spanned by this
	 *         vector
	 * @throws Exception If vector is a zero vector
	 */
	public Matrix2D getProjectionMatrix() throws Exception {
		Vector v;
		try {
			v = this.getUnitVector();
		} catch (Exception e) {
			// e.printStackTrace();
			throw new Exception("Can't project onto a zero vector");
		}
		return new Matrix2D(v, v.flipXY()).transform(new Matrix2D(v, Vector.ZERO_VECTOR));
	}

	/**
	 * @return The matrix of the reflection over the line spanned by this vector
	 * @throws Exception If vector is a zero vector
	 */
	public Matrix2D getReflectionMatrix() throws Exception {
		Vector v;
		try {
			v = this.getUnitVector();
		} catch (Exception e) {
			// e.printStackTrace();
			throw new Exception("Can't reflect over a zero vector");
		}
		double u1 = v.getXComponent();
		double u2 = v.getYComponent();
		return new Matrix2D(u1 * u1 - u2 * u2, 2 * u1 * u2, 2 * u1 * u2, u2 * u2 - u1 * u1);
	}
	
	public boolean linearlyIndependent(Vector v) {
		if (this.getXComponent() == 0 ^ v.getXComponent() == 0) {
			return true;
		}
		double mult = this.getXComponent() / v.getXComponent();
		if (v.scale(mult).equals(this)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Vector:" + "\n\tX: " + this.getXComponent() + "\n\tY: " + this.getYComponent()
				+ "\n\tLength: " + this.getLength() + "\n\tAngle: " + this.getAngle() + " ("
				+ Math.toDegrees(this.getAngle()) + "º)";
	}

	/**
	 * Returns whether the given object is an equivalent vector
	 * <p>
	 * Note however that this method does not take into account the color of this
	 * vector
	 * </p>
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector)) {
			return false;
		}
		Vector v = (Vector) obj;
		return this.add(v.negate()).isZeroVector() && this.hashCode() == v.hashCode();

	}

	/**
	 * 
	 * @return <i>true</i> if this vector is approximately a zero vector, and
	 *         <i>false</i> otherwise
	 */
	public boolean isZeroVector() {
		return this.length < 0.000000000001;
	}

	// may need work
	/**
	 * @param obj The other object being checked for equality
	 * @return whether the given object an equivalent vector, including by color
	 */
	public boolean strictEquals(Object obj) {
		return this.equals(obj) && ((Vector) obj).getColor() == this.getColor();
	}

	@Override
	public int hashCode() {
		long sum = 0;
		sum += Math.round(this.getXComponent() * 100000000000l);
		sum += Math.round(this.getYComponent() * 100000000000l);
		sum += Math.round(this.getLength() * 100000000000l);
		if (!this.isZeroVector())
			sum += Math.round(this.getAngle() * 100000000000l);
		return Long.hashCode(sum);
	}

	/**
	 * Fixes the given double to be positive if given -0.0
	 * 
	 * @param d A double to fix
	 * @return +0.0 if the given argument is equal to 0
	 */
	private double fixIfZero(double d) {
		if (d == -0.0) {
			return +0.0;
		}
		return d;
	}

}