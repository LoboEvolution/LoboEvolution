/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
/*
 * Created on Jun 2, 2005
 */
package org.lobobrowser.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The Class Cookie.
 *
 * @author J. H. S.
 */
public class Cookie extends NameValuePair {
    /** The comment. */
    private String comment;
    /** The domain. */
    private String domain;
    /** The expires. */
    private long expires;
    /** The max age. */
    private int maxAge = -1;
    /** The path. */
    private String path;
    /** The secure. */
    private boolean secure;
    /** The version. */
    private int version;
    
    /**
     * Instantiates a new cookie.
     */
    public Cookie() {
    }
    
    /**
     * Instantiates a new cookie.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     * @param path
     *            the path
     * @param domain
     *            the domain
     * @param expires
     *            the expires
     */
    public Cookie(String name, String value, String path, String domain,
            long expires) {
        super(name, value);
        this.path = path;
        this.domain = domain;
        this.expires = expires;
    }
    
    /** Gets the encoded name.
	 *
	 * @return the encoded name
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
    public String getEncodedName() throws UnsupportedEncodingException {
        return URLEncoder.encode(this.name, "UTF-8");
    }
    
    /** Gets the encoded value.
	 *
	 * @return the encoded value
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
    public String getEncodedValue() throws UnsupportedEncodingException {
        return URLEncoder.encode(this.value, "UTF-8");
    }
    
    /** Sets the comment.
	 *
	 * @param comment
	 *            the new comment
	 */
    public void setComment(String comment) {
        String old = getComment();
        this.comment = comment;
        firePropertyChange("comment", old, getComment());
    }
    
    /** Gets the comment.
	 *
	 * @return the comment
	 */
    public String getComment() {
        return comment;
    }
    
    /** Gets the expires.
	 *
	 * @return the expires
	 */
    public Long getExpires() {
        return expires;
    }
    
    /** Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
    public void setDomain(String domain) {
        String old = getDomain();
        this.domain = domain;
        firePropertyChange("domain", old, getDomain());
    }
    
    /** Gets the domain.
	 *
	 * @return the domain
	 */
    public String getDomain() {
        return domain;
    }
    
    /** Sets the max age.
	 *
	 * @param age
	 *            the new max age
	 */
    public void setMaxAge(int age) {
        long old = getMaxAge();
        this.maxAge = age;
        firePropertyChange("maxAge", old, getMaxAge());
    }
    
    /** Gets the max age.
	 *
	 * @return the max age
	 */
    public int getMaxAge() {
        return maxAge;
    }
    
    /** Sets the path.
	 *
	 * @param path
	 *            the new path
	 */
    public void setPath(String path) {
        String old = getPath();
        this.path = path;
        firePropertyChange("path", old, getPath());
    }
    
    /** Gets the path.
	 *
	 * @return the path
	 */
    public String getPath() {
        return path;
    }
    
    /** Sets the secure.
	 *
	 * @param secure
	 *            the new secure
	 */
    public void setSecure(boolean secure) {
        boolean old = isSecure();
        this.secure = secure;
        firePropertyChange("secure", old, isSecure());
    }
    
    /** Checks if is secure.
	 *
	 * @return the secure
	 */
    public boolean isSecure() {
        return secure;
    }
    
    /** Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
    public void setVersion(int version) {
        int old = getVersion();
        this.version = version;
        firePropertyChange("version", old, getVersion());
    }
    
    /** Gets the version.
	 *
	 * @return the version
	 */
    public int getVersion() {
        return version;
    }
    
    /*
     * (non-Javadoc)
     * @see org.lobobrowser.util.NameValuePair#toString()
     */
    @Override
    public String toString() {
        return "Cookie [" + getName() + "=" + getValue() + ", " + "Comment="
                + getComment() + ", " + "Domain=" + getDomain() + ", "
                + "Max-Age=" + getMaxAge() + ", " + "Path=" + getPath() + ", "
                + "Secure=" + isSecure() + ", " + "Version=" + getVersion()
                + "]";
    }
}
