/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
 * Created on Jun 2, 2005
 */
package org.loboevolution.net;

import lombok.Getter;
import lombok.Setter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * The Class Cookie.
 */
@Getter
public class Cookie extends NameValuePair {
	
	/** The comment. */
	private String comment;
	
	/** The domain. */
	private String domain;
	
	/** The expires. */
	@Setter
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
	public Cookie(final String name, final String value) {
		super(name, value);
	}
	
	/**
	 * Gets the encoded name.
	 *
	 * @return the encoded name
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public String getEncodedName() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.name, StandardCharsets.UTF_8);
	}

	/**
	 * Gets the encoded value.
	 *
	 * @return the encoded value
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public String getEncodedValue() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.value, StandardCharsets.UTF_8);
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment
	 *            the new comment
	 */
	public void setComment(final String comment) {
		final String old = getComment();
		this.comment = comment;
		firePropertyChange("comment", old, getComment());
	}


	/**
	 * Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(final String domain) {
		final String old = getDomain();
		this.domain = domain;
		firePropertyChange("domain", old, getDomain());
	}


	/**
	 * Sets the max age.
	 *
	 * @param age
	 *            the new max age
	 */
	public void setMaxAge(final int age) {
		final long old = getMaxAge();
		this.maxAge = age;
		firePropertyChange("maxAge", old, getMaxAge());
	}

	/**
	 * Sets the path.
	 *
	 * @param path
	 *            the new path
	 */
	public void setPath(final String path) {
		final String old = getPath();
		this.path = path;
		firePropertyChange("path", old, getPath());
	}

	/**
	 * Sets the secure.
	 *
	 * @param secure
	 *            the new secure
	 */
	public void setSecure(final boolean secure) {
		final boolean old = isSecure();
		this.secure = secure;
		firePropertyChange("secure", old, isSecure());
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(final int version) {
		final int old = getVersion();
		this.version = version;
		firePropertyChange("version", old, getVersion());
	}

	/**
	 * <p>Setter for the field httpOnly.</p>
	 *
	 * @param httpOnly a boolean.
	 */
	public void setHttpOnly(final boolean httpOnly) {
		final boolean old = isHttpOnly();
		this.httpOnly = httpOnly;
		firePropertyChange("httpOnly", old, isHttpOnly());
	}
}
