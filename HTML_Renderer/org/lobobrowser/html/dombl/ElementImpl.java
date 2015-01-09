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
 * Created on Oct 29, 2005
 */
package org.lobobrowser.html.dombl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.w3c.HTMLMenuElement;
import org.lobobrowser.util.Objects;
import org.lobobrowser.util.Strings;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

public class ElementImpl extends NodeImpl implements Element {
	private final String name;
	private String id;

	public ElementImpl(String name) {
		super();
		this.name = name;
	}

	protected Map<String, String> attributes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.NodeImpl#getattributes()
	 */
	public NamedNodeMap getAttributes() {
		synchronized (this) {
			Map<String, String> attrs = this.attributes;
			if (attrs == null) {
				attrs = new HashMap<String, String>();
				this.attributes = attrs;
			}
			return new NamedNodeMapImpl(this, this.attributes);
		}
	}

	public boolean hasAttributes() {
		synchronized (this) {
			Map<String, String> attrs = this.attributes;
			return attrs == null ? false : !attrs.isEmpty();
		}
	}

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

	public String getId() {
		String id = this.id;
		return id == null ? "" : id;
	}

	public void setId(String id) {
		this.setAttribute(HtmlAttributeProperties.ID, id);
	}

	public String getTitle() {
		return this.getAttribute(HtmlAttributeProperties.TITLE);
	}

	public void setTitle(String title) {
		this.setAttribute(HtmlAttributeProperties.TITLE, title);
	}

	public String getLang() {
		return this.getAttribute(HtmlAttributeProperties.LANG);
	}

	public void setLang(String lang) {
		this.setAttribute(HtmlAttributeProperties.LANG, lang);
	}

	public String getDir() {
		return this.getAttribute(HtmlAttributeProperties.DIR);
	}

	public void setDir(String dir) {
		this.setAttribute(HtmlAttributeProperties.DIR, dir);
	}
	
	public boolean getHidden() {
		return this.getAttribute(HtmlAttributeProperties.HIDDEN) == null ? true : false;
	}
	
	public String getContentEditable() {
		return this.getAttribute(HtmlAttributeProperties.CONTENTEDITABLE);
	}

	public void setContentEditable(String contenteditable) {
		this.setAttribute(HtmlAttributeProperties.CONTENTEDITABLE, contenteditable);
	}
	
	public String getSpellcheck() {
		return this.getAttribute(HtmlAttributeProperties.SPELLCHECK);
	}

	public void setSpellcheck(String spellcheck) {
		this.setAttribute(HtmlAttributeProperties.SPELLCHECK, spellcheck);
	}
	
	public boolean getDraggable() {
		String draggable = this.getAttribute(HtmlAttributeProperties.DRAGGABLE);
		if (draggable == null)
			return false;
		else
			return new Boolean(draggable);
	}

	public void setDraggable(boolean draggable) {
		this.setAttribute(HtmlAttributeProperties.DRAGGABLE, String.valueOf(draggable));
	}
	
	public boolean getIsContentEditable() {
		String content = getAttribute(HtmlAttributeProperties.CONTENTEDITABLE);
		if (content == null)
			return false;
		else
			return new Boolean(content);
	}
	
	public boolean getDisabled() {
		return this.getAttribute(HtmlAttributeProperties.DISABLE) == null ? true : false;
	}

	public boolean getChecked() {
		return this.getAttribute(HtmlAttributeProperties.CHECKED) == null ? true : false;
	}
	
	public boolean getItemScope() {
		String itemscope = this.getAttribute(HtmlAttributeProperties.ITEMSCOPE);
		if (itemscope == null)
			return false;
		else
			return new Boolean(itemscope);
	}

	public void setItemScope(boolean itemscope) {
		this.setAttribute(HtmlAttributeProperties.ITEMSCOPE, String.valueOf(itemscope));
		
	}
	
	public String getItemType() {
		return this.getAttribute(HtmlAttributeProperties.ITEMTYPE);
	}

	public void setItemType(String itemType) {
		this.setAttribute(HtmlAttributeProperties.ITEMTYPE,itemType);
		
	}
	
	public String getItemId() {
		return this.getAttribute(HtmlAttributeProperties.ITEMID);
	}

