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
/*
 * Created on Apr 23, 2005
 */
package org.loboevolution.html.renderer;

import java.util.Collection;

/**
 * Author J. H. S.
 */
class OverflowException extends Exception {

	private static final long serialVersionUID = 1L;

	private final Collection<Renderable> renderables;

	/**
	 * <p>Constructor for OverflowException.</p>
	 *
	 * @param renderables a {@link java.util.Collection} object.
	 */
	public OverflowException(Collection<Renderable> renderables) {
		this.renderables = renderables;
	}

	/**
	 * <p>Getter for the field renderables.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<Renderable> getRenderables() {
		return this.renderables;
	}
}
