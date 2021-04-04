
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
package org.loboevolution.html.dom.svg;

/**
 * <p>SVGFEBlendElement interface.</p>
 *
 *
 *
 */
public interface SVGFEBlendElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Blend Mode Types
	/** Constant SVG_FEBLEND_MODE_UNKNOWN=0 */
    short SVG_FEBLEND_MODE_UNKNOWN = 0;
	/** Constant SVG_FEBLEND_MODE_NORMAL=1 */
    short SVG_FEBLEND_MODE_NORMAL = 1;
	/** Constant SVG_FEBLEND_MODE_MULTIPLY=2 */
    short SVG_FEBLEND_MODE_MULTIPLY = 2;
	/** Constant SVG_FEBLEND_MODE_SCREEN=3 */
    short SVG_FEBLEND_MODE_SCREEN = 3;
	/** Constant SVG_FEBLEND_MODE_DARKEN=4 */
    short SVG_FEBLEND_MODE_DARKEN = 4;
	/** Constant SVG_FEBLEND_MODE_LIGHTEN=5 */
    short SVG_FEBLEND_MODE_LIGHTEN = 5;

	/**
	 * <p>getIn1.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedString} object.
	 */
	SVGAnimatedString getIn1();

	/**
	 * <p>getIn2.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedString} object.
	 */
	SVGAnimatedString getIn2();

	/**
	 * <p>getMode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getMode();
}
