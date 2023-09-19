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

import org.loboevolution.html.ListValues;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.renderer.info.RLayoutInfo;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.ListStyle;

class RList extends BaseRListElement {
	/**
	 * <p>Constructor for RList.</p>
	 *
	 * @param info a {@link org.loboevolution.html.renderer.info.RBlockInfo} object.
	 */
	public RList(RBlockInfo info) {
		super(info);
	}

	/** {@inheritDoc} */
	@Override
	protected void applyStyle(int availWidth, int availHeight) {
		super.applyStyle(availWidth, availHeight);
		ListStyle listStyle = this.listStyle;
		if (listStyle == null || ListValues.get(listStyle.getType()) == ListValues.TYPE_UNSET) {
			final Object rootNode = this.modelNode;
			if (rootNode instanceof HTMLElementImpl) {
				final HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
				if (listStyle == null) {
					listStyle = new ListStyle();
					this.listStyle = listStyle;
				}
				if ("ul".equalsIgnoreCase(rootElement.getTagName())) {
					final int listNesting = this.listNesting;
					switch (listNesting) {
						case 0:
							listStyle.setType(ListValues.TYPE_DISC.getValue());
							break;
						case 1:
							listStyle.setType(ListValues.TYPE_CIRCLE.getValue());
							break;
						default:
							listStyle.setType(ListValues.TYPE_SQUARE.getValue());
							break;
					}
				} else {
					listStyle.setType(ListValues.TYPE_DECIMAL.getValue());
				}
			}
		}
	}
	/** {@inheritDoc} */
	@Override
	public void doLayout(RLayoutInfo info) {
		final RenderState renderState = this.modelNode.getRenderState();

		final Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl) {

			final HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
			HTMLDocumentImpl doc = (HTMLDocumentImpl) rootElement.getDocumentNode();
			final String startText = rootElement.getAttribute("start");
			int counterStart = HtmlValues.getPixelSize(startText, renderState, doc.getDefaultView(), 0);

			renderState.resetCount(DEFAULT_COUNTER_NAME, this.listNesting, counterStart);
			super.doLayout(info);
		}
	}
}
