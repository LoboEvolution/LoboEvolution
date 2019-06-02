package org.lobo.menu.tools.screen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.lobo.component.BrowserFrame;
import org.lobo.img.ImageViewer;

public class ScreenShootWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

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