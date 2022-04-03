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
/*
 * Created on Oct 29, 2005
 */
package org.loboevolution.html.dom.domimpl;

import com.gargoylesoftware.css.dom.DOMException;
import com.gargoylesoftware.css.parser.selector.Selector;
import com.gargoylesoftware.css.parser.selector.SelectorList;
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLBodyElement;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.filter.ClassNameFilter;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.filter.TagNameFilter;
import org.loboevolution.html.dom.nodeimpl.NamedNodeMapImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.dom.nodeimpl.TextImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.js.geom.DOMRectImpl;
import org.loboevolution.html.js.geom.DOMRectListImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.node.js.geom.DOMRect;
import org.loboevolution.html.node.js.geom.DOMRectList;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderer.RBlock;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.html.node.NodeType;
import org.mozilla.javascript.annotations.JSFunction;

import javax.swing.*;
import java.awt.*;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

/**
 * <p>ElementImpl class.</p>
 */
public class ElementImpl extends WindowEventHandlersImpl implements Element {

	private final NamedNodeMapImpl map;

	private final String name;
	
	private String outer;

	private double scrollTop;

	/**
	 * <p>Constructor for ElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public ElementImpl(final String name) {
		this.name = name;
		this.map = new NamedNodeMapImpl(this, new HashMap<>());
	}

	/** {@inheritDoc} */
	@Override
	public boolean equalAttributes(Node arg) {
		if (arg instanceof ElementImpl) {
			return Objects.equals(map, ((ElementImpl) arg).getAttributes());
		} else {
			return false;
		}
	}

	/** {@inheritDoc} */
	@Override
	@JSFunction
	public String getAttribute(String name) {
		final Attr attr = getAttributeNode(name);
		return attr == null ? null : attr.getValue();
	}

