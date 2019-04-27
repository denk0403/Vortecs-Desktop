package Graphing;
import java.awt.geom.AffineTransform;

// represents a Camera object
public class Camera {

	private double translateX;
	private double translateY;
	private double scale;

	// creates a camera with the given x/y translations, and scale
	public Camera(double translateX, double translateY, double scale) {
		this.translateX = translateX;
		this.translateY = translateY;
		this.scale = scale;
	}

	// translates this camera by the given dx and dy
	public void translate(double dx, double dy) {
		this.translateX += dx;
		this.translateY += dy;
	}

	// scales this camera by the given factor
	public void scale(double factor) {
		this.scale *= factor;
	}
	
	// scales this camera by the given factor around the given x and y coordinate
	public void scaleAboutPoint(double factor, double x, double y) {
		double xDisp = (x - this.translateX);
		double yDisp = (y - this.translateY);
		double dx = xDisp * (factor - 1);
		double dy = yDisp * (factor - 1);
		this.translate(-dx, -dy);
		this.scale(factor);

	}
	
	// translates this camera to the given point
	public void translateTo(double x, double y) {
		this.translateX = x;
		this.translateY = y;
	}

	// returns the value of the x-translation
	public double getTranslateX() {
		return this.translateX;
	}

	// returns the value of the y-translation
	public double getTranslateY() {
		return this.translateY;
	}

	// returns the value of the scale
	public double getScale() {
		return this.scale;
	}

	// returns the AffineTransform represented by this Camera
	public AffineTransform getTransform() {
		return new AffineTransform(
				new double[] { Math.sqrt(this.scale), 0, 0, -Math.sqrt(this.scale), this.translateX, this.translateY });
	}
	
	@Override
	// returns a String representation of this Camera
	public String toString() {
		return "Camera:"
				+ "\n\tX-Translate: " + this.translateX
				+ "\n\tY-Translate: " + this.translateY
				+ "\n\tScale: " + this.scale;
				
	}

}
