import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

// displayer for graph inputs
public class Display extends JComponent {

	Camera camera;
	Displayable item;
	int width;
	int height;
	double centerX;
	double centerY;
	public static final float DEFAULT_SCALE = 6f;
	public static final float MIN_SCALE = DEFAULT_SCALE / 1.5f;
	public static final float MAX_SCALE = 1.5f * DEFAULT_SCALE;
	public static final float SCALE_FACTOR = 1 / 0.925f;

	// creates a new Display
	public Display(Displayable item, int width, int height) {
		this.item = item;
		this.width = width;
		this.height = height;
		this.centerX = this.width / 2;
		this.centerY = this.height / 2;
		this.camera = new Camera(this.centerX, this.centerY, DEFAULT_SCALE);
		this.initComponents();
	}

	// initializes components of Display
	private void initComponents() {
		// this.setMinimumSize(new Dimension(this.width, this.height));
		this.setPreferredSize(new Dimension(this.width, this.height));

		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				Camera camera = Display.this.camera;
				double scale = (float) Math.sqrt(Math.abs(camera.getScale()));

				if (e.getPreciseWheelRotation() > 0.1) { // zoom out (shrinks)
					if (scale > 1 / 200.0) {
						camera.scaleAboutPoint(1/Display.SCALE_FACTOR, e.getX(), e.getY());
					}

				} else if (e.getPreciseWheelRotation() < -0.1) { // zoom in (grows)
					if (scale < 500) {
						camera.scaleAboutPoint(Display.SCALE_FACTOR, e.getX(), e.getY());

					}
				}
				System.out.println(camera);
				Display.this.repaint();
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
			}

			// responsible for panning camera
			public void mouseDragged(MouseEvent e) {
				Camera camera = Display.this.camera;
				if (lastX == 0) {
					lastX = e.getX();
					lastY = e.getY();
				}

				/**
				 * 
				// function object to get the bounds of a Displayable
				DisplayableVisitor<Integer> boundGetter = new DisplayableVisitor<Integer>() {

					
					// MATH HERE IS ARBITRARY!!! TWEAK AND BALANCE IT!!!!
					 
					public Integer visit(TransformationPlane tp) {
						return (int) (2.5 * TransformationPlane.NUM_OF_GRID_LINES);
					}
				};

				*/
				
				// pans camera
				camera.translate(e.getX() - lastX, e.getY() - lastY);

				/*
				// THIS PART IS KINDA BROKEN!!!!
				// checks if movement was outside of bounds
				if (Math.abs(camera.getTranslateX() - Display.this.centerX) > Display.this.item
						.accept(boundGetter)
						|| Math.abs(camera.getTranslateY()
								- Display.this.centerY) > Display.this.item.accept(boundGetter)) {

					// moves camera back
					camera.translate(-(e.getX() - lastX), -(e.getY() - lastY));
				}
				*/

				// updates values of mouse's last position
				lastX = e.getX();
				lastY = e.getY();

				// repaints the view to display the changes
				Display.this.repaint();
			}

		});

		this.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() == 2) {
					Display.this.camera = new Camera(Display.this.width / 2,
							Display.this.height / 2, Display.this.camera.getScale());
				} else if (e.getClickCount() >= 3) {
					Display.this.camera = new Camera(Display.this.width / 2,
							Display.this.height / 2, Display.DEFAULT_SCALE);
				}
				Display.this.repaint();
			}
		});
	}

	// draws the display
	protected void paintComponent(Graphics g) {
		this.revalidate();
		Graphics2D g2 = (Graphics2D) g;
		
		// NEEDS TO BE FIXED
		g2.transform(this.camera.getTransform());
		this.item.display(g2, this.camera);
	}


}
