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
package com.jtattoo.plaf.graphite;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>GraphiteTabbedPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class GraphiteTabbedPaneUI extends BaseTabbedPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new GraphiteTabbedPaneUI();
	}

	private Color[] sepColors = null;

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(final int tabPlacement) {
		if (sepColors == null) {
			sepColors = new Color[5];
			sepColors[0] = getLoBorderColor(0);
			sepColors[1] = AbstractLookAndFeel.getControlColorDark();
			sepColors[2] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 4);
			sepColors[3] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 8);
			sepColors[4] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 12);
		}
		return sepColors;
	}

	/** {@inheritDoc} */
	@Override
	protected Color getLoBorderColor(final int tabIndex) {
		if (tabIndex == tabPane.getSelectedIndex() && tabPane.getBackgroundAt(tabIndex) instanceof ColorUIResource) {
			return AbstractLookAndFeel.getControlColorDark();
		}
		return MetalLookAndFeel.getControlShadow();
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
	protected void paintFocusIndicator(final Graphics g, final int tabPlacement, final Rectangle[] rects, final int tabIndex,
                                       final Rectangle iconRect, final Rectangle textRect, final boolean isSelected) {
		if (tabPane.isRequestFocusEnabled() && tabPane.hasFocus() && isSelected && tabIndex >= 0
				&& textRect.width > 8) {
			g.setColor(AbstractLookAndFeel.getTheme().getFocusColor());
			BasicGraphicsUtils.drawDashedRect(g, textRect.x - 4, textRect.y, textRect.width + 8, textRect.height);
			BasicGraphicsUtils.drawDashedRect(g, textRect.x - 3, textRect.y + 1, textRect.width + 6,
					textRect.height - 2);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final int tabPlacement, final Font font, final FontMetrics metrics, final int tabIndex, final String title,
			final Rectangle textRect, final boolean isSelected) {
		if (AbstractLookAndFeel.getTheme().isTextShadowOn()) {
			if (isSelected && tabPane.isEnabledAt(tabIndex)
					&& tabPane.getBackgroundAt(tabIndex) instanceof ColorUIResource
					&& getTextViewForTab(tabIndex) == null) {
				g.setFont(font);
				final Color selColor = AbstractLookAndFeel.getTabSelectionForegroundColor();
				if (ColorHelper.getGrayValue(selColor) > 164) {
					g.setColor(Color.black);
				} else {
					g.setColor(Color.white);
				}
				final int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + 1 + metrics.getAscent());
			}
		}
		super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
	}

} // edn of class GraphiteTabbedPaneUI
