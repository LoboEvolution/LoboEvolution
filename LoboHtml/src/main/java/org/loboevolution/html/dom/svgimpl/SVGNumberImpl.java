package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGNumber;
import org.w3c.dom.DOMException;

public class SVGNumberImpl implements SVGNumber {
	
	private float value;

	public SVGNumberImpl() {
		this.value= 0;
	}
	
	public SVGNumberImpl(float value) {
		this.value = value;
	}
	@Override
	public float getValue() {
		return value;
	}

	@Override
	public void setValue(float value) throws DOMException {
		this.value = value;
	}
}