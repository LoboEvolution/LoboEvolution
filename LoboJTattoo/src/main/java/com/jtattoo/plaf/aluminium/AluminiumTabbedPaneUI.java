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
package com.jtattoo.plaf.aluminium;

import java.awt.Color;
import java.awt.Font;
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
 * <p>AluminiumTabbedPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumTabbedPaneUI extends BaseTabbedPaneUI {

	private static Color[] TOP_SELECTED_TAB_COLORS = null;
	private static Color[] BOTTOM_SELECTED_TAB_COLORS = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AluminiumTabbedPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getTabColors(int tabIndex, boolean isSelected, boolean isRollover) {
		Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (backColor instanceof UIResource && isSelected) {
			if (tabPane.getTabPlacement() == BOTTOM) {
				return BOTTOM_SELECTED_TAB_COLORS;
			} else {
				return TOP_SELECTED_TAB_COLORS;
			}
		}
		return super.getTabColors(tabIndex, isSelected, isRollover);
	}

	/** {@inheritDoc} */
	@Override
	protected Font getTabFont(boolean isSelected) {
		if (isSelected) {
			return super.getTabFont(isSelected).deriveFont(Font.BOLD);
		} else {
			return super.getTabFont(isSelected);
		}
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
		tabAreaInsets = new Insets(2, 6, 2, 6);
		contentBorderInsets = new Insets(0, 0, 0, 0);
		Color c = AbstractLookAndFeel.getTheme().getBackgroundColor();
		Color cHi = ColorHelper.brighter(c, 20);
		Color cLo = ColorHelper.darker(c, 10);
		TOP_SELECTED_TAB_COLORS = ColorHelper.createColorArr(cHi, c, 20);
		BOTTOM_SELECTED_TAB_COLORS = ColorHelper.createColorArr(c, cLo, 20);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (!(backColor instanceof UIResource)) {
			super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
			return;
		}
		if (JTattooUtilities.isMac() || !AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			if (isSelected) {
				Color[] colorArr = getTabColors(tabIndex, isSelected, tabIndex == rolloverIndex);
				switch (tabPlacement) {
				case LEFT:
					JTattooUtilities.fillHorGradient(g, colorArr, x + 1, y + 1, w + 1, h - 1);
					break;
				case RIGHT:
					JTattooUtilities.fillHorGradient(g, colorArr, x - 1, y + 1, w + 1, h - 1);
					break;
				case BOTTOM:
					JTattooUtilities.fillHorGradient(g, colorArr, x + 1, y - 1, w - 1, h);
					break;
				case TOP:
				default:
					JTattooUtilities.fillHorGradient(g, colorArr, x + 1, y + 1, w - 1, h + 1);
					break;
				}
			} else {
				super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
			}
		} else {
			if (isSelected) {
				if (tabPane.getBackgroundAt(tabIndex) instanceof UIResource) {
					g.setColor(AbstractLookAndFeel.getBackgroundColor());
					switch (tabPlacement) {
					case TOP:
						AluminiumUtils.fillComponent(g, tabPane, x + 1, y + 1, w - 1, h + 1);
						break;
					case LEFT:
						AluminiumUtils.fillComponent(g, tabPane, x + 1, y + 1, w + 1, h - 1);
						break;
					case BOTTOM:
						AluminiumUtils.fillComponent(g, tabPane, x + 1, y - 2, w - 1, h + 1);
						break;
					default:
						AluminiumUtils.fillComponent(g, tabPane, x - 1, y + 1, w + 1, h - 1);
						break;
					}
				} else {
					super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
				}
			} else {
				super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
			}
		}
	}

} // end of class AluminiumTabbedPaneUI
