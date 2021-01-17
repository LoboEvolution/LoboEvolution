/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Dec 4, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableCellRenderState;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>HTMLTableCellElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLTableCellElementImpl extends HTMLAbstractUIElement implements HTMLTableCellElement {
	/**
	 * <p>Constructor for HTMLTableCellElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTableCellElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableCellRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getAbbr() {
		return getAttribute("abbr");
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public String getAxis() {
		return getAttribute("axis");
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		return getAttribute("bgcolor");
	}

	/** {@inheritDoc} */
	@Override
	public int getCellIndex() {
		// TODO Cell index in row
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	/** {@inheritDoc} */
	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

	/** {@inheritDoc} */
	@Override
	public int getColSpan() {
		final String colSpanText = getAttribute("colspan");
		return HtmlValues.getPixelSize(colSpanText, null, 1);
	}

	/** {@inheritDoc} */
	@Override
	public String getHeaders() {
		return getAttribute("headers");
	}

	/** {@inheritDoc} */
	@Override
	public String getHeight() {
		return getAttribute("height");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getNoWrap() {
		return "nowrap".equalsIgnoreCase(getAttribute("nowrap"));
	}

	/** {@inheritDoc} */
	@Override
	public int getRowSpan() {
		final String rowSpanText = getAttribute("rowspan");
		return HtmlValues.getPixelSize(rowSpanText, null, 1);
	}

	/** {@inheritDoc} */
	@Override
	public String getScope() {
		return getAttribute("scope");
	}

	/** {@inheritDoc} */
	@Override
	public String getVAlign() {
		return getAttribute("valign");
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/** {@inheritDoc} */
	@Override
	public void setAbbr(String abbr) {
		setAttribute("abbr", abbr);
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setAxis(String axis) {
		setAttribute("axis", axis);
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	/** {@inheritDoc} */
	@Override
	public void setCh(String ch) {
		setAttribute("ch", ch);
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(String chOff) {
		setAttribute("choff", chOff);
	}

	/** {@inheritDoc} */
	@Override
	public void setColSpan(int colSpan) {
		setAttribute("colspan", String.valueOf(colSpan));
	}

	/** {@inheritDoc} */
	@Override
	public void setHeaders(String headers) {
		setAttribute("headers", headers);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(String height) {
		setAttribute("height", height);
	}

	/** {@inheritDoc} */
	@Override
	public void setNoWrap(boolean noWrap) {
		setAttribute("nowrap", noWrap ? "nowrap" : null);
	}

	/** {@inheritDoc} */
	@Override
	public void setRowSpan(int rowSpan) {
		setAttribute("rowspan", String.valueOf(rowSpan));
	}

	/** {@inheritDoc} */
	@Override
	public void setScope(String scope) {
		setAttribute("scope", scope);
	}

	/** {@inheritDoc} */
	@Override
	public void setVAlign(String vAlign) {
		setAttribute("valign", vAlign);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
	
	@Override
	public String toString() {
		return "[object HTMLTableCellElement]";
	}
}
