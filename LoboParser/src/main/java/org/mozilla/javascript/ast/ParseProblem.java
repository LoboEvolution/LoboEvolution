/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

/**
 * Encapsulates information for a JavaScript parse error or warning.
 *
 * @author utente
 * @version $Id: $Id
 */
public class ParseProblem {

    public static enum Type {Error, Warning}

    private Type type;
    private String message;
    private String sourceName;
    private int offset;
    private int length;

    /**
     * Constructs a new ParseProblem.
     *
     * @param type a {@link org.mozilla.javascript.ast.ParseProblem.Type} object.
     * @param message a {@link java.lang.String} object.
     * @param sourceName a {@link java.lang.String} object.
     * @param offset a int.
     * @param length a int.
     */
    public ParseProblem(ParseProblem.Type type, String message,
                        String sourceName, int offset, int length) {
        setType(type);
        setMessage(message);
        setSourceName(sourceName);
        setFileOffset(offset);
        setLength(length);
    }

    /**
     * <p>Getter for the field type.</p>
     *
     * @return a {@link org.mozilla.javascript.ast.ParseProblem.Type} object.
     */
    public ParseProblem.Type getType() {
        return type;
    }

    /**
     * <p>Setter for the field type.</p>
     *
     * @param type a {@link org.mozilla.javascript.ast.ParseProblem.Type} object.
     */
    public void setType(ParseProblem.Type type) {
        this.type = type;
    }

    /**
     * <p>Getter for the field message.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>Setter for the field message.</p>
     *
     * @param msg a {@link java.lang.String} object.
     */
    public void setMessage(String msg) {
        this.message = msg;
    }

    /**
     * <p>Getter for the field sourceName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * <p>Setter for the field sourceName.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setSourceName(String name) {
        this.sourceName = name;
    }

    /**
     * <p>getFileOffset.</p>
     *
     * @return a int.
     */
    public int getFileOffset() {
        return offset;
    }

    /**
     * <p>setFileOffset.</p>
     *
     * @param offset a int.
     */
    public void setFileOffset(int offset) {
        this.offset = offset;
    }

    /**
     * <p>Getter for the field length.</p>
     *
     * @return a int.
     */
    public int getLength() {
        return length;
    }

    /**
     * <p>Setter for the field length.</p>
     *
     * @param length a int.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(200);
        sb.append(sourceName).append(":");
        sb.append("offset=").append(offset).append(",");
        sb.append("length=").append(length).append(",");
        sb.append(type == Type.Error ? "error: " : "warning: ");
        sb.append(message);
        return sb.toString();
    }
}
