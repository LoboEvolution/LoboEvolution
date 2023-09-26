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
package com.jtattoo.plaf.fast;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * author Michael Hagen
 *
 *
 *
 */
public class FastTabbedPaneUI extends BaseTabbedPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new FastTabbedPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getTabColors(final int tabIndex, final boolean isSelected, final boolean isRollover) {
		Color[] colorArr = AbstractLookAndFeel.getTheme().getTabColors();
		if (tabIndex >= 0 && tabIndex < tabPane.getTabCount()) {
			final Color backColor = tabPane.getBackgroundAt(tabIndex);
			if (backColor instanceof UIResource) {
				if (isSelected) {
					colorArr = AbstractLookAndFeel.getTheme().getSelectedColors();
				} else {
					if (JTattooUtilities.isFrameActive(tabPane)) {
						colorArr = AbstractLookAndFeel.getTheme().getTabColors();
					} else {
						colorArr = AbstractLookAndFeel.getTheme().getInActiveColors();
					}
				}
			} else if (backColor != null) {
				colorArr = ColorHelper.createColorArr(backColor, backColor, 2);
			}
		}
		return colorArr;
	}

	/** {@inheritDoc} */
	@Override
	protected boolean hasInnerBorder() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults() {
		super.installDefaults();
		roundedTabs = false;
		tabAreaInsets = new Insets(2, 6, 2, 6);
		contentBorderInsets = new Insets(0, 0, 0, 0);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintTabBackground(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y, final int w, final int h,
			final boolean isSelected) {
		if (isTabOpaque() || isSelected) {
			final Color[] colorArr = getTabColors(tabIndex, isSelected, false);
			g.setColor(colorArr[0]);
			switch (tabPlacement) {
			case TOP:
				if (isSelected) {
					g.fillRect(x + 1, y + 1, w - 1, h + 2);
				} else {
					g.fillRect(x + 1, y + 1, w - 1, h - 1);
				}
				break;
			case LEFT:
				if (isSelected) {
					g.fillRect(x + 1, y + 1, w + 2, h - 1);
				} else {
					g.fillRect(x + 1, y + 1, w - 1, h - 1);
				}
				break;
			case BOTTOM:
				if (isSelected) {
					g.fillRect(x + 1, y - 2, w - 1, h + 1);
				} else {
					g.fillRect(x + 1, y, w - 1, h - 1);
				}
				break;
			case RIGHT:
				if (isSelected) {
					g.fillRect(x - 2, y + 1, w + 2, h - 1);
				} else {
					g.fillRect(x, y + 1, w, h - 1);
				}
				break;
			default:
				break;
			}
		}
	}

} // end of class FastTabbedPaneUI
