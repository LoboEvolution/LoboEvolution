package org.loboevolution.html.buildersvg;

import org.loboevolution.html.builder.HTMLElementBuilder;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.svgimpl.SVGCircleElementImpl;

public class SVGCircleBuilder extends HTMLElementBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.builder.HTMLElementBuilder#build(java.lang.String)
	 */
	@Override
	protected HTMLElementImpl build(String name) {
		return new SVGCircleElementImpl(name);
	}
}