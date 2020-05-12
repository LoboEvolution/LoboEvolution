/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;

import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.RGBColor;

/**
 * <p>SVGColor interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGColor extends CSSValue {
	// Color Types
	/** Constant SVG_COLORTYPE_UNKNOWN=0 */
	static final short SVG_COLORTYPE_UNKNOWN = 0;
	/** Constant SVG_COLORTYPE_RGBCOLOR=1 */
	static final short SVG_COLORTYPE_RGBCOLOR = 1;
	/** Constant SVG_COLORTYPE_RGBCOLOR_ICCCOLOR=2 */
	static final short SVG_COLORTYPE_RGBCOLOR_ICCCOLOR = 2;
	/** Constant SVG_COLORTYPE_CURRENTCOLOR=3 */
	static final short SVG_COLORTYPE_CURRENTCOLOR = 3;

	/**
	 * <p>getColorType.</p>
	 *
	 * @return a short.
	 */
	short getColorType();

	/**
	 * <p>getRGBColor.</p>
	 *
	 * @return a {@link org.w3c.dom.css.RGBColor} object.
	 */
	public RGBColor getRGBColor();

	/**
	 * <p>getICCColor.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGICCColor} object.
	 */
	SVGICCColor getICCColor();

	/**
	 * <p>setRGBColor.</p>
	 *
	 * @param rgbColor a {@link java.lang.String} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	void setRGBColor(String rgbColor) throws SVGException;

	/**
	 * <p>setRGBColorICCColor.</p>
	 *
	 * @param rgbColor a {@link java.lang.String} object.
	 * @param iccColor a {@link java.lang.String} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	void setRGBColorICCColor(String rgbColor, String iccColor) throws SVGException;

	/**
	 * <p>setColor.</p>
	 *
	 * @param colorType a short.
	 * @param rgbColor a {@link java.lang.String} object.
	 * @param iccColor a {@link java.lang.String} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	void setColor(short colorType, String rgbColor, String iccColor) throws SVGException;
}
