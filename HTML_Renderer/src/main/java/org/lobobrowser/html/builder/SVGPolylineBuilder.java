package org.lobobrowser.html.builder;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.svgimpl.SVGPolylineElementImpl;

public class SVGPolylineBuilder  extends HTMLElementBuilder {

	
	@Override
	protected HTMLElementImpl build(String name) {
		return new SVGPolylineElementImpl(name);
	}
}
