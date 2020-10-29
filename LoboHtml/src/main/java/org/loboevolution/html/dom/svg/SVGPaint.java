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

/**
 * <p>SVGPaint interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGPaint extends SVGColor {
	// Paint Types
	/** Constant SVG_PAINTTYPE_UNKNOWN=0 */
    short SVG_PAINTTYPE_UNKNOWN = 0;
	/** Constant SVG_PAINTTYPE_RGBCOLOR=1 */
    short SVG_PAINTTYPE_RGBCOLOR = 1;
	/** Constant SVG_PAINTTYPE_RGBCOLOR_ICCCOLOR=2 */
    short SVG_PAINTTYPE_RGBCOLOR_ICCCOLOR = 2;
	/** Constant SVG_PAINTTYPE_NONE=101 */
    short SVG_PAINTTYPE_NONE = 101;
	/** Constant SVG_PAINTTYPE_CURRENTCOLOR=102 */
    short SVG_PAINTTYPE_CURRENTCOLOR = 102;
	/** Constant SVG_PAINTTYPE_URI_NONE=103 */
    short SVG_PAINTTYPE_URI_NONE = 103;
	/** Constant SVG_PAINTTYPE_URI_CURRENTCOLOR=104 */
    short SVG_PAINTTYPE_URI_CURRENTCOLOR = 104;
	/** Constant SVG_PAINTTYPE_URI_RGBCOLOR=105 */
    short SVG_PAINTTYPE_URI_RGBCOLOR = 105;
	/** Constant SVG_PAINTTYPE_URI_RGBCOLOR_ICCCOLOR=106 */
    short SVG_PAINTTYPE_URI_RGBCOLOR_ICCCOLOR = 106;
	/** Constant SVG_PAINTTYPE_URI=107 */
    short SVG_PAINTTYPE_URI = 107;

	/**
	 * <p>getPaintType.</p>
	 *
	 * @return a short.
	 */
	short getPaintType();

	/**
	 * <p>getUri.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getUri();

	/**
	 * <p>setUri.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	void setUri(String uri);

	/**
	 * <p>setPaint.</p>
	 *
	 * @param paintType a short.
	 * @param uri a {@link java.lang.String} object.
	 * @param rgbColor a {@link java.lang.String} object.
	 * @param iccColor a {@link java.lang.String} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	void setPaint(short paintType, String uri, String rgbColor, String iccColor) throws SVGException;
}
