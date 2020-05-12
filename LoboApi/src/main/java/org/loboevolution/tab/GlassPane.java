package org.loboevolution.tab;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class GlassPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private final AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

	private BufferedImage image;

	private Point location;

	private final DnDTabbedPane tab;

	/**
	 * <p>Constructor for GlassPane.</p>
	 *
	 * @param tab a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	public GlassPane(DnDTabbedPane tab) {
		setOpaque(false);
		this.tab = tab;
	}

	/**
	 * <p>createImage.</p>
	 *
	 * @param c a {@link java.awt.Component} object.
	 */
	public void createImage(Component c) {
		final Rectangle rect = this.tab.getBoundsAt(this.tab.dragTabIdx);
		this.image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		c.paint(this.image.getGraphics());
		this.image = this.image.getSubimage(rect.x < 0 ? 0 : rect.x, rect.y < 0 ? 0 : rect.y, rect.width, rect.height);
	}

	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		if (this.location == null) {
			return;
		}
		final Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(this.composite);

		final int tabOver = this.tab.getDropIndex(this.location);

		if (tabOver > -1 && tabOver != this.tab.dragTabIdx) {
			final Rectangle r = SwingUtilities.convertRectangle(this.tab, this.tab.getBoundsAt(tabOver), this);

			final int x = r.x + (tabOver <= this.tab.dragTabIdx ? 0 : r.width);
			g.setColor(Color.black);
			g.fillRect(x, r.y, 2, r.height);
		}

		if (this.image != null) {
			final Point _location = SwingUtilities.convertPoint(this.tab, this.location, this);
			final double xx = _location.getX() - this.image.getWidth(this) / 2d;
			final double yy = _location.getY() - this.image.getHeight(this) / 2d;
			g2.drawImage(this.image, (int) xx, (int) yy, null);
		}
	}

	/**
	 * <p>setDragLocation.</p>
	 *
	 * @param location the location to set
	 */
	public void setDragLocation(Point location) {
		this.location = location;
	}
}
