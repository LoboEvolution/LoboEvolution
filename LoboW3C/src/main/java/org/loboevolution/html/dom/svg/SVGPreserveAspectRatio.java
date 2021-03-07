
/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.svg;



/**
 * <p>SVGPreserveAspectRatio interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGPreserveAspectRatio {
	// Alignment Types
	/** Constant SVG_PRESERVEASPECTRATIO_UNKNOWN=0 */
    short SVG_PRESERVEASPECTRATIO_UNKNOWN = 0;
	/** Constant SVG_PRESERVEASPECTRATIO_NONE=1 */
    short SVG_PRESERVEASPECTRATIO_NONE = 1;
	/** Constant SVG_PRESERVEASPECTRATIO_XMINYMIN=2 */
    short SVG_PRESERVEASPECTRATIO_XMINYMIN = 2;
	/** Constant SVG_PRESERVEASPECTRATIO_XMIDYMIN=3 */
    short SVG_PRESERVEASPECTRATIO_XMIDYMIN = 3;
	/** Constant SVG_PRESERVEASPECTRATIO_XMAXYMIN=4 */
    short SVG_PRESERVEASPECTRATIO_XMAXYMIN = 4;
	/** Constant SVG_PRESERVEASPECTRATIO_XMINYMID=5 */
    short SVG_PRESERVEASPECTRATIO_XMINYMID = 5;
	/** Constant SVG_PRESERVEASPECTRATIO_XMIDYMID=6 */
    short SVG_PRESERVEASPECTRATIO_XMIDYMID = 6;
	/** Constant SVG_PRESERVEASPECTRATIO_XMAXYMID=7 */
    short SVG_PRESERVEASPECTRATIO_XMAXYMID = 7;
	/** Constant SVG_PRESERVEASPECTRATIO_XMINYMAX=8 */
    short SVG_PRESERVEASPECTRATIO_XMINYMAX = 8;
	/** Constant SVG_PRESERVEASPECTRATIO_XMIDYMAX=9 */
    short SVG_PRESERVEASPECTRATIO_XMIDYMAX = 9;
	/** Constant SVG_PRESERVEASPECTRATIO_XMAXYMAX=10 */
    short SVG_PRESERVEASPECTRATIO_XMAXYMAX = 10;
	// Meet-or-slice Types
	/** Constant SVG_MEETORSLICE_UNKNOWN=0 */
    short SVG_MEETORSLICE_UNKNOWN = 0;
	/** Constant SVG_MEETORSLICE_MEET=1 */
    short SVG_MEETORSLICE_MEET = 1;
	/** Constant SVG_MEETORSLICE_SLICE=2 */
    short SVG_MEETORSLICE_SLICE = 2;

	/**
	 * <p>getAlign.</p>
	 *
	 * @return a short.
	 */
	short getAlign();

	/**
	 * <p>setAlign.</p>
	 *
	 * @param align a short.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setAlign(short align);

	/**
	 * <p>getMeetOrSlice.</p>
	 *
	 * @return a short.
	 */
	short getMeetOrSlice();

	/**
	 * <p>setMeetOrSlice.</p>
	 *
	 * @param meetOrSlice a short.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setMeetOrSlice(short meetOrSlice);
}
