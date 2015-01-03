/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Aug 28, 2005
 */
package org.lobobrowser.html.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.HtmlMapping;
import org.lobobrowser.html.HtmlMappingChar;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.io.WritableLineReader;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * The <code>HtmlParser</code> class is an HTML DOM parser. This parser provides
 * the functionality for the standard DOM parser implementation
 * {@link org.lobobrowser.html.parser.DocumentBuilderImpl}. This parser class
 * may be used directly when a different DOM implementation is preferred.
 */
public class HtmlParser {
	private static final Logger logger = Logger.getLogger(HtmlParser.class.getName());
	private final Document document;
	private final UserAgentContext ucontext;
	private final String publicId;
	private final String systemId;

	private static Map<String, Character> ENTITIES = new HashMap<String, Character>(256);
	private static Map<String, ElementInfo> ELEMENT_INFOS = new HashMap<String, ElementInfo>(35);

	/**
	 * A node <code>UserData</code> key used to tell nodes that their content
	 * may be about to be modified. Elements could use this to temporarily
	 * suspend notifications. The value set will be either
	 * <code>Boolean.TRUE</code> or <code>Boolean.FALSE</code>.
	 */
	public static final String MODIFYING_KEY = "cobra.suspend";

	static {
		ENTITIES = HtmlMappingChar.mappingChar();
		ELEMENT_INFOS = HtmlMapping.mappingTag();
	}

	/**
	 * Constructs a <code>HtmlParser</code>.
	 * 
	 * @param document
	 *            A W3C Document instance.
	 * @param errorHandler
	 *            The error handler.
	 * @param publicId
	 *            The public ID of the document.
	 * @param systemId
	 *            The system ID of the document.
	 * @deprecated UserAgentContext should be passed in constructor.
	 */
	public HtmlParser(Document document, ErrorHandler errorHandler,
			String publicId, String systemId) {
		this.ucontext = null;
		this.document = document;
		this.publicId = publicId;
		this.systemId = systemId;
	}

	/**
	 * Constructs a <code>HtmlParser</code>.
	 * 
	 * @param ucontext
	 *            The user agent context.
	 * @param document
	 *            An W3C Document instance.
	 * @param errorHandler
	 *            The error handler.
	 * @param publicId
	 *            The public ID of the document.
	 * @param systemId
	 *            The system ID of the document.
	 */
	public HtmlParser(UserAgentContext ucontext, Document document,
			ErrorHandler errorHandler, String publicId, String systemId) {
		this.ucontext = ucontext;
		this.document = document;
		this.publicId = publicId;
		this.systemId = systemId;
	}

	/**
	 * Constructs a <code>HtmlParser</code>.
	 * 
	 * @param ucontext
	 *            The user agent context.
	 * @param document
	 *            A W3C Document instance.
	 */
	public HtmlParser(UserAgentContext ucontext, Document document) {
		this.ucontext = ucontext;
		this.document = document;
		this.publicId = null;
		this.systemId = null;
	}

	public static boolean isDecodeEntities(String elementName) {
		ElementInfo einfo = (ElementInfo) ELEMENT_INFOS.get(elementName
				.toUpperCase());
		return einfo == null ? true : einfo.decodeEntities;
	}

	/**
	 * Parses HTML from an input stream, assuming the character set is
	 * ISO-8859-1.
	 * 
	 * @param in
	 *            The input stream.
	 * @throws IOException
	 *             Thrown when there are errors reading the stream.
	 * @throws SAXException
	 *             Thrown when there are parse errors.
	 */
	public void parse(InputStream in) throws IOException, SAXException,
			UnsupportedEncodingException {
		this.parse(in, "ISO-8859-1");
	}

	/**
	 * Parses HTML from an input stream, using the given character set.
	 * 
	 * @param in
	 *            The input stream.
	 * @param charset
	 *            The character set.
	 * @throws IOException
	 *             Thrown when there's an error reading from the stream.
	 * @throws SAXException
	 *             Thrown when there is a parser error.
	 * @throws UnsupportedEncodingException
	 *             Thrown if the character set is not supported.
	 */
	public void parse(InputStream in, String charset) throws IOException,
			SAXException, UnsupportedEncodingException {
		WritableLineReader reader = new WritableLineReader(
				new InputStreamReader(in, charset));
		this.parse(reader);
	}

