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
package org.lobobrowser.html.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.lobobrowser.w3c.svg.SVGException;
import org.lobobrowser.w3c.svg.SVGMatrix;
import org.lobobrowser.w3c.svg.SVGTransform;
import org.lobobrowser.w3c.svg.SVGTransformList;
import org.w3c.dom.DOMException;

public class SVGTransformListImpl implements SVGTransformList {

	private List<SVGTransform> points;

	public SVGTransformListImpl() {
		this(new SVGTransform[0]);
	}

	public SVGTransformListImpl(SVGTransform[] points) {
		this.points = new ArrayList<SVGTransform>();
		for (SVGTransform s : points) {
			this.points.add(s);
		}
	}

	@Override
	public int getNumberOfItems() {
		return points.size();
	}

	@Override
	public void clear() throws DOMException {
		points.clear();
	}

	@Override
	public SVGTransform initialize(SVGTransform newItem) throws DOMException, SVGException {
		points = new ArrayList<SVGTransform>();
		points.add(newItem);
		return newItem;
	}

	@Override
	public SVGTransform getItem(int index) throws DOMException {
		return points.get(index);
	}

	@Override
	public SVGTransform insertItemBefore(SVGTransform newItem, int index) throws DOMException, SVGException {
		points.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGTransform replaceItem(SVGTransform newItem, int index) throws DOMException, SVGException {
		points.remove(index);
		points.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGTransform removeItem(int index) throws DOMException {
		SVGTransform item = points.get(index);
		points.remove(index);
		return item;
	}

	@Override
	public SVGTransform appendItem(SVGTransform newItem) throws DOMException, SVGException {
		points.add(newItem);
		return newItem;
	}

	@Override
	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform consolidate() {
		// TODO Auto-generated method stub
		return null;
	}

}
