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

package org.loboevolution.http;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.info.MetaInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>HtmlContent class.</p>
 */
public class HtmlContent {

	private final Document document;

	/**
	 * <p>Constructor for HtmlContent.</p>
	 *
	 * @param document a {@link org.loboevolution.html.node.Document} object.
	 */
	public HtmlContent(final Document document) {
		this.document = document;
	}

	/**
	 * <p>getJSList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<MetaInfo> getJSList() {
		final List<MetaInfo> infoList = new ArrayList<>();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final HTMLCollectionImpl nodeList = (HTMLCollectionImpl) doc.getElementsByTagName("script");

		if (nodeList == null) {
			return null;
		}

		nodeList.forEach(node -> {
			if (node instanceof HTMLElement element) {
                String src = element.getAttribute("src");
				if (Strings.isNotBlank(src)) {
					if (!Urls.isAbsolute(src)) {
						src = doc.getFullURL(src).toString();
					}
					if (src.startsWith("//")) {
						src = "http:" + src;
					}
					infoList.add(MetaInfo.builder().name(src).build());
				}
			}
		});
		return infoList;
	}

	/**
	 * <p>getMediaList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<MetaInfo> getMediaList() {
		final List<MetaInfo> infoList = new ArrayList<>();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		HTMLCollectionImpl nodeList = (HTMLCollectionImpl) doc.getElementsByTagName("img");

		if (nodeList == null) {
			return null;
		}
		
		nodeList.forEach(node -> {
			if (node instanceof HTMLElement element) {
                String src = element.getAttribute("src");
				if (Strings.isNotBlank(src)) {
					if (!Urls.isAbsolute(src)) {
						src = doc.getFullURL(src).toString();
					}
					if (src.startsWith("//")) {
						src = "http:" + src;
					}

					infoList.add(MetaInfo.builder().name(src).build());
				}
			}
		});

		nodeList = (HTMLCollectionImpl) doc.getElementsByTagName("link");
		nodeList.forEach(node -> {
			if (node instanceof HTMLElement element) {
                final String rel = element.getAttribute("rel");
				String href = element.getAttribute("href");
				if ("icon".equalsIgnoreCase(rel) && Strings.isNotBlank(href)) {
					if (!Urls.isAbsolute(href)) {
						href = doc.getFullURL(href).toString();
					}
					if (href.startsWith("//")) {
						href = "http:" + href;
					}
					infoList.add(MetaInfo.builder().name(href).build());
				}
			}
		});
		return infoList;
	}

	/**
	 * <p>getMetaList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<MetaInfo> getMetaList() {
		final List<MetaInfo> infoList = new ArrayList<>();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final HTMLCollectionImpl nodeList = (HTMLCollectionImpl) doc.getElementsByTagName("meta");

		if (nodeList == null) {
			return null;
		}

		nodeList.forEach(node -> {
			if (node instanceof HTMLElement element) {
                infoList.add(MetaInfo.builder()
						.name(element.getAttribute("name"))
						.itemprop(element.getAttribute("itemprop"))
						.property(element.getAttribute("property"))
						.httpEqui(element.getAttribute("http-equi"))
						.content(element.getAttribute("content"))
						.description(element.getAttribute("description"))
						.charset(element.getAttribute("charset"))
						.build());
			}
		});
		return infoList;
	}

	/**
	 * <p>getStyleList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<MetaInfo> getStyleList() {
		final List<MetaInfo> infoList = new ArrayList<>();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final HTMLCollectionImpl nodeList = (HTMLCollectionImpl) doc.getElementsByTagName("link");

		if (nodeList == null) {
			return null;
		}

		nodeList.forEach(node -> {
			if (node instanceof HTMLElement element) {
                final String rel = element.getAttribute("rel");
				String href = element.getAttribute("href");
				if ("stylesheet".equalsIgnoreCase(rel) && Strings.isNotBlank(href)) {
					if (!Urls.isAbsolute(href)) {
						href = doc.getFullURL(href).toString();
					}
					if (href.startsWith("//")) {
						href = "http:" + href;
					}
					infoList.add(MetaInfo.builder().name(href).build());
				}
			}
		});
		return infoList;
	}

}
