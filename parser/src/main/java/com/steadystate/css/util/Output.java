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

package com.steadystate.css.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * A simple helper to write formated to a writer.
 *
 * @author rbri
 */
public final class Output {
    private static final String NEW_LINE = System.getProperty("line.separator");

    private Writer writer_;
    private StringBuffer currentIndent_;
    private boolean afterNewLine_;
    private final String indent_;

    /**
     * Constructor.
     *
     * @param aWriter the writer to write to
     * @param anIndent String to be used for indenting (e.g. "", " ", " ", "\t")
     */
    public Output(final Writer aWriter, final String anIndent) {
        writer_ = new BufferedWriter(aWriter);
        indent_ = anIndent;
        currentIndent_ = new StringBuffer();
    }

    /**
     * Write the char.
     *
     * @param aChar the char to be written
     * @return this (for convenience)
     * @throws IOException in case of problems
     */
    public Output print(final char aChar) throws IOException {
        writeIndentIfNeeded();
        writer_.write(aChar);
        return this;
    }

    /**
     * Write the String.
     *
     * @param aString the string to be written
     * @return this (for convenience)
     * @throws IOException in case of problems
     */
    public Output print(final String aString) throws IOException {
        if (null != aString) {
            writeIndentIfNeeded();
            writer_.write(aString);
        }
        return this;
    }

    /**
     * Write the string on a new line.
     *
     * @param aString the string to be written
     * @return this (for convenience)
     * @throws IOException in case of problems
     */
    public Output println(final String aString) throws IOException {
        writeIndentIfNeeded();
        writer_.write(aString);
        writer_.write(NEW_LINE);
        afterNewLine_ = true;
        return this;
    }

    /**
     * Start a newline.
     *
     * @return this (for convenience)
     * @throws IOException in case of problems
     */
    public Output println() throws IOException {
        writer_.write(NEW_LINE);
        afterNewLine_ = true;
        return this;
    }

    /**
     * Flushes the output.
     *
     * @return this (for convenience)
     * @throws IOException in case of error
     */
    public Output flush() throws IOException {
        writer_.flush();
        return this;
    }

    /**
     * Indent the following.
     *
     * @return this (for convenience)
     */
    public Output indent() {
        currentIndent_.append(indent_);
        return this;
    }

    /**
     * Clear the indent.
     *
     * @return this (for convenience)
     */
    public Output unindent() {
        currentIndent_.setLength(Math.max(0, currentIndent_.length() - indent_.length()));
        return this;
    }

    /**
     * Helper to write a newline.
     *
     * @throws IOException
     *             in case of problems
     */
    private void writeIndentIfNeeded() throws IOException {
        if (afterNewLine_) {
            writer_.write(currentIndent_.toString());
            afterNewLine_ = false;
        }
    }
}
