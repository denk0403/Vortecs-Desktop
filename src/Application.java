import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

// the entire application essentially
public class Application {

	public Application() {
		this.initComponents();

	}

	private void initComponents() {

		// frame set-up
		JFrame frame = new JFrame("Grapher 3.0");
		frame.setPreferredSize(new Dimension(800, 600));
		//frame.setMinimumSize(new Dimension(300, 100));
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		// innerPanel set-up
		JPanel innerPanel = new JPanel(new BorderLayout());
		ControlPanel controls = new ControlPanel();
		Display screen = new Display(new TransformationPlane(), 800, 600);
		innerPanel.add(controls, BorderLayout.WEST);
		innerPanel.add(screen, BorderLayout.CENTER);

		//////////////////////////////////////////////
		frame.add(innerPanel);
		frame.pack();
		frame.setVisible(true);
	}
}
