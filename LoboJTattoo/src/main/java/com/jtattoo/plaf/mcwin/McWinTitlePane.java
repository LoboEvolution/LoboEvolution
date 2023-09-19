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
package com.jtattoo.plaf.mcwin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JRootPane;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRootPaneUI;
import com.jtattoo.plaf.BaseTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>McWinTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinTitlePane extends BaseTitlePane {

	// ------------------------------------------------------------------------------
// inner classes
//------------------------------------------------------------------------------
	protected class TitlePaneLayout implements LayoutManager {

		@Override
		public void addLayoutComponent(String name, Component c) {
		}

		protected int computeHeight() {
			FontMetrics fm = JTattooUtilities.getFontMetrics(McWinTitlePane.this, null, getFont());
			return fm.getHeight() + 5;
		}

		@Override
		public void layoutContainer(Container c) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				layoutMacStyle(c);
			} else {
				layoutDefault(c);
			}
		}

		public void layoutDefault(Container c) {
			boolean leftToRight = isLeftToRight();

			int w = getWidth();
			int h = getHeight();

			// assumes all buttons have the same dimensions these dimensions include the
			// borders
			int spacing = getHorSpacing();
			int buttonHeight = h - getVerSpacing() - 1;
			int buttonWidth = buttonHeight;

			if (menuBar != null) {
				int mw = menuBar.getPreferredSize().width;
				int mh = menuBar.getPreferredSize().height;
				if (leftToRight) {
					menuBar.setBounds(2, (h - mh) / 2, mw, mh);
				} else {
					menuBar.setBounds(getWidth() - mw, (h - mh) / 2, mw, mh);
				}
			}

			int x = leftToRight ? w - spacing : 0;
			int y = Math.max(0, (h - buttonHeight) / 2 - 1);

			if (closeButton != null) {
				x += leftToRight ? -buttonWidth : spacing;
				closeButton.setBounds(x, y, buttonWidth, buttonHeight);
				if (!leftToRight) {
					x += buttonWidth;
				}
			}

			if (maxButton != null && maxButton.getParent() != null) {
				if (Toolkit.getDefaultToolkit().isFrameStateSupported(BaseRootPaneUI.MAXIMIZED_BOTH)) {
					x += leftToRight ? -spacing - buttonWidth : spacing;
					maxButton.setBounds(x, y, buttonWidth, buttonHeight);
					if (!leftToRight) {
						x += buttonWidth;
					}
				}
			}

			if (iconifyButton != null && iconifyButton.getParent() != null) {
				x += leftToRight ? -spacing - buttonWidth : spacing;
				iconifyButton.setBounds(x, y, buttonWidth, buttonHeight);
				if (!leftToRight) {
					x += buttonWidth;
				}
			}

			buttonsWidth = leftToRight ? w - x : x;

			if (customTitlePanel != null) {
				int maxWidth = w - buttonsWidth - spacing - 20;
				if (menuBar != null) {
					maxWidth -= menuBar.getPreferredSize().width;
					maxWidth -= spacing;
				}
				int cpw = Math.min(maxWidth, customTitlePanel.getPreferredSize().width);
				int cph = h;
				int cpx = leftToRight ? w - buttonsWidth - cpw : buttonsWidth;
				int cpy = 0;
				customTitlePanel.setBounds(cpx, cpy, cpw, cph);
				buttonsWidth += customTitlePanel.getPreferredSize().width;
			}
		}

		private void layoutMacStyle(Container c) {
			int spacing = getHorSpacing();
			int h = getHeight();

			// assumes all buttons have the same dimensions these dimensions include the
			// borders
			int btnHeight = h - getVerSpacing();
			int btnWidth = btnHeight;

			int x = 0;
			int y = 0;

			if (closeButton != null) {
				closeButton.setBounds(x, y, btnWidth, btnHeight);
				x += btnWidth + spacing;
			}
			if (iconifyButton != null && iconifyButton.getParent() != null) {
				iconifyButton.setBounds(x, y, btnWidth, btnHeight);
				x += btnWidth + spacing;
			}
			if (maxButton != null && maxButton.getParent() != null) {
				if (Toolkit.getDefaultToolkit().isFrameStateSupported(BaseRootPaneUI.MAXIMIZED_BOTH)) {
					maxButton.setBounds(x, y, btnWidth, btnHeight);
					x += btnWidth + spacing;
				}
			}

			buttonsWidth = x;

			if (customTitlePanel != null) {
				int cpx = buttonsWidth + 5;
				int cpy = 0;
				int cpw = customTitlePanel.getPreferredSize().width;
				int cph = h;
				customTitlePanel.setBounds(cpx, cpy, cpw, cph);
				buttonsWidth += cpw + 5;
			}
		}

		@Override
		public Dimension minimumLayoutSize(Container c) {
			return preferredLayoutSize(c);
		}

		@Override
		public Dimension preferredLayoutSize(Container c) {
			int height = computeHeight();
			return new Dimension(height, height);
		}

		@Override
		public void removeLayoutComponent(Component c) {
		}

	} // end fo class TitlePaneLayout

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for McWinTitlePane.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 * @param ui a {@link com.jtattoo.plaf.BaseRootPaneUI} object.
	 */
	public McWinTitlePane(JRootPane root, BaseRootPaneUI ui) {
		super(root, ui);
	}

	/** {@inheritDoc} */
	@Override
	public LayoutManager createLayout() {
		return new TitlePaneLayout();
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
		} else {
			g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveBorderColor());
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(Graphics g, int x, int y, String title) {
		if (isActive()) {
			Color fc = AbstractLookAndFeel.getWindowTitleForegroundColor();
			if (ColorHelper.getGrayValue(fc) > 128) {
				g.setColor(ColorHelper.darker(AbstractLookAndFeel.getWindowTitleColorDark(), 40));
			} else {
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getWindowTitleColorLight(), 40));
			}
			JTattooUtilities.drawString(rootPane, g, title, x, y + 1);
			g.setColor(fc);
			JTattooUtilities.drawString(rootPane, g, title, x, y);
		} else {
			Color fc = AbstractLookAndFeel.getWindowInactiveTitleForegroundColor();
			if (ColorHelper.getGrayValue(fc) > 128) {
				g.setColor(ColorHelper.darker(AbstractLookAndFeel.getWindowInactiveTitleColorDark(), 40));
			} else {
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getWindowInactiveTitleColorLight(), 40));
			}
			JTattooUtilities.drawString(rootPane, g, title, x, y + 1);
			g.setColor(fc);
			JTattooUtilities.drawString(rootPane, g, title, x, y);
		}
	}

} // end of class McWinTitlePane
