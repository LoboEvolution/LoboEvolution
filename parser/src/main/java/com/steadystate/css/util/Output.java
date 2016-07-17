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
