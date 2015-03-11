/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on May 21, 2005
 */
package org.lobobrowser.context;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.lobobrowser.clientlet.ContentBuffer;
import org.lobobrowser.util.ID;
import org.lobobrowser.util.WeakValueHashMap;


/**
 * The Class VolatileContentImpl.
 *
 * @author J. H. S.
 */
public class VolatileContentImpl implements ContentBuffer {
	
	/** The Constant volatileMap. */
	private static final Map<Long, ContentBuffer> volatileMap = new WeakValueHashMap();
	
	/** The id. */
	private final long id;
	
	/** The content type. */
	private final String contentType;
	
	/** The bytes. */
	private final byte[] bytes;

	/**
	 * Instantiates a new volatile content impl.
	 *
	 * @param contentType the content type
	 * @param bytes the bytes
	 */
	public VolatileContentImpl(String contentType, byte[] bytes) {
		super();
		this.id = ID.generateLong();
		this.contentType = contentType;
		this.bytes = bytes;
		synchronized (volatileMap) {
			volatileMap.put(new Long(this.id), this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.VolatileContent#getURL()
	 */
	public URL getURL() {
		try {
			return new URL("vc:" + this.id);
		} catch (MalformedURLException mfu) {
			throw new IllegalStateException(mfu);
		}
	}

	/**
	 * Gets the volatile content.
	 *
	 * @param id the id
	 * @return the volatile content
	 */
	public static VolatileContentImpl getVolatileContent(long id) {
		synchronized (volatileMap) {
			return (VolatileContentImpl) volatileMap.get(new Long(id));
		}
	}

	/**
	 * Gets the bytes.
	 *
	 * @return Returns the bytes.
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * Gets the content type.
	 *
	 * @return Returns the contentType.
	 */
	public String getContentType() {
		return contentType;
	}
}