	public void setItemId(String itemId) {
		this.setAttribute(HtmlAttributeProperties.ITEMID,itemId);
	}
	
	public int getTabIndex() {
		try {
			return Integer.parseInt(this
					.getAttribute(HtmlAttributeProperties.TABINDEX));
		} catch (Exception err) {
			return 0;
		}
	}

	
	public void setTabIndex(int tabIndex) {
		this.setAttribute(HtmlAttributeProperties.TABINDEX,String.valueOf(tabIndex));
	}

	
	public String getAccessKey() {
		return this.getAttribute(HtmlAttributeProperties.ACCESSKEY);
	}

	
	public void setAccessKey(String accessKey) {
		this.setAttribute(HtmlAttributeProperties.ACCESSKEY,accessKey);
		
	}

	
	public HTMLMenuElement getContextMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setContextMenu(HTMLMenuElement contextMenu) {
		// TODO Auto-generated method stub
		
	}
	
	public final String getAttribute(String name) {
		String normalName = this.normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			return attributes == null ? null : (String) attributes.get(normalName);
		}
	}

	private Attr getAttr(String normalName, String value) {
		// TODO: "specified" attributes
		return new AttrImpl(normalName, value, true, this,
				HtmlAttributeProperties.ID.equals(normalName));
	}

	public Attr getAttributeNode(String name) {
		String normalName = this.normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			String value = attributes == null ? null : (String) attributes
					.get(normalName);
			return value == null ? null : this.getAttr(normalName, value);
		}
	}

	public Attr getAttributeNodeNS(String namespaceURI, String localName)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	public String getAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	protected static boolean isTagName(Node node, String name) {
		return node.getNodeName().equalsIgnoreCase(name);
	}

	public NodeList getElementsByTagName(String name) {
		boolean matchesAll = "*".equals(name);
		List<Object> descendents = new LinkedList<Object>();
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				Iterator<Node> i = nl.iterator();
				while (i.hasNext()) {
					Object child = i.next();
					if (child instanceof Element) {
						Element childElement = (Element) child;
						if (matchesAll || isTagName(childElement, name)) {
							descendents.add(child);
						}
						NodeList sublist = childElement
								.getElementsByTagName(name);
						int length = sublist.getLength();
						for (int idx = 0; idx < length; idx++) {
							descendents.add(sublist.item(idx));
						}
					}
				}
			}
		}
		return new NodeListImpl(descendents);
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	public String getTagName() {
		return this.getNodeName();
	}

	public boolean hasAttribute(String name) {
		String normalName = this.normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			return attributes == null ? false : attributes
					.containsKey(normalName);
		}
	}

	public boolean hasAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	public void removeAttribute(String name) throws DOMException {
		String normalName = this.normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			if (attributes == null) {
				return;
			}
			attributes.remove(normalName);
		}
	}

	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		String normalName = this.normalizeAttributeName(oldAttr.getName());
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			if (attributes == null) {
				return null;
			}
			String oldValue = (String) attributes.remove(normalName);
			// TODO: "specified" attributes
			return oldValue == null ? null : this.getAttr(normalName, oldValue);
		}
	}

	public void removeAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	protected void assignAttributeField(String normalName, String value) {
		// Note: overriders assume that processing here is only done after
		// checking attribute names, i.e. they may not call the super
		// implementation if an attribute is already taken care of.
		boolean isName = false;
		if (HtmlAttributeProperties.ID.equals(normalName) || (isName = HtmlAttributeProperties.NAME.equals(normalName))) {
			// Note that the value of name is used
			// as an ID, but the value of ID is not
			// used as a name.
			if (!isName) {
				this.id = value;
			}
			HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
			if (document != null) {
				// // Do not remove old ID. Consider scenario where both
				// // name and ID are provided in an element.
				// if (oldId != null) {
				// document.removeElementById(oldId);
				// }
				document.setElementById(value, this);
				if (isName) {
					String oldName = this.getAttribute(HtmlAttributeProperties.NAME);
					if (oldName != null) {
						document.removeNamedItem(oldName);
					}
					document.setNamedItem(value, this);
				}
			}
		}
	}

	protected final String normalizeAttributeName(String name) {
		return name.toLowerCase();
	}

	public void setAttribute(String name, String value) throws DOMException {
		String normalName = this.normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attribs = this.attributes;
			if (attribs == null) {
				attribs = new HashMap<String, String>(2);
				this.attributes = attribs;
			}
			attribs.put(normalName, value);
		}
		this.assignAttributeField(normalName, value);
	}

	/**
	 * Fast method to set attributes. It is not thread safe. Calling thread
	 * should hold a treeLock.
	 */
	public void setAttributeImpl(String name, String value) throws DOMException {
		String normalName = this.normalizeAttributeName(name);
		Map<String, String> attribs = this.attributes;
		if (attribs == null) {
			attribs = new HashMap<String, String>(2);
			this.attributes = attribs;
		}
		this.assignAttributeField(normalName, value);
		attribs.put(normalName, value);
	}

	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		String normalName = this.normalizeAttributeName(newAttr.getName());
		String value = newAttr.getValue();
		synchronized (this) {
			if (this.attributes == null) {
				this.attributes = new HashMap<String, String>();
			}
			this.attributes.put(normalName, value);
			// this.setIdAttribute(normalName, newAttr.isId());
		}
		this.assignAttributeField(normalName, value);
		return newAttr;
	}

	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	public void setAttributeNS(String namespaceURI, String qualifiedName,
			String value) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	public void setIdAttribute(String name, boolean isId) throws DOMException {
		String normalName = this.normalizeAttributeName(name);
		if (!HtmlAttributeProperties.ID.equals(normalName)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"IdAttribute can't be anything other than ID");
		}
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId)
			throws DOMException {
		String normalName = this.normalizeAttributeName(idAttr.getName());
		if (!HtmlAttributeProperties.ID.equals(normalName)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"IdAttribute can't be anything other than ID");
		}
	}

	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.NodeImpl#getLocalName()
	 */
	public String getLocalName() {
		return this.getNodeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.NodeImpl#getNodeName()
	 */
	public String getNodeName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.NodeImpl#getNodeType()
	 */
	public short getNodeType() {
		return Node.ELEMENT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.NodeImpl#getNodeValue()
	 */
	public String getNodeValue() throws DOMException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.NodeImpl#setNodeValue(java.lang.String)
	 */
	public void setNodeValue(String nodeValue) throws DOMException {
		// nop
	}

	/**
	 * Gets inner text of the element, possibly including text in comments. This
	 * can be used to get Javascript code out of a SCRIPT element.
	 * 
	 * @param includeComment
	 */
	protected String getRawInnerText(boolean includeComment) {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				Iterator<Node> i = nl.iterator();
				StringBuffer sb = null;
				while (i.hasNext()) {
					Object node = i.next();
					if (node instanceof Text) {
						Text tn = (Text) node;
						String txt = tn.getNodeValue();
						if (!"".equals(txt)) {
							if (sb == null) {
								sb = new StringBuffer();
							}
							sb.append(txt);
						}
					} else if (node instanceof ElementImpl) {
						ElementImpl en = (ElementImpl) node;
						String txt = en.getRawInnerText(includeComment);
						if (!"".equals(txt)) {
							if (sb == null) {
								sb = new StringBuffer();
							}
							sb.append(txt);
						}
					} else if (includeComment && node instanceof Comment) {
						Comment cn = (Comment) node;
						String txt = cn.getNodeValue();
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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getNodeName());
		sb.append(" [");
		NamedNodeMap attribs = this.getAttributes();
		int length = attribs.getLength();
		for (int i = 0; i < length; i++) {
			Attr attr = (Attr) attribs.item(i);
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

	public void setInnerText(String newText) {
		org.w3c.dom.Document document = this.document;
		if (document == null) {
			this.warn("setInnerText(): Element " + this
					+ " does not belong to a document.");
			return;
		}
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				nl.clear();
			}
		}
		// Create node and call appendChild outside of synchronized block.
		Node textNode = document.createTextNode(newText);
		this.appendChild(textNode);
	}

	protected Node createSimilarNode() {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return doc == null ? null : doc.createElement(this.getTagName());
	}

	protected String htmlEncodeChildText(String text) {
		if (org.lobobrowser.html.parser.HtmlParser.isDecodeEntities(this.name)) {
			return Strings.strictHtmlEncode(text, false);
		} else {
			return text;
		}
	}

}
