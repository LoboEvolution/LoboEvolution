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
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.ListStyle;
import org.loboevolution.http.UserAgentContext;

/**
 * The Class RList.
 */
public class RList extends BaseRListElement implements HtmlAttributeProperties {

	/**
	 * Instantiates a new r list.
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
	 * @param parent
	 *            the parent
	 */
	public RList(DOMNodeImpl modelNode, int listNesting, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer parentContainer) {
		super(modelNode, listNesting, pcontext, rcontext, frameContext, parentContainer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseRListElement#applyStyle(int, int)
	 */
	@Override
	protected void applyStyle(int availWidth, int availHeight) {
		super.applyStyle(availWidth, availHeight);
		ListStyle listStyle = this.listStyle;
		if (listStyle == null || listStyle.type == ListStyle.TYPE_UNSET) {
			Object rootNode = this.modelNode;
			if (!(rootNode instanceof HTMLElementImpl)) {
				return;
			}
			HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
			if (listStyle == null) {
				listStyle = new ListStyle();
				this.listStyle = listStyle;
			}
			if ("ul".equalsIgnoreCase(rootElement.getTagName())) {
				int listNesting = this.listNesting;
				if (listNesting == 0) {
					listStyle.type = ListStyle.TYPE_DISC;
				} else if (listNesting == 1) {
					listStyle.type = ListStyle.TYPE_CIRCLE;
				} else {
					listStyle.type = ListStyle.TYPE_SQUARE;
				}
			} else {
				listStyle.type = ListStyle.TYPE_DECIMAL;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.RBlock#doLayout(int, int, boolean,
	 * boolean, org.loboevolution.html.renderer.FloatingBoundsSource, int, int,
	 * boolean)
	 */
	@Override
	public void doLayout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		RenderState renderState = this.modelNode.getRenderState();
		int counterStart = 1;
		Object rootNode = this.modelNode;
		if (!(rootNode instanceof HTMLElementImpl)) {
			return;
		}
		HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
		String startText = rootElement.getAttribute(START);
		if (startText != null) {
			counterStart = HtmlValues.getPixelSize(startText, null, 0);
		}
		renderState.resetCount(DEFAULT_COUNTER_NAME, this.listNesting, counterStart);
		super.doLayout(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, defaultOverflowX,
				defaultOverflowY, sizeOnly);
	}
}
