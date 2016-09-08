/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
 * Created on Feb 12, 2006
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.ListRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.w3c.html.HTMLOListElement;

/**
 * The Class HTMLOListElementImpl.
 */
public class HTMLOListElementImpl extends HTMLAbstractUIElement implements
HTMLOListElement {

    /**
     * Instantiates a new HTMLO list element impl.
     *
     * @param name
     *            the name
     */
    public HTMLOListElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLOListElement#getCompact()
     */
    @Override
    public boolean getCompact() {
        String compactText = this.getAttribute(HtmlAttributeProperties.COMPACT);
        return HtmlAttributeProperties.COMPACT.equalsIgnoreCase(compactText);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLOListElement#setCompact(boolean)
     */
    @Override
    public void setCompact(boolean compact) {
        this.setAttribute(HtmlAttributeProperties.COMPACT,
                compact ? HtmlAttributeProperties.COMPACT : null);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLOListElement#getStart()
     */
    @Override
    public int getStart() {
        String startText = this.getAttribute(HtmlAttributeProperties.START);
        if (startText == null) {
            return 1;
        }
        try {
            return Integer.parseInt(startText);
        } catch (NumberFormatException nfe) {
            return 1;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLOListElement#setStart(int)
     */
    @Override
    public void setStart(int start) {
        this.setAttribute(HtmlAttributeProperties.START, String.valueOf(start));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLOListElement#getType()
     */
    @Override
    public String getType() {
        return this.getAttribute(HtmlAttributeProperties.TYPE);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLOListElement#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        this.setAttribute(HtmlAttributeProperties.TYPE, type);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser
     * .html.renderstate.RenderState)
     */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new ListRenderState(prevRenderState, this);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLOListElement#getReversed()
     */
    @Override
    public boolean getReversed() {
        String reversed = this.getAttribute(HtmlAttributeProperties.RESERVED);
        return HtmlAttributeProperties.RESERVED.equalsIgnoreCase(reversed);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLOListElement#setReversed(boolean)
     */
    @Override
    public void setReversed(boolean reversed) {
        this.setAttribute(HtmlAttributeProperties.RESERVED,
                reversed ? HtmlAttributeProperties.RESERVED : null);

    }
}
