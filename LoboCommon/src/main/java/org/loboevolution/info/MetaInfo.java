/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.info;

import java.io.Serializable;

/**
 * <p>MetaInfo class.</p>
 *
 *
 *
 */
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
	 * <p>Getter for the field charset.</p>
	 *
	 * @return the charset
	 */
	public String getCharset() {
		return this.charset;
	}

	/**
	 * <p>Getter for the field content.</p>
	 *
	 * @return the content
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * <p>Getter for the field description.</p>
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * <p>Getter for the field httpEqui.</p>
	 *
	 * @return the httpEqui
	 */
	public String getHttpEqui() {
		return this.httpEqui;
	}

	/**
	 * <p>Getter for the field itemprop.</p>
	 *
	 * @return the itemprop
	 */
	public String getItemprop() {
		return this.itemprop;
	}

	/**
	 * <p>Getter for the field name.</p>
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * <p>Getter for the field property.</p>
	 *
	 * @return the property
	 */
	public String getProperty() {
		return this.property;
	}

	/**
	 * <p>Setter for the field charset.</p>
	 *
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * <p>Setter for the field content.</p>
	 *
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * <p>Setter for the field httpEqui.</p>
	 *
	 * @param httpEqui the httpEqui to set
	 */
	public void setHttpEqui(String httpEqui) {
		this.httpEqui = httpEqui;
	}

	/**
	 * <p>Setter for the field itemprop.</p>
	 *
	 * @param itemprop the itemprop to set
	 */
	public void setItemprop(String itemprop) {
		this.itemprop = itemprop;
	}

	/**
	 * <p>Setter for the field name.</p>
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Setter for the field property.</p>
	 *
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

}
