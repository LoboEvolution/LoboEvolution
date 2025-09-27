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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.text.View;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>HiFiTabbedPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiTabbedPaneUI extends BaseTabbedPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new HiFiTabbedPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(final int tabPlacement) {
		final Color[] SEP_COLORS = { ColorHelper.darker(AbstractLookAndFeel.getBackgroundColor(), 40),
				ColorHelper.brighter(AbstractLookAndFeel.getBackgroundColor(), 20),
				ColorHelper.darker(AbstractLookAndFeel.getBackgroundColor(), 20),
				ColorHelper.darker(AbstractLookAndFeel.getBackgroundColor(), 40),
				ColorHelper.darker(AbstractLookAndFeel.getBackgroundColor(), 60), };
		return SEP_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final int tabPlacement, final Font font, final FontMetrics metrics, final int tabIndex, final String title,
			final Rectangle textRect, final boolean isSelected) {
		final Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (!(backColor instanceof UIResource)) {
			super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
			return;
		}
		g.setFont(font);
		final View v = getTextViewForTab(tabIndex);
		if (v != null) {
			// html
			final Graphics2D g2D = (Graphics2D) g;
			Object savedRenderingHint = null;
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
			}
			v.paint(g, textRect);
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
			}
		} else {
			// plain text
			final int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

			final Graphics2D g2D = (Graphics2D) g;
			final Composite composite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g2D.setComposite(alpha);
			Color fc = tabPane.getForegroundAt(tabIndex);
			if (isSelected) {
				fc = AbstractLookAndFeel.getTheme().getTabSelectionForegroundColor();
			}
			if (!tabPane.isEnabled() || !tabPane.isEnabledAt(tabIndex)) {
				fc = AbstractLookAndFeel.getTheme().getDisabledForegroundColor();
			}
			if (ColorHelper.getGrayValue(fc) > 128) {
				g2D.setColor(Color.black);
			} else {
				g2D.setColor(Color.white);
			}
			JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1,
					textRect.y + 1 + metrics.getAscent());
			g2D.setComposite(composite);
			g2D.setColor(fc);
			JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
					textRect.y + metrics.getAscent());
		}
	}

} // end of class HiFiTabbedPaneUI
