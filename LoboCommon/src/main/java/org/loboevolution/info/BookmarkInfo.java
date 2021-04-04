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
 * <p>BookmarkInfo class.</p>
 *
 *
 *
 */
public class BookmarkInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2257845000007000400L;

	/** The description. */
	private String description;

	/** The tags. */
	private String[] tags;

	/** The title. */
	private String title;

	/** The url. */
	private String url;

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public String[] getTags() {
		return this.tags;
	}

	/**
	 * Gets the tags text.
	 *
	 * @return the tags text
	 */
	public String getTagsText() {
		final String[] tags = this.tags;
		if (tags == null) {
			return "";
		}
		final StringBuilder buffer = new StringBuilder();
		boolean firstTime = true;
		for (final String tag : tags) {
			if (firstTime) {
				firstTime = false;
			} else {
				buffer.append(' ');
			}
			buffer.append(tag);
		}
		return buffer.toString();
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the tags.
	 *
	 * @param tags the new tags
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
