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
/*
 * Created on Oct 23, 2005
 */
package org.lobobrowser.html.parser;

import java.util.Set;

/**
 * The Class ElementInfo.
 */
public class ElementInfo {

    /** The end element type. */
    public final int endElementType;

    /** The child element ok. */
    public final boolean childElementOk;

    /** The stop tags. */
    public final Set<String> stopTags;

    /** The no script element. */
    public final boolean noScriptElement;

    /** The decode entities. */
    public final boolean decodeEntities;

    /** The Constant END_ELEMENT_FORBIDDEN. */
    public static final int END_ELEMENT_FORBIDDEN = 0;

    /** The Constant END_ELEMENT_OPTIONAL. */
    public static final int END_ELEMENT_OPTIONAL = 1;

    /** The Constant END_ELEMENT_REQUIRED. */
    public static final int END_ELEMENT_REQUIRED = 2;

    /**
     * Instantiates a new element info.
     *
     * @param ok
     *            the ok
     * @param type
     *            the type
     */
    public ElementInfo(boolean ok, int type) {
        this.childElementOk = ok;
        this.endElementType = type;
        this.stopTags = null;
        this.noScriptElement = false;
        this.decodeEntities = true;
    }

    /**
     * Instantiates a new element info.
     *
     * @param ok
     *            the ok
     * @param type
     *            the type
     * @param stopTags
     *            the stop tags
     */
    public ElementInfo(boolean ok, int type, Set<String> stopTags) {
        this.childElementOk = ok;
        this.endElementType = type;
        this.stopTags = stopTags;
        this.noScriptElement = false;
        this.decodeEntities = true;
    }

    /**
     * Instantiates a new element info.
     *
     * @param ok
     *            the ok
     * @param type
     *            the type
     * @param stopTags
     *            the stop tags
     * @param noScriptElement
     *            the no script element
     */
    public ElementInfo(boolean ok, int type, Set<String> stopTags,
            boolean noScriptElement) {
        this.childElementOk = ok;
        this.endElementType = type;
        this.stopTags = stopTags;
        this.noScriptElement = noScriptElement;
        this.decodeEntities = true;
    }

    /**
     * Instantiates a new element info.
     *
     * @param ok
     *            the ok
     * @param type
     *            the type
     * @param decodeEntities
     *            the decode entities
     */
    public ElementInfo(boolean ok, int type, boolean decodeEntities) {
        this.childElementOk = ok;
        this.endElementType = type;
        this.stopTags = null;
        this.noScriptElement = false;
        this.decodeEntities = decodeEntities;
    }
}
