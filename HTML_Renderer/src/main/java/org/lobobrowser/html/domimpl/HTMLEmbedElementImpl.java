/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.w3c.html.HTMLEmbedElement;

/**
 * The Class HTMLEmbedElementImpl.
 */
public class HTMLEmbedElementImpl extends HTMLElementImpl implements
HTMLEmbedElement {

    /**
     * Instantiates a new HTML embed element impl.
     *
     * @param name
     *            the name
     */
    public HTMLEmbedElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#getSrc()
     */
    @Override
    public String getSrc() {
        return this.getAttribute(HtmlAttributeProperties.SRC);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#setSrc(java.lang.String)
     */
    @Override
    public void setSrc(String src) {
        this.setAttribute(HtmlAttributeProperties.SRC, src);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#getType()
     */
    @Override
    public String getType() {
        return this.getAttribute(HtmlAttributeProperties.TYPE);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        this.setAttribute(HtmlAttributeProperties.TYPE, type);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#getWidth()
     */
    @Override
    public String getWidth() {
        return this.getAttribute(HtmlAttributeProperties.WIDTH);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#setWidth(java.lang.String)
     */
    @Override
    public void setWidth(String width) {
        this.setAttribute(HtmlAttributeProperties.WIDTH, width);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#getHeight()
     */
    @Override
    public String getHeight() {
        return this.getAttribute(HtmlAttributeProperties.HEIGHT);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#setHeight(java.lang.String)
     */
    @Override
    public void setHeight(String height) {
        this.setAttribute(HtmlAttributeProperties.HEIGHT, height);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#getAlign()
     */
    @Override
    public String getAlign() {
        return this.getAttribute(HtmlAttributeProperties.ALIGN);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#setAlign(java.lang.String)
     */
    @Override
    public void setAlign(String align) {
        this.setAttribute(HtmlAttributeProperties.ALIGN, align);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#getName()
     */
    @Override
    public String getName() {
        return this.getAttribute(HtmlAttributeProperties.NAME);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLEmbedElement#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.setAttribute(HtmlAttributeProperties.NAME, name);
    }
}
