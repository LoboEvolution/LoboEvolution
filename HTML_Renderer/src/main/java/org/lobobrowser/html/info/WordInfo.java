/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.lobobrowser.html.info;

import java.awt.FontMetrics;
import java.io.Serializable;

/**
 * The Class WordInfo.
 */
public class WordInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2331404075477256162L;

	/** The font metrics. */
	private FontMetrics fontMetrics;

	/** The descent. */
	private int descent;

	/** The ascent plus leading. */
	private int ascentPlusLeading;

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/**
	 * Gets the font metrics.
	 *
	 * @return the font metrics
	 */
	public FontMetrics getFontMetrics() {
		return fontMetrics;
	}

	/**
	 * Sets the font metrics.
	 *
	 * @param fontMetrics
	 *            the new font metrics
	 */
	public void setFontMetrics(FontMetrics fontMetrics) {
		this.fontMetrics = fontMetrics;
	}

	/**
	 * Gets the descent.
	 *
	 * @return the descent
	 */
	public int getDescent() {
		return descent;
	}

	/**
	 * Sets the descent.
	 *
	 * @param descent
	 *            the new descent
	 */
	public void setDescent(int descent) {
		this.descent = descent;
	}

	/**
	 * Gets the ascent plus leading.
	 *
	 * @return the ascent plus leading
	 */
	public int getAscentPlusLeading() {
		return ascentPlusLeading;
	}

	/**
	 * Sets the ascent plus leading.
	 *
	 * @param ascentPlusLeading
	 *            the new ascent plus leading
	 */
	public void setAscentPlusLeading(int ascentPlusLeading) {
		this.ascentPlusLeading = ascentPlusLeading;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
