/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

/**
 * A default status bar implementation that displays the current mouse position
 * (in pixel coordinates) and the colour of the pixel under the cursor.
 *
 * Author Kazo Csaba
 *
 */
public class DefaultStatusBar extends PixelInfoStatusBar implements ImageMouseMotionListener {

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(final ImageMouseEvent e) {
		setPixel(e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(final ImageMouseEvent e) {
		setPixel(-1, -1);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseEntered(final ImageMouseEvent e) {
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDragged(final ImageMouseEvent e) {
	}

	/** {@inheritDoc} */
	@Override
	protected void register(final ImageViewer viewer) {
		super.register(viewer);
		viewer.addImageMouseMotionListener(this);
	}

	/** {@inheritDoc} */
	@Override
	protected void unregister(final ImageViewer viewer) {
		super.unregister(viewer);
		viewer.removeImageMouseMotionListener(this);
	}

}
