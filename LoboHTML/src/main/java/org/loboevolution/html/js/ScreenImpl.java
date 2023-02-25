/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.html.js;

import org.loboevolution.html.node.js.Screen;
import org.loboevolution.js.AbstractScriptableDelegate;

import java.awt.*;

/**
 * <p>ScreenImpl class.</p>
 */
public class ScreenImpl extends AbstractScriptableDelegate implements Screen {
	private final GraphicsDevice graphicsDevice;
	private final GraphicsEnvironment graphicsEnvironment;

	/**
	 * Instantiates a new java ScreenImpl.
	 */
	ScreenImpl() {
		if (GraphicsEnvironment.isHeadless()) {
			this.graphicsEnvironment = null;
			this.graphicsDevice = null;
		} else {
			this.graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			this.graphicsDevice = this.graphicsEnvironment.getDefaultScreenDevice();
		}
	}

	/**
	 * <p>getAvailHeight.</p>
	 *
	 * @return a int.
	 */
	public int getAvailHeight() {
		final GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		return ge.getMaximumWindowBounds().height;
	}

	/**
	 * <p>getAvailWidth.</p>
	 *
	 * @return a int.
	 */
	public int getAvailWidth() {
		final GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		return ge.getMaximumWindowBounds().width;
	}

	/**
	 * <p>getColorDepth.</p>
	 *
	 * @return a int.
	 */
	public int getColorDepth() {
		return 24;
	}

	/**
	 * <p>getHeight.</p>
	 *
	 * @return a int.
	 */
	public int getHeight() {
		final GraphicsDevice gd = this.graphicsDevice;
		return gd == null ? 0 : gd.getDisplayMode().getHeight();
	}

	/**
	 * <p>getPixelDepth.</p>
	 *
	 * @return a int.
	 */
	public int getPixelDepth() {
		return 96;
	}

	/**
	 * <p>getWidth.</p>
	 *
	 * @return a int.
	 */
	public int getWidth() {
		final GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		final GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDisplayMode().getWidth();
	}
}
