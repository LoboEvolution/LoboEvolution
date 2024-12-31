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
package com.jtattoo.plaf.mcwin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>McWinUtils class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class McWinUtils {

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 */
	public static void fillComponent(final Graphics g, final Component c) {
		if (AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			final int w = c.getWidth();
			final int h = c.getHeight();
			final Point p = JTattooUtilities.getRelLocation(c);
			int y = 2 - p.y % 3;
			g.setColor(AbstractLookAndFeel.getTheme().getBackgroundColorLight());
			g.fillRect(0, 0, w, h);
			g.setColor(AbstractLookAndFeel.getTheme().getBackgroundColorDark());
			while (y < h) {
				g.drawLine(0, y, w, y);
				y += 3;
			}
		} else {
			g.setColor(c.getBackground());
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
		}
	}

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 * @param colors an array of {@link java.awt.Color} objects.
	 */
	public static void fillComponent(final Graphics g, final Component c, final Color[] colors) {
		final int w = c.getWidth();
		final int h = c.getHeight();
		JTattooUtilities.fillHorGradient(g, colors, 0, 0, w, h);
		if (AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			final Point p = JTattooUtilities.getRelLocation(c);
			int y = 2 - p.y % 3;
			final Color lc = AbstractLookAndFeel.getTheme().getBackgroundColorDark();
			Color lineColor = ColorHelper.brighter(ColorHelper.median(lc, colors[colors.length - 1]), 50);
			while (y < h) {
				g.setColor(lineColor);
				g.drawLine(0, y, w, y);
				lineColor = ColorHelper.darker(lineColor, 1.5);
				y += 3;
			}
		}
	}

	private McWinUtils() {
	}

} // end of class McWinUtils
