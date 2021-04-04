/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.info;

import java.io.Serializable;

/**
 * <p>CookieInfo class.</p>
 *
 *
 *
 */
public class CookieInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String comment;

	private String domain;

	private String expires;

	private boolean httpOnly;

	private int maxAge = -1;

	private String name;

	private String path;

	private boolean secure;

	private String value;

	private int version;

	/**
	 * <p>Getter for the field comment.</p>
	 *
	 * @return the comment
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * <p>Getter for the field domain.</p>
	 *
	 * @return the domain
	 */
	public String getDomain() {
		return this.domain;
	}

	/**
	 * <p>Getter for the field expires.</p>
	 *
	 * @return the expires
	 */
	public String getExpires() {
		return this.expires;
	}

	/**
	 * <p>Getter for the field maxAge.</p>
	 *
	 * @return the maxAge
	 */
	public int getMaxAge() {
		return this.maxAge;
	}

	/**
	 * <p>Getter for the field name.</p>
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * <p>Getter for the field path.</p>
	 *
	 * @return the path
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * <p>Getter for the field value.</p>
	 *
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * <p>Getter for the field version.</p>
	 *
	 * @return the version
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * <p>isHttpOnly.</p>
	 *
	 * @return the httpOnly
	 */
	public boolean isHttpOnly() {
		return this.httpOnly;
	}

	/**
	 * <p>isSecure.</p>
	 *
	 * @return the secure
	 */
	public boolean isSecure() {
		return this.secure;
	}

	/**
	 * <p>Setter for the field comment.</p>
	 *
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * <p>Setter for the field domain.</p>
	 *
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
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
	 * <p>Setter for the field httpOnly.</p>
	 *
	 * @param httpOnly the httpOnly to set
	 */
	public void setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
	}

	/**
	 * <p>Setter for the field maxAge.</p>
	 *
	 * @param maxAge the maxAge to set
	 */
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	/**
	 * <p>Setter for the field name.</p>
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Setter for the field path.</p>
	 *
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * <p>Setter for the field secure.</p>
	 *
	 * @param secure the secure to set
	 */
	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	/**
	 * <p>Setter for the field value.</p>
	 *
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * <p>Setter for the field version.</p>
	 *
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
}
