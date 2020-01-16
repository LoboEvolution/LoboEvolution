package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGNumber;
import org.loboevolution.html.dom.svg.SVGNumberList;
import org.w3c.dom.DOMException;

public class SVGNumberListImpl implements SVGNumberList {

	private List<SVGNumber> pointList;

	public SVGNumberListImpl() {
		pointList = new ArrayList<SVGNumber>();
	}

	@Override
	public int getNumberOfItems() {
		return pointList.size();
	}

	@Override
	public void clear() throws DOMException {
		pointList.clear();
	}

	@Override
	public SVGNumber initialize(SVGNumber newItem) throws DOMException, SVGException {
		pointList = new ArrayList<SVGNumber>();
		pointList.add(newItem);
		return newItem;
	}

	@Override
	public SVGNumber getItem(int index) throws DOMException {
		return pointList.get(index);
	}

	@Override
	public SVGNumber insertItemBefore(SVGNumber newItem, int index) throws DOMException, SVGException {

		if (pointList.contains(newItem)) {
			pointList.remove(newItem);
		}

		if (index < 0) {
			pointList.add(0, newItem);
		} else if (index > getNumberOfItems() - 1) { // insert at end
			pointList.add(newItem);
		} else {
			pointList.add(index, newItem);
		}
		return newItem;
	}

	@Override
	public SVGNumber replaceItem(SVGNumber newItem, int index) throws DOMException, SVGException {

		if (pointList.contains(newItem)) {
			pointList.remove(newItem);
		}

		if (index < 0 || index > getNumberOfItems() - 1) {
			return null;
		}

		pointList.remove(index);
		pointList.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGNumber removeItem(int index) throws DOMException {
		return pointList.remove(index);
	}

	@Override
	public SVGNumber appendItem(SVGNumber newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}