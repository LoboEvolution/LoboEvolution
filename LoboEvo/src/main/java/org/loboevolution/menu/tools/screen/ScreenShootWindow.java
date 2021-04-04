/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

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
 *
 *
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
