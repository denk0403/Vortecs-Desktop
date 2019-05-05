package Controls;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Matrices.Matrix2D;

// a container for all input boxes
public class InputBoxContainer extends JPanel {

	private static InputBoxContainer instance;
	private static List<VectorBox> boxes;

	static {
		instance = new InputBoxContainer();
	}

	private InputBoxContainer() {
		boxes = new LinkedList<>();
		this.initComponents();

	}

	private void initComponents() {
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(boxLayout);
	}

	// returns the single instance of input container
	public static InputBoxContainer getInstance() {
		return instance;
	}

	// adds an InputBox to this container
	public void addInputBox(VectorBox box) {
		boxes.add(box);
		this.add(box);
		this.revalidate();
		this.repaint();
	}

	// resets the container to be empty
	public void reset() {
		boxes.clear();
	}

	// draws all the items in the container
	public void drawInputs(Graphics g, Matrix2D m) {
		for (VectorBox box : boxes) {
			box.drawItem(g, m);
		}
	}

	public void removeBox(VectorBox associatedBox) {
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
		for (VectorBox ib : boxes) {
			ib.setInput(m.transform(ib.getInput()));
		}
	}

}
