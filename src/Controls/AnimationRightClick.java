package Controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Graphing.AnimateTransform;
import Graphing.Display;
import Graphing.TransformationPlane;

public class AnimationRightClick extends JPopupMenu {

	private final Display display;
	private AnimateTransform plane;

	public AnimationRightClick(Display display, AnimateTransform plane) {
		this.display = display;
		this.plane = plane;
		this.initComponents();
	}

	private void initComponents() {
		
		JMenuItem pause;
		JMenuItem unpause;
		
		pause = new JMenuItem("Pause Animation");
		unpause = new JMenuItem("Play Animation");
		
		pause.setVisible(true);
		unpause.setVisible(false);
		
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.pause();
				pause.setVisible(false);
				unpause.setVisible(true);
			}
		});
		unpause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plane.unpause();
				pause.setVisible(true);
				unpause.setVisible(false);
			}
		});
		JMenuItem stop = new JMenuItem("Stop Animation");
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.setItem(new TransformationPlane(plane.getCurrentMatrix()));
			}
		});
		JMenuItem recenter = new JMenuItem("Recenter View");
		recenter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.recenterCamera();
				display.repaint();
			}
		});
		JMenuItem reset = new JMenuItem("Reset View");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				display.resetCamera();
				display.repaint();
			}
		});
		
		this.add(recenter);
		this.add(reset);
		this.addSeparator();
		this.add(pause);
		this.add(unpause);
		this.add(stop);
	}
	
}
