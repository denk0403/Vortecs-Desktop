import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controls.ControlPanel;
import Graphing.Display;
import Graphing.TransformationPlane;

// the entire application essentially
public class Application {

	public Application() {
		this.initComponents();

	}

	private void initComponents() {

		// frame set-up
		JFrame frame = new JFrame("Vortecs Desktop");
		
		frame.setPreferredSize(new Dimension(900, 900));
		frame.setMinimumSize(new Dimension(250, 250));
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// innerPanel set-up
		JPanel innerPanel = new JPanel(new BorderLayout());
		Display screen = new Display(new TransformationPlane(), 650, 900);
		ControlPanel controls = new ControlPanel(screen);
		innerPanel.add(controls, BorderLayout.WEST);
		innerPanel.add(screen, BorderLayout.CENTER);
		

		//////////////////////////////////////////////
		frame.add(innerPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					screen.repaint();
					try {
						Thread.sleep(333);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).run();

	}
}
