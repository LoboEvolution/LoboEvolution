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

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * <p>AlignXRenderState class.</p>
 */
public class AlignXRenderState extends BlockRenderState {
	private final int alignXPercent;

    /**
     * <p>Constructor for AlignXRenderState.</p>
     *
     * @param prevRenderState a {@link RenderState} object.
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     * @param alignXPercent a {@link java.lang.Integer} object.
     */
    public AlignXRenderState(final RenderState prevRenderState, final HTMLElementImpl element, final int alignXPercent) {
        super(prevRenderState, element);
		this.alignXPercent = alignXPercent;
	}

	/** {@inheritDoc} */
	@Override
	public int getAlignXPercent() {
		return this.alignXPercent;
	}
}
