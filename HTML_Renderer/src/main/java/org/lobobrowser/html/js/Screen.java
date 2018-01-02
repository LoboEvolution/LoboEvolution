/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.js;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.lobobrowser.js.AbstractScriptableDelegate;

/**
 * The Class Screen.
 */
public class Screen extends AbstractScriptableDelegate {

	/** The graphics environment. */
	private final GraphicsEnvironment graphicsEnvironment;

	/** The graphics device. */
	private final GraphicsDevice graphicsDevice;

	/**
	 * Instantiates a new screen.
	 */
	Screen() {
		super();
		if (GraphicsEnvironment.isHeadless()) {
			this.graphicsEnvironment = null;
			this.graphicsDevice = null;
		} else {
			this.graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			this.graphicsDevice = this.graphicsEnvironment.getDefaultScreenDevice();
		}
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		GraphicsDevice gd = this.graphicsDevice;
		return gd == null ? 0 : gd.getDisplayMode().getHeight();
	}

	/**
	 * Gets the pixel depth.
	 *
	 * @return the pixel depth
	 */
	public int getPixelDepth() {
		return this.getColorDepth();
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDisplayMode().getWidth();
	}

	/**
	 * Gets the avail height.
	 *
	 * @return the avail height
	 */
	public int getAvailHeight() {
		GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		return ge.getMaximumWindowBounds().height;
	}

	/**
	 * Gets the avail width.
	 *
	 * @return the avail width
	 */
	public int getAvailWidth() {
		GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		return ge.getMaximumWindowBounds().width;
	}

	/**
	 * Gets the color depth.
	 *
	 * @return the color depth
	 */
	public int getColorDepth() {
		GraphicsDevice gd = this.graphicsDevice;
		if (gd == null) {
			return 0;
		}
		return gd.getDisplayMode().getBitDepth();
	}
}
