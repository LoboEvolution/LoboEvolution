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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.style.ListRenderState;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.w3c.HTMLUListElement;

public class HTMLUListElementImpl extends HTMLAbstractUIElement implements
		HTMLUListElement {
	public HTMLUListElementImpl(String name) {
		super(name);
	}

	public boolean getCompact() {
		String compactText = this.getAttribute(HtmlAttributeProperties.COMPACT);
		return HtmlAttributeProperties.COMPACT.equalsIgnoreCase(compactText);
	}

	public void setCompact(boolean compact) {
		this.setAttribute(HtmlAttributeProperties.COMPACT, compact ? HtmlAttributeProperties.COMPACT : null);
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
}
