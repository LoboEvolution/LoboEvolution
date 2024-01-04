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

package com.jtattoo.plaf;

import java.awt.Adjustable;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * <p>XPScrollBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class XPScrollBarUI extends BaseScrollBarUI {

	/** Constant rolloverColors */
	protected static Color[] rolloverColors = null;
	/** Constant dragColors */
	protected static Color[] dragColors = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new XPScrollBarUI();
	}

	/**
	 * <p>getFrameColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	protected Color getFrameColor() {
		return Color.white;
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getThumbColors() {
		if (isDragging) {
			return dragColors;
		}
		if (isRollover) {
			return rolloverColors;
		}
		return AbstractLookAndFeel.getTheme().getThumbColors();
	}

	/** {@inheritDoc} */
	@Override
	protected void installDefaults() {
		super.installDefaults();
		final Color[] colors = AbstractLookAndFeel.getTheme().getThumbColors();
		rolloverColors = new Color[colors.length];
		dragColors = new Color[colors.length];
		for (int i = 0; i < colors.length; i++) {
			rolloverColors[i] = ColorHelper.brighter(colors[i], 16);
			dragColors[i] = ColorHelper.darker(colors[i], 8);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintThumb(final Graphics g, final JComponent c, final Rectangle thumbBounds) {
		if (!c.isEnabled()) {
			return;
		}

		final Graphics2D g2D = (Graphics2D) g;
		final Composite savedComposite = g2D.getComposite();

		final int x = thumbBounds.x;
		final int y = thumbBounds.y;
		final int width = thumbBounds.width;
		final int height = thumbBounds.height;

		g.translate(x, y);

		final Color[] colors = getThumbColors();
		if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
			JTattooUtilities.fillVerGradient(g, colors, 0, 0, width, height);
			if (!AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
				final int dx = 6;
				int dy = height / 2 - 3;
				final int dw = width - 13;
				final Color c1 = ColorHelper.brighter(colors[0], 60);
				final Color c2 = ColorHelper.darker(colors[0], 30);
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
				g2D.setComposite(alpha);
				for (int i = 0; i < 4; i++) {
					g.setColor(c1);
					g.drawLine(dx, dy, dx + dw, dy);
					dy++;
					g.setColor(c2);
					g.drawLine(dx, dy, dx + dw, dy);
					dy++;
				}
			}
		} else {
			JTattooUtilities.fillHorGradient(g, colors, 0, 0, width, height);
			if (!AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
				int dx = width / 2 - 3;
				final int dy = 6;
				final int dh = height - 13;
				final Color c1 = ColorHelper.brighter(colors[0], 60);
				final Color c2 = ColorHelper.darker(colors[0], 30);
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
				g2D.setComposite(alpha);
				for (int i = 0; i < 4; i++) {
					g.setColor(c1);
					g.drawLine(dx, dy, dx, dy + dh);
					dx++;
					g.setColor(c2);
					g.drawLine(dx, dy, dx, dy + dh);
					dx++;
				}
			}
		}

		g.setColor(getFrameColor());
		g.drawLine(1, 1, width - 2, 1);
		g.drawLine(1, 2, 1, height - 3);
		g.drawLine(width - 2, 2, width - 2, height - 3);
		g.drawLine(2, height - 2, width - 3, height - 2);

		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g2D.setComposite(alpha);
		final Color fc = colors[colors.length - 1];
		g2D.setColor(fc);
		g.drawLine(2, 2, width - 3, 2);
		g.drawLine(2, 3, 2, height - 3);

		g.setColor(ColorHelper.darker(fc, 40));
		g.drawLine(width - 1, 2, width - 1, height - 3);
		g.drawLine(3, height - 1, width - 3, height - 1);
		alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
		g2D.setComposite(alpha);
		g.drawLine(1, height - 2, 2, height - 1);
		g.drawLine(width - 1, height - 2, width - 2, height - 1);

		g.translate(-x, -y);
		g2D.setComposite(savedComposite);
	}

} // end of class XPScrollBarUI
