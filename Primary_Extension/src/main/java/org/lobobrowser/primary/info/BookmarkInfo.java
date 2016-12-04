/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.primary.info;

import java.io.Serializable;
import java.net.URL;

/**
 * The Class BookmarkInfo.
 */
public class BookmarkInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2257845000007000400L;

	/** The url. */
	private URL url;

	/** The title. */
	private String title;

	/** The description. */
	private String description;

	/** The tags. */
	private String[] tags;

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
		String[] tags = this.tags;
		if (tags == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		boolean firstTime = true;
		for (String tag : tags) {
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
	 * Sets the tags.
	 *
	 * @param tags
	 *            the new tags
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
}
