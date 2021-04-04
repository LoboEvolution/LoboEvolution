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

import java.io.Serializable;

import com.gargoylesoftware.css.util.LangUtils;

/**
 * For associating a CSS event with a document location.
 *
 * Author Ronald Brill
 *
 */
public class Locator implements Serializable {

    private String uri_;
    private int lineNumber_;
    private int columnNumber_;

    /**
     * Creates new LocatorImpl.
     *
     * @param uri the uri
     * @param line the lineNumber
     * @param column the columnNumber
     */
    public Locator(final String uri, final int line, final int column) {
        uri_ = uri;
        lineNumber_ = line;
        columnNumber_ = column;
    }

    /**
     * <p>getUri.</p>
     *
     * @return the uri
     */
    public String getUri() {
        return uri_;
    }

    /**
     * Set the uri to a new value.
     *
     * @param uri the new uri
     */
    public void setUri(final String uri) {
        uri_ = uri;
    }

    /**
     * Return the column number where the current document event ends.
     * Note that this is the column number of the first
     * character after the text associated with the document
     * event.  The first column in a line is position 1.
     *
     * @return The column number, or -1 if none is available.
     * @see #getLineNumber
     */
    public int getColumnNumber() {
        return columnNumber_;
    }

    /**
     * Set the columnNumber to a new value.
     *
     * @param column the new columnNumber
     */
    public void setColumnNumber(final int column) {
        columnNumber_ = column;
    }

    /**
     * Return the line number where the current document event ends.
     * Note that this is the line position of the first character
     * after the text associated with the document event.
     *
     * @return The line number, or -1 if none is available.
     * @see #getColumnNumber
     */
    public int getLineNumber() {
        return lineNumber_;
    }

    /**
     * Set the lineNumber to a new value.
     *
     * @param line the new lineNumber
     */
    public void setLineNumber(final int line) {
        lineNumber_ = line;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Locator)) {
            return false;
        }
        final Locator l = (Locator) obj;
        return (getColumnNumber() == l.getColumnNumber())
            && (getLineNumber() == l.getLineNumber())
            && LangUtils.equals(getUri(), l.getUri());
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, columnNumber_);
        hash = LangUtils.hashCode(hash, lineNumber_);
        hash = LangUtils.hashCode(hash, uri_);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return new StringBuilder().append(getUri()).append(" (")
            .append(getLineNumber()).append(':')
            .append(getColumnNumber()).append(')').toString();
    }
}
