/*
 * $Id: Response.java 290 2008-04-08 21:00:46Z rbair $ Copyright 2004 Sun
 * Microsystems, Inc., 4150 Network Circle, Santa Clara, California 95054,
 * U.S.A. All rights reserved. This library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version. This library is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.lobobrowser.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    /**
     * The encoding for the body to be used when constructing a String from the
     * bytes. This defaults to UTF-8, but can be set by specifying the encoding
     * on a Request.
     */
    private String charset;
    private Set<Header> headers;
    private StatusCode statusCode;
    private String statusText;
    // TODO I'm still dealing with this as a String. I originally used an
    // InputStream,
    // but ran into problems because HttpClient was automatically closing that
    // stream
    // on me. Not sure how to do this, skipping it for now. Most likely, a
    // future
    // implementation will cache to disk. This requires security priviledges,
    // however,
    // and thus is somewhat problematic.
    private byte[] responseBody;
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
        if (baseUrl != null) {
            // TODO assure that there are no parameters in this URL. If there
            // are,
            // remove them
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

    /**
     * Gets an array of all the Headers for this Request. This array will never
     * be null. Ordering of items is not guaranteed.
     *
     * @return the array of Headers for this request
     */
    public Header[] getHeaders() {
        return headers.toArray(new Header[0]);
    }

    /**
     * Gets the Date specified in the Last-Modified header, if any. This is a
     * special convenience method for reading the Last-Modified value. If the
     * value associated with Last-Modified cannot be properly decoded, or if
     * Last-Modified is not specified, then null is returned.
     *
     * @return
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

    /**
     * Gets the status code. See {@link StatusCode} for the definitive list of
     * status codes.
     *
     * @return a code indicating the status of the response. This is never null.
     */
    public StatusCode getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the status text.
     *
     * @return the status text
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * Gets an <code>InputStream</code> from which the body of the response may
     * be read.
     *
     * @returns the body as an <code>InputStream</code>. This will never be
     *          null.
     */
    public InputStream getBodyAsStream() {
        return new ByteArrayInputStream(getBodyAsBytes());
    }

    /**
     * Gets a <code>Reader</code> from which the body of the response may be
     * read.
     *
     * @returns the body as a <code>Reader</code>. This will never be null.
     */
    public Reader getBodyAsReader() {
        if (responseBody == null) {
            return new StringReader("");
        } else {
            return new StringReader(getBody());
        }
    }

    /**
     * Gets a byte array representing the body of the response.
     *
     * @returns the body as a byte array. This will never be null.
     */
    public byte[] getBodyAsBytes() {
        return responseBody == null ? new byte[0] : responseBody;
    }

    /**
     * Gets the response body as a String.
     *
     * @returns the body as a String. This will never be null.
     */
    public String getBody() {
        try {
            return responseBody == null ? ""
                    : new String(responseBody, charset);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return responseBody == null ? "" : new String(responseBody);
        }
    }

    /**
     * Gets the url that was used to produce this <code>Response</code>. This
     * url will not contain a query string (that is, no parameters).
     */
    public String getBaseUrl() {
        return url;
    }

    /**
     * @inheritDoc
     */
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
