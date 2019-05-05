package Controls;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Matrices.Matrix2D;
import Vectors.CartesianVector;
import Vectors.Vector;

// box for user inputs for vectors
// responsible for manipulating vector
public class VectorBox extends JPanel {

	Vector vector; // vector represented by vector box
	CloseVectorButton closeButton;
	JSpinner xSpinner;
	JSpinner ySpinner;
	JSpinner lengthSpinner;
	JSpinner thetaSpinner;

	// creates a vector box with the zero vector
	public VectorBox() {
		super(new GridLayout(0, 2));
		this.vector = new CartesianVector(0, 0);
		this.closeButton = new CloseVectorButton(this);
		this.initComponents();
	}

	// creates a vector box with the given vector
	public VectorBox(Vector v) {
		super(new GridLayout(0, 2));
		this.vector = v;
		this.closeButton = new CloseVectorButton(this);
		this.initComponents();
	}

	// initiates some components
	private void initComponents() {

		this.setMinimumSize(new Dimension(175, 125));
		this.setMaximumSize(new Dimension(175, 125));

		this.closeButton.addMouseListener(new MouseListener() {

			// deletes this vector
			public void mousePressed(MouseEvent e) {
				VectorBox.this.closeSelf();
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		xSpinner = new JSpinner(new SpinnerNumberModel(0, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, 0.05));
		xSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				VectorBox.this.vector = VectorBox.this.vector
						.changeXTo((Double) xSpinner.getValue());
				updateSpinners();
				ControlPanel.display.repaint();
			}
		});

		ySpinner = new JSpinner(new SpinnerNumberModel(0, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, 0.05));
		ySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				VectorBox.this.vector = VectorBox.this.vector
						.changeYTo((Double) ySpinner.getValue());
				updateSpinners();
				ControlPanel.display.repaint();
			}
		});

		lengthSpinner = new JSpinner(new SpinnerNumberModel(0, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, 0.05));
		lengthSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				VectorBox.this.vector = VectorBox.this.vector
						.changeLengthTo((Double) lengthSpinner.getValue());
				updateSpinners();
				ControlPanel.display.repaint();
			}
		});

		thetaSpinner = new JSpinner(new SpinnerNumberModel(0, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, 0.01));
		thetaSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				VectorBox.this.vector = VectorBox.this.vector
						.changeThetaTo((Double) thetaSpinner.getValue());
				updateSpinners();
				ControlPanel.display.repaint();
			}
		});
		this.setBackground(this.vector.getColor());
		this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		this.add(new JLabel("X:"));
		this.add(new JLabel("Y:"));
		this.add(xSpinner);
		this.add(ySpinner);
		this.add(new JLabel("Length:"));
		this.add(new JLabel("Angle:"));
		this.add(lengthSpinner);
		this.add(thetaSpinner);

		JMenuBar bar = new JMenuBar();
		JMenu shortcuts = new JMenu("Shortcuts", true);
		JMenuItem negate = new JMenuItem("Negate");
		JMenuItem norm = new JMenuItem("Normalize");
		JMenuItem ortho = new JMenuItem("Orthogonalize");

		negate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setInput(vector.negate());
			}
		});

		norm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setInput(vector.getUnitVector());
				} catch (Exception e1) {
				}
			}
		});

		ortho.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setInput(vector.orthogonalize());
			}
		});
		
		shortcuts.add(negate);
		shortcuts.add(ortho);
		shortcuts.add(norm);
		
		bar.add(shortcuts);
		this.add(bar);

		this.add(closeButton);
	}

	protected void updateSpinners() {
		if (Math.abs((Double) xSpinner.getValue() - this.vector.getXComponent()) > 0.000001) {
			xSpinner.setValue(vector.getXComponent());
		}
		if (Math.abs((Double) ySpinner.getValue() - this.vector.getYComponent()) > 0.000001) {
			ySpinner.setValue(vector.getYComponent());
		}
		if (Math.abs((Double) lengthSpinner.getValue() - this.vector.getLength()) > 0.000001) {
			lengthSpinner.setValue(vector.getLength());
		}
		if (Math.abs((Double) thetaSpinner.getValue() - this.vector.getAngle()) > 0.000001) {
			thetaSpinner.setValue(vector.getAngle());
		}
	}

	// closes this vector
	private void closeSelf() {
		this.closeButton.close();
	}

	// draws the box's item onto the given graphics object
	public void drawItem(Graphics g, Matrix2D m) {
		// System.out.println("Drawing Vector!");
		vector.drawSelf(g, m);
	}

	// updates the x-component of the corresponding vector
	void changeXTo(double newX) {
		this.vector = this.vector.changeXTo(newX);
	}

	// updates the y-component of the corresponding vector
	void changeYTo(double newY) {
		this.vector = this.vector.changeYTo(newY);
	}

	// returns the input
	public Vector getInput() {
		return this.vector;
	}

	public void setInput(Vector vector) {
		this.vector = vector;
		updateSpinners();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}

}
