/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * <p>BrokenComponent class.</p>
 *
 *
 *
 */
public class BrokenComponent extends Component {

	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(10, 10);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		final Dimension size = this.getSize();
		g.drawRect(0, 0, size.width, size.height);
		g.drawLine(0, 0, size.width - 1, size.height - 1);
		g.drawLine(size.width - 1, 0, 0, size.height - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void update(Graphics g) {
		paint(g);
	}
}
