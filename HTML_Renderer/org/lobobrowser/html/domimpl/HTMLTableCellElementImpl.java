/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Dec 4, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.renderstate.TableCellRenderState;
import org.lobobrowser.html.w3c.HTMLTableCellElement;

/**
 * The Class HTMLTableCellElementImpl.
 */
public class HTMLTableCellElementImpl extends HTMLAbstractUIElement implements
HTMLTableCellElement {

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
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getCellIndex()
     */
    @Override
    public int getCellIndex() {
        // TODO Cell index in row
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getAbbr()
     */
    @Override
    public String getAbbr() {
        return this.getAttribute(HtmlAttributeProperties.ABBR);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setAbbr(java.lang.String)
     */
    @Override
    public void setAbbr(String abbr) {
        this.setAttribute(HtmlAttributeProperties.ABBR, abbr);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getAlign()
     */
    @Override
    public String getAlign() {
        return this.getAttribute(HtmlAttributeProperties.ALIGN);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setAlign(java.lang.String)
     */
    @Override
    public void setAlign(String align) {
        this.setAttribute(HtmlAttributeProperties.ALIGN, align);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getAxis()
     */
    @Override
    public String getAxis() {
        return this.getAttribute(HtmlAttributeProperties.AXIS);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setAxis(java.lang.String)
     */
    @Override
    public void setAxis(String axis) {
        this.setAttribute(HtmlAttributeProperties.AXIS, axis);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getBgColor()
     */
    @Override
    public String getBgColor() {
        return this.getAttribute(HtmlAttributeProperties.BGCOLOR);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.w3c.HTMLTableCellElement#setBgColor(java.lang.String)
     */
    @Override
    public void setBgColor(String bgColor) {
        this.setAttribute(HtmlAttributeProperties.BGCOLOR, bgColor);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getCh()
     */
    @Override
    public String getCh() {
        return this.getAttribute(HtmlAttributeProperties.CH);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setCh(java.lang.String)
     */
    @Override
    public void setCh(String ch) {
        this.setAttribute(HtmlAttributeProperties.CH, ch);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getChOff()
     */
    @Override
    public String getChOff() {
        return this.getAttribute(HtmlAttributeProperties.CHOFF);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setChOff(java.lang.String)
     */
    @Override
    public void setChOff(String chOff) {
        this.setAttribute(HtmlAttributeProperties.CHOFF, chOff);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getColSpan()
     */
    @Override
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

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setColSpan(int)
     */
    @Override
    public void setColSpan(int colSpan) {
        this.setAttribute(HtmlAttributeProperties.COLSPAN,
                String.valueOf(colSpan));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getHeaders()
     */
    @Override
    public String getHeaders() {
        return this.getAttribute(HtmlAttributeProperties.HEADERS);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.w3c.HTMLTableCellElement#setHeaders(java.lang.String)
     */
    @Override
    public void setHeaders(String headers) {
        this.setAttribute(HtmlAttributeProperties.HEADERS, headers);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getHeight()
     */
    @Override
    public String getHeight() {
        return this.getAttribute(HtmlAttributeProperties.HEIGHT);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.w3c.HTMLTableCellElement#setHeight(java.lang.String)
     */
    @Override
    public void setHeight(String height) {
        this.setAttribute(HtmlAttributeProperties.HEIGHT, height);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getNoWrap()
     */
    @Override
    public boolean getNoWrap() {
        return HtmlAttributeProperties.NOWRAP.equalsIgnoreCase(this
                .getAttribute(HtmlAttributeProperties.NOWRAP));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setNoWrap(boolean)
     */
    @Override
    public void setNoWrap(boolean noWrap) {
        this.setAttribute(HtmlAttributeProperties.NOWRAP,
                noWrap ? HtmlAttributeProperties.NOWRAP : null);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getRowSpan()
     */
    @Override
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

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setRowSpan(int)
     */
    @Override
    public void setRowSpan(int rowSpan) {
        this.setAttribute(HtmlAttributeProperties.ROWSPAN,
                String.valueOf(rowSpan));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getScope()
     */
    @Override
    public String getScope() {
        return this.getAttribute(HtmlAttributeProperties.SCOPE);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setScope(java.lang.String)
     */
    @Override
    public void setScope(String scope) {
        this.setAttribute(HtmlAttributeProperties.SCOPE, scope);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getVAlign()
     */
    @Override
    public String getVAlign() {
        return this.getAttribute(HtmlAttributeProperties.VALIGN);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.w3c.HTMLTableCellElement#setVAlign(java.lang.String)
     */
    @Override
    public void setVAlign(String vAlign) {
        this.setAttribute(HtmlAttributeProperties.VALIGN, vAlign);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#getWidth()
     */
    @Override
    public String getWidth() {
        return this.getAttribute(HtmlAttributeProperties.WIDTH);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCellElement#setWidth(java.lang.String)
     */
    @Override
    public void setWidth(String width) {
        this.setAttribute(HtmlAttributeProperties.WIDTH, width);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser
     * .html.renderstate.RenderState)
     */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new TableCellRenderState(prevRenderState, this);
    }
}
