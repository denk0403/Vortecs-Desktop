package Controls;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import Graphing.Display;

// The entire control panel on the left side of the screen
// only job is to display self
public class ControlPanel extends JPanel {

	private Controller controller;
	private JScrollPane inputScroll;
	static Display display;

	// creates control panel
	public ControlPanel(Display display) {
		ControlPanel.display = display;
		inputScroll = new JScrollPane(InputBoxContainer.getInstance(),
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//inputScroll = new JScrollPane();
		controller = new Controller();
		this.initComponents();
	}

	private void initComponents() {
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200,200));
		inputScroll.setViewportView(InputBoxContainer.getInstance());
		this.add(this.controller, BorderLayout.NORTH);
		this.add(inputScroll, BorderLayout.CENTER);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
