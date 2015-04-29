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
import org.lobobrowser.html.w3c.HTMLBRElement;

/**
 * The Class HTMLBRElementImpl.
 */
public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {

    /**
     * Instantiates a new HTMLBR element impl.
     *
     * @param name
     *            the name
     */
    public HTMLBRElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLBRElement#getClear()
     */
    @Override
    public String getClear() {
        return this.getAttribute(HtmlAttributeProperties.CLEAR);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLBRElement#setClear(java.lang.String)
     */
    @Override
    public void setClear(String clear) {
        this.setAttribute(HtmlAttributeProperties.CLEAR, clear);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#appendInnerTextImpl(java.lang.
     * StringBuffer)
     */
    @Override
    protected void appendInnerTextImpl(StringBuffer buffer) {
        buffer.append("\r\n");
        super.appendInnerTextImpl(buffer);
    }
}
