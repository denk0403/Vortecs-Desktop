package Graphing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

import javax.swing.JPopupMenu;

import Controls.InputBoxContainer;
import Controls.TransformationRightClick;
import Matrices.Matrix2D;
import Vectors.CartesianVector;
import Vectors.Vector;

/**
 * Represents a Transformable Coordinate Plane.
 * 
 * @author Dennis Kats
 */
public class TransformationPlane implements Transformable {

	public static final int MAX_NUM_OF_GRID_LINES = 1000;
	public static int NUM_OF_GRID_LINES = 10;
	private Matrix2D matrix;

	/**
	 * Creates a Transformation plane with the default basis of
	 * 
	 * <pre>
	 * [ 1 0 ]
	 * [ 0 1 ]
	 * </pre>
	 */
	public TransformationPlane() {
		this.matrix = new Matrix2D(1, 0, 0, 1);
	}
	
	/** 
	 * Creates a Transformation plane with the given basis
	 * @param m The basis of the plane
	 */
	public TransformationPlane(Matrix2D m) {
		this.matrix = m;
	}

	/**
	 * Displays this plane on the given graphics object.
	 */
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

	/**
	 * Displays this plane on the given graphics object using the camera.
	 */
	public void display(Graphics g, Camera camera, Display display) {
		Graphics2D g2 = (Graphics2D) g;
		g2.transform(camera.getTransform());

		// gets drawing scale of graphics object
		float realScale = (float) camera.getScale();

		// helps to resize grid lines
		float artScale = realScale; // artificial scale
		float tempScale = realScale;

		int scaleCount = 1;

		while (tempScale < Display.MIN_SCALE) {
			scaleCount += 1;
			tempScale *= Display.MAX_SCALE / Display.MIN_SCALE;
			artScale *= Math.pow((Display.MAX_SCALE / Display.MIN_SCALE), 2);
		}
		while (tempScale > Display.MAX_SCALE) {
			scaleCount -= 1;
			tempScale *= Display.MIN_SCALE / Display.MAX_SCALE;
			artScale *= Math.pow((Display.MIN_SCALE / Display.MAX_SCALE), 2);
		}

		// calculates the interval between grid lines
		double gridInterval = (artScale * camera.getScale());

		// calculates the amount needed to shift grid line drawing
		int xShift = (int) ((display.centerX - camera.getTranslateX()) / gridInterval);
		int yShift = -(int) ((display.centerY - camera.getTranslateY()) / gridInterval);

		// adjusts to work with different basis vectors
		Vector v = new CartesianVector(xShift, yShift);
		try {

			v = this.matrix.getInverse().transform(v);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		xShift = (int) Math.round(v.getXComponent());
		yShift = (int) Math.round(v.getYComponent());
		
		NUM_OF_GRID_LINES = 150; // needs work


		// sets the stroke to look consistent with the scale
		g2.setStroke(new BasicStroke(1.5f / realScale));

		this.drawMinorLines(g2, xShift, yShift, artScale);

		this.drawMajorLines(g2, xShift, yShift, artScale);

		g2.setStroke(new BasicStroke(2.25f / realScale));
		this.drawAxesLines(g2, xShift, yShift, artScale);
		g2.setStroke(new BasicStroke(1.5f / realScale));

		this.drawNumberLabels(g2, camera, xShift, yShift, realScale, artScale, scaleCount);

		// draws vector inputs
		InputBoxContainer.getInstance().drawInputs(g2, this.getMatrix());

	}

	// draws the minor lines of the grid onto the given graphics object with the
	// transformation applied
	private void drawMinorLines(Graphics2D g2, int xShift, int yShift, float scale) {
		Vector v1;
		Vector v2;

		g2.setColor(Color.LIGHT_GRAY);

		// draws minor horizontal grid lines with matrix transformation applied
		for (int row = -NUM_OF_GRID_LINES + yShift; row <= NUM_OF_GRID_LINES + yShift; row += 1) {
			v1 = new CartesianVector((-NUM_OF_GRID_LINES + xShift) * scale, row * scale);
			v1 = this.matrix.transform(v1);
			v2 = new CartesianVector((NUM_OF_GRID_LINES + xShift) * scale, row * scale);
			v2 = this.matrix.transform(v2);
			if (row % 5 != 0) {
				g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(),
						v2.getXComponent(), v2.getYComponent()));
			}
		}

