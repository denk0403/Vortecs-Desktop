package Graphing;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import Controls.TransformationRightClick;

// displayer for graph inputs
public class Display extends JComponent {

	Camera camera;
	Displayable item;
	int width;
	int height;
	double centerX;
	double centerY;
	private static final float SCALE_NUMBER = (float) Math.pow(7, 2);
	public static final float DEFAULT_SCALE = (float)Math.sqrt(6*SCALE_NUMBER/10.0);
	public static final float MIN_SCALE = (float)Math.sqrt(SCALE_NUMBER/5.0);
	public static final float MAX_SCALE = (float)Math.sqrt(SCALE_NUMBER);
	public static final float SCALE_FACTOR = 1 / 0.925f;

	// creates a new Display
	public Display(Displayable item, int width, int height) {
		this.item = item;
		this.width = width;
		this.height = height;
		this.centerX = this.width / 2.0;
		this.centerY = this.height / 2.0;
		this.camera = new Camera(this.centerX, this.centerY, DEFAULT_SCALE);
		this.initComponents();
	}

	// initializes components of Display
	private void initComponents() {
		//this.setMinimumSize(new Dimension(this.width, this.height));
		this.setPreferredSize(new Dimension(this.width, this.height));
		
		// adds right-click menu
		JPopupMenu rightClickMenu = new TransformationRightClick(this, (TransformationPlane) this.item);
		this.setComponentPopupMenu(rightClickMenu);
		rightClickMenu.setInheritsPopupMenu(true);

		// adds a component listener to the display for updating the center
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			// fixes Display fields during resizing
			public void componentResized(ComponentEvent e) {
				Display.this.width = e.getComponent().getWidth();
				Display.this.height = e.getComponent().getHeight();
				Display.this.centerX = Display.this.width / 2.0;
				Display.this.centerY = Display.this.height / 2.0;
			}

			public void componentMoved(ComponentEvent e) {}

			public void componentHidden(ComponentEvent e) {}
		});

		// adds a Mouse Wheel Listener for zooming
		this.addMouseWheelListener(new MouseWheelListener() {

			// adjusts the camera to respond to mouse wheel inputs
			public void mouseWheelMoved(MouseWheelEvent e) {
				Camera camera = Display.this.camera;
				double scale = (float) camera.getScale();

				if (e.getPreciseWheelRotation() > 0.1) { // zoom out (shrinks)
					if (scale > 1 / 50.0) {
						camera.scaleAboutPoint(1 / Display.SCALE_FACTOR, e.getX(), e.getY());
					}

				} else if (e.getPreciseWheelRotation() < -0.1) { // zoom in (grows)
					if (scale < 100) {
						camera.scaleAboutPoint(Display.SCALE_FACTOR, e.getX(), e.getY());
					}
				}

				System.out.println(camera); // temp
				Display.this.repaint();
			}
		});

		// adds a Mouse Motion Listener for panning
		this.addMouseMotionListener(new MouseMotionListener() {

			int lastX = 0;
			int lastY = 0;

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

				// pans camera
				camera.translate(e.getX() - lastX, e.getY() - lastY);

				// updates values of mouse's last position
				lastX = e.getX();
				lastY = e.getY();

				// repaints the view to display the changes
				Display.this.repaint();
				System.out.println(camera); // temp
			}

		});

		// adds a Mouse Listener for click shortcuts
		this.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			// adds shortcuts for recentering and reseting camera
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2) {
						Display.this.recenterCamera();
					} else if (e.getClickCount() >= 3) {
						Display.this.resetCamera();
					}
				}
				Display.this.repaint();
			}
		});

	}

	public void recenterCamera() {
		this.camera = new Camera(Display.this.width / 2, Display.this.height / 2,
				Display.this.camera.getScale());
	}

	public void resetCamera() {
		this.camera = new Camera(Display.this.width / 2, Display.this.height / 2,
				Display.DEFAULT_SCALE);
	}

	// draws the display
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// NEEDS TO BE FIXED
		this.item.display(g2, this.camera, this);
	}

}
