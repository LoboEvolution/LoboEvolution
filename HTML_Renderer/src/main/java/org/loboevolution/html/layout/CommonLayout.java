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
package org.loboevolution.html.layout;

import org.loboevolution.html.dombl.UINode;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.BaseBoundableRenderable;
import org.loboevolution.html.renderer.RBlockViewport;
import org.loboevolution.html.renderstate.RenderState;

/**
 * The Class CommonLayout.
 */
public abstract class CommonLayout implements MarkupLayout {

	/** The Constant DISPLAY_NONE. */
	protected static final int DISPLAY_NONE = 0;

	/** The Constant DISPLAY_INLINE. */
	protected static final int DISPLAY_INLINE = 1;

	/** The Constant DISPLAY_BLOCK. */
	protected static final int DISPLAY_BLOCK = 2;

	/** The Constant DISPLAY_LIST_ITEM. */
	protected static final int DISPLAY_LIST_ITEM = 3;

	/** The Constant DISPLAY_TABLE. */
	protected static final int DISPLAY_TABLE = 6;

	/** The Constant DISPLAY_INLINE_BLOCK. */
	protected static final int DISPLAY_INLINE_BLOCK = 8;

	/** The display. */
	private final int display;

	/**
	 * Instantiates a new common layout.
	 *
	 * @param defaultDisplay
	 *            the default display
	 */
	public CommonLayout(int defaultDisplay) {
		this.display = defaultDisplay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.MarkupLayout#layoutMarkup(org.loboevolution.
	 * html .renderer.RBlockViewport,
	 * org.loboevolution.html.domimpl.HTMLElementImpl)
	 */
	@Override
	public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		RenderState rs = markupElement.getRenderState();

		int display = 0;

		if (!markupElement.getHidden()) {
			display = DISPLAY_NONE;
		} else {
			display = rs == null ? this.display : rs.getDisplay();
		}

		if (display == RenderState.DISPLAY_INLINE) {
			// Inline elements with absolute or fixed positions need
			// to be treated as blocks.
			int position = rs == null ? RenderState.POSITION_STATIC : rs.getPosition();
			if (position == RenderState.POSITION_ABSOLUTE || position == RenderState.POSITION_FIXED) {
				display = RenderState.DISPLAY_BLOCK;
			} else {
				int boxFloat = rs == null ? RenderState.FLOAT_NONE : rs.getFloat();
				if (boxFloat != RenderState.FLOAT_NONE) {
					display = RenderState.DISPLAY_BLOCK;
				}
			}
		}

		switch (display) {
		case DISPLAY_NONE:
			// skip it completely.
			UINode node = markupElement.getUINode();
			if (node instanceof BaseBoundableRenderable) {
				// This is necessary so that if the element is made
				// visible again, it can be invalidated.
				((BaseBoundableRenderable) node).markLayoutValid();
			}
			break;
		case DISPLAY_BLOCK:
			bodyLayout.layoutRBlock(markupElement);
			break;
		case DISPLAY_LIST_ITEM:
			String tagName = markupElement.getTagName();
			if ("UL".equalsIgnoreCase(tagName) || "OL".equalsIgnoreCase(tagName)) {
				bodyLayout.layoutList(markupElement);
			} else {
				bodyLayout.layoutListItem(markupElement);
			}
			break;
		case DISPLAY_TABLE:
			bodyLayout.layoutRTable(markupElement);
			break;
		case DISPLAY_INLINE_BLOCK:
			bodyLayout.layoutRInlineBlock(markupElement);
			break;
		default:
			// Assume INLINE
			bodyLayout.layoutMarkup(markupElement);
			break;
		}
	}
}
