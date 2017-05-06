package org.lobobrowser.html.svgimpl;

import org.lobobrowser.w3c.svg.SVGAnimatedString;
import org.w3c.dom.DOMException;

public class SVGAnimatedStringImpl implements SVGAnimatedString {

	private String href;

	public SVGAnimatedStringImpl(String href) {
		this.href = href;
	}

	@Override
	public String getBaseVal() {
		return href;
	}

	@Override
	public void setBaseVal(String baseVal) throws DOMException {
		this.href = baseVal;

	}

	@Override
	public String getAnimVal() {
		return href;
	}

}
