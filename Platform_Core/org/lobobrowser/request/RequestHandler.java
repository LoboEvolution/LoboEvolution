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
package org.lobobrowser.request;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;

import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.RequestType;


/**
 * The Interface RequestHandler.
 */
public interface RequestHandler {
	
	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public ClientletRequest getRequest();

	/**
	 * Gets the actual request URL, which may be different from the URL of the
	 * original request.
	 *
	 * @return the latest request url
	 */
	public URL getLatestRequestURL();

	/**
	 * Gets the actual request method, which may be different from the method of
	 * the original request.
	 *
	 * @return the latest request method
	 */
	public String getLatestRequestMethod();

	/**
	 * Gets a hostname verifier used when an HTTPS host does not match the
	 * cerificate information.
	 *
	 * @return the hostname verifier
	 */
	public HostnameVerifier getHostnameVerifier();

	/**
	 * Process response.
	 *
	 * @param response the response
	 * @throws ClientletException the clientlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void processResponse(ClientletResponse response)
			throws ClientletException, IOException;

	/**
	 * Handle exception.
	 *
	 * @param response the response
	 * @param exception the exception
	 * @return true, if successful
	 * @throws ClientletException the clientlet exception
	 */
	public boolean handleException(ClientletResponse response,
			Throwable exception) throws ClientletException;

	/**
	 * Handle progress.
	 *
	 * @param progressType the progress type
	 * @param url the url
	 * @param method the method
	 * @param value the value
	 * @param max the max
	 */
	public void handleProgress(ProgressType progressType, URL url,
			String method, int value, int max);

	/**
	 * Cancel.
	 */
	public void cancel();

	/**
	 * Checks if is cancelled.
	 *
	 * @return true, if is cancelled
	 */
	public boolean isCancelled();

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	public RequestType getRequestType();
}
