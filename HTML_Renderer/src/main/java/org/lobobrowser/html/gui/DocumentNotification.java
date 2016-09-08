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
package org.lobobrowser.html.gui;

import org.lobobrowser.html.domimpl.DOMNodeImpl;

/**
 * The Class DocumentNotification.
 */
public class DocumentNotification {

    /** The Constant LOOK. */
    public static final int LOOK = 0;

    /** The Constant POSITION. */
    public static final int POSITION = 1;

    /** The Constant SIZE. */
    public static final int SIZE = 2;

    /** The Constant GENERIC. */
    public static final int GENERIC = 3;

    /** The type. */
    public final int type;

    /** The node. */
    public final DOMNodeImpl node;

    /**
     * Instantiates a new document notification.
     *
     * @param type
     *            the type
     * @param node
     *            the node
     */
    public DocumentNotification(int type, DOMNodeImpl node) {
        this.type = type;
        this.node = node;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DocumentNotification[type=" + this.type + ",node=" + this.node
                + "]";
    }
}
