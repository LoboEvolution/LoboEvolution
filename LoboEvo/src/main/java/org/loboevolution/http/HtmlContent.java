/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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
	 * @param document a {@link org.w3c.dom.Document} object.
	 */
	public HtmlContent(Document document) {
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
			if (node instanceof HTMLElement) {
				final HTMLElement element = (HTMLElement) node;
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
			if (node instanceof HTMLElement) {
				final HTMLElement element = (HTMLElement) node;
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
			if (node instanceof HTMLElement) {
				final HTMLElement element = (HTMLElement) node;
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
			if (node instanceof HTMLElement) {
				final HTMLElement element = (HTMLElement) node;
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
			if (node instanceof HTMLElement) {
				final HTMLElement element = (HTMLElement) node;
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
