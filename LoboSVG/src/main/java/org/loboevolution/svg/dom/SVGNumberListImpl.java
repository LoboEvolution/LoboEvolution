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
import org.loboevolution.svg.SVGNumber;
import org.loboevolution.svg.SVGNumberList;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>SVGNumberListImpl class.</p>
 */
public class SVGNumberListImpl implements SVGNumberList {

	private List<SVGNumber> pointList;

	/**
	 * <p>Constructor for SVGNumberListImpl.</p>
	 */
	public SVGNumberListImpl() {
		pointList = new ArrayList<>();
	}

	/**
	 * <p>Constructor for SVGNumberListImpl.</p>
	 *
	 * @param numb a {@link java.lang.String} object.
	 */
	public SVGNumberListImpl(final String numb) {
		pointList = new ArrayList<>();
		final SVGNumber number = new SVGNumberImpl(numb);
		pointList.add(number);
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
	public SVGNumber initialize(final SVGNumber newItem) throws DOMException, SVGException {
		pointList = new ArrayList<>();
		pointList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGNumber getItem(final int index) {
		return pointList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGNumber insertItemBefore(final SVGNumber newItem, final int index) throws DOMException, SVGException {

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
	public SVGNumber replaceItem(final SVGNumber newItem, final int index) throws DOMException, SVGException {

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
	public SVGNumber removeItem(final int index) {
		return pointList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGNumber appendItem(final SVGNumber newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}
