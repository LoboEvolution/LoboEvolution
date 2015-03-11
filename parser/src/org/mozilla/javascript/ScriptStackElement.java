/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;


/**
 * This class represents an element on the script execution stack.
 *
 * @author Hannes Wallnoefer
 * @see RhinoException#getScriptStack()
 * @since 1.7R3
 */
public final class ScriptStackElement implements Serializable {

    /** The Constant serialVersionUID. */
    static final long serialVersionUID = -6416688260860477449L;
    
    /** The file name. */
    public final String fileName;
    
    /** The function name. */
    public final String functionName;
    
    /** The line number. */
    public final int lineNumber;
    
    /** The column number. */
    public final int columnNumber;

    /**
     * Instantiates a new script stack element.
     *
     * @param fileName the file name
     * @param functionName the function name
     * @param lineNumber the line number
     * @param columnNumber the column number
     */
    public ScriptStackElement(String fileName, String functionName, int lineNumber, int columnNumber) {
        this.fileName = fileName;
        this.functionName = functionName;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * Instantiates a new script stack element.
     *
     * @param fileName the file name
     * @param functionName the function name
     * @param lineNumber the line number
     */
    public ScriptStackElement(String fileName, String functionName, int lineNumber)
    {
        this(fileName, functionName, lineNumber, -1);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        renderMozillaStyle(sb);
        return sb.toString();
    }

    /**
     * Render the stack trace to the specified StringBuilder using the appropriate style,
     * as defined in RhinoException.
     *
     * @param buffer the buffer
     * @since 1.7R5
     */
    public void render(StringBuilder buffer)
    {
        if (RhinoException.getStackStyle() == StackStyle.MOZILLA) {
            renderMozillaStyle(buffer);
        } else if (RhinoException.getStackStyle() == StackStyle.V8) {
            renderV8Style(buffer, true);
        } else {
            renderJavaStyle(buffer);
        }
    }

    /**
     * Render the stack trace to the specified StringBuilder using the appropriate style,
     * as defined in RhinoException. Render only the location, with no "at" stuff...
     *
     * @param buffer the buffer
     * @since 1.7R5
     */
    public void renderLocation(StringBuilder buffer)
    {
        if (RhinoException.getStackStyle() == StackStyle.MOZILLA) {
            renderMozillaStyle(buffer);
        } else if (RhinoException.getStackStyle() == StackStyle.V8) {
            renderV8Style(buffer, false);
        } else {
            renderJavaStyle(buffer);
        }
    }

    /**
     * Render stack element in Java-inspired style:
     * <code> at fileName:lineNumber (functionName)</code>.
     *
     * @param sb the StringBuilder to append to
     */
    public void renderJavaStyle(StringBuilder sb) {
        sb.append("\tat ").append(fileName);
        if (lineNumber > -1) {
            sb.append(':').append(lineNumber);
        }
        if (functionName != null) {
            sb.append(" (").append(functionName).append(')');
        }
    }

    /**
     * Render stack element in Mozilla/Firefox style:
     * <code>functionName()@fileName:lineNumber</code>.
     *
     * @param sb the StringBuilder to append to
     */
    public void renderMozillaStyle(StringBuilder sb) {
        if (functionName != null) {
            sb.append(functionName).append("()");
        }
        sb.append('@').append(fileName);
        if (lineNumber > -1) {
            sb.append(':').append(lineNumber);
        }
    }

    /**
     * Render stack element in V8 style:
     * </code>    at functionName (fileName:lineNumber:columnNumber)</code>
     * or:
     * </code>    at fileName:lineNumber:columnNumber</code>.
     *
     * @param sb the StringBuilder to append to
     * @param stackStyle the stack style
     */
    private void renderV8Style(StringBuilder sb, boolean stackStyle) {
        if (stackStyle) {
            sb.append("    at ");
        }

        if ((functionName == null) || "anonymous".equals(functionName)) {
            // Anonymous functions in V8 don't have names in the stack trace
            appendV8Location(sb);

        } else {
            sb.append(functionName).append(" (");
            appendV8Location(sb);
            sb.append(')');
        }
    }

    /**
     * Append v8 location.
     *
     * @param sb the sb
     */
    private void appendV8Location(StringBuilder sb)
    {
        sb.append(fileName);
        if (lineNumber > -1) {
            sb.append(':').append(lineNumber);
        }
        if (columnNumber > -1) {
            sb.append(':').append(columnNumber);
        }
    }
}
