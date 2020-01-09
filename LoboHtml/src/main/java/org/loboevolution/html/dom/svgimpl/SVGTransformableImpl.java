package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGTransformList;
import org.loboevolution.html.dom.svg.SVGTransformable;
import org.loboevolution.html.style.AbstractCSSProperties;

public class SVGTransformableImpl extends SVGLocatableImpl implements SVGTransformable {

	public SVGTransformableImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedTransformList getTransform() {
		AbstractCSSProperties style = getStyle();
		String transformString = Strings.isNotBlank(style.getTransform()) ? style.getTransform() : this.getAttribute("transform");
		SVGTransformList createTransformList = SVGTransformListImpl.createTransformList(transformString);
		return new SVGAnimatedTransformListImpl((SVGTransformListImpl) createTransformList);
	}
}
