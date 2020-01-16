package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGLength;
import org.loboevolution.html.dom.svg.SVGLengthList;
import org.w3c.dom.DOMException;

public class SVGLengthListImpl implements SVGLengthList {

	private List<SVGLength> pointList;

	public SVGLengthListImpl() {
		pointList = new ArrayList<SVGLength>();
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
	public SVGLength initialize(SVGLength newItem) throws DOMException, SVGException {
		pointList = new ArrayList<SVGLength>();
		pointList.add(newItem);
		return newItem;
	}

	@Override
	public SVGLength getItem(int index) throws DOMException {
		return pointList.get(index);
	}

	@Override
	public SVGLength insertItemBefore(SVGLength newItem, int index) throws DOMException, SVGException {

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
	public SVGLength replaceItem(SVGLength newItem, int index) throws DOMException, SVGException {

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
	public SVGLength removeItem(int index) throws DOMException {
		return pointList.remove(index);
	}

	@Override
	public SVGLength appendItem(SVGLength newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}