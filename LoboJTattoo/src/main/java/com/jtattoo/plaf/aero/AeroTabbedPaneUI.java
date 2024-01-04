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

package com.jtattoo.plaf.aero;

import java.awt.Color;
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
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AeroTabbedPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AeroTabbedPaneUI extends BaseTabbedPaneUI {
	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AeroTabbedPaneUI();
	}

	private Color[] sepColors = null;

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(final int tabPlacement) {
		if (sepColors == null) {
			sepColors = new Color[5];
			sepColors[0] = ColorHelper.brighter(AbstractLookAndFeel.getControlColorDark(), 40);
			sepColors[1] = ColorHelper.brighter(AbstractLookAndFeel.getControlColorLight(), 40);
			sepColors[2] = ColorHelper.brighter(AbstractLookAndFeel.getControlColorLight(), 60);
			sepColors[3] = ColorHelper.brighter(AbstractLookAndFeel.getControlColorLight(), 20);
			sepColors[4] = ColorHelper.brighter(AbstractLookAndFeel.getControlColorDark(), 30);
		}
		return sepColors;
	}

	/** {@inheritDoc} */
	@Override
	protected Font getTabFont(final boolean isSelected) {
		if (isSelected) {
			return super.getTabFont(isSelected).deriveFont(Font.BOLD);
		} else {
			return super.getTabFont(isSelected);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void installComponents() {
		simpleButtonBorder = true;
		super.installComponents();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final int tabPlacement, final Font font, final FontMetrics metrics,
							 final int tabIndex, final String title,
							 final Rectangle textRect, final boolean isSelected) {
		final Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (!(backColor instanceof UIResource)) {
			super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
		} else {
			g.setFont(font);
			final View v = getTextViewForTab(tabIndex);
			if (v != null) {
				// html
				final Graphics2D g2D = (Graphics2D) g;
				Object savedRenderingHint = null;
				if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
					savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
					g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
				}
				v.paint(g, textRect);
				if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
					g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
				}
			} else {
				// plain text
				final int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

				if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
					if (isSelected) {
						final Color titleColor = AbstractLookAndFeel.getTabSelectionForegroundColor();
						if (ColorHelper.getGrayValue(titleColor) > 164) {
							g.setColor(Color.black);
						} else {
							g.setColor(Color.white);
						}
						JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1,textRect.y + 1 + metrics.getAscent());
						g.setColor(titleColor);
					} else {
						g.setColor(tabPane.getForegroundAt(tabIndex));
					}
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,textRect.y + metrics.getAscent());

				} else { // tab disabled
					g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,textRect.y + metrics.getAscent());
					g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,textRect.y + metrics.getAscent() - 1);
				}
			}
		}
	}

} // end of class AeroTabbedPaneUI
