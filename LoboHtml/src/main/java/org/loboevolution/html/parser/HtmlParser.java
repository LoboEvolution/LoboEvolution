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

package org.loboevolution.html.parser;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.Entities;
import org.loboevolution.html.HTMLEntities;
import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.dom.domimpl.DocumentTypeImpl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.nodeimpl.DOMException;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.ElementInfo;
import org.loboevolution.html.node.Code;

import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * The HtmlParser class is an HTML DOM parser. This parser provides
 * the functionality for the standard DOM parser implementation
 * {@link org.loboevolution.html.parser.DocumentBuilderImpl}. This parser class
 * may be used directly when a different DOM implementation is preferred.
 *
 *
 *
 */
public class HtmlParser {
	private static final Logger logger = Logger.getLogger(HtmlParser.class.getName());

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

	private final static Pattern doctypePattern = Pattern.compile("(\\S+)\\s+PUBLIC\\s+\"([^\"]*)\"\\s+\"([^\"]*)\".*>");

	private final static String[] elementsThatDontNeedBodyElement = { "HTML", "HEAD", "META", "TITLE", "LINK", "SCRIPT", "STYLE" };

	private final static String[] elementsThatDontNeedHeadElement = { "HTML", "P", "DIV", "SPAN", "UL", "OL", "LI", "A",
			"IMG", "IFRAME", "TABLE", "TBODY", "THEAD", "TR", "TH", "TD", "MATHML", "FRAMESET" };

	/**
	 * Constructs a HtmlParser.
	 *
	 * @param ucontext The user agent context.
	 * @param document A W3C Document instance.
	 */
	public HtmlParser(UserAgentContext ucontext, Document document) {
		this.ucontext = ucontext;
		this.document = document;
	}

