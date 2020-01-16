package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.dom.svg.SVGException;
import org.loboevolution.html.dom.svg.SVGStringList;
import org.w3c.dom.DOMException;

public class SVGStringListImpl implements SVGStringList {

	private List<String> pointList;

	public SVGStringListImpl() {
		pointList = new ArrayList<String>();
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
	public String initialize(String newItem) throws DOMException, SVGException {
		pointList = new ArrayList<String>();
		pointList.add(newItem);
		return newItem;
	}

	@Override
	public String getItem(int index) throws DOMException {
		return pointList.get(index);
	}

	@Override
	public String insertItemBefore(String newItem, int index) throws DOMException, SVGException {

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
	public String replaceItem(String newItem, int index) throws DOMException, SVGException {

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
	public String removeItem(int index) throws DOMException {
		return pointList.remove(index);
	}

	@Override
	public String appendItem(String newItem) throws DOMException, SVGException {
		pointList.add(newItem);
		return newItem;
	}
}