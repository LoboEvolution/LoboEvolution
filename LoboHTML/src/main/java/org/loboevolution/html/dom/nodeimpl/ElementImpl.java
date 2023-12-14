/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
/*
 * Created on Oct 29, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.DOMException;
import org.htmlunit.cssparser.parser.selector.Selector;
import org.htmlunit.cssparser.parser.selector.SelectorList;
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.filter.ClassNameFilter;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.filter.TagNameFilter;
import org.loboevolution.html.dom.filter.TagNsNameFilter;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.html.js.geom.DOMRectImpl;
import org.loboevolution.html.js.geom.DOMRectListImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.node.js.geom.DOMRect;
import org.loboevolution.html.node.js.geom.DOMRectList;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.html.renderer.RBlock;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.StyleSheetAggregator;

import org.mozilla.javascript.annotations.JSFunction;

import javax.swing.*;
import java.awt.*;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * <p>ElementImpl class.</p>
 */
@Slf4j
public abstract class ElementImpl extends NodeImpl implements Element {

	private static final int SCROLL_BAR_THICKNESS = 17;

	private final NamedNodeMapImpl map;

	private final String name;


	/**
	 * <p>Constructor for ElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public ElementImpl(final String name) {
		this.name = name;
		this.map = new NamedNodeMapImpl(this, new NodeListImpl());

	}

	/** {@inheritDoc} */
	@Override
	@JSFunction
	public String getAttribute(final String name) {
		final Attr attr = getAttributeNode(name);
		return attr == null ? null : attr.getValue();
	}

	/** {@inheritDoc} */
	@Override
	@JSFunction
	public String getAttributeNS(final String namespaceURI, final String localName) throws DOMException {
		final Attr attr = getAttributeNodeNS(namespaceURI, localName);
		if (attr != null) {
			return attr.getValue();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	@JSFunction
	public Attr getAttributeNode(final String name) {
		final AttrImpl attribute = (AttrImpl) map.getNamedItem(name);
		if (attribute != null) {
			attribute.setSpecified(true);
			return attribute;
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	@JSFunction
	public Attr getAttributeNodeNS(final String namespaceURI, final String localName) throws DOMException {

		if (Strings.isBlank(namespaceURI)) {
			return getAttributeNode(localName);
		}

		final String local = localName.contains(":") ? localName.split(":")[1] : localName;
		final AttrImpl attribute = (AttrImpl) map.getNamedItemNS(namespaceURI, local);
		if (attribute != null) {
			attribute.setSpecified(true);
			return attribute;
		}

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void removeAttribute(final String name) {
		try {
			map.removeNamedItem(name);
		} catch (final DOMException ex) {
			log.error(ex.getMessage());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeAttributeNS(final String namespaceURI, final String localName) throws DOMException {
		map.removeNamedItemNS(namespaceURI, localName);
	}

	/** {@inheritDoc} */
	@Override
	public Attr removeAttributeNode(final Attr oldAttr) {

		if (oldAttr.getOwnerElement() == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "refChild not found");
		}

		oldAttr.setOwnerElement(null);
		try {
			return (Attr) map.removeNamedItem(oldAttr.getLocalName());
		} catch (final DOMException ex) {
			try {
				return (Attr) map.removeNamedItemNS("*", oldAttr.getLocalName());
			} catch (final DOMException ex1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "Attribute not found");
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setAttribute(final String attrName, final String value) {
		String prefix = null;
		String name = attrName;
		if (Strings.isBlank(name)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains null value");
		}

		if (name.contains(":")) {
			final String[] split = name.split(":");
			if (split.length != 2) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has an empty local name.");
			}
			if (Strings.isBlank(split[0]) || Strings.isBlank(split[1])) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has an empty local name.");
			}

			if (!Strings.isXMLIdentifier(split[0]) || !Strings.isXMLIdentifier(split[1])) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}

			prefix = split[0];
			name = split[1];
		} else {
			if (!Strings.isXMLIdentifier(name)) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}
		}

		final Node node = map.getNamedItem(name);
		if (node != null) {
			removeAttributeNode((AttrImpl) node);
		}

		final AttrImpl attr = new AttrImpl(name, value, "id".equalsIgnoreCase(name), this, true);
		final Document doc = getOwnerDocument();
		attr.setOwnerDocument(doc);
		attr.setNamespaceURI(getNamespaceURI() != null ? getNamespaceURI() : doc != null ? doc.getNamespaceURI() : getParentNode() != null ? getParentNode().getNamespaceURI() : null);
		if (Strings.isNotBlank(prefix) && Strings.isNotBlank(attr.getNamespaceURI())) {
			attr.setPrefix(prefix);
		}
		map.setNamedItem(attr);
		assignAttributeField(name, value);
	}

	/** {@inheritDoc} */
	@Override
	public void setAttributeNS(final String namespaceURI, final String qName, final String value) throws DOMException {
		String prefix = null;
		String qualifiedName = qName;
		if (Strings.isBlank(qualifiedName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains null value");
		}

		if (qualifiedName.contains(":")) {
			final String[] split = qualifiedName.split(":");
			if (split.length != 2) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has an empty local name.");
			}

			if (Strings.isBlank(split[0]) || Strings.isBlank(split[1])) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has an empty local name.");
			}

			if (split[0].equals("xml") && !"http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR, "The namespaceURI is not http://www.w3.org/XML/1998/namespace.");
			}

			if (!Strings.isXMLIdentifier(split[0]) || !Strings.isXMLIdentifier(split[1])) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}

			prefix = split[0];
			qualifiedName = split[1];

		} else {
			if (!Strings.isXMLIdentifier(qualifiedName)) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
			}
		}

		final Node node = map.getNamedItemNS(namespaceURI, qualifiedName);

		if (node != null && Strings.isNotBlank(namespaceURI) && namespaceURI.equals(node.getNamespaceURI())) {
			removeAttributeNode((AttrImpl) node);
		}

		final AttrImpl attr = new AttrImpl(qualifiedName, value, "id".equalsIgnoreCase(name), this, true);
		attr.setNamespaceURI(namespaceURI);
		attr.setOwnerDocument(getOwnerDocument());
		if (Strings.isNotBlank(prefix)) attr.setPrefix(prefix);
		map.setNamedItemNS(attr);
		assignAttributeField(qualifiedName, value);
	}

	/** {@inheritDoc} */
	@Override
	public void setIdAttribute(final String localName, final boolean isId) throws DOMException {
		final AttrImpl attr = (AttrImpl) getAttributeNode(localName);
		if (attr != null) {
			attr.setNameId(isId);
		} else {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "Attribute not found");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setIdAttributeNS(final String namespaceURI, final String localName, final boolean isId) throws DOMException {
		final AttrImpl attr = (AttrImpl) getAttributeNodeNS(namespaceURI, localName);
		if (attr != null) {
			attr.setNameId(isId);
		} else {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "Attribute not found");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setIdAttributeNode(final Attr idAttr, final boolean isId) {
		final Attr checkAttr = getAttributeNode(idAttr.getName());

		if(checkAttr == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "Attribute not found");
		}
		final AttrImpl attr = (AttrImpl) idAttr;
		attr.setNameId(isId);
	}

	/** {@inheritDoc} */
	@Override
	public Attr setAttributeNode(final Attr newAttr) {
		final Attr checkAttr = getAttributeNode(newAttr.getName());

		if (checkAttr == null && Objects.equals(newAttr.getOwnerElement(), this)) {
			throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR,
					"Attr is already an attribute of another Element object. The DOM user must explicitly clone Attr nodes to re-use them in other elements.");
		}

		if(!Objects.equals(newAttr.getOwnerDocument(), getOwnerDocument())) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
		}

		if (checkAttr != null && Strings.isBlank(checkAttr.getNamespaceURI())) {
			removeAttributeNode(checkAttr);
		}

		newAttr.setOwnerElement(this);
		assignAttributeField(newAttr.getName(), newAttr.getValue());
		return (Attr) map.setNamedItem(newAttr);
	}

