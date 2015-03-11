/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;


/**
 * Encapsulates information for a JavaScript parse error or warning.
 */
public class ParseProblem {

    /**
     * The Enum Type.
     */
    public static enum Type {/** The Error. */
Error, /** The Warning. */
 Warning}

    /** The type. */
    private Type type;
    
    /** The message. */
    private String message;
    
    /** The source name. */
    private String sourceName;
    
    /** The offset. */
    private int offset;
    
    /** The length. */
    private int length;

    /**
     * Constructs a new ParseProblem.
     *
     * @param type the type
     * @param message the message
     * @param sourceName the source name
     * @param offset the offset
     * @param length the length
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
     * Gets the type.
     *
     * @return the type
     */
    public ParseProblem.Type getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(ParseProblem.Type type) {
        this.type = type;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param msg the new message
     */
    public void setMessage(String msg) {
        this.message = msg;
    }

    /**
     * Gets the source name.
     *
     * @return the source name
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * Sets the source name.
     *
     * @param name the new source name
     */
    public void setSourceName(String name) {
        this.sourceName = name;
    }

    /**
     * Gets the file offset.
     *
     * @return the file offset
     */
    public int getFileOffset() {
        return offset;
    }

    /**
     * Sets the file offset.
     *
     * @param offset the new file offset
     */
    public void setFileOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Gets the length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length.
     *
     * @param length the new length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
