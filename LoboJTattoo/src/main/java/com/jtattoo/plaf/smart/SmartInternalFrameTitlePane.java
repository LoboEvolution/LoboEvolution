/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>SmartInternalFrameTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class SmartInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for SmartInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public SmartInternalFrameTitlePane(final JInternalFrame f) {
		super(f);
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
		final Icon icon = frame.getFrameIcon();
		if (icon != null) {
			iconWidth = icon.getIconWidth() + 5;
		}

		int titleWidth = 0;
		final String frameTitle = frame.getTitle();
		if (frameTitle != null) {
			g.setFont(getFont());
			final FontMetrics fm = JTattooUtilities.getFontMetrics(this, g, getFont());
			titleWidth = fm.stringWidth(
					JTattooUtilities.getClippedText(frame.getTitle(), fm, getWidth() - iconWidth - buttonsWidth - 15))
					+ 10;
		}

		final int dx;
		final int dw;
		final boolean leftToRight = JTattooUtilities.isLeftToRight(frame);
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
	}

	/** {@inheritDoc} */
	@Override
	public void paintPalette(final Graphics g) {
		final int width = getWidth();
		final int height = getHeight();

		final Color backColor;
		final Color frameColor;
		if (JTattooUtilities.isFrameActive(this)) {
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 0, 0, width,
					height);
			backColor = AbstractLookAndFeel.getTheme().getWindowTitleColors()[10];
			frameColor = ColorHelper.darker(AbstractLookAndFeel.getWindowTitleColorDark(), 15);
		} else {
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 0, 0,
					width, height);
			backColor = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors()[10];
			frameColor = ColorHelper.darker(AbstractLookAndFeel.getWindowInactiveTitleColorDark(), 15);
		}
		g.setColor(frameColor);
		g.drawLine(0, height - 1, width, height - 1);

		final int dx = 8;
		int dy = 2;
		final int dw = width - buttonsWidth - 2 * 8;
		final Graphics2D g2D = (Graphics2D) g;
		final Composite composite = g2D.getComposite();
		final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
		g2D.setComposite(alpha);

		final float dc1 = 50.0f;
		float dc2 = 5.0f;
		final Color c1 = ColorHelper.brighter(backColor, dc1);
		Color c2;
		while (dy + 2 < height) {
			c2 = ColorHelper.darker(backColor, dc2);
			dc2 += 5.0f;
			g.setColor(c1);
			g.drawLine(dx, dy, dx + dw, dy);
			dy++;
			g.setColor(c2);
			g.drawLine(dx, dy, dx + dw, dy);
			dy += 2;
		}
		g2D.setComposite(composite);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(final Graphics g, final int x, final int y, final String title) {
		if (isActive()) {
			final Color titleColor = AbstractLookAndFeel.getWindowTitleForegroundColor();
			if (ColorHelper.getGrayValue(titleColor) > 164) {
				g.setColor(Color.black);
				JTattooUtilities.drawString(frame, g, title, x + 1, y);
			}
			g.setColor(titleColor);
			JTattooUtilities.drawString(frame, g, title, x, y - 1);
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
			JTattooUtilities.drawString(frame, g, title, x, y - 1);
		}
	}

} // end of class SmartInternalFrameTitlePane
