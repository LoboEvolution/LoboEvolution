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
/*
 * Created on Jun 2, 2005
 */
package org.loboevolution.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The Class Cookie.
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class Cookie extends NameValuePair {
	
	/** The comment. */
	private String comment;
	
	/** The domain. */
	private String domain;
	
	/** The expires. */
	private String expires;
	
	/** The max age. */
	private int maxAge = -1;
	
	/** The path. */
	private String path;
	
	/** The secure. */
	private boolean secure;
	
	/** The version. */
	private int version;
	
	/** The version. */
	private boolean httpOnly;

	/**
	 * <p>Constructor for Cookie.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	public Cookie(String name, String value) {
		super(name, value);
	}
	
	/**
	 * Gets the encoded name.
	 *
	 * @return the encoded name
	 * @throws java.io.UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public String getEncodedName() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.name, "UTF-8");
	}

	/**
	 * Gets the encoded value.
	 *
	 * @return the encoded value
	 * @throws java.io.UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public String getEncodedValue() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.value, "UTF-8");
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment
	 *            the new comment
	 */
	public void setComment(String comment) {
		String old = getComment();
		this.comment = comment;
		firePropertyChange("comment", old, getComment());
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Gets the expires.
	 *
	 * @return the expires
	 */
	public String getExpires() {
		return expires;
	}

	/**
	 * <p>Setter for the field expires.</p>
	 *
	 * @param expires the expires to set
	 */
	public void setExpires(String expires) {
		this.expires = expires;
	}



	/**
	 * Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(String domain) {
		String old = getDomain();
		this.domain = domain;
		firePropertyChange("domain", old, getDomain());
	}

	/**
	 * Gets the domain.
	 *
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the max age.
	 *
	 * @param age
	 *            the new max age
	 */
	public void setMaxAge(int age) {
		long old = getMaxAge();
		this.maxAge = age;
		firePropertyChange("maxAge", old, getMaxAge());
	}

	/**
	 * Gets the max age.
	 *
	 * @return the max age
	 */
	public int getMaxAge() {
		return maxAge;
	}

	/**
	 * Sets the path.
	 *
	 * @param path
	 *            the new path
	 */
	public void setPath(String path) {
		String old = getPath();
		this.path = path;
		firePropertyChange("path", old, getPath());
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the secure.
	 *
	 * @param secure
	 *            the new secure
	 */
	public void setSecure(boolean secure) {
		boolean old = isSecure();
		this.secure = secure;
		firePropertyChange("secure", old, isSecure());
	}

	/**
	 * Checks if is secure.
	 *
	 * @return the secure
	 */
	public boolean isSecure() {
		return secure;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(int version) {
		int old = getVersion();
		this.version = version;
		firePropertyChange("version", old, getVersion());
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * <p>isHttpOnly.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isHttpOnly() {
		return httpOnly;
	}

	/**
	 * <p>Setter for the field httpOnly.</p>
	 *
	 * @param httpOnly a boolean.
	 */
	public void setHttpOnly(boolean httpOnly) {
		boolean old = isHttpOnly();
		this.httpOnly = httpOnly;
		firePropertyChange("httpOnly", old, isHttpOnly());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.util.NameValuePair#toString()
	 */
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Cookie [" + getName() + "=" + getValue() + ", " + "Comment=" + getComment() + ", " + "Domain="
				+ getDomain() + ", " + "Max-Age=" + getMaxAge() + ", " + "Path=" + getPath() + ", " + "Secure="
				+ isSecure() + ", " + "Version=" + getVersion() + "]";
	}
}
