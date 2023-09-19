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

package org.loboevolution.html.renderstate;

/**
 * <p>TextDecorationRenderState class.</p>
 */
public class TextDecorationRenderState extends RenderStateDelegator {

	/** The text decoration mask. */
	private final int textDecorationMask;

	/**
	 * Instantiates a new text decoration render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param textDecorationMask
	 *            the text decoration mask
	 */
	public TextDecorationRenderState(RenderState prevRenderState, int textDecorationMask) {
		super(prevRenderState);
		this.textDecorationMask = textDecorationMask;
	}


	/** {@inheritDoc} */
	@Override
	public int getTextDecorationMask() {
		RenderState prs = this.delegate;
		int parentMask = prs == null ? 0 : prs.getTextDecorationMask();
		return parentMask | this.textDecorationMask;
	}

	@Override
	public int getDefaultDisplay() {
		return 0;
	}
}
