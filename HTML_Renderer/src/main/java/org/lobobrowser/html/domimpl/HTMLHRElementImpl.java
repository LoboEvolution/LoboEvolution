/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLHRElement;

/**
 * The Class HTMLHRElementImpl.
 */
public class HTMLHRElementImpl extends HTMLAbstractUIElement implements
HTMLHRElement {

    /**
     * Instantiates a new HTMLHR element impl.
     *
     * @param name
     *            the name
     */
    public HTMLHRElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#getAlign()
     */
    @Override
    public String getAlign() {
        return this.getAttribute(HtmlAttributeProperties.ALIGN);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#getNoShade()
     */
    @Override
    public boolean getNoShade() {
        return HtmlAttributeProperties.NOSHADE.equalsIgnoreCase(this
                .getAttribute(HtmlAttributeProperties.NOSHADE));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#getSize()
     */
    @Override
    public String getSize() {
        return this.getAttribute(HtmlAttributeProperties.SIZE);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#getWidth()
     */
    @Override
    public String getWidth() {
        return this.getAttribute(HtmlAttributeProperties.WIDTH);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#setAlign(java.lang.String)
     */
    @Override
    public void setAlign(String align) {
        this.setAttribute(HtmlAttributeProperties.ALIGN, align);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#setNoShade(boolean)
     */
    @Override
    public void setNoShade(boolean noShade) {
        this.setAttribute(HtmlAttributeProperties.NOSHADE,
                noShade ? HtmlAttributeProperties.NOSHADE : null);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#setSize(java.lang.String)
     */
    @Override
    public void setSize(String size) {
        this.setAttribute(HtmlAttributeProperties.SIZE, size);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#setWidth(java.lang.String)
     */
    @Override
    public void setWidth(String width) {
        this.setAttribute(HtmlAttributeProperties.WIDTH, width);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#getColor()
     */
    @Override
    public String getColor() {
        return this.getAttribute(HtmlAttributeProperties.COLOR);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLHRElement#setColor(java.lang.String)
     */
    @Override
    public void setColor(String color) {
        this.setAttribute(HtmlAttributeProperties.COLOR, color);

    }
}
