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

package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGLength;
import org.loboevolution.html.dom.svg.SVGLengthList;
import org.w3c.dom.DOMException;

/**
 * <p>SVGLengthListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGLengthListImpl implements SVGLengthList {

	private List<SVGLength> pointList;

	/**
	 * <p>Constructor for SVGLengthListImpl.</p>
	 */
	public SVGLengthListImpl() {
		pointList = new ArrayList<>();
	}

	/** {@inheritDoc} */
	@Override
	public int getNumberOfItems() {
		return pointList.size();
	}

	/** {@inheritDoc} */
	@Override
	public void clear() throws DOMException {
		pointList.clear();
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength initialize(SVGLength newItem) throws DOMException, SVGException {
		pointList = new ArrayList<>();
		pointList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength getItem(int index) throws DOMException {
		return pointList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength insertItemBefore(SVGLength newItem, int index) throws DOMException, SVGException {

		pointList.remove(newItem);

		if (index < 0) {
			pointList.add(0, newItem);
		} else if (index > getNumberOfItems() - 1) { // insert at end
			pointList.add(newItem);
		} else {
			pointList.add(index, newItem);
		}
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength replaceItem(SVGLength newItem, int index) throws DOMException, SVGException {

		pointList.remove(newItem);

		if (index < 0 || index > getNumberOfItems() - 1) {
			return null;
		}

		pointList.remove(index);
		pointList.add(index, newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength removeItem(int index) throws DOMException {
		return pointList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength appendItem(SVGLength newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}
