/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

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

    Contact info: lobochief@users.sourceforge.net
*/
package org.lobobrowser.html.layout;

import org.lobobrowser.html.dombl.UINode;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.renderer.BaseBoundableRenderable;
import org.lobobrowser.html.renderer.MarkupLayout;
import org.lobobrowser.html.renderer.RBlockViewport;
import org.lobobrowser.html.style.RenderState;

public abstract class CommonLayout implements MarkupLayout {
	protected static final int DISPLAY_NONE = 0;
	protected static final int DISPLAY_INLINE = 1;
	protected static final int DISPLAY_BLOCK = 2;
	protected static final int DISPLAY_LIST_ITEM = 3;
	protected static final int DISPLAY_TABLE = 6;

	private final int display;

	public CommonLayout(int defaultDisplay) {
		this.display = defaultDisplay;
	}

	public void layoutMarkup(RBlockViewport bodyLayout,	HTMLElementImpl markupElement) {
		RenderState rs = markupElement.getRenderState();
		int display = rs == null ? this.display : rs.getDisplay();
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
			if ("UL".equalsIgnoreCase(tagName)
					|| "OL".equalsIgnoreCase(tagName)) {
				bodyLayout.layoutList(markupElement);
			} else {
				bodyLayout.layoutListItem(markupElement);
			}
			break;
		case DISPLAY_TABLE:
			bodyLayout.layoutRTable(markupElement);
			break;
		default:
			// Assume INLINE
			bodyLayout.layoutMarkup(markupElement);
			break;
		}
	}
}