	/**
	 * Parses HTML given by a <code>Reader</code>. This method appends nodes to
	 * the document provided to the parser.
	 * 
	 * @param reader
	 *            An instance of <code>Reader</code>.
	 * @throws IOException
	 *             Thrown if there are errors reading the input stream.
	 * @throws SAXException
	 *             Thrown if there are parse errors.
	 */
	public void parse(Reader reader) throws IOException, SAXException {
		this.parse(new LineNumberReader(reader));
	}

	public void parse(LineNumberReader reader) throws IOException, SAXException {
		Document doc = this.document;
		this.parse(reader, doc);
	}

	/**
	 * This method may be used when the DOM should be built under a given node,
	 * such as when <code>innerHTML</code> is used in Javascript.
	 * 
	 * @param reader
	 *            A document reader.
	 * @param parent
	 *            The root node for the parsed DOM.
	 * @throws IOException
	 * @throws SAXException
	 */
	public void parse(Reader reader, Node parent) throws IOException,
			SAXException {
		this.parse(new LineNumberReader(reader), parent);
	}

	/**
	 * This method may be used when the DOM should be built under a given node,
	 * such as when <code>innerHTML</code> is used in Javascript.
	 * 
	 * @param reader
	 *            A LineNumberReader for the document.
	 * @param parent
	 *            The root node for the parsed DOM.
	 * @throws IOException
	 * @throws SAXException
	 */
	public void parse(LineNumberReader reader, Node parent) throws IOException,
			SAXException {
		// Note: Parser does not clear document. It could be used incrementally.
		try {
			parent.setUserData(MODIFYING_KEY, Boolean.TRUE, null);
			try {
				while (this.parseToken(parent, reader, null, new LinkedList<String>()) != TOKEN_EOD) {
					;
				}
			} catch (StopException se) {
				throw new SAXException("Unexpected flow exception", se);
			}
		} finally {
			parent.setUserData(MODIFYING_KEY, Boolean.FALSE, null);
		}
	}

	private static final int TOKEN_EOD = 0;
	private static final int TOKEN_COMMENT = 1;
	private static final int TOKEN_TEXT = 2;
	private static final int TOKEN_BEGIN_ELEMENT = 3;
	private static final int TOKEN_END_ELEMENT = 4;
	private static final int TOKEN_FULL_ELEMENT = 5;
	private static final int TOKEN_BAD = 6;

	private String normalLastTag = null;
	private boolean justReadTagBegin = false;
	private boolean justReadTagEnd = false;

	/**
	 * Only set when readAttribute returns false.
	 */
	private boolean justReadEmptyElement = false;

