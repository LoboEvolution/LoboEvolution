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
package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGLength;
import org.loboevolution.html.dom.svg.SVGLengthList;
import org.w3c.dom.DOMException;

public class SVGLengthListImpl implements SVGLengthList {

	private List<SVGLength> lengthList;

	public SVGLengthListImpl() {
		this.lengthList = new ArrayList<SVGLength>();
		for (SVGLength s : lengthList) {
			this.lengthList.add(s);
		}
	}

	@Override
	public int getNumberOfItems() {
		return lengthList.size();
	}

	@Override
	public void clear() throws DOMException {
		this.lengthList.clear();

	}

	@Override
	public SVGLength initialize(SVGLength newItem) throws DOMException, SVGException {
		lengthList = new ArrayList<SVGLength>();
		lengthList.add(newItem);
		return newItem;
	}

	@Override
	public SVGLength getItem(int index) throws DOMException {
		return lengthList.get(index);
	}

	@Override
	public SVGLength insertItemBefore(SVGLength newItem, int index) throws DOMException, SVGException {
		lengthList.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGLength replaceItem(SVGLength newItem, int index) throws DOMException, SVGException {
		lengthList.remove(index);
		lengthList.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGLength removeItem(int index) throws DOMException {
		SVGLength item = lengthList.get(index);
		lengthList.remove(index);
		return item;
	}

	@Override
	public SVGLength appendItem(SVGLength newItem) throws DOMException, SVGException {
		lengthList.add(newItem);
		return newItem;
	}

}
