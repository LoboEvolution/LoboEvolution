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
package org.lobobrowser.html.renderer;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.ListStyle;
import org.lobobrowser.http.UserAgentContext;

/**
 * The Class BaseRListElement.
 */
class BaseRListElement extends RBlock {

    /** The Constant DEFAULT_COUNTER_NAME. */
    protected static final String DEFAULT_COUNTER_NAME = "$cobra.counter";

    /** The list style. */
    protected ListStyle listStyle = null;

    /**
     * Instantiates a new base r list element.
     *
     * @param modelNode
     *            the model node
     * @param listNesting
     *            the list nesting
     * @param pcontext
     *            the pcontext
     * @param rcontext
     *            the rcontext
     * @param frameContext
     *            the frame context
     * @param parentContainer
     *            the parent container
     */
    public BaseRListElement(DOMNodeImpl modelNode, int listNesting,
            UserAgentContext pcontext, HtmlRendererContext rcontext,
            FrameContext frameContext, RenderableContainer parentContainer) {
        super(modelNode, listNesting, pcontext, rcontext, frameContext,
                parentContainer);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BaseElementRenderable#applyStyle(int, int)
     */
    @Override
    protected void applyStyle(int availWidth, int availHeight) {
        this.listStyle = null;
        super.applyStyle(availWidth, availHeight);
        Object rootNode = this.modelNode;
        if (!(rootNode instanceof HTMLElementImpl)) {
            return;
        }
        HTMLElementImpl rootElement = (HTMLElementImpl) rootNode;
        AbstractCSS2Properties props = rootElement.getCurrentStyle();
        if (props == null) {
            return;
        }
        ListStyle listStyle = null;
        String listStyleText = props.getListStyle();
        if (listStyleText != null) {
            listStyle = HtmlValues.getListStyle(listStyleText);
        }
        String listStyleTypeText = props.getListStyleType();
        if (listStyleTypeText != null) {
            int listType = HtmlValues.getListStyleType(listStyleTypeText);
            if (listType != ListStyle.TYPE_UNSET) {
                if (listStyle == null) {
                    listStyle = new ListStyle();
                }
                listStyle.type = listType;
            }
        }
        if ((listStyle == null) || (listStyle.type == ListStyle.TYPE_UNSET)) {
            String typeAttributeText = rootElement
                    .getAttribute(HtmlAttributeProperties.TYPE);
            if (typeAttributeText != null) {
                int newStyleType = HtmlValues
                        .getListStyleTypeDeprecated(typeAttributeText);
                if (newStyleType != ListStyle.TYPE_UNSET) {
                    if (listStyle == null) {
                        listStyle = new ListStyle();
                        this.listStyle = listStyle;
                    }
                    listStyle.type = newStyleType;
                }
            }
        }
        this.listStyle = listStyle;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.RBlock#toString()
     */
    @Override
    public String toString() {
        return "BaseRListElement[node=" + this.modelNode + "]";
    }
}
