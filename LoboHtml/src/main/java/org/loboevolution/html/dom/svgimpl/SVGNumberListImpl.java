package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGNumber;
import org.loboevolution.html.dom.svg.SVGNumberList;
import org.w3c.dom.DOMException;

/**
 * <p>SVGNumberListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGNumberListImpl implements SVGNumberList {

	private List<SVGNumber> pointList;

	/**
	 * <p>Constructor for SVGNumberListImpl.</p>
	 */
	public SVGNumberListImpl() {
		pointList = new ArrayList<SVGNumber>();
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
	public SVGNumber initialize(SVGNumber newItem) throws DOMException, SVGException {
		pointList = new ArrayList<SVGNumber>();
		pointList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGNumber getItem(int index) throws DOMException {
		return pointList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGNumber insertItemBefore(SVGNumber newItem, int index) throws DOMException, SVGException {

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
	public SVGNumber replaceItem(SVGNumber newItem, int index) throws DOMException, SVGException {

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
	public SVGNumber removeItem(int index) throws DOMException {
		return pointList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGNumber appendItem(SVGNumber newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}
