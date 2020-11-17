package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGPathSeg;
import org.loboevolution.html.dom.svg.SVGPathSegList;
import org.w3c.dom.DOMException;

/**
 * <p>SVGPathSegListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
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
	public SVGPathSegListImpl(SVGPathSegList pathSegList) {
		pointList = new ArrayList<>();
		for (SVGPathSeg seg : pointList) {
			switch (seg.getPathSegType()) {
			case SVGPathSeg.PATHSEG_CLOSEPATH: {
				SVGPathSeg newSeg = new SVGPathSegClosePathImpl();
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_MOVETO_ABS: {
				float x = ((SVGPathSegMovetoAbsImpl) seg).getX();
				float y = ((SVGPathSegMovetoAbsImpl) seg).getY();
				SVGPathSeg newSeg = new SVGPathSegMovetoAbsImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_MOVETO_REL: {
				float x = ((SVGPathSegMovetoRelImpl) seg).getX();
				float y = ((SVGPathSegMovetoRelImpl) seg).getY();
				SVGPathSeg newSeg = new SVGPathSegMovetoRelImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_ABS: {
				float x = ((SVGPathSegLinetoAbsImpl) seg).getX();
				float y = ((SVGPathSegLinetoAbsImpl) seg).getY();
				SVGPathSeg newSeg = new SVGPathSegLinetoAbsImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_REL: {
				float x = ((SVGPathSegLinetoRelImpl) seg).getX();
				float y = ((SVGPathSegLinetoRelImpl) seg).getY();
				SVGPathSeg newSeg = new SVGPathSegLinetoRelImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_ABS: {
				float x = ((SVGPathSegLinetoHorizontalAbsImpl) seg).getX();
				SVGPathSeg newSeg = new SVGPathSegLinetoHorizontalAbsImpl(x);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_REL: {
				float x = ((SVGPathSegLinetoHorizontalRelImpl) seg).getX();
				SVGPathSeg newSeg = new SVGPathSegLinetoHorizontalRelImpl(x);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_VERTICAL_ABS: {
				float y = ((SVGPathSegLinetoVerticalAbsImpl) seg).getY();
				SVGPathSeg newSeg = new SVGPathSegLinetoVerticalAbsImpl(y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_LINETO_VERTICAL_REL: {
				float y = ((SVGPathSegLinetoVerticalRelImpl) seg).getY();
				SVGPathSeg newSeg = new SVGPathSegLinetoVerticalRelImpl(y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_CUBIC_ABS: {
				float x = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX();
				float y = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY();
				float x1 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX1();
				float y1 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY1();
				float x2 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX2();
				float y2 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY2();
				SVGPathSeg newSeg = new SVGPathSegCurvetoCubicAbsImpl(x, y, x1, y1, x2, y2);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_CUBIC_REL: {
				float x = ((SVGPathSegCurvetoCubicRelImpl) seg).getX();
				float y = ((SVGPathSegCurvetoCubicRelImpl) seg).getY();
				float x1 = ((SVGPathSegCurvetoCubicRelImpl) seg).getX1();
				float y1 = ((SVGPathSegCurvetoCubicRelImpl) seg).getY1();
				float x2 = ((SVGPathSegCurvetoCubicRelImpl) seg).getX2();
				float y2 = ((SVGPathSegCurvetoCubicRelImpl) seg).getY2();
				SVGPathSeg newSeg = new SVGPathSegCurvetoCubicRelImpl(x, y, x1, y1, x2, y2);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_ABS: {
				float x = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getX();
				float y = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getY();
				float x2 = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getX2();
				float y2 = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getY2();
				SVGPathSeg newSeg = new SVGPathSegCurvetoCubicSmoothAbsImpl(x, y, x2, y2);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_REL: {
				float x = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getX();
				float y = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getY();
				float x2 = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getX2();
				float y2 = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getY2();
				SVGPathSeg newSeg = new SVGPathSegCurvetoCubicSmoothRelImpl(x, y, x2, y2);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_ABS: {
				float x = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getX();
				float y = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getY();
				float x1 = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getX1();
				float y1 = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getY1();
				SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticAbsImpl(x, y, x1, y1);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_REL: {
				float x = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getX();
				float y = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getY();
				float x1 = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getX1();
				float y1 = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getY1();
				SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticRelImpl(x, y, x1, y1);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS: {
				float x = ((SVGPathSegCurvetoQuadraticSmoothAbsImpl) seg).getX();
				float y = ((SVGPathSegCurvetoQuadraticSmoothAbsImpl) seg).getY();
				SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticSmoothAbsImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL: {
				float x = ((SVGPathSegCurvetoQuadraticSmoothRelImpl) seg).getX();
				float y = ((SVGPathSegCurvetoQuadraticSmoothRelImpl) seg).getY();
				SVGPathSeg newSeg = new SVGPathSegCurvetoQuadraticSmoothRelImpl(x, y);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_ARC_ABS: {
				float x = ((SVGPathSegArcAbsImpl) seg).getX();
				float y = ((SVGPathSegArcAbsImpl) seg).getY();
				float r1 = ((SVGPathSegArcAbsImpl) seg).getR1();
				float r2 = ((SVGPathSegArcAbsImpl) seg).getR2();
				float angle = ((SVGPathSegArcAbsImpl) seg).getAngle();
				boolean largeArcFlag = ((SVGPathSegArcAbsImpl) seg).getLargeArcFlag();
				boolean sweepFlag = ((SVGPathSegArcAbsImpl) seg).getSweepFlag();
				SVGPathSeg newSeg = new SVGPathSegArcAbsImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag);
				appendItem(newSeg);
				break;
			}

			case SVGPathSeg.PATHSEG_ARC_REL: {
				float x = ((SVGPathSegArcRelImpl) seg).getX();
				float y = ((SVGPathSegArcRelImpl) seg).getY();
				float r1 = ((SVGPathSegArcRelImpl) seg).getR1();
				float r2 = ((SVGPathSegArcRelImpl) seg).getR2();
				float angle = ((SVGPathSegArcRelImpl) seg).getAngle();
				boolean largeArcFlag = ((SVGPathSegArcRelImpl) seg).getLargeArcFlag();
				boolean sweepFlag = ((SVGPathSegArcRelImpl) seg).getSweepFlag();
				SVGPathSeg newSeg = new SVGPathSegArcRelImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag);
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
	public void clear() throws DOMException {
		pointList.clear();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSeg initialize(SVGPathSeg newItem) throws DOMException, SVGException {
		pointList = new ArrayList<>();
		pointList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSeg getItem(int index) throws DOMException {
		return pointList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSeg insertItemBefore(SVGPathSeg newItem, int index) throws DOMException, SVGException {

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
	public SVGPathSeg replaceItem(SVGPathSeg newItem, int index) throws DOMException, SVGException {

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
	public SVGPathSeg removeItem(int index) throws DOMException {
		return pointList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSeg appendItem(SVGPathSeg newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}
