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

package org.loboevolution.html.parser;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.Entities;
import org.loboevolution.html.HTMLEntities;
import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.nodeimpl.DocumentTypeImpl;
import org.loboevolution.html.dom.nodeimpl.EntityReferenceImpl;
import org.loboevolution.html.dom.nodeimpl.NotationImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.AttributeInfo;
import org.loboevolution.info.ElementInfo;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * The XHtmlParser class is an HTML DOM parser. This parser provides
 * the functionality for the standard DOM parser implementation
 * {@link org.loboevolution.html.parser.DocumentBuilderImpl}. This parser class
 * may be used directly when a different DOM implementation is preferred.
 */
@Slf4j
public class XHtmlParser {

	/** Constant MODIFYING_KEY="cobra.suspend" */
	public static final String MODIFYING_KEY = "cobra.suspend";
	private static final int TOKEN_BAD = 6;

	private static final int TOKEN_BEGIN_ELEMENT = 3;
	private static final int TOKEN_COMMENT = 1;

	private static final int TOKEN_END_ELEMENT = 4;

	private static final int TOKEN_EOD = 0;

	private static final int TOKEN_FULL_ELEMENT = 5;

	private static final int TOKEN_TEXT = 2;

	private final Document document;

	private boolean justReadEmptyElement = false;

	private boolean justReadTagBegin = false;

	private boolean justReadTagEnd = false;

	private String normalLastTag = null;

	private final UserAgentContext ucontext;

	private Node lastRootElement = null;

	private Node lastHeadElement = null;

	private Node lastBodyElement = null;

	private boolean needRoot = false;

	@Getter
	private final Map<String, String> namespaces = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	/**
	 * Constructs a XHtmlParser.
	 *
	 * @param ucontext The user agent context.
	 * @param document A W3C Document instance.
	 */
	public XHtmlParser(final UserAgentContext ucontext, final Document document) {
		this.ucontext = ucontext;
		this.document = document;
	}

	/**
	 * Constructs a XHtmlParser.
	 *
	 * @param ucontext     The user agent context.
	 * @param document     An W3C Document instance.
	 * @param needRoot a boolean.
	 */
	public XHtmlParser(final UserAgentContext ucontext, final Document document, final boolean needRoot) {
		this.ucontext = ucontext;
		this.document = document;
		this.needRoot = needRoot;
	}

	/**
	 * <p>isDecodeEntities.</p>
	 *
	 * @param elementName a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isDecodeEntities(final String elementName) {
		final ElementInfo einfo = HTMLEntities.ELEMENT_INFOS.get(HTMLTag.get(elementName.toUpperCase()));
		return einfo == null || einfo.isDecodeEntities();
	}

	/**
	 * This method may be used when the DOM should be built under a given node, such
	 * as when innerHTML is used in Javascript.
	 *
	 * @param reader A LineNumberReader for the document.
	 * @param parent The root node for the parsed DOM.
	 * @throws java.io.IOException if any.
	 * @throws org.xml.sax.SAXException if any.
	 */
	public void parse(final LineNumberReader reader, final Node parent) throws IOException, SAXException {

		// Note: Parser does not clear document. It could be used incrementally.
		try {
			parent.setUserData(MODIFYING_KEY, Boolean.TRUE, null);
			try {
				while (this.parseToken(parent, reader, null, new LinkedList<>()) != TOKEN_EOD) {
				}
			} catch (final StopException se) {
				throw new SAXException("Unexpected flow exception", se);
			}
		} finally {
			if (needRoot) {
				ensureRootElement(parent);
				ensureHeadElement(lastRootElement);
				ensureBodyElement(lastRootElement);
			}
			parent.setUserData(MODIFYING_KEY, Boolean.FALSE, null);
		}
	}

	/**
	 * Parses HTML given by a Reader. This method appends nodes to the
	 * document provided to the parser.
	 *
	 * @param reader An instance of Reader.
	 * @throws java.io.IOException if any.
	 * @throws org.xml.sax.SAXException if any.
	 */
	public void parse(final Reader reader) throws IOException, SAXException {
		this.parse(new LineNumberReader(reader));
	}

	/**
	 * This method may be used when the DOM should be built under a given node, such
	 * as when innerHTML is used in Javascript.
	 *
	 * @param reader A document reader.
	 * @param parent The root node for the parsed DOM.
	 * @throws java.io.IOException if any.
	 * @throws org.xml.sax.SAXException if any.
	 */
	public void parse(final Reader reader, final Node parent) throws IOException, SAXException {
		this.parse(new LineNumberReader(reader), parent);
	}

	/**
	 * <p>parse.</p>
	 *
	 * @param reader a {@link java.io.LineNumberReader} object.
	 * @throws java.io.IOException if any.
	 * @throws org.xml.sax.SAXException if any.
	 */
	public void parse(final LineNumberReader reader) throws IOException, SAXException {
		this.parse(reader, this.document);
	}

