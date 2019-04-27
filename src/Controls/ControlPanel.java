package Controls;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

// The entire control panel on the left side of the screen
// only job is to display self
public class ControlPanel extends JPanel {

	private Controller controller;
	private JScrollPane inputScroll;

	// creates control panel
	public ControlPanel() {
		inputScroll = new JScrollPane(InputBoxContainer.getInstance(),
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		controller = new Controller();
		this.initComponents();
	}

	private void initComponents() {
		this.add(this.controller);
		this.add(inputScroll);
	}

	@Override
	protected void paintComponent(Graphics g) {
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		super.paintComponent(g);
	}

}
