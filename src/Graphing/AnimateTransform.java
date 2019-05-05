package Graphing;

import java.awt.Graphics;

import javax.swing.JPopupMenu;

import Controls.AnimationRightClick;
import Matrices.Matrix2D;

public class AnimateTransform implements Displayable{
	
	public static int MIN_ITERATIONS = 25; // fastest
	public static int DEFAULT_ITERATIONS = 175;
	public static int ITERATIONS = DEFAULT_ITERATIONS;
	public static int MAX_ITERATIONS = 350; //slowest

	TransformationPlane plane;
	Matrix2D end;
	Matrix2D dm;
	private boolean isPaused;
	
	public AnimateTransform(TransformationPlane plane, Matrix2D apply) {
		this.plane = plane;
		this.end = this.plane.getMatrix().transform(apply);
		Matrix2D dist = end.add(this.plane.getMatrix().scale(-1));
		this.dm = dist.scale(1.0/ITERATIONS);
		this.isPaused = false;
	}

	@Override
	public void display(Graphics g) {
		return;
	}

	@Override
	public void display(Graphics g, Camera camera, Display display) {
		if (!this.isPaused) {
			if (!this.plane.getMatrix().equals(this.end)) {
				//System.out.println(plane.getMatrix());
				//System.out.println(end);
				//System.out.println(plane.getMatrix().add(end.scale(-1)));
				this.plane.setMatrix(plane.getMatrix().add(this.dm));
			} else {
				//System.out.println(plane.getMatrix());
				//System.out.println(end);
				display.setItem(new TransformationPlane(this.end));
				display.repaint();
				return;
			}
		}
		
		this.plane.display(g, camera, display);
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display.repaint();
	}
	
	@Override
	public JPopupMenu getRightClickMenu(Display display) {
		return new AnimationRightClick(display, this);
	}
	
	public Matrix2D getCurrentMatrix() {
		return this.plane.getMatrix();
	}
	
	public void pause() {
		this.isPaused = true;
	}
	
	public void unpause() {
		this.isPaused = false;
	}
	
	public boolean isPaused() {
		return this.isPaused;
	}
	
}
