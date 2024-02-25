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

package com.jtattoo.plaf.acryl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.text.View;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * author Michael Hagen *
 */
public class AcrylTabbedPaneUI extends BaseTabbedPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AcrylTabbedPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	protected Color getContentBorderColor() {
		return ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 50);
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(final int tabPlacement) {
		final Color[] SEP_COLORS = { ColorHelper.brighter(AbstractLookAndFeel.getControlColorLight(), 20),
				AbstractLookAndFeel.getControlColorLight(),
				ColorHelper.brighter(AbstractLookAndFeel.getControlColorDark(), 20),
				AbstractLookAndFeel.getControlColorDark(),
				ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 20) };
		return SEP_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	protected Color getLoBorderColor(final int tabIndex) {
		if (tabIndex == tabPane.getSelectedIndex() && tabPane.getBackgroundAt(tabIndex) instanceof ColorUIResource) {
			return ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 10);
		}
		return super.getLoBorderColor(tabIndex);
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getTabColors(final int tabIndex, final boolean isSelected, final boolean isRollover) {
		if (tabIndex >= 0 && tabIndex < tabPane.getTabCount()) {
			final boolean isEnabled = tabPane.isEnabledAt(tabIndex);
			final Color backColor = tabPane.getBackgroundAt(tabIndex);
			final Color[] colorArr;
			if (backColor instanceof UIResource) {
				if (isSelected) {
					colorArr = AbstractLookAndFeel.getTheme().getDefaultColors();
				} else if (isRollover && isEnabled) {
					colorArr = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else {
					colorArr = AbstractLookAndFeel.getTheme().getTabColors();
				}
			} else {
				if (isSelected) {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 60), backColor, 20);
				} else if (isRollover && isEnabled) {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 80),
							ColorHelper.brighter(backColor, 20), 20);
				} else {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 40),
							ColorHelper.darker(backColor, 10), 20);
				}
			}
			return colorArr;
		}
		return AbstractLookAndFeel.getTheme().getTabColors();
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
	public void installDefaults() {
		super.installDefaults();
		tabAreaInsets.bottom = 5;
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

			if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
				if (isSelected) {
					final Color shadowColor = ColorHelper.darker(AbstractLookAndFeel.getWindowTitleColorDark(), 30);
					g.setColor(shadowColor);
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,
							textRect.y - 1 + metrics.getAscent());
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,
							textRect.y + 1 + metrics.getAscent());
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1,
							textRect.y - 1 + metrics.getAscent());
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1,
							textRect.y + 1 + metrics.getAscent());
					g.setColor(AbstractLookAndFeel.getTheme().getTabSelectionForegroundColor());
				} else {
					g.setColor(tabPane.getForegroundAt(tabIndex));
				}
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());

			} else { // tab disabled
				g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());
				g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,
						textRect.y + metrics.getAscent() - 1);
			}
		}
	}

} // end of class AcrylTabbedPaneUI
