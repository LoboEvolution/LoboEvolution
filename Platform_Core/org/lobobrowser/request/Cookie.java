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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.lobobrowser.util.NameValuePair;

/**
 * @author J. H. S.
 */
public class Cookie extends NameValuePair {
	private String comment;
	private String domain;
	private long expires;
	private int maxAge = -1;
	private String path;
	private boolean secure;
	private int version;
	
	public Cookie(){
	}
	
	public Cookie(String name,String value, String path, String domain, long expires){
		super(name,value);
		this.path = path;
		this.domain = domain;
		this.expires = expires;
	}

	public String getEncodedName() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.name, "UTF-8");
	}

	public String getEncodedValue() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.value, "UTF-8");
	}
	
	public void setComment(String comment) {
		String old = getComment();
		this.comment = comment;
		firePropertyChange("comment", old, getComment());
	}

	public String getComment() {
		return comment;
	}
	
	public Long getExpires() {
		return expires;
	}

	public void setDomain(String domain) {
		String old = getDomain();
		this.domain = domain;
		firePropertyChange("domain", old, getDomain());
	}

	public String getDomain() {
		return domain;
	}

	public void setMaxAge(int age) {
		long old = getMaxAge();
		this.maxAge = age;
		firePropertyChange("maxAge", old, getMaxAge());
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setPath(String path) {
		String old = getPath();
		this.path = path;
		firePropertyChange("path", old, getPath());
	}

	public String getPath() {
		return path;
	}

	public void setSecure(boolean secure) {
		boolean old = isSecure();
		this.secure = secure;
		firePropertyChange("secure", old, isSecure());
	}

	public boolean isSecure() {
		return secure;
	}

	public void setVersion(int version) {
		int old = getVersion();
		this.version = version;
		firePropertyChange("version", old, getVersion());
	}

	public int getVersion() {
		return version;
	}

	public String toString() {
		return "Cookie [" + getName() + "=" + getValue() + ", " + "Comment="
				+ getComment() + ", " + "Domain=" + getDomain() + ", "
				+ "Max-Age=" + getMaxAge() + ", " + "Path=" + getPath() + ", "
				+ "Secure=" + isSecure() + ", " + "Version=" + getVersion()
				+ "]";
	}
}
