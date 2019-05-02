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
		
		/*
		JMenuBar bar = new JMenuBar();
		bar.add(new JMenu("hello", true));
		frame.setJMenuBar(bar);
		*/

		

		// innerPanel set-up
		JPanel innerPanel = new JPanel(new BorderLayout());
		ControlPanel controls = new ControlPanel();
		Display screen = new Display(new TransformationPlane(), 900, 900);
		//innerPanel.add(controls, BorderLayout.WEST);
		innerPanel.add(screen, BorderLayout.CENTER);
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers()
						& Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0) && e.isShiftDown()) {
					InputBoxContainer.getInstance().addInputBox(new VectorBox(Vector.generateRandomVector(1, 1)));
					screen.repaint();
					
				}
				else if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers()
						& Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0)) {
					InputBoxContainer.getInstance().removeLast();
					screen.repaint();
				}
			}
		});

		//////////////////////////////////////////////
		frame.add(innerPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
