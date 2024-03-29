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

package org.loboevolution.javax.xml.transform.stax;

import lombok.Getter;
import org.loboevolution.javax.xml.stream.XMLEventReader;
import org.loboevolution.javax.xml.stream.XMLStreamConstants;
import org.loboevolution.javax.xml.stream.XMLStreamException;
import org.loboevolution.javax.xml.stream.XMLStreamReader;
import org.loboevolution.javax.xml.stream.events.XMLEvent;
import org.loboevolution.javax.xml.transform.Source;

/**
 * <p>Acts as a holder for an XML {@link Source} in the
 * form of a StAX reader,i.e.
 * {@link XMLStreamReader} or {@link XMLEventReader}.
 * <code>StAXSource</code> can be used in all cases that accept
 * a <code>Source</code>, e.g. {@link org.loboevolution.javax.xml.transform.Transformer},
 * {@link org.loboevolution.javax.xml.validation.Validator} which accept
 * <code>Source</code> as input.
 *
 * <p><code>StAXSource</code>s are consumed during processing
 * and are not reusable.</p>
 *
 * @author <a href="mailto:Neeraj.Bajaj@Sun.com">Neeraj Bajaj</a>
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 *
 * @see <a href="http://jcp.org/en/jsr/detail?id=173">
 *  JSR 173: Streaming API for XML</a>
 * @see XMLStreamReader
 * @see XMLEventReader
 *
 * @since 1.6
 */
public class StAXSource implements Source {

    /**
     * returns true when passed this value as an argument,
     * the Transformer supports Source input of this type.
     */
    public static final String FEATURE =
        "http://org.loboevolution.javax.xml.transform.stax.StAXSource/feature";

    /** <p><code>XMLEventReader</code> to be used for source input.</p> */
    private XMLEventReader xmlEventReader = null;

    /** <p><code>XMLStreamReader</code> to be used for source input.</p> */
    private XMLStreamReader xmlStreamReader = null;

    /**
     * <p>Get the system identifier used by this
     * <code>StAXSource</code>.</p>
     *
     * <p>The <code>XMLStreamReader</code> or <code>XMLEventReader</code>
     * used to construct this <code>StAXSource</code> is queried to determine
     * the system identifier of the XML source.</p>
     *
     * <p>The system identifier may be <code>null</code> or
     * an empty <code>""</code> <code>String</code>.</p>
     *
     * System identifier used by this <code>StAXSource</code>.
     */
    @Getter
    private String systemId = null;

    /**
     * <p>Creates a new instance of a <code>StAXSource</code>
     * by supplying an {@link XMLEventReader}.</p>
     *
     * <p><code>XMLEventReader</code> must be a
     * non-<code>null</code> reference.</p>
     *
     * <p><code>XMLEventReader</code> must be in
     * {@link XMLStreamConstants#START_DOCUMENT} or
     * {@link XMLStreamConstants#START_ELEMENT} state.</p>
     *
     * @param xmlEventReader <code>XMLEventReader</code> used to create
     *   this <code>StAXSource</code>.
     *
     * @throws XMLStreamException If <code>xmlEventReader</code> access
     *   throws an <code>Exception</code>.
     * @throws IllegalArgumentException If <code>xmlEventReader</code> ==
     *   <code>null</code>.
     * @throws IllegalStateException If <code>xmlEventReader</code>
     *   is not in <code>XMLStreamConstants.START_DOCUMENT</code> or
     *   <code>XMLStreamConstants.START_ELEMENT</code> state.
     */
    public StAXSource(final XMLEventReader xmlEventReader)
        throws XMLStreamException {

        if (xmlEventReader == null) {
            throw new IllegalArgumentException(
                    "StAXSource(XMLEventReader) with XMLEventReader == null");
        }

        // TODO: This is ugly ...
        // there is no way to know the current position(event) of
        // XMLEventReader.  peek() is the only way to know the next event.
        // The next event on the input stream should be
        // XMLStreamConstants.START_DOCUMENT or
        // XMLStreamConstants.START_ELEMENT.
        final XMLEvent event = xmlEventReader.peek();
        final int eventType = event.getEventType();
        if (eventType != XMLStreamConstants.START_DOCUMENT
                && eventType != XMLStreamConstants.START_ELEMENT) {
            throw new IllegalStateException(
                "StAXSource(XMLEventReader) with XMLEventReader "
                + "not in XMLStreamConstants.START_DOCUMENT or "
                + "XMLStreamConstants.START_ELEMENT state");
        }

        this.xmlEventReader = xmlEventReader;
        systemId = event.getLocation().getSystemId();
    }

