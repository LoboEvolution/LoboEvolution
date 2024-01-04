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
package org.jpedal.jbig2;

/**
 * <p>JBIG2Exception class.</p>
 */
public class JBIG2Exception extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for JBIG2Exception.</p>
     *
     * @param ex a {@link java.lang.Exception} object.
     */
    public JBIG2Exception(final Exception ex) {
        super(ex);
    }

    /**
     * Constructs a <CODE>JBIGException</CODE> whithout a message.
     */
    public JBIG2Exception() {
        super();
    }

    /**
     * Constructs a <code>JBIGException</code> with a message.
     *
     * @param message a message describing the exception
     */
    public JBIG2Exception(final String message) {
        super(message);
    }
}
