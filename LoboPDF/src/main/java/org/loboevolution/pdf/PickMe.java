/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
package org.loboevolution.pdf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * A button that appears on a particular graphics device, asking whether that
 * device should be used for multiple-monitor choices.
 */

public class PickMe extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -658915481325845436L;

	/** The mygd. */
	private transient GraphicsDevice mygd;
	
	private transient FullScreenWindow full;

	/**
	 * Creates the PickMe button on a particular display.
	 *
	 * @param gd
	 *            the GraphicsDevice (display) to use for this button
	 */
	
	public PickMe(GraphicsDevice gd, FullScreenWindow full) {
		super(gd.getDefaultConfiguration());
		createAndShowGUI(gd, full);
	}

	private void createAndShowGUI(GraphicsDevice gd, FullScreenWindow full) {
		setUndecorated(true);
		mygd = gd;
		this.full = full;
		JButton jb = new JButton("Click here to use this screen");
		jb.setBackground(Color.yellow);
		jb.addActionListener(evt -> pickDevice(mygd));
		Dimension sz = jb.getPreferredSize();
		sz.width += 30;
		sz.height = 0;
		jb.setPreferredSize(sz);
		getContentPane().add(jb);
		pack();
		Rectangle bounds = gd.getDefaultConfiguration().getBounds();
		int x = bounds.width / 2 - sz.width / 2 + bounds.x;
		int y = bounds.height / 2 - sz.height / 2 + bounds.y;
		setLocation(x, y);
		setVisible(true);
	}
	
	/**
	 * Select a particular screen for display of this window, and set the flag.
	 */

	private void pickDevice(GraphicsDevice gd) {
		full.setPickedDevice(gd);
		full.getFlag().set();
	}
}