	/**
	 * Parses text followed by one element.
	 * If tags in this set are encountered, the method throws StopException.
	 *
	 * @param parent  a {@link org.loboevolution.html.node.Node} object.
	 * @param reader  a {@link java.io.LineNumberReader} object.
	 * @param stopTags  a {@link java.util.Set} object
	 * @param ancestors  a {@link java.util.LinkedList} object
	 * @return {@link java.lang.Number} object.
	 */
	private int parseToken(Node parent, final LineNumberReader reader, final Set<HTMLTag> stopTags,
                           final List<String> ancestors) throws IOException, StopException {
		final Document doc = this.document;
		final HTMLDocumentImpl htmlDoc = (HTMLDocumentImpl) doc;
		final StringBuilder textSb = this.readUpToTagBegin(reader);
		if (textSb == null) {
			return TOKEN_EOD;
		}
		if (!textSb.isEmpty()) {
			final String text = textSb.toString();
			if (Strings.isNotBlank(text.trim())) {

				final StringBuilder ent = new StringBuilder();
				final StringBuilder txt = new StringBuilder();
				final StringBuilder cdata = new StringBuilder();
				final AtomicBoolean isEnt = new AtomicBoolean(false);
				final AtomicBoolean isCda = new AtomicBoolean(false);

				try {
					for (int i = 0; i < text.length(); i++) {
						final char ch = text.charAt(i);
								if (ch == '&') {
									if (!txt.isEmpty()) {
										final Node textNode = doc.createTextNode(txt.toString());
										safeAppendChild(parent, textNode);
										txt.setLength(0);
									}

									isEnt.set(true);
								}

								if (ch == '<') {
									isCda.set(true);
								}

								if (ch == '>') {
									isCda.set(false);
									cdata.append(ch);
									final Node textNode = doc.createCDATASection(cdata.toString());
									safeAppendChild(parent, textNode);
									cdata.setLength(0);
								}

								if (!isEnt.get() && !isCda.get()) {
									txt.append(ch);
								} else if (isCda.get()) {
									cdata.append(ch);
								} else {
									ent.append(ch);
								}

								if (ch == ';') {
									isEnt.set(false);
									final Node textNode = doc.createEntityReference(ent.toString());
									safeAppendChild(parent, textNode);
									ent.setLength(0);

								}
							}

					if (!txt.isEmpty()) {
						final Node textNode = doc.createTextNode(txt.toString());
						safeAppendChild(parent, textNode);
						txt.setLength(0);
					}
				} catch (final DOMException de) {
					if ((parent.getNodeType() != Node.DOCUMENT_NODE) || (de.getCode() != DOMException.HIERARCHY_REQUEST_ERR)) {
						log.error("parseToken(): Unable to append child to  {} ", parent, de);
					}
				}
			}
		}

		if (this.justReadTagBegin) {
			String tag = this.readTag(parent, reader);

			if (Strings.isBlank(tag)) {
				return TOKEN_EOD;
			}
			String normalTag = tag.toUpperCase();

			if (shouldCloseParentBeforeAppending(parent, normalTag)) {
				this.normalLastTag = parent.getNodeName();

				Node grandParent = parent.getParentNode();
				if (grandParent != null) {
					grandParent.removeChild(parent);
					grandParent.appendChild(parent);

					Element newElement = doc.createElement(normalTag);
					grandParent.appendChild(newElement);

					return parseToken(newElement, reader, stopTags, ancestors);
				}
				return TOKEN_END_ELEMENT;
			}

			try {

				if (tag.startsWith("!")) {
					switch (tag) {
						case "!--":
							final StringBuilder comment = this.passEndOfComment(reader);
							final StringBuilder decText = entityDecode(comment);
							safeAppendChild(parent, doc.createComment(decText.toString()));
							return TOKEN_COMMENT;
						case "!DOCTYPE":
							final String doctypeStr = this.parseEndOfTag(reader);
							String qName = null;
							String publicId = null;
							String systemId = null;
							if (Strings.containsIgnoreCase(doctypeStr, "public")) {
								final String[] publics = Strings.splitIgnoreCase(doctypeStr, "public");
								final String[] result = publics[1].replace("[", "").split("\"");
								final List<String> list = Arrays.stream(result)
										.filter(s -> Strings.isNotBlank(s) && s.length() > 1)
										.collect(Collectors.toList());

								if(list.size() == 1) {
									publicId = list.stream().findFirst().get();
								}

								if(list.size() == 2) {
									publicId = list.get(0);
									systemId = list.get(1);
								}

								qName  = publics[0];

							}

							if (qName == null && Strings.containsIgnoreCase(doctypeStr, "svg")) {
								final String[] publics = Strings.splitIgnoreCase(doctypeStr, "svg");
								qName = publics[0];
								this.document.setXml(true);
							}

							if (qName == null && Strings.containsIgnoreCase(doctypeStr, "html")) {
								qName = "html";
							}

							final DocumentType docType = new DocumentTypeImpl(qName, publicId, systemId);
							docType.setOwnerDocument(htmlDoc);
							htmlDoc.setDoctype(docType);
							needRoot = false;
							return TOKEN_BAD;
						case "!ENTITY":
							String doctypeStr2 = this.parseEndOfTag(reader).trim();
							doctypeStr2 = doctypeStr2.substring(0, doctypeStr2.length() - 1);

							String[] parts = doctypeStr2.split("\\s+", 3);
							if (parts.length < 2) {
								return TOKEN_BAD;
							}

							String entityName = parts[0].trim();
							String entityValue = parts[1].replaceAll("^\"|\"$", ""); // Remove surrounding quotes

							EntityReferenceImpl reference = new EntityReferenceImpl();
							reference.setOwnerDocument(document);
							reference.setParentImpl(document);
							reference.setNodeName(entityName);

							if (parts.length >= 3) {
								String[] remainingParts = parts[2].split("\\s+");
								boolean isPublic = false;
								boolean isNdata = false;

								for (String part : remainingParts) {
									part = part.trim();
									if (part.isEmpty()) continue;

									if ("PUBLIC".equals(part)) {
										isPublic = true;
									} else if ("NDATA".equals(part)) {
										isNdata = true;
									} else if (isPublic) {
										reference.setPublicId(part);
										isPublic = false;
									} else if (isNdata) {
										reference.setNotationName(part);
										isNdata = false;
									} else {
										reference.setSystemId(part);
									}
								}
							} else {
								reference.setSystemId(entityValue);
							}

							htmlDoc.getDoctype().getEntities().setNamedItem(reference);
							needRoot = false;
							return TOKEN_BAD;
						case "!NOTATION":
							final String notationStr = this.parseEndOfTag(reader);
							final NotationImpl not = new NotationImpl();
							not.setOwnerDocument(document);
							not.setParentImpl(document);

							if (notationStr.contains("PUBLIC")) {
								final String[] split = notationStr.split("PUBLIC");
								final AtomicInteger ai = new AtomicInteger(0);
								Arrays.stream(split).forEach(s -> {
									if(ai.get() == 0) {
										not.setNodeName(s.trim());
									}

									if(ai.get() == 1) {
										not.setPublicId(s.split("\"")[1].trim());
									}

									ai.incrementAndGet();

								});
								ai.set(0);
							}

							if (notationStr.contains("SYSTEM")) {
								final String[] split = notationStr.split("SYSTEM");
								final AtomicInteger ai = new AtomicInteger(0);
								Arrays.stream(split).forEach(s -> {
									if(ai.get() == 0) {
										not.setNodeName(s.trim());
									}

									if(ai.get() == 1) {
										not.setPublicId(s.split("\"")[1].trim());
									}

									ai.incrementAndGet();

								});
							}

							htmlDoc.getDoctype().getNotations().setNamedItem(not);
							needRoot = false;
							return TOKEN_BAD;
						default:
							passEndOfTag(reader);
							return TOKEN_BAD;
					}
				} else if (tag.startsWith("/")) {
					normalTag = normalTag.substring(1);
					this.passEndOfTag(reader);
					return TOKEN_END_ELEMENT;
				} else if (tag.startsWith("?")) {

					tag = tag.substring(1);
					final StringBuilder data = readProcessingInstruction(reader);
					if (!tag.equals("xml")) {
						String processData = data.toString();
						processData = processData.substring(0, processData.length() - 1);
						final ProcessingInstruction pi = doc.createProcessingInstruction(tag, processData);
						parent.appendChild(pi);
						return TOKEN_FULL_ELEMENT;
					} else {
						this.document.setXml(true);
						return TOKEN_TEXT;
					}
				} else {
					final List<AttributeInfo> attributeInfo = new ArrayList<>();
					Element element = null;
					try {
						if (!this.justReadTagEnd) {
							while (this.readAttribute(reader, attributeInfo)) {
								// EMPTY LOOP
							}
						}
						if (this.document.isXml()) {
							final AtomicReference<String> atomicReference = new AtomicReference<>(normalTag);
							final AtomicReference<String> reference = new AtomicReference<>();
							final String elm = atomicReference.get();

							if (attributeInfo.isEmpty()) {
								reference.set(getNamespaces().get(elm.contains(":") ? elm.split(":")[0] : ""));
							}

							attributeInfo.forEach(info -> {
								final String attribute = info.getAttributeName();
								final int index = attribute.contains("xmlns") ? 1 : 0;
								final String attributeSplit = attribute.contains(":") ? attribute.split(":")[index] : attribute;

								if (attribute.equals("xmlns") ||
										(attributeSplit.equalsIgnoreCase((elm.contains(":") ? elm.split(":")[0] : elm).toLowerCase()))) {

									if (getNamespaces().get(attributeSplit) == null) {
										getNamespaces().put(attributeSplit, info.getAttributeValue());
										reference.set(info.getAttributeValue());
									} else {
										reference.set(getNamespaces().get(attributeSplit));
									}
								}
							});
							element = doc.createElementNS(reference.get(), normalTag);

						} else {

							if (shouldCloseParentBeforeAppending(parent, normalTag)) {
								this.normalLastTag = parent.getNodeName();

								parent.getParentNode().appendChild(parent.cloneNode(false));
								return TOKEN_END_ELEMENT;
							}

							element = doc.createElement(normalTag);

							if (element != null) {
								Node current = element;
								while (current != null && current.getParentNode() != null) {
									if (shouldCloseParentBeforeAppending(current.getParentNode(), current.getNodeName())) {
										Node grandParent = current.getParentNode().getParentNode();
										if (grandParent != null) {
											grandParent.appendChild(current.getParentNode());
										}
									}
									current = current.getParentNode();
								}
							}
						}

						element.setUserData(MODIFYING_KEY, Boolean.TRUE, null);

						safeAppendChild(parent, element);
						final AtomicReference<Element> elementAtomicReference = new AtomicReference<>(element);

						attributeInfo.forEach(info -> setAttributeNode(elementAtomicReference.get(), info.getAttributeName(), info.getAttributeValue()));

						if (stopTags != null && stopTags.contains(HTMLTag.get(normalTag))) {
							throw new StopException(element);
						}

						if (!this.justReadEmptyElement) {
							ElementInfo einfo = HTMLEntities.ELEMENT_INFOS.get(HTMLTag.get(normalTag.toUpperCase()));
							int endTagType = einfo == null ? ElementInfo.END_ELEMENT_REQUIRED : einfo.getEndElementType();
							if (endTagType != ElementInfo.END_ELEMENT_FORBIDDEN) {
								boolean childrenOk = einfo == null || einfo.isChildElementOk();
								Set<HTMLTag> newStopSet = einfo == null ? null : einfo.getStopTags();
								if (newStopSet == null) {
									if (endTagType == ElementInfo.END_ELEMENT_OPTIONAL) {
										newStopSet = Collections.singleton(HTMLTag.get(normalTag));
									}
								}
								if (stopTags != null) {
									if (newStopSet != null) {
										final Set<HTMLTag> newStopSet2 = new HashSet<>();
										newStopSet2.addAll(stopTags);
										newStopSet2.addAll(newStopSet);
										newStopSet = newStopSet2;
									} else {
										newStopSet = endTagType == ElementInfo.END_ELEMENT_REQUIRED ? null : stopTags;
									}
								}
								ancestors.addFirst(normalTag);
								try {
									for (;;) {
										try {
											final int token;
											if ((einfo != null) && einfo.isNoScriptElement()) {
												final UserAgentContext ucontext = this.ucontext;
												if ((ucontext == null) || ucontext.isScriptingEnabled()) {
													token = this.parseForEndTag(parent, reader, tag, false, shouldDecodeEntities(einfo));
												} else {
													token = this.parseToken(element, reader, newStopSet, ancestors);
												}
											} else {
												token = childrenOk
														? this.parseToken(element, reader, newStopSet, ancestors)
														: this.parseForEndTag(element, reader, tag, true,
														shouldDecodeEntities(einfo));
											}
											if (token == TOKEN_END_ELEMENT) {
												final String normalLastTag = this.normalLastTag;
												if (normalTag.equalsIgnoreCase(normalLastTag)) {
													return TOKEN_FULL_ELEMENT;
												} else {
													final ElementInfo closeTagInfo = HTMLEntities.ELEMENT_INFOS
															.get(HTMLTag.get(normalLastTag.toUpperCase()));
													if ((closeTagInfo == null)
															|| (closeTagInfo.getEndElementType() != ElementInfo.END_ELEMENT_FORBIDDEN)) {
														final Iterator<String> i = ancestors.iterator();
														if (i.hasNext()) {
															i.next();
															while (i.hasNext()) {
																final String normalAncestorTag = i.next();
																if (normalLastTag.equals(normalAncestorTag)) {
																	normalTag = normalLastTag;
																	return TOKEN_END_ELEMENT;
																}
															}
														}
													}
												}
											} else if (token == TOKEN_EOD) {
												getNamespaces().clear();
												return TOKEN_EOD;
											}
										} catch (final StopException se) {
											final Element newElement = se.getElement();
											tag = newElement.getTagName();
											normalTag = tag.toUpperCase();
											if (stopTags != null && stopTags.contains(HTMLTag.get(normalTag))) {
												throw se;
											}
											einfo = HTMLEntities.ELEMENT_INFOS.get(HTMLTag.get(normalTag.toUpperCase()));
											endTagType = einfo == null ? ElementInfo.END_ELEMENT_REQUIRED
													: einfo.getEndElementType();
											childrenOk = einfo == null || einfo.isChildElementOk();
											newStopSet = einfo == null ? null : einfo.getStopTags();
											if (newStopSet == null) {
												if (endTagType == ElementInfo.END_ELEMENT_OPTIONAL) {
													newStopSet = Collections.singleton(HTMLTag.get(normalTag));
												}
											}
											if (stopTags != null && newStopSet != null) {
												final Set<HTMLTag> newStopSet2 = new HashSet<>();
												newStopSet2.addAll(stopTags);
												newStopSet2.addAll(newStopSet);
												newStopSet = newStopSet2;
											}
											ancestors.removeFirst();
											ancestors.addFirst(normalTag);
											element.setUserData(MODIFYING_KEY, Boolean.FALSE, null);
											element = newElement;
											safeAppendChild(parent, element);
											if (this.justReadEmptyElement) {
												return TOKEN_BEGIN_ELEMENT;
											}
										}
									}
								} finally {
									ancestors.removeFirst();
								}
							}
						}
						return TOKEN_BEGIN_ELEMENT;
					} finally {
						if(element != null)
							element.setUserData(MODIFYING_KEY, Boolean.FALSE, null);
					}
				}
			} finally {
				this.normalLastTag = normalTag;
			}
		} else {
			this.normalLastTag = null;
			return TOKEN_TEXT;
		}
	}

