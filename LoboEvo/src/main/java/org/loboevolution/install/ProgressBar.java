package org.loboevolution.install;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * <p>ProgressBar class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ProgressBar extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(() -> new ProgressBar());
	}

	/**
	 * <p>Constructor for ProgressBar.</p>
	 */
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
