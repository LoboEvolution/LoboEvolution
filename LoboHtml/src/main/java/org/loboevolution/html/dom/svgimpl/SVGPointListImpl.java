package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGPoint;
import org.loboevolution.html.dom.svg.SVGPointList;
import org.w3c.dom.DOMException;

public class SVGPointListImpl implements SVGPointList {

	private List<SVGPoint> pointList;

	public SVGPointListImpl() {
		pointList = new ArrayList<SVGPoint>();
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
	public SVGPoint initialize(SVGPoint newItem) throws DOMException, SVGException {
		pointList = new ArrayList<SVGPoint>();
		pointList.add(newItem);
		return newItem;
	}

	@Override
	public SVGPoint getItem(int index) throws DOMException {
		return pointList.get(index);
	}

	@Override
	public SVGPoint insertItemBefore(SVGPoint newItem, int index) throws DOMException, SVGException {

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
	public SVGPoint replaceItem(SVGPoint newItem, int index) throws DOMException, SVGException {

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
	public SVGPoint removeItem(int index) throws DOMException {
		return pointList.remove(index);
	}

	@Override
	public SVGPoint appendItem(SVGPoint newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}