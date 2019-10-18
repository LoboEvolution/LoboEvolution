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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLDivElement;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;

public class HTMLDivElementImpl extends HTMLAbstractUIElement implements HTMLDivElement {

	public HTMLDivElementImpl(String name) {
		super(name);
	}

	@Override
	protected void appendInnerTextImpl(StringBuffer buffer) {
		final int length = buffer.length();
		int lineBreaks;
		if (length == 0) {
			lineBreaks = 2;
		} else {
			int start = length - 2;
			if (start < 0) {
				start = 0;
			}
			lineBreaks = 0;
			for (int i = start; i < length; i++) {
				final char ch = buffer.charAt(i);
				if (ch == '\n') {
					lineBreaks++;
				}
			}
		}
		for (int i = 0; i < 1 - lineBreaks; i++) {
			buffer.append("\r\n");
		}
		super.appendInnerTextImpl(buffer);
		buffer.append("\r\n");
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new BlockRenderState(prevRenderState, this);
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}
}
