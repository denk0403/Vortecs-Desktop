package Controls;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Matrices.Matrix2D;
import Vectors.Vector;

// a container for all input boxes
public class InputBoxContainer extends JPanel {

	private static InputBoxContainer instance;
	private static List<InputBox> boxes;

	static {
		instance = new InputBoxContainer();
	}

	private InputBoxContainer() {
		boxes = new LinkedList<>();
		this.initComponents();

		// for testing only
		////////////////
		/*
		 * VectorBox test1 = new VectorBox(); test1.changeXTo(10 * Math.random() - 5);
		 * test1.changeYTo(10 * Math.random() - 5); VectorBox test2 = new VectorBox();
		 * test2.changeXTo(0.1); test2.changeYTo(0.1); VectorBox test3 = new
		 * VectorBox(); test3.changeXTo(10 * Math.random() - 5); test3.changeYTo(10 *
		 * Math.random() - 5); VectorBox test4 = new VectorBox(); test4.changeXTo(10 *
		 * Math.random() - 5); test4.changeYTo(10 * Math.random() - 5); VectorBox test5
		 * = new VectorBox(); test5.changeXTo(10); test5.changeYTo(10);
		 * this.addInputBox(test1); this.addInputBox(test2); this.addInputBox(test3);
		 * this.addInputBox(test4); this.addInputBox(test5);
		 */
		/////////////////

	}

	private void initComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	// returns the single instance of input container
	public static InputBoxContainer getInstance() {
		return instance;
	}

	// adds an InputBox to this container
	public void addInputBox(InputBox box) {
		boxes.add(box);
		this.add((JPanel) box);
	}

	// resets the container to be empty
	public void reset() {
		boxes.clear();
	}

	// draws all the items in the container
	public void drawInputs(Graphics g, Matrix2D m) {
		for (InputBox box : boxes) {
			box.drawItem(g, m);
		}
	}

	public void remove(InputBox associatedBox) {
		boxes.remove(associatedBox);
		this.remove(associatedBox);
		this.validate();
		this.repaint();
	}

	public void removeLast() {
		if (boxes.size() > 0)
			boxes.remove(boxes.size() - 1);
	}

	public void applyToAll(Matrix2D m) {
		List<InputBox> newBoxes = new LinkedList<>();
		for (InputBox ib : boxes) {
			newBoxes.add(new VectorBox(m.transform(((Vector) (ib.getInput())))));
		}
		boxes = newBoxes;
	}

}
