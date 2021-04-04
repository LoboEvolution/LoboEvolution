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

package org.loboevolution.menu.view;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

/**
 * <p>FullScreenAction class.</p>
 *
 *
 *
 */
public class FullScreenAction extends AbstractAction {

	private static final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	private static final DisplayMode newDisplayMode = new DisplayMode(800, 600, 32, 0);

	private static final DisplayMode oldDisplayMode = device.getDisplayMode();

	private static final long serialVersionUID = 1L;

	private int countFs = 0;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for FullScreenAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public FullScreenAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.countFs == 0) {
			this.countFs = this.countFs + 1;
			device.setFullScreenWindow(this.frame);
			if (device != null && device.isDisplayChangeSupported()) {
				device.setDisplayMode(newDisplayMode);
			}
		} else {
			this.countFs = 0;
			device.setDisplayMode(oldDisplayMode);
			device.setFullScreenWindow(null);
		}

		this.frame.revalidate();

	}

}
