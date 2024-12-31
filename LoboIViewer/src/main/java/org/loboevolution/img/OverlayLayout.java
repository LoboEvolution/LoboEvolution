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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/* This layout manager ensures that the ImageComponent and all the overlays fill
* the container exactly.
*/
/**
 * <p>OverlayLayout class.</p>
 *
 *
 *
 */
public class OverlayLayout implements LayoutManager {
	
	private final transient LayeredImageView layeredImageView;
	
	/**
	 * <p>Constructor for OverlayLayout.</p>
	 *
	 * @param layeredImageView a {@link org.loboevolution.img.LayeredImageView} object.
	 */
	public OverlayLayout(final LayeredImageView layeredImageView) {
		this.layeredImageView = layeredImageView;
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
		return layeredImageView.getTheImage().getPreferredSize();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		return layeredImageView.getTheImage().getMinimumSize();
	}

	/** {@inheritDoc} */
	@Override
	public void layoutContainer(final Container parent) {
		for (int i = 0; i < parent.getComponentCount(); i++) {
			parent.getComponent(i).setBounds(0, 0, parent.getWidth(), parent.getHeight());
		}
	}

}
