/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

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

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.util.Objects;
import org.lobobrowser.util.Strings;
import org.lobobrowser.w3c.html.HTMLMenuElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

/**
 * The Class DOMElementImpl.
 */
public class DOMElementImpl extends DOMNodeImpl implements Element {

	/** The name. */
	private final String name;

	/** The id. */
	private String id;

	/**
	 * Instantiates a new DOM element impl.
	 *
	 * @param name
	 *            the name
	 */
	public DOMElementImpl(String name) {
		super();
		this.name = name;
	}

	/** The attributes. */
	protected Map<String, String> attributes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getattributes()
	 */
	@Override
	public NamedNodeMap getAttributes() {
		synchronized (this) {
			Map<String, String> attrs = this.attributes;
			if (attrs == null) {
				attrs = new HashMap<String, String>();
				this.attributes = attrs;
			}
			return new DOMAttrMapImpl(this, this.attributes);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#hasAttributes()
	 */
	@Override
	public boolean hasAttributes() {
		synchronized (this) {
			Map<String, String> attrs = this.attributes;
			return attrs == null ? false : !attrs.isEmpty();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.DOMNodeImpl#equalAttributes(org.w3c.dom.
	 * Node)
	 */
	@Override
	public boolean equalAttributes(Node arg) {
		if (arg instanceof DOMElementImpl) {
			synchronized (this) {
				Map<String, String> attrs1 = this.attributes;
				if (attrs1 == null) {
					attrs1 = Collections.emptyMap();
				}
				Map<String, String> attrs2 = ((DOMElementImpl) arg).attributes;
				if (attrs2 == null) {
					attrs2 = Collections.emptyMap();
				}
				return Objects.equals(attrs1, attrs2);
			}
		} else {
			return false;
		}
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		String id = this.id;
		return id == null ? "" : id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.setAttribute(HtmlAttributeProperties.ID, id);
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.getAttribute(HtmlAttributeProperties.TITLE);
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.setAttribute(HtmlAttributeProperties.TITLE, title);
	}

	/**
	 * Gets the lang.
	 *
	 * @return the lang
	 */
	public String getLang() {
		return this.getAttribute(HtmlAttributeProperties.LANG);
	}

	/**
	 * Sets the lang.
	 *
	 * @param lang
	 *            the new lang
	 */
	public void setLang(String lang) {
		this.setAttribute(HtmlAttributeProperties.LANG, lang);
	}

	/**
	 * Gets the dir.
	 *
	 * @return the dir
	 */
	public String getDir() {
		return this.getAttribute(HtmlAttributeProperties.DIR);
	}

	/**
	 * Sets the dir.
	 *
	 * @param dir
	 *            the new dir
	 */
	public void setDir(String dir) {
		this.setAttribute(HtmlAttributeProperties.DIR, dir);
	}

	/**
	 * Gets the hidden.
	 *
	 * @return the hidden
	 */
	public boolean getHidden() {
		return this.getAttribute(HtmlAttributeProperties.HIDDEN) == null ? true : false;
	}

	/**
	 * Gets the content editable.
	 *
	 * @return the content editable
	 */
	public String getContentEditable() {
		return this.getAttribute(HtmlAttributeProperties.CONTENTEDITABLE);
	}

	/**
	 * Sets the content editable.
	 *
	 * @param contenteditable
	 *            the new content editable
	 */
	public void setContentEditable(String contenteditable) {
		this.setAttribute(HtmlAttributeProperties.CONTENTEDITABLE, contenteditable);
	}

	/**
	 * Gets the spellcheck.
	 *
	 * @return the spellcheck
	 */
	public String getSpellcheck() {
		return this.getAttribute(HtmlAttributeProperties.SPELLCHECK);
	}

	/**
	 * Sets the spellcheck.
	 *
	 * @param spellcheck
	 *            the new spellcheck
	 */
	public void setSpellcheck(String spellcheck) {
		this.setAttribute(HtmlAttributeProperties.SPELLCHECK, spellcheck);
	}

	/**
	 * Gets the draggable.
	 *
	 * @return the draggable
	 */
	public boolean getDraggable() {
		String draggable = this.getAttribute(HtmlAttributeProperties.DRAGGABLE);
		if (draggable == null) {
			return false;
		} else {
			return Boolean.valueOf(draggable);
		}
	}

	/**
	 * Sets the draggable.
	 *
	 * @param draggable
	 *            the new draggable
	 */
	public void setDraggable(boolean draggable) {
		this.setAttribute(HtmlAttributeProperties.DRAGGABLE, String.valueOf(draggable));
	}

	/**
	 * Gets the checks if is content editable.
	 *
	 * @return the checks if is content editable
	 */
	public boolean getIsContentEditable() {
		String content = getAttribute(HtmlAttributeProperties.CONTENTEDITABLE);
		if (content == null) {
			return false;
		} else {
			return Boolean.valueOf(content);
		}
	}

	/**
	 * Gets the disabled.
	 *
	 * @return the disabled
	 */
	public boolean getDisabled() {
		return this.getAttribute(HtmlAttributeProperties.DISABLE) == null ? true : false;
	}

	/**
	 * Gets the checked.
	 *
	 * @return the checked
	 */
	public boolean getChecked() {
		return this.getAttribute(HtmlAttributeProperties.CHECKED) == null ? true : false;
	}

	/**
	 * Gets the item scope.
	 *
	 * @return the item scope
	 */
	public boolean getItemScope() {
		String itemscope = this.getAttribute(HtmlAttributeProperties.ITEMSCOPE);
		if (itemscope == null) {
			return false;
		} else {
			return Boolean.valueOf(itemscope);
		}
	}

	/**
	 * Sets the item scope.
	 *
	 * @param itemscope
	 *            the new item scope
	 */
	public void setItemScope(boolean itemscope) {
		this.setAttribute(HtmlAttributeProperties.ITEMSCOPE, String.valueOf(itemscope));

	}

	/**
	 * Gets the item type.
	 *
	 * @return the item type
	 */
	public String getItemType() {
		return this.getAttribute(HtmlAttributeProperties.ITEMTYPE);
	}

	/**
	 * Sets the item type.
	 *
	 * @param itemType
	 *            the new item type
	 */
	public void setItemType(String itemType) {
		this.setAttribute(HtmlAttributeProperties.ITEMTYPE, itemType);

	}

	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public String getItemId() {
		return this.getAttribute(HtmlAttributeProperties.ITEMID);
	}

	/**
	 * Sets the item id.
	 *
	 * @param itemId
	 *            the new item id
	 */
	public void setItemId(String itemId) {
		this.setAttribute(HtmlAttributeProperties.ITEMID, itemId);
	}

	/**
	 * Gets the tab index.
	 *
	 * @return the tab index
	 */
	public int getTabIndex() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.TABINDEX));
		} catch (Exception err) {
			return 0;
		}
	}

	/**
	 * Sets the tab index.
	 *
	 * @param tabIndex
	 *            the new tab index
	 */
	public void setTabIndex(int tabIndex) {
		this.setAttribute(HtmlAttributeProperties.TABINDEX, String.valueOf(tabIndex));
	}

	/**
	 * Gets the access key.
	 *
	 * @return the access key
	 */
	public String getAccessKey() {
		return this.getAttribute(HtmlAttributeProperties.ACCESSKEY);
	}

	/**
	 * Sets the access key.
	 *
	 * @param accessKey
	 *            the new access key
	 */
	public void setAccessKey(String accessKey) {
		this.setAttribute(HtmlAttributeProperties.ACCESSKEY, accessKey);

	}

	/**
	 * Gets the context menu.
	 *
	 * @return the context menu
	 */
	public HTMLMenuElement getContextMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the context menu.
	 *
	 * @param contextMenu
	 *            the new context menu
	 */
	public void setContextMenu(HTMLMenuElement contextMenu) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 */
	@Override
	public final String getAttribute(String name) {
		String normalName = this.normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			return attributes == null ? null : (String) attributes.get(normalName);
		}
	}

	/**
	 * Gets the attr.
	 *
	 * @param normalName
	 *            the normal name
	 * @param value
	 *            the value
	 * @return the attr
	 */
	private Attr getAttr(String normalName, String value) {
		// TODO: "specified" attributes
		return new DOMAttrImpl(normalName, value, true, this, HtmlAttributeProperties.ID.equals(normalName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getAttributeNode(java.lang.String)
	 */
	@Override
	public Attr getAttributeNode(String name) {
		String normalName = this.normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			String value = attributes == null ? null : (String) attributes.get(normalName);
			return value == null ? null : this.getAttr(normalName, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getAttributeNodeNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getAttributeNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/**
	 * Checks if is tag name.
	 *
	 * @param node
	 *            the node
	 * @param name
	 *            the name
	 * @return true, if is tag name
	 */
	protected static boolean isTagName(Node node, String name) {
		return node.getNodeName().equalsIgnoreCase(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getElementsByTagName(java.lang.String)
	 */
	@Override
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
						NodeList sublist = childElement.getElementsByTagName(name);
						int length = sublist.getLength();
						for (int idx = 0; idx < length; idx++) {
							descendents.add(sublist.item(idx));
						}
					}
				}
			}
		}
		return new DOMNodeListImpl(descendents);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getElementsByTagNameNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getSchemaTypeInfo()
	 */
	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getTagName()
	 */
	@Override
	public String getTagName() {
		return this.getNodeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#hasAttribute(java.lang.String)
	 */
	@Override
	public boolean hasAttribute(String name) {
		String normalName = this.normalizeAttributeName(name);
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			return attributes == null ? false : attributes.containsKey(normalName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#hasAttributeNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#removeAttribute(java.lang.String)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#removeAttributeNode(org.w3c.dom.Attr)
	 */
	@Override
	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		String normalName = this.normalizeAttributeName(oldAttr.getName());
		synchronized (this) {
			Map<String, String> attributes = this.attributes;
			if (attributes == null) {
				return null;
			}
			String oldValue = attributes.remove(normalName);
			// TODO: "specified" attributes
			return oldValue == null ? null : this.getAttr(normalName, oldValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#removeAttributeNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/**
	 * Assign attribute field.
	 *
	 * @param normalName
	 *            the normal name
	 * @param value
	 *            the value
	 */
	protected void assignAttributeField(String normalName, String value) {
		// Note: overriders assume that processing here is only done after
		// checking attribute names, i.e. they may not call the super
		// implementation if an attribute is already taken care of.
		boolean isName = false;
		if (HtmlAttributeProperties.ID.equals(normalName)
				|| (isName = HtmlAttributeProperties.NAME.equals(normalName))) {
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

	/**
	 * Normalize attribute name.
	 *
	 * @param name
	 *            the name
	 * @return the string
	 */
	protected final String normalizeAttributeName(String name) {
		return name.toLowerCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#setAttribute(java.lang.String, java.lang.String)
	 */
	@Override
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
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @throws DOMException
	 *             the DOM exception
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#setAttributeNode(org.w3c.dom.Attr)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#setAttributeNodeNS(org.w3c.dom.Attr)
	 */
	@Override
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#setAttributeNS(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#setIdAttribute(java.lang.String, boolean)
	 */
	@Override
	public void setIdAttribute(String name, boolean isId) throws DOMException {
		String normalName = this.normalizeAttributeName(name);
		if (!HtmlAttributeProperties.ID.equals(normalName)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "IdAttribute can't be anything other than ID");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#setIdAttributeNode(org.w3c.dom.Attr, boolean)
	 */
	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
		String normalName = this.normalizeAttributeName(idAttr.getName());
		if (!HtmlAttributeProperties.ID.equals(normalName)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "IdAttribute can't be anything other than ID");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#setIdAttributeNS(java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getLocalName()
	 */
	@Override
	public String getLocalName() {
		return this.getNodeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getNodeType()
	 */
	@Override
	public short getNodeType() {
		return Node.ELEMENT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getNodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#setNodeValue(String)
	 */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		// nop
	}

	/**
	 * Gets inner text of the element, possibly including text in comments. This
	 * can be used to get Javascript code out of a SCRIPT element.
	 *
	 * @param includeComment
	 *            the include comment
	 * @return the raw inner text
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
					} else if (node instanceof DOMElementImpl) {
						DOMElementImpl en = (DOMElementImpl) node;
						String txt = en.getRawInnerText(includeComment);
						if (!"".equals(txt)) {
							if (sb == null) {
								sb = new StringBuffer();
							}
							sb.append(txt);
						}
					} else if (includeComment && (node instanceof Comment)) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#toString()
	 */
	@Override
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
			if ((i + 1) < length) {
				sb.append(',');
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Sets the inner text.
	 *
	 * @param newText
	 *            the new inner text
	 */
	public void setInnerText(String newText) {
		org.w3c.dom.Document document = this.document;
		if (document == null) {
			logger.error("setInnerText(): Element " + this + " does not belong to a document.");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#createSimilarNode()
	 */
	@Override
	protected Node createSimilarNode() {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return doc == null ? null : doc.createElement(this.getTagName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.DOMNodeImpl#htmlEncodeChildText(java.lang.
	 * String )
	 */
	@Override
	protected String htmlEncodeChildText(String text) {
		if (HtmlParser.isDecodeEntities(this.name)) {
			return Strings.strictHtmlEncode(text, false);
		} else {
			return text;
		}
	}

}
