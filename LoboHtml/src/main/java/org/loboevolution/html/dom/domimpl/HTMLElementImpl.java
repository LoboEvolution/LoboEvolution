/*    GNU LESSER GENERAL PUBLIC LICENSE
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
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.DOMTokenList;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.CSSPropertiesContext;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.javacc.CSS3Parser;

/**
 * <p>HTMLElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLElementImpl extends ElementImpl implements HTMLElement, CSSPropertiesContext {
	private Map<String, AbstractCSSProperties> computedStyles;

	private volatile AbstractCSSProperties currentStyleDeclarationState;

	private Map<HTMLElementImpl, Boolean> hasHoverStyleByElement = null;
	private Boolean isHoverStyle = null;

	private boolean isMouseOver = false;

	private volatile AbstractCSSProperties localStyleDeclarationState;

	/**
	 * <p>Constructor for HTMLElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLElementImpl(String name) {
		super(name);
	}

	/**
	 * <p>Constructor for HTMLElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param noStyleSheet a boolean.
	 */
	public HTMLElementImpl(String name, boolean noStyleSheet) {
		super(name);
	}

	/**
	 * Adds style sheet declarations applicable to this element. A properties object
	 * is created if necessary when the one passed is null.
	 *
	 * @param style a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 * @return a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 */
	protected final AbstractCSSProperties addStyleSheetDeclarations(AbstractCSSProperties style) {
		final Node pn = this.parentNode;
		if (pn == null) {
			return style;
		}
		final String classNames = getClassName();
		final String elementName = getTagName();
		final String[] classNameArray = Strings.isNotBlank(classNames) ? Strings.split(classNames) : null;
		final List<CSSStyleSheetImpl.SelectorEntry> matchingRules = findStyleDeclarations(elementName, classNameArray);
		for (CSSStyleSheetImpl.SelectorEntry entry : matchingRules) {
			final CSSStyleDeclarationImpl cssStyleDeclarationImpl = entry.getRule().getStyle();
			if (style == null) {
				style = new AbstractCSSProperties(this);
			}
			style.addStyleDeclaration(cssStyleDeclarationImpl);
		}
		return style;
	}

	/**
	 * <p>appendOuterHTMLImpl.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuilder} object.
	 */
	protected void appendOuterHTMLImpl(StringBuilder buffer) {
		final String tagName = getTagName();
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

	/** {@inheritDoc} */
	@Override
	protected void assignAttributeField(String normalName, String value) {
		if (!this.notificationsSuspended) {
			informInvalidAttibute(normalName);
		} else {
			if ("style".equals(normalName)) {
				forgetLocalStyle();
			}
		}
		super.assignAttributeField(normalName, value);
	}

	private boolean classMatch(String classTL) {
		final String classNames = getClassName();
		if (classNames == null || classNames.length() == 0) {
			return classTL == null;
		}
		final StringTokenizer tok = new StringTokenizer(classNames, " \t\r\n");
		while (tok.hasMoreTokens()) {
			final String token = tok.nextToken();
			if (token.toLowerCase().equals(classTL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>createDefaultStyleSheet.</p>
	 *
	 * @return a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 */
	protected AbstractCSSProperties createDefaultStyleSheet() {
		// Override to provide element defaults.
		return null;
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		// Overrides NodeImpl method
		// Called in synchronized block already
		return new StyleSheetRenderState(prevRenderState, this);
	}

	/**
	 * <p>findStyleDeclarations.</p>
	 *
	 * @param elementName a {@link java.lang.String} object.
	 * @param classes an array of {@link java.lang.String} objects.
	 * @return a {@link java.util.List} object.
	 */
	protected final List<CSSStyleSheetImpl.SelectorEntry> findStyleDeclarations(String elementName, String[] classes) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			return new ArrayList<CSSStyleSheetImpl.SelectorEntry>();
		}
		final StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
		return ssa.getActiveStyleDeclarations(this, elementName, classes);
	}

	/**
	 * <p>forgetLocalStyle.</p>
	 */
	protected final void forgetLocalStyle() {
		synchronized (this) {
			this.currentStyleDeclarationState = null;
			this.localStyleDeclarationState = null;
			this.computedStyles = null;
		}
	}

	/**
	 * <p>forgetStyle.</p>
	 *
	 * @param deep a boolean.
	 */
	protected final void forgetStyle(boolean deep) {
		synchronized (this) {
			this.currentStyleDeclarationState = null;
			this.computedStyles = null;
			this.isHoverStyle = null;
			this.hasHoverStyleByElement = null;
			if (deep) {
				for (Node node : Nodes.iterable(nodeList)) {
					if (node instanceof HTMLElementImpl) {
						((HTMLElementImpl) node).forgetStyle(deep);
					}
				}
			}
		}
	}

	/**
	 * <p>getAncestor.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getAncestor(String elementTL) {
		final Object nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			final HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			if ("*".equals(elementTL)) {
				return parentElement;
			}
			final String pelementTL = parentElement.getTagName().toLowerCase();
			if (elementTL.equals(pelementTL)) {
				return parentElement;
			}
			return parentElement.getAncestor(elementTL);
		} else {
			return null;
		}
	}

	/**
	 * <p>getAncestorForJavaClass.</p>
	 *
	 * @param javaClass a {@link java.lang.Class} object.
	 * @return a {@link java.lang.Object} object.
	 */
	protected Object getAncestorForJavaClass(Class<?> javaClass) {
		final Object nodeObj = getParentNode();
		if (nodeObj == null || javaClass.isInstance(nodeObj)) {
			return nodeObj;
		} else if (nodeObj instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) nodeObj).getAncestorForJavaClass(javaClass);
		} else {
			return null;
		}
	}

	/**
	 * Get an ancestor that matches the element tag name given and the style class
	 * given.
	 *
	 * @param elementTL An tag name in lowercase or an asterisk (*).
	 * @param classTL   A class name in lowercase.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getAncestorWithClass(String elementTL, String classTL) {
		final Object nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			final HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			final String pelementTL = parentElement.getTagName().toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && parentElement.classMatch(classTL)) {
				return parentElement;
			}
			return parentElement.getAncestorWithClass(elementTL, classTL);
		} else {
			return null;
		}
	}

	/**
	 * <p>getAncestorWithId.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @param idTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getAncestorWithId(String elementTL, String idTL) {
		final Object nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			final HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			final String pelementTL = parentElement.getTagName().toLowerCase();
			final String pid = parentElement.getId();
			final String pidTL = pid == null ? null : pid.toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return parentElement;
			}
			return parentElement.getAncestorWithId(elementTL, idTL);
		} else {
			return null;
		}
	}

	/**
	 * <p>getAttributeAsBoolean.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean getAttributeAsBoolean(String name) {
		return getAttribute(name) != null;
	}

	/**
	 * <p>getAttributeAsInt.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param defaultValue a int.
	 * @return a int.
	 */
	protected int getAttributeAsInt(String name, int defaultValue) {
		final String value = getAttribute(name);
		return HtmlValues.getPixelSize(value, null, defaultValue);
	}

	/**
	 * <p>getCharset.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCharset() {
		return getAttribute("charset");
	}

	/** {@inheritDoc} */
	@Override
	public String getClassName() {
		final String className = getAttribute("class");
		return className == null ? "" : className;
	}
	
	/**
	 * <p>getClassList.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.DOMTokenList} object.
	 */
	public DOMTokenList getClassList() {
        return new DOMTokenListImpl(this, this.getClassName());
	}

	/**
	 * <p>getComputedStyle.</p>
	 *
	 * @param pseudoElement a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 */
	public AbstractCSSProperties getComputedStyle(String pseudoElement) {
		if (pseudoElement == null) {
			pseudoElement = "";
		}
		synchronized (this) {
			final Map<String, AbstractCSSProperties> cs = this.computedStyles;
			if (cs != null) {
				final AbstractCSSProperties sds = cs.get(pseudoElement);
				if (sds != null) {
					return sds;
				}
			}
		}

		AbstractCSSProperties sds = createDefaultStyleSheet();
		sds = addStyleSheetDeclarations(sds);
		// Now add local style if any.
		final AbstractCSSProperties localStyle = getStyle();
		if (sds == null) {
			sds = new AbstractCSSProperties(this);
			sds.setLocalStyleProperties(localStyle);
		} else {
			sds.setLocalStyleProperties(localStyle);
		}
		synchronized (this) {
			// Check if style properties were set while outside
			// the synchronized block (can happen). We need to
			// return instance already set for consistency.
			Map<String, AbstractCSSProperties> cs = this.computedStyles;
			if (cs == null) {
				cs = new HashMap<String, AbstractCSSProperties>(2);
				this.computedStyles = cs;
			} else {
				final AbstractCSSProperties sds2 = cs.get(pseudoElement);
				if (sds2 != null) {
					return sds2;
				}
			}
			cs.put(pseudoElement, sds);
		}
		return sds;
	}

	/**
	 * Gets the style object associated with the element. It may return null only if
	 * the type of element does not handle stylesheets.
	 *
	 * @return a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 */
	public AbstractCSSProperties getCurrentStyle() {
		AbstractCSSProperties sds;
		synchronized (this) {
			sds = this.currentStyleDeclarationState;
			if (sds != null) {
				return sds;
			}
		}
		// Can't do the following in synchronized block (reverse locking order with
		// document).
		// First, add declarations from stylesheet
		sds = createDefaultStyleSheet();
		sds = addStyleSheetDeclarations(sds);
		// Now add local style if any.
		final AbstractCSSProperties localStyle = getStyle();
		if (sds == null) {
			sds = new AbstractCSSProperties(this);
			sds.setLocalStyleProperties(localStyle);
		} else {
			sds.setLocalStyleProperties(localStyle);
		}
		synchronized (this) {
			// Check if style properties were set while outside
			// the synchronized block (can happen).
			final AbstractCSSProperties setProps = this.currentStyleDeclarationState;
			if (setProps != null) {
				return setProps;
			}
			this.currentStyleDeclarationState = sds;
			return sds;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getDocumentBaseURI() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			return doc.getBaseURI();
		} else {
			return null;
		}
	}

	/**
	 * Gets form input due to the current element. It should return
	 * null except when the element is a form input element.
	 *
	 * @return an array of {@link org.loboevolution.html.dom.input.FormInput} objects.
	 */
	protected FormInput[] getFormInputs() {
		// Override in input elements
		return null;
	}

	/**
	 * <p>getOffsetHeight.</p>
	 *
	 * @return a int.
	 */
	public int getOffsetHeight() {
		final UINode uiNode = getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().height;
	}

	/**
	 * <p>getOffsetLeft.</p>
	 *
	 * @return a int.
	 */
	public int getOffsetLeft() {
		final UINode uiNode = getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().x;
	}

	/**
	 * <p>getOffsetTop.</p>
	 *
	 * @return a int.
	 */
	public int getOffsetTop() {
		// TODO: Sometimes this can be called while parsing, and
		// browsers generally give the right answer.
		final UINode uiNode = getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().y;
	}

	/**
	 * <p>getOffsetWidth.</p>
	 *
	 * @return a int.
	 */
	public int getOffsetWidth() {
		final UINode uiNode = getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().width;
	}

	/**
	 * <p>getOuterHTML.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getOuterHTML() {
		final StringBuilder buffer = new StringBuilder();
		synchronized (this) {
			appendOuterHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	/**
	 * <p>getParent.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getParent(String elementTL) {
		final Object nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			final HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			if ("*".equals(elementTL)) {
				return parentElement;
			}
			final String pelementTL = parentElement.getTagName().toLowerCase();
			if (elementTL.equals(pelementTL)) {
				return parentElement;
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public AbstractCSSProperties getParentStyle() {
		final Object parent = this.parentNode;
		if (parent instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) parent).getCurrentStyle();
		}
		return null;
	}

	/**
	 * <p>getParentWithClass.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @param classTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getParentWithClass(String elementTL, String classTL) {
		final Object nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			final HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			final String pelementTL = parentElement.getTagName().toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && parentElement.classMatch(classTL)) {
				return parentElement;
			}
		}
		return null;
	}

	/**
	 * <p>getParentWithId.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @param idTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getParentWithId(String elementTL, String idTL) {
		final Object nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			final HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			final String pelementTL = parentElement.getTagName().toLowerCase();
			final String pid = parentElement.getId();
			final String pidTL = pid == null ? null : pid.toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return parentElement;
			}
		}
		return null;
	}

	/**
	 * <p>getPreceedingSibling.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getPreceedingSibling(String elementTL) {
		final HTMLElementImpl psibling = getPreceedingSiblingElement();
		if (psibling != null) {
			if ("*".equals(elementTL)) {
				return psibling;
			}
			final String pelementTL = psibling.getTagName().toLowerCase();
			if (elementTL.equals(pelementTL)) {
				return psibling;
			}
		}
		return null;
	}

	/**
	 * <p>getPreceedingSiblingElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getPreceedingSiblingElement() {
		final Node parentNode = getParentNode();
		if (parentNode == null) {
			return null;
		}
		final NodeList childNodes = parentNode.getChildNodes();
		if (childNodes == null) {
			return null;
		}
		final int length = childNodes.getLength();
		HTMLElementImpl priorElement = null;
		for (int i = 0; i < length; i++) {
			final Node child = childNodes.item(i);
			if (child == this) {
				return priorElement;
			}
			if (child instanceof HTMLElementImpl) {
				priorElement = (HTMLElementImpl) child;
			}
		}
		return null;
	}

	/**
	 * <p>getPreceedingSiblingWithClass.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @param classTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getPreceedingSiblingWithClass(String elementTL, String classTL) {
		final HTMLElementImpl psibling = getPreceedingSiblingElement();
		if (psibling != null) {
			final String pelementTL = psibling.getTagName().toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && psibling.classMatch(classTL)) {
				return psibling;
			}
		}
		return null;
	}

	/**
	 * <p>getPreceedingSiblingWithId.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @param idTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getPreceedingSiblingWithId(String elementTL, String idTL) {
		final HTMLElementImpl psibling = getPreceedingSiblingElement();
		if (psibling != null) {
			final String pelementTL = psibling.getTagName().toLowerCase();
			final String pid = psibling.getId();
			final String pidTL = pid == null ? null : pid.toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return psibling;
			}
		}
		return null;
	}

	/**
	 * Gets the pseudo-element lowercase names currently applicable to this element.
	 * Method must return null if there are no such pseudo-elements.
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<String> getPseudoNames() {
		Set<String> pnset = null;
		if (this.isMouseOver) {
			if (pnset == null) {
				pnset = new HashSet<String>(1);
			}
			pnset.add("hover");
		}
		return pnset;
	}

	/**
	 * Gets the local style object associated with the element. The properties
	 * object returned only includes properties from the local style attribute. It
	 * may return null only if the type of element does not handle stylesheets.
	 *
	 * @return a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 */
	public AbstractCSSProperties getStyle() {
		AbstractCSSProperties sds;
		synchronized (this) {
			sds = this.localStyleDeclarationState;
			if (sds != null) {
				return sds;
			}
			sds = new AbstractCSSProperties(this);
			// Add any declarations in style attribute (last takes precedence).
			final String style = getAttribute("style");
			if (style != null && style.length() != 0) {
                final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
				try {
					final CSSStyleDeclarationImpl sd = parser.parseStyleDeclaration(style);
					sds.addStyleDeclaration(sd);
				} catch (final Exception err) {
					final String id = getId();
					final String withId = id == null ? "" : " with ID '" + id + "'";
					this.warn("Unable to parse style attribute value for element " + getTagName() + withId + " in " + getDocumentURL() + ".", err);
				}
			}
			this.localStyleDeclarationState = sds;
		}
		// Synchronization note: Make sure getStyle() does not return multiple values.
		return sds;
	}

	private boolean hasHoverStyle() {
		Boolean ihs;
		synchronized (this) {
			ihs = this.isHoverStyle;
			if (ihs != null) {
				return ihs;
			}
		}
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			ihs = Boolean.FALSE;
		} else {
			final StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
			final String id = getId();
			final String elementName = getTagName();
			final String classNames = getClassName();
			String[] classNameArray = null;
			if (classNames != null && classNames.length() != 0) {
				classNameArray = Strings.split(classNames);
			}
			ihs = ssa.affectedByPseudoNameInAncestor(this, this, elementName, id, classNameArray, "hover");
		}
		synchronized (this) {
			this.isHoverStyle = ihs;
		}
		return ihs;
	}

	private boolean hasHoverStyle(HTMLElementImpl ancestor) {
		Map<HTMLElementImpl, Boolean> ihs;
		synchronized (this) {
			ihs = this.hasHoverStyleByElement;
			if (ihs != null) {
				final Boolean f = ihs.get(ancestor);
				if (f != null) {
					return f;
				}
			}
		}
		boolean hhs;
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			hhs = Boolean.FALSE;
		} else {
			final StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
			final String id = getId();
			final String elementName = getTagName();
			final String classNames = getClassName();
			String[] classNameArray = null;
			if (classNames != null && classNames.length() != 0) {
				classNameArray = Strings.split(classNames);
			}
			hhs = ssa.affectedByPseudoNameInAncestor(this, ancestor, elementName, id, classNameArray, "hover");
		}
		synchronized (this) {
			ihs = this.hasHoverStyleByElement;
			if (ihs == null) {
				ihs = new HashMap<HTMLElementImpl, Boolean>(2);
				this.hasHoverStyleByElement = ihs;
			}
			ihs.put(ancestor, hhs);
		}
		return hhs;
	}

	/** {@inheritDoc} */
	@Override
	public void informInvalid() {
		// This is called when an attribute or child changes.
		forgetStyle(false);
		super.informInvalid();
	}

	/**
	 * <p>informInvalidAttibute.</p>
	 *
	 * @param normalName a {@link java.lang.String} object.
	 */
	public void informInvalidAttibute(String normalName) {
		if ("style".equals(normalName)) {
			this.forgetLocalStyle();
		}
		forgetStyle(true);
		informInvalidRecursive();

	}
	
	private void informInvalidRecursive() {
		super.informInvalid();
		NodeImpl[] nodeList = this.getChildrenArray();
		if (nodeList != null) {
			for (NodeImpl n : nodeList) {
				if (n instanceof HTMLElementImpl) {
					HTMLElementImpl htmlElementImpl = (HTMLElementImpl) n;
					htmlElementImpl.informInvalidRecursive();
				}
			}
		}
	}

	private void invalidateDescendentsForHover() {
		synchronized (this.treeLock) {
			invalidateDescendentsForHoverImpl(this);
		}
	}

	private void invalidateDescendentsForHoverImpl(HTMLElementImpl ancestor) {
		for (Node node : Nodes.iterable(nodeList)) {
			if (node instanceof HTMLElementImpl) {
				final HTMLElementImpl descendent = (HTMLElementImpl) node;
				if (descendent.hasHoverStyle(ancestor)) {
					descendent.informInvalid();
				}
				descendent.invalidateDescendentsForHoverImpl(ancestor);
			}
		}
	}

	/**
	 * <p>setCharset.</p>
	 *
	 * @param charset a {@link java.lang.String} object.
	 */
	public void setCharset(String charset) {
		setAttribute("charset", charset);
	}

	/** {@inheritDoc} */
	@Override
	public void setClassName(String className) {
		setAttribute("class", className);
	}

	/**
	 * <p>setCurrentStyle.</p>
	 *
	 * @param value a {@link java.lang.Object} object.
	 */
	public void setCurrentStyle(Object value) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Cannot set currentStyle property");
	}
	
	/**
	 * <p>getHidden.</p>
	 *
	 * @return a boolean.
	 */
	public boolean getHidden() {
		return this.getAttribute("hidden") != null;
	}

	/**
	 * <p>setInnerHTML.</p>
	 *
	 * @param newHtml a {@link java.lang.String} object.
	 */
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
	 * <p>setMouseOver.</p>
	 *
	 * @param mouseOver a boolean.
	 */
	public void setMouseOver(boolean mouseOver) {
		if (this.isMouseOver != mouseOver) {
			// Change isMouseOver field before checking to invalidate.
			this.isMouseOver = mouseOver;
			// Check if descendents are affected (e.g. div:hover a { ... } )
			invalidateDescendentsForHover();
			if (this.hasHoverStyle()) {
				// TODO: OPTIMIZATION: In some cases it should be much
				// better to simply invalidate the "look" of the node.
				informInvalid();
			}
		}
	}

	/**
	 * <p>setStyle.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setStyle(String value) {
		this.setAttribute("style", value);
	}
	
    /** {@inheritDoc} */
    @Override
    public String getContentEditable() {
        String contenteditable = this.getAttribute("contenteditable");
        return Strings.isBlank(contenteditable) ? "true" : contenteditable;
    }

    /** {@inheritDoc} */
    @Override
    public void setContentEditable(String contenteditable) {
        this.setAttribute("contenteditable", contenteditable);
    }

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return super.toString() + "[currentStyle=" + getCurrentStyle() + "]";
	}

	/** {@inheritDoc} */
	@Override
	public void warn(String message) {
		logger.log(Level.WARNING, message);
	}

	/** {@inheritDoc} */
	@Override
	public void warn(String message, Throwable err) {
		logger.log(Level.WARNING, message, err);
	}
}
