/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.renderer;

import java.util.Comparator;

/**
 * <p>ZIndexComparator class.</p>
 *
 *
 *
 */
public class ZIndexComparator implements Comparator<Object> {

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
