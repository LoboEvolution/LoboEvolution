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
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.ListStyle;

import java.awt.*;

class BaseRListElement extends RBlock {

	/** Constant DEFAULT_COUNTER_NAME="$cobra.counter" */
	protected static final String DEFAULT_COUNTER_NAME = "$cobra.counter";

	protected ListStyle listStyle = null;

	/**
	 * <p>Constructor for BaseRListElement.</p>
	 *
	 * @param info a {@link org.loboevolution.html.renderer.info.RBlockInfo} object.
	 */
	public BaseRListElement(RBlockInfo info) {
		super(info);
	}

	/** {@inheritDoc} */
	@Override
	protected void applyStyle(int availWidth, int availHeight) {
		this.listStyle = null;
		super.applyStyle(availWidth, availHeight);
		final Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl) {

			final HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
			final CSSStyleDeclaration props = rootElement.getCurrentStyle();
			if (props != null) {
				ListStyle listStyle = null;
				final String listStyleText = props.getListStyle();
				if (listStyleText != null) {
					listStyle = HtmlValues.getListStyle(listStyleText);
				}
				final String listStyleTypeText = props.getListStyleType();
				if (listStyleTypeText != null) {
					final ListValues listType = HtmlValues.getListStyleType(listStyleTypeText);
					if (listType != ListValues.TYPE_UNSET) {
						if (listStyle == null) {
							listStyle = new ListStyle();
						}
						listStyle.setType(listType.getValue());
					}
				}

				final String listStyleImage = props.getListStyleImage();
				if (listStyleImage != null && HtmlValues.isUrl(listStyleImage)) {
					final Image img = HtmlValues.getListStyleImage(listStyleImage);
					if (listStyle == null) {
						listStyle = new ListStyle();
					}
					listStyle.setType(ListValues.TYPE_URL.getValue());
					listStyle.setImage(img);
				}

				if (listStyle == null || ListValues.get(listStyle.getType()) == ListValues.TYPE_UNSET) {
					final String typeAttributeText = rootElement.getAttribute("type");
					if (typeAttributeText != null) {
						final ListValues newStyleType = HtmlValues.getListStyleType(typeAttributeText);
						if (newStyleType != ListValues.TYPE_UNSET) {
							if (listStyle == null) {
								listStyle = new ListStyle();
								this.listStyle = listStyle;
							}
							listStyle.setType(newStyleType.getValue());
						}
					}
				}
				this.listStyle = listStyle;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "BaseRListElement[node=" + this.modelNode + "]";
	}
}
