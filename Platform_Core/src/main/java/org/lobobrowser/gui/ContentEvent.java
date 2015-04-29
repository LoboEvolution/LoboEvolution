/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.gui;

import java.util.EventObject;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;

/**
 * The Class ContentEvent.
 */
public class ContentEvent extends EventObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** The content. */
    private final ComponentContent content;

    /** The response. */
    private final ClientletResponse response;

    /**
     * Instantiates a new content event.
     *
     * @param source
     *            the source
     * @param content
     *            the content
     * @param response
     *            the response
     */
    public ContentEvent(Object source, ComponentContent content,
            ClientletResponse response) {
        super(source);
        this.content = content;
        this.response = response;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public ComponentContent getContent() {
        return content;
    }

    /**
     * Gets the response.
     *
     * @return the response
     */
    public ClientletResponse getResponse() {
        return response;
    }
}
