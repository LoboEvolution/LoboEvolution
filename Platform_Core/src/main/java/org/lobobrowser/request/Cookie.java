/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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

import java.io.UnsupportedEncodingException;

/**
 * @author J. H. S.
 */
public class Cookie {
	private final String name;
	private final String value;

	/**
	 * @param name
	 * @param value
	 */
	public Cookie(final String name, final String value) {
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	public String getEncodedName() throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(this.name, "UTF-8");
	}

	public String getEncodedValue() throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(this.value, "UTF-8");
	}

	public String toString() {
		return "Cookie[name=" + this.name + ",value=" + this.value + "]";
	}
}
