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

package com.jtattoo.plaf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * <p>Abstract XPScrollButton class.</p>
 *
 * Author Michael Hagen
 *
 */
public abstract class XPScrollButton extends BaseScrollButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for XPScrollButton.</p>
	 *
	 * @param direction a {@link java.lang.Integer} object.
	 * @param width a {@link java.lang.Integer} object.
	 */
	public XPScrollButton(final int direction, final int width) {
		super(direction, width);
	}

	private Icon getDownArrowIcon() {
		if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
			final AbstractLookAndFeel laf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
			return laf.getIconFactory().getDownArrowIcon();
		}
		return null;
	}

	/**
	 * <p>getFrameColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getFrameColor() {
		return Color.white;
	}

	private Icon getLeftArrowIcon() {
		if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
			final AbstractLookAndFeel laf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
			return laf.getIconFactory().getLeftArrowIcon();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		switch (getDirection()) {
		case NORTH:
            case WEST:
            case EAST:
            case SOUTH:
                return new Dimension(buttonWidth, buttonWidth);
            default:
			return new Dimension(0, 0);
		}
	}

	private Icon getRightArrowIcon() {
		if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
			final AbstractLookAndFeel laf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
			return laf.getIconFactory().getRightArrowIcon();
		}
		return null;
	}

	private Icon getUpArrowIcon() {
		if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
			final AbstractLookAndFeel laf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
			return laf.getIconFactory().getUpArrowIcon();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		final Graphics2D g2D = (Graphics2D) g;
		final Composite savedComposite = g2D.getComposite();
		final Paint savedPaint = g2D.getPaint();

		final boolean isPressed = getModel().isPressed();
		final boolean isRollover = getModel().isRollover();

		final int width = getWidth();
		final int height = getHeight();

		final Color[] tc = AbstractLookAndFeel.getTheme().getThumbColors();
		Color c1 = tc[0];
		Color c2 = tc[tc.length - 1];
		if (isPressed) {
			c1 = ColorHelper.darker(c1, 5);
			c2 = ColorHelper.darker(c2, 5);
		} else if (isRollover) {
			c1 = ColorHelper.brighter(c1, 20);
			c2 = ColorHelper.brighter(c2, 20);
		}

		g2D.setPaint(new GradientPaint(0, 0, c1, width, height, c2));
		g.fillRect(0, 0, width, height);
		g2D.setPaint(savedPaint);

		g.setColor(getFrameColor());
		g.drawLine(1, 1, width - 2, 1);
		g.drawLine(1, 1, 1, height - 3);
		g.drawLine(width - 2, 1, width - 2, height - 3);
		g.drawLine(2, height - 2, width - 3, height - 2);

		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g2D.setComposite(alpha);
		g2D.setColor(c2);
		g.drawLine(2, 2, width - 3, 2);
		g.drawLine(2, 3, 2, height - 3);

		g.setColor(ColorHelper.darker(c2, 40));
		g.drawLine(width - 1, 2, width - 1, height - 3);
		g.drawLine(3, height - 1, width - 3, height - 1);
		alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
		g2D.setComposite(alpha);
		g.drawLine(1, height - 2, 2, height - 1);
		g.drawLine(width - 1, height - 2, width - 2, height - 1);

		g2D.setComposite(savedComposite);

		final int x;
		final int y;
		final Icon icon;
		// paint the icon
		switch (getDirection()) {
		case NORTH:
			icon = getUpArrowIcon();
			x = width / 2 - icon.getIconWidth() / 2;
			y = height / 2 - icon.getIconHeight() / 2;
			break;
		case SOUTH:
			icon = getDownArrowIcon();
			x = width / 2 - icon.getIconWidth() / 2;
			y = height / 2 - icon.getIconHeight() / 2 + 1;
			break;
		case WEST:
			icon = getLeftArrowIcon();
			x = width / 2 - icon.getIconWidth() / 2;
			y = height / 2 - icon.getIconHeight() / 2;
			break;
		default:
			icon = getRightArrowIcon();
			x = width / 2 - icon.getIconWidth() / 2 + 1;
			y = height / 2 - icon.getIconHeight() / 2;
			break;
		}
		icon.paintIcon(this, g, x, y);
	}

} // end of class XPScrollButton
