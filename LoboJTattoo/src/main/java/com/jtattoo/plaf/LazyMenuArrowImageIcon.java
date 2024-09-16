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

import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <p>LazyMenuArrowImageIcon class.</p>
 *
 * Author Michael Hagen *
 */
@Slf4j
public class LazyMenuArrowImageIcon implements Icon {

	private String leftToRightName = null;
	private String rightToLefttName = null;
	private Icon leftToRightIcon = null;
	private Icon rightToLeftIcon = null;

	/**
	 * <p>Constructor for LazyMenuArrowImageIcon.</p>
	 *
	 * @param leftToRightName a {@link java.lang.String} object.
	 * @param rightToLefttName a {@link java.lang.String} object.
	 */
	public LazyMenuArrowImageIcon(final String leftToRightName, final String rightToLefttName) {
		this.leftToRightName = leftToRightName;
		this.rightToLefttName = rightToLefttName;
	}

	private Icon getIcon(final Component c) {
		if (JTattooUtilities.isLeftToRight(c)) {
			return getLeftToRightIcon();
		} else {
			return getRightToLeftIcon();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getIconHeight() {
		final Icon ico = getIcon(null);
		if (ico != null) {
			return ico.getIconHeight();
		} else {
			return 16;
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getIconWidth() {
		final Icon ico = getIcon(null);
		if (ico != null) {
			return ico.getIconWidth();
		} else {
			return 16;
		}
	}

	private Icon getLeftToRightIcon() {
		if (leftToRightIcon == null) {
			try {
				leftToRightIcon = new ImageIcon(LazyMenuArrowImageIcon.class.getResource(leftToRightName));
			} catch (final Throwable t) {
				log.error("ERROR: loading image failed {} ", leftToRightIcon, t);
			}
		}
		return leftToRightIcon;
	}

	private Icon getRightToLeftIcon() {
		if (rightToLeftIcon == null) {
			try {
				rightToLeftIcon = new ImageIcon(LazyMenuArrowImageIcon.class.getResource(rightToLefttName));
			} catch (final Throwable t) {
				log.error("ERROR: loading image failed {} ", rightToLeftIcon, t);
			}
		}
		return rightToLeftIcon;
	}

	/** {@inheritDoc} */
	@Override
	public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {
		final int x = iconX;
		final Icon ico = getIcon(c);
		if (ico != null) {
			ico.paintIcon(c, g, x, y);
		} else {
			g.setColor(Color.red);
			g.fillRect(x, y, 16, 16);
			g.setColor(Color.white);
			g.drawLine(x, y, x + 15, y + 15);
			g.drawLine(x + 15, y, x, y + 15);
		}
	}

} // end of class LazyMenuArrowImageIcon
