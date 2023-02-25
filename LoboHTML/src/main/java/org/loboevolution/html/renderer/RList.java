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
