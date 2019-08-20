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
 * Created on Dec 4, 2005
 */
package org.lobobrowser.html.dom.domimpl;

import org.lobobrowser.html.dom.HTMLTableCellElement;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.TableCellRenderState;

public class HTMLTableCellElementImpl extends HTMLAbstractUIElement implements HTMLTableCellElement {
	public HTMLTableCellElementImpl(String name) {
		super(name);
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableCellRenderState(prevRenderState, this);
	}

	@Override
	public String getAbbr() {
		return getAttribute("abbr");
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public String getAxis() {
		return getAttribute("axis");
	}

	@Override
	public String getBgColor() {
		return getAttribute("bgcolor");
	}

	@Override
	public int getCellIndex() {
		// TODO Cell index in row
		return 0;
	}

	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

	@Override
	public int getColSpan() {
		final String colSpanText = getAttribute("colspan");
		return HtmlValues.getPixelSize(colSpanText, null, 1);
	}

	@Override
	public String getHeaders() {
		return getAttribute("headers");
	}

	@Override
	public String getHeight() {
		return getAttribute("height");
	}

	@Override
	public boolean getNoWrap() {
		return "nowrap".equalsIgnoreCase(getAttribute("nowrap"));
	}

	@Override
	public int getRowSpan() {
		final String rowSpanText = getAttribute("rowspan");
		return HtmlValues.getPixelSize(rowSpanText, null, 1);
	}

	@Override
	public String getScope() {
		return getAttribute("scope");
	}

	@Override
	public String getVAlign() {
		return getAttribute("valign");
	}

	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	@Override
	public void setAbbr(String abbr) {
		setAttribute("abbr", abbr);
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public void setAxis(String axis) {
		setAttribute("axis", axis);
	}

	@Override
	public void setBgColor(String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	@Override
	public void setCh(String ch) {
		setAttribute("ch", ch);
	}

	@Override
	public void setChOff(String chOff) {
		setAttribute("choff", chOff);
	}

	@Override
	public void setColSpan(int colSpan) {
		setAttribute("colspan", String.valueOf(colSpan));
	}

	@Override
	public void setHeaders(String headers) {
		setAttribute("headers", headers);
	}

	@Override
	public void setHeight(String height) {
		setAttribute("height", height);
	}

	@Override
	public void setNoWrap(boolean noWrap) {
		setAttribute("nowrap", noWrap ? "nowrap" : null);
	}

	@Override
	public void setRowSpan(int rowSpan) {
		setAttribute("rowspan", String.valueOf(rowSpan));
	}

	@Override
	public void setScope(String scope) {
		setAttribute("scope", scope);
	}

	@Override
	public void setVAlign(String vAlign) {
		setAttribute("valign", vAlign);
	}

	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
