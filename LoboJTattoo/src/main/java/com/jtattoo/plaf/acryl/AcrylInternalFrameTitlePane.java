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

package com.jtattoo.plaf.acryl;

import java.awt.Color;
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
 * <p>AcrylInternalFrameTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AcrylInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	// --------------------------------------------------------------------------------------------
	class BaseTitlePaneLayout extends TitlePaneLayout {

		@Override
		public void addLayoutComponent(final String name, final Component c) {
		}

		@Override
		public void layoutContainer(final Container c) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				layoutMacStyle(c);
			} else {
				layoutDefault(c);
			}
		}

		public void layoutDefault(final Container c) {
			final boolean leftToRight = JTattooUtilities.isLeftToRight(frame);

			final int spacing = getHorSpacing();
			final int w = getWidth();
			final int h = getHeight();

			// assumes all buttons have the same dimensions these dimensions include the
			// borders
			final int btnHeight = h - getVerSpacing();
			final int btnWidth = btnHeight + 10;

			int x = leftToRight ? w - spacing : 0;
			final int y = Math.max(0, (h - btnHeight) / 2 - 1);

			if (frame.isClosable()) {
				x += leftToRight ? -btnWidth : spacing;
				closeButton.setBounds(x, y, btnWidth, btnHeight);
				if (!leftToRight) {
					x += btnWidth;
				}
			}

			if (frame.isMaximizable() && !isPalette) {
				x += leftToRight ? -spacing - btnWidth : spacing;
				maxButton.setBounds(x, y, btnWidth, btnHeight);
				if (!leftToRight) {
					x += btnWidth;
				}
			}

			if (frame.isIconifiable() && !isPalette) {
				x += leftToRight ? -spacing - btnWidth : spacing;
				iconButton.setBounds(x, y, btnWidth, btnHeight);
				if (!leftToRight) {
					x += btnWidth;
				}
			}

			buttonsWidth = leftToRight ? w - x : x;

			if (customTitlePanel != null) {
				int maxWidth = w - buttonsWidth - spacing - 20;
				final Icon icon = frame.getFrameIcon();
				if (icon != null) {
					maxWidth -= icon.getIconWidth();
					maxWidth -= spacing;
				}
				final int cpw = Math.min(maxWidth, customTitlePanel.getPreferredSize().width);
				final int cph = h;
				final int cpx = leftToRight ? w - buttonsWidth - cpw : buttonsWidth;
				final int cpy = 0;
				customTitlePanel.setBounds(cpx, cpy, cpw, cph);
				buttonsWidth += customTitlePanel.getPreferredSize().width;
			}
		}

		private void layoutMacStyle(final Container c) {
			final int spacing = getHorSpacing();
			final int h = getHeight();

			// assumes all buttons have the same dimensions these dimensions include the
			// borders
			final int btnHeight = h - getVerSpacing();
			final int btnWidth = btnHeight;

			int x = 0;
			final int y = 0;

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
				final int cpx = buttonsWidth + 5;
				final int cpy = 0;
				final int cpw = customTitlePanel.getPreferredSize().width;
				final int cph = h;
				customTitlePanel.setBounds(cpx, cpy, cpw, cph);
				buttonsWidth += cpw + 5;
			}
		}

		@Override
		public Dimension minimumLayoutSize(final Container c) {
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
			final FontMetrics fm = JTattooUtilities.getFontMetrics(AcrylInternalFrameTitlePane.this, null, getFont());
			final String frameTitle = frame.getTitle();
			final int title_w = frameTitle != null ? fm.stringWidth(frameTitle) : 0;
			final int title_length = frameTitle != null ? frameTitle.length() : 0;

			if (title_length > 2) {
				final int subtitle_w = fm.stringWidth(frame.getTitle().substring(0, 2) + "...");
				width += Math.min(title_w, subtitle_w);
			} else {
				width += title_w;
			}

			int height = paletteTitleHeight;
			if (!isPalette) {
				final int fontHeight = fm.getHeight() + 5;
				final Icon icon = isMacStyleWindowDecoration() ? null : frame.getFrameIcon();
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
		public Dimension preferredLayoutSize(final Container c) {
			return minimumLayoutSize(c);
		}

	} // end class BaseTitlePaneLayout

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for AcrylInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public AcrylInternalFrameTitlePane(final JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	protected LayoutManager createLayout() {
		return new BaseTitlePaneLayout();
	}

	/** {@inheritDoc} */
	@Override
	protected int getHorSpacing() {
		return 1;
	}

	/** {@inheritDoc} */
	@Override
	protected int getVerSpacing() {
		return 3;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowBorderColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveBorderColor());
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(final Graphics g, final int x, final int y, final String title) {
		Color shadowColor = AbstractLookAndFeel.getWindowTitleColorDark();
		if (isActive()) {
			shadowColor = ColorHelper.darker(shadowColor, 30);
		}
		g.setColor(shadowColor);
		JTattooUtilities.drawString(frame, g, title, x - 1, y - 2);
		JTattooUtilities.drawString(frame, g, title, x - 1, y);
		JTattooUtilities.drawString(frame, g, title, x + 1, y - 2);
		JTattooUtilities.drawString(frame, g, title, x + 1, y);
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
		}
		JTattooUtilities.drawString(frame, g, title, x, y - 1);
	}

} // end of class AcrylInternalFrameTitlePane
