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

package org.loboevolution.img;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.JViewport;

/**
 * A custom layout manager for the image viewer scroll pane viewport. It is used
 * to set the size of the viewport component with respect to the resize
 * strategy.
 * 
 * Author Kaz Csaba
 */
class CustomViewportLayout implements LayoutManager {

	private final ImageViewer viewer;

	/**
	 * <p>Constructor for CustomViewportLayout.</p>
	 *
	 * @param viewer a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public CustomViewportLayout(final ImageViewer viewer) {
		this.viewer = viewer;
	}

	/** {@inheritDoc} */
	@Override
	public void addLayoutComponent(final String name, final Component comp) {
	}

	/** {@inheritDoc} */
	@Override
	public void removeLayoutComponent(final Component comp) {
	}

	/** {@inheritDoc} */
	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		final BufferedImage image = viewer.getImage();
		if (image == null)
			return new Dimension();
		else
			return new Dimension(image.getWidth(), image.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		return new Dimension(4, 4);
	}

	/** {@inheritDoc} */
	@Override
	public void layoutContainer(final Container parent) {
		final JViewport vp = (JViewport) parent;
		final Component view = vp.getView();

		if (view == null) {
			return;
		}

		final Dimension vpSize = vp.getSize();
		final Dimension viewSize = new Dimension(view.getPreferredSize());

		if (viewer.getResizeStrategy() == ResizeStrategy.SHRINK_TO_FIT
				|| viewer.getResizeStrategy() == ResizeStrategy.RESIZE_TO_FIT) {
			viewSize.width = vpSize.width;
			viewSize.height = vpSize.height;
		} else {
			viewSize.width = Math.max(viewSize.width, vpSize.width);
			viewSize.height = Math.max(viewSize.height, vpSize.height);
		}

		// vp.setViewPosition(new Point());
		vp.setViewSize(viewSize);
	}
}
