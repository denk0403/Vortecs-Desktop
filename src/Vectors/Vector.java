package Vectors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import Matrices.Matrix2D;

public abstract class Vector {

	private static final double ARROW_TO_ARROW_HEAD_SCALE = 1 / 8.0;

	protected final Color color;
	protected final double xComponent;
	protected final double yComponent;
	protected final double length;
	protected final double theta; // in radians

	private final double arrowHeadLength;

	// constructs a vector with the given values
	Vector(double xComponent, double yComponent, double length, double theta, Color color) {
		this.xComponent = xComponent;
		this.yComponent = yComponent;
		this.length = length;
		this.theta = this.normalizeAngle(theta);
		this.color = color;
		this.arrowHeadLength = this.length * ARROW_TO_ARROW_HEAD_SCALE;
	}

	// constructs a copy of the given vector
	Vector(Vector v) {
		this.xComponent = v.getXComponent();
		this.yComponent = v.getYComponent();
		this.length = v.getLength();
		this.theta = v.getAngle();
		this.color = generateRandomColor();
		this.arrowHeadLength = this.length * ARROW_TO_ARROW_HEAD_SCALE;
	}

	// returns the negation of this vector
	public abstract Vector negate();

	// returns the PI/2 (90 degree) rotation of this vector
	public abstract Vector orthogonalize();

	// changes the X value to the given one
	public abstract Vector changeXTo(double newX);

	// changes the Y value to the given one
	public abstract Vector changeYTo(double newY);

	// changes the Length value to the given one
	public abstract Vector changeLengthTo(double newLength);

	// changes the Theta value to the given one
	public abstract Vector changeThetaTo(double newTheta);

	// normalizes the given angle between -PI and PI
	private double normalizeAngle(double theta) {
		if (theta > Math.PI) {
			return this.normalizeAngle(theta - Math.PI);
		} else if (theta < -Math.PI) {
			return this.normalizeAngle(theta + Math.PI);
		} else {
			return theta;
		}
	}

	// draws this vector onto center of given graphics object
	public void drawSelf(Graphics g) {
		if (this.length <= 0.0001) {
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

	// draws this vector onto center of given graphics object according to the given
	// matrix
	public void drawSelf(Graphics g, Matrix2D m) {
		m.transform(this).drawSelf(g);
	}

	// returns the x-component of this vector
	public double getXComponent() {
		return this.xComponent;
	}

	// returns the y-component of this vector
	public double getYComponent() {
		return this.yComponent;
	}

	// returns the length of this vector
	public double getLength() {
		return this.length;
	}

	// returns the angle in radians of this vector
	public double getAngle() {
		return this.theta;
	}

	// returns the color of this vector
	public Color getColor() {
		return this.color;
	}

	// generates a random color
	protected static Color generateRandomColor() {
		return new Color((int) (190 * Math.random()) + 45, (int) (190 * Math.random()) + 45,
				(int) (190 * Math.random()) + 45);
	}

	// generates a random vector between the given minimum size and maximum size
	public static Vector generateRandomVector(int minSize, int maxSize) {
		return new PolarVector((maxSize - minSize) * Math.random() + minSize,
				2 * Math.PI * Math.random() - Math.PI);
	}

	// returns the dot product of this vector and a given vector
	public double dotProduct(Vector v) {
		return (this.getXComponent() * v.getXComponent()) + (this.getYComponent() * v.getYComponent());
	}

	// returns the vector sum of this vector and the given vector
	public Vector add(Vector v) {
		return new CartesianVector(this.getXComponent() + v.getXComponent(),
				this.getYComponent() + v.getYComponent());
	}

	// returns a new vector given by scaling this vector by the given scalar
	public Vector scale(double scalar) {
		return new PolarVector(this.getXComponent() * scalar, this.getYComponent() * scalar,
				this.getLength() * scalar, this.getAngle(), Vector.generateRandomColor());
	}

	// returns a string representation of this vector
	public String toString() {
		return "Vector:" + "\n\tX: " + this.getXComponent() + "\n\tY: " + this.getYComponent()
				+ "\n\tLength: " + this.getLength() + "\n\tAngle: " + this.getAngle() + " ("
				+ Math.toDegrees(this.getAngle()) + "º)";
	}

}