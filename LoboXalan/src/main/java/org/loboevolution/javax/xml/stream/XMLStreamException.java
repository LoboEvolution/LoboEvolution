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

/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.loboevolution.javax.xml.stream;

public class XMLStreamException extends Exception {

    protected Throwable nestedException;
    protected Location location;

    /**
     * Default constructor
     */
    public XMLStreamException() {
        super();
    }

    /**
     * Construct an exception with the assocated message.
     *
     * @param msg the message to report
     */
    public XMLStreamException(final String msg) {
        super(msg);
    }

    /**
     * Construct an exception with the assocated exception
     *
     * @param th a nested exception
     */
    public XMLStreamException(final Throwable th) {
        super(th);
        nestedException = th;
    }

    /**
     * Construct an exception with the assocated message and exception
     *
     * @param th  a nested exception
     * @param msg the message to report
     */
    public XMLStreamException(final String msg, final Throwable th) {
        super(msg, th);
        nestedException = th;
    }

    /**
     * Construct an exception with the assocated message, exception and location.
     *
     * @param th       a nested exception
     * @param msg      the message to report
     * @param location the location of the error
     */
    public XMLStreamException(final String msg, final Location location, final Throwable th) {
        super("ParseError at [row,col]:[" + location.getLineNumber() + "," +
                location.getColumnNumber() + "]\n" +
                "Message: " + msg);
        nestedException = th;
        this.location = location;
    }

    /**
     * Construct an exception with the assocated message, exception and location.
     *
     * @param msg      the message to report
     * @param location the location of the error
     */
    public XMLStreamException(final String msg,
                              final Location location) {
        super("ParseError at [row,col]:[" + location.getLineNumber() + "," +
                location.getColumnNumber() + "]\n" +
                "Message: " + msg);
        this.location = location;
    }
}
