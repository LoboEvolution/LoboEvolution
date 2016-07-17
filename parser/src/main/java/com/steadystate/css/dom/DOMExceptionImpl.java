/*
 * Copyright (C) 1999-2016 David Schweinsberg.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

    private static final long serialVersionUID = 7365733663951145145L;

    public static final int SYNTAX_ERROR = 0;
    public static final int INDEX_OUT_OF_BOUNDS = 1;
    public static final int READ_ONLY_STYLE_SHEET = 2;
    public static final int EXPECTING_UNKNOWN_RULE = 3;
    public static final int EXPECTING_STYLE_RULE = 4;
    public static final int EXPECTING_CHARSET_RULE = 5;
    public static final int EXPECTING_IMPORT_RULE = 6;
    public static final int EXPECTING_MEDIA_RULE = 7;
    public static final int EXPECTING_FONT_FACE_RULE = 8;
    public static final int EXPECTING_PAGE_RULE = 9;
    public static final int FLOAT_ERROR = 10;
    public static final int STRING_ERROR = 11;
    public static final int COUNTER_ERROR = 12;
    public static final int RECT_ERROR = 13;
    public static final int RGBCOLOR_ERROR = 14;
    public static final int CHARSET_NOT_FIRST = 15;
    public static final int CHARSET_NOT_UNIQUE = 16;
    public static final int IMPORT_NOT_FIRST = 17;
    public static final int NOT_FOUND = 18;
    public static final int NOT_IMPLEMENTED = 19;
    public static final int INSERT_BEFORE_IMPORT = 20;

    private static ResourceBundle ExceptionResource_ =
        ResourceBundle.getBundle(
            "com.steadystate.css.parser.ExceptionResource",
            Locale.getDefault());

    public DOMExceptionImpl(final short code, final int messageKey) {
        this(code, messageKey, null);
    }

    public DOMExceptionImpl(final int code, final int messageKey) {
        this(code, messageKey, null);
    }

    public DOMExceptionImpl(final int code, final int messageKey, final String info) {
        super((short) code, constructMessage(messageKey, info));
    }

    private static String constructMessage(final int key, final String info) {
        final String messageKey = "s" + String.valueOf(key);
        String message = ExceptionResource_.getString(messageKey);
        if (null != info && info.length() > 0) {
            message = message + " (" + info + ")";
        }
        return message;
    }
}
