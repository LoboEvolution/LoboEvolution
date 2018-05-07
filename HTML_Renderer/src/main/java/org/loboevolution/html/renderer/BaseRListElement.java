/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderer;


import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.rendererblock.RBlock;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.ListStyle;
import org.loboevolution.http.UserAgentContext;

/**
 * The Class BaseRListElement.
 */
public class BaseRListElement extends RBlock implements HtmlAttributeProperties {

	/** The Constant DEFAULT_COUNTER_NAME. */
	protected static final String DEFAULT_COUNTER_NAME = "$cobra.counter";

	/** The list style. */
	protected ListStyle listStyle = null;

	/**
	 * Instantiates a new base r list element.
	 *
	 * @param modelNode
	 *            the model node
	 * @param listNesting
	 *            the list nesting
	 * @param pcontext
	 *            the pcontext
	 * @param rcontext
	 *            the rcontext
	 * @param frameContext
	 *            the frame context
	 * @param parentContainer
	 *            the parent container
	 */
	public BaseRListElement(DOMNodeImpl modelNode, int listNesting, UserAgentContext pcontext,
			HtmlRendererContext rcontext, FrameContext frameContext, RenderableContainer parentContainer) {
		super(modelNode, listNesting, pcontext, rcontext, frameContext, parentContainer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseElementRenderable#applyStyle(int,
	 * int)
	 */
	@Override
	protected void applyStyle(int availWidth, int availHeight) {
		this.listStyle = null;
		super.applyStyle(availWidth, availHeight);
		Object rootNode = this.modelNode;
		if (!(rootNode instanceof HTMLElementImpl)) {
			return;
		}
		HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
		AbstractCSSProperties props = rootElement.getCurrentStyle();
		if (props == null) {
			return;
		}
		ListStyle listStyle = null;
		String listStyleText = props.getListStyle();
		if (listStyleText != null) {
			if (INHERIT.equals(listStyleText)) {
				listStyleText = rootElement.getParentStyle().getListStyle();
			}
			listStyle = HtmlValues.getListStyle(listStyleText);
		}
		String listStyleTypeText = props.getListStyleType();
		if (listStyleTypeText != null) {

			if (INHERIT.equals(listStyleTypeText)) {
				listStyleTypeText = rootElement.getParentStyle().getListStyleType();
			}

			int listType = HtmlValues.getListStyleType(listStyleTypeText);
			if (listType != ListStyle.TYPE_UNSET) {
				if (listStyle == null) {
					listStyle = new ListStyle();
				}
				listStyle.type = listType;
			}
		}
		if (listStyle == null || listStyle.type == ListStyle.TYPE_UNSET) {
			String typeAttributeText = rootElement.getAttribute(TYPE);
			if (typeAttributeText != null) {

				if (INHERIT.equals(typeAttributeText)) {
					typeAttributeText = rootElement.getParentStyle().getListStyle();
				}

				int newStyleType = HtmlValues.getListStyleType(typeAttributeText);
				if (newStyleType != ListStyle.TYPE_UNSET) {
					if (listStyle == null) {
						listStyle = new ListStyle();
						this.listStyle = listStyle;
					}
					listStyle.type = newStyleType;
				}
			}
		}
		this.listStyle = listStyle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.RBlock#toString()
	 */
	@Override
	public String toString() {
		return "BaseRListElement[node=" + this.modelNode + "]";
	}
}
