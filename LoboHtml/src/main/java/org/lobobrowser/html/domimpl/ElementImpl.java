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
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lobo.common.Strings;
import org.lobobrowser.util.Objects;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

public class ElementImpl extends DOMFunctionImpl implements Element {
	protected static boolean isTagName(Node node, String name) {
		return node.getNodeName().equalsIgnoreCase(name);
	}

	protected Map attributes;

	private String id;

	private final String name;

	public ElementImpl(String name) {
		super();
		this.name = name;
	}

	protected void assignAttributeField(String normalName, String value) {
		// Note: overriders assume that processing here is only done after
		// checking attribute names, i.e. they may not call the super
		// implementation if an attribute is already taken care of.
		boolean isName = false;
		if ("id".equals(normalName) || (isName = "name".equals(normalName))) {
			// Note that the value of name is used
			// as an ID, but the value of ID is not
			// used as a name.
			if (!isName) {
				this.id = value;
			}
			final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
			if (document != null) {
				//// Do not remove old ID. Consider scenario where both
				//// name and ID are provided in an element.
//				if (oldId != null) {
//					document.removeElementById(oldId);
//				}
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

	@Override
	protected Node createSimilarNode() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return doc == null ? null : doc.createElement(getTagName());
	}

	@Override
	public boolean equalAttributes(Node arg) {
		if (arg instanceof ElementImpl) {
			synchronized (this) {
				Map attrs1 = this.attributes;
				if (attrs1 == null) {
					attrs1 = Collections.EMPTY_MAP;
				}
				Map attrs2 = ((ElementImpl) arg).attributes;
				if (attrs2 == null) {
					attrs2 = Collections.EMPTY_MAP;
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

	@Override
	public final String getAttribute(String name) {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			final Map attributes = this.attributes;
			return attributes == null ? null : (String) attributes.get(normalName);
		}
	}

	@Override
	public Attr getAttributeNode(String name) {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			final Map attributes = this.attributes;
			final String value = attributes == null ? null : (String) attributes.get(normalName);
			return value == null ? null : getAttr(normalName, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getattributes()
	 */
	@Override
	public NamedNodeMap getAttributes() {
		synchronized (this) {
			Map attrs = this.attributes;
			if (attrs == null) {
				attrs = new HashMap();
				this.attributes = attrs;
			}
			return new NamedNodeMapImpl(this, this.attributes);
		}
	}

	public String getDir() {
		return getAttribute("dir");
	}

	@Override
	public NodeList getElementsByTagName(String name) {
		final boolean matchesAll = "*".equals(name);
		final List descendents = new LinkedList();
		synchronized (this.treeLock) {
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				final Iterator i = nl.iterator();
				while (i.hasNext()) {
					final Object child = i.next();
					if (child instanceof Element) {
						final Element childElement = (Element) child;
						if (matchesAll || isTagName(childElement, name)) {
							descendents.add(child);
						}
						final NodeList sublist = childElement.getElementsByTagName(name);
						final int length = sublist.getLength();
						for (int idx = 0; idx < length; idx++) {
							descendents.add(sublist.item(idx));
						}
					}
				}
			}
		}
		return new NodeListImpl(descendents);
	}

	public String getId() {
		final String id = this.id;
		return id == null ? "" : id;
	}

	public String getLang() {
		return getAttribute("lang");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getLocalName()
	 */
	@Override
	public String getLocalName() {
		return getNodeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getNodeType()
	 */
	@Override
	public short getNodeType() {
		return Node.ELEMENT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#getNodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	/**
	 * Gets inner text of the element, possibly including text in comments. This can
	 * be used to get Javascript code out of a SCRIPT element.
	 * 
	 * @param includeComment
	 */
	protected String getRawInnerText(boolean includeComment) {
		synchronized (this.treeLock) {
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				final Iterator i = nl.iterator();
				StringBuffer sb = null;
				while (i.hasNext()) {
					final Object node = i.next();
					if (node instanceof Text) {
						final Text tn = (Text) node;
						final String txt = tn.getNodeValue();
						if (!"".equals(txt)) {
							if (sb == null) {
								sb = new StringBuffer();
							}
							sb.append(txt);
						}
					} else if (node instanceof ElementImpl) {
						final ElementImpl en = (ElementImpl) node;
						final String txt = en.getRawInnerText(includeComment);
						if (!"".equals(txt)) {
							if (sb == null) {
								sb = new StringBuffer();
							}
							sb.append(txt);
						}
					} else if (includeComment && node instanceof Comment) {
						final Comment cn = (Comment) node;
						final String txt = cn.getNodeValue();
						if (!"".equals(txt)) {
							if (sb == null) {
								sb = new StringBuffer();
							}
							sb.append(txt);
						}
					}
				}
				return sb == null ? "" : sb.toString();
			} else {
				return "";
			}
		}
	}

	@Override
	public String getTagName() {
		return getNodeName();
	}

	public String getTitle() {
		return getAttribute("title");
	}

	@Override
	public boolean hasAttribute(String name) {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			final Map attributes = this.attributes;
			return attributes == null ? false : attributes.containsKey(normalName);
		}
	}

	@Override
	public boolean hasAttributes() {
		synchronized (this) {
			final Map attrs = this.attributes;
			return attrs == null ? false : !attrs.isEmpty();
		}
	}

	@Override
	protected String htmlEncodeChildText(String text) {
		if (org.lobobrowser.html.parser.HtmlParser.isDecodeEntities(this.name)) {
			return Strings.strictHtmlEncode(text, false);
		} else {
			return text;
		}
	}

	protected final String normalizeAttributeName(String name) {
		return name.toLowerCase();
	}

	@Override
	public void removeAttribute(String name) throws DOMException {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			final Map attributes = this.attributes;
			if (attributes == null) {
				return;
			}
			attributes.remove(normalName);
		}
	}

	@Override
	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		final String normalName = normalizeAttributeName(oldAttr.getName());
		synchronized (this) {
			final Map attributes = this.attributes;
			if (attributes == null) {
				return null;
			}
			final String oldValue = (String) attributes.remove(normalName);
			// TODO: "specified" attributes
			return oldValue == null ? null : getAttr(normalName, oldValue);
		}
	}

	@Override
	public void setAttribute(String name, String value) throws DOMException {
		final String normalName = normalizeAttributeName(name);
		synchronized (this) {
			Map attribs = this.attributes;
			if (attribs == null) {
				attribs = new HashMap(2);
				this.attributes = attribs;
			}
			attribs.put(normalName, value);
		}
		assignAttributeField(normalName, value);
	}

	/**
	 * Fast method to set attributes. It is not thread safe. Calling thread should
	 * hold a treeLock.
	 */
	public void setAttributeImpl(String name, String value) throws DOMException {
		final String normalName = normalizeAttributeName(name);
		Map attribs = this.attributes;
		if (attribs == null) {
			attribs = new HashMap(2);
			this.attributes = attribs;
		}
		assignAttributeField(normalName, value);
		attribs.put(normalName, value);
	}

	@Override
	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		final String normalName = normalizeAttributeName(newAttr.getName());
		final String value = newAttr.getValue();
		synchronized (this) {
			if (this.attributes == null) {
				this.attributes = new HashMap();
			}
			this.attributes.put(normalName, value);
			// this.setIdAttribute(normalName, newAttr.isId());
		}
		assignAttributeField(normalName, value);
		return newAttr;
	}

	public void setDir(String dir) {
		setAttribute("dir", dir);
	}

	public void setId(String id) {
		setAttribute("id", id);
	}

	@Override
	public void setIdAttribute(String name, boolean isId) throws DOMException {
		final String normalName = normalizeAttributeName(name);
		if (!"id".equals(normalName)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "IdAttribute can't be anything other than ID");
		}
	}

	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
		final String normalName = normalizeAttributeName(idAttr.getName());
		if (!"id".equals(normalName)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "IdAttribute can't be anything other than ID");
		}
	}

	public void setInnerText(String newText) {
		final org.w3c.dom.Document document = this.document;
		if (document == null) {
			this.warn("setInnerText(): Element " + this + " does not belong to a document.");
			return;
		}
		synchronized (this.treeLock) {
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				nl.clear();
			}
		}
		// Create node and call appendChild outside of synchronized block.
		final Node textNode = document.createTextNode(newText);
		appendChild(textNode);
	}

	public void setLang(String lang) {
		setAttribute("lang", lang);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.NodeImpl#setNodeValue(java.lang.String)
	 */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		// nop
	}

	public void setTitle(String title) {
		setAttribute("title", title);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
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

}
