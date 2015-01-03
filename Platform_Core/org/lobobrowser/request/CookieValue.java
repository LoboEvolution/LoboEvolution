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
 * Created on Jun 2, 2005
 */
package org.lobobrowser.request;

import java.io.Serializable;

/**
 * @author J. H. S.
 */
public class CookieValue implements Serializable {
	private final String value;
	private final String path;
	private final Long expirationTime;
	private static final long serialVersionUID = 225784501000400500L;

	/**
	 * 
	 */
	public CookieValue(String value, String path, Long expirationTime) {
		this.value = value;
		this.path = path;
		this.expirationTime = expirationTime;
	}

	public CookieValue(String value, String path) {
		this.value = value;
		this.path = path;
		this.expirationTime = null;
	}

	public String getValue() {
		return this.value;
	}

	public Long getExpires() {
		return this.expirationTime;
	}

	public String getPath() {
		return path;
	}

	public boolean isExpired() {
		Long exp = this.expirationTime;
		return exp == null ? false : exp.longValue() < System
				.currentTimeMillis();
	}

	public String toString() {
		return "CookieValue[value=" + value + ",path=" + path + ",expiration="
				+ expirationTime + "]";
	}
}
