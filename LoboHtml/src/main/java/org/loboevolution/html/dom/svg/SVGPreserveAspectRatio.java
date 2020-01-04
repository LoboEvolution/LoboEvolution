
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
	static final short SVG_PRESERVEASPECTRATIO_UNKNOWN = 0;
	static final short SVG_PRESERVEASPECTRATIO_NONE = 1;
	static final short SVG_PRESERVEASPECTRATIO_XMINYMIN = 2;
	static final short SVG_PRESERVEASPECTRATIO_XMIDYMIN = 3;
	static final short SVG_PRESERVEASPECTRATIO_XMAXYMIN = 4;
	static final short SVG_PRESERVEASPECTRATIO_XMINYMID = 5;
	static final short SVG_PRESERVEASPECTRATIO_XMIDYMID = 6;
	static final short SVG_PRESERVEASPECTRATIO_XMAXYMID = 7;
	static final short SVG_PRESERVEASPECTRATIO_XMINYMAX = 8;
	static final short SVG_PRESERVEASPECTRATIO_XMIDYMAX = 9;
	static final short SVG_PRESERVEASPECTRATIO_XMAXYMAX = 10;
	// Meet-or-slice Types
	static final short SVG_MEETORSLICE_UNKNOWN = 0;
	static final short SVG_MEETORSLICE_MEET = 1;
	static final short SVG_MEETORSLICE_SLICE = 2;

	short getAlign();

	void setAlign(short align) throws DOMException;

	short getMeetOrSlice();

	void setMeetOrSlice(short meetOrSlice) throws DOMException;
}