		// draws minor vertical grid lines with matrix transformation applied
		for (int col = -NUM_OF_GRID_LINES + xShift; col <= NUM_OF_GRID_LINES + xShift; col += 1) {
			v1 = new CartesianVector(col * scale, (-NUM_OF_GRID_LINES + yShift) * scale);
			v1 = this.matrix.transform(v1);
			v2 = new CartesianVector(col * scale, (NUM_OF_GRID_LINES + yShift) * scale);
			v2 = this.matrix.transform(v2);
			if (col % 5 != 0) {
				g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(),
						v2.getXComponent(), v2.getYComponent()));
			}
		}
	}

	// draws the major lines of the grid onto the given graphics object with the
	// transformation applied
	private void drawMajorLines(Graphics2D g2, int xShift, int yShift, float scale) {
		Vector v1;
		Vector v2;

		g2.setColor(Color.GRAY);

		// draws major horizontal grid lines with matrix transformation applied
		for (int row = -NUM_OF_GRID_LINES + yShift; row <= NUM_OF_GRID_LINES + yShift; row += 1) {
			if (row != 0 && row % 5 == 0) {
				v1 = new CartesianVector((-NUM_OF_GRID_LINES + xShift) * scale, row * scale);
				v1 = this.matrix.transform(v1);
				v2 = new CartesianVector((NUM_OF_GRID_LINES + xShift) * scale, row * scale);
				v2 = this.matrix.transform(v2);
				g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(),
						v2.getXComponent(), v2.getYComponent()));
			}
		}

		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 4));

		// draws major vertical grid lines with matrix transformation applied
		for (int col = -NUM_OF_GRID_LINES + xShift; col <= NUM_OF_GRID_LINES + xShift; col += 1) {
			if (col != 0 && col % 5 == 0) {
				v1 = new CartesianVector(col * scale, (-NUM_OF_GRID_LINES + yShift) * scale);
				v1 = this.matrix.transform(v1);
				v2 = new CartesianVector(col * scale, (NUM_OF_GRID_LINES + yShift) * scale);
				v2 = this.matrix.transform(v2);
				g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(),
						v2.getXComponent(), v2.getYComponent()));
			}
		}
	}

	// draws the axes lines of the grid onto the given graphics object with the
	// transformation applied
	private void drawAxesLines(Graphics2D g2, int xShift, int yShift, float scale) {
		Vector v1;
		Vector v2;

		g2.setColor(Color.BLACK);

		// draws x-axis with transformation applied
		v1 = new CartesianVector((-NUM_OF_GRID_LINES + xShift) * scale, 0 * scale);
		v1 = this.matrix.transform(v1);
		v2 = new CartesianVector((NUM_OF_GRID_LINES + xShift) * scale, 0 * scale);
		v2 = this.matrix.transform(v2);
		g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(), v2.getXComponent(),
				v2.getYComponent()));

		// draws y-axis with transformation applied
		v1 = new CartesianVector(0 * scale, (-NUM_OF_GRID_LINES + yShift) * scale);
		v1 = this.matrix.transform(v1);
		v2 = new CartesianVector(0 * scale, (NUM_OF_GRID_LINES + yShift) * scale);
		v2 = this.matrix.transform(v2);
		g2.draw(new Line2D.Double(v1.getXComponent(), v1.getYComponent(), v2.getXComponent(),
				v2.getYComponent()));
	}

	// TBH, The math here is was mostly just guessed and checked
	private void drawNumberLabels(Graphics2D g2, Camera camera, int xShift, int yShift, float realScale,
			float artScale, double scaleCount) {
		
		Vector v1;

		g2.setColor(Color.BLACK);
		g2.scale(1, -1);
		
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		float fontNum = 3f;
		
		if (!this.matrix.getSecondColumn().isZeroVector()) {
			// draws grid line labels on major horizontal lines
			for (int row = -NUM_OF_GRID_LINES + yShift; row <= NUM_OF_GRID_LINES + yShift; row += 1) {
				if (row != 0 && row % 5 == 0) {
		
					double num = row * Math.pow(5, scaleCount) / 5;
					String str = new DecimalFormat("#.###").format(num);
					int numSize = str.length();
					
					float fontSize = (float) (Display.DEFAULT_SCALE * (fontNum - (numSize * 0.1)) / realScale);
					
					v1 = new CartesianVector(0.75f * Display.DEFAULT_SCALE * fontNum / realScale, -(row * artScale - fontSize/2));
					v1 = this.matrix.transform(v1);

					g2.setFont(
							new Font(Font.SANS_SERIF, Font.BOLD, 3)
									.deriveFont(fontSize));
					g2.drawString(str, (float) -v1.getXComponent(), (float) v1.getYComponent());
				}
			}
		}

		if (!this.matrix.getFirstColumn().isZeroVector() && this.matrix.getFirstColumn().linearlyIndependent(this.matrix.getSecondColumn())) {
			// draws grid line labels on major vertical lines
			for (int col = -NUM_OF_GRID_LINES + xShift; col <= NUM_OF_GRID_LINES + xShift; col += 1) {
				if (col != 0 && col % 5 == 0) {

					double num = col * Math.pow(5, scaleCount) / 5;
					String str = new DecimalFormat("#.###").format(num);
					int numSize = str.length();
					float fontSize = (float) (Display.DEFAULT_SCALE * (fontNum - (numSize * 0.1)) / realScale);
					
					v1 = new CartesianVector((col * artScale - (numSize*(fontSize/3))), -1.25f * Display.DEFAULT_SCALE * fontNum / realScale);
					v1 = this.matrix.transform(v1);
					
					g2.setFont(
							new Font(Font.SANS_SERIF, Font.BOLD, 3)
									.deriveFont(fontSize));
					g2.drawString(str, (float) v1.getXComponent(), (float) -v1.getYComponent());
				}
			}
		}

		
		float fontSize = (float) (Display.DEFAULT_SCALE * (3.5 - 0.25) / realScale);
		g2.setFont(
				new Font(Font.SANS_SERIF, Font.BOLD, 3)
						.deriveFont(fontSize));
		g2.drawString("0", 0.75f * Display.DEFAULT_SCALE * fontNum / realScale, 1.25f * Display.DEFAULT_SCALE * fontNum / realScale);
		
		g2.scale(1, -1);

	}

	/**
	 * Transforms this plane by applying the given matrix on this plane's matrix.
	 */
	public void transform(Matrix2D m) {
		System.out.println(m.applyTransformOn(this.getMatrix()));
		this.setMatrix(m.applyTransformOn(this.getMatrix()));
	}

	/**
	 * Returns the matrix of this plane
	 */
	public Matrix2D getMatrix() {
		return this.matrix;
	}

	/**
	 * Sets this plane's matrix to the given one
	 */
	public void setMatrix(Matrix2D m) {
		this.matrix = m;
	}
	
	@Override
	public JPopupMenu getRightClickMenu(Display display) {
		return new TransformationRightClick(display, this);
	}

}
