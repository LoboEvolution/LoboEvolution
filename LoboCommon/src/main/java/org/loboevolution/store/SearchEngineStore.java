/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.store;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * The Class SearchEngineStore.
 *
 * @author utente
 * @version $Id: $Id
 */
public class SearchEngineStore implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 225745010000001000L;

	/** The base url. */
	private String baseUrl;

	/** The description. */
	private String description;

	/** The query parameter. */
	private boolean isSelected;

	/** The name. */
	private String name;

	/** The query parameter. */
	private String queryParameter;

	/** The file. */
	private String type;

	/**
	 * Gets the base url.
	 *
	 * @return the base url
	 */
	public String getBaseUrl() {
		return this.baseUrl;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the query parameter.
	 *
	 * @return the query parameter
	 */
	public String getQueryParameter() {
		return this.queryParameter;
	}

	/**
	 * <p>Getter for the field type.</p>
	 *
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Gets the url.
	 *
	 * @param query the query
	 * @return the url
	 * @throws java.net.MalformedURLException the malformed url exception
	 */
	public URL getURL(String query) throws MalformedURLException {
		final String baseUrl = this.baseUrl;
		final int qmIdx = baseUrl.indexOf('?');
		final char join = qmIdx == -1 ? '?' : '&';
		try {
			return new URL(baseUrl + join + this.queryParameter + "=" + URLEncoder.encode(query, "UTF-8"));
		} catch (final UnsupportedEncodingException uee) {
			throw new IllegalStateException("not expected", uee);
		}
	}

	/**
	 * <p>isSelected.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isSelected() {
		return this.isSelected;
	}

	/**
	 * <p>Setter for the field baseUrl.</p>
	 *
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * <p>Setter for the field description.</p>
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the name.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Setter for the field queryParameter.</p>
	 *
	 * @param queryParameter the queryParameter to set
	 */
	public void setQueryParameter(String queryParameter) {
		this.queryParameter = queryParameter;
	}

	/**
	 * <p>setSelected.</p>
	 *
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * <p>Setter for the field type.</p>
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return this.name;
	}
}
