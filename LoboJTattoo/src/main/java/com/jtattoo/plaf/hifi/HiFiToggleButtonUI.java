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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseToggleButtonUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>HiFiToggleButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiToggleButtonUI extends BaseToggleButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new HiFiToggleButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final AbstractButton b, final Rectangle textRect, final String text) {
		final ButtonModel model = b.getModel();
		final FontMetrics fm = JTattooUtilities.getFontMetrics(b, g, b.getFont());
		final int mnemIndex = b.getDisplayedMnemonicIndex();
		int offs = 0;
		if (model.isArmed() && model.isPressed()) {
			offs = 1;
		}

		final Graphics2D g2D = (Graphics2D) g;
		final Composite composite = g2D.getComposite();
		final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
		g2D.setComposite(alpha);
		Color foreground = b.getForeground();
		final Color background = b.getBackground();
		if (model.isPressed() && model.isArmed() || model.isSelected()) {
			if (foreground instanceof ColorUIResource && background instanceof ColorUIResource) {
				foreground = AbstractLookAndFeel.getTheme().getPressedForegroundColor();
			}
		}
		if (!model.isEnabled()) {
			foreground = AbstractLookAndFeel.getTheme().getDisabledForegroundColor();
		}
		if (ColorHelper.getGrayValue(foreground) > 128) {
			g2D.setColor(Color.black);
		} else {
			g2D.setColor(Color.white);
		}
		JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs + 1,
				textRect.y + offs + fm.getAscent() + 1);
		g2D.setComposite(composite);
		g2D.setColor(foreground);
		JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs,
				textRect.y + offs + fm.getAscent());
	}

} // end of class HiFiToggleButtonUI
