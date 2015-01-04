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
 * Created on Oct 8, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.style.BodyRenderState;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.w3c.HTMLBodyElement;
import org.lobobrowser.html.w3c.HTMLDocument;
import org.mozilla.javascript.Function;
import org.w3c.dom.Document;

public class HTMLBodyElementImpl extends HTMLAbstractUIElement implements HTMLBodyElement {
	public HTMLBodyElementImpl(String name) {
		super(name);
	}

	public void setOwnerDocument(Document value, boolean deep) {
		super.setOwnerDocument(value, deep);
		if (value instanceof HTMLDocument) {
			((HTMLDocument) value).setBody(this);
		}
	}

	public void setOwnerDocument(Document value) {
		super.setOwnerDocument(value);
		if (value instanceof HTMLDocument) {
			((HTMLDocument) value).setBody(this);
		}
	}

	public String getALink() {
		return this.getAttribute(HtmlAttributeProperties.ALINK);
	}

	public void setALink(String aLink) {
		this.setAttribute(HtmlAttributeProperties.ALINK, aLink);
	}

	public String getBackground() {
		return this.getAttribute(HtmlAttributeProperties.BACKGROUND);
	}

	public void setBackground(String background) {
		this.setAttribute(HtmlAttributeProperties.BACKGROUND, background);
	}

	public String getBgColor() {
		return this.getAttribute(HtmlAttributeProperties.BGCOLOR);
	}

	public void setBgColor(String bgColor) {
		this.setAttribute(HtmlAttributeProperties.BGCOLOR, bgColor);
	}

	public String getLink() {
		return this.getAttribute(HtmlAttributeProperties.LINK);
	}

	public void setLink(String link) {
		this.setAttribute(HtmlAttributeProperties.LINK, link);
	}

	public String getText() {
		return this.getAttribute(HtmlAttributeProperties.TEXT);
	}

	public void setText(String text) {
		this.setAttribute(HtmlAttributeProperties.TEXT, text);
	}

	public String getVLink() {
		return this.getAttribute(HtmlAttributeProperties.VLINK);
	}

	public void setVLink(String vLink) {
		this.setAttribute(HtmlAttributeProperties.VLINK, vLink);
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new BodyRenderState(prevRenderState, this);
	}

	public Function getOnload() {
		Object document = this.document;
		if (document instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) document).getOnloadHandler();
		} else {
			return null;
		}
	}

	public void setOnload(Function onload) {
		Object document = this.document;
		if (document instanceof HTMLDocumentImpl) {
			// Note that body.onload overrides
			// Window.onload.
			((HTMLDocumentImpl) document).setOnloadHandler(onload);
		}
	}

	protected void assignAttributeField(String normalName, String value) {
		if (HtmlAttributeProperties.ONLOAD.equals(normalName)) {
			Function onload = this.getEventFunction(null, normalName);
			if (onload != null) {
				this.setOnload(onload);
			}
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

}
