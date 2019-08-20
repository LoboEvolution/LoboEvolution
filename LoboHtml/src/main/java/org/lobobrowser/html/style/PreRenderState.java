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
package org.lobobrowser.html.style;

import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;

public class PreRenderState extends BlockRenderState {
	public PreRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	@Override
	public int getWhiteSpace() {
		final Integer ws = this.iWhiteSpace;
		if (ws != null) {
			return ws.intValue();
		}
		final AbstractCSSProperties props = getCssProperties();
		final String whiteSpaceText = props == null ? null : props.getWhiteSpace();
		int wsValue;
		if (whiteSpaceText == null) {
			wsValue = WS_PRE;
		} else {
			final String whiteSpaceTextTL = whiteSpaceText.toLowerCase();
			if ("nowrap".equals(whiteSpaceTextTL)) {
				wsValue = WS_NOWRAP;
			} else if ("normal".equals(whiteSpaceTextTL)) {
				wsValue = WS_NORMAL;
			} else {
				wsValue = WS_PRE;
			}
		}
		this.iWhiteSpace = new Integer(wsValue);
		return wsValue;
	}
}
