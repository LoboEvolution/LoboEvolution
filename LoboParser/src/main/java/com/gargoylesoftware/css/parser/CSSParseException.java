/*
 * Copyright (c) 2019-2020 Ronald Brill.
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
package com.gargoylesoftware.css.parser;

/**
 * Encapsulate a CSS parse error or warning.
 *
 * Author Ronald Brill
 *
 */
public class CSSParseException extends CSSException {

    private String uri_;
    private int lineNumber_;
    private int columnNumber_;

    /**
     * Create a new CSSParseException from a message and a Locator.
     *
     * <p>This constructor is especially useful when an application is
     * creating its own exception from within a DocumentHandler
     * callback.</p>
     *
     * @param message The error or warning message.
     * @param locator The locator object for the error or warning.
     * @see Locator
     */
    public CSSParseException(final String message, final Locator locator) {
        this(message, locator, null);
    }

    /**
     *
     * Wrap an existing exception in a CSSParseException.
     *
     * <p>This constructor is especially useful when an application is
     * creating its own exception from within a DocumentHandler
     * callback, and needs to wrap an existing exception that is not a
     * subclass of CSSException.</p>
     *
     * @param message The error or warning message, or null to
     *                use the message from the embedded exception.
     * @param locator The locator object for the error or warning.
     * @param e Any exception
     * @see Locator
     */
    public CSSParseException(final String message, final Locator locator, final Exception e) {
        this(message, locator.getUri(), locator.getLineNumber(), locator.getColumnNumber(), e);
    }

    /**
     * Create a new CSSParseException.
     *
     * <p>This constructor is most useful for parser writers.</p>
     *
     * <p>the parser must resolve the URI fully before creating the exception.</p>
     *
     * @param message The error or warning message.
     * @param uri The URI of the document that generated the error or warning.
     * @param lineNumber The line number of the end of the text that
     *                   caused the error or warning.
     * @param columnNumber The column number of the end of the text that
     *                     cause the error or warning.
     */
    public CSSParseException(final String message, final String uri, final int lineNumber, final int columnNumber) {
        this(message, uri, lineNumber, columnNumber, null);
    }

    /**
     * Create a new CSSParseException with an embedded exception.
     *
     * <p>This constructor is most useful for parser writers who
     * need to wrap an exception that is not a subclass of
     * CSSException.</p>
     *
     * <p>The parser must resolve the URI fully before creating the
     * exception.</p>
     *
     * @param message The error or warning message, or null to use
     *                the message from the embedded exception.
     * @param uri The URI of the document that generated
     *                 the error or warning.
     * @param lineNumber The line number of the end of the text that
     *                   caused the error or warning.
     * @param columnNumber The column number of the end of the text that
     *                     cause the error or warning.
     * @param e Another exception to embed in this one.
     */
    public CSSParseException(final String message, final String uri,
            final int lineNumber, final int columnNumber, final Exception e) {
        super(ErrorCode.SYNTAX_ERR, message, e);
        uri_ = uri;
        lineNumber_ = lineNumber;
        columnNumber_ = columnNumber;
    }

    /**
     * Get the URI of the document where the exception occurred.
     *
     * <p>The URI will be resolved fully.</p>
     *
     * @return A string containing the URI, or null
     *         if none is available.
     */
    public String getURI() {
        return uri_;
    }

    /**
     * The line number of the end of the text where the exception occurred.
     *
     * @return An integer representing the line number, or -1
     *         if none is available.
     * @see Locator#getLineNumber
     */
    public int getLineNumber() {
        return lineNumber_;
    }

    /**
     * The column number of the end of the text where the exception occurred.
     *
     * <p>The first column in a line is position 1.</p>
     *
     * @return An integer representing the column number, or -1
     *         if none is available.
     * @see Locator#getColumnNumber
     */
    public int getColumnNumber() {
        return columnNumber_;
    }
}
