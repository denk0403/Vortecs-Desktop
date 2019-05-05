package Controls.MatrixSelect;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

import Graphing.AnimateTransform;
import Graphing.Display;
import Graphing.TransformationPlane;
import Matrices.Matrix2D;
import Matrices.MatrixString;

public class MatrixInput extends JPanel {

	Display display;
	TransformationPlane plane;
	JFrame frame;
	JList<MatrixString> list;
	private JTextField e11;
	private JTextField e12;
	private JTextField e21;
	private JTextField e22;

	public MatrixInput(Display display, TransformationPlane plane, JFrame frame,
			JList<MatrixString> list) {
		super(new GridLayout(3, 2));
		this.display = display;
		this.plane = plane;
		this.frame = frame;
		this.list = list;
		this.e11 = new JTextField();
		this.e12 = new JTextField();
		this.e21 = new JTextField();
		this.e22 = new JTextField();
		this.initComponents();
	}

	private void initComponents() {

		
		MouseListener switchToCustom = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (MatrixInput.this.list.getSelectedValue() != MatrixString.CUSTOM) {
					Matrix2D copyOver = MatrixInput.this.list.getSelectedValue().getMatrix();
					MatrixInput.this.list.setSelectedValue(MatrixString.CUSTOM, true);
					MatrixInput.this.copyMatrix(copyOver);
					MatrixInput.this.list.repaint();
				}
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

			}
		};

		this.e11.addMouseListener(switchToCustom);
		this.e12.addMouseListener(switchToCustom);
		this.e21.addMouseListener(switchToCustom);
		this.e22.addMouseListener(switchToCustom);

		this.add(this.e11);
		this.add(this.e12);
		this.add(this.e21);
		this.add(this.e22);
		
		JSlider slider = new JSlider(AnimateTransform.MIN_ITERATIONS, AnimateTransform.MAX_ITERATIONS, AnimateTransform.DEFAULT_ITERATIONS);
		Hashtable<Integer, JLabel> labels = new Hashtable<>();
		labels.put(AnimateTransform.MIN_ITERATIONS, new JLabel("Fastest"));
		labels.put(AnimateTransform.MAX_ITERATIONS, new JLabel("Slowest"));
		slider.setLabelTable(labels);
		slider.setPaintLabels(true);
		
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AnimateTransform.ITERATIONS = slider.getValue();
				display.setItem(new AnimateTransform(plane,
						new Matrix2D(Parser.parse(MatrixInput.this.e11.getText()),
								Parser.parse(MatrixInput.this.e21.getText()),
								Parser.parse(MatrixInput.this.e12.getText()),
								Parser.parse(MatrixInput.this.e22.getText()))));
				display.repaint();
				frame.dispose();
			}
		});
		this.add(applyButton);
		
		JPanel speedControl = new JPanel();
		speedControl.setLayout(new BoxLayout(speedControl, BoxLayout.Y_AXIS));
		speedControl.add(new JLabel("Animation Speed"));
		speedControl.add(slider);
		this.add(speedControl);
	}

	public void copyMatrix(Matrix2D m) {
		this.e11.setText(new Double(m.getFirstColumn().getXComponent()).toString());
		this.e21.setText(new Double(m.getFirstColumn().getYComponent()).toString());
		this.e12.setText(new Double(m.getSecondColumn().getXComponent()).toString());
		this.e22.setText(new Double(m.getSecondColumn().getYComponent()).toString());
	}

	public void empty() {
		this.e11.setText("");
		this.e12.setText("");
		this.e21.setText("");
		this.e22.setText("");
	}

}
