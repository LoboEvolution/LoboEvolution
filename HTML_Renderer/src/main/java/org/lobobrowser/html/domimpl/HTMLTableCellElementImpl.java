/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Dec 4, 2005
 */
package org.lobobrowser.html.domimpl;


import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.renderstate.TableCellRenderState;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLTableCellElement;

/**
 * The Class HTMLTableCellElementImpl.
 */
public class HTMLTableCellElementImpl extends HTMLAbstractUIElement implements HTMLTableCellElement {

	/**
	 * Instantiates a new HTML table cell element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLTableCellElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getCellIndex()
	 */
	@Override
	public int getCellIndex() {
		// TODO Cell index in row
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getAbbr()
	 */
	@Override
	public String getAbbr() {
		return this.getAttribute(ABBR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setAbbr(java.lang.String)
	 */
	@Override
	public void setAbbr(String abbr) {
		this.setAttribute(ABBR, abbr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getAxis()
	 */
	@Override
	public String getAxis() {
		return this.getAttribute(AXIS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setAxis(java.lang.String)
	 */
	@Override
	public void setAxis(String axis) {
		this.setAttribute(AXIS, axis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getBgColor()
	 */
	@Override
	public String getBgColor() {
		return this.getAttribute(BGCOLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#setBgColor(java.lang.
	 * String)
	 */
	@Override
	public void setBgColor(String bgColor) {
		this.setAttribute(BGCOLOR, bgColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getCh()
	 */
	@Override
	public String getCh() {
		return this.getAttribute(CH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setCh(java.lang.String)
	 */
	@Override
	public void setCh(String ch) {
		this.setAttribute(CH, ch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getChOff()
	 */
	@Override
	public String getChOff() {
		return this.getAttribute(CHOFF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setChOff(java.lang.String)
	 */
	@Override
	public void setChOff(String chOff) {
		this.setAttribute(CHOFF, chOff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getColSpan()
	 */
	@Override
	public int getColSpan() {
		String colSpanText = this.getAttribute(COLSPAN);
		return HtmlValues.getPixelSize(colSpanText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#setColSpan(int)
	 */
	@Override
	public void setColSpan(int colSpan) {
		this.setAttribute(COLSPAN, String.valueOf(colSpan));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getHeaders()
	 */
	@Override
	public String getHeaders() {
		return this.getAttribute(HEADERS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#setHeaders(java.lang.
	 * String)
	 */
	@Override
	public void setHeaders(String headers) {
		this.setAttribute(HEADERS, headers);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getHeight()
	 */
	@Override
	public String getHeight() {
		return this.getAttribute(HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		this.setAttribute(HEIGHT, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getNoWrap()
	 */
	@Override
	public boolean getNoWrap() {
		return NOWRAP.equalsIgnoreCase(this.getAttribute(NOWRAP));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#setNoWrap(boolean)
	 */
	@Override
	public void setNoWrap(boolean noWrap) {
		this.setAttribute(NOWRAP, noWrap ? NOWRAP : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getRowSpan()
	 */
	@Override
	public int getRowSpan() {
		String rowSpanText = this.getAttribute(ROWSPAN);
		return HtmlValues.getPixelSize(rowSpanText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#setRowSpan(int)
	 */
	@Override
	public void setRowSpan(int rowSpan) {
		this.setAttribute(ROWSPAN, String.valueOf(rowSpan));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getScope()
	 */
	@Override
	public String getScope() {
		return this.getAttribute(SCOPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setScope(java.lang.String)
	 */
	@Override
	public void setScope(String scope) {
		this.setAttribute(SCOPE, scope);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getVAlign()
	 */
	@Override
	public String getVAlign() {
		return this.getAttribute(VALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setVAlign(java.lang.String)
	 */
	@Override
	public void setVAlign(String vAlign) {
		this.setAttribute(VALIGN, vAlign);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableCellElement#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.getAttribute(WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableCellElement#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		this.setAttribute(WIDTH, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableCellRenderState(prevRenderState, this);
	}
}
