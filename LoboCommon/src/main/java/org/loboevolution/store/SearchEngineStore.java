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
	 * @throws MalformedURLException the malformed url exception
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

	public boolean isSelected() {
		return this.isSelected;
	}

	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
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
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param queryParameter the queryParameter to set
	 */
	public void setQueryParameter(String queryParameter) {
		this.queryParameter = queryParameter;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
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
	@Override
	public String toString() {
		return this.name;
	}
}
