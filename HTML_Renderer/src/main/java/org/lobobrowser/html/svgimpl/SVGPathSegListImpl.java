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
 */
package org.lobobrowser.html.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.lobobrowser.w3c.svg.SVGException;
import org.lobobrowser.w3c.svg.SVGPathSeg;
import org.lobobrowser.w3c.svg.SVGPathSegList;
import org.w3c.dom.DOMException;

public class SVGPathSegListImpl implements SVGPathSegList {

	private List<SVGPathSeg> segList;

	public SVGPathSegListImpl() {
		this(new SVGPathSeg[0]);
	}

	public SVGPathSegListImpl(SVGPathSeg[] points) {
		this.segList = new ArrayList<SVGPathSeg>();
		for (SVGPathSeg s : segList) {
			this.segList.add(s);
		}
	}

	@Override
	public int getNumberOfItems() {
		return segList.size();
	}

	@Override
	public void clear() throws DOMException {
		segList.clear();
	}

	@Override
	public SVGPathSeg initialize(SVGPathSeg newItem) throws DOMException, SVGException {
		segList = new ArrayList<SVGPathSeg>();
		segList.add(newItem);
		return newItem;
	}

	@Override
	public SVGPathSeg getItem(int index) throws DOMException {
		return segList.get(index);
	}

	@Override
	public SVGPathSeg insertItemBefore(SVGPathSeg newItem, int index) throws DOMException, SVGException {
		segList.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGPathSeg replaceItem(SVGPathSeg newItem, int index) throws DOMException, SVGException {
		segList.remove(index);
		segList.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGPathSeg removeItem(int index) throws DOMException {
		SVGPathSeg item = segList.get(index);
		segList.remove(index);
		return item;
	}

	@Override
	public SVGPathSeg appendItem(SVGPathSeg newItem) throws DOMException, SVGException {
		segList.add(newItem);
		return newItem;
	}
}
