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

package com.jtattoo.plaf;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 * <p>BaseLabelUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseLabelUI extends BasicLabelUI {

	private static BaseLabelUI baseLabelUI = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		if (baseLabelUI == null) {
			baseLabelUI = new BaseLabelUI();
		}
		return baseLabelUI;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintDisabledText(final JLabel l, final Graphics g, final String s, final int textX, final int textY) {
		final int mnemIndex = l.getDisplayedMnemonicIndex();
		g.setColor(Color.white);
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX + 1, textY + 1);
		g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintEnabledText(final JLabel l, final Graphics g, final String s, final int textX, final int textY) {
		final int mnemIndex = l.getDisplayedMnemonicIndex();
		final Object sc = l.getClientProperty("shadowColor");
		if (sc instanceof Color) {
			g.setColor((Color) sc);
			JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY + 1);
		}
		g.setColor(l.getForeground());
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
	}

} // end of class BaseLabelUI
