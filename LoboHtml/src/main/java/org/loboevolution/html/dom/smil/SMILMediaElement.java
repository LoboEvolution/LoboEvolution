/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.smil;

import org.w3c.dom.DOMException;

/**
 * Declares media content.
 *
 * @author utente
 * @version $Id: $Id
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
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
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setType(String type) throws DOMException;

}
