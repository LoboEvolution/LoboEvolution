/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.svg.dom;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.svg.SVGException;
import org.loboevolution.svg.SVGLength;
import org.loboevolution.svg.SVGLengthList;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>SVGLengthListImpl class.</p>
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
	public void clear() {
		pointList.clear();
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength initialize(final SVGLength newItem) throws DOMException, SVGException {
		pointList = new ArrayList<>();
		pointList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength getItem(final int index) {
		return pointList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength insertItemBefore(final SVGLength newItem, final int index) throws DOMException, SVGException {

		pointList.remove(newItem);

		if (index < 0) {
			pointList.addFirst(newItem);
		} else if (index > getNumberOfItems() - 1) { // insert at end
			pointList.add(newItem);
		} else {
			pointList.add(index, newItem);
		}
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength replaceItem(final SVGLength newItem, final int index) throws DOMException, SVGException {

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
	public SVGLength removeItem(final int index) {
		return pointList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength appendItem(final SVGLength newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}
