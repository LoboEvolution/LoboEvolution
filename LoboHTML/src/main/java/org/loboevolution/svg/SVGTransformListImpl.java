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

package org.loboevolution.svg;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>SVGTransformListImpl class.</p>
 */
public class SVGTransformListImpl implements SVGTransformList {
	
	private List<SVGTransform> transList;

	/**
	 * <p>Constructor for SVGTransformListImpl.</p>
	 */
	public SVGTransformListImpl() {
		transList = new ArrayList<>();
	}

	/** {@inheritDoc} */
	@Override
	public int getNumberOfItems() {
		return transList.size();
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		transList.clear();
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform initialize(final SVGTransform newItem) throws SVGException {
		transList = new ArrayList<>();
		transList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform getItem(final int index) {
		return transList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform insertItemBefore(final SVGTransform newItem, final int index) throws SVGException {

        transList.remove(newItem);

		if (index < 0) {
			transList.addFirst(newItem);
		} else if (index > getNumberOfItems() - 1) { // insert at end
			transList.add(newItem);
		} else {
			transList.add(index, newItem);
		}
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform replaceItem(final SVGTransform newItem, final int index) throws SVGException {

        transList.remove(newItem);

		if (index < 0 || index > getNumberOfItems() - 1) {
			return null;
		}

		transList.remove(index);
		transList.add(index, newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform removeItem(final int index) {
		return transList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform appendItem(final SVGTransform newItem) throws SVGException {
		transList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform createSVGTransformFromMatrix(final SVGMatrix matrix) {
		final SVGTransform transform = new SVGTransformImpl();
		transform.setMatrix(matrix);
		return transform;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform consolidate() {
		final int numTransforms = getNumberOfItems();
		if (numTransforms == 0) {
			return null;
		}
		final SVGTransform transform = getItem(0);
		SVGMatrix result = transform.getMatrix();
		for (final SVGTransform svgTransform : transList) {
			result = result.multiply(svgTransform.getMatrix());
		}
		final SVGTransform newTransform = new SVGTransformImpl();
		newTransform.setMatrix(result);
		initialize(newTransform);
		return newTransform;
	}

	/**
	 * <p>getAffineTransform.</p>
	 *
	 * @return a {@link AffineTransform} object.
	 */
	protected AffineTransform getAffineTransform() {
		final int numTransforms = getNumberOfItems();
		if (numTransforms == 0) {
			return new AffineTransform();
		}

		SVGTransform transform = getItem(0);
		SVGMatrix result = transform.getMatrix();
		for (int i = 1; i < numTransforms; i++) {
			transform = getItem(i);
			result = result.multiply(transform.getMatrix());
		}
		return new AffineTransform(result.getA(), result.getB(), result.getC(), result.getD(), result.getE(), result.getF());
	}
}
