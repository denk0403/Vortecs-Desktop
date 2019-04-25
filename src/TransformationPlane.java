import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

public class TransformationPlane implements Displayable, Transformable {

	public static final int NUM_OF_GRID_LINES = 1000;
	
	public TransformationPlane() {

	}

	@Override
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
			artScale *= Math.pow((Display.MIN_SCALE / Display.MAX_SCALE), 2);;
		}
		

		// sets the stroke to look consistent with the scale
		g2.setStroke(new BasicStroke(1.5f / realScale));

		// draws horizontal grid lines
		for (int row = -NUM_OF_GRID_LINES; row <= NUM_OF_GRID_LINES; row += 1) {
			if (row == 0) {
				g2.setStroke(new BasicStroke(3f / realScale));
				g2.draw(new Line2D.Double(-NUM_OF_GRID_LINES * artScale, row * artScale, NUM_OF_GRID_LINES * artScale, row * artScale));
				g2.setStroke(new BasicStroke(1.5f / realScale));
			} else {
				g2.draw(new Line2D.Double(-NUM_OF_GRID_LINES * artScale, row * artScale, NUM_OF_GRID_LINES * artScale, row * artScale));
			}

		}

		// draws vertical grid lines
		for (int col = -NUM_OF_GRID_LINES; col <= NUM_OF_GRID_LINES; col += 1) {
			if (col == 0) {
				g2.setStroke(new BasicStroke(3f / realScale));
				g2.draw(new Line2D.Double(col * artScale, -NUM_OF_GRID_LINES * artScale, col * artScale, NUM_OF_GRID_LINES * artScale));
				g2.setStroke(new BasicStroke(1.5f / realScale));
			} else {
				g2.draw(new Line2D.Double(col * artScale, -NUM_OF_GRID_LINES * artScale, col * artScale, NUM_OF_GRID_LINES * artScale));
			}
			
		}

		// draws vector inputs
		InputBoxContainer.getInstance().drawInputs(g);
	}
	
	// accepts visitor
	public <T> T accept(DisplayableVisitor<T> visitor) {
		return visitor.visit(this);
	}

//	protected void paintComponent(Graphics g) {
//		Graphics2D g2 = (Graphics2D) g;
//		AffineTransform b = g2.getTransform();
//		b.translate(this.getWidth() / 2, this.getHeight() / 2);
//		g2.setTransform(b);
//		g2.transform(a);
//		float scale = (float) Math.sqrt(Math.abs(Display.this.a.getDeterminant()));
//		System.out.println(g2.getTransform());
//		Stroke orig = g2.getStroke();
//		g2.setStroke(new BasicStroke(1.5f / scale));
//		for (int row = -1000; row <= 1000; row += 1) {
//			g2.draw(new Line2D.Double(-1000 * scale, row * scale, 1000 * scale, row * scale));
//		}
//		for (int col = -1000; col <= 1000; col += 1) {
//			g2.draw(new Line2D.Double(col * scale, -1000 * scale, col * scale, 1000 * scale));
//		}
//		g2.setStroke(orig);
//		InputBoxContainer.getInstance().drawInputs(g);
//	}

}
