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
package com.jtattoo.plaf.fast;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.BaseScrollButton;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>FastScrollButton class.</p>
 *
 *
 *
 */
public class FastScrollButton extends BaseScrollButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for FastScrollButton.</p>
	 *
	 * @param direction a {@link java.lang.Integer} object.
	 * @param width a {@link java.lang.Integer} object.
	 */
	public FastScrollButton(final int direction, final int width) {
		super(direction, width);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		final boolean isPressed = getModel().isPressed();

		final int width = getWidth();
		final int height = getHeight();

		Color backColor = AbstractLookAndFeel.getTheme().getControlBackgroundColor();
		if (!JTattooUtilities.isActive(this)) {
			backColor = ColorHelper.brighter(backColor, 50);
		}
		if (isPressed) {
			backColor = ColorHelper.darker(backColor, 10);
		}
		final Color hiColor = ColorHelper.brighter(backColor, 40);
		final Color loColor = ColorHelper.darker(backColor, 30);
		g.setColor(backColor);
		g.fillRect(0, 0, width, height);

		switch (getDirection()) {
		case NORTH: {
			final Icon upArrow = BaseIcons.getUpArrowIcon();
			final int x = width / 2 - upArrow.getIconWidth() / 2;
			final int y = height / 2 - upArrow.getIconHeight() / 2 - 1;
			upArrow.paintIcon(this, g, x, y);
			break;
		}
		case SOUTH: {
			final Icon downArrow = BaseIcons.getDownArrowIcon();
			final int x = width / 2 - downArrow.getIconWidth() / 2;
			final int y = height / 2 - downArrow.getIconHeight() / 2;
			downArrow.paintIcon(this, g, x, y);
			break;
		}
		case WEST: {
			final Icon leftArrow = BaseIcons.getLeftArrowIcon();
			final int x = width / 2 - leftArrow.getIconWidth() / 2 - 1;
			final int y = height / 2 - leftArrow.getIconHeight() / 2;
			leftArrow.paintIcon(this, g, x, y);
			break;
		}
		default: {
			final Icon rightArrow = BaseIcons.getRightArrowIcon();
			final int x = width / 2 - rightArrow.getIconWidth() / 2;
			final int y = height / 2 - rightArrow.getIconHeight() / 2;
			rightArrow.paintIcon(this, g, x, y);
			break;
		}
		}

		JTattooUtilities.draw3DBorder(g, ColorHelper.brighter(loColor, 20), loColor, 0, 0, width, height);
		if (!isPressed) {
			g.setColor(hiColor);
			g.drawLine(1, 1, width - 2, 1);
			g.drawLine(1, 1, 1, height - 2);
		}
	}

} // end of class FastScrollButton
