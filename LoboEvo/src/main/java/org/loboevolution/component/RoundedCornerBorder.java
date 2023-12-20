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

package org.loboevolution.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

/**
 * <p>RoundedCornerBorder class.</p>
 *
 *
 *
 */
public class RoundedCornerBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	private final Color ALPHA_ZERO = new Color(0x0, true);

	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets(final Component c) {
		return new Insets(4, 8, 4, 8);
	}

	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets(final Component c, final Insets insets) {
		insets.set(4, 8, 4, 8);
		return insets;
	}

	/**
	 * <p>getBorderShape.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Shape} object.
	 */
	public Shape getBorderShape(final int x, final int y, final int w, final int h) {
		final int r = h;
		return new RoundRectangle2D.Double(x, y, w, h, r, r);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
		final Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		final Shape border = getBorderShape(x, y, width - 1, height - 1);

		g2.setPaint(this.ALPHA_ZERO);
		final Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
		corner.subtract(new Area(border));
		g2.fill(corner);

		g2.setPaint(Color.GRAY);
		g2.draw(border);
		g2.dispose();
	}
}
