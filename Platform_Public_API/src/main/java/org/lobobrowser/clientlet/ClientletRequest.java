/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
	 * Gets the method.
	 *
	 * @return the method
	 */
	String getMethod();

	/**
	 * Gets the request url.
	 *
	 * @return the request url
	 */
	URL getRequestURL();

	/**
	 * Gets the user agent.
	 *
	 * @return the user agent
	 */
	UserAgent getUserAgent();

	/**
	 * Gets the referrer.
	 *
	 * @return the referrer
	 */
	String getReferrer();

	/**
	 * Gets the parameter info.
	 *
	 * @return the parameter info
	 */
	ParameterInfo getParameterInfo();

	/**
	 * Gets the extra headers.
	 *
	 * @return the extra headers
	 */
	Header[] getExtraHeaders();

	/**
	 * Checks if is gets the request.
	 *
	 * @return true, if is gets the request
	 */
	boolean isGetRequest();

	/**
	 * Checks if is post request.
	 *
	 * @return true, if is post request
	 */
	boolean isPostRequest();

	/**
	 * Checks if is new window request.
	 *
	 * @return true, if is new window request
	 */
	boolean isNewWindowRequest();

	/**
	 * Gets the alt post data.
	 *
	 * @return the alt post data
	 */
	String getAltPostData();

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	RequestType getRequestType();
}
