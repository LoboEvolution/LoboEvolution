/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * The Class SearchEngineStore.
 */
@Data
public class SearchEngineStore implements Serializable {

	/** The Constant serialVersionUID. */
	@Serial
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
	 * Gets the url.
	 *
	 * @param query the query
	 * @return the url
	 * @throws java.lang.Exception if any.
	 */
	public URL getURL(final String query) throws Exception {
		final String baseUrl = this.baseUrl;
		final int qmIdx = baseUrl.indexOf('?');
		final char join = qmIdx == -1 ? '?' : '&';
		final String url = baseUrl + join + this.queryParameter + "=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        return new URI(url).toURL();
    }


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return this.name;
	}
}