	/**
	 * Constructs a HtmlParser.
	 *
	 * @param ucontext     The user agent context.
	 * @param document     An W3C Document instance.
	 * @param errorHandler The error handler.
	 * @param needRoot a boolean.
	 */
	public HtmlParser(UserAgentContext ucontext, Document document, ErrorHandler errorHandler, final boolean needRoot) {
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
	public static boolean isDecodeEntities(String elementName) {
		final ElementInfo einfo = HTMLEntities.ELEMENT_INFOS.get(HTMLTag.get(elementName.toUpperCase()));
		return einfo == null || einfo.decodeEntities;
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
		final Document doc = this.document;
		this.parse(reader, doc);
	}

	/**
	 * Parses text followed by one element.
	 *
	 * @param parent
	 * @param reader
	 * @param stopTags    If tags in this set are encountered, the method throws
	 *                    StopException.
	 * @return
	 * @throws IOException
	 * @throws StopException
	 * @throws SAXException
	 */
	private int parseToken(final Node parent, final LineNumberReader reader, final Set<HTMLTag> stopTags,
						   final LinkedList<String> ancestors) throws IOException, StopException, SAXException {
		final Document doc = this.document;
		final HTMLDocumentImpl htmlDoc = (HTMLDocumentImpl) doc;
		final StringBuilder textSb = this.readUpToTagBegin(reader);
		if (textSb == null) {
			return TOKEN_EOD;
		}
		if (textSb.length() != 0) {
			// int textLine = reader.getLineNumber();
			final StringBuilder decText = entityDecode(textSb);
			final Node textNode = doc.createTextNode(decText.toString());
			try {
				safeAppendChild(parent, textNode);
			} catch (final DOMException de) {
				if ((parent.getNodeType() != NodeType.DOCUMENT_NODE) || (de.getCode() != Code.HIERARCHY_REQUEST_ERR)) {
					logger.log(Level.WARNING, "parseToken(): Unable to append child to " + parent + ".", de);
				}
			}
		}
		if (this.justReadTagBegin) {
			String tag = this.readTag(parent, reader);
			if (tag == null) {
				return TOKEN_EOD;
			}
			String normalTag = tag.toUpperCase();
			try {
				if (tag.startsWith("!")) {
					if ("!--".equals(tag)) {
						// int commentLine = reader.getLineNumber();
						final StringBuilder comment = this.passEndOfComment(reader);
						final StringBuilder decText = entityDecode(comment);
						safeAppendChild(parent, doc.createComment(decText.toString()));
						return TOKEN_COMMENT;
					} else if ("!DOCTYPE".equals(tag)) {
						final String doctypeStr = this.parseEndOfTag(reader);
						final Matcher doctypeMatcher = doctypePattern.matcher(doctypeStr);
						if (doctypeMatcher.matches()) {
							final String qName = doctypeMatcher.group(1);
							final String publicId = doctypeMatcher.group(2);
							final String systemId = doctypeMatcher.group(3);
							final DocumentTypeImpl doctype = new DocumentTypeImpl(qName, publicId, systemId);
							htmlDoc.setDoctype(doctype);
							needRoot = false;
						}
						return TOKEN_BAD;
					} else {
						passEndOfTag(reader);
						return TOKEN_BAD;
					}
				} else if (tag.startsWith("/")) {
					tag = tag.substring(1);
					normalTag = normalTag.substring(1);
					this.passEndOfTag(reader);
					return TOKEN_END_ELEMENT;
				} else if (tag.startsWith("?")) {
					tag = tag.substring(1);
					final StringBuilder data = readProcessingInstruction(reader);
					safeAppendChild(parent, doc.createProcessingInstruction(tag, data.toString()));
					return TOKEN_FULL_ELEMENT;
				} else {
					final int localIndex = normalTag.indexOf(':');
					final boolean tagHasPrefix = localIndex > 0;
					final String localName = tagHasPrefix ? normalTag.substring(localIndex + 1) : normalTag;
					Element element = doc.createElement(localName);
					element.setUserData(MODIFYING_KEY, Boolean.TRUE, null);
					try {
						if (!this.justReadTagEnd) {
							while (this.readAttribute(reader, element)) {
								// EMPTY LOOP
							}
						}
						if (stopTags != null && stopTags.contains(HTMLTag.get(normalTag))) {
							// Throw before appending to parent.
							// After attributes are set.
							// After MODIFYING_KEY is set.
							throw new StopException(element);
						}
						// Add element to parent before children are added.
						// This is necessary for incremental rendering.
						safeAppendChild(parent, element);
						if (!this.justReadEmptyElement) {
							ElementInfo einfo = HTMLEntities.ELEMENT_INFOS.get(HTMLTag.get(localName.toUpperCase()));
							int endTagType = einfo == null ? ElementInfo.END_ELEMENT_REQUIRED : einfo.endElementType;
							if (endTagType != ElementInfo.END_ELEMENT_FORBIDDEN) {
								boolean childrenOk = einfo == null || einfo.childElementOk;
								Set<HTMLTag> newStopSet = einfo == null ? null : einfo.stopTags;
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
											int token;
											if ((einfo != null) && einfo.noScriptElement) {
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
															|| (closeTagInfo.endElementType != ElementInfo.END_ELEMENT_FORBIDDEN)) {
														// TODO: Rather inefficient algorithm, but it's
														// probably executed infrequently?
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
													// TODO: Working here
												}
											} else if (token == TOKEN_EOD) {
												return TOKEN_EOD;
											}
										} catch (final StopException se) {
											// newElement does not have a parent.
											final Element newElement = se.getElement();
											tag = newElement.getTagName();
											normalTag = tag.toUpperCase();
											if (stopTags != null && stopTags.contains(HTMLTag.get(normalTag))) {
												throw se;
											}
											einfo = HTMLEntities.ELEMENT_INFOS.get(HTMLTag.get(normalTag.toUpperCase()));
											endTagType = einfo == null ? ElementInfo.END_ELEMENT_REQUIRED
													: einfo.endElementType;
											childrenOk = einfo == null || einfo.childElementOk;
											newStopSet = einfo == null ? null : einfo.stopTags;
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
											// Switch element
											element.setUserData(MODIFYING_KEY, Boolean.FALSE, null);
											// newElement should have been suspended.
											element = newElement;
											// Add to parent
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
						// This can inform elements to continue with notifications.
						// It can also cause Javascript to be loaded / processed.
						// Update: Elements now use Document.addJob() to delay processing
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
	private StringBuilder readUpToTagBegin(final LineNumberReader reader) throws IOException, SAXException {
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
	 *
	 * @param parent
	 * @param reader
	 * @param tagName
	 * @return
	 * @throws IOException
	 */
	private int parseForEndTag(Node parent, final LineNumberReader reader, final String tagName,
							   final boolean addTextNode, final boolean decodeEntities) throws IOException, SAXException {
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
						INNER: while ((intCh = reader.read()) != -1) {
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
										if (text.length() != 0) {
											final Node textNode = doc.createTextNode(text);
											safeAppendChild(parent, textNode);
										}
									}
									return TOKEN_END_ELEMENT;
								} else {
									break INNER;
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
		if (addTextNode) {
			if (decodeEntities) {
				sb = entityDecode(sb);
			}
			final String text = sb.toString();
			if (text.length() != 0) {
				final Node textNode = doc.createTextNode(text);
				safeAppendChild(parent, textNode);
			}
		}
		return HtmlParser.TOKEN_EOD;
	}

	private static void readCData(LineNumberReader reader, StringBuilder sb) throws IOException {

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
		char[] chars = new char[n];
		int i = 0;
		while (i < n) {
			int ich = -1;
			try {
				ich = reader.read();
			} catch (IOException e) {
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
	 *
	 * @param reader
	 * @return
	 */
	private String readTag(final Node parent, final LineNumberReader reader) throws IOException {
		final StringBuilder sb = new StringBuilder();
		int chInt;
		chInt = reader.read();
		if (chInt != -1) {
			boolean cont = true;
			char ch;
			LOOP: for (;;) {
				ch = (char) chInt;
				if (Character.isLetter(ch)) {
					// Speed up normal case
					break LOOP;
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
					final Document doc = this.document;
					final Node textNode = doc.createTextNode(ltText.toString());
					try {
						parent.appendChild(textNode);
					} catch (final DOMException de) {
						if ((parent.getNodeType() != NodeType.DOCUMENT_NODE)
								|| (de.getCode() != Code.HIERARCHY_REQUEST_ERR)) {
							logger.log(Level.WARNING, "parseToken(): Unable to append child to " + parent + ".", de);
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
					final Document doc = this.document;
					final Node textNode = doc.createTextNode(ltText.toString());
					try {
						parent.appendChild(textNode);
					} catch (final DOMException de) {
						if ((parent.getNodeType() != NodeType.DOCUMENT_NODE)
								|| (de.getCode() != Code.HIERARCHY_REQUEST_ERR)) {
							logger.log(Level.WARNING, "parseToken(): Unable to append child to " + parent + ".", de);
						}
					}
					if (chInt == -1) {
						cont = false;
					} else {
						continue;
					}
				}
				break LOOP;
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
						final String tag = sb.toString();
						return tag;
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
		if (sb.length() > 0) {
			this.justReadTagEnd = false;
			this.justReadTagBegin = false;
			this.justReadEmptyElement = false;
		}
		final String tag = sb.toString();
		return tag;
	}

	private StringBuilder passEndOfComment(final LineNumberReader reader) throws IOException {
		if (this.justReadTagEnd) {
			return new StringBuilder(0);
		}
		final StringBuilder sb = new StringBuilder();
		OUTER: for (;;) {
			int chInt = reader.read();
			if (chInt == -1) {
				break OUTER;
			}
			char ch = (char) chInt;
			if (ch == '-') {
				chInt = reader.read();
				if (chInt == -1) {
					sb.append(ch);
					break OUTER;
				}
				ch = (char) chInt;
				if (ch == '-') {
					StringBuilder extra = null;
					INNER: for (;;) {
						chInt = reader.read();
						if (chInt == -1) {
							if (extra != null) {
								sb.append(extra.toString());
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
								sb.append(extra.toString());
							}
							sb.append(ch);
							break INNER;
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
		if (sb.length() > 0) {
			this.justReadTagBegin = false;
			this.justReadTagEnd = false;
		}
		return sb;
	}

	private String parseEndOfTag(final Reader reader) throws IOException {
		if (this.justReadTagEnd) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		boolean readSomething = false;
		for (;;) {
			final int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			result.append((char) chInt);
			readSomething = true;
			final char ch = (char) chInt;
			if (ch == '>') {
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
			this.justReadTagEnd = false;
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

	private boolean readAttribute(final LineNumberReader reader, final Element element)
			throws IOException, SAXException {
		if (this.justReadTagEnd) {
			return false;
		}

		// Read attribute name up to '=' character.
		// May read several attribute names without explicit values.

		StringBuilder attributeName = null;
		boolean blankFound = false;
		boolean lastCharSlash = false;
		for (;;) {
			final int chInt = reader.read();
			if (chInt == -1) {
				if ((attributeName != null) && (attributeName.length() != 0)) {
					final String attributeNameStr = attributeName.toString();
					element.setAttribute(attributeNameStr, attributeNameStr);
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
				if ((attributeName != null) && (attributeName.length() != 0)) {
					final String attributeNameStr = attributeName.toString();
					element.setAttribute(attributeNameStr, attributeNameStr);
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
					if ((attributeName != null) && (attributeName.length() != 0)) {
						final String attributeNameStr = attributeName.toString();
						element.setAttribute(attributeNameStr, attributeNameStr);
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
				if ((attributeName != null) && (attributeName.length() != 0)) {
					final String attributeNameStr = attributeName.toString();
					element.setAttribute(attributeNameStr, attributeNameStr);
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
			if ((openQuote != -1) && (ch == openQuote)) {
				lastCharSlash = false;
				if (attributeName != null) {
					final String attributeNameStr = attributeName.toString();
					if (attributeValue == null) {
						// Quotes are closed. There's a distinction
						// between blank values and null in HTML, as
						// processed by major browsers.
						element.setAttribute(attributeNameStr, "");
					} else {
						final StringBuilder actualAttributeValue = entityDecode(attributeValue);
						element.setAttribute(attributeNameStr, actualAttributeValue.toString());
					}
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = false;
				return true;
			} else if ((openQuote == -1) && (ch == '>')) {
				if (attributeName != null) {
					final String attributeNameStr = attributeName.toString();
					if (attributeValue == null) {
						element.setAttribute(attributeNameStr, null);
					} else {
						final StringBuilder actualAttributeValue = entityDecode(attributeValue);
						element.setAttribute(attributeNameStr, actualAttributeValue.toString());
					}
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = true;
				this.justReadEmptyElement = lastCharSlash;
				return false;
			} else if ((openQuote == -1) && Character.isWhitespace(ch)) {
				lastCharSlash = false;
				if (attributeName != null) {
					final String attributeNameStr = attributeName.toString();
					if (attributeValue == null) {
						element.setAttribute(attributeNameStr, null);
					} else {
						final StringBuilder actualAttributeValue = entityDecode(attributeValue);
						element.setAttribute(attributeNameStr, actualAttributeValue.toString());
					}
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
			if (attributeValue == null) {
				element.setAttribute(attributeNameStr, null);
			} else {
				final StringBuilder actualAttributeValue = entityDecode(attributeValue);
				element.setAttribute(attributeNameStr, actualAttributeValue.toString());
			}
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
		    //logger.info("Inserting HTML");
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
		Node newParent = parent;
		if (needRoot) {
			// final String nodeName = child.getNodeName();
			final String nodeNameTU = child.getNodeName().toUpperCase();
			if ("BODY".equals(nodeNameTU)) {
				lastBodyElement = child;
				// logger.info("Found body elem: " + child);
			} else if ("HEAD".equals(nodeNameTU)) {
				lastHeadElement = child;
			} else if ((child instanceof Element) && (depthAtMost(parent, 2))) {
				final boolean dontNeedBody = ArrayUtilities.contains(elementsThatDontNeedBodyElement, nodeNameTU);
				final boolean dontNeedHead = ArrayUtilities.contains(elementsThatDontNeedHeadElement, nodeNameTU);
				if ((!hasAncestorTag(parent, "BODY")) && (!dontNeedBody)) {
					ensureBodyElement(parent);
					newParent = lastBodyElement;
				} else if ((!hasAncestorTag(parent, "HEAD")) && (!dontNeedHead)) {
					ensureHeadElement(parent);
					newParent = lastHeadElement;
				}
			}
		}
		newParent.appendChild(child);
	}

	private void ensureBodyElement(final Node parent) {
		if (lastBodyElement == null) {
			lastBodyElement = document.createElement("BODY");
			parent.appendChild(lastBodyElement);
		}
	}

	private void ensureHeadElement(final Node parent) {
		if (lastHeadElement == null) {
			// logger.info("Inserting HEAD");
			lastHeadElement = document.createElement("HEAD");
			parent.appendChild(lastHeadElement);
		}
	}

	private boolean shouldDecodeEntities(final ElementInfo einfo) {
		return (einfo == null || einfo.decodeEntities);
	}

	private static StringBuilder entityDecode(final StringBuilder rawText) throws org.xml.sax.SAXException {
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
			sb.append(rawText.substring(startIdx, ampIdx));
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
					logger.log(Level.WARNING, "entityDecode()", nfe);
					decimal = 0;
				}
				sb.append((char) decimal);
			} else {
				final int chInt = getEntityChar(spec);
				if (chInt == -1) {
					sb.append('&');
					sb.append(spec);
					sb.append(';');
				} else {
					sb.append((char) chInt);
				}
			}
			startIdx = colonIdx + 1;
		}
	}

	private static int getEntityChar(final String spec) {
		Character c = (Character) HTMLEntities.ENTITIES.get(Entities.get(spec));
		if (c == null) {
			final String specTL = spec.toLowerCase();
			c = HTMLEntities.ENTITIES.get(Entities.get(specTL));
			if (c == null) {
				return -1;
			}
		}
		return c;
	}
}
