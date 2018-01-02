/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.smil;

import org.w3c.dom.DOMException;

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
	 */
	public String getAbstractAttr();

	public void setAbstractAttr(String abstractAttr) throws DOMException;

	/**
	 * See the alt attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getAlt();

	public void setAlt(String alt) throws DOMException;

	/**
	 * See the author attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getAuthor();

	public void setAuthor(String author) throws DOMException;

	/**
	 * See the clipBegin attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getClipBegin();

	public void setClipBegin(String clipBegin) throws DOMException;

	/**
	 * See the clipEnd attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getClipEnd();

	public void setClipEnd(String clipEnd) throws DOMException;

	/**
	 * See the copyright attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getCopyright();

	public void setCopyright(String copyright) throws DOMException;

	/**
	 * See the longdesc attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getLongdesc();

	public void setLongdesc(String longdesc) throws DOMException;

	/**
	 * See the port attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getPort();

	public void setPort(String port) throws DOMException;

	/**
	 * See the readIndex attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getReadIndex();

	public void setReadIndex(String readIndex) throws DOMException;

	/**
	 * See the rtpformat attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getRtpformat();

	public void setRtpformat(String rtpformat) throws DOMException;

	/**
	 * See the src attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getSrc();

	public void setSrc(String src) throws DOMException;

	/**
	 * See the stripRepeat attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getStripRepeat();

	public void setStripRepeat(String stripRepeat) throws DOMException;

	/**
	 * See the title attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getTitle();

	public void setTitle(String title) throws DOMException;

	/**
	 * See the transport attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getTransport();

	public void setTransport(String transport) throws DOMException;

	/**
	 * See the type attribute from .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getType();

	public void setType(String type) throws DOMException;

}
