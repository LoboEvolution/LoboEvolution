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
package org.lobobrowser.request;

import java.net.URL;

import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.http.Header;
import org.lobobrowser.ua.ParameterInfo;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.ua.UserAgent;

/**
 * The Class ClientletRequestImpl.
 */
public class ClientletRequestImpl implements ClientletRequest {

    /** The method. */
    private final String method;

    /** The url. */
    private final URL url;

    /** The param info. */
    private final ParameterInfo paramInfo;

    /** The extra headers. */
    private final Header[] extraHeaders;

    /** The for new window. */
    private final boolean forNewWindow;

    /** The referrer. */
    private final String referrer;

    /** The alt post data. */
    private final String altPostData;

    /** The request type. */
    private final RequestType requestType;

    /**
     * Instantiates a new clientlet request impl.
     *
     * @param forNewWindow
     *            the for new window
     * @param url
     *            the url
     * @param method
     *            the method
     * @param paramInfo
     *            the param info
     * @param extraHeaders
     *            the extra headers
     * @param referrer
     *            the referrer
     * @param altPostData
     *            the alt post data
     * @param requestType
     *            the request type
     */
    public ClientletRequestImpl(boolean forNewWindow, URL url, String method,
            ParameterInfo paramInfo, Header[] extraHeaders, String referrer,
            String altPostData, RequestType requestType) {
        this.method = method;
        this.url = url;
        this.paramInfo = paramInfo;
        this.extraHeaders = extraHeaders;
        this.forNewWindow = forNewWindow;
        this.referrer = referrer;
        this.altPostData = altPostData;
        this.requestType = requestType;
    }

    /**
     * Instantiates a new clientlet request impl.
     *
     * @param forNewWindow
     *            the for new window
     * @param url
     *            the url
     * @param method
     *            the method
     * @param paramInfo
     *            the param info
     * @param requestType
     *            the request type
     */
    public ClientletRequestImpl(boolean forNewWindow, URL url, String method,
            ParameterInfo paramInfo, RequestType requestType) {
        this(forNewWindow, url, method, paramInfo, null, null, null,
                requestType);
    }

    /**
     * Instantiates a new clientlet request impl.
     *
     * @param url
     *            the url
     * @param requestType
     *            the request type
     */
    public ClientletRequestImpl(URL url, RequestType requestType) {
        this(false, url, "GET", null, null, null, null, requestType);
    }

    /**
     * Instantiates a new clientlet request impl.
     *
     * @param forNewWindow
     *            the for new window
     * @param url
     *            the url
     * @param requestType
     *            the request type
     */
    public ClientletRequestImpl(boolean forNewWindow, URL url,
            RequestType requestType) {
        this(forNewWindow, url, "GET", null, null, null, null, requestType);
    }

    /**
     * Instantiates a new clientlet request impl.
     *
     * @param url
     *            the url
     * @param method
     *            the method
     * @param altPostData
     *            the alt post data
     * @param requestType
     *            the request type
     */
    public ClientletRequestImpl(URL url, String method, String altPostData,
            RequestType requestType) {
        this(false, url, method, null, null, null, altPostData, requestType);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#getExtraHeaders()
     */
    @Override
    public Header[] getExtraHeaders() {
        return this.extraHeaders;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#getMethod()
     */
    @Override
    public String getMethod() {
        return this.method;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#getParameterInfo()
     */
    @Override
    public ParameterInfo getParameterInfo() {
        return this.paramInfo;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#getRequestURL()
     */
    @Override
    public URL getRequestURL() {
        return this.url;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#getUserAgent()
     */
    @Override
    public UserAgent getUserAgent() {
        return UserAgentImpl.getInstance();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#isGetRequest()
     */
    @Override
    public boolean isGetRequest() {
        return "GET".equalsIgnoreCase(method);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#isNewWindowRequest()
     */
    @Override
    public boolean isNewWindowRequest() {
        return this.forNewWindow;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#isPostRequest()
     */
    @Override
    public boolean isPostRequest() {
        return "POST".equalsIgnoreCase(this.method);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#getReferrer()
     */
    @Override
    public String getReferrer() {
        return this.referrer;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#getAltPostData()
     */
    @Override
    public String getAltPostData() {
        return this.altPostData;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ClientletRequest#getRequestType()
     */
    @Override
    public RequestType getRequestType() {
        return this.requestType;
    }
}
