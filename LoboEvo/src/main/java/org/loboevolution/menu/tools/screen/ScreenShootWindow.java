package org.loboevolution.menu.tools.screen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.img.ImageViewer;

/**
 * <p>ScreenShootWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ScreenShootWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ScreenShootWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public ScreenShootWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		setTitle("Screenshot");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(700, 600));

		final JPanel panel = new JPanel();
		final Dimension size = frame.getSize();

		final BufferedImage image = (BufferedImage) frame.createImage(size.width, size.height);
		final Graphics g = image.getGraphics();
		frame.paint(g);

		final ImageViewer viewer = new ImageViewer(image);
		panel.add(viewer.getComponent());
		add(panel);
		revalidate();
		repaint();
		pack();
		setVisible(true);
	}
}
