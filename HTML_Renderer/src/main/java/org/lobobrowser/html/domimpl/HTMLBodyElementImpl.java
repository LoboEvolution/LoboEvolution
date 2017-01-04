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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.BodyRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.w3c.html.HTMLBodyElement;
import org.lobobrowser.w3c.html.HTMLDocument;
import org.mozilla.javascript.Function;
import org.w3c.dom.Document;

/**
 * The Class HTMLBodyElementImpl.
 */
public class HTMLBodyElementImpl extends HTMLAbstractUIElement implements
        HTMLBodyElement {

    /**
     * Instantiates a new HTML body element impl.
     *
     * @param name
     *            the name
     */
    public HTMLBodyElementImpl(String name) {
        super(name);
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.domimpl.DOMNodeImpl#setOwnerDocument(org.w3c.dom.Document
 * , boolean)
 */
    @Override
    public void setOwnerDocument(Document value, boolean deep) {
        super.setOwnerDocument(value, deep);
        if (value instanceof HTMLDocument) {
            ((HTMLDocument) value).setBody(this);
        }
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.domimpl.DOMNodeImpl#setOwnerDocument(org.w3c.dom.Document
 * )
 */
    @Override
    public void setOwnerDocument(Document value) {
        super.setOwnerDocument(value);
        if (value instanceof HTMLDocument) {
            ((HTMLDocument) value).setBody(this);
        }
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#getALink()
 */
    @Override
    public String getALink() {
        return this.getAttribute(HtmlAttributeProperties.ALINK);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#setALink(java.lang.String)
 */
    @Override
    public void setALink(String aLink) {
        this.setAttribute(HtmlAttributeProperties.ALINK, aLink);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#getBackground()
 */
    @Override
    public String getBackground() {
        return this.getAttribute(HtmlAttributeProperties.BACKGROUND);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#setBackground(java.lang.String)
 */
    @Override
    public void setBackground(String background) {
        this.setAttribute(HtmlAttributeProperties.BACKGROUND, background);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#getBgColor()
 */
    @Override
    public String getBgColor() {
        return this.getAttribute(HtmlAttributeProperties.BGCOLOR);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#setBgColor(java.lang.String)
 */
    @Override
    public void setBgColor(String bgColor) {
        this.setAttribute(HtmlAttributeProperties.BGCOLOR, bgColor);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#getLink()
 */
    @Override
    public String getLink() {
        return this.getAttribute(HtmlAttributeProperties.LINK);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#setLink(java.lang.String)
 */
    @Override
    public void setLink(String link) {
        this.setAttribute(HtmlAttributeProperties.LINK, link);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#getText()
 */
    @Override
    public String getText() {
        return this.getAttribute(HtmlAttributeProperties.TEXT);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#setText(java.lang.String)
 */
    @Override
    public void setText(String text) {
        this.setAttribute(HtmlAttributeProperties.TEXT, text);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#getVLink()
 */
    @Override
    public String getVLink() {
        return this.getAttribute(HtmlAttributeProperties.VLINK);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.w3c.html.HTMLBodyElement#setVLink(java.lang.String)
 */
    @Override
    public void setVLink(String vLink) {
        this.setAttribute(HtmlAttributeProperties.VLINK, vLink);
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser
 * .html.renderstate.RenderState)
 */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new BodyRenderState(prevRenderState, this);
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.domimpl.HTMLAbstractUIElement#getOnload()
 */
    @Override
    public Function getOnload() {
        Object document = this.document;
        if (document instanceof HTMLDocumentImpl) {
            return ((HTMLDocumentImpl) document).getOnloadHandler();
        } else {
            return null;
        }
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.domimpl.HTMLAbstractUIElement#setOnload(org.mozilla.
 * javascript.Function)
 */
    @Override
    public void setOnload(Function onload) {
        Object document = this.document;
        if (document instanceof HTMLDocumentImpl) {
            // Note that body.onload overrides
            // Window.onload.
            ((HTMLDocumentImpl) document).setOnloadHandler(onload);
        }
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.domimpl.HTMLAbstractUIElement#assignAttributeField(java
 * .lang.String, java.lang.String)
 */
    @Override
    protected void assignAttributeField(String normalName, String value) {
        if (HtmlAttributeProperties.ONLOAD.equals(normalName)) {
            Function onload = this.getEventFunction(null, normalName);
            if (onload != null) {
                this.setOnload(onload);
            }
        } else {
            super.assignAttributeField(normalName, value);
        }
    }
}
