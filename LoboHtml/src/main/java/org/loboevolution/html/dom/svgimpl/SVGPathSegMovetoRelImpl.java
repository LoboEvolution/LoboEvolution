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
 */package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSeg;
import org.loboevolution.html.dom.svg.SVGPathSegMovetoRel;

public class SVGPathSegMovetoRelImpl extends SVGPathSegImpl implements SVGPathSegMovetoRel {

	private static final long serialVersionUID = -6722776032077341870L;

	/**
	 * @param x
	 * @param y
	 */
	public SVGPathSegMovetoRelImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		return SVGPathSeg.PATHSEG_MOVETO_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "m";
	}
}