	/**
	 * Reads text until the beginning of the next tag. Leaves the reader offset past
	 * the opening angle bracket. Returns null only on EOF.
	 */
	private StringBuilder readUpToTagBegin(final LineNumberReader reader) throws IOException {
		StringBuilder sb = null;
		int intCh;
		while ((intCh = reader.read()) != -1) {
			final char ch = (char) intCh;
			if (ch == '<') {
				this.justReadTagBegin = true;
				this.justReadTagEnd = false;
				this.justReadEmptyElement = false;
				if (sb == null) {
					sb = new StringBuilder(0);
				}
				return sb;
			}
			if (sb == null) {
				sb = new StringBuilder();
			}
			sb.append(ch);
		}
		this.justReadTagBegin = false;
		this.justReadTagEnd = false;
		this.justReadEmptyElement = false;
		return sb;
	}

	/**
	 * Assumes that the content is completely made up of text, and parses until an
	 * ending tag is found.
	 */
	private int parseForEndTag(final Node parent, final LineNumberReader reader, final String tagName,
							   final boolean addTextNode, final boolean decodeEntities) throws IOException {
		final Document doc = this.document;
		int intCh;
		StringBuilder sb = new StringBuilder();
		while ((intCh = reader.read()) != -1) {
			char ch = (char) intCh;
			if (ch == '<') {
				intCh = reader.read();
				if (intCh != -1) {
					ch = (char) intCh;
					if (ch == '/') {
						final StringBuilder tempBuffer = new StringBuilder();
						while ((intCh = reader.read()) != -1) {
							ch = (char) intCh;
							if (ch == '>') {
								final String thisTag = tempBuffer.toString().trim();
								if (thisTag.equalsIgnoreCase(tagName)) {
									this.justReadTagBegin = false;
									this.justReadTagEnd = true;
									this.justReadEmptyElement = false;
									this.normalLastTag = thisTag;
									if (addTextNode) {
										if (decodeEntities) {
											sb = entityDecode(sb);
										}
										final String text = sb.toString();
										if (Strings.isNotBlank(text.trim())) {
											Node actualParent = parent;
											if (shouldCloseParentBeforeAppending(parent, tagName)) {
												actualParent = parent.getParentNode();
											}
											final Node textNode = text.trim().startsWith("&") ?
													doc.createEntityReference(text) : doc.createTextNode(text);
											safeAppendChild(actualParent, textNode);
										}
									}
									return TOKEN_END_ELEMENT;
								} else {
									break;
								}
							} else {
								tempBuffer.append(ch);
							}
						}
						sb.append("</");
						sb.append(tempBuffer);
					} else if (ch == '!') {
						final String nextSeven = readN(reader, 7);
						if ("[CDATA[".equals(nextSeven)) {
							readCData(reader, sb);
						} else {
							sb.append('!');
							if (nextSeven != null) {
								sb.append(nextSeven);
							}
						}
					} else {
						sb.append('<');
						sb.append(ch);
					}
				} else {
					sb.append('<');
				}
			} else {
				sb.append(ch);
			}
		}
		this.justReadTagBegin = false;
		this.justReadTagEnd = false;
		this.justReadEmptyElement = false;
		if (addTextNode && !sb.isEmpty()) {
			String text = decodeEntities ? entityDecode(sb).toString() : sb.toString();
			if (Strings.isNotBlank(text.trim())) {
				Node targetParent = parent;

				if (shouldCloseParentBeforeAppending(parent, tagName)) {
					targetParent = parent.getParentNode();
				}

				Node textNode = text.trim().startsWith("&") ?
						doc.createEntityReference(text) : doc.createTextNode(text);
				safeAppendChild(targetParent, textNode);
			}
		}

		return XHtmlParser.TOKEN_EOD;
	}

