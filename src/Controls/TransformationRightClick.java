package Controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Graphing.Display;
import Graphing.Transformable;
import Graphing.TransformationPlane;
import Matrices.Matrix2D;
import Vectors.Vector;

public class TransformationRightClick extends JPopupMenu {

	private final Display display;
	private final TransformationPlane plane;
	
	public TransformationRightClick(Display display, TransformationPlane plane) {
		this.display = display;
		this.plane = plane;
		this.initComponents();
	}

	private void initComponents() {
		
		JMenuItem recenter = new JMenuItem("Recenter View");
		recenter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.recenterCamera();
				display.repaint();
			}
		});
		JMenuItem reset = new JMenuItem("Reset View");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.resetCamera();
				display.repaint();
			}
		});
		JMenuItem addVector = new JMenuItem("Add Random Vector");
		addVector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().addInputBox(new VectorBox(
						Vector.generateRandomVector(1, 1)));
				display.repaint();
			}
		});
		JMenuItem undoVector = new JMenuItem("Undo Last Vector");
		undoVector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().removeLast();
				display.repaint();
			}
		});
		JMenuItem clearAll = new JMenuItem("Clear All Vectors");
		clearAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().reset();
				display.repaint();
			}
		});
		// TEMP!!!!!
		JMenuItem xyFlip = new JMenuItem("Flip X and Y Coordinates");
		xyFlip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().applyToAll(Matrix2D.FLIP_XY_MATRIX);
				display.repaint();
			}
		});
		JMenuItem rotateAllP = new JMenuItem("Rotate All Vectors 90º");
		rotateAllP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().applyToAll(Matrix2D.ROTATE_90_MATRIX);;
				display.repaint();
			}
		});
		JMenuItem rotateAllN = new JMenuItem("Rotate All Vectors -90º");
		rotateAllN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					InputBoxContainer.getInstance().applyToAll(Matrix2D.ROTATE_90_MATRIX.getInverse());
				} catch (Exception e1) {
					e1.printStackTrace();
				};
				display.repaint();
			}
		});
		JMenuItem slant = new JMenuItem("Slant Plane");
		slant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.transform(new Matrix2D(1,1,1,2));
				display.repaint();
			}
		});
		JMenuItem slantOther = new JMenuItem("Slant Plane Other Way");
		slantOther.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					plane.transform((new Matrix2D(1,1, 1, 2)).getInverse());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				display.repaint();
			}
		});
		JMenuItem projectX = new JMenuItem("Project onto X-Axis");
		projectX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.transform(Matrix2D.PROJECT_X_MATRIX);
				display.repaint();
			}
		});
		JMenuItem projectY = new JMenuItem("Project onto Y-Axis");
		projectY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.transform(Matrix2D.PROJECT_Y_MATRIX);
				display.repaint();
			}
		});
		JMenuItem projectXY = new JMenuItem("Project onto Y = X");
		projectXY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.transform(new Matrix2D(1/2.0, 1/2.0, 1/2.0, 1/2.0));
				display.repaint();
			}
		});
		JMenuItem reflectX = new JMenuItem("Reflect Over X-axis");
		reflectX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.transform(Matrix2D.REFLECT_OVER_X_MATRIX);
				display.repaint();
			}
		});
		JMenuItem reflectY = new JMenuItem("Reflect Over Y-axis");
		reflectY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.transform(Matrix2D.REFLECT_OVER_Y_MATRIX);
				display.repaint();
			}
		});
		JMenuItem reflectXY = new JMenuItem("Reflect Over Y = X");
		reflectXY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.transform(Matrix2D.FLIP_XY_MATRIX);
				display.repaint();
			}
		});
		JMenuItem resetMat = new JMenuItem("Reset Basis");
		resetMat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.setMatrix(Matrix2D.IDENTITY_MATRIX);
				display.repaint();
			}
		});
		this.add(recenter);
		this.add(reset);
		this.addSeparator();
		this.add(addVector);
		this.add(undoVector);
		this.add(clearAll);
		this.addSeparator();
		this.add(xyFlip);
		this.add(rotateAllP);
		this.add(rotateAllN);
		this.addSeparator();
		this.add(slant);
		this.add(slantOther);
		this.add(projectX);
		this.add(projectY);
		this.add(projectXY);
		this.add(reflectX);
		this.add(reflectY);
		this.add(reflectXY);
		this.add(resetMat);
	}
	
}
