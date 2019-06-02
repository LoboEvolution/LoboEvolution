package org.lobo.component;

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

public class RoundedCornerBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	private final Color ALPHA_ZERO = new Color(0x0, true);

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(4, 8, 4, 8);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.set(4, 8, 4, 8);
		return insets;
	}

	public Shape getBorderShape(int x, int y, int w, int h) {
		final int r = h;
		return new RoundRectangle2D.Double(x, y, w, h, r, r);
	}

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
