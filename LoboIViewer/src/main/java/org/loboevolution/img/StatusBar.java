/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.img;

import javax.swing.JComponent;

/**
 * Abstract superclass for status bars of the image viewer.
 *
 * Author Kazo Csaba
 *
 */
public abstract class StatusBar {
	/**
	 * The ImageViewer associated with this status bar.
	 */
	private ImageViewer imageViewer;

	/**
	 * Returns the status bar component that can be added to the image viewer GUI.
	 *
	 * @return the status bar component
	 */
	public abstract JComponent getComponent();

	/**
	 * Returns the image viewer that this status bar belongs to.
	 *
	 * @return the current image viewer, or null if there is none
	 */
	public final ImageViewer getImageViewer() {
		return imageViewer;
	}

	/**
	 * <p>Setter for the field imageViewer.</p>
	 *
	 * @param imageViewer a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public final void setImageViewer(final ImageViewer imageViewer) {
		if (this.imageViewer != null)
			unregister(this.imageViewer);
		this.imageViewer = imageViewer;
		if (this.imageViewer != null)
			register(this.imageViewer);
	}

	/**
	 * Called when this status bar is added to an image viewer. Subclasses can
	 * override this method to register listeners.
	 *
	 * @param viewer
	 *            the new viewer associated with this status bar
	 */
	protected void register(final ImageViewer viewer) {
	}

	/**
	 * Called when this status bar is removed from an image viewer. Subclasses can
	 * override this method to remove listeners.
	 *
	 * @param viewer
	 *            the viewer that this status bar is removed from
	 */
	protected void unregister(final ImageViewer viewer) {
	}
}
