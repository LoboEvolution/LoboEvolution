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

package org.loboevolution.menu.view;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

/**
 * <p>FullScreenAction class.</p>
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
	public FullScreenAction(final BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
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
