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

package com.jtattoo.plaf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * <p>BaseScrollButton class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseScrollButton extends BasicArrowButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected int buttonWidth = 24;

	/**
	 * <p>Constructor for BaseScrollButton.</p>
	 *
	 * @param direction a int.
	 * @param width a int.
	 */
	public BaseScrollButton(int direction, int width) {
		super(direction);
		buttonWidth = width;
	}

	/**
	 * <p>Getter for the field buttonWidth.</p>
	 *
	 * @return a int.
	 */
	public int getButtonWidth() {
		return buttonWidth;
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
		boolean isPressed = getModel().isPressed();
		boolean isRollover = getModel().isRollover();

		int width = getWidth();
		int height = getHeight();

		Color[] colors;
		if (isPressed) {
			colors = AbstractLookAndFeel.getTheme().getPressedColors();
		} else if (isRollover) {
			colors = AbstractLookAndFeel.getTheme().getRolloverColors();
		} else {
			colors = AbstractLookAndFeel.getTheme().getButtonColors();
		}

		boolean inverse = ColorHelper.getGrayValue(colors) < 128;

		Color frameColorHi = ColorHelper.brighter(colors[0], 20);
		Color frameColorLo = ColorHelper.darker(colors[colors.length - 1], 20);

		Graphics2D g2D = (Graphics2D) g;
		Composite savedComposite = g2D.getComposite();
		if (getDirection() == NORTH || getDirection() == SOUTH) {
			JTattooUtilities.fillVerGradient(g2D, colors, 0, 0, width, height);
		} else {
			JTattooUtilities.fillHorGradient(g2D, colors, 0, 0, width, height);
		}
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		switch (getDirection()) {
		case NORTH: {
			Icon upArrow = inverse ? BaseIcons.getUpArrowInverseIcon() : BaseIcons.getUpArrowIcon();
			int x = width / 2 - upArrow.getIconWidth() / 2;
			int y = height / 2 - upArrow.getIconHeight() / 2 - 1;
			upArrow.paintIcon(this, g2D, x, y);
			break;
		}
		case SOUTH: {
			Icon downArrow = inverse ? BaseIcons.getDownArrowInverseIcon() : BaseIcons.getDownArrowIcon();
			int x = width / 2 - downArrow.getIconWidth() / 2;
			int y = height / 2 - downArrow.getIconHeight() / 2;
			downArrow.paintIcon(this, g2D, x, y);
			break;
		}
		case WEST: {
			Icon leftArrow = inverse ? BaseIcons.getLeftArrowInverseIcon() : BaseIcons.getLeftArrowIcon();
			int x = width / 2 - leftArrow.getIconWidth() / 2 - 1;
			int y = height / 2 - leftArrow.getIconHeight() / 2;
			leftArrow.paintIcon(this, g2D, x, y);
			break;
		}
		default: {
			Icon rightArrow = inverse ? BaseIcons.getRightArrowInverseIcon() : BaseIcons.getRightArrowIcon();
			int x = width / 2 - rightArrow.getIconWidth() / 2;
			int y = height / 2 - rightArrow.getIconHeight() / 2;
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
