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
package org.lobobrowser.w3c.svg;

public interface SVGPathSeg {
	// Path Segment Types
	public static final short PATHSEG_UNKNOWN = 0;
	public static final short PATHSEG_CLOSEPATH = 1;
	public static final short PATHSEG_MOVETO_ABS = 2;
	public static final short PATHSEG_MOVETO_REL = 3;
	public static final short PATHSEG_LINETO_ABS = 4;
	public static final short PATHSEG_LINETO_REL = 5;
	public static final short PATHSEG_CURVETO_CUBIC_ABS = 6;
	public static final short PATHSEG_CURVETO_CUBIC_REL = 7;
	public static final short PATHSEG_CURVETO_QUADRATIC_ABS = 8;
	public static final short PATHSEG_CURVETO_QUADRATIC_REL = 9;
	public static final short PATHSEG_ARC_ABS = 10;
	public static final short PATHSEG_ARC_REL = 11;
	public static final short PATHSEG_LINETO_HORIZONTAL_ABS = 12;
	public static final short PATHSEG_LINETO_HORIZONTAL_REL = 13;
	public static final short PATHSEG_LINETO_VERTICAL_ABS = 14;
	public static final short PATHSEG_LINETO_VERTICAL_REL = 15;
	public static final short PATHSEG_CURVETO_CUBIC_SMOOTH_ABS = 16;
	public static final short PATHSEG_CURVETO_CUBIC_SMOOTH_REL = 17;
	public static final short PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS = 18;
	public static final short PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL = 19;

	public short getPathSegType();

	public String getPathSegTypeAsLetter();

	float getX();

	float getY();

	void setX(float x);

	void setY(float y);
}
