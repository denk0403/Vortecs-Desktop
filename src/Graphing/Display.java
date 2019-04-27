package Graphing;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Controls.InputBoxContainer;
import Controls.VectorBox;
import Matrices.Matrix2D;
import Vectors.CartesianVector;

// displayer for graph inputs
public class Display extends JComponent {

	Camera camera;
	Displayable item;
	int width;
	int height;
	double centerX;
	double centerY;
	public static final float DEFAULT_SCALE = 8f;
	public static final float MIN_SCALE = DEFAULT_SCALE / 1.5f;
	public static final float MAX_SCALE = 1.5f * DEFAULT_SCALE;
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

		// adds a Mouse Wheel Listener
		this.addMouseWheelListener(new MouseWheelListener() {

			// adjusts the camera to respond to mouse wheel inputs
			public void mouseWheelMoved(MouseWheelEvent e) {
				Camera camera = Display.this.camera;
				double scale = (float) Math.sqrt(camera.getScale());

				if (e.getPreciseWheelRotation() > 0.1) { // zoom out (shrinks)
					if (scale > 1 / 200.0) {
						camera.scaleAboutPoint(1 / Display.SCALE_FACTOR, e.getX(), e.getY());
					}

				} else if (e.getPreciseWheelRotation() < -0.1) { // zoom in (grows)
					if (scale < 250) {
						camera.scaleAboutPoint(Display.SCALE_FACTOR, e.getX(), e.getY());
					}
				}

				System.out.println(camera); // temp
				Display.this.repaint();
			}
		});

		// adds a Mouse Motion Listener
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

		// adds a Mouse Listener
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

		// MOVE TO NEW CLASS!!! ///////////////////
		// adds right-click menu
		JPopupMenu rightClickMenu = new JPopupMenu();
		JMenuItem recenter = new JMenuItem("Recenter View");
		recenter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Display.this.recenterCamera();
				Display.this.repaint();
			}
		});
		JMenuItem reset = new JMenuItem("Reset View");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Display.this.resetCamera();
				Display.this.repaint();
			}
		});
		JMenuItem addVector = new JMenuItem("Add Random Vector");
		addVector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().addInputBox(new VectorBox(
						new CartesianVector(30 * Math.random() - 15, 30 * Math.random() - 15)));
				Display.this.repaint();
			}
		});
		JMenuItem undoVector = new JMenuItem("Undo Last Vector");
		undoVector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().removeLast();
				Display.this.repaint();
			}
		});
		JMenuItem clearAll = new JMenuItem("Clear All Vectors");
		clearAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().reset();
				Display.this.repaint();
			}
		});
		// TEMP!!!!!
		JMenuItem xyFlip = new JMenuItem("Flip X and Y Coordinates");
		xyFlip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().applyToAll(new Matrix2D(0, 1, 1, 0));;
				Display.this.repaint();
			}
		});
		JMenuItem rotateAllP = new JMenuItem("Rotate All Vectors 90º");
		rotateAllP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().applyToAll(new Matrix2D(0, 1, -1, 0));;
				Display.this.repaint();
			}
		});
		JMenuItem rotateAllN = new JMenuItem("Rotate All Vectors -90º");
		rotateAllN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					InputBoxContainer.getInstance().applyToAll((new Matrix2D(0, 1, -1, 0)).getInverse());
				} catch (Exception e1) {
					e1.printStackTrace();
				};
				Display.this.repaint();
			}
		});
		JMenuItem slant = new JMenuItem("Slant Plane");
		slant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Transformable)(Display.this.item)).transform(new Matrix2D(1, 0, 1/Math.sqrt(2), 1/Math.sqrt(2)));
				Display.this.repaint();
			}
		});
		JMenuItem slantOther = new JMenuItem("Slant Plane Other Way");
		slantOther.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					((Transformable)(Display.this.item)).transform((new Matrix2D(1, 0, 1/Math.sqrt(2), 1/Math.sqrt(2))).getInverse());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Display.this.repaint();
			}
		});
		JMenuItem project = new JMenuItem("Project onto X-Axis");
		project.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Transformable)(Display.this.item)).transform(new Matrix2D(1, 1, 1, 1));
				Display.this.repaint();
			}
		});
		JMenuItem resetMat = new JMenuItem("Reset Basis");
		resetMat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Transformable)(Display.this.item)).setMatrix(new Matrix2D(1, 0, 0, 1));
				Display.this.repaint();
			}
		});
		rightClickMenu.add(recenter);
		rightClickMenu.add(reset);
		rightClickMenu.addSeparator();
		rightClickMenu.add(addVector);
		rightClickMenu.add(undoVector);
		rightClickMenu.add(clearAll);
		rightClickMenu.addSeparator();
		rightClickMenu.add(xyFlip);
		rightClickMenu.add(rotateAllP);
		rightClickMenu.add(rotateAllN);
		rightClickMenu.addSeparator();
		rightClickMenu.add(slant);
		rightClickMenu.add(slantOther);
		rightClickMenu.add(project);
		rightClickMenu.add(resetMat);
		
		

		this.setComponentPopupMenu(rightClickMenu);
		rightClickMenu.setInheritsPopupMenu(true);
		///////////////////////////
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
