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
package com.jtattoo.plaf.hifi;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>HiFiInternalFrameTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for HiFiInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public HiFiInternalFrameTitlePane(final JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	protected boolean centerButtons() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Graphics g) {
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(final Graphics g, final int x, final int y, final String title) {
		g.setColor(Color.black);
		JTattooUtilities.drawString(frame, g, title, x + 1, y);
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
		}
		JTattooUtilities.drawString(frame, g, title, x, y - 1);
	}

} // end of class HiFiInternalFrameTitlePane
