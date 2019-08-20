package org.lobobrowser.html.dom.svgimpl;

import org.lobobrowser.html.dom.svg.SVGAnimatedEnumeration;
import org.lobobrowser.html.dom.svg.SVGAnimatedLength;
import org.lobobrowser.html.dom.svg.SVGAnimatedLengthList;
import org.lobobrowser.html.dom.svg.SVGAnimatedNumberList;
import org.lobobrowser.html.dom.svg.SVGPoint;
import org.lobobrowser.html.dom.svg.SVGRect;
import org.lobobrowser.html.dom.svg.SVGTextElement;
import org.w3c.dom.DOMException;

public class SVGTextElementImpl extends SVGSVGElementImpl implements SVGTextElement {

	public SVGTextElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedLengthList getDx() {
		return new SVGAnimatedLengthListImpl(
				SVGUtility.constructLengthList(this.getAttribute("dx")));
	}

	@Override
	public SVGAnimatedLengthList getDy() {
		return new SVGAnimatedLengthListImpl(
				SVGUtility.constructLengthList(this.getAttribute("dy")));
	}

	@Override
	public SVGAnimatedNumberList getRotate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedLength getTextLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedEnumeration getLengthAdjust() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfChars() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getComputedTextLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSubStringLength(int charnum, int nchars) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SVGPoint getStartPositionOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGPoint getEndPositionOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGRect getExtentOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRotationOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharNumAtPosition(SVGPoint point) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void selectSubString(int charnum, int nchars) throws DOMException {
		// TODO Auto-generated method stub

	}

	public String getTextAnchor() {
		return this.getAttribute("text-anchor");
	}
}
