/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.renderer;

import org.loboevolution.html.ListValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.NodeImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.ListStyle;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

class RList extends BaseRListElement {
	/**
	 * <p>Constructor for RList.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 * @param listNesting a int.
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param parentContainer a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	public RList(NodeImpl modelNode, int listNesting, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer parentContainer) {
		super(modelNode, listNesting, pcontext, rcontext, frameContext, parentContainer);
	}

	/** {@inheritDoc} */
	@Override
	protected void applyStyle(int availWidth, int availHeight) {
		super.applyStyle(availWidth, availHeight);
		ListStyle listStyle = this.listStyle;
		if (listStyle == null || ListValues.get(listStyle.getType()) == ListValues.TYPE_UNSET) {
			final Object rootNode = this.modelNode;
			if (!(rootNode instanceof HTMLElementImpl)) {
				return;
			}
			final HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
			if (listStyle == null) {
				listStyle = new ListStyle();
				this.listStyle = listStyle;
			}
			if ("ul".equalsIgnoreCase(rootElement.getTagName())) {
				final int listNesting = this.listNesting;
				if (listNesting == 0) {
					listStyle.setType(ListValues.TYPE_DISC.getValue());
				} else if (listNesting == 1) {
					listStyle.setType(ListValues.TYPE_CIRCLE.getValue());
				} else {
					listStyle.setType(ListValues.TYPE_SQUARE.getValue());
				}
			} else {
				listStyle.setType(ListValues.TYPE_DECIMAL.getValue());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void doLayout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		final RenderState renderState = this.modelNode.getRenderState();
		
		final Object rootNode = this.modelNode;
		if (!(rootNode instanceof HTMLElementImpl)) {
			return;
		}
		final HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
		final String startText = rootElement.getAttribute("start");
		int counterStart = HtmlValues.getPixelSize(startText, renderState, 0);

		renderState.resetCount(DEFAULT_COUNTER_NAME, this.listNesting, counterStart);
		super.doLayout(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, defaultOverflowX,
				defaultOverflowY, sizeOnly);
	}
}
