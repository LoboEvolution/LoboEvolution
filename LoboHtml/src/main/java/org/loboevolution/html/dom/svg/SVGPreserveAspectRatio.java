
/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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

import org.w3c.dom.DOMException;

public interface SVGPreserveAspectRatio {
	// Alignment Types
	public static final short SVG_PRESERVEASPECTRATIO_UNKNOWN = 0;
	public static final short SVG_PRESERVEASPECTRATIO_NONE = 1;
	public static final short SVG_PRESERVEASPECTRATIO_XMINYMIN = 2;
	public static final short SVG_PRESERVEASPECTRATIO_XMIDYMIN = 3;
	public static final short SVG_PRESERVEASPECTRATIO_XMAXYMIN = 4;
	public static final short SVG_PRESERVEASPECTRATIO_XMINYMID = 5;
	public static final short SVG_PRESERVEASPECTRATIO_XMIDYMID = 6;
	public static final short SVG_PRESERVEASPECTRATIO_XMAXYMID = 7;
	public static final short SVG_PRESERVEASPECTRATIO_XMINYMAX = 8;
	public static final short SVG_PRESERVEASPECTRATIO_XMIDYMAX = 9;
	public static final short SVG_PRESERVEASPECTRATIO_XMAXYMAX = 10;
	// Meet-or-slice Types
	public static final short SVG_MEETORSLICE_UNKNOWN = 0;
	public static final short SVG_MEETORSLICE_MEET = 1;
	public static final short SVG_MEETORSLICE_SLICE = 2;

	public short getAlign();

	public void setAlign(short align) throws DOMException;

	public short getMeetOrSlice();

	public void setMeetOrSlice(short meetOrSlice) throws DOMException;
}
