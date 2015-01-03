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
 * Created on Jun 12, 2005
 */
package org.lobobrowser.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.store.CacheManager;
import org.lobobrowser.store.ClassLoaderObjectInputStream;
import org.lobobrowser.util.Urls;

public class CacheInfo {
	private static final Logger logger = Logger.getLogger(CacheInfo.class
			.getName());
	static final String HEADER_REQUEST_TIME = "X-Request-Time";
	private final URL url;

	private final MemoryCacheEntry memoryEntry;
	private final byte[] persistentContent;

	private URLConnection connection;

	/**
	 * 
	 */
	public CacheInfo(MemoryCacheEntry memEntry, byte[] persContent, URL url) {
		super();
		this.persistentContent = persContent;
		this.url = url;
		this.memoryEntry = memEntry;
	}

	/**
	 * This method should only be called if the connection is later going to be
	 * closed.
	 */
	public final java.net.URLConnection getURLConnection() {
		if (this.connection == null) {
			MemoryCacheEntry memEntry = this.memoryEntry;
			if (memEntry != null) {
				this.connection = new MemoryURLConnection(this.url, memEntry);
			} else {
				byte[] content = this.persistentContent;
				if (content == null) {
					throw new IllegalStateException(
							"Memory entry and persistent content unavailable.");
				}
				this.connection = new FileWithHeadersURLConnection(this.url,
						content);
			}
		}
		return this.connection;
	}

	/**
	 * This method should be called when the CacheInfo instance is no longer
	 * needed in order to release resources.
	 */
	public final void dispose() {
		URLConnection connection = this.connection;
		if (connection instanceof FileWithHeadersURLConnection) {
			((FileWithHeadersURLConnection) connection).disconnect();
		}
	}

	public final boolean isCacheConnection(URLConnection connection) {
		return connection == this.getURLConnection();
	}

	public final String getDateAsText() {
		return this.getURLConnection().getHeaderField("date");
	}

	/**
	 * Adds the request time of the cached document to the given offset.
	 */
	public final Long getExpiresGivenOffset(long offsetSeconds) {
		MemoryCacheEntry entry = this.memoryEntry;
		if (entry != null) {
			return entry.requestTime + (offsetSeconds * 1000);
		} else {
			String rtText = this.getURLConnection().getHeaderField(
					HEADER_REQUEST_TIME);
			if (rtText == null) {
				return null;
			}
			long rt = Long.parseLong(rtText);
			return rt + (offsetSeconds * 1000);
		}
	}

	/**
	 * Gets the timestamp when the cache entry should expire and must be
	 * revalidated. If <code>null</code>, the browser can use a default. When
	 * the entry must be revalidated, this method returns zero.
	 */
	public final Long getExpires() {
		MemoryCacheEntry entry = this.memoryEntry;
		if (entry != null) {
			return entry.expiration;
		} else {
			URLConnection connection = this.getURLConnection();
			String requestTimeText = connection
					.getHeaderField(HEADER_REQUEST_TIME);
			if (requestTimeText == null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("getExpires(): Cached content does not have "
							+ HEADER_REQUEST_TIME + " header: " + this.url
							+ ".");
				}
				return new Long(0);
			}
			long requestTime = Long.parseLong(requestTimeText);
			return Urls.getExpiration(connection, requestTime);
		}
	}

	public long getRequestTime() {
		MemoryCacheEntry entry = this.memoryEntry;
		if (entry != null) {
			return entry.requestTime;
		} else {
			URLConnection connection = this.getURLConnection();
			String requestTimeText = connection
					.getHeaderField(HEADER_REQUEST_TIME);
			if (requestTimeText == null) {
				return 0;
			}
			return Long.parseLong(requestTimeText);
		}
	}

	public boolean hasTransientEntry() {
		return this.memoryEntry != null;
	}

	public Object getTransientObject() {
		MemoryCacheEntry memEntry = this.memoryEntry;
		return memEntry != null ? memEntry.altObject : null;
	}

	public int getTransientObjectSize() {
		MemoryCacheEntry memEntry = this.memoryEntry;
		return memEntry != null ? memEntry.altObjectSize : 0;
	}

	public Object getPersistentObject(ClassLoader classLoader) {
		try {
			byte[] content = CacheManager.getInstance().getPersistent(this.url,
					true);
			if (content == null) {
				return null;
			}
			InputStream in = new ByteArrayInputStream(content);
			try {
				ObjectInputStream oin = new ClassLoaderObjectInputStream(in,
						classLoader);
				return oin.readObject();
			} finally {
				in.close();
			}
		} catch (IOException ioe) {
			logger.log(
					Level.WARNING,
					"getPersistentObject(): Unable to load persistent cached object.",
					ioe);
			return null;
		} catch (ClassNotFoundException ioe) {
			logger.log(
					Level.WARNING,
					"getPersistentObject(): Failed to load persistent cached object apparently due to versioning issue.",
					ioe);
			return null;
		}
	}

	public void delete() {
		CacheManager cm = CacheManager.getInstance();
		cm.removeTransient(this.url);
		try {
			cm.removePersistent(this.url, false);
			cm.removePersistent(this.url, true);
		} catch (IOException ioe) {
			logger.log(Level.WARNING, "delete()", ioe);
		}
	}

	public byte[] getPersistentContent() {
		return persistentContent;
	}
}
