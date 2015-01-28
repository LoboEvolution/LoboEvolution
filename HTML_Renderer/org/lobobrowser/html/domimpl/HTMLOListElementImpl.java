/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.ListRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.w3c.HTMLOListElement;

public class HTMLOListElementImpl extends HTMLAbstractUIElement implements
		HTMLOListElement {
	public HTMLOListElementImpl(String name) {
		super(name);
	}

	public boolean getCompact() {
		String compactText = this.getAttribute(HtmlAttributeProperties.COMPACT);
		return HtmlAttributeProperties.COMPACT.equalsIgnoreCase(compactText);
	}

	public void setCompact(boolean compact) {
		this.setAttribute(HtmlAttributeProperties.COMPACT, compact ? HtmlAttributeProperties.COMPACT : null);
	}

	public int getStart() {
		String startText = this.getAttribute(HtmlAttributeProperties.START);
		if (startText == null) {
			return 1;
		}
		try {
			return Integer.parseInt(startText);
		} catch (NumberFormatException nfe) {
			return 1;
		}
	}

	public void setStart(int start) {
		this.setAttribute(HtmlAttributeProperties.START, String.valueOf(start));
	}

	public String getType() {
		return this.getAttribute(HtmlAttributeProperties.TYPE);
	}

	public void setType(String type) {
		this.setAttribute(HtmlAttributeProperties.TYPE, type);
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ListRenderState(prevRenderState, this);
	}

	@Override
	public boolean getReversed() {
		String reversed = this.getAttribute(HtmlAttributeProperties.RESERVED);
		return HtmlAttributeProperties.RESERVED.equalsIgnoreCase(reversed);
	}

	@Override
	public void setReversed(boolean reversed) {
		this.setAttribute(HtmlAttributeProperties.RESERVED, reversed ? HtmlAttributeProperties.RESERVED : null);

		
	}
}
