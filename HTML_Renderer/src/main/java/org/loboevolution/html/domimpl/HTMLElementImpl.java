/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.domimpl;

import java.awt.Color;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.loboevolution.html.FormInput;
import org.loboevolution.html.dombl.UINode;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderstate.ColorRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.style.AbstractCSS2Properties;
import org.loboevolution.html.style.CSS2PropertiesContext;
import org.loboevolution.html.style.ComputedCSS2Properties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.LocalCSS2Properties;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.html.style.selectors.AttributeSelector;
import org.loboevolution.util.Strings;
import org.loboevolution.util.gui.LAFSettings;
import org.loboevolution.w3c.html.DOMSettableTokenList;
import org.loboevolution.w3c.html.DOMStringMap;
import org.loboevolution.w3c.html.DOMTokenList;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLPropertiesCollection;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;

/**
 * The Class HTMLElementImpl.
 */
public class HTMLElementImpl extends DOMElementImpl implements HTMLElement, CSS2PropertiesContext {

	/** The current style declaration state. */
	private volatile AbstractCSS2Properties currentStyleDeclarationState;

	/** The local style declaration state. */
	private volatile AbstractCSS2Properties localStyleDeclarationState;
	
	/** The computed styles. */
	private Map<String, AbstractCSS2Properties> computedStyles;

	/** The is mouse over. */
	private boolean isMouseOver = false;

	/** The is hover style. */
	private Boolean isHoverStyle = null;

	/** The has hover style by element. */
	private Map<HTMLElementImpl, Boolean> hasHoverStyleByElement = null;
	
	/**
	 * Instantiates a new HTML element impl.
	 *
	 * @param name
	 *            the name
	 * @param noStyleSheet
	 *            the no style sheet
	 */
	public HTMLElementImpl(String name, boolean noStyleSheet) {
		super(name);
	}

	/**
	 * Instantiates a new HTML element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLElementImpl(String name) {
		super(name);
	}

	/**
	 * Forget local style.
	 */
	protected final void forgetLocalStyle() {
		synchronized (this) {
			this.currentStyleDeclarationState = null;
			this.localStyleDeclarationState = null;
			this.computedStyles = null;
		}
	}