	private static void readCData(final LineNumberReader reader, final StringBuilder sb) throws IOException {

		int next = reader.read();

		while (next >= 0) {
			final char nextCh = (char) next;
			if (nextCh == ']') {
				final String next2 = readN(reader, 2);
				if (next2 != null) {
					if ("]>".equals(next2)) {
						break;
					} else {
						sb.append(nextCh);
						sb.append(next2);
						next = reader.read();
					}
				} else {
					break;
				}
			} else {
				sb.append(nextCh);
				next = reader.read();
			}
		}
	}

	// Tries to read at most n characters.
	private static String readN(final LineNumberReader reader, final int n) {
		final char[] chars = new char[n];
		int i = 0;
		while (i < n) {
			final int ich;
			try {
				ich = reader.read();
			} catch (final IOException e) {
				break;
			}
			if (ich >= 0) {
				chars[i] = (char) ich;
				i += 1;
			} else {
				break;
			}
		}

		if (i == 0) {
			return null;
		} else {
			return String.valueOf(chars, 0, i);
		}
	}

	/**
	 * The reader offset should be
	 */
	private String readTag(final Node parent, final LineNumberReader reader) throws IOException {
		final StringBuilder sb = new StringBuilder();
		int chInt;
		chInt = reader.read();
		if (chInt != -1) {
			boolean cont = true;
			char ch;
			for (;;) {
				ch = (char) chInt;
				if (Character.isLetter(ch)) {
					// Speed up normal case
					break;
				} else if (ch == '!') {
					sb.append('!');
					chInt = reader.read();
					if (chInt != -1) {
						ch = (char) chInt;
						if (ch == '-') {
							sb.append('-');
							chInt = reader.read();
							if (chInt != -1) {
								ch = (char) chInt;
								if (ch == '-') {
									sb.append('-');
									cont = false;
								}
							} else {
								cont = false;
							}
						} else{
							if (ch == '[') {
								final StringBuilder ltText = new StringBuilder();
								readCData(reader, ltText);
								parent.appendChild(document.createCDATASection("<![" + ltText + "]]"));
							}
						}
					} else {
						cont = false;
					}
				} else if (ch == '/') {
					sb.append(ch);
					chInt = reader.read();
					if (chInt != -1) {
						ch = (char) chInt;
					} else {
						cont = false;
					}
				} else if (ch == '<') {
					final StringBuilder ltText = new StringBuilder(3);
					ltText.append('<');
					while ((chInt = reader.read()) == '<') {
						ltText.append('<');
					}
					final String text = ltText.toString();
					final Node textNode = text.trim().startsWith("&") ? this.document.createEntityReference(text) : this.document.createTextNode(text);
					try {
						parent.appendChild(textNode);
					} catch (final DOMException de) {
						if ((parent.getNodeType() != Node.DOCUMENT_NODE)
								|| (de.getCode() != DOMException.HIERARCHY_REQUEST_ERR)) {
							log.error("parseToken(): Unable to append child to  {} ", parent, de);
						}
					}
					if (chInt == -1) {
						cont = false;
					} else {
						continue;
					}
				} else if (Character.isWhitespace(ch)) {
					final StringBuilder ltText = new StringBuilder();
					ltText.append('<');
					ltText.append(ch);
					while ((chInt = reader.read()) != -1) {
						ch = (char) chInt;
						if (ch == '<') {
							chInt = reader.read();
							break;
						}
						ltText.append(ch);
					}
					final String text = ltText.toString();
					final Node textNode = text.trim().startsWith("&") ? this.document.createEntityReference(text) : this.document.createTextNode(text);
					try {
						parent.appendChild(textNode);
					} catch (final DOMException de) {
						if ((parent.getNodeType() != Node.DOCUMENT_NODE)
								|| (de.getCode() != DOMException.HIERARCHY_REQUEST_ERR)) {
							log.error("parseToken(): Unable to append child to  {} ", parent, de);
						}
					}
					if (chInt == -1) {
						cont = false;
					} else {
						continue;
					}
				}
				break;
			}
			if (cont) {
				boolean lastCharSlash = false;
				for (;;) {
					if (Character.isWhitespace(ch)) {
						break;
					} else if (ch == '>') {
						this.justReadTagEnd = true;
						this.justReadTagBegin = false;
						this.justReadEmptyElement = lastCharSlash;
						return sb.toString();
					} else if (ch == '/') {
						lastCharSlash = true;
					} else {
						if (lastCharSlash) {
							sb.append('/');
						}
						lastCharSlash = false;
						sb.append(ch);
					}
					chInt = reader.read();
					if (chInt == -1) {
						break;
					}
					ch = (char) chInt;
				}
			}
		}
		if (!sb.isEmpty()) {
			this.justReadTagEnd = false;
			this.justReadTagBegin = false;
			this.justReadEmptyElement = false;
		}
		return sb.toString();
	}

