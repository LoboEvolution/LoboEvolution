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
/*
 * Created on Feb 12, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLOListElement;
import org.loboevolution.html.renderstate.ListRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;

public class HTMLOListElementImpl extends HTMLAbstractUIElement implements HTMLOListElement {
	public HTMLOListElementImpl(String name) {
		super(name);
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ListRenderState(prevRenderState, this);
	}

	@Override
	public boolean getCompact() {
		final String compactText = getAttribute("compact");
		return "compact".equalsIgnoreCase(compactText);
	}

	@Override
	public int getStart() {
		final String startText = getAttribute("start");
		return HtmlValues.getPixelSize(startText, null, 1);
	}

	@Override
	public String getType() {
		return getAttribute("type");
	}

	@Override
	public void setCompact(boolean compact) {
		setAttribute("compact", compact ? "compact" : null);
	}

	@Override
	public void setStart(int start) {
		setAttribute("start", String.valueOf(start));
	}

	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}
}
