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
		pointList = new ArrayList<SVGLength>();
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
		pointList = new ArrayList<SVGLength>();
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