	/**
	 * Forget style.
	 *
	 * @param deep
	 *            the deep
	 */
	protected final void forgetStyle(boolean deep) {
		// TODO: OPTIMIZATION: If we had a ComputedStyle map in
		// window (Mozilla model) the map could be cleared in one shot.
		synchronized (this) {
			this.currentStyleDeclarationState = null;
			this.computedStyles = null;
			this.isHoverStyle = null;
			this.hasHoverStyleByElement = null;
			if (deep) {
				ArrayList<Node> nl = this.nodeList;
				if (nl != null) {
					Iterator<Node> i = nl.iterator();
					while (i.hasNext()) {
						Object node = i.next();
						if (node instanceof HTMLElementImpl) {
							((HTMLElementImpl) node).forgetStyle(deep);
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the current style.
	 *
	 * @return the current style
	 */
	public AbstractCSS2Properties getCurrentStyle() {
		AbstractCSS2Properties sds;
		synchronized (this) {
			sds = this.currentStyleDeclarationState;
			if (sds != null) {
				return sds;
			}
		}
		// Can't do the following in synchronized block (reverse locking order
		// with document).
		// First, add declarations from stylesheet
		sds = this.createDefaultStyleSheet();
		sds = this.addStyleSheetDeclarations(sds, this.getPseudoNames());
		// Now add local style if any.
		AbstractCSS2Properties localStyle = this.getStyle();
		if (sds == null) {
			sds = new ComputedCSS2Properties(this);
			sds.setLocalStyleProperties(localStyle);
		} else {
			sds.setLocalStyleProperties(localStyle);
		}
		synchronized (this) {
			// Check if style properties were set while outside
			// the synchronized block (can happen).
			AbstractCSS2Properties setProps = this.currentStyleDeclarationState;
			if (setProps != null) {
				return setProps;
			}
			this.currentStyleDeclarationState = sds;
			return sds;
		}
	}

	/**
	 * Gets the local style object associated with the element. The properties
	 * object returned only includes properties from the local style attribute.
	 * It may return null only if the type of element does not handle
	 * stylesheets.
	 *
	 * @return the style
	 */
	@Override
	public AbstractCSS2Properties getStyle() {

		AbstractCSS2Properties sds;
		synchronized (this) {
			sds = this.localStyleDeclarationState;
			if (sds != null) {
				return sds;
			}
			sds = new LocalCSS2Properties(this);
			// Add any declarations in style attribute (last takes precedence).
			String style = this.getAttribute(STYLE_HTML);

			if (style != null && style.length() != 0) {
				CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
				InputSource inputSource = this.getCssInputSourceForDecl(style);
				try {
					CSSStyleDeclaration sd = parser.parseStyleDeclaration(inputSource);
					sd.setCssText(style);
					sds.addStyleDeclaration(sd);
				} catch (Exception err) {
					String id = this.getId();
					String withId = id == null ? "" : " with ID '" + id + "'";
					logger.error("Unable to parse style attribute value for element " + this.getTagName() + withId
							+ " in " + this.getDocumentURL() + ".", err);
				}
			}
			this.localStyleDeclarationState = sds;

		}
		// Synchronization note: Make sure getStyle() does not return multiple
		// values.
		return sds;
	}

	/**
	 * Creates the default style sheet.
	 *
	 * @return the abstract cs s2 properties
	 */
	protected AbstractCSS2Properties createDefaultStyleSheet() {
		ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		css.internalSetLC("font-size", String.valueOf((int) LAFSettings.getInstance().getFontSize()) + "px");
		return css;
	}

	/**
	 * Gets the computed style.
	 *
	 * @param pseudoElement
	 *            the pseudo element
	 * @return the computed style
	 */
	public AbstractCSS2Properties getComputedStyle(String pseudoElement) {
		if (pseudoElement == null) {
			pseudoElement = "";
		}
		synchronized (this) {
			Map<String, AbstractCSS2Properties> cs = this.computedStyles;
			if (cs != null) {
				AbstractCSS2Properties sds = cs.get(pseudoElement);
				if (sds != null) {
					return sds;
				}
			}
		}
		// Can't do the following in synchronized block (reverse locking order
		// with document).
		// First, add declarations from stylesheet
		Set<String> pes = pseudoElement.length() == 0 ? null : Collections.singleton(pseudoElement);
		AbstractCSS2Properties sds = this.createDefaultStyleSheet();
		sds = this.addStyleSheetDeclarations(sds, pes);
		// Now add local style if any.
		AbstractCSS2Properties localStyle = this.getStyle();
		if (sds == null) {
			sds = new ComputedCSS2Properties(this);
			sds.setLocalStyleProperties(localStyle);
		} else {
			sds.setLocalStyleProperties(localStyle);
		}
		synchronized (this) {
			// Check if style properties were set while outside
			// the synchronized block (can happen). We need to
			// return instance already set for consistency.
			Map<String, AbstractCSS2Properties> cs = this.computedStyles;
			if (cs == null) {
				cs = new HashMap<String, AbstractCSS2Properties>(2);
				this.computedStyles = cs;
			} else {
				AbstractCSS2Properties sds2 = cs.get(pseudoElement);
				if (sds2 != null) {
					return sds2;
				}
			}
			cs.put(pseudoElement, sds);
		}
		return sds;
	}

	/**
	 * Sets the style.
	 *
	 * @param value
	 *            the new style
	 */
	public void setStyle(String value) {
		this.setAttribute(STYLE_HTML, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getClassName()
	 */
	@Override
	public String getClassName() {
		String className = this.getAttribute(CLASS);
		return className == null ? "" : className;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setClassName(java.lang.String)
	 */
	@Override
	public void setClassName(String className) {
		this.setAttribute(CLASS, className);
	}

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	public String getCharset() {
		return this.getAttribute(CHARSET);
	}

	/**
	 * Sets the charset.
	 *
	 * @param charset
	 *            the new charset
	 */
	public void setCharset(String charset) {
		this.setAttribute(CHARSET, charset);
	}

	/**
	 * Gets the attribute as int.
	 *
	 * @param name
	 *            the name
	 * @param defaultValue
	 *            the default value
	 * @return the attribute as int
	 */
	protected int getAttributeAsInt(String name, int defaultValue) {
		String valueText = this.getAttribute(name);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/**
	 * Gets the attribute as boolean.
	 *
	 * @param name
	 *            the name
	 * @return the attribute as boolean
	 */
	public boolean getAttributeAsBoolean(String name) {
		return this.getAttribute(name) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.domimpl.DOMElementImpl#assignAttributeField(java.
	 * lang .String, java.lang.String)
	 */
	@Override
	protected void assignAttributeField(String normalName, String value) {
		if (!this.notificationsSuspended) {
			this.informInvalidAttibute(normalName);
		} else {
			if ("style".equals(normalName)) {
				this.forgetLocalStyle();
			}
		}
		super.assignAttributeField(normalName, value);
	}

	/**
	 * Gets the css input source for decl.
	 *
	 * @param text
	 *            the text
	 * @return the css input source for decl
	 */
	protected final InputSource getCssInputSourceForDecl(String text) {
		Reader reader = new StringReader(text);
		InputSource is = new InputSource(reader);
		return is;
	}

	/**
	 * Adds style sheet declarations applicable to this element. A properties
	 * object is created if necessary when the one passed is <code>null</code>.
	 *
	 * @param style
	 *            the style
	 * @param pseudoNames
	 *            the pseudo names
	 * @return the abstract cs s2 properties
	 */
	protected final AbstractCSS2Properties addStyleSheetDeclarations(AbstractCSS2Properties style,
			Set<String> pseudoNames) {
		Node pn = this.parentNode;
		if (pn == null) {
			// do later
			return style;
		}
		String classNames = this.getClassName();
		if (classNames != null && classNames.length() != 0) {
			String id = this.getId();
			String elementName = this.getTagName();
			String[] classNameArray = Strings.split(classNames);
			for (int i = classNameArray.length; --i >= 0;) {
				String className = classNameArray[i];
				Collection<CSSStyleDeclaration> sds = this.findStyleDeclarations(elementName, id, className,
						pseudoNames);
				if (sds != null) {
					Iterator<CSSStyleDeclaration> sdsi = sds.iterator();
					while (sdsi.hasNext()) {
						CSSStyleDeclaration sd = sdsi.next();
						if (style == null) {
							style = new ComputedCSS2Properties(this);
						}
						style.addStyleDeclaration(sd);
					}
				}
			}
		} else {
			String id = this.getId();
			String elementName = this.getTagName();
			Collection<CSSStyleDeclaration> sds = this.findStyleDeclarations(elementName, id, null, pseudoNames);
			if (sds != null) {
				Iterator<CSSStyleDeclaration> sdsi = sds.iterator();
				while (sdsi.hasNext()) {
					CSSStyleDeclaration sd = sdsi.next();
					if (style == null) {
						style = new ComputedCSS2Properties(this);
					}
					style.addStyleDeclaration(sd);
				}
			}
		}
		return style;
	}
	
	/**
	 * Sets the mouse over.
	 *
	 * @param mouseOver
	 *            the new mouse over
	 */
	public void setMouseOver(boolean mouseOver) {
		if (this.isMouseOver != mouseOver) {
			// Change isMouseOver field before checking to invalidate.
			this.isMouseOver = mouseOver;
			// Check if descendents are affected (e.g. div:hover a {...} )
			this.invalidateDescendentsForHover();
			if (this.hasHoverStyle()) {
				// TODO: OPTIMIZATION: In some cases it should be much
				// better to simply invalidate the "look" of the node.
				this.informInvalid();
			}
		}
	}

	/**
	 * Invalidate descendents for hover.
	 */
	private void invalidateDescendentsForHover() {
		synchronized (this.getTreeLock()) {
			this.invalidateDescendentsForHoverImpl(this);
		}
	}

	/**
	 * Invalidate descendents for hover impl.
	 *
	 * @param ancestor
	 *            the ancestor
	 */
	private void invalidateDescendentsForHoverImpl(HTMLElementImpl ancestor) {
		ArrayList<Node> nodeList = this.nodeList;
		if (nodeList != null) {
			int size = nodeList.size();
			for (int i = 0; i < size; i++) {
				Object node = nodeList.get(i);
				if (node instanceof HTMLElementImpl) {
					HTMLElementImpl descendent = (HTMLElementImpl) node;
					if (descendent.hasHoverStyle(ancestor)) {
						descendent.informInvalid();
					}
					descendent.invalidateDescendentsForHoverImpl(ancestor);
				}
			}
		}
	}
	
	/**
	 * Checks for hover style.
	 *
	 * @return true, if successful
	 */
	private boolean hasHoverStyle() {
		Boolean ihs;
		synchronized (this) {
			ihs = this.isHoverStyle;
			if (ihs != null) {
				return ihs.booleanValue();
			}
		}
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			ihs = Boolean.FALSE;
		} else {
			StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
			String id = this.getId();
			String elementName = this.getTagName();
			String classNames = this.getClassName();
			String[] classNameArray = null;
			if (classNames != null && classNames.length() != 0) {
				classNameArray = Strings.split(classNames);
			}
			ihs = Boolean
					.valueOf(ssa.affectedByPseudoNameInAncestor(this, this, elementName, id, classNameArray, "hover"));
		}
		synchronized (this) {
			this.isHoverStyle = ihs;
		}
		return ihs.booleanValue();
	}

	/**
	 * Checks for hover style.
	 *
	 * @param ancestor
	 *            the ancestor
	 * @return true, if successful
	 */
	private boolean hasHoverStyle(HTMLElementImpl ancestor) {
		Map<HTMLElementImpl, Boolean> ihs;
		synchronized (this) {
			ihs = this.hasHoverStyleByElement;
			if (ihs != null) {
				Boolean f = ihs.get(ancestor);
				if (f != null) {
					return f.booleanValue();
				}
			}
		}
		Boolean hhs;
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			hhs = Boolean.FALSE;
		} else {
			StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
			String id = this.getId();
			String elementName = this.getTagName();
			String classNames = this.getClassName();
			String[] classNameArray = null;
			if (classNames != null && classNames.length() != 0) {
				classNameArray = Strings.split(classNames);
			}
			hhs = Boolean.valueOf(
					ssa.affectedByPseudoNameInAncestor(this, ancestor, elementName, id, classNameArray, "hover"));
		}
		synchronized (this) {
			ihs = this.hasHoverStyleByElement;
			if (ihs == null) {
				ihs = new HashMap<HTMLElementImpl, Boolean>(2);
				this.hasHoverStyleByElement = ihs;
			}
			ihs.put(ancestor, hhs);
		}
		return hhs.booleanValue();
	}

	/**
	 * Gets the pseudo names.
	 *
	 * @return the pseudo names
	 */
	public Set<String> getPseudoNames() {
		Set<String> pnset = new HashSet<String>();
		pnset.add(AttributeSelector.LAST_CHILD);
		pnset.add(AttributeSelector.LAST_OF_TYPE);
		pnset.add(AttributeSelector.FIRST_CHILD);
		pnset.add(AttributeSelector.FIRST_OF_TYPE);
		pnset.add(AttributeSelector.ONLY_CHILD);
		pnset.add(AttributeSelector.ONLY_OF_TYPE);
		pnset.add(AttributeSelector.NTH_CHILD);
		pnset.add(AttributeSelector.NTH_LAST_CHILD);
		pnset.add(AttributeSelector.NTH_OF_TYPE);
		pnset.add(AttributeSelector.NTH_LAST_OF_TYPE);
		pnset.add(AttributeSelector.HOVER);
		pnset.add(AttributeSelector.ROOT);
		pnset.add(AttributeSelector.EMPTY);
		pnset.add(AttributeSelector.LANG);
		return pnset;
	}

	/**
	 * Find style declarations.
	 *
	 * @param elementName
	 *            the element name
	 * @param id
	 *            the id
	 * @param className
	 *            the class name
	 * @param pseudoNames
	 *            the pseudo names
	 * @return the collection
	 */
	protected final Collection<CSSStyleDeclaration> findStyleDeclarations(String elementName, String id,
			String className, Set<String> pseudoNames) {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			return null;
		}

		StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
		return ssa.getActiveStyleDeclarations(this, elementName, id, className, pseudoNames, getAttributes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#informInvalid()
	 */
	@Override
	public void informInvalid() {
		// This is called when an attribute or child changes.
		this.forgetStyle(false);
		super.informInvalid();
	}

	/**
	 * Inform invalid attibute.
	 *
	 * @param normalName
	 *            the normal name
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
		DOMNodeImpl[] nodeList = this.getChildrenArray();
		if (nodeList != null) {
			for (DOMNodeImpl n : nodeList) {
				if (n instanceof HTMLElementImpl) {
					HTMLElementImpl htmlElementImpl = (HTMLElementImpl) n;
					htmlElementImpl.informInvalidRecursive();
				}
			}
		}
	}

	/**
	 * Gets the form inputs.
	 *
	 * @return the form inputs
	 */
	protected FormInput[] getFormInputs() {
		// Override in input elements
		return null;
	}

	/**
	 * Class match.
	 *
	 * @param classTL
	 *            the class tl
	 * @return true, if successful
	 */
	private boolean classMatch(String classTL) {
		String classNames = this.getClassName();
		if (classNames == null || classNames.length() == 0) {
			return classTL == null;
		}
		StringTokenizer tok = new StringTokenizer(classNames, " \t\r\n");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			if (token.equalsIgnoreCase(classTL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get an ancestor that matches the element tag name given and the style
	 * class given.
	 *
	 * @param elementTL
	 *            An tag name in lowercase or an asterisk (*).
	 * @param classTL
	 *            A class name in lowercase.
	 * @return the ancestor with class
	 */
	public HTMLElementImpl getAncestorWithClass(String elementTL, String classTL) {
		Object nodeObj = this.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && parentElement.classMatch(classTL)) {
				return parentElement;
			}
			return parentElement.getAncestorWithClass(elementTL, classTL);
		} else {
			return null;
		}
	}

	/**
	 * Gets the parent with class.
	 *
	 * @param elementTL
	 *            the element tl
	 * @param classTL
	 *            the class tl
	 * @return the parent with class
	 */
	public HTMLElementImpl getParentWithClass(String elementTL, String classTL) {
		Object nodeObj = this.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && parentElement.classMatch(classTL)) {
				return parentElement;
			}
		}
		return null;
	}

	/**
	 * Gets the preceeding sibling element.
	 *
	 * @return the preceeding sibling element
	 */
	public HTMLElementImpl getPreceedingSiblingElement() {
		Node parentNode = this.getParentNode();
		if (parentNode == null) {
			return null;
		}
		NodeList childNodes = parentNode.getChildNodes();
		if (childNodes == null) {
			return null;
		}
		int length = childNodes.getLength();
		HTMLElementImpl priorElement = null;
		for (int i = 0; i < length; i++) {
			Node child = childNodes.item(i);
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
	 * Gets the preceeding sibling with class.
	 *
	 * @param elementTL
	 *            the element tl
	 * @param classTL
	 *            the class tl
	 * @return the preceeding sibling with class
	 */
	public HTMLElementImpl getPreceedingSiblingWithClass(String elementTL, String classTL) {
		HTMLElementImpl psibling = this.getPreceedingSiblingElement();
		if (psibling != null) {
			String pelementTL = psibling.getTagName().toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && psibling.classMatch(classTL)) {
				return psibling;
			}
		}
		return null;
	}

	/**
	 * Gets the ancestor with id.
	 *
	 * @param elementTL
	 *            the element tl
	 * @param idTL
	 *            the id tl
	 * @return the ancestor with id
	 */
	public HTMLElementImpl getAncestorWithId(String elementTL, String idTL) {
		Object nodeObj = this.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			String pid = parentElement.getId();
			String pidTL = pid == null ? null : pid.toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return parentElement;
			}
			return parentElement.getAncestorWithId(elementTL, idTL);
		} else {
			return null;
		}
	}

	/**
	 * Gets the parent with id.
	 *
	 * @param elementTL
	 *            the element tl
	 * @param idTL
	 *            the id tl
	 * @return the parent with id
	 */
	public HTMLElementImpl getParentWithId(String elementTL, String idTL) {
		Object nodeObj = this.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			String pid = parentElement.getId();
			String pidTL = pid == null ? null : pid.toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return parentElement;
			}
		}
		return null;
	}

	/**
	 * Gets the preceeding sibling with id.
	 *
	 * @param elementTL
	 *            the element tl
	 * @param idTL
	 *            the id tl
	 * @return the preceeding sibling with id
	 */
	public HTMLElementImpl getPreceedingSiblingWithId(String elementTL, String idTL) {
		HTMLElementImpl psibling = this.getPreceedingSiblingElement();
		if (psibling != null) {
			String pelementTL = psibling.getTagName().toLowerCase();
			String pid = psibling.getId();
			String pidTL = pid == null ? null : pid.toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return psibling;
			}
		}
		return null;
	}

	/**
	 * Gets the ancestor.
	 *
	 * @param elementTL
	 *            the element tl
	 * @return the ancestor
	 */
	public HTMLElementImpl getAncestor(String elementTL) {
		Object nodeObj = this.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			if ("*".equals(elementTL)) {
				return parentElement;
			}
			String pelementTL = parentElement.getTagName().toLowerCase();
			if (elementTL.equals(pelementTL)) {
				return parentElement;
			}
			return parentElement.getAncestor(elementTL);
		} else {
			return null;
		}
	}

	/**
	 * Gets the parent.
	 *
	 * @param elementTL
	 *            the element tl
	 * @return the parent
	 */
	public HTMLElementImpl getParent(String elementTL) {
		Object nodeObj = this.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			if ("*".equals(elementTL)) {
				return parentElement;
			}
			String pelementTL = parentElement.getTagName().toLowerCase();
			if (elementTL.equals(pelementTL)) {
				return parentElement;
			}
		}
		return null;
	}

	/**
	 * Gets the preceeding sibling.
	 *
	 * @param elementTL
	 *            the element tl
	 * @return the preceeding sibling
	 */
	public HTMLElementImpl getPreceedingSibling(String elementTL) {
		HTMLElementImpl psibling = this.getPreceedingSiblingElement();
		if (psibling != null) {
			if ("*".equals(elementTL)) {
				return psibling;
			}
			String pelementTL = psibling.getTagName().toLowerCase();
			if (elementTL.equals(pelementTL)) {
				return psibling;
			}
		}
		return null;
	}

	/**
	 * Gets the ancestor for java class.
	 *
	 * @param javaClass
	 *            the java class
	 * @return the ancestor for java class
	 */
	protected Object getAncestorForJavaClass(Class javaClass) {
		Object nodeObj = this.getParentNode();
		if (nodeObj == null || javaClass.isInstance(nodeObj)) {
			return nodeObj;
		} else if (nodeObj instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) nodeObj).getAncestorForJavaClass(javaClass);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setInnerHTML(java.lang.String)
	 */
	@Override
	public void setInnerHTML(String newHtml) {
		HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document == null) {
			logger.error("setInnerHTML(): Element " + this + " does not belong to a document.");
			return;
		}
		HtmlParser parser = new HtmlParser(document.getUserAgentContext(), document, null, null, null);
		synchronized (this) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				nl.clear();
			}
		}
		// Should not synchronize around parser probably.
		try {
			Reader reader = new StringReader(newHtml);
			try {
				parser.parse(reader, this);
			} finally {
				reader.close();
			}
		} catch (Exception thrown) {
			logger.error("setInnerHTML(): Error setting inner HTML.", thrown);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOuterHTML()
	 */
	@Override
	public String getOuterHTML() {
		StringBuffer buffer = new StringBuffer();
		synchronized (this) {
			this.appendOuterHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	/**
	 * Append outer html impl.
	 *
	 * @param buffer
	 *            the buffer
	 */
	public void appendOuterHTMLImpl(StringBuffer buffer) {
		String tagName = this.getTagName();
		buffer.append('<');
		buffer.append(tagName);
		Map<String, String> attributes = this.attributes;
		if (attributes != null) {
			Iterator i = attributes.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				String value = (String) entry.getValue();
				if (value != null) {
					buffer.append(' ');
					buffer.append(entry.getKey());
					buffer.append("=\"");
					buffer.append(Strings.strictHtmlEncode(value, true));
					buffer.append("\"");
				}
			}
		}
		ArrayList<Node> nl = this.nodeList;
		if (nl == null || nl.size() == 0) {
			buffer.append("/>");
			return;
		}
		buffer.append('>');
		this.appendInnerHTMLImpl(buffer);
		buffer.append("</");
		buffer.append(tagName);
		buffer.append('>');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#createRenderState(org.
	 * loboevolution .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		// Overrides DOMNodeImpl method
		// Called in synchronized block already
		if (prevRenderState.getColor() == null) {
			prevRenderState = new ColorRenderState(prevRenderState, Color.BLACK);
		}

		return new StyleSheetRenderState(prevRenderState, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetTop()
	 */
	@Override
	public int getOffsetTop() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetLeft()
	 */
	@Override
	public int getOffsetLeft() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetWidth()
	 */
	@Override
	public int getOffsetWidth() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetHeight()
	 */
	@Override
	public int getOffsetHeight() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.style.CSS2PropertiesContext#getParentStyle()
	 */
	@Override
	public AbstractCSS2Properties getParentStyle() {
		Object parent = this.parentNode;
		if (parent instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) parent).getCurrentStyle();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.style.CSS2PropertiesContext#getDocumentBaseURI()
	 */
	@Override
	public String getDocumentBaseURI() {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			return doc.getBaseURI();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMElementImpl#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "[currentStyle=" + this.getCurrentStyle() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setOuterHTML(java.lang.String)
	 */
	@Override
	public void setOuterHTML(String outerHTML) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLElement#insertAdjacentHTML(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void insertAdjacentHTML(String position, String text) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getClassList()
	 */
	@Override
	public DOMTokenList getClassList() {
		return new DOMTokenListImpl(this, this.getClassName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getDataset()
	 */
	@Override
	public DOMStringMap getDataset() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#click()
	 */
	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#focus()
	 */
	@Override
	public void focus() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#blur()
	 */
	@Override
	public void blur() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getAccessKeyLabel()
	 */
	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getCommandType()
	 */
	@Override
	public String getCommandType() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getLabel()
	 */
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getIcon()
	 */
	@Override
	public String getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetParent()
	 */
	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getItemRef()
	 */
	@Override
	public DOMSettableTokenList getItemRef() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setItemRef(java.lang.String)
	 */
	@Override
	public void setItemRef(String itemRef) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getItemProp()
	 */
	@Override
	public DOMSettableTokenList getItemProp() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setItemProp(java.lang.String)
	 */
	@Override
	public void setItemProp(String itemProp) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getProperties()
	 */
	@Override
	public HTMLPropertiesCollection getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getItemValue()
	 */
	@Override
	public Object getItemValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setItemValue(java.lang.Object)
	 */
	@Override
	public void setItemValue(Object itemValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub

	}

	@Override
	public DOMSettableTokenList getDropzone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDropzone(String dropzone) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub

	}
}
