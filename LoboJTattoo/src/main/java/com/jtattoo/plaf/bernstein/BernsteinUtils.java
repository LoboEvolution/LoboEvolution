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
package com.jtattoo.plaf.bernstein;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>BernsteinUtils class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class BernsteinUtils {

	private static final Icon BG_IMAGE = new LazyImageIcon("bernstein/icons/background.jpg");
	private static final int IMAGE_WIDTH = BG_IMAGE.getIconWidth();
	private static final int IMAGE_HEIGHT = BG_IMAGE.getIconHeight();

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
			int y = -p.y;
			while (y < h) {
				int x = -p.x;
				while (x < w) {
					BG_IMAGE.paintIcon(c, g, x, y);
					x += IMAGE_WIDTH;
				}
				y += IMAGE_HEIGHT;
			}
		} else {
			g.setColor(c.getBackground());
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
		}
	}

	private BernsteinUtils() {
	}

} // end of class BernsteinUtils
