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
 * <p>SVGPathSeg interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGPathSeg {

	/** Constant PATHSEG_UNKNOWN=0 */
    short PATHSEG_UNKNOWN = 0;

	/** Constant PATHSEG_CLOSEPATH=1 */
    short PATHSEG_CLOSEPATH = 1;

	/** Constant PATHSEG_MOVETO_ABS=2 */
    short PATHSEG_MOVETO_ABS = 2;

	/** Constant PATHSEG_MOVETO_REL=3 */
    short PATHSEG_MOVETO_REL = 3;

	/** Constant PATHSEG_LINETO_ABS=4 */
    short PATHSEG_LINETO_ABS = 4;

	/** Constant PATHSEG_LINETO_REL=5 */
    short PATHSEG_LINETO_REL = 5;

	/** Constant PATHSEG_CURVETO_CUBIC_ABS=6 */
    short PATHSEG_CURVETO_CUBIC_ABS = 6;

	/** Constant PATHSEG_CURVETO_CUBIC_REL=7 */
    short PATHSEG_CURVETO_CUBIC_REL = 7;

	/** Constant PATHSEG_CURVETO_QUADRATIC_ABS=8 */
    short PATHSEG_CURVETO_QUADRATIC_ABS = 8;

	/** Constant PATHSEG_CURVETO_QUADRATIC_REL=9 */
    short PATHSEG_CURVETO_QUADRATIC_REL = 9;

	/** Constant PATHSEG_ARC_ABS=10 */
    short PATHSEG_ARC_ABS = 10;

	/** Constant PATHSEG_ARC_REL=11 */
    short PATHSEG_ARC_REL = 11;

	/** Constant PATHSEG_LINETO_HORIZONTAL_ABS=12 */
    short PATHSEG_LINETO_HORIZONTAL_ABS = 12;

	/** Constant PATHSEG_LINETO_HORIZONTAL_REL=13 */
    short PATHSEG_LINETO_HORIZONTAL_REL = 13;

	/** Constant PATHSEG_LINETO_VERTICAL_ABS=14 */
    short PATHSEG_LINETO_VERTICAL_ABS = 14;

	/** Constant PATHSEG_LINETO_VERTICAL_REL=15 */
    short PATHSEG_LINETO_VERTICAL_REL = 15;

	/** Constant PATHSEG_CURVETO_CUBIC_SMOOTH_ABS=16 */
    short PATHSEG_CURVETO_CUBIC_SMOOTH_ABS = 16;

	/** Constant PATHSEG_CURVETO_CUBIC_SMOOTH_REL=17 */
    short PATHSEG_CURVETO_CUBIC_SMOOTH_REL = 17;

	/** Constant PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS=18 */
    short PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS = 18;

	/** Constant PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL=19 */
    short PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL = 19;

	/**
	 * <p>getPathSegType.</p>
	 *
	 * @return a short.
	 */
	short getPathSegType();

	/**
	 * <p>getPathSegTypeAsLetter.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPathSegTypeAsLetter();
}
