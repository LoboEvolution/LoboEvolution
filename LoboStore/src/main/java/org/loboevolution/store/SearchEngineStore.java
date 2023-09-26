/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.store;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * The Class SearchEngineStore.
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
	 * @throws java.net.MalformedURLException if any.
	 */
	public URL getURL(final String query) throws MalformedURLException {
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
	public void setBaseUrl(final String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * <p>Setter for the field description.</p>
	 *
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets the name.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * <p>Setter for the field queryParameter.</p>
	 *
	 * @param queryParameter the queryParameter to set
	 */
	public void setQueryParameter(final String queryParameter) {
		this.queryParameter = queryParameter;
	}

	/**
	 * <p>setSelected.</p>
	 *
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(final boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * <p>Setter for the field type.</p>
	 *
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return this.name;
	}
}
