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
