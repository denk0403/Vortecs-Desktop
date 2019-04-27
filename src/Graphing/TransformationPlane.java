package Graphing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

import Controls.InputBoxContainer;
import Matrices.Matrix2D;
import Vectors.CartesianVector;
import Vectors.Vector;

public class TransformationPlane implements Transformable {

	public static int NUM_OF_GRID_LINES = 10;
	private Matrix2D matrix;

	public TransformationPlane() {
		this.matrix = new Matrix2D(1, 0, 0, 1);
	}

	// displays this plane on the given graphics object
	public void display(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform camera = g2.getTransform();

		// gets drawing scale of graphics object
		float realScale = (float) Math.sqrt(Math.abs(camera.getDeterminant()));

		// helps to resize grid lines
		float artScale = realScale; // artificial scale
		float tempScale = realScale;

		while (tempScale < Display.MIN_SCALE) {
			tempScale *= Display.MAX_SCALE / Display.MIN_SCALE;
			artScale *= Math.pow((Display.MAX_SCALE / Display.MIN_SCALE), 2);
		}
		while (tempScale > Display.MAX_SCALE) {
			tempScale *= Display.MIN_SCALE / Display.MAX_SCALE;
			artScale *= Math.pow((Display.MIN_SCALE / Display.MAX_SCALE), 2);
		}

		// sets the stroke to look consistent with the scale
		g2.setStroke(new BasicStroke(1.5f / realScale));

		int xShift = 0;
		int yShift = 0;

		// draws horizontal grid lines
		for (int row = -NUM_OF_GRID_LINES + yShift; row <= NUM_OF_GRID_LINES + yShift; row += 1) {
			if (row == 0) {
				g2.setStroke(new BasicStroke(3f / realScale));
				g2.draw(new Line2D.Double(-NUM_OF_GRID_LINES * artScale, row * artScale,
						NUM_OF_GRID_LINES * artScale, row * artScale));
				g2.setStroke(new BasicStroke(1.5f / realScale));
			} else {
				g2.draw(new Line2D.Double(-NUM_OF_GRID_LINES * artScale, row * artScale,
						NUM_OF_GRID_LINES * artScale, row * artScale));
			}

		}

		// draws vertical grid lines
		for (int col = -NUM_OF_GRID_LINES + xShift; col <= NUM_OF_GRID_LINES + xShift; col += 1) {
			if (col == 0) {
				g2.setStroke(new BasicStroke(3f / realScale));
				g2.draw(new Line2D.Double(col * artScale, -NUM_OF_GRID_LINES * artScale,
						col * artScale, NUM_OF_GRID_LINES * artScale));
				g2.setStroke(new BasicStroke(1.5f / realScale));
			} else {
				g2.draw(new Line2D.Double(col * artScale, -NUM_OF_GRID_LINES * artScale,
						col * artScale, NUM_OF_GRID_LINES * artScale));
			}

		}

		// draws vector inputs
		InputBoxContainer.getInstance().drawInputs(g, this.getMatrix());
	}

