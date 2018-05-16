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
package org.loboevolution.primary.settings;

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
	private String name;

	/** The description. */
	private String description;

	/** The base url. */
	private String baseUrl;

	/** The query parameter. */
	private String queryParameter;
	
	/** The file. */
	private String type;
	
	/** The query parameter. */
	private boolean isSelected;
	
	/**
	 * Gets the url.
	 *
	 * @param query
	 *            the query
	 * @return the url
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public URL getURL(String query) throws MalformedURLException {
		String baseUrl = this.baseUrl;
		int qmIdx = baseUrl.indexOf('?');
		char join = qmIdx == -1 ? '?' : '&';
		try {
			return new URL(baseUrl + join + this.queryParameter + "=" + URLEncoder.encode(query, "UTF-8"));
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the query parameter.
	 *
	 * @return the query parameter
	 */
	public String getQueryParameter() {
		return queryParameter;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * @param queryParameter the queryParameter to set
	 */
	public void setQueryParameter(String queryParameter) {
		this.queryParameter = queryParameter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
