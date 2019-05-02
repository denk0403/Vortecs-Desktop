package Graphing;

import java.awt.Graphics;

import Matrices.Matrix2D;

public class AnimateTransform implements Displayable{

	TransformationPlane plane;
	Matrix2D end;
	Matrix2D dm;
	
	public AnimateTransform(TransformationPlane plane, Matrix2D apply) {
		this.plane = plane;
		this.end = this.plane.getMatrix().transform(apply);
		Matrix2D dist = end.add(this.plane.getMatrix().scale(-1));
		this.dm = dist.scale(0.02);
	}

	@Override
	public void display(Graphics g) {
		return;
	}

	@Override
	public void display(Graphics g, Camera camera, Display display) {
		if (!this.plane.getMatrix().equals(this.end)) {
			this.plane.setMatrix(plane.getMatrix().add(this.dm));
		} else {
			display.item = new TransformationPlane(this.end);
			display.repaint();
			return;
		}
		
		this.plane.display(g, camera, display);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display.repaint();
	}
	
}
