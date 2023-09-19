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

import java.awt.*;
import java.util.Iterator;

/**
 * A {@link org.loboevolution.html.renderer.Renderable} with children.
 */
public interface RCollection extends BoundableRenderable {
	/**
	 * <p>blur.</p>
	 */
	void blur();

	/**
	 * <p>focus.</p>
	 */
	void focus();

	/**
	 * <p>invalidateLayoutDeep.</p>
	 */
	void invalidateLayoutDeep();

	/**
	 * <p>updateWidgetBounds.</p>
	 *
	 * @param guiX a int.
	 * @param guiY a int.
	 */
	void updateWidgetBounds(int guiX, int guiY);
	
	/**
	 * <p>getRenderables.</p>
	 *
	 * @return a {@link java.util.Iterator} object.
	 */
	Iterator<Renderable> getRenderables();
	
	/**
	 * <p>getRenderable.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @return a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 */
	BoundableRenderable getRenderable(final int x, final int y);
	
	/**
	 * <p>getClipBounds.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	Rectangle getClipBounds();
	
	/**
	 * <p>getClipBoundsWithoutInsets.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	Rectangle getClipBoundsWithoutInsets();
}
