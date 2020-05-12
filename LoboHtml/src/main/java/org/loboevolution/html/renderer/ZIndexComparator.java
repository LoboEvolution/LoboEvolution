package org.loboevolution.html.renderer;

import java.util.Comparator;

class ZIndexComparator implements Comparator<Object> {

	/** {@inheritDoc} */
	@Override
	public int compare(Object object1, Object object2) {
		final PositionedRenderable element1 = (PositionedRenderable) object1;
		final PositionedRenderable element2 = (PositionedRenderable) object2;
		final int zIndex1 = element1.getRenderable().getZIndex();
		final int zIndex2 = element2.getRenderable().getZIndex();
		final int diff = zIndex1 - zIndex2;
		if (diff != 0) {
			return diff;
		}
		return element1.getOrdinal() - element2.getOrdinal();
	}
}
