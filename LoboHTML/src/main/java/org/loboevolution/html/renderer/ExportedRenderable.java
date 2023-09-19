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

class ExportedRenderable {
	/**
	 * -1 (left), 0, or +1 (right).
	 */
	public final int alignment;
	public final RBlockViewport originalTarget;

	public final BoundableRenderable renderable;

	/**
	 * Coordinates in original target, for aligned blocks.
	 */
	public final int x, y;

	/**
	 * <p>Constructor for ExportedRenderable.</p>
	 *
	 * @param originalTarget a {@link org.loboevolution.html.renderer.RBlockViewport} object.
	 * @param renderable a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 * @param x a int.
	 * @param y a int.
	 * @param alignment a int.
	 */
	public ExportedRenderable(final RBlockViewport originalTarget, final BoundableRenderable renderable, final int x,
			final int y, final int alignment) {
		super();
		this.originalTarget = originalTarget;
		this.x = x;
		this.y = y;
		this.alignment = alignment;
		this.renderable = renderable;
	}
}