	private StringBuilder passEndOfComment(final LineNumberReader reader) throws IOException {
		if (this.justReadTagEnd) {
			return new StringBuilder(0);
		}
		final StringBuilder sb = new StringBuilder();
		OUTER: for (;;) {
			int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			char ch = (char) chInt;
			if (ch == '-') {
				chInt = reader.read();
				if (chInt == -1) {
					sb.append(ch);
					break;
				}
				ch = (char) chInt;
				if (ch == '-') {
					StringBuilder extra = null;
					for (; ; ) {
						chInt = reader.read();
						if (chInt == -1) {
							if (extra != null) {
								sb.append(extra);
							}
							break OUTER;
						}
						ch = (char) chInt;
						if (ch == '>') {
							this.justReadTagBegin = false;
							this.justReadTagEnd = true;
							return sb;
						} else if (ch == '-') {
							// Allow any number of dashes at the end
							if (extra == null) {
								extra = new StringBuilder();
								extra.append("--");
							}
							extra.append("-");
						} else if (Character.isWhitespace(ch)) {
							if (extra == null) {
								extra = new StringBuilder();
								extra.append("--");
							}
							extra.append(ch);
						} else {
							if (extra != null) {
								sb.append(extra);
							}
							sb.append(ch);
							break;
						}
					}
				} else {
					sb.append('-');
					sb.append(ch);
				}
			} else {
				sb.append(ch);
			}
		}
		if (!sb.isEmpty()) {
			this.justReadTagBegin = false;
			this.justReadTagEnd = false;
		}
		return sb;
	}

