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
 * Created on Jan 28, 2006
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLFrameSetElement;

/**
 * The Class HTMLFrameSetElementImpl.
 */
public class HTMLFrameSetElementImpl extends HTMLElementImpl implements
HTMLFrameSetElement {

    /**
     * Instantiates a new HTML frame set element impl.
     *
     * @param name
     *            the name
     * @param noStyleSheet
     *            the no style sheet
     */
    public HTMLFrameSetElementImpl(String name, boolean noStyleSheet) {
        super(name, noStyleSheet);
    }

    /**
     * Instantiates a new HTML frame set element impl.
     *
     * @param name
     *            the name
     */
    public HTMLFrameSetElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLFrameSetElement#getCols()
     */
    @Override
    public String getCols() {
        return this.getAttribute(HtmlAttributeProperties.COLS);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLFrameSetElement#setCols(java.lang.String)
     */
    @Override
    public void setCols(String cols) {
        this.setAttribute(HtmlAttributeProperties.COLS, cols);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLFrameSetElement#getRows()
     */
    @Override
    public String getRows() {
        return this.getAttribute(HtmlAttributeProperties.ROWS);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLFrameSetElement#setRows(java.lang.String)
     */
    @Override
    public void setRows(String rows) {
        this.setAttribute(HtmlAttributeProperties.ROWS, rows);
    }
}
