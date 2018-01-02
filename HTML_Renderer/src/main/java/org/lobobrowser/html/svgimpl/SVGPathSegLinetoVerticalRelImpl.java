/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
 */package org.lobobrowser.html.svgimpl;

import org.lobobrowser.w3c.svg.SVGPathSeg;
import org.lobobrowser.w3c.svg.SVGPathSegLinetoVerticalRel;

public class SVGPathSegLinetoVerticalRelImpl extends SVGPathSegImpl implements SVGPathSegLinetoVerticalRel {

	private static final long serialVersionUID = -6722776032077341870L;

	/**
	 * @param y
	 */
	public SVGPathSegLinetoVerticalRelImpl(float y) {
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return SVGPathSeg.PATHSEG_LINETO_VERTICAL_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {

		return "v";
	}
}
