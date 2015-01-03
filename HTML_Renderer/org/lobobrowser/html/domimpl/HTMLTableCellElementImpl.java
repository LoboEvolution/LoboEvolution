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
 * Created on Dec 4, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.TableCellRenderState;
import org.lobobrowser.html.w3c.HTMLTableCellElement;

public class HTMLTableCellElementImpl extends HTMLAbstractUIElement implements
		HTMLTableCellElement {
	public HTMLTableCellElementImpl(String name) {
		super(name);
	}

	public int getCellIndex() {
		// TODO Cell index in row
		return 0;
	}

	public String getAbbr() {
		return this.getAttribute(HtmlAttributeProperties.ABBR);
	}

	public void setAbbr(String abbr) {
		this.setAttribute(HtmlAttributeProperties.ABBR, abbr);
	}

	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	public String getAxis() {
		return this.getAttribute(HtmlAttributeProperties.AXIS);
	}

	public void setAxis(String axis) {
		this.setAttribute(HtmlAttributeProperties.AXIS, axis);
	}

	public String getBgColor() {
		return this.getAttribute(HtmlAttributeProperties.BGCOLOR);
	}

	public void setBgColor(String bgColor) {
		this.setAttribute(HtmlAttributeProperties.BGCOLOR, bgColor);
	}

	public String getCh() {
		return this.getAttribute(HtmlAttributeProperties.CH);
	}

	public void setCh(String ch) {
		this.setAttribute(HtmlAttributeProperties.CH, ch);
	}

	public String getChOff() {
		return this.getAttribute(HtmlAttributeProperties.CHOFF);
	}

	public void setChOff(String chOff) {
		this.setAttribute(HtmlAttributeProperties.CHOFF, chOff);
	}

	public int getColSpan() {
		String colSpanText = this.getAttribute(HtmlAttributeProperties.COLSPAN);
		if (colSpanText == null) {
			return 1;
		} else {
			try {
				return Integer.parseInt(colSpanText);
			} catch (NumberFormatException nfe) {
				return 1;
			}
		}
	}

	public void setColSpan(int colSpan) {
		this.setAttribute(HtmlAttributeProperties.COLSPAN, String.valueOf(colSpan));
	}

	public String getHeaders() {
		return this.getAttribute(HtmlAttributeProperties.HEADERS);
	}

	public void setHeaders(String headers) {
		this.setAttribute(HtmlAttributeProperties.HEADERS, headers);
	}

	public String getHeight() {
		return this.getAttribute(HtmlAttributeProperties.HEIGHT);
	}

	public void setHeight(String height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, height);
	}

	public boolean getNoWrap() {
		return HtmlAttributeProperties.NOWRAP.equalsIgnoreCase(this.getAttribute(HtmlAttributeProperties.NOWRAP));
	}

	public void setNoWrap(boolean noWrap) {
		this.setAttribute(HtmlAttributeProperties.NOWRAP, noWrap ? HtmlAttributeProperties.NOWRAP : null);
	}

	public int getRowSpan() {
		String rowSpanText = this.getAttribute(HtmlAttributeProperties.ROWSPAN);
		if (rowSpanText == null) {
			return 1;
		} else {
			try {
				return Integer.parseInt(rowSpanText);
			} catch (NumberFormatException nfe) {
				return 1;
			}
		}
	}

	public void setRowSpan(int rowSpan) {
		this.setAttribute(HtmlAttributeProperties.ROWSPAN, String.valueOf(rowSpan));
	}

	public String getScope() {
		return this.getAttribute(HtmlAttributeProperties.SCOPE);
	}

	public void setScope(String scope) {
		this.setAttribute(HtmlAttributeProperties.SCOPE, scope);
	}

	public String getVAlign() {
		return this.getAttribute(HtmlAttributeProperties.VALIGN);
	}

	public void setVAlign(String vAlign) {
		this.setAttribute(HtmlAttributeProperties.VALIGN, vAlign);
	}

	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableCellRenderState(prevRenderState, this);
	}
}
