package Controls.MatrixSelect;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Graphing.Display;
import Graphing.TransformationPlane;
import Matrices.Matrix2D;
import Matrices.MatrixString;

public class MatrixSelection extends JFrame {

	private final Display display;
	private TransformationPlane plane;

	public MatrixSelection(Display display, TransformationPlane plane) {
		this.display = display;
		this.plane = plane;
		this.initComponents();
	}

	private void initComponents() {
		JFrame frame = new JFrame("Matrix Selection");
		frame.setPreferredSize(new Dimension(380, 250));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		/////////////////////////////////

		MatrixList panelWithList = new MatrixList();
		
		MatrixInput matrixInput = new MatrixInput(display, plane, frame, panelWithList.list);
		
		panelWithList.setMatrixInput(matrixInput);
		
		JPanel screen = new JPanel(new GridBagLayout());
		screen.add(panelWithList,
				new GridBagConstraints(0, 0, 4, 4, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
						GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

		screen.add(matrixInput,
				new GridBagConstraints(4, 0, 3, 3, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
						GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		
		frame.add(screen);
		
		/////////////////////////////
		frame.pack();
		frame.setLocationRelativeTo(this.display);
		frame.setVisible(true);
	}
}
