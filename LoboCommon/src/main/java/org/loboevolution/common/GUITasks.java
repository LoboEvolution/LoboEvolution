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
package org.loboevolution.common;

import java.awt.BasicStroke;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * <p>GUITasks class.</p>
 *
 *
 *
 */
public class GUITasks {
	/**
	 * <p>drawDashed.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a int.
	 * @param y1 a int.
	 * @param x2 a int.
	 * @param y2 a int.
	 * @param dashSize a int.
	 * @param gapSize a int.
	 */
	public static void drawDashed(Graphics g, int x1, int y1, int x2, int y2, int dashSize, int gapSize) {
		if (x2 < x1) {
			final int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y2 < y1) {
			final int temp = y1;
			y1 = y2;
			y2 = temp;
		}
		final int totalDash = dashSize + gapSize;
		if (y1 == y2) {
			final int virtualStartX = x1 / totalDash * totalDash;
			for (int x = virtualStartX; x < x2; x += totalDash) {
				int topX = x + dashSize;
				if (topX > x2) {
					topX = x2;
				}
				int firstX = x;
				if (firstX < x1) {
					firstX = x1;
				}
				if (firstX < topX) {
					g.drawLine(firstX, y1, topX, y1);
				}
			}
		} else if (x1 == x2) {
			final int virtualStartY = y1 / totalDash * totalDash;
			for (int y = virtualStartY; y < y2; y += totalDash) {
				int topY = y + dashSize;
				if (topY > y2) {
					topY = y2;
				}
				int firstY = y;
				if (firstY < y1) {
					firstY = y1;
				}
				if (firstY < topY) {
					g.drawLine(x1, firstY, x1, topY);
				}
			}
		} else {
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	
	/**
	 * <p>drawDotted.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a int.
	 * @param y1 a int.
	 * @param x2 a int.
	 * @param y2 a int.
	 * @param width a float.
	 */
	public static void drawDotted(final Graphics g, final int x1, final int y1, final int x2, final int y2, final float width) {
		final Graphics ng = g.create();
		try {
			final Graphics2D g2d = (Graphics2D) ng.create();
			final float dot = Math.max(2f, width);
			final float[] dotPattern = { dot };
			final Stroke stroke = new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dotPattern, 0.0f);
			g2d.setStroke(stroke);
			g2d.drawLine(x1, y1, x2, y2);
			g2d.dispose();
		} finally {
			ng.dispose();
		}
	}

	/**
	 * <p>getTopFrame.</p>
	 *
	 * @return a {@link java.awt.Frame} object.
	 */
	public static Frame getTopFrame() {
		final Frame[] frames = Frame.getFrames();
		for (final Frame frame : frames) {
			if (frame.getFocusOwner() != null) {
				return frame;
			}
		}
		if (frames.length > 0) {
			return frames[0];
		}
		return null;
	}
}
