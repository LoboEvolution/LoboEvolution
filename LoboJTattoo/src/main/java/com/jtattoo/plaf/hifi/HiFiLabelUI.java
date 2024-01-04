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

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>HiFiLabelUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiLabelUI extends BasicLabelUI {

	private static HiFiLabelUI hifiLabelUI = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		if (hifiLabelUI == null) {
			hifiLabelUI = new HiFiLabelUI();
		}
		return hifiLabelUI;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintDisabledText(final JLabel l, final Graphics g, final String s, final int textX, final int textY) {
		final int mnemIndex = l.getDisplayedMnemonicIndex();
		g.setColor(Color.black);
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX + 1, textY + 1);
		g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintEnabledText(final JLabel l, final Graphics g, final String s, final int textX, final int textY) {
		final int mnemIndex = l.getDisplayedMnemonicIndex();
		final Color fc = l.getForeground();
		if (AbstractLookAndFeel.getTheme().isTextShadowOn() && ColorHelper.getGrayValue(fc) > 128) {
			g.setColor(Color.black);
			JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX + 1, textY + 1);
		}
		g.setColor(fc);
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
	}

} // end of class HiFiLabelUI
