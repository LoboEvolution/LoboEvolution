package org.loboevolution.html.dom.svgimpl;

import java.awt.geom.AffineTransform;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGTransform;
import org.loboevolution.html.dom.svg.SVGTransformList;
import org.w3c.dom.DOMException;

public class SVGTransformListImpl implements SVGTransformList {

	@Override
	public int getNumberOfItems() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public SVGTransform initialize(SVGTransform newItem) throws DOMException, SVGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform getItem(int index) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform insertItemBefore(SVGTransform newItem, int index) throws DOMException, SVGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform replaceItem(SVGTransform newItem, int index) throws DOMException, SVGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform removeItem(int index) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform appendItem(SVGTransform newItem) throws DOMException, SVGException {
		// TODO Auto-generated method stub
		return null;
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

	protected AffineTransform getAffineTransform() {
		int numTransforms = getNumberOfItems();
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
