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
package com.jtattoo.plaf.aluminium;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>AluminiumUtils class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class AluminiumUtils {

	private static final Icon BG_IMAGE = new LazyImageIcon("aluminium/icons/background.jpg");
	// private static final Icon ALTER_BG_IMAGE = new
	// LazyImageIcon("aluminium/icons/alterBackground.jpg");
	private static Image backgroundImage = null;

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 */
	public static void fillComponent(Graphics g, Component c) {
		Graphics2D g2D = (Graphics2D) g;
		int w = c.getWidth();
		int h = c.getHeight();
		if (AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			// pattern
			Point p = JTattooUtilities.getRelLocation(c);
			Dimension d = JTattooUtilities.getFrameSize(c);
			int y = -p.y;
			int iw = BG_IMAGE.getIconWidth();
			int ih = BG_IMAGE.getIconHeight();
			while (y < h) {
				int x = -p.x;
				while (x < w) {
					BG_IMAGE.paintIcon(c, g, x, y);
					x += iw;
				}
				y += ih;
			}
			// higlight
			if (backgroundImage == null || backgroundImage.getWidth(null) != d.width
					|| backgroundImage.getHeight(null) != d.height) {
				backgroundImage = c.createImage(d.width, d.height);
				Graphics2D ig2D = (Graphics2D) backgroundImage.getGraphics();
				Point pt1 = new Point(0, 0);
				Point pt2 = new Point(d.width, 0);
				float[] fractions = { 0.0f, 0.5f, 1.0f };
				Color c1 = new Color(220, 220, 220);
				Color[] colors = { c1, Color.white, c1 };
				ig2D.setPaint(new LinearGradientPaint(pt1, pt2, fractions, colors));
				ig2D.fillRect(0, 0, d.width, d.height);
				ig2D.dispose();
			}

			Composite savedComposite = g2D.getComposite();
			g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
			g2D.drawImage(backgroundImage, -p.x, 0, null);
			g2D.setComposite(savedComposite);
		} else {
			g.setColor(c.getBackground());
			g.fillRect(0, 0, w, h);
		}
	}

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void fillComponent(Graphics g, Component c, int x, int y, int w, int h) {
		Graphics2D g2D = (Graphics2D) g;
		Shape savedClip = g2D.getClip();
		Area clipArea = new Area(new Rectangle2D.Double(x, y, w, h));
		if (savedClip != null) {
			clipArea.intersect(new Area(savedClip));
		}
		g2D.setClip(clipArea);
		fillComponent(g, c);
		g2D.setClip(savedClip);
	}

	private AluminiumUtils() {
	}

} // end of class AluminiumUtils
