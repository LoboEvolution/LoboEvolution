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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.Icon;
import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>McWinInternalFrameTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	// ------------------------------------------------------------------------------
// inner classes
//------------------------------------------------------------------------------
	class MyTitlePaneLayout extends TitlePaneLayout {

		@Override
		public void addLayoutComponent(String name, Component c) {
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
			boolean leftToRight = JTattooUtilities.isLeftToRight(frame);

			int spacing = getHorSpacing();
			int w = getWidth();
			int h = getHeight();

			// assumes all buttons have the same dimensions these dimensions include the
			// borders
			int buttonHeight = h - getVerSpacing() - 1;
			int buttonWidth = buttonHeight;

			int x = leftToRight ? w - spacing : 0;
			int y = Math.max(0, (h - buttonHeight) / 2 - 1);

			if (frame.isClosable()) {
				x += leftToRight ? -buttonWidth : spacing;
				closeButton.setBounds(x, y, buttonWidth, buttonHeight);
				if (!leftToRight) {
					x += buttonWidth;
				}
			}

			if (frame.isMaximizable() && !isPalette) {
				x += leftToRight ? -spacing - buttonWidth : spacing;
				maxButton.setBounds(x, y, buttonWidth, buttonHeight);
				if (!leftToRight) {
					x += buttonWidth;
				}
			}

			if (frame.isIconifiable() && !isPalette) {
				x += leftToRight ? -spacing - buttonWidth : spacing;
				iconButton.setBounds(x, y, buttonWidth, buttonHeight);
				if (!leftToRight) {
					x += buttonWidth;
				}
			}

			buttonsWidth = leftToRight ? w - x : x;

			if (customTitlePanel != null) {
				int maxWidth = w - buttonsWidth - spacing - 20;
				Icon icon = frame.getFrameIcon();
				if (icon != null) {
					maxWidth -= icon.getIconWidth();
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

			if (frame.isClosable()) {
				closeButton.setBounds(x, y, btnWidth, btnHeight);
				x += btnWidth + spacing;
			}
			if (frame.isIconifiable() && !isPalette) {
				iconButton.setBounds(x, y, btnWidth, btnHeight);
				x += btnWidth + spacing;
			}
			if (frame.isMaximizable() && !isPalette) {
				maxButton.setBounds(x, y, btnWidth, btnHeight);
				x += btnWidth + spacing;
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
			int width = 30;
			if (frame.isClosable()) {
				width += 21;
			}
			if (frame.isMaximizable()) {
				width += 16 + (frame.isClosable() ? 10 : 4);
			}
			if (frame.isIconifiable()) {
				width += 16 + (frame.isMaximizable() ? 2 : frame.isClosable() ? 10 : 4);
			}
			FontMetrics fm = JTattooUtilities.getFontMetrics(McWinInternalFrameTitlePane.this, null, getFont());
			String frameTitle = frame.getTitle();
			int title_w = frameTitle != null ? fm.stringWidth(frameTitle) : 0;
			int title_length = frameTitle != null ? frameTitle.length() : 0;

			if (title_length > 2) {
				int subtitle_w = fm.stringWidth(frame.getTitle().substring(0, 2) + "...");
				width += title_w < subtitle_w ? title_w : subtitle_w;
			} else {
				width += title_w;
			}

			int height = paletteTitleHeight;
			if (!isPalette) {
				int fontHeight = fm.getHeight() + 5;
				Icon icon = isMacStyleWindowDecoration() ? null : frame.getFrameIcon();
				int iconHeight = 0;
				if (icon != null) {
					iconHeight = Math.min(icon.getIconHeight(), 18);
				}
				iconHeight += 5;
				height = Math.max(fontHeight, iconHeight);
			}
			return new Dimension(width, height);
		}

		@Override
		public Dimension preferredLayoutSize(Container c) {
			return minimumLayoutSize(c);
		}

		@Override
		public void removeLayoutComponent(Component c) {
		}

	} // end of class MyTitlePaneLayout

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for McWinInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public McWinInternalFrameTitlePane(JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	protected LayoutManager createLayout() {
		return new MyTitlePaneLayout();
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		if (JTattooUtilities.isActive(this)) {
			g.setColor(AbstractLookAndFeel.getWindowBorderColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveBorderColor());
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(Graphics g, int x, int y, String title) {
		if (isActive()) {
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getWindowTitleColorLight(), 40));
			JTattooUtilities.drawString(frame, g, title, x, y - 1);
			g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
			JTattooUtilities.drawString(frame, g, title, x, y - 2);
		} else {
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getWindowInactiveTitleColorLight(), 40));
			JTattooUtilities.drawString(frame, g, title, x, y - 1);
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
			JTattooUtilities.drawString(frame, g, title, x, y - 2);
		}
	}

} // end of class McWinInternalFrameTitlePane
