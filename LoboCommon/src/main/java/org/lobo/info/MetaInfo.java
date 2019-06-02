package org.lobo.info;

import java.io.Serializable;

public class MetaInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String charset;
	private String content;
	private String description;
	private String httpEqui;
	private String itemprop;
	private String name;
	private String property;

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return this.charset;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the httpEqui
	 */
	public String getHttpEqui() {
		return this.httpEqui;
	}

	/**
	 * @return the itemprop
	 */
	public String getItemprop() {
		return this.itemprop;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return this.property;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param httpEqui the httpEqui to set
	 */
	public void setHttpEqui(String httpEqui) {
		this.httpEqui = httpEqui;
	}

	/**
	 * @param itemprop the itemprop to set
	 */
	public void setItemprop(String itemprop) {
		this.itemprop = itemprop;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

}
