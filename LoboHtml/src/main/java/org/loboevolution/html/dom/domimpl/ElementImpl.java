/*
 GNU LESSER GENERAL PUBLIC LICENSE
 Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
 * Created on Oct 29, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.parser.HtmlParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * <p>ElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ElementImpl extends DOMFunctionImpl implements Element {

	/**
	 * <p>isTagName.</p>
	 *
	 * @param node a {@link org.w3c.dom.Node} object.
	 * @param name a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	protected static boolean isTagName(Node node, String name) {
		return node.getNodeName().equalsIgnoreCase(name);
	}

	protected Map<String, String> attributes;

	private String id;

	private final String name;

	/**
	 * <p>Constructor for ElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public ElementImpl(String name) {
		this.name = name;
	}

	/**
	 * <p>assignAttributeField.</p>
	 *
	 * @param normalName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	protected void assignAttributeField(String normalName, String value) {
		boolean isName = false;
		if ("id".equals(normalName) || (isName = "name".equals(normalName))) {
			if (!isName) {
				this.id = value;
			}
			final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
			if (document != null) {
				document.setElementById(value, this);
				if (isName) {
					final String oldName = getAttribute("name");
					if (oldName != null) {
						document.removeNamedItem(oldName);
					}
					document.setNamedItem(value, this);
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected Node createSimilarNode() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return doc == null ? null : doc.createElement(getTagName());
	}

	/** {@inheritDoc} */
	@Override
	public boolean equalAttributes(Node arg) {
		if (arg instanceof ElementImpl) {
			synchronized (this) {
				Map<String, String> attrs1 = this.attributes;
				if (attrs1 == null) {
					attrs1 = Collections.emptyMap();
				}
				Map<String, String> attrs2 = ((ElementImpl) arg).attributes;
				if (attrs2 == null) {
					attrs2 = Collections.emptyMap();
				}
				return Objects.equals(attrs1, attrs2);
			}
		} else {
			return false;
		}
	}

	private Attr getAttr(String normalName, String value) {
		// TODO: "specified" attributes
		return new AttrImpl(normalName, value, true, this, "id".equals(normalName));
	}

	// private String title;

	/** {@inheritDoc} */
	@Override
	public String getAttribute(String name) {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			final Map<String, String> attributes = this.attributes;
			return attributes == null ? null : attributes.get(normalName);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Attr getAttributeNode(String name) {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			final Map<String, String> attributes = this.attributes;
			final String value = attributes == null ? null : attributes.get(normalName);
			return value == null ? null : getAttr(normalName, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getattributes()
	 */
	/** {@inheritDoc} */
	@Override
	public NamedNodeMap getAttributes() {
		synchronized (this) {
			Map<String, String> attrs = this.attributes;
			if (attrs == null) {
				attrs = new HashMap<>();
				this.attributes = attrs;
			}
			return new NamedNodeMapImpl(this, this.attributes);
		}
	}

	/**
	 * <p>getDir.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDir() {
		return getAttribute("dir");
	}

	/**
	 * <p>Getter for the field id.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getId() {
		final String id = this.id;
		return id == null ? "" : id;
	}

	/**
	 * <p>getLang.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLang() {
		return getAttribute("lang");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getLocalName()
	 */
	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return getNodeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getNodeName()
	 */
	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getNodeType()
	 */
	/** {@inheritDoc} */
	@Override
	public short getNodeType() {
		return Node.ELEMENT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getNodeValue()
	 */
	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	/**
	 * Gets inner text of the element, possibly including text in comments. This can
	 * be used to get Javascript code out of a SCRIPT element.
	 *
	 * @param includeComment a boolean.
	 * @return a {@link java.lang.String} object.
	 */
	protected String getRawInnerText(boolean includeComment) {
		StringBuilder sb = null;
		for (Node node : Nodes.iterable(nodeList)) {
			if (node instanceof Text) {
				final Text tn = (Text) node;
				final String txt = tn.getNodeValue();
				if (Strings.isNotBlank(txt)) {
					if (sb == null) {
						sb = new StringBuilder();
					}
					sb.append(txt);
				}
			} else if (node instanceof ElementImpl) {
				final ElementImpl en = (ElementImpl) node;
				final String txt = en.getRawInnerText(includeComment);
				if (Strings.isNotBlank(txt)) {
					if (sb == null) {
						sb = new StringBuilder();
					}
					sb.append(txt);
				}
			} else if (includeComment && node instanceof Comment) {
				final Comment cn = (Comment) node;
				final String txt = cn.getNodeValue();
				if (Strings.isNotBlank(txt)) {
					if (sb == null) {
						sb = new StringBuilder();
					}
					sb.append(txt);
				}
			}
		}
		return sb == null ? "" : sb.toString();

	}

	/** {@inheritDoc} */
	@Override
	public String getTagName() {
		return getNodeName();
	}

	/**
	 * <p>getTitle.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTitle() {
		return getAttribute("title");
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasAttribute(String name) {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			final Map<String, String> attributes = this.attributes;
			return attributes != null && attributes.containsKey(normalName);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasAttributes() {
		synchronized (this) {
			final Map<String, String> attrs = this.attributes;
			return attrs != null && !attrs.isEmpty();
		}
	}

	/** {@inheritDoc} */
	@Override
	protected String htmlEncodeChildText(String text) {
		if (HtmlParser.isDecodeEntities(this.name)) {
			return Strings.strictHtmlEncode(text, false);
		} else {
			return text;
		}
	}

	/**
	 * <p>normalizeAttributeName.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	protected final String normalizeAttributeName(String name) {
		return name.toLowerCase();
	}

	/** {@inheritDoc} */
	@Override
	public void removeAttribute(String name) throws DOMException {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			final Map<String, String> attributes = this.attributes;
			if (attributes == null) {
				return;
			}
			attributes.remove(normalName);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		final String normalName = normalizeAttributeName(oldAttr.getName());
		synchronized (this) {
			final Map<String, String> attributes = this.attributes;
			if (attributes == null) {
				return null;
			}
			final String oldValue = attributes.remove(normalName);
			// TODO: "specified" attributes
			return oldValue == null ? null : getAttr(normalName, oldValue);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setAttribute(String name, String value) throws DOMException {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attribs = this.attributes;
			if (attribs == null) {
				attribs = new HashMap<>(2);
				this.attributes = attribs;
			}
			attribs.put(normalName, value);
		}
		assignAttributeField(normalName, value);
	}

	/**
	 * Fast method to set attributes. It is not thread safe. Calling thread should
	 * hold a treeLock.
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setAttributeImpl(String name, String value) throws DOMException {
		final String normalName = normalizeAttributeName(name);
		Map<String, String> attribs = this.attributes;
		if (attribs == null) {
			attribs = new HashMap<>(2);
			this.attributes = attribs;
		}
		assignAttributeField(normalName, value);
		attribs.put(normalName, value);
	}

	/** {@inheritDoc} */
	@Override
	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		final String normalName = normalizeAttributeName(newAttr.getName());
		final String value = newAttr.getValue();
		synchronized (this) {
			if (this.attributes == null) {
				this.attributes = new HashMap<>();
			}
			this.attributes.put(normalName, value);
			// this.setIdAttribute(normalName, newAttr.isId());
		}
		assignAttributeField(normalName, value);
		return newAttr;
	}

	/**
	 * <p>setDir.</p>
	 *
	 * @param dir a {@link java.lang.String} object.
	 */
	public void setDir(String dir) {
		setAttribute("dir", dir);
	}

	/**
	 * <p>Setter for the field id.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 */
	public void setId(String id) {
		setAttribute("id", id);
	}

	/** {@inheritDoc} */
	@Override
	public void setIdAttribute(String name, boolean isId) throws DOMException {
		final String normalName = normalizeAttributeName(name);
		if (!"id".equals(normalName)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "IdAttribute can't be anything other than ID");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
		final String normalName = normalizeAttributeName(idAttr.getName());
		if (!"id".equals(normalName)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "IdAttribute can't be anything other than ID");
		}
	}

	/**
	 * <p>setInnerText.</p>
	 *
	 * @param newText a {@link java.lang.String} object.
	 */
	public void setInnerText(String newText) {
		final Document document = this.document;
		if (document == null) {
			this.warn("setInnerText(): Element " + this + " does not belong to a document.");
			return;
		}
		this.nodeList.clear();
		final Node textNode = document.createTextNode(newText);
		appendChild(textNode);
	}

	/**
	 * <p>setLang.</p>
	 *
	 * @param lang a {@link java.lang.String} object.
	 */
	public void setLang(String lang) {
		setAttribute("lang", lang);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#setNodeValue(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		// nop
	}

	/**
	 * <p>setTitle.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	public void setTitle(String title) {
		setAttribute("title", title);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getNodeName());
		sb.append(" [");
		final NamedNodeMap attribs = getAttributes();
		final int length = attribs.getLength();
		for (int i = 0; i < length; i++) {
			final Attr attr = (Attr) attribs.item(i);
			sb.append(attr.getNodeName());
			sb.append('=');
			sb.append(attr.getNodeValue());
			if (i + 1 < length) {
				sb.append(',');
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * <p>getFirstElementChild.</p>
	 *
	 * @return a {@link org.w3c.dom.Element} object.
	 */
	public Element getFirstElementChild() {
		for (Node n : Nodes.iterable(nodeList)) {
			if (n instanceof Element) {
				return (Element) n;
			}
		}
		return null;
	}

	/**
	 * <p>getLastElementChild.</p>
	 *
	 * @return a {@link org.w3c.dom.Element} object.
	 */
	public Element getLastElementChild() {
		final int N = this.nodeList.getLength();
		for (int i = N - 1; i >= 0; i--) {
			final Node n = this.nodeList.get(i);
			if (n instanceof Element) {
				return (Element) n;
			}
		}

		return null;
	}

	/**
	 * <p>getChildElementCount.</p>
	 *
	 * @return a int.
	 */
	public int getChildElementCount() {
		int count = 0;
		for (Node n : Nodes.iterable(nodeList)) {
			if (n instanceof Element) {
				count++;
			}
		}

		return count;
	}

}
