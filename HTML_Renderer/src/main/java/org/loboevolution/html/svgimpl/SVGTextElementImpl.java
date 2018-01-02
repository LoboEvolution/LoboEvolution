package org.loboevolution.html.svgimpl;

import org.loboevolution.w3c.svg.SVGAnimatedEnumeration;
import org.loboevolution.w3c.svg.SVGAnimatedLength;
import org.loboevolution.w3c.svg.SVGAnimatedLengthList;
import org.loboevolution.w3c.svg.SVGAnimatedNumberList;
import org.loboevolution.w3c.svg.SVGPoint;
import org.loboevolution.w3c.svg.SVGRect;
import org.loboevolution.w3c.svg.SVGTextElement;
import org.w3c.dom.DOMException;

public class SVGTextElementImpl extends SVGSVGElementImpl implements SVGTextElement {

	public SVGTextElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedLengthList getDx() {
		return new SVGAnimatedLengthListImpl(
				SVGUtility.constructLengthList(this.getAttribute(DX)));
	}

	@Override
	public SVGAnimatedLengthList getDy() {
		return new SVGAnimatedLengthListImpl(
				SVGUtility.constructLengthList(this.getAttribute(DY)));
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
		return this.getAttribute(TEXTANCHOR);
	}
}
