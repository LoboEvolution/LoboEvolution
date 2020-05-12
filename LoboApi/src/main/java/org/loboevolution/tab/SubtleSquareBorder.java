package org.loboevolution.tab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

class SubtleSquareBorder implements Border {

	protected Color m_bottomColor = Color.gray;

	protected int m_h = 6;

	protected Color m_topColor = Color.white;

	protected int m_w = 6;

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
	public Insets getBorderInsets(Component c) {
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
