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
/*
 * Created on Mar 19, 2005
 */
package org.loboevolution.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * <p>WrapperLayout class.</p>
 *
 * Author J. H. S.
 *
 */
public class WrapperLayout implements LayoutManager {
	private static final WrapperLayout instance = new WrapperLayout();

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link org.loboevolution.common.WrapperLayout} object.
	 */
	public static WrapperLayout getInstance() {
		return instance;
	}


	/** {@inheritDoc} */
	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
	}

	/** {@inheritDoc} */
	@Override
	public void layoutContainer(Container arg0) {
		final int count = arg0.getComponentCount();
		if (count > 0) {
			final Component child = arg0.getComponent(0);
			final java.awt.Insets insets = arg0.getInsets();
			child.setBounds(insets.left, insets.top, arg0.getWidth() - insets.left - insets.right,
					arg0.getHeight() - insets.top - insets.bottom);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		final java.awt.Insets insets = arg0.getInsets();
		final int count = arg0.getComponentCount();
		if (count > 0) {
			final Dimension d = arg0.getComponent(0).getMinimumSize();
			return new Dimension(d.width + insets.left + insets.right, d.height + insets.top + insets.bottom);
		} else {
			return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		final java.awt.Insets insets = arg0.getInsets();
		final int count = arg0.getComponentCount();
		if (count > 0) {
			final Dimension d = arg0.getComponent(0).getPreferredSize();
			return new Dimension(d.width + insets.left + insets.right, d.height + insets.top + insets.bottom);
		} else {
			return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeLayoutComponent(Component arg0) {
	}
}
