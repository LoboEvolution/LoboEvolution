/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

/**
 * Contains a renderer node and a position in that node. */
public class RenderableSpot {
	public final BoundableRenderable renderable;
	public final int x;
	public final int y;

	/**
	 * <p>Constructor for RenderableSpot.</p>
	 *
	 * @param renderable a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 */
	public RenderableSpot(final BoundableRenderable renderable, final int x, final int y) {
		super();
		this.renderable = renderable;
		this.x = x;
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof RenderableSpot otherRp)) {
			return false;
		}
        return otherRp.renderable == this.renderable && otherRp.x == this.x && otherRp.y == this.y;
	}

	/**
	 * <p>getPoint.</p>
	 *
	 * @return a {@link java.awt.Point} object.
	 */
	public Point getPoint() {
		return new Point(this.x, this.y);
	}
}
