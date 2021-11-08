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

package org.loboevolution.tab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

class SubtleSquareBorder implements Border {

	protected final Color m_bottomColor = Color.gray;

	protected final int m_h = 6;

	protected final Color m_topColor = Color.white;

	protected final int m_w = 6;

	protected boolean roundc = false;

	/**
	 * <p>Constructor for SubtleSquareBorder.</p>
	 *
	 * @param round_corners a boolean.
	 */
	public SubtleSquareBorder(boolean round_corners) {
		this.roundc = round_corners;
	}

	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets(final Component c) {
		return new Insets(this.m_h, this.m_w, this.m_h, this.m_w);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		w = w - 3;
		h = h - 3;
		x++;
		y++;
// Rounded corners
		if (this.roundc) {
			g.setColor(this.m_topColor);
			g.drawLine(x, y + 2, x, y + h - 2);
			g.drawLine(x + 2, y, x + w - 2, y);
			g.drawLine(x, y + 2, x + 2, y); // Top left diagonal
			g.drawLine(x, y + h - 2, x + 2, y + h); // Bottom left diagonal
			g.setColor(this.m_bottomColor);
			g.drawLine(x + w, y + 2, x + w, y + h - 2);
			g.drawLine(x + 2, y + h, x + w - 2, y + h);
			g.drawLine(x + w - 2, y, x + w, y + 2); // Top right diagonal
			g.drawLine(x + w, y + h - 2, x + w - 2, y + h); // Bottom right diagonal
		}
// Square corners
		else {
			g.setColor(this.m_topColor);
			g.drawLine(x, y, x, y + h);
			g.drawLine(x, y, x + w, y);
			g.setColor(this.m_bottomColor);
			g.drawLine(x + w, y, x + w, y + h);
			g.drawLine(x, y + h, x + w, y + h);
		}
	}
}
