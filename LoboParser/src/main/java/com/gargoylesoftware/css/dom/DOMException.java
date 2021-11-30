/*
 * Copyright (c) 2019-2021 Ronald Brill.
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

/**
 * The DOMException class
 */
public class DOMException extends RuntimeException {

    public static final short INDEX_SIZE_ERR = 1;

    public static final short DOMSTRING_SIZE_ERR = 2;

    public static final short HIERARCHY_REQUEST_ERR = 3;

    public static final short WRONG_DOCUMENT_ERR = 4;

    public static final short INVALID_CHARACTER_ERR = 5;

    public static final short NO_DATA_ALLOWED_ERR = 6;

    public static final short NO_MODIFICATION_ALLOWED_ERR = 7;

    public static final short NOT_FOUND_ERR = 8;

    public static final short NOT_SUPPORTED_ERR = 9;

    public static final short INUSE_ATTRIBUTE_ERR = 10;

    public static final short INVALID_STATE_ERR = 11;

    public static final short SYNTAX_ERR = 12;

    public static final short INVALID_MODIFICATION_ERR = 13;

    public static final short NAMESPACE_ERR = 14;

    public static final short INVALID_ACCESS_ERR = 15;

    public static final short VALIDATION_ERR = 16;

    public static final short TYPE_MISMATCH_ERR = 17;

    /**
     * SYNTAX_ERROR = 0.
     */
    public static final int SYNTAX_ERROR = 0;
    /**
     * INDEX_OUT_OF_BOUNDS = 1.
     */
    public static final int INDEX_OUT_OF_BOUNDS = 1;
    /**
     * READ_ONLY_STYLE_SHEET = 2.
     */
    public static final int READ_ONLY_STYLE_SHEET = 2;
    /**
     * EXPECTING_STYLE_RULE = 3.
     */
    public static final int EXPECTING_STYLE_RULE = 3;
    /**
     * EXPECTING_CHARSET_RULE = 4.
     */
    public static final int EXPECTING_CHARSET_RULE = 4;
    /**
     * EXPECTING_IMPORT_RULE = 5.
     */
    public static final int EXPECTING_IMPORT_RULE = 5;
    /**
     * EXPECTING_MEDIA_RULE = 6.
     */
    public static final int EXPECTING_MEDIA_RULE = 6;
    /**
     * EXPECTING_FONT_FACE_RULE = 7.
     */
    public static final int EXPECTING_FONT_FACE_RULE = 7;
    /**
     * EXPECTING_PAGE_RULE = 8.
     */
    public static final int EXPECTING_PAGE_RULE = 8;
    /**
     * FLOAT_ERROR = 9.
     */
    public static final int FLOAT_ERROR = 9;
    /**
     * STRING_ERROR = 10.
     */
    public static final int STRING_ERROR = 10;
    /**
     * CHARSET_NOT_FIRST = 11.
     */
    public static final int CHARSET_NOT_FIRST = 11;
    /**
     * CHARSET_NOT_UNIQUE = 12.
     */
    public static final int CHARSET_NOT_UNIQUE = 12;
    /**
     * IMPORT_NOT_FIRST = 13.
     */
    public static final int IMPORT_NOT_FIRST = 13;
    /**
     * NOT_FOUND = 14.
     */
    public static final int NOT_FOUND = 14;
    /**
     * NOT_IMPLEMENTED = 15.
     */
    public static final int NOT_IMPLEMENTED = 15;
    /**
     * INSERT_BEFORE_IMPORT = 16.
     */
    public static final int INSERT_BEFORE_IMPORT = 16;

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
            "A charset rule must be the first rule",
            "A charset rule already exists",
            "An import rule must preceed all other rules",
            "The specified type was not found",
            "The functionality is not implemented",
            "Can't insert a rule before the last charset or import rule"
    };

    private final short code;

    private final String message;

    public DOMException(short code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public DOMException(short code, short codeMessage) {
        super(messages[codeMessage]);
        this.code = code;
        this.message = messages[codeMessage];
    }

    public DOMException(short code, int codeMessage) {
        super(messages[codeMessage]);
        this.code = code;
        this.message = messages[codeMessage];
    }

    /**
     * Ctor.
     * @param code the code
     * @param messageKey the message key
     * @param info additional info
     */
    public DOMException(final int code, final int messageKey, final String info) {
        super(messages[messageKey] + " (" + info + ")");
        this.code = (short)code;
        this.message = messages[messageKey] + " (" + info + ")";
    }

    /**
     * <p>Getter for the field <code>code</code>.</p>
     */
    public short getCode() {
        return code;
    }

    /**
     * <p>Getter for the field <code>message</code>.</p>
     */
    public String getMessage() {
        return message;
    }
}
