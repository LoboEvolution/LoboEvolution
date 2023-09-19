/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
