/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
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
 * @author utente
 * @version $Id: $Id
 */
public class RoundedCornerBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	private final Color ALPHA_ZERO = new Color(0x0, true);

	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(4, 8, 4, 8);
	}

	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.set(4, 8, 4, 8);
		return insets;
	}

	/**
	 * <p>getBorderShape.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 * @return a {@link java.awt.Shape} object.
	 */
	public Shape getBorderShape(int x, int y, int w, int h) {
		final int r = h;
		return new RoundRectangle2D.Double(x, y, w, h, r, r);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
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
