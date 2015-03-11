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
package org.lobobrowser.primary.settings;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * The Class SearchEngine.
 */
public class SearchEngine implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 225745010000001000L;
	
	/** The name. */
	private final String name;
	
	/** The description. */
	private final String description;
	
	/** The base url. */
	private final String baseUrl;
	
	/** The query parameter. */
	private final String queryParameter;

	/**
	 * Instantiates a new search engine.
	 *
	 * @param name the name
	 * @param description the description
	 * @param baseUrl the base url
	 * @param queryParameter the query parameter
	 */
	public SearchEngine(final String name, final String description,
			final String baseUrl, final String queryParameter) {
		super();
		this.name = name;
		this.description = description;
		this.baseUrl = baseUrl;
		this.queryParameter = queryParameter;
	}

	/**
	 * Gets the url.
	 *
	 * @param query the query
	 * @return the url
	 * @throws MalformedURLException the malformed url exception
	 */
	public URL getURL(String query) throws MalformedURLException {
		String baseUrl = this.baseUrl;
		int qmIdx = baseUrl.indexOf('?');
		char join = qmIdx == -1 ? '?' : '&';
		try {
			return new URL(baseUrl + join + this.queryParameter + "="
					+ URLEncoder.encode(query, "UTF-8"));
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalStateException("not expected", uee);
		}
	}

	/**
	 * Gets the base url.
	 *
	 * @return the base url
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the query parameter.
	 *
	 * @return the query parameter
	 */
	public String getQueryParameter() {
		return queryParameter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.name;
	}
}
