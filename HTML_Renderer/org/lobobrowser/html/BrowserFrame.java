/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jan 29, 2006
 */
package org.lobobrowser.html;

import java.awt.Component;
import java.awt.Insets;
import java.net.URL;

import org.w3c.dom.Document;

/**
 * The <code>BrowserFrame</code> interface represents a browser frame. A simple
 * implementation of this interface is provided in
 * {@link org.lobobrowser.html.test.SimpleBrowserFrame}.
 */
public interface BrowserFrame {

    /**
     * Gets the component that renders the frame. This can be a
     * {@link org.lobobrowser.html.gui.HtmlPanel}.
     *
     * @return the component
     */
    Component getComponent();

    /**
     * Loads a URL in the frame.
     *
     * @param url
     *            the url
     */
    void loadURL(URL url);

    /**
     * Gets the content document.
     *
     * @return the content document
     */
    Document getContentDocument();

    /**
     * Gets the {@link HtmlRendererContext} of the frame.
     *
     * @return the html renderer context
     */
    HtmlRendererContext getHtmlRendererContext();

    /**
     * Sets the default margin insets of the browser frame.
     *
     * @param insets
     *            The margin insets.
     */
    void setDefaultMarginInsets(Insets insets);

    /**
     * Sets the default horizontal overflow of the browser frame.
     *
     * @param overflowX
     *            See constants in
     *            {@link org.lobobrowser.html.renderstate.RenderState}.
     */
    void setDefaultOverflowX(int overflowX);

    /**
     * Sets the default vertical overflow of the browser frame.
     *
     * @param overflowY
     *            See constants in
     *            {@link org.lobobrowser.html.renderstate.RenderState}.
     */
    void setDefaultOverflowY(int overflowY);
}
