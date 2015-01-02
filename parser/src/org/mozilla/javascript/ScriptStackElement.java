/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;

/**
 * This class represents an element on the script execution stack.
 * @see RhinoException#getScriptStack()
 * @author Hannes Wallnoefer
 * @since 1.7R3
 */
public final class ScriptStackElement implements Serializable {

    static final long serialVersionUID = -6416688260860477449L;
    
    public final String fileName;
    public final String functionName;
    public final int lineNumber;
    public final int columnNumber;

    public ScriptStackElement(String fileName, String functionName, int lineNumber, int columnNumber) {
        this.fileName = fileName;
        this.functionName = functionName;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public ScriptStackElement(String fileName, String functionName, int lineNumber)
    {
        this(fileName, functionName, lineNumber, -1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        renderMozillaStyle(sb);
        return sb.toString();
    }

    /**
     * Render the stack trace to the specified StringBuilder using the appropriate style,
     * as defined in RhinoException.
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
     * <code>    at fileName:lineNumber (functionName)</code>
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
     * <code>functionName()@fileName:lineNumber</code>
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
     * <code>    at functionName (fileName:lineNumber:columnNumber)</code>
     * or:
     * <code>    at fileName:lineNumber:columnNumber</code>
     * @param sb the StringBuilder to append to
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
