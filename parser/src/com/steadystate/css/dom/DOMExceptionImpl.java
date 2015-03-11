/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.dom;

import java.util.Locale;
import java.util.ResourceBundle;

import org.w3c.dom.DOMException;


/**
 * Custom {@link DOMException} extension.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class DOMExceptionImpl extends DOMException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7365733663951145145L;

    /** The Constant SYNTAX_ERROR. */
    public static final int SYNTAX_ERROR = 0;
    
    /** The Constant INDEX_OUT_OF_BOUNDS. */
    public static final int INDEX_OUT_OF_BOUNDS = 1;
    
    /** The Constant READ_ONLY_STYLE_SHEET. */
    public static final int READ_ONLY_STYLE_SHEET = 2;
    
    /** The Constant EXPECTING_UNKNOWN_RULE. */
    public static final int EXPECTING_UNKNOWN_RULE = 3;
    
    /** The Constant EXPECTING_STYLE_RULE. */
    public static final int EXPECTING_STYLE_RULE = 4;
    
    /** The Constant EXPECTING_CHARSET_RULE. */
    public static final int EXPECTING_CHARSET_RULE = 5;
    
    /** The Constant EXPECTING_IMPORT_RULE. */
    public static final int EXPECTING_IMPORT_RULE = 6;
    
    /** The Constant EXPECTING_MEDIA_RULE. */
    public static final int EXPECTING_MEDIA_RULE = 7;
    
    /** The Constant EXPECTING_FONT_FACE_RULE. */
    public static final int EXPECTING_FONT_FACE_RULE = 8;
    
    /** The Constant EXPECTING_PAGE_RULE. */
    public static final int EXPECTING_PAGE_RULE = 9;
    
    /** The Constant FLOAT_ERROR. */
    public static final int FLOAT_ERROR = 10;
    
    /** The Constant STRING_ERROR. */
    public static final int STRING_ERROR = 11;
    
    /** The Constant COUNTER_ERROR. */
    public static final int COUNTER_ERROR = 12;
    
    /** The Constant RECT_ERROR. */
    public static final int RECT_ERROR = 13;
    
    /** The Constant RGBCOLOR_ERROR. */
    public static final int RGBCOLOR_ERROR = 14;
    
    /** The Constant CHARSET_NOT_FIRST. */
    public static final int CHARSET_NOT_FIRST = 15;
    
    /** The Constant CHARSET_NOT_UNIQUE. */
    public static final int CHARSET_NOT_UNIQUE = 16;
    
    /** The Constant IMPORT_NOT_FIRST. */
    public static final int IMPORT_NOT_FIRST = 17;
    
    /** The Constant NOT_FOUND. */
    public static final int NOT_FOUND = 18;
    
    /** The Constant NOT_IMPLEMENTED. */
    public static final int NOT_IMPLEMENTED = 19;
    
    /** The Constant INSERT_BEFORE_IMPORT. */
    public static final int INSERT_BEFORE_IMPORT = 20;

    /** The Exception resource_. */
    private static ResourceBundle ExceptionResource_ =
        ResourceBundle.getBundle(
            "com.steadystate.css.parser.ExceptionResource",
            Locale.getDefault());

    /**
     * Instantiates a new DOM exception impl.
     *
     * @param code the code
     * @param messageKey the message key
     */
    public DOMExceptionImpl(final short code, final int messageKey) {
        this(code, messageKey, null);
    }

    /**
     * Instantiates a new DOM exception impl.
     *
     * @param code the code
     * @param messageKey the message key
     */
    public DOMExceptionImpl(final int code, final int messageKey) {
        this(code, messageKey, null);
    }

    /**
     * Instantiates a new DOM exception impl.
     *
     * @param code the code
     * @param messageKey the message key
     * @param info the info
     */
    public DOMExceptionImpl(final int code, final int messageKey, final String info) {
        super((short) code, constructMessage(messageKey, info));
    }

    /**
     * Construct message.
     *
     * @param key the key
     * @param info the info
     * @return the string
     */
    private static String constructMessage(final int key, final String info) {
        final String messageKey = "s" + String.valueOf(key);
        String message = ExceptionResource_.getString(messageKey);
        if (null != info && info.length() > 0) {
            message = message + " (" + info + ")";
        }
        return message;
    }
}
