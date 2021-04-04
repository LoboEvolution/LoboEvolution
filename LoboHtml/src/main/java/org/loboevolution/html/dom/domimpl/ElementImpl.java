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
/*
 * Created on Oct 29, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.awt.Dimension;
import java.awt.Font;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.loboevolution.common.Strings;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.html.dom.nodeimpl.DOMException;
import org.loboevolution.html.dom.nodeimpl.NamedNodeMapImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Code;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.DOMTokenList;

import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;
import org.loboevolution.html.node.Text;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventListener;

/**
 * <p>ElementImpl class.</p>
 *
 *
 *
 */
public class ElementImpl extends WindowEventHandlersImpl implements Element {

	protected Map<String, String> attributes;

	private String id;

	private final String name;
	
	private String outer;

	/**
	 * <p>Constructor for ElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public ElementImpl(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>assignAttributeField.</p>
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
		return new AttrImpl(normalName, value, true, this, "id".equals(normalName));
	}

	/** {@inheritDoc} */
	@Override
	public String getAttribute(String name) {
		final String normalName = Strings.normalizeAttributeName(name);
		synchronized (this) {
			final Map<String, String> attributes = this.attributes;
			return attributes == null ? null : attributes.get(normalName);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Attr getAttributeNode(String name) {
		final String normalName = Strings.normalizeAttributeName(name);
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
	public NodeType getNodeType() {
		return NodeType.ELEMENT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getNodeValue()
	 */
	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
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
		for (Iterator<Node> i= nodeList.iterator(); i.hasNext(); ) {
			Node node = i.next();
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
		final String normalName = Strings.normalizeAttributeName(name);
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

	/** {@inheritDoc} */
	@Override
	public void removeAttribute(String name) {
		final String normalName = Strings.normalizeAttributeName(name);
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
	public Attr removeAttributeNode(Attr oldAttr) {
		final String normalName = Strings.normalizeAttributeName(oldAttr.getName());
		synchronized (this) {
			final Map<String, String> attributes = this.attributes;
			if (attributes == null) {
				return null;
			}
			final String oldValue = attributes.remove(normalName);
			return oldValue == null ? null : getAttr(normalName, oldValue);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setAttribute(String name, String value) {
		final String normalName = Strings.normalizeAttributeName(name);
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
	public void setAttributeImpl(String name, String value) {
		final String normalName = Strings.normalizeAttributeName(name);
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
	public Attr setAttributeNode(Attr newAttr) {
		final String normalName = Strings.normalizeAttributeName(newAttr.getName());
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
	 * {@inheritDoc}
	 *
	 * <p>Setter for the field id.</p>
	 */
	public void setId(String id) {
		setAttribute("id", id);
	}

	/** {@inheritDoc} */
	@Override
	public void setIdAttribute(String name, boolean isId) {
		final String normalName = Strings.normalizeAttributeName(name);
		if (!"id".equals(normalName)) {
			throw new DOMException(Code.NOT_SUPPORTED_ERR, "IdAttribute can't be anything other than ID");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId) {
		final String normalName = Strings.normalizeAttributeName(idAttr.getName());
		if (!"id".equals(normalName)) {
			throw new DOMException(Code.NOT_SUPPORTED_ERR, "IdAttribute can't be anything other than ID");
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
	public void setNodeValue(String nodeValue) {
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
	
	/**
	 * <p>getFirstElementChild.</p>
	 *
	 * @return a {@link org.w3c.dom.Element} object.
	 */
	public Element getFirstElementChild() {
		for (Iterator<Node> i = nodeList.iterator(); i.hasNext();) {
			Node n = i.next();
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
		for (Iterator<Node> i= nodeList.iterator(); i.hasNext(); ) {
			Node n = i.next();
			if (n instanceof Element) {
				count++;
			}
		}

		return count;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * setInnerHTML.
	 * </p>
	 */
	@Override
	public void setInnerHTML(String newHtml) {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document == null) {
			this.warn("setInnerHTML(): Element " + this + " does not belong to a document.");
			return;
		}
		final HtmlParser parser = new HtmlParser(document.getUserAgentContext(), document, null, false);
		this.nodeList.clear();
		// Should not synchronize around parser probably.
		try {
			try (Reader reader = new StringReader(newHtml)) {
				parser.parse(reader, this);
			}
		} catch (final Exception thrown) {
			this.warn("setInnerHTML(): Error setting inner HTML.", thrown);
		}
	}
	
	/**
	 * <p>Getter for the field <code>outer</code>.</p>
	 *
	 * @return the outer
	 */
	public String getOuter() {
		return outer;
	}

	/**
	 * <p>Setter for the field <code>outer</code>.</p>
	 *
	 * @param outer the outer to set
	 */
	public void setOuter(String outer) {
		this.outer = outer;
	}

	
	/**
	 * {@inheritDoc}
	 *
	 * <p>getClassList.</p>
	 */
	@Override
	public DOMTokenList getClassList() {
        return new DOMTokenListImpl(this, this.getClassName());
	}

	/** {@inheritDoc} */
	@Override
	public String getClassName() {
		final String className = getAttribute("class");
		return className == null ? "" : className;
	}

	/** {@inheritDoc} */
	@Override
	public void setClassName(String className) {
		setAttribute("class", className);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientHeight() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
		HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
		Dimension preferredSize = htmlPanel.getPreferredSize();
		AbstractCSSProperties currentStyle = ((HTMLElementImpl)this).getCurrentStyle();	
		String height = currentStyle.getHeight();
						
		if (Strings.isBlank(height) || "auto".equalsIgnoreCase(height)) {
			if (this instanceof HTMLDivElementImpl && Strings.isBlank(getTextContent())) {
				height = "0px";
			}
			
			if (this instanceof HTMLDivElementImpl && Strings.isNotBlank(getTextContent())) {
				FontFactory FONT_FACTORY = FontFactory.getInstance();
				Font DEFAULT_FONT = FONT_FACTORY.getFont(new FontKey());
				height = DEFAULT_FONT.getSize() + "px";
			}
		}
		return HtmlValues.getPixelSize(height, null, -1, preferredSize.height);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public int getClientTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public int getClientWidth() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
		HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
		Dimension preferredSize = htmlPanel.getPreferredSize();
		AbstractCSSProperties currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
		String width = currentStyle.getWidth();
		
		if(Strings.isBlank(width) || "auto".equalsIgnoreCase(width)) {
			width = "100%";
		}		
		return HtmlValues.getPixelSize(width, null, -1, preferredSize.width);
	}

	/** {@inheritDoc} */
	@Override
	public EventListener<Event> getOnfullscreenchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * {@inheritDoc}
	 *
	 * <p>getOuterHTML.</p>
	 */
	@Override
	public String getOuterHTML() {
		final StringBuilder buffer = new StringBuilder();
		synchronized (this) {
			appendOuterHTMLImpl(buffer);
		}
		return buffer.toString();
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>setOuterHTML.</p>
	 */
	@Override
	public void setOuterHTML(String newHtml) {
		this.outer = outerNewHtml(newHtml);
		if (this.outer != null) {
			final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
			if (document == null) {
				this.warn("setOuterHTML(): Element " + this + " does not belong to a document.");
				return;
			}
			final HtmlParser parser = new HtmlParser(document.getUserAgentContext(), document, null, false);
			this.nodeList.clear();
			// Should not synchronize around parser probably.
			try {
				try (Reader reader = new StringReader(newHtml)) {
					parser.parse(reader, this);
				}
			} catch (final Exception thrown) {
				this.warn("setOuterHTML(): Error setting inner HTML.", thrown);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setScrollLeft(double scrollLeft) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setScrollTop(double scrollTop) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public String getSlot() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setSlot(String slot) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public <E extends Element> E closest(String selector) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String[] getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasPointerCapture(int pointerId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean matches(String selectors) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void releasePointerCapture(int pointerId) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void requestPointerLock() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scroll() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scroll(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scrollBy() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scrollBy(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scrollIntoView(boolean arg) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scrollIntoView() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scrollTo() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scrollTo(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setPointerCapture(int pointerId) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean toggleAttribute(String qualifiedName, boolean force) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean toggleAttribute(String qualifiedName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * <p>appendOuterHTMLImpl.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuilder} object.
	 */
	public void appendOuterHTMLImpl(StringBuilder buffer) {
		final String tagName = getTagName().toUpperCase();
		buffer.append('<');
		buffer.append(tagName);
		final Map<String, String> attributes = this.attributes;
		if (attributes != null) {
			attributes.forEach((k, v) -> {
				if (v != null) {
					buffer.append(' ');
					buffer.append(k);
					buffer.append("=\"");
					buffer.append(Strings.strictHtmlEncode(v, true));
					buffer.append("\"");
				}
			});
		}
		if (nodeList.getLength() == 0) {
			buffer.append("/>");
			return;
		}
		buffer.append('>');
		appendInnerHTMLImpl(buffer);
		buffer.append("</");
		buffer.append(tagName);
		buffer.append('>');
	}
	
	private String outerNewHtml(final String newHtml) {
		if (newHtml != null) {
			return newHtml.endsWith(">") || ! newHtml.startsWith("<") ? newHtml : newHtml + ">";
		}
		return "";
	}
}
