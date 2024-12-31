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

package com.jtattoo.plaf.acryl;

import java.awt.Adjustable;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseScrollBarUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AcrylScrollBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AcrylScrollBarUI extends BaseScrollBarUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AcrylScrollBarUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintThumb(final Graphics g, final JComponent c, final Rectangle thumbBounds) {
		if (!c.isEnabled()) {
			return;
		}

		g.translate(thumbBounds.x, thumbBounds.y);

		final Color[] colors = getThumbColors();
		Color frameColor = AbstractLookAndFeel.getFrameColor();
		if (!JTattooUtilities.isActive(scrollbar)) {
			frameColor = ColorHelper.brighter(frameColor, 80);
		}
		if (isRollover || isDragging) {
			frameColor = AbstractLookAndFeel.getTheme().getRolloverColorDark();
		}

		if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
			JTattooUtilities.fillVerGradient(g, colors, 1, 1, thumbBounds.width - 1, thumbBounds.height - 1);

			JTattooUtilities.draw3DBorder(g, frameColor, ColorHelper.darker(frameColor, 15), 0, 0, thumbBounds.width,
					thumbBounds.height);

			final Graphics2D g2D = (Graphics2D) g;
			final Composite composite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g2D.setComposite(alpha);

			if (!AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
				final int dx = 5;
				int dy = thumbBounds.height / 2 - 3;
				final int dw = thumbBounds.width - 11;

				final Color c1 = Color.white;
				final Color c2 = Color.darkGray;

				for (int i = 0; i < 4; i++) {
					g.setColor(c1);
					g.drawLine(dx, dy, dx + dw, dy);
					dy++;
					g.setColor(c2);
					g.drawLine(dx, dy, dx + dw, dy);
					dy++;
				}
			}
			g2D.setComposite(composite);
		} else { // HORIZONTAL
			JTattooUtilities.fillHorGradient(g, colors, 1, 1, thumbBounds.width - 1, thumbBounds.height - 1);
			JTattooUtilities.draw3DBorder(g, frameColor, ColorHelper.darker(frameColor, 10), 0, 0, thumbBounds.width,
					thumbBounds.height);

			int dx = thumbBounds.width / 2 - 3;
			final int dy = 5;
			final int dh = thumbBounds.height - 11;

			final Graphics2D g2D = (Graphics2D) g;
			final Composite composite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g2D.setComposite(alpha);

			if (!AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
				final Color c1 = Color.white;
				final Color c2 = Color.darkGray;

				for (int i = 0; i < 4; i++) {
					g.setColor(c1);
					g.drawLine(dx, dy, dx, dy + dh);
					dx++;
					g.setColor(c2);
					g.drawLine(dx, dy, dx, dy + dh);
					dx++;
				}
			}
			g2D.setComposite(composite);
		}

		g.translate(-thumbBounds.x, -thumbBounds.y);
	}

} // end of class AcrylScrollBarUI
