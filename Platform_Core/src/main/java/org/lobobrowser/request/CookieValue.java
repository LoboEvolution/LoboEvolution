/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

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
 * The Class CookieValue.
 *
 * @author J. H. S.
 */
public class CookieValue implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 225784501000400500L;

    /** The value. */
    private String value;

    /** The path. */
    private String path;

    /** The domain. */
    private String domain;

    /** The expiration time. */
    private Long expirationTime;

    /**
     * Instantiates a new cookie value.
     *
     * @param value
     *            the value
     * @param path
     *            the path
     * @param domain
     *            the domain
     * @param expirationTime
     *            the expiration time
     */
    public CookieValue(String value, String path, String domain,
            Long expirationTime) {
        this.value = value;
        this.path = path;
        this.expirationTime = expirationTime;
        this.domain = domain;
    }

    /**
     * Instantiates a new cookie value.
     *
     * @param value
     *            the value
     * @param path
     *            the path
     */
    public CookieValue(String value, String path) {
        this.value = value;
        this.path = path;
        this.expirationTime = null;
    }

    /** Gets the value.
	 *
	 * @return the value
	 */
    public String getValue() {
        return this.value;
    }

    /** Gets the expires.
	 *
	 * @return the expires
	 */
    public Long getExpires() {
        return this.expirationTime;
    }

    /** Gets the path.
	 *
	 * @return the path
	 */
    public String getPath() {
        return path;
    }

    /** Gets the domain.
	 *
	 * @return the domain
	 */
    public String getDomain() {
        return domain;
    }

    /** Checks if is expired.
	 *
	 * @return true, if is expired
	 */
    public boolean isExpired() {
        Long exp = this.expirationTime;
        return exp == null ? false : exp.longValue() < System
                .currentTimeMillis();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CookieValue[value=" + value + ",path=" + path + ",expiration="
                + expirationTime + "]";
    }
}
