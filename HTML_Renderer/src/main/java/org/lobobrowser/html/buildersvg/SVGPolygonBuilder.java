package org.lobobrowser.html.buildersvg;

import org.lobobrowser.html.builder.HTMLElementBuilder;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.svgimpl.SVGPolygonElementImpl;

public class SVGPolygonBuilder extends HTMLElementBuilder {

	@Override
	protected HTMLElementImpl build(String name) {
		return new SVGPolygonElementImpl(name);
	}
}
