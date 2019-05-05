package Controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Controls.MatrixSelect.MatrixSelection;
import Graphing.AnimateTransform;
import Graphing.Display;
import Graphing.Transformable;
import Graphing.TransformationPlane;
import Matrices.Matrix2D;
import Vectors.Vector;

public class TransformationRightClick extends JPopupMenu {

	private final Display display;
	private TransformationPlane plane;

	public TransformationRightClick(Display display, TransformationPlane plane) {
		this.display = display;
		this.plane = plane;
		this.initComponents();
	}

	private void initComponents() {

		JMenuItem recenter = new JMenuItem("Recenter View");
		recenter.setToolTipText("Recenters camera at (0,0) with current zoom");
		recenter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.recenterCamera();
				display.repaint();
			}
		});
		JMenuItem reset = new JMenuItem("Reset View");
		reset.setToolTipText("Recenters camera at (0,0) with default zoom");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.resetCamera();
				display.repaint();
			}
		});
		JMenuItem resetMat = new JMenuItem("Reset Basis");
		resetMat.setToolTipText("Undoes any transformations that have not been \"comitted\"");
		resetMat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane = new TransformationPlane();
				display.setItem(plane);
				display.repaint();
			}
		});
		JMenuItem custom = new JMenuItem("Apply Transformation...");
		custom.setToolTipText("Opens up menu of transformations");
		custom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new MatrixSelection(display, plane);
			}
		});
		JMenuItem fixCoor = new JMenuItem("Commit Transformation");
		fixCoor.setToolTipText("Commits transformation on vectors while reseting the basis");
		fixCoor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().applyToAll(plane.getMatrix());
				resetMat.doClick();
			}
		});
		
		
		this.add(recenter);
		this.add(reset);
		this.addSeparator();
		this.add(custom);
		this.add(fixCoor);
		this.add(resetMat);
	}

}
