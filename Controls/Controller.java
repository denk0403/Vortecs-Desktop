package Controls;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import Vectors.CartesianVector;

// responsible for displaying buttons 
// and dispatching the correct InputBoxAdders
public class Controller extends JPanel {

	private static InputBoxContainer boxContainer = InputBoxContainer.getInstance();
	
	Controller() {
		this.initComponents();
	}

	private void initComponents() {
		
		JButton addVector = new JButton("Add Vector");
		
		addVector.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boxContainer.addInputBox(new VectorBox());
				boxContainer.repaint();
			}
		});
		
		this.add(addVector);
	}

	
	
	
	
	
	

}
