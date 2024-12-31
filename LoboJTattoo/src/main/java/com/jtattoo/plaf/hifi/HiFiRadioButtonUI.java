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
package com.jtattoo.plaf.hifi;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRadioButtonUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>HiFiRadioButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiRadioButtonUI extends BaseRadioButtonUI {

	private static HiFiRadioButtonUI radioButtonUI = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		if (radioButtonUI == null) {
			radioButtonUI = new HiFiRadioButtonUI();
		}
		return radioButtonUI;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(final Graphics g, final JComponent c) {
		if (c.isOpaque()) {
			if (c.getBackground() instanceof ColorUIResource
					&& c.getBackground().equals(AbstractLookAndFeel.getBackgroundColor())) {
				HiFiUtils.fillComponent(g, c);
			} else {
				g.setColor(c.getBackground());
				g.fillRect(0, 0, c.getWidth(), c.getHeight());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final JComponent c, final String text, final Rectangle textRect) {
		final View v = (View) c.getClientProperty(BasicHTML.propertyKey);
		if (v != null) {
			v.paint(g, textRect);
		} else {
			final AbstractButton b = (AbstractButton) c;
			final ButtonModel model = b.getModel();
			final int mnemIndex = b.getDisplayedMnemonicIndex();
			g.setFont(b.getFont());
			final FontMetrics fm = JTattooUtilities.getFontMetrics(b, g, b.getFont());
			if (model.isEnabled()) {
				final Color fc = b.getForeground();
				if (AbstractLookAndFeel.getTheme().isTextShadowOn() && ColorHelper.getGrayValue(fc) > 128) {
					g.setColor(Color.black);
					JTattooUtilities.drawStringUnderlineCharAt(c, g, text, mnemIndex, textRect.x + 1,
							textRect.y + 1 + fm.getAscent());
				}
				g.setColor(fc);
				JTattooUtilities.drawStringUnderlineCharAt(c, g, text, mnemIndex, textRect.x,
						textRect.y + fm.getAscent());
			} else {
				g.setColor(Color.black);
				JTattooUtilities.drawStringUnderlineCharAt(c, g, text, mnemIndex, textRect.x + 1,
						textRect.y + 1 + fm.getAscent());
				g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
				JTattooUtilities.drawStringUnderlineCharAt(c, g, text, mnemIndex, textRect.x,
						textRect.y + fm.getAscent());
			}
		}
	}

} // end of class HiFiRadioButtonUI