	// displays this plane on the given graphics object using the camera
	public void display(Graphics g, Camera camera, Display display) {
		Graphics2D g2 = (Graphics2D) g;
		g2.transform(camera.getTransform());
		//g2.transform(this.getMatrix().getAsAffineTransform());

		// gets drawing scale of graphics object
		float realScale = (float) Math.sqrt(camera.getScale());

		// helps to resize grid lines
		float artScale = realScale; // artificial scale
		float tempScale = realScale;

		while (tempScale < Display.MIN_SCALE) {
			tempScale *= Display.MAX_SCALE / Display.MIN_SCALE;
			artScale *= Math.pow((Display.MAX_SCALE / Display.MIN_SCALE), 2);
		}
		while (tempScale > Display.MAX_SCALE) {
			tempScale *= Display.MIN_SCALE / Display.MAX_SCALE;
			artScale *= Math.pow((Display.MIN_SCALE / Display.MAX_SCALE), 2);
		}

		// sets the stroke to look consistent with the scale
		g2.setStroke(new BasicStroke(1.5f / realScale));

		// calculates the interval between grid lines
		double gridInterval = (artScale * Math.sqrt(camera.getScale()));

		// calculates the amount needed to shift grid line drawing
		int xShift = (int) ((display.centerX - camera.getTranslateX()) / gridInterval);
		int yShift = -(int) ((display.centerY - camera.getTranslateY()) / gridInterval);

		// adjusts to work with different basis vectors
		Vector v = new CartesianVector(xShift, yShift);
		try {
			v = this.matrix.getInverse().transform(v);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		xShift = (int) Math.round(v.getXComponent());
		yShift = (int) Math.round(v.getYComponent());

		// adjusts the # of grid lines needed for the given display size and basis
		// vectors
		// - The math is based on dividing out the distortion of the plane on the width
		// - and height of the display, and then figuring out which of those is greater,
		// - and using the determinant to scale further
		NUM_OF_GRID_LINES = (int) (Math.max(
				display.getWidth() / Math.min(
						Math.abs(this.matrix.getFirstColumn().getXComponent()) <= 0.001 ? 1
								: this.matrix.getFirstColumn().getXComponent(),
						Math.abs(this.matrix.getSecondColumn().getXComponent()) <= 0.001 ? 1
								: this.matrix.getSecondColumn().getXComponent()),
				display.getHeight() / Math.min(
						Math.abs(this.matrix.getFirstColumn().getYComponent()) <= 0.001 ? 1
								: this.matrix.getFirstColumn().getYComponent(),
						Math.abs(this.matrix.getSecondColumn().getYComponent()) <= 0.001 ? 1
								: this.matrix.getSecondColumn().getYComponent()))
				/ gridInterval / (Math.abs(this.matrix.getDeterminant()) <= 0.001 ? 1 : this.matrix.getDeterminant()));
		//System.out.println(NUM_OF_GRID_LINES);

		Vector v1;
		Vector v2;
		// draws horizontal grid lines with matrix transformation applied
		for (int row = -NUM_OF_GRID_LINES + yShift; row <= NUM_OF_GRID_LINES + yShift; row += 1) {
			v1 = new CartesianVector((-NUM_OF_GRID_LINES + xShift) * artScale, row * artScale);
			v1 = this.matrix.transform(v1);
			v2 = new CartesianVector((NUM_OF_GRID_LINES + xShift) * artScale, row * artScale);
			v2 = this.matrix.transform(v2);
			if (row == 0) {
				g2.setStroke(new BasicStroke(3f / realScale));
				g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(),
						v2.getXComponent(), v2.getYComponent()));
				g2.setStroke(new BasicStroke(1.5f / realScale));
			} else {
				g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(),
						v2.getXComponent(), v2.getYComponent()));
			}

		}

		// draws vertical grid lines with matrix transformation applied
		for (int col = -NUM_OF_GRID_LINES + xShift; col <= NUM_OF_GRID_LINES + xShift; col += 1) {
			v1 = new CartesianVector(col * artScale, (-NUM_OF_GRID_LINES + yShift) * artScale);
			v1 = this.matrix.transform(v1);
			v2 = new CartesianVector(col * artScale, (NUM_OF_GRID_LINES + yShift) * artScale);
			v2 = this.matrix.transform(v2);
			if (col == 0) {
				g2.setStroke(new BasicStroke(3f / realScale));
				g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(),
						v2.getXComponent(), v2.getYComponent()));
				g2.setStroke(new BasicStroke(1.5f / realScale));
			} else {
				g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(),
						v2.getXComponent(), v2.getYComponent()));
			}

		}

		// draws vector inputs
		InputBoxContainer.getInstance().drawInputs(g2, this.getMatrix());

	}

	// transforms this transformable
	public void transform(Matrix2D m) {
		System.out.print("transforming here");
		System.out.println(m);
		this.setMatrix(m.applyTransformOn(this.getMatrix()));
	}

	// returns the matrix of this transformable
	public Matrix2D getMatrix() {
		return this.matrix;
	}
	
	// sets this transformable's matrix to the given one
	public void setMatrix(Matrix2D m) {
		this.matrix = m;
	}

}
