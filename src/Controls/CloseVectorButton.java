package Controls;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Graphing.Display;

// responsible for closing and removing input boxes
public class CloseVectorButton extends JButton {

	private VectorBox associatedBox;
	private Display display;

	CloseVectorButton(VectorBox associatedBox) {
		super("Delete");
		this.associatedBox = associatedBox;
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InputBoxContainer.getInstance().removeBox(associatedBox);
				display.getParent().repaint();
				CloseVectorButton.this.display.repaint();
			}
		});
	}

	void close() {
		InputBoxContainer.getInstance().removeBox(associatedBox);
	}
	
	void giveDisplay(Display display) {
		this.display = display;
	}

}