    /**
     * <p>Creates a new instance of a <code>StAXSource</code>
     * by supplying an {@link XMLStreamReader}.</p>
     *
     * <p><code>XMLStreamReader</code> must be a
     * non-<code>null</code> reference.</p>
     *
     * <p><code>XMLStreamReader</code> must be in
     * {@link XMLStreamConstants#START_DOCUMENT} or
     * {@link XMLStreamConstants#START_ELEMENT} state.</p>
     *
     * @param xmlStreamReader <code>XMLStreamReader</code> used to create
     *   this <code>StAXSource</code>.
     *
     * @throws IllegalArgumentException If <code>xmlStreamReader</code> ==
     *   <code>null</code>.
     * @throws IllegalStateException If <code>xmlStreamReader</code>
     *   is not in <code>XMLStreamConstants.START_DOCUMENT</code> or
     *   <code>XMLStreamConstants.START_ELEMENT</code> state.
     */
    public StAXSource(final XMLStreamReader xmlStreamReader) {

        if (xmlStreamReader == null) {
            throw new IllegalArgumentException(
                    "StAXSource(XMLStreamReader) with XMLStreamReader == null");
        }

        final int eventType = xmlStreamReader.getEventType();
        if (eventType != XMLStreamConstants.START_DOCUMENT
                && eventType != XMLStreamConstants.START_ELEMENT) {
            throw new IllegalStateException(
                    "StAXSource(XMLStreamReader) with XMLStreamReader"
                    + "not in XMLStreamConstants.START_DOCUMENT or "
                    + "XMLStreamConstants.START_ELEMENT state");
        }

        this.xmlStreamReader = xmlStreamReader;
        systemId = xmlStreamReader.getLocation().getSystemId();
    }

    /**
     * <p>Get the <code>XMLEventReader</code> used by this
     * <code>StAXSource</code>.</p>
     *
     * <p><code>XMLEventReader</code> will be <code>null</code>.
     * if this <code>StAXSource</code> was created with a
     * <code>XMLStreamReader</code>.</p>
     *
     * @return <code>XMLEventReader</code> used by this
     *   <code>StAXSource</code>.
     */
    public XMLEventReader getXMLEventReader() {

        return xmlEventReader;
    }

    /**
     * <p>Get the <code>XMLStreamReader</code> used by this
     * <code>StAXSource</code>.</p>
     *
     * <p><code>XMLStreamReader</code> will be <code>null</code>
     * if this <code>StAXSource</code> was created with a
     * <code>XMLEventReader</code>.</p>
     *
     * @return <code>XMLStreamReader</code> used by this
     *   <code>StAXSource</code>.
     */
    public XMLStreamReader getXMLStreamReader() {

        return xmlStreamReader;
    }

    /**
     * <p>In the context of a <code>StAXSource</code>, it is not appropriate
     * to explicitly set the system identifier.
     * The <code>XMLStreamReader</code> or <code>XMLEventReader</code>
     * used to construct this <code>StAXSource</code> determines the
     * system identifier of the XML source.</p>
     *
     * <p>An {@link UnsupportedOperationException} is <strong>always</strong>
     * thrown by this method.</p>
     *
     * @param systemId Ignored.
     *
     * @throws UnsupportedOperationException Is <strong>always</strong>
     *   thrown by this method.
     */
    public void setSystemId(final String systemId) {

        throw new UnsupportedOperationException(
                "StAXSource#setSystemId(systemId) cannot set the "
                + "system identifier for a StAXSource");
    }
}
