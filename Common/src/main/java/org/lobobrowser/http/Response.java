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
package org.lobobrowser.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 * Represents a Response from an http {@link Request}. Instances of this class
 * are created as a result of executing a <code>Request</code> in a
 * {@link Session}. They are not created manually.
 * </p>
 *
 * <p>
 * <code>Response</code>s can access all of the http {@link Header}s that were
 * returned with the response, as well as the status code, status message, and
 * response body. In addition, the url that this response originated from is
 * available. This is useful when constructing further requests based on
 * relative url paths.
 * </p>
 *
 * <p>
 * As with <code>Request</code>, the body of the <code>Response</code> is
 * treated specially. Since the response may be very large (like, say,
 * downloading an operating system) it is critical that the responses not be
 * cached. As a result, once the response body has been read, any futher calls
 * to getResponseBody <em>may</em> result in an exception.
 * </p>
 *
 * @author rbair
 */
public class Response {
	
	 /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(Response.class);
    		
    /**
     * The encoding for the body to be used when constructing a String from the
     * bytes. This defaults to UTF-8, but can be set by specifying the encoding
     * on a Request.
     */
    private String charset;
    
    /** The headers. */
    private Set<Header> headers;
    
    /** The status code. */
    private StatusCode statusCode;
    
    /** The status text. */
    private String statusText;
    // TODO I'm still dealing with this as a String. I originally used an
    // InputStream,
    // but ran into problems because HttpClient was automatically closing that
    // stream
    // on me. Not sure how to do this, skipping it for now. Most likely, a
    // future
    // implementation will cache to disk. This requires security priviledges,
    // however,
    /** The response body. */
    // and thus is somewhat problematic.
    private byte[] responseBody;
    
    /** The url. */
    private String url;

    /**
     * Creates a new instance of Response. Response is an immutable object,
     * hence this large constructor.
     */
    Response(StatusCode statusCode, String statusText, byte[] responseBody,
            String charset, Set<Header> headers, String baseUrl) {
        if (statusCode == null) {
            throw new NullPointerException("statusCode cannot be null");
        }
        
        if (responseBody == null) {
            responseBody = new byte[0];
        }
        
        
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.responseBody = responseBody;
        this.charset = charset == null ? "ISO-8859-1" : charset;
        this.url = baseUrl;
        this.headers = new HashSet<Header>();
        if (headers != null) {
            for (Header h : headers) {
                if (h == null) {
                    throw new IllegalArgumentException(
                            "There was a null header in the results.");
                }
                this.headers.add(h);
            }
        }
    }

    /**
     * Returns the Header with the given name, or null if there is no such
     * header. Comparisons with header names are done in a case insensitive
     * manner.
     *
     * @param name
     *            the name to look for. This must not be null.
     * @return the Header with the given name.
     */
    public Header getHeader(String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        for (Header h : headers) {
            if (name.equalsIgnoreCase(h.getName())) {
                return h;
            }
        }
        return null;
    }

    /** Gets the headers.
	 *
	 * @return the headers
	 */
    public Header[] getHeaders() {
        return headers.toArray(new Header[0]);
    }

    /** Gets the last modified.
	 *
	 * @return the last modified
	 */
    public Date getLastModified() {
        Header h = getHeader("Last-Modified");
        if (h == null) {
            return null;
        }
        try {
            String value = h.getValue();
            Long longValue = Long.parseLong(value);
            return new Date(longValue);
        } catch (Exception e) {
            // silently fail. Maybe should log using FINER?
            return null;
        }
    }

    /** Gets the status code.
	 *
	 * @return the status code
	 */
    public StatusCode getStatusCode() {
        return statusCode;
    }

    /** Gets the status text.
	 *
	 * @return the status text
	 */
    public String getStatusText() {
        return statusText;
    }

    /** Gets the body as stream.
	 *
	 * @return the body as stream
	 */
    public InputStream getBodyAsStream() {
        return new ByteArrayInputStream(getBodyAsBytes());
    }

    /** Gets the body as reader.
	 *
	 * @return the body as reader
	 */
    public Reader getBodyAsReader() {
        if (responseBody == null) {
            return new StringReader("");
        } else {
            return new StringReader(getBody());
        }
    }

    /** Gets the body as bytes.
	 *
	 * @return the body as bytes
	 */
    public byte[] getBodyAsBytes() {
        return responseBody == null ? new byte[0] : responseBody;
    }

    /** Gets the body.
	 *
	 * @return the body
	 */
    public String getBody() {
        try {
            return responseBody == null ? ""
                    : new String(responseBody, charset);
        } catch (UnsupportedEncodingException ex) {
        	logger.log(Level.ERROR, ex);
            return responseBody == null ? "" : new String(responseBody,StandardCharsets.UTF_8);
        }
    }

    /** Gets the base url.
	 *
	 * @return the base url
	 */
    public String getBaseUrl() {
        return url;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  ").append(statusCode).append("\n");
        for (Header h : getHeaders()) {
            buffer.append("  ").append(h.getName()).append(": ")
                    .append(h.getValue());
            buffer.append("\n");
        }
        buffer.append(getBody());
        return buffer.toString();
    }
}
