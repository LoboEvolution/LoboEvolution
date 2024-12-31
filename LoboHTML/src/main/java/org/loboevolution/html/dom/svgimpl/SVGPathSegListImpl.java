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

package org.loboevolution.html.dom.svgimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGPathSeg;
import org.loboevolution.html.dom.svg.SVGPathSegList;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>SVGPathSegListImpl class.</p>
 */
public class SVGPathSegListImpl implements SVGPathSegList {

	private List<SVGPathSeg> pointList;

	/**
	 * <p>Constructor for SVGPathSegListImpl.</p>
	 */
	public SVGPathSegListImpl() {
		pointList = new ArrayList<>();
	}

	/**
	 * <p>Constructor for SVGPathSegListImpl.</p>
	 *
	 * @param pathSegList a {@link org.loboevolution.html.dom.svg.SVGPathSegList} object.
	 */
	public SVGPathSegListImpl(final SVGPathSegList pathSegList) {
		pointList = new ArrayList<>();
		for (final SVGPathSeg seg : pointList) {
			switch (seg.getPathSegType()) {
			case SVGPathSeg.PATHSEG_CLOSEPATH: {
				final SVGPathSeg newSeg = new SVGPathSegClosePathImpl();
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_MOVETO_ABS: {
				final float x = ((SVGPathSegMovetoAbsImpl) seg).getX();
				final float y = ((SVGPathSegMovetoAbsImpl) seg).getY();
				final SVGPathSeg newSeg = new SVGPathSegMovetoAbsImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_MOVETO_REL: {
				final float x = ((SVGPathSegMovetoRelImpl) seg).getX();
				final float y = ((SVGPathSegMovetoRelImpl) seg).getY();
				final SVGPathSeg newSeg = new SVGPathSegMovetoRelImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_ABS: {
				final float x = ((SVGPathSegLinetoAbsImpl) seg).getX();
				final float y = ((SVGPathSegLinetoAbsImpl) seg).getY();
				final SVGPathSeg newSeg = new SVGPathSegLinetoAbsImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_REL: {
				final float x = ((SVGPathSegLinetoRelImpl) seg).getX();
				final float y = ((SVGPathSegLinetoRelImpl) seg).getY();
				final SVGPathSeg newSeg = new SVGPathSegLinetoRelImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_ABS: {
				final float x = ((SVGPathSegLinetoHorizontalAbsImpl) seg).getX();
				final SVGPathSeg newSeg = new SVGPathSegLinetoHorizontalAbsImpl(x);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_REL: {
				final float x = ((SVGPathSegLinetoHorizontalRelImpl) seg).getX();
				final SVGPathSeg newSeg = new SVGPathSegLinetoHorizontalRelImpl(x);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_VERTICAL_ABS: {
				final float y = ((SVGPathSegLinetoVerticalAbsImpl) seg).getY();
				final SVGPathSeg newSeg = new SVGPathSegLinetoVerticalAbsImpl(y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_VERTICAL_REL: {
				final float y = ((SVGPathSegLinetoVerticalRelImpl) seg).getY();
				final SVGPathSeg newSeg = new SVGPathSegLinetoVerticalRelImpl(y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_CUBIC_ABS: {
				final float x = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX();
				final float y = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY();
				final float x1 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX1();
				final float y1 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY1();
				final float x2 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX2();
				final float y2 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY2();
				final SVGPathSeg newSeg = new SVGPathSegCurvetoCubicAbsImpl(x, y, x1, y1, x2, y2);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_CUBIC_REL: {
				final float x = ((SVGPathSegCurvetoCubicRelImpl) seg).getX();
				final float y = ((SVGPathSegCurvetoCubicRelImpl) seg).getY();
				final float x1 = ((SVGPathSegCurvetoCubicRelImpl) seg).getX1();
				final float y1 = ((SVGPathSegCurvetoCubicRelImpl) seg).getY1();
				final float x2 = ((SVGPathSegCurvetoCubicRelImpl) seg).getX2();
				final float y2 = ((SVGPathSegCurvetoCubicRelImpl) seg).getY2();
				final SVGPathSeg newSeg = new SVGPathSegCurvetoCubicRelImpl(x, y, x1, y1, x2, y2);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_ABS: {
				final float x = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getX();
				final float y = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getY();
				final float x2 = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getX2();
				final float y2 = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getY2();
				final SVGPathSeg newSeg = new SVGPathSegCurvetoCubicSmoothAbsImpl(x, y, x2, y2);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_REL: {
				final float x = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getX();
				final float y = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getY();
				final float x2 = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getX2();
				final float y2 = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getY2();
				final SVGPathSeg newSeg = new SVGPathSegCurvetoCubicSmoothRelImpl(x, y, x2, y2);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_ABS: {
				final float x = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getX();
				final float y = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getY();
				final float x1 = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getX1();
				final float y1 = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getY1();
				final SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticAbsImpl(x, y, x1, y1);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_REL: {
				final float x = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getX();
				final float y = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getY();
				final float x1 = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getX1();
				final float y1 = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getY1();
				final SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticRelImpl(x, y, x1, y1);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS: {
				final float x = ((SVGPathSegCurvetoQuadraticSmoothAbsImpl) seg).getX();
				final float y = ((SVGPathSegCurvetoQuadraticSmoothAbsImpl) seg).getY();
				final SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticSmoothAbsImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL: {
				final float x = ((SVGPathSegCurvetoQuadraticSmoothRelImpl) seg).getX();
				final float y = ((SVGPathSegCurvetoQuadraticSmoothRelImpl) seg).getY();
				final SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticSmoothRelImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_ARC_ABS: {
				final float x = ((SVGPathSegArcAbsImpl) seg).getX();
				final float y = ((SVGPathSegArcAbsImpl) seg).getY();
				final float r1 = ((SVGPathSegArcAbsImpl) seg).getR1();
				final float r2 = ((SVGPathSegArcAbsImpl) seg).getR2();
				final float angle = ((SVGPathSegArcAbsImpl) seg).getAngle();
				final boolean largeArcFlag = ((SVGPathSegArcAbsImpl) seg).getLargeArcFlag();
				final boolean sweepFlag = ((SVGPathSegArcAbsImpl) seg).getSweepFlag();
				final SVGPathSeg newSeg = new SVGPathSegArcAbsImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_ARC_REL: {
				final float x = ((SVGPathSegArcRelImpl) seg).getX();
				final float y = ((SVGPathSegArcRelImpl) seg).getY();
				final float r1 = ((SVGPathSegArcRelImpl) seg).getR1();
				final float r2 = ((SVGPathSegArcRelImpl) seg).getR2();
				final float angle = ((SVGPathSegArcRelImpl) seg).getAngle();
				final boolean largeArcFlag = ((SVGPathSegArcRelImpl) seg).getLargeArcFlag();
				final boolean sweepFlag = ((SVGPathSegArcRelImpl) seg).getSweepFlag();
				final SVGPathSeg newSeg = new SVGPathSegArcRelImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag);
				appendItem(newSeg);
				break;
			}
			default:
				break;
			}
		}
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
	public SVGPathSeg initialize(final SVGPathSeg newItem) throws DOMException, SVGException {
		pointList = new ArrayList<>();
		pointList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSeg getItem(final int index) {
		return pointList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSeg insertItemBefore(final SVGPathSeg newItem, final int index) throws DOMException, SVGException {

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
	public SVGPathSeg replaceItem(final SVGPathSeg newItem, final int index) throws DOMException, SVGException {

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
	public SVGPathSeg removeItem(final int index) {
		return pointList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSeg appendItem(final SVGPathSeg newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}
