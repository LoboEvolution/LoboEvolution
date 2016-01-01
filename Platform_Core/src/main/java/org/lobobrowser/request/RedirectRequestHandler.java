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
 * Created on May 27, 2005
 */
package org.lobobrowser.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;

import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.util.Urls;

/**
 * The Class RedirectRequestHandler.
 *
 * @author J. H. S.
 */
public class RedirectRequestHandler implements RequestHandler {

    /** The orig handler. */
    private final RequestHandler origHandler;

    /** The latest request url. */
    private final URL latestRequestURL;

    /**
     * Instantiates a new redirect request handler.
     *
     * @param origHandler
     *            the orig handler
     * @param origConnection
     *            the orig connection
     * @throws MalformedURLException
     *             the malformed url exception
     * @throws UnsupportedEncodingException 
     */
    public RedirectRequestHandler(RequestHandler origHandler,
            HttpURLConnection origConnection) throws MalformedURLException, UnsupportedEncodingException {
        this.origHandler = origHandler;
        String location = origConnection.getHeaderField("Location");
        URL origURL = origConnection.getURL();
        if (location == null) {
            throw new IllegalArgumentException(
                    "No Location header in redirect response for "
                            + origConnection.getURL());
        }
        URL finalURL = Urls.createURL(origURL, location);
        String origHost = origURL.getHost();
        String finalHost = finalURL.getHost();
        if (origHost.equals(finalHost)) {
            if (origURL.getProtocol().equalsIgnoreCase(finalURL.getProtocol())) {
                if (origURL.getPort() == finalURL.getPort()) {
                    String origPath = origURL.getFile();
                    String finalPath = finalURL.getFile();
                    if (origPath.equals(finalPath)) {
                        throw new IllegalArgumentException("Redirecting URL '"
                                + origURL + "' and target URL '" + finalURL
                                + "' are equal!");
                    }
                }
            }
        }
        this.latestRequestURL = finalURL;
    }

    /*
     * (non-Javadoc)
     * @see net.sourceforge.xamj.http.RequestHandler#getHostnameVerifier()
     */
    @Override
    public HostnameVerifier getHostnameVerifier() {
        return this.origHandler.getHostnameVerifier();
    }

    /*
     * (non-Javadoc)
     * @see net.sourceforge.xamj.http.RequestHandler#getLatestRequestMethod()
     */
    @Override
    public String getLatestRequestMethod() {
        return "GET";
    }

    /*
     * (non-Javadoc)
     * @see net.sourceforge.xamj.http.RequestHandler#getLatestRequestURL()
     */
    @Override
    public URL getLatestRequestURL() {
        return this.latestRequestURL;
    }

    /*
     * (non-Javadoc)
     * @see net.sourceforge.xamj.http.RequestHandler#getRequest()
     */
    @Override
    public ClientletRequest getRequest() {
        return this.origHandler.getRequest();
    }

    /*
     * (non-Javadoc)
     * @see net.sourceforge.xamj.http.RequestHandler#handleException(Exception )
     */
    @Override
    public boolean handleException(ClientletResponse response,
            Throwable exception) throws ClientletException {
        return this.origHandler.handleException(response, exception);
    }

    /*
     * (non-Javadoc)
     * @see net.sourceforge.xamj.http.RequestHandler#handleProgress(URL, int, int)
     */
    @Override
    public void handleProgress(ProgressType progressType, URL url,
            String method, int value, int max) {
        this.origHandler.handleProgress(progressType, url, method, value, max);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourceforge.xamj.http.RequestHandler#processResponse(org.xamjwg.clientlet
     * .ClientletResponse)
     */
    @Override
    public void processResponse(ClientletResponse response)
            throws ClientletException, IOException {
        this.origHandler.processResponse(response);
    }

    /** The cancelled. */
    private volatile boolean cancelled;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#cancel()
     */
    @Override
    public void cancel() {
        this.cancelled = true;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#isCancelled()
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#getRequestType()
     */
    @Override
    public RequestType getRequestType() {
        return this.origHandler.getRequestType();
    }
}
