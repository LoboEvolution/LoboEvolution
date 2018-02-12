/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.request;

import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.clientlet.ClientletException;
import org.loboevolution.clientlet.ClientletRequest;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.ua.ProgressType;
import org.loboevolution.ua.RequestType;

/**
 * Request handler used by request().
 *
 * @author J. H. S.
 */
public abstract class SimpleRequestHandler implements RequestHandler {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SimpleRequestHandler.class);

	/** The request. */
	private final ClientletRequest request;

	/** The request type. */
	private final RequestType requestType;
	
	/** The cancelled. */
	private volatile boolean cancelled;

	/**
	 * Instantiates a new simple request handler.
	 *
	 * @param forNewWindow
	 *            the for new window
	 * @param url
	 *            the url
	 * @param requestType
	 *            the request type
	 */
	public SimpleRequestHandler(boolean forNewWindow, URL url, RequestType requestType) {
		this.requestType = requestType;
		this.request = new ClientletRequestImpl(forNewWindow, url, requestType);
	}

	/**
	 * Instantiates a new simple request handler.
	 *
	 * @param url
	 *            the url
	 * @param requestType
	 *            the request type
	 */
	public SimpleRequestHandler(URL url, RequestType requestType) {
		this.requestType = requestType;
		this.request = new ClientletRequestImpl(url, requestType);
	}

	/**
	 * Instantiates a new simple request handler.
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
	public SimpleRequestHandler(URL url, String method, String altPostData, RequestType requestType) {
		this.requestType = requestType;
		this.request = new ClientletRequestImpl(url, method, altPostData, requestType);
	}

	/**
	 * Checks if is new navigation entry.
	 *
	 * @return true, if is new navigation entry
	 */
	public boolean isNewNavigationEntry() {
		return false;
	}

	/**
	 * Gets the cache file suffix.
	 *
	 * @return the cache file suffix
	 */
	public String getCacheFileSuffix() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.RequestHandler#getHostnameVerifier()
	 */
	@Override
	public HostnameVerifier getHostnameVerifier() {
		return new LocalHostnameVerifier();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.RequestHandler#getLatestRequestMethod()
	 */
	@Override
	public String getLatestRequestMethod() {
		return this.request.getMethod();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.RequestHandler#getLatestRequestURL()
	 */
	@Override
	public URL getLatestRequestURL() {
		return this.request.getRequestURL();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.RequestHandler#getRequest()
	 */
	@Override
	public ClientletRequest getRequest() {
		return this.request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.request.RequestHandler#handleProgress(org.loboevolution.ua.
	 * ProgressType, java.net.URL, java.lang.String, int, int)
	 */
	@Override
	public void handleProgress(ProgressType progressType, URL url, String method, int value, int max) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.request.RequestHandler#handleException(org.loboevolution.
	 * clientlet .ClientletResponse, java.lang.Throwable)
	 */
	@Override
	public boolean handleException(ClientletResponse response, Throwable exception) throws ClientletException {
		logger.error("handleException(): Error processing response=[" + response + "]", exception);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.RequestHandler#cancel()
	 */
	@Override
	public void cancel() {
		this.cancelled = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.RequestHandler#isCancelled()
	 */
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.RequestHandler#getRequestType()
	 */
	@Override
	public RequestType getRequestType() {
		return this.requestType;
	}

	/**
	 * The Class LocalHostnameVerifier.
	 */
	private class LocalHostnameVerifier implements HostnameVerifier {
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.net.ssl.HostnameVerifier#verify(String,
		 * javax.net.ssl.SSLSession)
		 */
		@Override
		public boolean verify(final String host, SSLSession arg1) {
			final VerifiedHostsStore vhs = VerifiedHostsStore.getInstance();
			return vhs.contains(host);
		}
	}

}
