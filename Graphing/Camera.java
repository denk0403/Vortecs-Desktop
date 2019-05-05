package Graphing;

import java.awt.geom.AffineTransform;

/**
 *  Represents a Camera object
 * @author Dennis Kats
 *
 */
public class Camera {

	private double translateX;
	private double translateY;
	private double scale;

	/**
	 *  Creates a camera with the given x/y translations, and scale
	 * @param translateX The translation in the x-direction
	 * @param translateY The translation in the y-direction
	 * @param scale The scale of the at which the camera is looking at
	 */
	public Camera(double translateX, double translateY, double scale) {
		this.translateX = translateX;
		this.translateY = translateY;
		this.scale = scale * scale;
	}
	
	/**
	 *  Creates a default camera, positioned at 0, and with a scale of 1
	 */
	public Camera() {
		this.translateX = 0;
		this.translateY = 0;
		this.scale = 1;
	}

	/**
	 *  Translates this camera by the given dx and dy
	 * @param dx The change in the x-direction
	 * @param dy The change in the y-direction
	 */
	public void translate(double dx, double dy) {
		this.translateX += dx;
		this.translateY += dy;
	}

	/**
	 * Scales this camera by the given factor
	 * @param The factor to scale the camera by
	 */
	public void scale(double factor) {
		this.scaleAboutPoint(factor, this.translateX, this.translateY);
	}

	/**
	 *  Scales this camera by the given factor around the given x and y coordinate
	 * @param factor The factor to scale the camera by
	 * @param x The x-coordinate to scale relative to
	 * @param y The y-coordinate to scale relative to
	 */
	public void scaleAboutPoint(double factor, double x, double y) {
		factor *= factor;
		double xDisp = (x - this.translateX);
		double yDisp = (y - this.translateY);
		double dx = xDisp * (factor - 1);
		double dy = yDisp * (factor - 1);
		this.translate(-dx, -dy);
		this.scale *= factor;

	}

	/**
	 *  Translates this camera to the given point
	 * @param x The x-coordinate to move the camera to
	 * @param y The y-coordinate to move the camera to
	 */
	public void translateTo(double x, double y) {
		this.translateX = x;
		this.translateY = y;
	}

	/**
	 * @return Returns the value of the x-translation
	 */
	public double getTranslateX() {
		return this.translateX;
	}

	/**
	 * @return Returns the value of the y-translation
	 */
	public double getTranslateY() {
		return this.translateY;
	}

	/**
	 * @return Returns the value of the scale
	 */
	public double getScale() {
		return Math.sqrt(this.scale);
	}

	/**
	 * @return Returns the AffineTransform represented by this Camera
	 */
	public AffineTransform getTransform() {
		return new AffineTransform(new double[] { this.getScale(), 0, 0,
				-this.getScale(), this.getTranslateX(), this.getTranslateY() });
	}


	@Override
	public String toString() {
		return "Camera:" + "\n\tX-Translate: " + this.getTranslateX() + "\n\tY-Translate: "
				+ this.getTranslateY() + "\n\tScale: " + this.getScale();

	}

}
