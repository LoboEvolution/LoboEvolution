/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.pdfview.function.postscript;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * **************************************************************************
 * Very simple post script parser / tokenizer
 * <p>
 * Author  Bernd Rosstauscher
 *
 * @since 22.10.2010
 * **************************************************************************
 */
public class PostScriptParser {

    /**
     * **********************************************************************
     * Constructor
     * **********************************************************************
     */
    public PostScriptParser() {
        super();
    }

    /**
     * **********************************************************************
     * Parses the given script and returns a list of tokens.
     *
     * @param scriptContent to parse.
     * @return the list of tokens.
     * **********************************************************************
     */
    public List<String> parse(final String scriptContent) {
        final List<String> tokens = new LinkedList<>();
        final StringTokenizer tok = new StringTokenizer(scriptContent, " \t\n\r");
        while (tok.hasMoreTokens()) {
            String t = tok.nextToken();
            t = filterBlockStart(t);
            t = filterBlockEnd(t);
            if (t.length() > 0) {
                tokens.add(t.trim());
            }
        }
        return tokens;
    }

    /*************************************************************************
     * @param token a {@link String} object.
     * @return a {@link String} object.
     ************************************************************************/
    private String filterBlockEnd(final String token) {
        String t = token;
        if (t.endsWith("}")) {
            t = t.substring(0, t.length() - 1);
        }
        return t;
    }

    /*************************************************************************
     * @param token a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     ************************************************************************/
    private String filterBlockStart(final String token) {
        String t = token;
        if (t.startsWith("{")) {
            t = t.substring(1);
        }
        return t;
    }
}