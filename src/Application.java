import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import Controls.ControlPanel;
import Controls.InputBoxContainer;
import Controls.VectorBox;
import Graphing.Display;
import Graphing.TransformationPlane;
import Vectors.Vector;

// the entire application essentially
public class Application {

	public Application() {
		this.initComponents();

	}

	private void initComponents() {

		// frame set-up
		JFrame frame = new JFrame("Grapher 3.0");
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
