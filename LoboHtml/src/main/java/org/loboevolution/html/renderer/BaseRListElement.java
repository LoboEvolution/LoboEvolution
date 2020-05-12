/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html.renderer;

import org.loboevolution.html.ListValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.NodeImpl;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.ListStyle;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

class BaseRListElement extends RBlock {
	/** Constant DEFAULT_COUNTER_NAME="$cobra.counter" */
	protected static final String DEFAULT_COUNTER_NAME = "$cobra.counter";
	protected ListStyle listStyle = null;

	/**
	 * <p>Constructor for BaseRListElement.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 * @param listNesting a int.
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param parentContainer a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	public BaseRListElement(NodeImpl modelNode, int listNesting, UserAgentContext pcontext,
			HtmlRendererContext rcontext, FrameContext frameContext, RenderableContainer parentContainer) {
		super(modelNode, listNesting, pcontext, rcontext, frameContext, parentContainer);
	}

	/** {@inheritDoc} */
	@Override
	protected void applyStyle(int availWidth, int availHeight) {
		this.listStyle = null;
		super.applyStyle(availWidth, availHeight);
		final Object rootNode = this.modelNode;
		if (!(rootNode instanceof HTMLElementImpl)) {
			return;
		}
		final HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
		final AbstractCSSProperties props = rootElement.getCurrentStyle();
		if (props == null) {
			return;
		}
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

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "BaseRListElement[node=" + this.modelNode + "]";
	}
}
