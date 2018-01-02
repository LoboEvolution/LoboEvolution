/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
	ClientletRequest getRequest();

	/**
	 * Gets the latest request url.
	 *
	 * @return the latest request url
	 */
	URL getLatestRequestURL();

	/**
	 * Gets the latest request method.
	 *
	 * @return the latest request method
	 */
	String getLatestRequestMethod();

	/**
	 * Gets the hostname verifier.
	 *
	 * @return the hostname verifier
	 */
	HostnameVerifier getHostnameVerifier();

	/**
	 * Process response.
	 *
	 * @param response
	 *            the response
	 * @throws ClientletException
	 *             the clientlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void processResponse(ClientletResponse response) throws ClientletException, IOException;

	/**
	 * Handle exception.
	 *
	 * @param response
	 *            the response
	 * @param exception
	 *            the exception
	 * @return true, if successful
	 * @throws ClientletException
	 *             the clientlet exception
	 */
	boolean handleException(ClientletResponse response, Throwable exception) throws ClientletException;

	/**
	 * Handle progress.
	 *
	 * @param progressType
	 *            the progress type
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @param value
	 *            the value
	 * @param max
	 *            the max
	 */
	void handleProgress(ProgressType progressType, URL url, String method, int value, int max);

	/**
	 * Cancel.
	 */
	void cancel();

	/**
	 * Checks if is cancelled.
	 *
	 * @return true, if is cancelled
	 */
	boolean isCancelled();

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	RequestType getRequestType();
}
