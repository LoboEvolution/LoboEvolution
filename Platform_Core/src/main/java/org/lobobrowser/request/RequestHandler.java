/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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

public interface RequestHandler {
	public ClientletRequest getRequest();

	/**
	 * Gets the actual request URL, which may be different from the URL of the
	 * original request.
	 */
	public URL getLatestRequestURL();

	/**
	 * Gets the actual request method, which may be different from the method of
	 * the original request.
	 */
	public String getLatestRequestMethod();

	/**
	 * Gets a hostname verifier used when an HTTPS host does not match the
	 * cerificate information.
	 */
	public HostnameVerifier getHostnameVerifier();

	public void processResponse(ClientletResponse response)
			throws ClientletException, IOException;

	public boolean handleException(ClientletResponse response,
			Throwable exception) throws ClientletException;

	public void handleProgress(ProgressType progressType, java.net.URL url,
			String method, int value, int max);

	public void cancel();

	public boolean isCancelled();

	public RequestType getRequestType();
}
