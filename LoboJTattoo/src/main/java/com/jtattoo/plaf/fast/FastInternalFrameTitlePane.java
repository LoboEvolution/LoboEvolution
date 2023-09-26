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
package com.jtattoo.plaf.fast;

import java.awt.Graphics;

import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>FastInternalFrameTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for FastInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public FastInternalFrameTitlePane(final JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(final Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowTitleBackgroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor());
		}
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void paintPalette(final Graphics g) {
		if (JTattooUtilities.isFrameActive(this)) {
			g.setColor(AbstractLookAndFeel.getWindowTitleBackgroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor());
		}
		g.fillRect(0, 0, getWidth(), getHeight());
	}

} // end of class FastInternalFrameTitlePane
