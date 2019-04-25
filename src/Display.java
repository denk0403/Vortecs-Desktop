import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

// displayer for graph inputs
public class Display extends JComponent {

	AffineTransform camera;
	double centerX; // maybe temp
	double centerY; // maybe temp
	Displayable item;
	int width; // maybe temp
	int height; // maybe temp
	public static final float DEFAULT_SCALE = 7f;
	public static final float MIN_SCALE = DEFAULT_SCALE / 2.5f;
	public static final float MAX_SCALE = 2 * DEFAULT_SCALE;
	public static final float SCALE_FACTOR = 1 / 0.975f;

	// creates a default Display
	public Display(Displayable item) {
		this(item, 200, 200);
	}

	// creates a new Display
	public Display(Displayable item, int width, int height) {
		this.camera = new AffineTransform();
		this.item = item; // maybe temp
		this.width = width; // maybe temp
		this.height = height; // maybe temp
		this.centerX = this.width / 2; // maybe temp
		this.centerY = this.height / 2; // maybe temp
		this.camera.translate(this.width / 2, this.height / 2);
		this.camera.scale(DEFAULT_SCALE, -DEFAULT_SCALE);
		System.out.println(this.camera);
		this.initComponents();
	}

	// initializes components of Display
	private void initComponents() {
		// this.setMinimumSize(new Dimension(this.width, this.height));
		this.setPreferredSize(new Dimension(this.width, this.height));

		// adds a Mouse Wheel Listener
		this.addMouseWheelListener(new MouseWheelListener() {

			// adjusts "zoom" of camera
			public void mouseWheelMoved(MouseWheelEvent e) {
				AffineTransform camera = Display.this.camera;
				float scale = (float) Math.sqrt(Math.abs(camera.getDeterminant()));

				if (e.getPreciseWheelRotation() > 0.15) { // zoom out (shrinks)
					if (scale > 1 / 200.0) {
						
						double dx = this.xOriginDisplacementFromMouse(e) / 25.0 / scale;
						double dy = this.yOriginDisplacementFromMouse(e) / 25.0 / scale;
						camera.translate(dx, dy);
						camera.scale(1 / Display.SCALE_FACTOR, 1 / Display.SCALE_FACTOR);
						
						System.out.println(camera);
					}
				// VERY BROKEN!!!!!
				} else if (e.getPreciseWheelRotation() < -0.10) { // zoom in (grows)
					if (scale < 500) {
						//camera.scale(Display.SCALE_FACTOR, Display.SCALE_FACTOR);
						//scale = (float) Math.sqrt(Math.abs(camera.getDeterminant()));
						double dx = -this.xOriginDisplacementFromMouse(e)*(Display.SCALE_FACTOR - 1)/scale;
						double dy = -this.yOriginDisplacementFromMouse(e)*(Display.SCALE_FACTOR - 1)/scale;
						//double dx = this.xOriginDisplacementFromMouse(e) / 25.0 / scale;
						//double dy = this.yOriginDisplacementFromMouse(e) / 25.0 / scale;
						camera.translate(dx, dy);
						camera.scale(Display.SCALE_FACTOR, Display.SCALE_FACTOR);
						//camera.translate(dx, dy);
						
					}
				}

				Display.this.repaint();
			}

			private double xDistanceToCenter(MouseWheelEvent e) {
				return e.getX() - Display.this.centerX;
			}

			private double yDistanceToCenter(MouseWheelEvent e) {
				return Display.this.centerY - e.getY();
			}

			private double xOriginDisplacementFromMouse(MouseWheelEvent e) {
				return this.xDistanceToCenter(e) - Display.this.getOriginXDisplacementToCenter();
			}

			private double yOriginDisplacementFromMouse(MouseWheelEvent e) {
				return this.yDistanceToCenter(e) - Display.this.getOriginYDisplacementToCenter();
			}
		});

		// adds a Mouse Motion Listener
		this.addMouseMotionListener(new MouseMotionListener() {

			int lastX;
			int lastY;

			// resets last position of mouseX and mouseY to 0
			// if not dragged
			public void mouseMoved(MouseEvent e) {
				lastX = 0;
				lastY = 0;
				//System.out.println(this.xOriginDisplacementFromMouse(e));
				//System.out.println(this.yOriginDisplacementFromMouse(e));
			}

			// responsible for panning camera
			public void mouseDragged(MouseEvent e) {
				AffineTransform camera = Display.this.camera;
				if (lastX == 0) {
					lastX = e.getX();
					lastY = e.getY();
				}
//				 System.out.print("X: ");
//				 System.out.println(camera.getTranslateX());
//				 System.out.print("Y: ");
//				 System.out.println(camera.getTranslateY());

				// function object to get the bounds of a Displayable
				DisplayableVisitor<Integer> boundGetter = new DisplayableVisitor<Integer>() {

					/**
					 * ADJUST MATH!!!
					 */
					public Integer visit(TransformationPlane tp) {
						return (int) (2.5 * TransformationPlane.NUM_OF_GRID_LINES);
					}
				};

				// pans camera
				camera.translate((e.getX() - lastX) / Math.sqrt(Math.abs(camera.getDeterminant())),
						-(e.getY() - lastY) / Math.sqrt(Math.abs(camera.getDeterminant())));

				// System.out.println(camera);
				// System.out.println(camera.getTranslateX());
				// System.out.println(camera.getTranslateY());
				// System.out.println(Display.this.centerX);
				// System.out.println(Display.this.centerY);
				// System.out.println(camera.getTranslateY());
				//System.out.println(Display.this.getOriginXDisplacementToCenter());
				//System.out.println(Display.this.getOriginYDisplacementToCenter());
				// System.out.println(Math.abs(camera.getTranslateX() - Display.this.centerX));
				// System.out.println(Display.this.item.accept(boundGetter));
				// System.out.println(Math.abs(camera.getTranslateY() - Display.this.centerY));
				// System.out.println(Display.this.item.accept(boundGetter));
				System.out.println("_______");

				// THIS PART IS KINDA BROKEN!!!!
				// checks if movement was outside of bounds
				if (Math.abs(camera.getTranslateX() - Display.this.centerX) > Display.this.item
						.accept(boundGetter)
						|| Math.abs(camera.getTranslateY()
								- Display.this.centerY) > Display.this.item.accept(boundGetter)) {

					// moves camera back
					camera.translate(
							-(e.getX() - lastX) / Math.sqrt(Math.abs(camera.getDeterminant())),
							(e.getY() - lastY) / Math.sqrt(Math.abs(camera.getDeterminant())));
				}

				// updates values of mouse's last position
				lastX = e.getX();
				lastY = e.getY();

				// repaints the view to display the changes
				Display.this.repaint();
			}
			
			private double xDistanceToCenter(MouseEvent e) {
				return e.getX() - Display.this.centerX;
			}

			private double yDistanceToCenter(MouseEvent e) {
				return Display.this.centerY - e.getY();
			}

			private double xOriginDisplacementFromMouse(MouseEvent e) {
				return this.xDistanceToCenter(e) - Display.this.getOriginXDisplacementToCenter();
			}

			private double yOriginDisplacementFromMouse(MouseEvent e) {
				return this.yDistanceToCenter(e) - Display.this.getOriginYDisplacementToCenter();
			}
		});

		this.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() == 2) {
					recenterView();
				}
				else if (e.getClickCount() == 3) {
					resetView();
				}
				Display.this.repaint();
			}
			
			private void recenterView() {
				double[] viewMatrix = new double[6];
				Display.this.camera.getMatrix(viewMatrix);
				viewMatrix[4] = Display.this.centerX;
				viewMatrix[5] = Display.this.centerY;
				Display.this.camera.setTransform(new AffineTransform(viewMatrix));
				Display.this.repaint();
			}
			
			private void resetView() {
				Display.this.camera = new AffineTransform();
				Display.this.camera.translate(Display.this.width / 2, Display.this.height / 2);
				Display.this.camera.scale(DEFAULT_SCALE, -DEFAULT_SCALE);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		this.revalidate();
		Graphics2D g2 = (Graphics2D) g;
		g2.transform(this.camera);
		this.item.display(g2);
	}

	private double getOriginXDisplacementToCenter() {
		return this.camera.getTranslateX() - this.centerX;
	}

	private double getOriginYDisplacementToCenter() {
		return this.centerY - this.camera.getTranslateY();
	}

}
