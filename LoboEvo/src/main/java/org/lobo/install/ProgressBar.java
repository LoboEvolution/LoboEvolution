package org.lobo.install;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class ProgressBar extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(() -> new ProgressBar());
	}

	public ProgressBar() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setTitle("Installing...");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		final JComponent newContentPane = new ProgressBarUI(this);
		newContentPane.setOpaque(true);
		setContentPane(newContentPane);
		pack();
		setVisible(true);

	}

}
