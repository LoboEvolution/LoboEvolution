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

package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * Render state for elements that are displayed as blocks by default.
 */
public class BlockRenderState extends StyleSheetRenderState {
	/**
	 * <p>Constructor for BlockRenderState.</p>
	 *
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	public BlockRenderState(final HTMLDocumentImpl document) {
		super(document);
	}

	/**
	 * <p>Constructor for BlockRenderState.</p>
	 *
	 * @param prevRenderState a {@link RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public BlockRenderState(final RenderState prevRenderState, final HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/** {@inheritDoc} */
	@Override
	public int getDefaultDisplay() {
		return DISPLAY_BLOCK;
	}
}
