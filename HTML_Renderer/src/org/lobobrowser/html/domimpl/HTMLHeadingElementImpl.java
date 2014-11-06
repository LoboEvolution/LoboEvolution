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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.html.style.HeadingRenderState;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.w3c.HTMLHeadingElement;

public class HTMLHeadingElementImpl extends HTMLAbstractUIElement implements
		HTMLHeadingElement {
	public HTMLHeadingElementImpl(String name) {
		super(name);
	}

	public String getAlign() {
		return this.getAttribute("align");
	}

	public void setAlign(String align) {
		this.setAttribute("align", align);
	}

	private final float getHeadingFontSize() {
		String tagName = this.getTagName();
		try {
			int lastCharValue = tagName.charAt(1) - '0';
			switch (lastCharValue) {
			case 1:
				return 24.0f;
			case 2:
				return 18.0f;
			case 3:
				return 15.0f;
			case 4:
				return 12.0f;
			case 5:
				return 10.0f;
			case 6:
				return 8.0f;
			}
		} catch (Exception thrown) {
			this.warn(
					"getHeadingFontSize(): Bad heading tag: "
							+ this.getTagName(), thrown);
		}
		return 14.0f;
	}

	private final String getHeadingFontSizeText() {
		String tagName = this.getTagName();
		try {
			int lastCharValue = tagName.charAt(1) - '0';
			switch (lastCharValue) {
			case 1:
				return "24pt";
			case 2:
				return "18pt";
			case 3:
				return "13.55pt";
			case 4:
				return "12pt";
			case 5:
				return "10pt";
			case 6:
				return "7.55pt";
			}
		} catch (Exception thrown) {
			this.warn(
					"getHeadingFontSizeText(): Bad heading tag: "
							+ this.getTagName(), thrown);
		}
		return "14px";
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		float fontSize = this.getHeadingFontSize();
		return new HeadingRenderState(prevRenderState, this);
	}

	protected void appendInnerTextImpl(StringBuffer buffer) {
		int length = buffer.length();
		int lineBreaks;
		if (length == 0) {
			lineBreaks = 2;
		} else {
			int start = length - 4;
			if (start < 0) {
				start = 0;
			}
			lineBreaks = 0;
			for (int i = start; i < length; i++) {
				char ch = buffer.charAt(i);
				if (ch == '\n') {
					lineBreaks++;
				}
			}
		}
		for (int i = 0; i < 2 - lineBreaks; i++) {
			buffer.append("\r\n");
		}
		super.appendInnerTextImpl(buffer);
		buffer.append("\r\n\r\n");
	}

	protected AbstractCSS2Properties createDefaultStyleSheet() {
		ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		css.internalSetLC("font-size", this.getHeadingFontSizeText());
		css.internalSetLC("font-weight", "bolder");
		return css;
	}
}
