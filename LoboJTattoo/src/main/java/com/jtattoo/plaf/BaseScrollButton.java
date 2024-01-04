/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

/**
 * <p>BaseScrollButton class.</p>
 *
 * Author Michael Hagen
 *
 */
@Getter
public class BaseScrollButton extends BasicArrowButton {

	protected int buttonWidth = 24;

	/**
	 * <p>Constructor for BaseScrollButton.</p>
	 *
	 * @param direction a {@link java.lang.Integer} object.
	 * @param width a {@link java.lang.Integer} object.
	 */
	public BaseScrollButton(final int direction, final int width) {
		super(direction);
		buttonWidth = width;
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		switch (getDirection()) {
		case NORTH:
            case SOUTH:
                return new Dimension(buttonWidth, buttonWidth + 1);
            case EAST:
            case WEST:
                return new Dimension(buttonWidth + 1, buttonWidth);
            default:
			return new Dimension(0, 0);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		final boolean isPressed = getModel().isPressed();
		final boolean isRollover = getModel().isRollover();

		final int width = getWidth();
		final int height = getHeight();

		final Color[] colors;
		if (isPressed) {
			colors = AbstractLookAndFeel.getTheme().getPressedColors();
		} else if (isRollover) {
			colors = AbstractLookAndFeel.getTheme().getRolloverColors();
		} else {
			colors = AbstractLookAndFeel.getTheme().getButtonColors();
		}

		final boolean inverse = ColorHelper.getGrayValue(colors) < 128;

		final Color frameColorHi = ColorHelper.brighter(colors[0], 20);
		final Color frameColorLo = ColorHelper.darker(colors[colors.length - 1], 20);

		final Graphics2D g2D = (Graphics2D) g;
		final Composite savedComposite = g2D.getComposite();
		if (getDirection() == NORTH || getDirection() == SOUTH) {
			JTattooUtilities.fillVerGradient(g2D, colors, 0, 0, width, height);
		} else {
			JTattooUtilities.fillHorGradient(g2D, colors, 0, 0, width, height);
		}
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		switch (getDirection()) {
		case NORTH: {
			final Icon upArrow = inverse ? BaseIcons.getUpArrowInverseIcon() : BaseIcons.getUpArrowIcon();
			final int x = width / 2 - upArrow.getIconWidth() / 2;
			final int y = height / 2 - upArrow.getIconHeight() / 2 - 1;
			upArrow.paintIcon(this, g2D, x, y);
			break;
		}
		case SOUTH: {
			final Icon downArrow = inverse ? BaseIcons.getDownArrowInverseIcon() : BaseIcons.getDownArrowIcon();
			final int x = width / 2 - downArrow.getIconWidth() / 2;
			final int y = height / 2 - downArrow.getIconHeight() / 2;
			downArrow.paintIcon(this, g2D, x, y);
			break;
		}
		case WEST: {
			final Icon leftArrow = inverse ? BaseIcons.getLeftArrowInverseIcon() : BaseIcons.getLeftArrowIcon();
			final int x = width / 2 - leftArrow.getIconWidth() / 2 - 1;
			final int y = height / 2 - leftArrow.getIconHeight() / 2;
			leftArrow.paintIcon(this, g2D, x, y);
			break;
		}
		default: {
			final Icon rightArrow = inverse ? BaseIcons.getRightArrowInverseIcon() : BaseIcons.getRightArrowIcon();
			final int x = width / 2 - rightArrow.getIconWidth() / 2;
			final int y = height / 2 - rightArrow.getIconHeight() / 2;
			rightArrow.paintIcon(this, g2D, x, y);
			break;
		}
		}
		JTattooUtilities.draw3DBorder(g2D, frameColorLo, ColorHelper.darker(frameColorLo, 10), 0, 0, width, height);
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		g2D.setColor(frameColorHi);
		g2D.drawLine(1, 1, width - 2, 1);
		g2D.drawLine(1, 1, 1, height - 2);

		g2D.setComposite(savedComposite);
	}

} // end of class BaseScrollButton
