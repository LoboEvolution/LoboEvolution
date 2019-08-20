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

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.lobobrowser.html.style;

import java.awt.Color;

import org.lobo.laf.ColorFactory;
import org.lobobrowser.html.dom.HTMLBodyElement;
import org.lobobrowser.html.dom.HTMLDocument;
import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;
import org.lobobrowser.http.HtmlRendererContext;

public class AnchorRenderState extends StyleSheetRenderState {
	
	private final HTMLElementImpl elem;
	
	private RenderState delegate;
	
	public AnchorRenderState(RenderState prevRenderState, HTMLElementImpl elem) {
		super(prevRenderState, elem);
		this.elem = elem;
		this.delegate = prevRenderState;
	}
	
	@Override
	public int getTextDecorationMask() {
		final RenderState prs = this.delegate;
		final int parentMask = prs == null ? 0 : prs.getTextDecorationMask();
		return parentMask | RenderState.MASK_TEXTDECORATION_UNDERLINE;
	}

	@Override
	public Color getColor() {
		final HTMLDocument doc = (HTMLDocument) elem.getOwnerDocument();
		if (doc != null) {
			final HTMLBodyElement body = (HTMLBodyElement) doc.getBody();
			if (body != null) {
				final String vlink = body.getVLink();
				final String link = body.getLink();
				if (vlink != null || link != null) {
					final HtmlRendererContext rcontext = elem.getHtmlRendererContext();
					if (rcontext != null) {
						final boolean visited = rcontext.isVisitedLink(elem);
						final String colorText = visited ? vlink : link;
						if (colorText != null) {
							return ColorFactory.getInstance().getColor(colorText);
						}
					}
				}
			}
		}
		return Color.BLUE;
	}
}