	/** {@inheritDoc} */
	@Override
	public Attr setAttributeNodeNS(final Attr newAttr) throws DOMException {
		final Attr checkAttr = getAttributeNodeNS(newAttr.getNamespaceURI(), newAttr.getLocalName());
		if (checkAttr == null && Objects.equals(newAttr.getOwnerElement(), this)) {
			throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR,
					"Attr is already an attribute of another Element object. The DOM user must explicitly clone Attr nodes to re-use them in other elements.");
		}

		if(!Objects.equals(newAttr.getOwnerDocument(), getOwnerDocument())) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
		}

		if (checkAttr != null && Objects.equals(checkAttr.getNamespaceURI(), newAttr.getNamespaceURI())) {
			removeAttributeNS(newAttr.getNamespaceURI(), newAttr.getLocalName());
		}

		newAttr.setOwnerElement(this);
		assignAttributeField(newAttr.getName(), newAttr.getValue());
		return (Attr) map.setNamedItemNS(newAttr);
	}

	/** {@inheritDoc} */
	@Override
	public NamedNodeMap getAttributes() {
		return this.map;
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
		String id = getAttribute("id");
		id = Strings.isBlank(id) ? getAttributeNS("*", "id") : id;
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

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		if (this.name.contains(":")) {
			return this.name.split(":")[1];
		}
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		final StringBuilder builder = new StringBuilder();
		if (Strings.isNotBlank(getPrefix())) {
			builder.append(getPrefix()).append(":");
		}

		return builder.append(getLocalName()).toString();
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.ELEMENT_NODE;
	}

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
	protected String getRawInnerText(final boolean includeComment) {
		StringBuilder sb = null;
		for (final Node node : nodeList) {
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
	public boolean hasAttribute(final String name) {
		return map.getNamedItem(name) != null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasAttributeNS(final String namespaceURI, final String localName) throws DOMException {

		if (Strings.isBlank(localName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "null localName");
		}

		return map.getNamedItemNS(namespaceURI, localName) != null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasAttributes() {
		return map.getAttributes().getLength() > 0;
	}

	/** {@inheritDoc} */
	@Override
	protected String htmlEncodeChildText(final String text) {
		if (XHtmlParser.isDecodeEntities(this.name)) {
			return Strings.strictHtmlEncode(text, false);
		} else {
			return text;
		}
	}

	/**
	 * <p>setDir.</p>
	 *
	 * @param dir a {@link java.lang.String} object.
	 */
	public void setDir(final String dir) {
		setAttribute("dir", dir);
	}

	/** {@inheritDoc} */
	@Override
	public void setId(final String id) {
		setAttribute("id", id);
	}

	/**
	 * <p>setInnerText.</p>
	 *
	 * @param newText a {@link java.lang.String} object.
	 */
	public void setInnerText(final String newText) {
		final Document document = this.document;
		if (document != null) {
			this.nodeList.clear();
			final Node textNode = document.createTextNode(newText);
			appendChild(textNode);
		} else {
			this.warn("setInnerText(): Element " + this + " does not belong to a document.");
		}
	}

	/**
	 * <p>setLang.</p>
	 *
	 * @param lang a {@link java.lang.String} object.
	 */
	public void setLang(final String lang) {
		setAttribute("lang", lang);
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(final String nodeValue) {}

	/**
	 * <p>setTitle.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	public void setTitle(final String title) {
		setAttribute("title", title);
	}

	/** {@inheritDoc} */
	@Override
	public void setInnerHTML(final String newHtml) {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document != null) {
			final XHtmlParser parser = new XHtmlParser(document.getUserAgentContext(), document, false);
			this.nodeList.clear();
			try (final Reader reader = new StringReader(newHtml)) {
				parser.parse(reader, this);
			} catch (final Exception thrown) {
				this.warn("setInnerHTML(): Error setting inner HTML.", thrown);
			}
		} else {
			this.warn("setInnerHTML(): Element " + this + " does not belong to a document.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public DOMTokenList getClassList() {
		final DOMTokenListImpl tokList = new DOMTokenListImpl(this);
		final String className = getClassName();
		if(Strings.isNotBlank(className)){
			final String[] listString = className.split(" ");
			final List<String> names = Arrays.asList(listString);
			names.forEach(tokList::populate);
		}
        return tokList;
	}

	/** {@inheritDoc} */
	@Override
	public String getClassName() {
		final String className = getAttribute("class");
		return className == null ? "" : className;
	}

	/** {@inheritDoc} */
	@Override
	public void setClassName(final String className) {
		setAttribute("class", className);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientHeight() {
		return calculateHeight(false, true, true);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientLeft() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final CSSStyleDeclaration currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
		return HtmlValues.getPixelSize(currentStyle.getBorderLeftWidth(), null, doc.getDefaultView(), 0);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientTop() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final CSSStyleDeclaration currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
		return HtmlValues.getPixelSize(currentStyle.getBorderTopWidth(), null, doc.getDefaultView(), 0);
	}

	/** {@inheritDoc} */
	@Override
	public Integer getClientWidth() {
		return calculateWidth(false, true, true);
	}

	/** {@inheritDoc} */
	@Override
	public String getInnerHTML() {
		final StringBuilder buffer = new StringBuilder();
		synchronized (this) {
			appendInnerHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String getInnerText() {
		final StringBuilder buffer = new StringBuilder();
		synchronized (this) {
			appendInnerTextImpl(buffer);
		}
		return buffer.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String getOuterHTML() {
		final StringBuilder buffer = new StringBuilder();
		synchronized (this) {
			appendOuterHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	/** {@inheritDoc} */
	@Override
	public void setOuterHTML(final String newHtml) {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document != null) {
			if (this.parentNode != null) {
				final NodeListImpl list = ((NodeListImpl) this.parentNode.getChildNodes());
				final int idx = list.indexOf(this);
				list.remove(idx);

				try (final Reader reader = new StringReader(newHtml != null ? newHtml : "")) {
					final XHtmlParser parser = new XHtmlParser(document.getUserAgentContext(), document, false);
					parser.parse(reader, this.parentNode);
				} catch (final Exception thrown) {
					this.warn("setOuterHTML(): Error setting inner HTML.", thrown);
				}
			}
		} else {
			this.warn("setOuterHTML(): Element " + this + " does not belong to a document.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getSlot() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setSlot(final String slot) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public <E extends Element> E closest(final String selector) {
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
	public boolean hasPointerCapture(final int pointerId) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean matches(final String selectors) {
		if (Strings.isBlank(selectors)) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "The provided selector is empty.");
		}

		try {
			final SelectorList selectorList = CSSUtilities.getSelectorList(selectors);
			if (selectorList != null) {
				for (final Selector select : selectorList) {
					if (StyleSheetAggregator.selects(select, this, null)) {
						return true;
					}
				}
			}
			return false;
		} catch (final Exception e) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Is not a valid selector.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void releasePointerCapture(final int pointerId) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void requestPointerLock() {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void scroll(final int x, final int y) {
		setScrollLeft(x);
		setScrollTop(y);
	}

	/** {@inheritDoc} */
	@Override
	public void scrollBy(final int x, final int y) {
		scroll(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void scrollIntoView(final boolean arg) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void scrollIntoView() {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void scrollTo(final int x, final int y) {
		scroll(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollHeight() {
		return getClientHeight();
	}

	/** {@inheritDoc} */
	@Override
    public double getScrollLeft() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
        return isHScrollable() ? htmlRendererContext.getScrollx() : 0;
    }

	/** {@inheritDoc} */
	@Override
    public void setScrollLeft(final double left) {
		double scrollLeft = left;
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();

        if (scrollLeft < 0 || !isHScrollable()) {
            scrollLeft = 0;
        }

        htmlRendererContext.setScrollx(scrollLeft);
        final RBlock bodyBlock = (RBlock) this.getUINode();
        if (bodyBlock != null && bodyBlock.getScroll() != null)
            bodyBlock.getScroll().scrollBy(JScrollBar.HORIZONTAL, scrollLeft);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getScrollTop() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
		return isVScrollable() ? htmlRendererContext.getScrolly() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScrollTop(final double scroll) {
		double scrollTop = scroll;
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();

        if (scrollTop < 0 || !isVScrollable()) {
            scrollTop = 0;
        }

        htmlRendererContext.setScrolly(scrollTop);
        final RBlock bodyBlock = (RBlock) this.getUINode();
        if (bodyBlock != null && bodyBlock.getScroll() != null)
            bodyBlock.getScroll().scrollBy(JScrollBar.VERTICAL, scrollTop);
    }

	/** {@inheritDoc} */
	@Override
	public double getScrollWidth() {
		return getClientWidth();
	}

	/** {@inheritDoc} */
	@Override
	public void setPointerCapture(final int pointerId) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public boolean toggleAttribute(final String qualifiedName, final boolean force) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean toggleAttribute(final String qualifiedName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node insertAdjacentElement(final String where, final Node insertedElement) {
		switch (where.toLowerCase()) {
			case "afterbegin":
				return prepend(insertedElement);
			case "beforebegin":
				if (this.parentNode != null) {
					final NodeListImpl list = ((NodeListImpl) this.parentNode.getChildNodes());
					final int idx = list.indexOf(this);
					final Node d = list.get(idx);
					list.remove(idx);
					list.add(idx, insertedElement);
					list.add(idx + 1, d);
					return insertedElement;
				}
				break;
			case "beforeend":
				return appendChild(insertedElement);
			case "afterend":
				return insertBefore(insertedElement, getNextSibling());
					default:
				break;

		}
		return null;
	}

	@Override
	public void insertAdjacentHTML(final String position, final String text) {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document != null) {
			final XHtmlParser parser = new XHtmlParser(document.getUserAgentContext(), document, false);
			try (final Reader reader = new StringReader(text)) {
				switch (position) {
					case "afterbegin":
						parser.parse(reader, this);
						final NodeListImpl list = ((NodeListImpl) this.getChildNodes());
						final Node last  = getChildNodes().item(getChildNodes().getLength()-1);
						list.remove(last);
						insertBefore(last, getFirstChild());
						break;
					case "beforebegin":
						parser.parse(reader, this);
						final NodeListImpl nodeList = ((NodeListImpl) this.getChildNodes());
						final Node nodeLast = getChildNodes().item(getChildNodes().getLength() - 1);
						nodeList.remove(nodeLast);
						final NodeListImpl parentList = ((NodeListImpl) this.parentNode.getChildNodes());
						final int idx = parentList.indexOf(this);
						final Node d = parentList.get(idx);
						parentList.remove(idx);
						parentList.add(idx, nodeLast);
						parentList.add(idx + 1, d);
						break;
					case "beforeend":
						final NodeListImpl beforeEndList = ((NodeListImpl) this.getChildNodes());
						final Node beforeEndLast = getChildNodes().item(getChildNodes().getLength() - 1);
						beforeEndList.remove(beforeEndLast);

						parser.parse(reader, this);
						appendChild(beforeEndLast);
						break;
					case "afterend":
						parser.parse(reader, this);
						final NodeListImpl nodeList2 = ((NodeListImpl) this.getChildNodes());
						final Node nodeLast2 = getChildNodes().item(getChildNodes().getLength() - 1);
						nodeList2.remove(nodeLast2);
						appendChild(nodeLast2);
						break;
					default:
						break;

				}
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public DOMRect getBoundingClientRect() {

		final CSSStyleDeclaration currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final Window win = doc.getDefaultView();
		final RenderState rs  = doc.getRenderState();
		int width = calculateWidth(true, true, true);
		final int height = calculateHeight(true, true, true);
		final String position = currentStyle.getPosition();
		int marginLeft =  HtmlValues.getPixelSize(currentStyle.getMarginLeft(), rs, win, 0);
		int marginTop =  HtmlValues.getPixelSize(currentStyle.getMarginTop(), rs, win, 0);

		int top = 8;
		int left = 8;

		if (getParentNode() == null && getChildElementCount() == 0) {
			top = 0;
			left = 0;
		}

		if(CSSValues.ABSOLUTE.isEqual(position)){
			final int topLeft = HtmlValues.getPixelSize(currentStyle.getWidth(), rs, win, 0);
			top = HtmlValues.getPixelSize(currentStyle.getTop(), rs, win, 0);
			final String leftTxt = currentStyle.getLeft();

			if (Strings.isBlank(leftTxt)) {
				left = topLeft > 0 ? topLeft / 2 : topLeft;
			} else {
				left = HtmlValues.getPixelSize(leftTxt, rs, win, 0);
			}
		}


		for (Node n = getParentNode(); n != null; n = n.getParentNode()) {

			if (!(n instanceof TextImpl) && !(n instanceof HTMLDocumentImpl)) {

				final HTMLElementImpl p = (HTMLElementImpl) n;
				final CSSStyleDeclaration pCurrentStyle = p.getCurrentStyle();
				final String positionTxt = pCurrentStyle.getPosition();
				if (CSSValues.ABSOLUTE.isEqual(positionTxt)) {

					final String topTxt = pCurrentStyle.getTop();
					final String leftTxt = pCurrentStyle.getLeft();
					final int scrollTop = (int) p.getScrollTop();
					final int scrollLeft = (int) p.getScrollLeft();

					if (Strings.isNotBlank(topTxt)) {
						top = HtmlValues.getPixelSize(topTxt, rs, win, 0);
					}

					if (Strings.isNotBlank(leftTxt) && left == 0) {
						left = HtmlValues.getPixelSize(leftTxt, rs, win, 0);
					}

					if (Strings.isNotBlank(pCurrentStyle.getWidth()) && !pCurrentStyle.getWidth().equals("0")) {
						width = HtmlValues.getPixelSize(pCurrentStyle.getWidth(), rs, win, 0);
					}

					top = scrollTop > top ? scrollTop - top : top;
					left = scrollLeft > left ? scrollLeft - left : left;
					left = left == 0 ? 8 : left;
				} else {
					if (Strings.isNotBlank(pCurrentStyle.getMarginTop())) {
						top = 0;
						marginTop += HtmlValues.getPixelSize(pCurrentStyle.getMarginTop(), rs, win, 0);
					}
					if (Strings.isNotBlank(pCurrentStyle.getMarginLeft())) {
						left = 0;
						marginLeft += HtmlValues.getPixelSize(pCurrentStyle.getMarginLeft(), rs, win, 0);
					}
				}
			}
		}

		top = top + marginTop;
		left = left + marginLeft;
		final int bottom = top + height;
		return new DOMRectImpl(width, height, top, bottom, left);
	}

	/** {@inheritDoc} */
	@Override
	public DOMRectList getClientRects() {
		final DOMRectListImpl list = new DOMRectListImpl();
		final CSSStyleDeclaration style = ((HTMLElementImpl) this).getCurrentStyle();
		String display = Strings.isNotBlank(style.getDisplay()) ? style.getDisplay() : getAttribute("display");
		if (!"none".equals(display)) {
			for (Node n = getParentNode(); n != null; n = n.getPreviousSibling()) {
				if (!(n instanceof HTMLBodyElement) && !(n instanceof TextImpl) && !(n instanceof HTMLDocumentImpl)) {
					final HTMLElementImpl p = (HTMLElementImpl) n;
					final CSSStyleDeclaration st = p.getCurrentStyle();
					display = st.getDisplay();
				}
			}
		}

		if (!"none".equals(display)) {
			list.add(getBoundingClientRect());
		}

		return list;
	}

	/** {@inheritDoc} */
	@Override
	public TypeInfo getSchemaTypeInfo() {
		return new AttributeTypeInfo(this);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByClassName(final String classNames) {
		return new HTMLCollectionImpl(this, new ClassNameFilter(classNames));
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByTagName(final String tagname) {
		if ("*".equals(tagname)) {
			return new HTMLCollectionImpl(this, new ElementFilter(null));
		} else {
			return new HTMLCollectionImpl(this, new TagNameFilter(tagname));
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByTagNameNS(final String namespaceURI, final String localName) {

		if("*".equals(namespaceURI) && "*".equals(localName)) {
			return new HTMLCollectionImpl(this, new ElementFilter(null));
		}

		return new HTMLCollectionImpl(this, new TagNsNameFilter(localName, namespaceURI));
	}

	/** {@inheritDoc} */
	@Override
	public Element getFirstElementChild() {
		return (Element) nodeList.stream().filter(n -> n instanceof Element).findFirst().orElse(null);
	}

	/** {@inheritDoc} */
	@Override
	public Element getLastElementChild() {
		long count = nodeList.stream().filter(n -> n instanceof Element).count();
		if(count == 0) count = 1;
		final Stream<Node> stream = nodeList.stream();
		return (Element) stream.filter(n -> n instanceof Element).skip(count - 1).findFirst().orElse(null);
	}

	/** {@inheritDoc} */
	@Override
	public int getChildElementCount() {
		return (int) nodeList.stream().filter(n -> n instanceof Element).count();
	}

	/** {@inheritDoc} */
	@Override
	public Element querySelector(final String selectors) {
		try {
			final SelectorList selectorList = CSSUtilities.getSelectorList(selectors);
			final List<Element> elem = new ArrayList<>();
			if (selectorList != null) {
				final NodeListImpl childNodes = (NodeListImpl) getDescendents(new ElementFilter(null), true);
				childNodes.forEach(child -> {
					for (final Selector selector : selectorList) {
						if (child instanceof Element && StyleSheetAggregator.selects(selector, child, null)) {
							elem.add((Element) child);
						}
					}
				});
			}
			return elem.size() > 0 ? elem.get(0) : null;
		} catch (final Exception e) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Is not a valid selector.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public NodeList querySelectorAll(final String selector) {

		final ArrayList<Node> al = new ArrayList<>();

		if(selector == null) {
			return new NodeListImpl(al);
		}

		if(selector.isEmpty()){
			throw new DOMException(DOMException.NOT_FOUND_ERR, "The provided selector is empty.");
		}

		if(selector.trim().isEmpty()){
			throw new DOMException(DOMException.NOT_FOUND_ERR, "is not a valid selector.");
		}

		try {
			final SelectorList selectorList = CSSUtilities.getSelectorList(selector);
			if (selectorList != null) {
				final NodeListImpl childNodes = (NodeListImpl) getDescendents(new ElementFilter(null), true);
				childNodes.forEach(child -> {
					for (final Selector select : selectorList) {
						if (child instanceof Element && StyleSheetAggregator.selects(select, child, null)) {
							al.add(child);
						}
					}
				});
			}
			return new NodeListImpl(al);
		} catch (final Exception e) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Is not a valid selector.");
		}
	}

	public void assignAttributeField(final String normalName, final String value) {
		boolean isName = false;
		if ("id".equalsIgnoreCase(normalName) || (isName = "name".equals(normalName))) {
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

	@Override
	public boolean isEqualNode(final Node arg) {
		if (!super.isEqualNode(arg)) {
			return false;
		}

		final boolean hasAttrs = hasAttributes();
		if (hasAttrs != arg.hasAttributes()) {
			return false;
		}
		if (hasAttrs) {
			final NamedNodeMap map = getAttributes();
			final NamedNodeMap mapArg = arg.getAttributes();
			if (map.getLength() != mapArg.getLength()) {
				return false;
			}

			for (final Node n1 : Nodes.iterable(map)) {
				if (n1.getLocalName() == null) {
					final Node n2 = mapArg.getNamedItem(n1.getNodeName());
					if (n2 == null || !n1.isEqualNode(n2)) {
						return false;
					}
				} else {
					final Node n2 = mapArg.getNamedItemNS(n1.getNamespaceURI(), n1.getLocalName());
					if (n2 == null || !n1.isEqualNode(n2)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * <p>appendOuterHTMLImpl.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuilder} object.
	 */
	public void appendOuterHTMLImpl(final StringBuilder buffer) {
		final String tagName = getTagName().toUpperCase();
		buffer.append('<');
		buffer.append(tagName);
		for (final Node attrNode : Nodes.iterable(map)) {
			final Attr attr = (Attr) attrNode;
			buffer.append(' ');
			buffer.append(attr.getName());
			buffer.append("=\"");
			buffer.append(Strings.strictHtmlEncode(attr.getValue(), true));
			buffer.append("\"");
		}

		if (nodeList.getLength() == 0) {
			buffer.append("/>");
		} else {
			buffer.append('>');
			appendInnerHTMLImpl(buffer);
			buffer.append("</");
			buffer.append(tagName);
			buffer.append('>');
		}
	}

    private boolean isHScrollable() {
        final String overflow;
        final CSSStyleDeclaration currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
        overflow = currentStyle.getOverflow();
        int widthChild = 0;

        for (final Node child : (NodeListImpl) this.getChildNodes()) {
			if (child instanceof HTMLElementImpl) {
				final CSSStyleDeclaration pCurrentStyle = ((HTMLElementImpl)child).getCurrentStyle();
				final Integer cliInteger =  ((HTMLElementImpl) child).getClientWidth();
				if(cliInteger != null) widthChild += cliInteger;
				widthChild += HtmlValues.getPixelSize(pCurrentStyle.getLeft(), null, document.getDefaultView(), 0);
				widthChild += HtmlValues.getPixelSize(pCurrentStyle.getRight(), null, document.getDefaultView(), 0);
			}
		}
        return ("scroll".equals(overflow) || "auto".equals(overflow)) && (widthChild > this.getClientWidth());
    }

    private boolean isVScrollable() {
        final String overflow;
        final CSSStyleDeclaration currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
        overflow = currentStyle.getOverflow();
        int heightChild = 0;

		for (final Node child : (NodeListImpl) this.getChildNodes()) {
			if (child instanceof HTMLElementImpl) {
				heightChild += ((HTMLElementImpl) child).getClientHeight();
				final CSSStyleDeclaration pCurrentStyle = ((HTMLElementImpl)child).getCurrentStyle();
				heightChild += HtmlValues.getPixelSize(pCurrentStyle.getTop(), null, document.getDefaultView(), 0);
			}
		}

        return ("scroll".equals(overflow) || "auto".equals(overflow)) && (heightChild > this.getClientHeight());
    }

	public int calculateWidth(final boolean isBorder, final boolean isPadding, final boolean isClient) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final CSSStyleDeclaration currentStyle = ((HTMLElementImpl) this).getCurrentStyle();
		final String width = currentStyle.getWidth();
		final String overflow = currentStyle.getOverflow();
		final RenderState rs = getRenderState();
		final boolean padding = "border-box".equals(currentStyle.getBoxSizing()) ? false : isPadding;
		final boolean border = "border-box".equals(currentStyle.getBoxSizing()) ? false : isBorder;

		int widthSize;

		if (getParentNode() == null ||
				rs.getDisplay() == RenderState.DISPLAY_NONE ||
				(isClient && rs.getDisplay() == RenderState.DISPLAY_INLINE)) {
			widthSize = 0;
		} else {
			final int parentWidth = this instanceof HTMLHtmlElement ? -1 : isClient ? getParentElement().getClientWidth() : getParentElement().getOffsetWidth();
			widthSize = HtmlValues.getPixelSize(CSSValues.AUTO.isEqual(width) ? "100%" : Strings.isBlank(width) ? childWidth(this, doc, parentWidth) + "px" : width, null, doc.getDefaultView(), 0, parentWidth);
			if (Strings.isBlank(width) && rs.getDisplay() == RenderState.DISPLAY_BLOCK) {
				widthSize = parentWidth;
			} else {

				if (widthSize == 0 && (this instanceof HTMLInputElement ||
						this instanceof HTMLButtonElement ||
						this instanceof HTMLHeadElement ||
						this instanceof HTMLSelectElement ||
						this instanceof HTMLTextAreaElement ||
						this instanceof HTMLProgressElement ||
						this instanceof HTMLMeterElement ||
						this instanceof HTMLStrongElementImpl)) {
					return widthSize;
				}
			}

			if (!isClient && rs.getDisplay() == RenderState.DISPLAY_INLINE) {
				widthSize = 0;
			} else {
				final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
				final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
				final Dimension preferredSize = htmlPanel.getPreferredSize();
				widthSize = widthSize == -1 ? preferredSize.width : widthSize;

				if (!(this instanceof HTMLHtmlElement) && widthSize == preferredSize.width) {
					widthSize = widthSize - SCROLL_BAR_THICKNESS;
				}
			}

			if (padding) {
				widthSize += HtmlValues.getPixelSize(currentStyle.getPaddingRight(), null, doc.getDefaultView(), 0);
				widthSize += HtmlValues.getPixelSize(currentStyle.getPaddingLeft(), null, doc.getDefaultView(), 0);
			}

			if (border) {
				widthSize += HtmlValues.getPixelSize(currentStyle.getBorderLeftWidth(), null, doc.getDefaultView(), 0);
				widthSize += HtmlValues.getPixelSize(currentStyle.getBorderRightWidth(), null, doc.getDefaultView(), 0);
			}

			if (isClient && widthSize > 0 && CSSValues.SCROLL.isEqual(overflow)) {
				widthSize = widthSize - SCROLL_BAR_THICKNESS;
			}
		}
		return widthSize;
	}

	public int calculateHeight(final boolean isBorder, final boolean isPadding, final boolean isClient) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final CSSStyleDeclaration currentStyle = ((HTMLElementImpl) this).getCurrentStyle();
		final String height = currentStyle.getHeight();
		final String overflow = currentStyle.getOverflow();
		final String position = currentStyle.getPosition();
		final RenderState rs = getRenderState();
		final boolean padding = !"border-box".equals(currentStyle.getBoxSizing()) && isPadding;
		final boolean border = !"border-box".equals(currentStyle.getBoxSizing()) && isBorder;

		int heightSize;

		final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
		final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
		final Dimension preferredSize = htmlPanel.getPreferredSize();

		if (getParentNode() == null ||
				rs.getDisplay() == RenderState.DISPLAY_NONE ||
				(isClient && rs.getDisplay() == RenderState.DISPLAY_INLINE)) {
			heightSize = 0;
		} else {
			final int parentHeight = this instanceof HTMLHtmlElement ? preferredSize.height : isClient ? getParentElement().getClientHeight() : getParentElement().getOffsetHeight();
			heightSize = HtmlValues.getPixelSize(CSSValues.AUTO.isEqual(height) ? "100%" : Strings.isBlank(height) ? childHeight(this, position, doc, parentHeight) + "px" : height, null, doc.getDefaultView(), 0, parentHeight);
			if (heightSize == 0 && (this instanceof HTMLInputElement ||
					this instanceof HTMLButtonElement ||
					this instanceof HTMLHeadElement ||
					this instanceof HTMLSelectElement ||
					this instanceof HTMLTextAreaElement ||
					this instanceof HTMLProgressElement ||
					this instanceof HTMLMeterElement ||
					this instanceof HTMLStrongElementImpl)) {
				return heightSize;
			}

			if (!isClient && rs.getDisplay() == RenderState.DISPLAY_INLINE) {
				heightSize = 0;
			} else {

				heightSize = heightSize == -1 ? preferredSize.height : heightSize;

				if (!(this instanceof HTMLHtmlElement) && heightSize == preferredSize.height) {
					heightSize = heightSize - SCROLL_BAR_THICKNESS;
				}
			}

			if (padding) {
				heightSize += HtmlValues.getPixelSize(currentStyle.getPaddingTop(), null, doc.getDefaultView(), 0);
				heightSize += HtmlValues.getPixelSize(currentStyle.getPaddingBottom(), null, doc.getDefaultView(), 0);
			}

			if (border) {
				heightSize += HtmlValues.getPixelSize(currentStyle.getBorderTopWidth(), null, doc.getDefaultView(), 0);
				heightSize += HtmlValues.getPixelSize(currentStyle.getBorderBottomWidth(), null, doc.getDefaultView(), 0);
			}

			if (isClient && heightSize > 0 && CSSValues.SCROLL.isEqual(overflow)) {
				heightSize = heightSize - SCROLL_BAR_THICKNESS;
			}
		}
		return heightSize;
	}

	private int childHeight(final ElementImpl elm, final String position, final HTMLDocumentImpl doc, final Integer parentHeight) {
		final AtomicInteger h = new AtomicInteger(CSSValues.ABSOLUTE.isEqual(position) ? -1 : 0);
		if (elm instanceof HTMLTextAreaElement ||
				elm instanceof HTMLBaseFontElement ||
				elm instanceof HTMLScriptElement ||
				elm instanceof HTMLSuperscriptElementImpl) return h.get();

		elm.getNodeList().forEach(child -> {
			final int type = child.getNodeType();
			switch (type) {
				case Node.CDATA_SECTION_NODE:
				case Node.TEXT_NODE:
					if (elm instanceof HTMLFieldSetElement) {
						h.addAndGet(35);
					} else if (elm instanceof HTMLDDElementImpl) {
						h.addAndGet(17);
					} else {
						h.addAndGet((int)Strings.texMeasure(((Text)child).getTextContent(), elm.getRenderState().getFont()).getHeight());
					}
					break;
				case Node.ELEMENT_NODE:
					final CSSStyleDeclaration currentStyle = ((HTMLElementImpl)child).getCurrentStyle();
					final String height = currentStyle.getHeight();
					if(Strings.isNotBlank(height)){
						h.addAndGet(HtmlValues.getPixelSize(CSSValues.AUTO.isEqual(height) ? "100%" : height, null, doc.getDefaultView(), 0, parentHeight));
					} else {
						h.addAndGet(childHeight((ElementImpl) child, currentStyle.getPosition(), doc, parentHeight));
					}
					break;
				default:
					break;
			}
		});
		return h.get();
	}

	private int childWidth(final ElementImpl elm, final HTMLDocumentImpl doc, final Integer parentWidth) {
		final AtomicInteger h = new AtomicInteger(0);
		elm.getNodeList().forEach(child -> {
			final int type = child.getNodeType();
			switch (type) {
				case Node.ELEMENT_NODE:
					final CSSStyleDeclaration currentStyle = ((HTMLElementImpl)child).getCurrentStyle();
					final String width = currentStyle.getWidth();
					if(Strings.isNotBlank(width)){
						h.addAndGet(HtmlValues.getPixelSize(width, null, doc.getDefaultView(), 0, parentWidth));
					} else {
						h.addAndGet(childWidth((ElementImpl) child, doc, parentWidth));
					}
					break;
				default:
					break;
			}
		});
		return h.get();
	}
}
