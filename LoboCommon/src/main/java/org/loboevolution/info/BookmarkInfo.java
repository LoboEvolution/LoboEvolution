/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>BookmarkInfo class.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
