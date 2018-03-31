/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.screenshot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.loboevolution.primary.clientlets.img.ImageViewer;

/**
 * The Class ScreenShootFrame.
 */
public class ScreenShootFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The image. */
	private transient BufferedImage image;

	public ScreenShootFrame(JPanel panelframe) {
		setTitle("Screenshot");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(700, 600));

		JFrame win = (JFrame) SwingUtilities.getWindowAncestor(panelframe);
		JPanel panel = new JPanel();
		Dimension size = win.getSize();
		image = (BufferedImage) win.createImage(size.width, size.height);
		Graphics g = image.getGraphics();
		win.paint(g);

		ImageViewer viewer = new ImageViewer(image);
		panel.add(viewer.getComponent());
		add(panel);
		revalidate();
		repaint();
		pack();
		this.setVisible(true);
	}
}
