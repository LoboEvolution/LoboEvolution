package org.lobobrowser.html.svgimpl;

import java.util.ArrayList;
import java.util.List;

import org.lobobrowser.w3c.svg.SVGException;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGPointList;
import org.w3c.dom.DOMException;

public class SVGPointListImpl implements SVGPointList {

	private List<SVGPoint> points;

	public SVGPointListImpl() {
		this(new SVGPoint[0]);
	}

	public SVGPointListImpl(SVGPoint[] points) {
		this.points = new ArrayList<SVGPoint>();
		for (SVGPoint s : points) {
			this.points.add(s);
		}
	}

	@Override
	public int getNumberOfItems() {
		return points.size();
	}

	@Override
	public void clear() throws DOMException {
		points.clear();
	}

	@Override
	public SVGPoint initialize(SVGPoint newItem) throws DOMException, SVGException {
		points = new ArrayList<SVGPoint>();
		points.add(newItem);
		return newItem;
	}

	@Override
	public SVGPoint getItem(int index) throws DOMException {
		return points.get(index);
	}

	@Override
	public SVGPoint insertItemBefore(SVGPoint newItem, int index) throws DOMException, SVGException {
		points.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGPoint replaceItem(SVGPoint newItem, int index) throws DOMException, SVGException {
		points.remove(index);
		points.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGPoint removeItem(int index) throws DOMException {
		SVGPoint item = points.get(index);
		points.remove(index);
		return item;
	}

	@Override
	public SVGPoint appendItem(SVGPoint newItem) throws DOMException, SVGException {
		points.add(newItem);
		return newItem;
	}
}