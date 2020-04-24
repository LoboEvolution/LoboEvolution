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

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.loboevolution.common.Objects;

/**
 * The Class FullScreenWindow.
 */
public class FullScreenWindow {

	/**
	 * The screen that the user last chose for displaying a FullScreenWindow.
	 */

	private GraphicsDevice defaultScreen;

	/** The current screen for the FullScreenWindow. */

	private GraphicsDevice screen;

	/** The JFrame filling the screen. */

	private JFrame jf;

	/**
	 *
	 * Whether this FullScreenWindow has been used. Each FullScreenWindow
	 *
	 * can only be displayed once.
	 *
	 */

	private boolean dead = false;

	/**
	 * Flag indicating whether the user has selected a screen or not.
	 */

	private Flag flag = new Flag();

	/** The picked device. */
	private GraphicsDevice pickedDevice;

	/**
	 *
	 * Create a full screen window containing a JComponent, and ask the
	 *
	 * user which screen they'd like to use if more than one is present.
	 *
	 * @param part
	 *            the JComponent to display
	 *
	 * @param forcechoice
	 *            true if you want force the display of the screen
	 *
	 *            choice buttons. If false, buttons will only display if the user
	 *
	 *            hasn't previously picked a screen.
	 *
	 */

	public FullScreenWindow(JComponent part, boolean forcechoice) {
		init(part, forcechoice);
	}

	/**
	 *
	 * Create a full screen window containing a JComponent. The user
	 *
	 * will only be asked which screen to display on if there are multiple
	 *
	 * monitors attached and the user hasn't already made a choice.
	 *
	 * @param part
	 *            the JComponent to display
	 *
	 */

	public FullScreenWindow(JComponent part) {
		init(part, false);
	}

	/**
	 *
	 * Close the full screen window. This particular FullScreenWindow
	 *
	 * object cannot be used again.
	 *
	 */

	public void close() {
		dead = true;
		getFlag().set();
		screen.setFullScreenWindow(null);

		if (jf != null) {
			jf.dispose();
		}
	}

	/**
	 * Create the window, asking for which screen to use if there are multiple
	 * monitors and either forcechoice is true, or the user hasn't already picked a
	 * screen.
	 *
	 * @param part
	 *            the JComponent to display
	 * @param forcechoice
	 *            false if user shouldn't be asked twice which of several monitors
	 *            to use.
	 */

	private void init(JComponent part, boolean forcechoice) {
		screen = null;
		
		if (forcechoice) {
			defaultScreen = null;
		}

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screens[] = ge.getScreenDevices();

		if (defaultScreen != null) {
			for (GraphicsDevice screen2 : screens) {
				if (Objects.equals(screen2, defaultScreen)) {
					screen = defaultScreen;
				}
			}
		}

		if (screens.length == 1) {
			screen = screens[0];
		}

		if (screen == null) {
			screen = pickScreen(screens);
		}

		if (dead) {
			return;
		}

		defaultScreen = screen;

		GraphicsConfiguration gc = screen.getDefaultConfiguration();

		jf = new JFrame(gc);
		jf.setUndecorated(true);
		jf.setBounds(gc.getBounds());
		jf.getContentPane().add(part);
		jf.setVisible(true);
		screen.setFullScreenWindow(jf);
	}

	

	/**
	 *
	 * Displays a button on each attached monitor, and returns the
	 *
	 * GraphicsDevice object associated with that monitor.
	 *
	 * @param scrns
	 *            a list of GraphicsDevices on which to display buttons
	 *
	 * @return the GraphicsDevice selected.
	 *
	 */

	private GraphicsDevice pickScreen(GraphicsDevice scrns[]) {

		getFlag().clear();

		PickMe pickers[] = new PickMe[scrns.length];

		for (int i = 0; i < scrns.length; i++) {
			pickers[i] = new PickMe(scrns[i], this);
		}

		getFlag().waitForFlag();

		for (PickMe picker : pickers) {
			if (picker != null) {
				picker.dispose();
			}
		}
		return getPickedDevice();
	}

	/**
	 * @return the pickedDevice
	 */
	public GraphicsDevice getPickedDevice() {
		return pickedDevice;
	}

	/**
	 * @param pickedDevice the pickedDevice to set
	 */
	public void setPickedDevice(GraphicsDevice pickedDevice) {
		this.pickedDevice = pickedDevice;
	}

	/**
	 * @return the flag
	 */
	public Flag getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Flag flag) {
		this.flag = flag;
	}
}
