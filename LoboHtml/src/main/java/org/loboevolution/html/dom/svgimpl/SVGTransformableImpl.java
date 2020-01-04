package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGTransformable;
import org.loboevolution.html.style.AbstractCSSProperties;

public class SVGTransformableImpl extends SVGLocatableImpl implements SVGTransformable {

	public SVGTransformableImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedTransformList getTransform() {
		return new SVGAnimatedTransformListImpl(new SVGTransformListImpl());
	}

	@Override
	public AbstractCSSProperties getSVGStyle() {
		// TODO Auto-generated method stub
		return null;
	}

}
