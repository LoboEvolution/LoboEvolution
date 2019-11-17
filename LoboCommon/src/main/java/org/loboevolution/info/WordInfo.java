/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Apr 17, 2005
 */
package org.loboevolution.info;

import java.awt.FontMetrics;

/**
 * @author J. H. S.
 */
public class WordInfo {
	
	private int ascentPlusLeading;
	
	private int descent;
	
	private FontMetrics fontMetrics;
	
	private int height;
	
	private int width;

	/**
	 * @return the ascentPlusLeading
	 */
	public int getAscentPlusLeading() {
		return ascentPlusLeading;
	}

	/**
	 * @return the descent
	 */
	public int getDescent() {
		return descent;
	}

	/**
	 * @return the fontMetrics
	 */
	public FontMetrics getFontMetrics() {
		return fontMetrics;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param ascentPlusLeading the ascentPlusLeading to set
	 */
	public void setAscentPlusLeading(int ascentPlusLeading) {
		this.ascentPlusLeading = ascentPlusLeading;
	}

	/**
	 * @param descent the descent to set
	 */
	public void setDescent(int descent) {
		this.descent = descent;
	}

	/**
	 * @param fontMetrics the fontMetrics to set
	 */
	public void setFontMetrics(FontMetrics fontMetrics) {
		this.fontMetrics = fontMetrics;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
