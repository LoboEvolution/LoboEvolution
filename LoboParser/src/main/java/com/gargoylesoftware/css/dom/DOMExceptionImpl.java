/*
 * Copyright (c) 2019 Ronald Brill.
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
package com.gargoylesoftware.css.dom;

import org.w3c.dom.DOMException;

/**
 * Custom {@link org.w3c.dom.DOMException} extension.
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public class DOMExceptionImpl extends DOMException {
	
	private static final long serialVersionUID = 1L;

    /** SYNTAX_ERROR = 0. */
    public static final int SYNTAX_ERROR = 0;
    /** INDEX_OUT_OF_BOUNDS = 1. */
    public static final int INDEX_OUT_OF_BOUNDS = 1;
    /** READ_ONLY_STYLE_SHEET = 2. */
    public static final int READ_ONLY_STYLE_SHEET = 2;
    /** EXPECTING_STYLE_RULE = 3. */
    public static final int EXPECTING_STYLE_RULE = 3;
    /** EXPECTING_CHARSET_RULE = 4. */
    public static final int EXPECTING_CHARSET_RULE = 4;
    /** EXPECTING_IMPORT_RULE = 5. */
    public static final int EXPECTING_IMPORT_RULE = 5;
    /** EXPECTING_MEDIA_RULE = 6. */
    public static final int EXPECTING_MEDIA_RULE = 6;
    /** EXPECTING_FONT_FACE_RULE = 7. */
    public static final int EXPECTING_FONT_FACE_RULE = 7;
    /** EXPECTING_PAGE_RULE = 8. */
    public static final int EXPECTING_PAGE_RULE = 8;
    /** FLOAT_ERROR = 9. */
    public static final int FLOAT_ERROR = 9;
    /** STRING_ERROR = 10. */
    public static final int STRING_ERROR = 10;
    /** COUNTER_ERROR = 11. */
    public static final int COUNTER_ERROR = 11;
    /** RECT_ERROR = 12. */
    public static final int RECT_ERROR = 12;
    /** RGBCOLOR_ERROR = 13. */
    public static final int RGBCOLOR_ERROR = 13;
    /** CHARSET_NOT_FIRST = 14. */
    public static final int CHARSET_NOT_FIRST = 14;
    /** CHARSET_NOT_UNIQUE = 15. */
    public static final int CHARSET_NOT_UNIQUE = 15;
    /** IMPORT_NOT_FIRST = 16. */
    public static final int IMPORT_NOT_FIRST = 16;
    /** NOT_FOUND = 17. */
    public static final int NOT_FOUND = 17;
    /** NOT_IMPLEMENTED = 18. */
    public static final int NOT_IMPLEMENTED = 18;
    /** INSERT_BEFORE_IMPORT = 19. */
    public static final int INSERT_BEFORE_IMPORT = 19;

    static final String[] messages = {
        "Syntax error",
        "Index out of bounds error",
        "This style sheet is read only",
        "The text does not represent a style rule",
        "The text does not represent a charset rule",
        "The text does not represent an import rule",
        "The text does not represent a media rule",
        "The text does not represent a font face rule",
        "The text does not represent a page rule",
        "This isn't a Float type",
        "This isn't a String type",
        "This isn't a Counter type",
        "This isn't a Rect type",
        "This isn't an RGBColor type",
        "A charset rule must be the first rule",
        "A charset rule already exists",
        "An import rule must preceed all other rules",
        "The specified type was not found",
        "The functionality is not implemented",
        "Can't insert a rule before the last charset or import rule"
    };

    /**
     * Ctor.
     *
     * @param code the code
     * @param messageKey the message key
     */
    public DOMExceptionImpl(final short code, final int messageKey) {
        this(code, messageKey, null);
    }

    /**
     * Ctor.
     *
     * @param code the code
     * @param messageKey the message key
     */
    public DOMExceptionImpl(final int code, final int messageKey) {
        this(code, messageKey, null);
    }

    /**
     * Ctor.
     *
     * @param code the code
     * @param messageKey the message key
     * @param info additional info
     */
    public DOMExceptionImpl(final int code, final int messageKey, final String info) {
        super((short) code, messages[messageKey] + " (" + info + ")");
    }
}