	/**
	 * Parses text followed by one element.
	 * 
	 * @param parent
	 * @param reader
	 * @param stopAtTagUC
	 *            If this tag is encountered, the method throws StopException.
	 * @param stopTags
	 *            If tags in this set are encountered, the method throws
	 *            StopException.
	 * @return
	 * @throws IOException
	 * @throws StopException
	 * @throws SAXException
	 */
	private final int parseToken(Node parent, LineNumberReader reader,
			Set<String> stopTags, LinkedList<String> ancestors) throws IOException,
			StopException, SAXException {
		Document doc = this.document;
		StringBuffer textSb = this.readUpToTagBegin(reader);
		if (textSb == null) {
			return TOKEN_EOD;
		}
		if (textSb.length() != 0) {
			// int textLine = reader.getLineNumber();
			StringBuffer decText = this.entityDecode(textSb);
			Node textNode = doc.createTextNode(decText.toString());
			try {
				parent.appendChild(textNode);
			} catch (DOMException de) {
				if (parent.getNodeType() != Node.DOCUMENT_NODE
						|| de.code != DOMException.HIERARCHY_REQUEST_ERR) {
					logger.log(Level.WARNING,
							"parseToken(): Unable to append child to " + parent
									+ ".", de);
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
						StringBuffer comment = this.passEndOfComment(reader);
						StringBuffer decText = this.entityDecode(comment);
						parent.appendChild(doc.createComment(decText.toString()));
						return TOKEN_COMMENT;
					} else {
						// TODO: DOCTYPE node
						this.passEndOfTag(reader);
						return TOKEN_BAD;
					}
				} else if (tag.startsWith("/")) {
					tag = tag.substring(1);
					normalTag = normalTag.substring(1);
					this.passEndOfTag(reader);
					return TOKEN_END_ELEMENT;
				} else if (tag.startsWith("?")) {
					tag = tag.substring(1);
					StringBuffer data = readProcessingInstruction(reader);
					parent.appendChild(doc.createProcessingInstruction(tag,
							data.toString()));
					return TOKEN_FULL_ELEMENT;
				} else {
					Element element = doc.createElement(tag);
					element.setUserData(MODIFYING_KEY, Boolean.TRUE, null);
					try {
						if (!this.justReadTagEnd) {
							while (this.readAttribute(reader, element)) {
								;
							}
						}
						if (stopTags != null && stopTags.contains(normalTag)) {
							// Throw before appending to parent.
							// After attributes are set.
							// After MODIFYING_KEY is set.
							throw new StopException(element);
						}
						// Add element to parent before children are added.
						// This is necessary for incremental rendering.
						parent.appendChild(element);
						if (!this.justReadEmptyElement) {
							ElementInfo einfo = (ElementInfo) ELEMENT_INFOS
									.get(normalTag);
							int endTagType = einfo == null ? ElementInfo.END_ELEMENT_REQUIRED
									: einfo.endElementType;
							if (endTagType != ElementInfo.END_ELEMENT_FORBIDDEN) {
								boolean childrenOk = einfo == null ? true
										: einfo.childElementOk;
								Set<String> newStopSet = einfo == null ? null
										: einfo.stopTags;
								if (newStopSet == null) {
									if (endTagType == ElementInfo.END_ELEMENT_OPTIONAL) {
										newStopSet = Collections
												.singleton(normalTag);
									}
								}
								if (stopTags != null) {
									if (newStopSet != null) {
										Set<String> newStopSet2 = new HashSet<String>();
										newStopSet2.addAll(stopTags);
										newStopSet2.addAll(newStopSet);
										newStopSet = newStopSet2;
									} else {
										newStopSet = endTagType == ElementInfo.END_ELEMENT_REQUIRED ? null
												: stopTags;
									}
								}
								ancestors.addFirst(normalTag);
								try {
									for (;;) {
										try {
											int token;
											if (einfo != null
													&& einfo.noScriptElement) {
												UserAgentContext ucontext = this.ucontext;
												if (ucontext == null
														|| ucontext
																.isScriptingEnabled()) {
													token = this
															.parseForEndTag(
																	parent,
																	reader,
																	tag,
																	false,
																	einfo.decodeEntities);
												} else {
													token = this.parseToken(
															element, reader,
															newStopSet,
															ancestors);
												}
											} else {
												token = childrenOk ? this
														.parseToken(element,
																reader,
																newStopSet,
																ancestors)
														: this.parseForEndTag(
																element,
																reader,
																tag,
																true,
																einfo.decodeEntities);
											}
											if (token == TOKEN_END_ELEMENT) {
												String normalLastTag = this.normalLastTag;
												if (normalTag
														.equals(normalLastTag)) {
													return TOKEN_FULL_ELEMENT;
												} else {
													ElementInfo closeTagInfo = (ElementInfo) ELEMENT_INFOS
															.get(normalLastTag);
													if (closeTagInfo == null
															|| closeTagInfo.endElementType != ElementInfo.END_ELEMENT_FORBIDDEN) {
														// TODO: Rather
														// inefficient
														// algorithm, but it's
														// probably executed
														// infrequently?
														Iterator<String> i = ancestors
																.iterator();
														if (i.hasNext()) {
															i.next();
															while (i.hasNext()) {
																String normalAncestorTag = (String) i
																		.next();
																if (normalLastTag
																		.equals(normalAncestorTag)) {
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
										} catch (StopException se) {
											// newElement does not have a
											// parent.
											Element newElement = se
													.getElement();
											tag = newElement.getTagName();
											normalTag = tag.toUpperCase();
											// If a subelement throws
											// StopException with
											// a tag matching the current stop
											// tag, the exception
											// is rethrown (e.g.
											// <TR><TD>blah<TR><TD>blah)
											if (stopTags != null
													&& stopTags
															.contains(normalTag)) {
												throw se;
											}
											einfo = (ElementInfo) ELEMENT_INFOS
													.get(normalTag);
											endTagType = einfo == null ? ElementInfo.END_ELEMENT_REQUIRED
													: einfo.endElementType;
											childrenOk = einfo == null ? true
													: einfo.childElementOk;
											newStopSet = einfo == null ? null
													: einfo.stopTags;
											if (newStopSet == null) {
												if (endTagType == ElementInfo.END_ELEMENT_OPTIONAL) {
													newStopSet = Collections
															.singleton(normalTag);
												}
											}
											if (stopTags != null
													&& newStopSet != null) {
												Set<String> newStopSet2 = new HashSet<String>();
												newStopSet2.addAll(stopTags);
												newStopSet2.addAll(newStopSet);
												newStopSet = newStopSet2;
											}
											ancestors.removeFirst();
											ancestors.addFirst(normalTag);
											// Switch element
											element.setUserData(MODIFYING_KEY,
													Boolean.FALSE, null);
											// newElement should have been
											// suspended.
											element = newElement;
											// Add to parent
											parent.appendChild(element);
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
						// This can inform elements to continue with
						// notifications.
						// It can also cause Javascript to get processed.
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
	 * Reads text until the beginning of the next tag. Leaves the reader offset
	 * past the opening angle bracket. Returns null only on EOF.
	 */
	private final StringBuffer readUpToTagBegin(LineNumberReader reader)
			throws IOException, SAXException {
		StringBuffer sb = null;
		int intCh;
		while ((intCh = reader.read()) != -1) {
			char ch = (char) intCh;
			if (ch == '<') {
				this.justReadTagBegin = true;
				this.justReadTagEnd = false;
				this.justReadEmptyElement = false;
				if (sb == null) {
					sb = new StringBuffer(0);
				}
				return sb;
			}
			if (sb == null) {
				sb = new StringBuffer();
			}
			sb.append(ch);
		}
		this.justReadTagBegin = false;
		this.justReadTagEnd = false;
		this.justReadEmptyElement = false;
		return sb;
	}

	/**
	 * Assumes that the content is completely made up of text, and parses until
	 * an ending tag is found.
	 * 
	 * @param parent
	 * @param reader
	 * @param tagName
	 * @return
	 * @throws IOException
	 */
	private final int parseForEndTag(Node parent, LineNumberReader reader,
			String tagName, boolean addTextNode, boolean decodeEntities)
			throws IOException, SAXException {
		Document doc = this.document;
		int intCh;
		StringBuffer sb = new StringBuffer();
		while ((intCh = reader.read()) != -1) {
			char ch = (char) intCh;
			if (ch == '<') {
				intCh = reader.read();
				if (intCh != -1) {
					ch = (char) intCh;
					if (ch == '/') {
						StringBuffer tempBuffer = new StringBuffer();
						INNER: while ((intCh = reader.read()) != -1) {
							ch = (char) intCh;
							if (ch == '>') {
								String thisTag = tempBuffer.toString().trim();
								if (thisTag.equalsIgnoreCase(tagName)) {
									this.justReadTagBegin = false;
									this.justReadTagEnd = true;
									this.justReadEmptyElement = false;
									this.normalLastTag = thisTag.toUpperCase();
									if (addTextNode) {
										if (decodeEntities) {
											sb = this.entityDecode(sb);
										}
										String text = sb.toString();
										if (text.length() != 0) {
											Node textNode = doc
													.createTextNode(text);
											parent.appendChild(textNode);
										}
									}
									return HtmlParser.TOKEN_END_ELEMENT;
								} else {
									break INNER;
								}
							} else {
								tempBuffer.append(ch);
							}
						}
						sb.append("</");
						sb.append(tempBuffer);
					} else {
						sb.append('<');
					}
				}
			}
			sb.append(ch);
		}
		this.justReadTagBegin = false;
		this.justReadTagEnd = false;
		this.justReadEmptyElement = false;
		if (addTextNode) {
			if (decodeEntities) {
				sb = this.entityDecode(sb);
			}
			String text = sb.toString();
			if (text.length() != 0) {
				Node textNode = doc.createTextNode(text);
				parent.appendChild(textNode);
			}
		}
		return HtmlParser.TOKEN_EOD;
	}

	/**
	 * The reader offset should be
	 * 
	 * @param reader
	 * @return
	 */
	private final String readTag(Node parent, LineNumberReader reader)
			throws IOException {
		StringBuffer sb = new StringBuffer();
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
					StringBuffer ltText = new StringBuffer(3);
					ltText.append('<');
					while ((chInt = reader.read()) == '<') {
						ltText.append('<');
					}
					Document doc = this.document;
					Node textNode = doc.createTextNode(ltText.toString());
					try {
						parent.appendChild(textNode);
					} catch (DOMException de) {
						if (parent.getNodeType() != Node.DOCUMENT_NODE
								|| de.code != DOMException.HIERARCHY_REQUEST_ERR) {
							logger.log(Level.WARNING,
									"parseToken(): Unable to append child to "
											+ parent + ".", de);
						}
					}
					if (chInt == -1) {
						cont = false;
					} else {
						continue LOOP;
					}
				} else if (Character.isWhitespace(ch)) {
					StringBuffer ltText = new StringBuffer();
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
					Document doc = this.document;
					Node textNode = doc.createTextNode(ltText.toString());
					try {
						parent.appendChild(textNode);
					} catch (DOMException de) {
						if (parent.getNodeType() != Node.DOCUMENT_NODE
								|| de.code != DOMException.HIERARCHY_REQUEST_ERR) {
							logger.log(Level.WARNING,
									"parseToken(): Unable to append child to "
											+ parent + ".", de);
						}
					}
					if (chInt == -1) {
						cont = false;
					} else {
						continue LOOP;
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
						String tag = sb.toString();
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
		String tag = sb.toString();
		return tag;
	}

	private final StringBuffer passEndOfComment(LineNumberReader reader)
			throws IOException {
		if (this.justReadTagEnd) {
			return new StringBuffer(0);
		}
		StringBuffer sb = new StringBuffer();
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
					StringBuffer extra = null;
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
								extra = new StringBuffer();
								extra.append("--");
							}
							extra.append("-");
						} else if (Character.isWhitespace(ch)) {
							if (extra == null) {
								extra = new StringBuffer();
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

	private final void passEndOfTag(Reader reader) throws IOException {
		if (this.justReadTagEnd) {
			return;
		}
		boolean readSomething = false;
		for (;;) {
			int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			readSomething = true;
			char ch = (char) chInt;
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

	private final StringBuffer readProcessingInstruction(LineNumberReader reader)
			throws IOException {
		StringBuffer pidata = new StringBuffer();
		if (this.justReadTagEnd) {
			return pidata;
		}
		int ch;
		for (ch = reader.read(); ch != -1 && ch != '>'; ch = reader.read()) {
			pidata.append((char) ch);
		}
		this.justReadTagBegin = false;
		this.justReadTagEnd = ch != -1;
		return pidata;
	}

	private final boolean readAttribute(LineNumberReader reader, Element element)
			throws IOException, SAXException {
		if (this.justReadTagEnd) {
			return false;
		}

		// Read attribute name up to '=' character.
		// May read several attribute names without explicit values.

		StringBuffer attributeName = null;
		boolean blankFound = false;
		boolean lastCharSlash = false;
		for (;;) {
			int chInt = reader.read();
			if (chInt == -1) {
				if (attributeName != null && attributeName.length() != 0) {
					String attributeNameStr = attributeName.toString();
					element.setAttribute(attributeNameStr, attributeNameStr);
					attributeName.setLength(0);
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = false;
				this.justReadEmptyElement = false;
				return false;
			}
			char ch = (char) chInt;
			if (ch == '=') {
				lastCharSlash = false;
				blankFound = false;
				break;
			} else if (ch == '>') {
				if (attributeName != null && attributeName.length() != 0) {
					String attributeNameStr = attributeName.toString();
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
					if (attributeName != null && attributeName.length() != 0) {
						String attributeNameStr = attributeName.toString();
						element.setAttribute(attributeNameStr, attributeNameStr);
						attributeName.setLength(0);
					}
				}
				if (attributeName == null) {
					attributeName = new StringBuffer(6);
				}
				attributeName.append(ch);
			}
		}
		// Read blanks up to open quote or first non-blank.
		StringBuffer attributeValue = null;
		int openQuote = -1;
		for (;;) {
			int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			char ch = (char) chInt;
			if (ch == '>') {
				if (attributeName != null && attributeName.length() != 0) {
					String attributeNameStr = attributeName.toString();
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
					if (attributeValue == null) {
						attributeValue = new StringBuffer(6);
					}
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
			int chInt = reader.read();
			if (chInt == -1) {
				break;
			}
			char ch = (char) chInt;
			if (openQuote != -1 && ch == openQuote) {
				lastCharSlash = false;
				if (attributeName != null) {
					String attributeNameStr = attributeName.toString();
					if (attributeValue == null) {
						// Quotes are closed. There's a distinction
						// between blank values and null in HTML, as
						// processed by major browsers.
						element.setAttribute(attributeNameStr, "");
					} else {
						StringBuffer actualAttributeValue = this
								.entityDecode(attributeValue);
						element.setAttribute(attributeNameStr,
								actualAttributeValue.toString());
					}
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = false;
				return true;
			} else if (openQuote == -1 && ch == '>') {
				if (attributeName != null) {
					String attributeNameStr = attributeName.toString();
					if (attributeValue == null) {
						element.setAttribute(attributeNameStr, null);
					} else {
						StringBuffer actualAttributeValue = this
								.entityDecode(attributeValue);
						element.setAttribute(attributeNameStr,
								actualAttributeValue.toString());
					}
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = true;
				this.justReadEmptyElement = lastCharSlash;
				return false;
			} else if (openQuote == -1 && Character.isWhitespace(ch)) {
				lastCharSlash = false;
				if (attributeName != null) {
					String attributeNameStr = attributeName.toString();
					if (attributeValue == null) {
						element.setAttribute(attributeNameStr, null);
					} else {
						StringBuffer actualAttributeValue = this
								.entityDecode(attributeValue);
						element.setAttribute(attributeNameStr,
								actualAttributeValue.toString());
					}
				}
				this.justReadTagBegin = false;
				this.justReadTagEnd = false;
				return true;
			} else {
				if (attributeValue == null) {
					attributeValue = new StringBuffer(6);
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
			String attributeNameStr = attributeName.toString();
			if (attributeValue == null) {
				element.setAttribute(attributeNameStr, null);
			} else {
				StringBuffer actualAttributeValue = this
						.entityDecode(attributeValue);
				element.setAttribute(attributeNameStr,
						actualAttributeValue.toString());
			}
		}
		return false;
	}

	private final StringBuffer entityDecode(StringBuffer rawText)
			throws org.xml.sax.SAXException {
		int startIdx = 0;
		StringBuffer sb = null;
		for (;;) {
			int ampIdx = rawText.indexOf("&", startIdx);
			if (ampIdx == -1) {
				if (sb == null) {
					return rawText;
				} else {
					sb.append(rawText.substring(startIdx));
					return sb;
				}
			}
			if (sb == null) {
				sb = new StringBuffer();
			}
			sb.append(rawText.substring(startIdx, ampIdx));
			int colonIdx = rawText.indexOf(";", ampIdx);
			if (colonIdx == -1) {
				sb.append('&');
				startIdx = ampIdx + 1;
				continue;
			}
			String spec = rawText.substring(ampIdx + 1, colonIdx);
			if (spec.startsWith("#")) {
				String number = spec.substring(1).toLowerCase();
				int decimal;
				try {
					if (number.startsWith("x")) {
						decimal = Integer.parseInt(number.substring(1), 16);
					} else {
						decimal = Integer.parseInt(number);
					}
				} catch (NumberFormatException nfe) {
					logger.log(Level.WARNING, "entityDecode()", nfe);
					decimal = 0;
				}
				sb.append((char) decimal);
			} else {
				int chInt = this.getEntityChar(spec);
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

	/*private final Locator getLocator(int lineNumber, int columnNumber) {
		return new LocatorImpl(this.publicId, this.systemId, lineNumber,
				columnNumber);
	}*/

	private final int getEntityChar(String spec) {
		// TODO: Declared entities
		Character c = (Character) ENTITIES.get(spec);
		if (c == null) {
			String specTL = spec.toLowerCase();
			c = (Character) ENTITIES.get(specTL);
			if (c == null) {
				return -1;
			}
		}
		return (int) c.charValue();
	}
}
