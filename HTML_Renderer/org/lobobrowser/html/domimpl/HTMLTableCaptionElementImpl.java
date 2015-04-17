/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project.
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

package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.renderstate.TableCaptionRenderState;
import org.lobobrowser.html.w3c.HTMLTableCaptionElement;

/**
 * The Class HTMLTableCaptionElementImpl.
 */
public class HTMLTableCaptionElementImpl extends HTMLAbstractUIElement
implements HTMLTableCaptionElement {

    /**
     * Instantiates a new HTML table caption element impl.
     *
     * @param name
     *            the name
     */
    public HTMLTableCaptionElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTableCaptionElement#getAlign()
     */
    @Override
    public String getAlign() {
        return this.getAttribute(HtmlAttributeProperties.TEXTALIGN);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.w3c.HTMLTableCaptionElement#setAlign(java.lang.String)
     */
    @Override
    public void setAlign(String align) {
        this.setAttribute(HtmlAttributeProperties.TEXTALIGN, align);
    }

    /**
     * Gets the caption side.
     *
     * @return the caption side
     */
    public String getCaptionSide() {
        return this.getAttribute(HtmlAttributeProperties.CAPTIONSIDE);
    }

    /**
     * Sets the caption side.
     *
     * @param captionSide
     *            the new caption side
     */
    public void setCaptionSide(String captionSide) {
        this.setAttribute(HtmlAttributeProperties.CAPTIONSIDE, captionSide);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser
     * .html.renderstate.RenderState)
     */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new TableCaptionRenderState(prevRenderState, this);
    }
}