	private String parseEndOfTag(final Reader reader) throws IOException {
		if (this.justReadTagEnd) {
			return "";
		}
		final StringBuilder result = new StringBuilder();
		boolean readSomething = false;
		for (;;) {
			final int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			result.append((char) chInt);
			readSomething = true;
			final char ch = (char) chInt;
			if (ch == '>' || ch == '[') {
				this.justReadTagEnd = true;
				this.justReadTagBegin = false;
				return result.toString();
			}
		}
		if (readSomething) {
			this.justReadTagBegin = false;
			this.justReadTagEnd = false;
		}
		return result.toString();
	}

	private void passEndOfTag(final Reader reader) throws IOException {
		if (this.justReadTagEnd) {
			return;
		}
		boolean readSomething = false;
		for (;;) {
			final int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			readSomething = true;
			final char ch = (char) chInt;
			if (ch == '>') {
				this.justReadTagEnd = true;
				this.justReadTagBegin = false;
				return;
			}
		}
		if (readSomething) {
			this.justReadTagBegin = false;
		}
	}

	private StringBuilder readProcessingInstruction(final LineNumberReader reader) throws IOException {
		final StringBuilder pidata = new StringBuilder();
		if (this.justReadTagEnd) {
			return pidata;
		}
		int ch;
		for (ch = reader.read(); (ch != -1) && (ch != '>'); ch = reader.read()) {
			pidata.append((char) ch);
		}
		this.justReadTagBegin = false;
		this.justReadTagEnd = ch != -1;
		return pidata;
	}

