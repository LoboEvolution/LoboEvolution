/*
Copyright 1994-2006 The Lobo Project. Copyright 2014 Lobo Evolution. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list 
of conditions and the following disclaimer. Redistributions in binary form must 
reproduce the above copyright notice, this list of conditions and the following 
disclaimer in the documentation and/or other materials provided with the distribution.
 
THIS SOFTWARE IS PROVIDED BY THE LOBO PROJECT ``AS IS'' AND ANY EXPRESS OR IMPLIED 
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO 
EVENT SHALL THE FREEBSD PROJECT OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.lobobrowser.clientlet;

import java.net.URL;

import org.lobobrowser.http.Header;
import org.lobobrowser.ua.ParameterInfo;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.ua.UserAgent;


/**
 * A URL request such as a HTTP, file or FTP request.
 * 
 * @author J. H. S.
 */
public interface ClientletRequest {
	/**
	 * Gets the request method.
	 * 
	 * @return GET, POST, etc.
	 */
	public String getMethod();

	/**
	 * Gets the request URL.
	 *
	 * @return the request url
	 */
	public URL getRequestURL();

	/**
	 * Gets information about the user agent making the request.
	 *
	 * @return the user agent
	 */
	public UserAgent getUserAgent();

	/**
	 * Gets the referring URL. It should be <code>null</code> if none or
	 * unknown.
	 *
	 * @return the referrer
	 */
	public String getReferrer();

	/**
	 * Gets information about the request parameters.
	 *
	 * @return the parameter info
	 */
	public ParameterInfo getParameterInfo();

	/**
	 * Gets additional headers used in the request.
	 *
	 * @return the extra headers
	 */
	public Header[] getExtraHeaders();

	/**
	 * Convenience method. Determines if the request method is GET.
	 *
	 * @return true, if is gets the request
	 */
	public boolean isGetRequest();

	/**
	 * Convenience method. Determines if the request method is POST.
	 *
	 * @return true, if is post request
	 */
	public boolean isPostRequest();

	/**
	 * Determines if the request was made in order to open a new browser window.
	 *
	 * @return true, if is new window request
	 */
	public boolean isNewWindowRequest();

	/**
	 * Provides alternative POST data in case no <code>ParameterInfo</code> is
	 * provied.
	 *
	 * @return the alt post data
	 */
	public String getAltPostData();

	/**
	 * Gets the type of request.
	 *
	 * @return the request type
	 */
	public RequestType getRequestType();
}
