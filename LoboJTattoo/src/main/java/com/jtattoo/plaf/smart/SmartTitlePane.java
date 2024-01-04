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

package com.jtattoo.plaf.smart;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JRootPane;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRootPaneUI;
import com.jtattoo.plaf.BaseTitleButton;
import com.jtattoo.plaf.BaseTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>SmartTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class SmartTitlePane extends BaseTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for SmartTitlePane.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 * @param ui a {@link com.jtattoo.plaf.BaseRootPaneUI} object.
	 */
	public SmartTitlePane(final JRootPane root, final BaseRootPaneUI ui) {
		super(root, ui);
	}

	/** {@inheritDoc} */
	@Override
	public void createButtons() {
		iconifyButton = new BaseTitleButton(iconifyAction, ICONIFY, iconifyIcon, 1.0f);
		maxButton = new BaseTitleButton(restoreAction, MAXIMIZE, maximizeIcon, 1.0f);
		closeButton = new BaseTitleButton(closeAction, CLOSE, closeIcon, 1.0f);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(final Graphics g) {
		final Graphics2D g2D = (Graphics2D) g;
		final int width = getWidth();
		final int height = getHeight();
		final Color backColor;
		final Color frameColor;
		if (JTattooUtilities.isActive(this)) {
			backColor = AbstractLookAndFeel.getTheme().getWindowTitleColors()[10];
			frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 0, 0, width,
					height);
		} else {
			backColor = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors()[10];
			frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 40);
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 0, 0,
					width, height);
		}

		int iconWidth = 0;
		if (menuBar != null) {
			iconWidth = menuBar.getWidth() + 5;
		}

		int titleWidth = 0;
		final String frameTitle = getTitle();
		if (frameTitle != null) {
			final Font f = getFont();
			g.setFont(f);
			final FontMetrics fm = g.getFontMetrics();
			titleWidth = fm.stringWidth(
					JTattooUtilities.getClippedText(getTitle(), fm, getWidth() - iconWidth - buttonsWidth - 15)) + 10;
			if (getWindow() instanceof JDialog) {
				final Image image = getFrameIconImage();
				if (image != null) {
					titleWidth += getHeight();
				}
			}
		}

		final int dx;
		final int dw;
		final boolean leftToRight = isLeftToRight();
		final int xOffset = leftToRight ? iconWidth + 10 + titleWidth : width - 10 - iconWidth - titleWidth;

		if (leftToRight) {
			dw = width - buttonsWidth - xOffset - 10;
			dx = xOffset;
		} else {
			dw = xOffset - buttonsWidth - 10;
			dx = buttonsWidth + 10;
		}
		int dy = 3;

		if (!AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()
				&& !AbstractLookAndFeel.getTheme().isCenterWindowTitleOn() && dw > 0) {
			final Composite composite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g2D.setComposite(alpha);

			final float dc1 = 50.0f;
			float dc2 = 5.0f;

			final Color c1 = ColorHelper.brighter(backColor, dc1);
			Color c2;
			while (dy + 5 < height) {
				c2 = ColorHelper.darker(backColor, dc2);
				dc2 += 5.0f;
				g.setColor(c1);
				g.drawLine(dx, dy, dx + dw, dy);
				dy++;
				g.setColor(c2);
				g.drawLine(dx, dy, dx + dw, dy);
				dy += 3;
			}
			g2D.setComposite(composite);
		}
		g.setColor(frameColor);
		g.drawLine(0, height - 1, width, height - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getTheme().getFrameColor());
		} else {
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 40));
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(final Graphics g, final int x, final int y, final String title) {
		if (isActive()) {
			final Color titleColor = AbstractLookAndFeel.getWindowTitleForegroundColor();
			if (ColorHelper.getGrayValue(titleColor) > 164) {
				g.setColor(Color.black);
				JTattooUtilities.drawString(rootPane, g, title, x + 1, y + 1);
			}
			g.setColor(titleColor);
			JTattooUtilities.drawString(rootPane, g, title, x, y);
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
			JTattooUtilities.drawString(rootPane, g, title, x, y);
		}
	}

} // end of class SmartTitlePane
