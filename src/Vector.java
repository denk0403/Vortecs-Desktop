import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public abstract class Vector {

	protected final Color color;
	protected final double xComponent;
	protected final double yComponent;
	protected final double length;
	protected final double theta; // in radians

	Vector(double xComponent, double yComponent, double length, double theta, Color color) {
		this.xComponent = xComponent;
		this.yComponent = yComponent;
		this.length = length;
		this.theta = this.normalizeAngle(theta);
		this.color = color;
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

	// draws self onto center of given graphics object
	protected void drawSelf(Graphics g) {
		if (this.length <= 0.0001) {
			return;
		}
		Graphics2D g2 = (Graphics2D) g;
		float scale = (float) Math.sqrt(Math.abs(g2.getTransform().getDeterminant()));
		//System.out.println("Scale: " + scale);

		// sets paintbrush to this vector's corresponding characteristics
		g2.setColor(this.color);
		g2.setStroke(new BasicStroke(2.5f/scale));

		// draws arrow stem
		g2.draw(new Line2D.Double(0, 0, (this.xComponent * scale), (this.yComponent * scale)));

		// draws arrow head PI/8 radians from terminal line
		g2.draw(new Line2D.Double((this.xComponent * scale), (this.yComponent * scale),
				((this.xComponent * scale)
						+ scale * this.length / 8 * Math.cos(this.theta + Math.PI - (Math.PI / 8))),
				((this.yComponent * scale)
						+ scale * this.length / 8 * Math.sin(this.theta + Math.PI - (Math.PI / 8)))));
		g2.draw(new Line2D.Double((this.xComponent * scale), (this.yComponent * scale),
				((this.xComponent * scale)
						+ scale * this.length / 8 * Math.cos(this.theta + Math.PI + (Math.PI / 8))),
				((this.yComponent * scale)
						+ scale * this.length / 8 * Math.sin(this.theta + Math.PI + (Math.PI / 8)))));
	}

}