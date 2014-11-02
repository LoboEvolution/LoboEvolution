/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project. Copyright (C) 2014 Lobo Evolution

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

package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.TableCaptionRenderState;
import org.lobobrowser.w3c.dom.html.HTMLTableCaptionElement;

public class HTMLTableCaptionElementImpl extends HTMLAbstractUIElement
		implements HTMLTableCaptionElement {

	public HTMLTableCaptionElementImpl(String name) {
		super(name);
	}

	public String getAlign() {
		return this.getAttribute("text-align");
	}

	public void setAlign(String align) {
		this.setAttribute("text-align", align);
	}

	public String getCaptionSide() {
		return this.getAttribute("caption-side");
	}

	public void setCaptionSide(String captionSide) {
		this.setAttribute("caption-side", captionSide);
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableCaptionRenderState(prevRenderState, this);
	}
}