	private boolean readAttribute(final LineNumberReader reader, final List<AttributeInfo> attributes)
			throws IOException {
		if (this.justReadTagEnd) {
			return false;
		}

		StringBuilder attributeName = null;
		boolean blankFound = false;
		boolean lastCharSlash = false;
		for (;;) {
			final int chInt = reader.read();
			if (chInt == -1) {
				if (Strings.isStringBuilderNotBlack(attributeName)) {
					final String attributeNameStr = attributeName.toString();
					attributes.add(new AttributeInfo(attributeNameStr, attributeNameStr));
					attributeName.setLength(0);
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = false;
				this.justReadEmptyElement = false;
				return false;
			}
			final char ch = (char) chInt;
			if (ch == '=') {
				lastCharSlash = false;
				blankFound = false;
				break;
			} else if (ch == '>') {
				if (Strings.isStringBuilderNotBlack(attributeName)) {
					final String attributeNameStr = attributeName.toString();
					attributes.add(new AttributeInfo(attributeNameStr, attributeNameStr));
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = true;
				this.justReadEmptyElement = lastCharSlash;
				return false;
			} else if (ch == '/') {
				blankFound = true;
				lastCharSlash = true;
			} else if (Character.isWhitespace(ch)) {
				lastCharSlash = false;
				blankFound = true;
			} else {
				lastCharSlash = false;
				if (blankFound) {
					blankFound = false;
					if (Strings.isStringBuilderNotBlack(attributeName)) {
						final String attributeNameStr = attributeName.toString();
						attributes.add(new AttributeInfo(attributeNameStr, attributeNameStr));
						attributeName.setLength(0);
					}
				}
				if (attributeName == null) {
					attributeName = new StringBuilder(6);
				}
				attributeName.append(ch);
			}
		}
		// Read blanks up to open quote or first non-blank.
		StringBuilder attributeValue = null;
		int openQuote = -1;
		for (;;) {
			final int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			final char ch = (char) chInt;
			if (ch == '>') {
				if (Strings.isStringBuilderNotBlack(attributeName)) {
					final String attributeNameStr = attributeName.toString();
					attributes.add(new AttributeInfo(attributeNameStr, attributeNameStr));
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = true;
				this.justReadEmptyElement = lastCharSlash;
				return false;
			} else if (ch == '/') {
				lastCharSlash = true;
			} else if (Character.isWhitespace(ch)) {
				lastCharSlash = false;
			} else {
				if (ch == '"') {
					openQuote = '"';
				} else if (ch == '\'') {
					openQuote = '\'';
				} else {
					openQuote = -1;
					attributeValue = new StringBuilder(6);
					if (lastCharSlash) {
						attributeValue.append('/');
					}
					attributeValue.append(ch);
				}
				lastCharSlash = false;
				break;
			}
		}

		// Read attribute value

		for (;;) {
			final int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			final char ch = (char) chInt;
			if (ch == openQuote) {
				lastCharSlash = false;
				if (attributeName != null) {
					final String attributeNameStr = attributeName.toString();
					attributes.add(new AttributeInfo(attributeNameStr, attributeValue == null ? "" : entityDecode(attributeValue).toString()));

				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = false;
				return true;
			} else if (openQuote == -1 && ch == '>') {
				if (attributeName != null) {
					final String attributeNameStr = attributeName.toString();
					attributes.add(new AttributeInfo(attributeNameStr, attributeValue == null ? "" : entityDecode(attributeValue).toString()));
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = true;
				this.justReadEmptyElement = lastCharSlash;
				return false;
			} else if (openQuote == -1 && Character.isWhitespace(ch)) {
				lastCharSlash = false;
				if (attributeName != null) {
					final String attributeNameStr = attributeName.toString();
					attributes.add(new AttributeInfo(attributeNameStr, attributeValue == null ? "" : entityDecode(attributeValue).toString()));
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = false;
				return true;
			} else {
				if (attributeValue == null) {
					attributeValue = new StringBuilder(6);
				}
				if (lastCharSlash) {
					attributeValue.append('/');
				}
				lastCharSlash = false;
				attributeValue.append(ch);
			}
		}
		this.justReadTagBegin = false;
		this.justReadTagEnd = false;
		if (attributeName != null) {
			final String attributeNameStr = attributeName.toString();
			attributes.add(new AttributeInfo(attributeNameStr, attributeValue == null ? "" : entityDecode(attributeValue).toString()));
		}
		return false;
	}

	private static boolean hasAncestorTag(final Node node, final String tag) {
		if (node == null) {
			return false;
		} else if (tag.equalsIgnoreCase(node.getNodeName())) {
			return true;
		} else {
			return hasAncestorTag(node.getParentNode(), tag);
		}
	}

	private void safeAppendChild(final Node parent, final Node child) {
		Node newParent = parent;

		if(child instanceof Element)
		if (needRoot) {
			final String nodeName = child.getNodeName();
			if ("HTML".equalsIgnoreCase(nodeName)) {
				lastRootElement = child;
			} else if ((child instanceof Element) && (depthAtMost(parent, 1)) && (!hasAncestorTag(parent, "HTML"))) {
				ensureRootElement(parent);
				newParent = lastRootElement;
			}
		}

		ensureBodyAppendChild(newParent, child);
	}

	private void ensureRootElement(final Node parent) {
		if (lastRootElement == null) {
			lastRootElement = document.createElement("HTML");
			parent.appendChild(lastRootElement);
		}
	}

	private static boolean depthAtMost(final Node n, final int maxDepth) {
		if (maxDepth <= 0) {
			return false;
		} else {
			final Node parent = n.getParentNode();
			return parent == null || depthAtMost(parent, maxDepth - 1);
		}
	}

	private void ensureBodyAppendChild(final Node parent, final Node child) {
        if (needRoot) {
			final String nodeNameTU = child.getNodeName().toUpperCase();
			if ("BODY".equals(nodeNameTU)) {
				lastBodyElement = child;
			} else if ("HEAD".equals(nodeNameTU)) {
				lastHeadElement = child;
			}
		}
		if(parent != null) {
            parent.appendChild(child);}
	}

	private void ensureBodyElement(final Node parent) {
		if (lastBodyElement == null) {
			lastBodyElement = document.createElement("BODY");
			parent.appendChild(lastBodyElement);
		}
	}

	private void ensureHeadElement(final Node parent) {
		if (lastHeadElement == null) {
			lastHeadElement = document.createElement("HEAD");
			parent.appendChild(lastHeadElement);
		}
	}

	private boolean shouldDecodeEntities(final ElementInfo einfo) {
		return (einfo == null || einfo.isDecodeEntities());
	}

	private static StringBuilder entityDecode(final StringBuilder rawText) {
		int startIdx = 0;
		StringBuilder sb = null;
		for (;;) {
			final int ampIdx = rawText.indexOf("&", startIdx);
			if (ampIdx == -1) {
				if (sb == null) {
					return rawText;
				} else {
					sb.append(rawText.substring(startIdx));
					return sb;
				}
			}
			if (sb == null) {
				sb = new StringBuilder();
			}


			sb.append(rawText, startIdx, ampIdx);
			final int colonIdx = rawText.indexOf(";", ampIdx);
			if (colonIdx == -1) {
				sb.append('&');
				startIdx = ampIdx + 1;
				continue;
			}
			final String spec = rawText.substring(ampIdx + 1, colonIdx);
			if (spec.startsWith("#")) {
				final String number = spec.substring(1).toLowerCase();
				int decimal;
				try {
					if (number.startsWith("x")) {
						decimal = Integer.parseInt(number.substring(1), 16);
					} else {
						decimal = Integer.parseInt(number);
					}
				} catch (final NumberFormatException nfe) {
					log.warn("entityDecode() ", nfe);
					decimal = 0;
				}
				sb.append((char) decimal);
			} else {
				final int chInt = getEntityChar(spec);
				if (chInt == -1) {
					sb.append(spec);
				} else {
					sb.append((char) chInt);
				}
			}
			startIdx = colonIdx + 1;
		}
	}

	private static int getEntityChar(final String spec) {
		Character c = HTMLEntities.ENTITIES.get(Entities.get(spec));
		if (c == null) {
			final String specTL = spec.toLowerCase();
			c = HTMLEntities.ENTITIES.get(Entities.get(specTL));
			if (c == null) {
				return -1;
			}
		}
		return c;
	}

	private void setAttributeNode(final Element element, final String attributeName, final String attributeValue) {
		if (this.document.isXml()) {
			String namespaceURI = null;
			if (attributeName.contains(":")) {
				String key = attributeName.split(":")[attributeName.contains("xmlns") ? 1 : 0];
				if (getNamespaces().get(key) == null) {
					getNamespaces().put(key, attributeValue);
					namespaceURI = attributeValue;
				} else {
					namespaceURI = getNamespaces().get(key);
				}
			}

			if (Strings.isNotBlank(namespaceURI)) {
				element.setAttributeNS(namespaceURI, attributeName, attributeValue);
			} else {
				element.setAttribute(attributeName, attributeValue);
			}
		} else {
			element.setAttribute(attributeName, attributeValue);
		}
	}

	private boolean shouldCloseParentBeforeAppending(Node parent, String tagName) {
		if (parent == null || parent.getNodeType() != Node.ELEMENT_NODE) {
			return false;
		}

		String parentTag = parent.getNodeName().toUpperCase();
		String newTag = tagName.toUpperCase();
		HTMLEntities entities = new HTMLEntities();

		if (entities.isElementNestable(newTag)) {
			return false;
		}

		if (entities.isComplexNestingElement(parentTag) && entities.isComplexNestingElement(newTag)) {
			return entities.violatesComplexNestingRules(parentTag, newTag);
		}

		if (parentTag.equals(newTag) && entities.isElementNonNestable(parentTag)) {
			return true;
		}

		return entities.violatesParentChildRule(parentTag, newTag);
	}
}
