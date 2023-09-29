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

import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * <p>LazyImageIcon class.</p>
 *
 * Author Michael Hagen*
 */
@Slf4j
public class LazyImageIcon implements Icon {
	
	private String name = null;
	private ImageIcon icon = null;

	/**
	 * <p>Constructor for LazyImageIcon.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public LazyImageIcon(final String name) {
		this.name = name;
	}

	private Icon getIcon() {
		if (icon == null) {
			try {
				icon = new ImageIcon(LazyImageIcon.class.getResource(name));
			} catch (final Throwable t) {
				log.error("ERROR: loading image failed {} ", name, t);
			}
		}
		return icon;
	}

	/** {@inheritDoc} */
	@Override
	public int getIconHeight() {
		if (getIcon() != null) {
			return icon.getIconHeight();
		} else {
			return 16;
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getIconWidth() {
		if (getIcon() != null) {
			return icon.getIconWidth();
		} else {
			return 16;
		}
	}

	/**
	 * <p>getImage.</p>
	 *
	 * @return a {@link java.awt.Image} object.
	 */
	public Image getImage() {
		if (getIcon() != null) {
			return icon.getImage();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {
final int x = iconX;
		if (getIcon() != null) {
			icon.paintIcon(c, g, x, y);
		} else {
			g.setColor(Color.red);
			g.fillRect(x, y, 16, 16);
			g.setColor(Color.white);
			g.drawLine(x, y, x + 15, y + 15);
			g.drawLine(x + 15, y, x, y + 15);
		}
	}

} // end of class LazyImageIcon
