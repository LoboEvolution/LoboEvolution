package org.loboevolution.html.buildersvg;

import org.loboevolution.html.builder.HTMLElementBuilder;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.svgimpl.SVGPolygonElementImpl;

public class SVGPolygonBuilder extends HTMLElementBuilder {

	@Override
	protected HTMLElementImpl build(String name) {
		return new SVGPolygonElementImpl(name);
	}
}
