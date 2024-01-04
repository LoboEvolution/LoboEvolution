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
package com.jtattoo.plaf.luna;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;

/**
 * author Michael Hagen
 *
 *
 *
 */
public class LunaTabbedPaneUI extends BaseTabbedPaneUI {

	private static final Color[] SELECTED_TAB_COLOR = new Color[] { AbstractLookAndFeel.getBackgroundColor() };
	private static final Color[] SEP_COLOR = new Color[] { AbstractLookAndFeel.getControlDarkShadow() };

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new LunaTabbedPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(final int tabPlacement) {
		return SEP_COLOR;
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getTabColors(final int tabIndex, final boolean isSelected, final boolean isRollover) {
		if (isSelected && tabPane.getBackgroundAt(tabIndex) instanceof UIResource) {
			return SELECTED_TAB_COLOR;
		} else {
			return super.getTabColors(tabIndex, isSelected, isRollover);
		}
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
	protected boolean hasInnerBorder() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	protected void installComponents() {
		simpleButtonBorder = true;
		super.installComponents();
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults() {
		super.installDefaults();
		tabAreaInsets = new Insets(2, 6, 2, 6);
		contentBorderInsets = new Insets(0, 0, 0, 0);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintTabBackground(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y, final int w, final int h,
			final boolean isSelected) {
		final Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (isSelected && backColor instanceof UIResource) {
			if (tabPane.getBackgroundAt(tabIndex) instanceof UIResource) {
				g.setColor(AbstractLookAndFeel.getBackgroundColor());
			} else {
				g.setColor(tabPane.getBackgroundAt(tabIndex));
			}
			switch (tabPlacement) {
			case TOP:
				g.fillRect(x + 1, y + 1, w - 1, h + 2);
				break;
			case LEFT:
				g.fillRect(x + 1, y + 1, w + 2, h - 1);
				break;
			case BOTTOM:
				g.fillRect(x + 1, y - 2, w - 1, h + 2);
				break;
			default:
				g.fillRect(x - 2, y + 1, w + 2, h - 1);
				break;
			}
		} else {
			super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
			if (!isSelected && tabIndex == rolloverIndex && tabPane.isEnabledAt(tabIndex)) {
				g.setColor(AbstractLookAndFeel.getFocusColor());
				switch (tabPlacement) {
				case TOP:
					g.fillRect(x + 2, y + 1, w - 3, 2);
					break;
				case LEFT:
					g.fillRect(x, y + 1, w - 1, 2);
					break;
				case BOTTOM:
					g.fillRect(x + 2, y + h - 3, w - 3, 2);
					break;
				default:
					g.fillRect(x, y + 1, w - 1, 2);
					break;
				}
			}
		}
	}

} // end of class LunaTabbedPaneUI
