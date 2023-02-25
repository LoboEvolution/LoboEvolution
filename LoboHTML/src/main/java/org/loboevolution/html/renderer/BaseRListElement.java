/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
