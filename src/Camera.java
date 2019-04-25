import java.awt.geom.AffineTransform;

public class Camera {

	private double translateX;
	private double translateY;
	private double scale;

	public Camera(double translateX, double translateY, double scale) {
		this.translateX = translateX;
		this.translateY = translateY;
		this.scale = scale;
	}

	public void translate(double dx, double dy) {
		this.translateX += dx;
		this.translateY += dy;
	}

	public void scale(double factor) {
		this.scale *= factor;
	}

	public double getTranslateX() {
		return this.translateX;
	}

	public double getTranslateY() {
		return this.translateY;
	}

	public double getScale() {
		return this.scale;
	}

	public AffineTransform getTransform() {
		return new AffineTransform(
				new double[] { this.scale, 0, 0, -this.scale, this.translateX, this.translateY });
	}
	
	@Override
	public String toString() {
		return this.getTransform().toString();
	}

}
