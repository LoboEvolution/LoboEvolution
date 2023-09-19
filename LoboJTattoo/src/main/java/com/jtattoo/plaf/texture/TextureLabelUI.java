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
package com.jtattoo.plaf.texture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>TextureLabelUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureLabelUI extends BasicLabelUI {

	private static TextureLabelUI hifiLabelUI = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		if (hifiLabelUI == null) {
			hifiLabelUI = new TextureLabelUI();
		}
		return hifiLabelUI;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY) {
		int mnemIndex = l.getDisplayedMnemonicIndex();
		Graphics2D g2D = (Graphics2D) g;
		Composite savedComposite = g2D.getComposite();
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
		g2D.setComposite(alpha);
		Color fc = l.getForeground();
		if (ColorHelper.getGrayValue(fc) > 164) {
			fc = ColorHelper.brighter(AbstractLookAndFeel.getDisabledForegroundColor(), 40);
			g.setColor(Color.black);
		} else {
			fc = AbstractLookAndFeel.getDisabledForegroundColor();
			g.setColor(Color.white);
		}
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY + 1);
		g2D.setComposite(savedComposite);
		g.setColor(fc);
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintEnabledText(JLabel l, Graphics g, String s, int textX, int textY) {
		int mnemIndex = l.getDisplayedMnemonicIndex();
		Color fc = l.getForeground();
		if (AbstractLookAndFeel.getTheme().isTextShadowOn() && ColorHelper.getGrayValue(fc) > 164) {
			g.setColor(Color.black);
			JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY + 1);
		} else {
			Object sc = l.getClientProperty("shadowColor");
			if (sc instanceof Color) {
				g.setColor((Color) sc);
				JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY + 1);
			}
		}
		g.setColor(fc);
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
	}

} // end of class TextureLabelUI
