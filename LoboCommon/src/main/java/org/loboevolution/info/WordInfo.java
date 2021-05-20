/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.loboevolution.info;

import java.awt.FontMetrics;

/**
 * <p>WordInfo class.</p>
 *
 * Author J. H. S.
 *
 */
public class WordInfo {
	
	private int ascentPlusLeading;
	
	private int descent;
	
	private FontMetrics fontMetrics;
	
	private int height;
	
	private int width;

	/**
	 * <p>Getter for the field ascentPlusLeading.</p>
	 *
	 * @return the ascentPlusLeading
	 */
	public int getAscentPlusLeading() {
		return ascentPlusLeading;
	}

	/**
	 * <p>Getter for the field descent.</p>
	 *
	 * @return the descent
	 */
	public int getDescent() {
		return descent;
	}

	/**
	 * <p>Getter for the field fontMetrics.</p>
	 *
	 * @return the fontMetrics
	 */
	public FontMetrics getFontMetrics() {
		return fontMetrics;
	}

	/**
	 * <p>Getter for the field height.</p>
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * <p>Getter for the field width.</p>
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * <p>Setter for the field ascentPlusLeading.</p>
	 *
	 * @param ascentPlusLeading the ascentPlusLeading to set
	 */
	public void setAscentPlusLeading(int ascentPlusLeading) {
		this.ascentPlusLeading = ascentPlusLeading;
	}

	/**
	 * <p>Setter for the field descent.</p>
	 *
	 * @param descent the descent to set
	 */
	public void setDescent(int descent) {
		this.descent = descent;
	}

	/**
	 * <p>Setter for the field fontMetrics.</p>
	 *
	 * @param fontMetrics the fontMetrics to set
	 */
	public void setFontMetrics(FontMetrics fontMetrics) {
		this.fontMetrics = fontMetrics;
	}

	/**
	 * <p>Setter for the field height.</p>
	 *
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * <p>Setter for the field width.</p>
	 *
	 * @param width the width to set
	 */
	public void setWidth(final int width) {
		this.width = width;
	}
}
