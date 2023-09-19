/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.dom.smil;

import org.htmlunit.cssparser.dom.DOMException;

/**
 * Declares media content.
 */
public interface SMILMediaElement extends ElementTime, SMILElement {
    /**
     * See the abstract attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getAbstractAttr();

    /**
     * <p>setAbstractAttr.</p>
     *
     * @param abstractAttr a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setAbstractAttr(String abstractAttr) throws DOMException;

    /**
     * See the alt attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getAlt();

    /**
     * <p>setAlt.</p>
     *
     * @param alt a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setAlt(String alt) throws DOMException;

    /**
     * See the author attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getAuthor();

    /**
     * <p>setAuthor.</p>
     *
     * @param author a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setAuthor(String author) throws DOMException;

    /**
     * See the clipBegin attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getClipBegin();

    /**
     * <p>setClipBegin.</p>
     *
     * @param clipBegin a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setClipBegin(String clipBegin) throws DOMException;

    /**
     * See the clipEnd attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getClipEnd();

    /**
     * <p>setClipEnd.</p>
     *
     * @param clipEnd a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setClipEnd(String clipEnd) throws DOMException;

    /**
     * See the copyright attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getCopyright();

    /**
     * <p>setCopyright.</p>
     *
     * @param copyright a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setCopyright(String copyright) throws DOMException;

    /**
     * See the longdesc attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getLongdesc();

    /**
     * <p>setLongdesc.</p>
     *
     * @param longdesc a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setLongdesc(String longdesc) throws DOMException;

    /**
     * See the port attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getPort();

    /**
     * <p>setPort.</p>
     *
     * @param port a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setPort(String port) throws DOMException;

    /**
     * See the readIndex attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getReadIndex();

    /**
     * <p>setReadIndex.</p>
     *
     * @param readIndex a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setReadIndex(String readIndex) throws DOMException;

    /**
     * See the rtpformat attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getRtpformat();

    /**
     * <p>setRtpformat.</p>
     *
     * @param rtpformat a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setRtpformat(String rtpformat) throws DOMException;

    /**
     * See the src attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getSrc();

    /**
     * <p>setSrc.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setSrc(String src) throws DOMException;

    /**
     * See the stripRepeat attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getStripRepeat();

    /**
     * <p>setStripRepeat.</p>
     *
     * @param stripRepeat a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setStripRepeat(String stripRepeat) throws DOMException;

    /**
     * See the title attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getTitle();

    /**
     * <p>setTitle.</p>
     *
     * @param title a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setTitle(String title) throws DOMException;

    /**
     * See the transport attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getTransport();

    /**
     * <p>setTransport.</p>
     *
     * @param transport a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setTransport(String transport) throws DOMException;

    /**
     * See the type attribute from .
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getType();

    /**
     * <p>setType.</p>
     *
     * @param type a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setType(String type) throws DOMException;

}