	/** {@inheritDoc} */
	@Override
	@JSFunction
	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		final Attr attr = getAttributeNode(localName);
		if (attr != null && ((namespaceURI == null || "*".equals(namespaceURI)) || namespaceURI.equals(attr.getNamespaceURI()))) {
			return attr.getValue();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	@JSFunction
	public Attr getAttributeNode(String name) {
		Attr attribute = map.getNamedItem(name);
		return attribute == null ? null : new AttrImpl(name, attribute.getValue(), true, this, "id".equalsIgnoreCase(name));
	}

	/** {@inheritDoc} */
	@Override
	@JSFunction
	public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
		final Attr attribute = map.getNamedItem(localName);
		if (attribute != null && ((namespaceURI == null || "*".equals(namespaceURI)) || namespaceURI.equals(attribute.getNamespaceURI()))) {
			return new AttrImpl(localName, attribute.getValue(), true, this, "id".equalsIgnoreCase(localName));
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void removeAttribute(String name) {
		map.removeNamedItem(name);
	}

	/** {@inheritDoc} */
	@Override
	public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
		final Attr attr = getAttributeNodeNS(namespaceURI, localName);
		if (attr != null) {
			removeAttribute(attr.getName());
		}
	}

	/** {@inheritDoc} */
	@Override
	public Attr removeAttributeNode(Attr oldAttr) {
		return map.removeNamedItem(oldAttr.getName());
	}

	/** {@inheritDoc} */
	@Override
	public void setAttribute(String name, String value) {

		if (Strings.isBlank(name)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "null name");
		}

		if (!Strings.isValidString(name)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		map.setNamedItem(new AttrImpl(name, value, true, this, "id".equalsIgnoreCase(name)));
		assignAttributeField(name, value);
	}

	/** {@inheritDoc} */
	@Override
	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {

		if (Strings.isBlank(qualifiedName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "null name");
		}

		final AttrImpl attr = new AttrImpl(qualifiedName, value, true, this, "id".equalsIgnoreCase(qualifiedName));
		attr.setNamespaceURI(namespaceURI);
		map.setNamedItem(attr);
		assignAttributeField(qualifiedName, value);
	}

	/** {@inheritDoc} */
	@Override
	public void setIdAttribute(String localName, boolean isId) throws DOMException {
		final AttrImpl attr = (AttrImpl)getAttributeNode(name);
		if(attr != null) attr.setIsId(isId);
	}

		/** {@inheritDoc} */
	@Override
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
		final AttrImpl attr = (AttrImpl)getAttributeNode(localName);
		if (attr != null && ((namespaceURI == null || "*".equals(namespaceURI)) || namespaceURI.equals(attr.getNamespaceURI()))) {
			attr.setIsId(isId);
		}
	}
	/** {@inheritDoc} */
	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId) {
		final AttrImpl attr = (AttrImpl)idAttr;
		attr.setIsId(isId);
	}

	/** {@inheritDoc} */
	@Override
	public Attr setAttributeNode(Attr newAttr) {
		final String value = newAttr.getValue();
		assignAttributeField(newAttr.getName(), value);
		return map.setNamedItem(newAttr);
	}

	/** {@inheritDoc} */
	@Override
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		return setAttributeNode(newAttr);
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
		final String id = getAttribute("id");
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
		if (getNodeName().contains(":")) {
			return getNodeName().split(":")[1];
		}
		return getNodeName();
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return NodeType.ELEMENT_NODE;
	}

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
		for (Node node : nodeList) {
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
		return map.getNamedItem(name) != null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {

		if (Strings.isBlank(localName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "null localName");
		}

		Attr attr = map.getNamedItem(localName);
		return attr != null && namespaceURI.equals(attr.getNamespaceURI());
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasAttributes() {
		return map.getAttributes().size() > 0;
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
	 * <p>setDir.</p>
	 *
	 * @param dir a {@link java.lang.String} object.
	 */
	public void setDir(String dir) {
		setAttribute("dir", dir);
	}

	/** {@inheritDoc} */
	@Override
	public void setId(String id) {
		setAttribute("id", id);
	}

	/**
	 * <p>setInnerText.</p>
	 *
	 * @param newText a {@link java.lang.String} object.
	 */
	public void setInnerText(String newText) {
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
	public void setLang(String lang) {
		setAttribute("lang", lang);
	}

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

	/** {@inheritDoc} */
	@Override
	public void setInnerHTML(String newHtml) {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document != null) {
			final HtmlParser parser = new HtmlParser(document.getUserAgentContext(), document, null, false);
			this.nodeList.clear();
			try {
				try (Reader reader = new StringReader(newHtml)) {
					parser.parse(reader, this);
				}
			} catch (final Exception thrown) {
				this.warn("setInnerHTML(): Error setting inner HTML.", thrown);
			}
		} else {
			this.warn("setInnerHTML(): Element " + this + " does not belong to a document.");
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

	/** {@inheritDoc} */
	@Override
	public DOMTokenList getClassList() {
		DOMTokenListImpl tokList = new DOMTokenListImpl(this);
		final String className = getClassName();
		if(Strings.isNotBlank(className)){
			final String[] listString = className.split(" ");
			List<String> names = Arrays.asList(listString);
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
	public void setClassName(String className) {
		setAttribute("class", className);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientHeight() {
		return calculateHeight(false, true);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientLeft() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		AbstractCSSProperties currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
		return HtmlValues.getPixelSize(currentStyle.getBorderLeftWidth(), null, doc.getDefaultView(), 0);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientTop() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		AbstractCSSProperties currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
		return HtmlValues.getPixelSize(currentStyle.getBorderTopWidth(), null, doc.getDefaultView(), 0);
	}

	/** {@inheritDoc} */
	@Override
	public int getClientWidth() {
		return calculateWidth(false, true);
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
	public void setOuterHTML(String newHtml) {
		this.outer = outerNewHtml(newHtml);
		if (this.outer != null) {
			final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
			if (document != null) {
				final HtmlParser parser = new HtmlParser(document.getUserAgentContext(), document, null, false);
				this.nodeList.clear();
				try {
					try (Reader reader = new StringReader(newHtml)) {
						parser.parse(reader, this);
					}
				} catch (final Exception thrown) {
					this.warn("setOuterHTML(): Error setting inner HTML.", thrown);
				}
			} else {
				this.warn("setOuterHTML(): Element " + this + " does not belong to a document.");
			}
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
		if (Strings.isBlank(selectors)) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "The provided selector is empty.");
		}

		try {
			SelectorList selectorList = CSSUtilities.getSelectorList(selectors);
			if (selectorList != null) {
				for (Selector select : selectorList) {
					if (StyleSheetAggregator.selects(select, this, null)) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Is not a valid selector.");
		}
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
	public void scroll(int x, int y) {
		setScrollLeft(x);
		setScrollTop(y);
	}

	/** {@inheritDoc} */
	@Override
	public void scrollBy(int x, int y) {
		scroll(x, y);
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
	public void scrollTo(int x, int y) {
		scroll(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollHeight() {
		return isVScrollable() ? getClientHeight() : 0;
	}

	/** {@inheritDoc} */
	@Override
    public double getScrollLeft() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
        return isHScrollable() ? htmlRendererContext.getScrollx() : 0;
    }

	/** {@inheritDoc} */
	@Override
    public void setScrollLeft(double scrollLeft) {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();

        if (scrollLeft < 0 || !isHScrollable()) {
            scrollLeft = 0;
        }

        htmlRendererContext.setScrollx(scrollLeft);
        RBlock bodyBlock = (RBlock) this.getUINode();
        if (bodyBlock != null && bodyBlock.getScroll() != null)
            bodyBlock.getScroll().scrollBy(JScrollBar.HORIZONTAL, scrollLeft);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getScrollTop() {
		if (scrollTop > 0) return scrollTop;
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
		return isVScrollable() ? htmlRendererContext.getScrolly() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScrollTop(double scrollTop) {
		this.scrollTop = scrollTop;
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();

        if (scrollTop < 0 || !isVScrollable()) {
            scrollTop = 0;
        }

        htmlRendererContext.setScrolly(scrollTop);
        RBlock bodyBlock = (RBlock) this.getUINode();
        if (bodyBlock != null && bodyBlock.getScroll() != null)
            bodyBlock.getScroll().scrollBy(JScrollBar.VERTICAL, scrollTop);
    }

	/** {@inheritDoc} */
	@Override
	public double getScrollWidth() {
		return isHScrollable() ? getClientWidth() : 0;
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

	/** {@inheritDoc} */
	@Override
	public DOMRect getBoundingClientRect() {

		AbstractCSSProperties currentStyle = ((HTMLElementImpl) this).getCurrentStyle();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final Window win = doc.getDefaultView();
		final RenderState rs  = doc.getRenderState();
		int width = calculateWidth(true, true);
		int height = calculateHeight(true, true);
		int top = HtmlValues.getPixelSize(currentStyle.getTop(), rs, win, 0);
		int left = HtmlValues.getPixelSize(currentStyle.getLeft(), rs, win, 0);

		for (Node n = getParentNode(); n != null; n = n.getParentNode()) {

			if (!(n instanceof HTMLBodyElement) && !(n instanceof TextImpl) && !(n instanceof HTMLDocumentImpl)) {
				HTMLElementImpl p = (HTMLElementImpl) n;
				AbstractCSSProperties pCurrentStyle = p.getCurrentStyle();
				String topTxt = pCurrentStyle.getTop();
				String leftTxt = pCurrentStyle.getLeft();
				int scrollTop = (int) p.getScrollTop();
				int scrollLeft = (int) p.getScrollLeft();
				if (Strings.isNotBlank(topTxt)) {
					top += HtmlValues.getPixelSize(topTxt, rs, win, 0);
				}

				if (Strings.isNotBlank(leftTxt)) {
					left += HtmlValues.getPixelSize(leftTxt, rs, win, 0);
				}

				top -= scrollTop;
				left -= scrollLeft;
			}
		}

		return new DOMRectImpl(width, height, top, 0, 0, left);
	}

	/** {@inheritDoc} */
	@Override
	public DOMRectList getClientRects() {
		DOMRectListImpl list = new DOMRectListImpl();
		AbstractCSSProperties style = ((HTMLElementImpl) this).getCurrentStyle();
		String display = Strings.isNotBlank(style.getDisplay()) ? style.getDisplay() : getAttribute("display");
		if (!"none".equals(display)) {
			for (Node n = getParentNode(); n != null; n = n.getPreviousSibling()) {
				if (!(n instanceof HTMLBodyElement) && !(n instanceof TextImpl) && !(n instanceof HTMLDocumentImpl)) {
					HTMLElementImpl p = (HTMLElementImpl) n;
					AbstractCSSProperties st = p.getStyle();
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
		return new AttributeTypeInfo(false);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByClassName(String classNames) {
		return new HTMLCollectionImpl(this, Arrays.asList(this.getNodeList(new ClassNameFilter(classNames)).toArray()));
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByTagName(String tagname) {
		if ("*".equals(tagname)) {
			return new HTMLCollectionImpl(this, Arrays.asList(this.getNodeList(new ElementFilter(null)).toArray()));
		} else {
			return new HTMLCollectionImpl(this, Arrays.asList(this.getNodeList(new TagNameFilter(tagname)).toArray()));
		}
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElementsByTagNameNS(String namespaceURI, String localName) {
		HTMLCollectionImpl coll = (HTMLCollectionImpl) getElementsByTagName(localName);
		HTMLCollectionImpl list = new HTMLCollectionImpl(this, new ArrayList<>());
		for (Node node : coll) {
			if ((namespaceURI == null || "*".equals(namespaceURI)) || node.getNamespaceURI().equals(namespaceURI)) {
				list.add(node);
			}
		}
		return list;
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
		Stream<Node> stream = nodeList.stream();
		return (Element) stream.filter(n -> n instanceof Element).skip(count - 1).findFirst().orElse(null);
	}

	/** {@inheritDoc} */
	@Override
	public int getChildElementCount() {
		return (int) nodeList.stream().filter(n -> n instanceof Element).count();
	}

	/** {@inheritDoc} */
	@Override
	public Element querySelector(String selectors) {
		try {
			SelectorList selectorList = CSSUtilities.getSelectorList(selectors);
			List<Element> elem = new ArrayList<>();
			if (selectorList != null) {
				NodeListImpl childNodes = (NodeListImpl) getDescendents(new ElementFilter(null), true);
				childNodes.forEach(child -> {
					for (Selector selector : selectorList) {
						if (child instanceof Element && StyleSheetAggregator.selects(selector, child, null)) {
							elem.add((Element) child);
						}
					}
				});
			}
			return elem.size() > 0 ? elem.get(0) : null;
		} catch (Exception e) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Is not a valid selector.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public NodeList querySelectorAll(String selector) {

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
			SelectorList selectorList = CSSUtilities.getSelectorList(selector);
			if (selectorList != null) {
				NodeListImpl childNodes = (NodeListImpl) getDescendents(new ElementFilter(null), true);
				childNodes.forEach(child -> {
					for (Selector select : selectorList) {
						if (child instanceof Element && StyleSheetAggregator.selects(select, child, null)) {
							al.add(child);
						}
					}
				});
			}
			return new NodeListImpl(al);
		} catch (Exception e) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Is not a valid selector.");
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void assignAttributeField(String normalName, String value) {
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

	/**
	 * <p>appendOuterHTMLImpl.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuilder} object.
	 */
	public void appendOuterHTMLImpl(StringBuilder buffer) {
		final String tagName = getTagName().toUpperCase();
		buffer.append('<');
		buffer.append(tagName);
		for (Attr attr : Nodes.iterable(map)) {
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
	
	private String outerNewHtml(final String newHtml) {
		if (newHtml != null) {
			return newHtml.endsWith(">") || ! newHtml.startsWith("<") ? newHtml : newHtml + ">";
		}
		return "";
	}

    private boolean isHScrollable() {
        String overflow;
        AbstractCSSProperties currentStyle = ((HTMLElementImpl) this).getCurrentStyle();
        overflow = currentStyle.getOverflow();
        int widthChild = 0;

        for (final Node child : (NodeListImpl) this.getChildNodes()) {
            if (child instanceof HTMLElementImpl) widthChild += ((HTMLElementImpl) child).getClientWidth();
        }

        return ("scroll".equals(overflow) || "auto".equals(overflow)) && (widthChild > this.getClientWidth());
    }

    private boolean isVScrollable() {
        String overflow;
        AbstractCSSProperties currentStyle = ((HTMLElementImpl) this).getCurrentStyle();
        overflow = currentStyle.getOverflow();
        int heightChild = 0;

        for (final Node child : (NodeListImpl) this.getChildNodes()) {
            if (child instanceof HTMLElementImpl) heightChild += ((HTMLElementImpl) child).getClientHeight();
        }
        return ("scroll".equals(overflow) || "auto".equals(overflow)) && (heightChild > this.getClientHeight());
    }

	protected int calculateWidth(boolean border, boolean padding) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
		final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
		final Dimension preferredSize = htmlPanel.getPreferredSize();
		final AbstractCSSProperties currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
		String width = currentStyle.getWidth();
		String paddingRight = currentStyle.getPaddingRight();
		String paddingLeft = currentStyle.getPaddingLeft();
		String borderLeftWidth = currentStyle.getBorderLeftWidth();
		String borderRightWidth = currentStyle.getBorderRightWidth();
		String boxSizing = currentStyle.getBoxSizing();
		int sizeWidth = preferredSize.width;

		if (this instanceof HTMLBodyElementImpl) {
			width = String.valueOf(doc.getDefaultView().getInnerWidth());
		}

		final Node nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl elem = (HTMLElementImpl)nodeObj;
			if(elem.getClientHeight() != -1) {
				sizeWidth = elem.getClientWidth();
			}
		}

		if (Strings.isBlank(width) || "auto".equalsIgnoreCase(width)) {
			width = "100%";
		}

		int widthSize = HtmlValues.getPixelSize(width, null, doc.getDefaultView(), -1, sizeWidth);

		if ("border-box".equals(boxSizing)) {
			padding = false;
			border = false;
		}

		if (padding) {
			widthSize += HtmlValues.getPixelSize(paddingRight, null, doc.getDefaultView(), 0);
			widthSize += HtmlValues.getPixelSize(paddingLeft, null, doc.getDefaultView(), 0);
		}

		if (border) {
			widthSize += HtmlValues.getPixelSize(borderRightWidth, null, doc.getDefaultView(), 0);
			widthSize += HtmlValues.getPixelSize(borderLeftWidth, null, doc.getDefaultView(), 0);
		}

		return widthSize;
	}

	protected int calculateHeight(boolean border, boolean padding) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
		final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
		final Dimension preferredSize = htmlPanel.getPreferredSize();
		final AbstractCSSProperties currentStyle = ((HTMLElementImpl)this).getCurrentStyle();
		String height = currentStyle.getHeight();
		String paddingTop = currentStyle.getPaddingTop();
		String paddingBottom = currentStyle.getPaddingBottom();
		String borderTopWidth = currentStyle.getBorderTopWidth();
		String borderBottomWidth = currentStyle.getBorderBottomWidth();
		String boxSizing = currentStyle.getBoxSizing();
		int sizeHeight = preferredSize.height;

		if (this instanceof HTMLBodyElementImpl) {
			height = String.valueOf(doc.getDefaultView().getInnerHeight());
		}

		final Node nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl elem = (HTMLElementImpl)nodeObj;
			if(elem.getClientHeight() != -1) {
				sizeHeight = elem.getClientHeight();
			}
		}
		if ("auto".equalsIgnoreCase(height)) {
			height = "100%";
		}

		if (Strings.isBlank(height)) {
			height = "18";
		}

		int heightSize = HtmlValues.getPixelSize(height, null, doc.getDefaultView(), -1, sizeHeight);

		if ("border-box".equals(boxSizing)) {
			padding = false;
			border = false;
		}

		if (padding) {
			heightSize += HtmlValues.getPixelSize(paddingTop, null, doc.getDefaultView(), 0);
			heightSize += HtmlValues.getPixelSize(paddingBottom, null, doc.getDefaultView(), 0);
		}

		if (border) {
			heightSize += HtmlValues.getPixelSize(borderTopWidth, null, doc.getDefaultView(), 0);
			heightSize += HtmlValues.getPixelSize(borderBottomWidth, null, doc.getDefaultView(), 0);
		}

		return heightSize;
	}
}
