/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.CSSStyleSheetImpl;
import org.htmlunit.cssparser.dom.CSSValueImpl;
import org.htmlunit.cssparser.dom.Property;
import org.htmlunit.cssparser.parser.CSSOMParser;
import org.htmlunit.cssparser.parser.javacc.CSS3Parser;
import org.loboevolution.common.Strings;
import org.loboevolution.css.ComputedCSSStyleDeclaration;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.HTMLBodyElement;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.dom.HTMLTableElement;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.html.dom.nodeimpl.ElementImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.events.GlobalEventHandlers;
import org.loboevolution.html.renderer.HtmlController;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.style.*;
import org.loboevolution.info.PropertyCssInfo;
import org.mozilla.javascript.Function;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>HTMLElementImpl class.</p>
 */
@Slf4j
public class HTMLElementImpl extends ElementImpl implements HTMLElement, GlobalEventHandlers, CSSPropertiesContext {

	private ComputedCSSStyleDeclaration computedStyles;

	private CSSStyleDeclaration currentStyleDeclarationState;

	private CSSStyleDeclaration mouseOverStyleDeclarationState = null;

	private CSSStyleDeclarationImpl localStyleDeclarationState = null;

	@Getter
	private boolean hasMouseOver;
	
	/**
	 * <p>Constructor for HTMLElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public void assignAttributeField(final String normalName, final String value) {
		if (!this.notificationsSuspended) {
			informInvalidAttibute(normalName);
		} else {
			if ("style".equals(normalName)) {
				forgetLocalStyle();
			}
		}
		super.assignAttributeField(normalName, value);
	}


	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return new StyleSheetRenderState(prevRenderState, this);
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
	protected final void forgetStyle(final boolean deep) {
		synchronized (this) {
			this.currentStyleDeclarationState = null;
			this.computedStyles = null;
			if (deep) {

				nodeList.forEach(node -> {
					if (node instanceof HTMLElementImpl) {
						((HTMLElementImpl) node).forgetStyle(deep);
					}
				});
			}
		}
	}

	/**
	 * <p>getAncestorForJavaClass.</p>
	 *
	 * @param javaClass a {@link java.lang.Class} object.
	 * @return a {@link java.lang.Object} object.
	 */
	protected Object getAncestorForJavaClass(final Class<?> javaClass) {
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
	 * <p>getAttributeAsBoolean.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean getAttributeAsBoolean(final String name) {
		return getAttribute(name) != null;
	}

	/**
	 * <p>getAttributeAsInt.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param defaultValue a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getAttributeAsInt(final String name, final int defaultValue) {
		final String value = getAttribute(name);
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		return HtmlValues.getPixelSize(value, null, doc.getDefaultView(), defaultValue);
	}

	/**
	 * <p>getCharset.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCharset() {
		return getAttribute("charset");
	}


	public ComputedCSSStyleDeclaration getComputedStyle() {
		return getComputedStyle(getTagName());
	}

	/**
	 * <p>getComputedStyle.</p>
	 *
	 * @param pseudoElem a {@link java.lang.String} object.
	 * @return a {@link ComputedCSSStyleDeclaration} object.
	 */
	public ComputedCSSStyleDeclaration getComputedStyle(final String pseudoElem) {

		String pseudoElement = pseudoElem;

		if (pseudoElement == null) {
			pseudoElement = "";
		}

		final CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) addStyleSheetDeclarations(false, pseudoElement);
		final CSSStyleDeclarationImpl localStyle = (CSSStyleDeclarationImpl) getStyle();
		localStyle.merge(style);
		this.computedStyles = new ComputedCSSStyleDeclarationImpl(this, localStyle);
		return this.computedStyles;
	}

	/**
	 * Gets the style object associated with the element.
	 * It may return null only if the type of element does not handle stylesheets.
	 *
	 * @return a {@link CSSStyleDeclaration} object.
	 */
	public CSSStyleDeclaration getCurrentStyle() {
		CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) this.currentStyleDeclarationState;
		if (style != null) {
			return style;
		}
		style = (CSSStyleDeclarationImpl) addStyleSheetDeclarations(false, getTagName());
		final CSSStyleDeclarationImpl localStyle = (CSSStyleDeclarationImpl) getStyle();
		localStyle.merge(style);
		this.currentStyleDeclarationState = localStyle;
		return this.currentStyleDeclarationState;
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

	/** {@inheritDoc} */
	@Override
	public Integer getOffsetHeight() {
		return calculateHeight(true, true, false);
	}

	/** {@inheritDoc} */
	@Override
	public double getOffsetLeft() {

		StyleSheetRenderState renderState = (StyleSheetRenderState) getRenderState();
		final String left = renderState.getLeft();
		if (this instanceof HTMLBodyElement || (left != null && RenderState.POSITION_STATIC == renderState.getPosition())) {
			return 0;
		}

		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        if(CSSValues.AUTO.isEqual(left) && RenderState.POSITION_FIXED == renderState.getPosition()){
			return HtmlValues.getPixelSize(renderState.getPreviousRenderState().getLeft(), getRenderState(), doc.getDefaultView(), 0);
		}

		int offseLeft = HtmlValues.getPixelSize(left, getRenderState(), doc.getDefaultView(), 0);
		int borderLeftWidth = 0;
		int marginLeft = renderState.getMarginInsets() != null ? renderState.getMarginInsets().getLeft() : 0;
		int paddingLeft = RenderState.POSITION_STATIC != renderState.getPosition() ? renderState.getPaddingInsets() != null ? renderState.getPaddingInsets().getLeft() : 0 : 0;

		Node currentElement = this;
		while (currentElement != null) {
			final Node parentNode = currentElement.getParentNode();
            if (parentNode instanceof HTMLBodyElement && RenderState.POSITION_STATIC == renderState.getPosition() &&
					RenderState.POSITION_RELATIVE == renderState.getPosition()) {
                offseLeft += 8;
            } else if (parentNode instanceof HTMLElement) {
				final HTMLElementImpl parentElement = (HTMLElementImpl) parentNode;
				final CSSStyleDeclaration css = parentElement.getCurrentStyle();
				borderLeftWidth += HtmlValues.getPixelSize(css.getBorderLeftWidth(), getRenderState(), doc.getDefaultView(), 0);
				marginLeft += HtmlValues.getPixelSize(css.getMarginLeft(), getRenderState(), doc.getDefaultView(), 0);
				paddingLeft += HtmlValues.getPixelSize(css.getPaddingLeft(), getRenderState(), doc.getDefaultView(), 0);
			}
			currentElement = currentElement.getParentNode();
		}

		offseLeft += marginLeft;
		offseLeft += borderLeftWidth;
		offseLeft += paddingLeft;

		if (offseLeft == 0 && getParentNode() instanceof HTMLBodyElement) {
			return 8;
		}

		return offseLeft;
	}

	/** {@inheritDoc} */
	@Override
	public int getOffsetTop() {

		StyleSheetRenderState renderState = (StyleSheetRenderState) getRenderState();
		final String top = renderState.getTop();
		if (this instanceof HTMLBodyElement || (top != null && RenderState.POSITION_STATIC == renderState.getPosition())) {
			return 0;
		}

		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if(CSSValues.AUTO.isEqual(top) && RenderState.POSITION_FIXED == renderState.getPosition()){
			return HtmlValues.getPixelSize(renderState.getPreviousRenderState().getTop(), getRenderState(), doc.getDefaultView(), 0);
		}

		int offsetTop = HtmlValues.getPixelSize(top, getRenderState(), doc.getDefaultView(), 0);
		int borderTopWidth = 0;
		int marginTop = renderState.getMarginInsets() != null ? renderState.getMarginInsets().getTop() : 0;
		int paddingTop = RenderState.POSITION_STATIC != renderState.getPosition() ? renderState.getPaddingInsets() != null ? renderState.getPaddingInsets().getTop() : 0 : 0;

		Node currentElement = this;
		while (currentElement != null) {
			final Node parentNode = currentElement.getParentNode();
			if (parentNode instanceof HTMLElement) {
				final HTMLElementImpl parentElement = (HTMLElementImpl) parentNode;
				final CSSStyleDeclaration css = parentElement.getCurrentStyle();
				borderTopWidth += HtmlValues.getPixelSize(css.getBorderTopWidth(), getRenderState(), doc.getDefaultView(), 0);
				if(RenderState.POSITION_STATIC != renderState.getPosition()){
					marginTop += HtmlValues.getPixelSize(css.getMarginTop(), getRenderState(), doc.getDefaultView(), 0);
				}
				paddingTop += HtmlValues.getPixelSize(css.getPaddingTop(), getRenderState(), doc.getDefaultView(), 0);
			}
			currentElement = currentElement.getParentNode();
		}

		offsetTop += marginTop;
		offsetTop += borderTopWidth;
		offsetTop += paddingTop;

		if (offsetTop == 0 && getParentNode() instanceof HTMLBodyElement) {
			return 8;
		}

		return offsetTop;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getOffsetWidth() {
		return calculateWidth(true, true, false);
	}

	/**
	 * <p>getParent.</p>
	 *
	 * @param elementTL a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getParent(final String elementTL) {
		final Object nodeObj = getParentNode();
		if (nodeObj instanceof HTMLElementImpl parentElement) {
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
	public CSSStyleDeclaration getParentStyle() {
		final Object parent = this.parentNode;
		if (parent instanceof HTMLElementImpl elementParent) {
            if (elementParent.currentStyleDeclarationState != null) {
				return elementParent.currentStyleDeclarationState;
			}

			return elementParent.getCurrentStyle();
		}
		return null;
	}

	public CSSStyleDeclaration getStyle() {
		CSSStyleDeclarationImpl styleDeclaration = new CSSStyleDeclarationImpl(this, false);
		if (localStyleDeclarationState == null || localStyleDeclarationState.getLength() == 0) {
			final Attr style = getAttributeNode("style");
			if (style != null && Strings.isNotBlank(style.getValue())) {
				final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
				try {
					styleDeclaration = new CSSStyleDeclarationImpl(this, parser.parseStyleDeclaration(style.getValue()));
				} catch (final Exception err) {
					final String id = getId();
					final String withId = Strings.isBlank(id) ? "" : " with ID '" + id + "'";
					this.warn("Unable to parse style attribute value for element " + getTagName() + withId + " in " + getDocumentURL() + ".", err);
				}
			}
			this.localStyleDeclarationState = propertyValueProcessed(styleDeclaration);
		}

		return this.localStyleDeclarationState;
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
	public void informInvalidAttibute(final String normalName) {
		if ("style".equals(normalName)) {
			this.forgetLocalStyle();
		}
		forgetStyle(true);
		informInvalidRecursive();

	}

	private void informInvalidRecursive() {
		super.informInvalid();
		final NodeListImpl nodeList = this.getNodeList();
		if (nodeList != null) {
			nodeList.forEach(n -> {
				if (n instanceof HTMLElementImpl htmlElementImpl) {
                    htmlElementImpl.informInvalidRecursive();
				}
			});
		}
	}

	/**
	 * <p>setCharset.</p>
	 *
	 * @param charset a {@link java.lang.String} object.
	 */
	public void setCharset(final String charset) {
		setAttribute("charset", charset);
	}
	
	/**
	 * <p>setMouseOver.</p>
	 *
	 * @param mouseOver a boolean.
	 */
	public void setMouseOver(final boolean mouseOver) {
		if (hasMouseOver) {
			if (mouseOver) {
				if (mouseOverStyleDeclarationState != null) {
					currentStyleDeclarationState = mouseOverStyleDeclarationState;
				} else {
					currentStyleDeclarationState = addStyleSheetDeclarations(true, getTagName());
					mouseOverStyleDeclarationState = currentStyleDeclarationState;
				}

				informInvalidRecursive();
			} else {
				forgetStyle(true);
				informInvalidRecursive();
			}
		}
	}

	/**
	 * <p>setStyle.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 *
	 */
	public void setStyle(final String value) {
		this.setAttribute("style", value);
	}
	
    /** {@inheritDoc} */
    @Override
    public String getContentEditable() {
        final String contenteditable = this.getAttribute("contenteditable");
        return Strings.isBlank(contenteditable) ? "true" : contenteditable;
    }

    /** {@inheritDoc} */
    @Override
    public void setContentEditable(final String contenteditable) {
        this.setAttribute("contenteditable", contenteditable);
    }
 
	/** {@inheritDoc} */
	@Override
	public void warn(final String message) {
		log.warn(message);
	}

	/** {@inheritDoc} */
	@Override
	public void warn(final String message, final Throwable err) {
		log.warn(message, err);
	}

	/** {@inheritDoc} */
	@Override
	public String getAccessKey() {
		return getAttribute("accessKey");
	}

	/** {@inheritDoc} */
	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getAutocapitalize() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
    public Element getOffsetParent() {
        final Node parent = getParentNode();
        final RenderState rs = getRenderState();
        if (parent == null || rs == null) {
            return null;
        }

        Node currentElement = this;

        if (RenderState.POSITION_FIXED == rs.getPosition()) {
            return null;
        }

        final boolean staticPos = RenderState.POSITION_STATIC == rs.getPosition();
        while (currentElement != null) {
            final Node parentNode = currentElement.getParentNode();
            if (parentNode instanceof HTMLBodyElement
                    || (staticPos && parentNode instanceof HTMLTableElement)
                    || (staticPos && parentNode instanceof HTMLTableCellElement)) {
                return (HTMLElementImpl) parentNode;
            }

            if (parentNode instanceof HTMLElement) {
                final HTMLElementImpl parentElement = (HTMLElementImpl) parentNode;
                final RenderState parentRs = parentElement.getRenderState();
                if (parentRs != null && RenderState.POSITION_STATIC != parentRs.getPosition()) {
                    return (HTMLElementImpl) parentNode;
                }
            }

            currentElement = currentElement.getParentNode();
        }

        return null;
    }

	/** {@inheritDoc} */
	@Override
	public boolean isSpellcheck() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isHidden() {
		final String hidden = getAttribute("hidden");
		return hidden != null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setAccessKey(final String accessKey) {
		setAttribute("accessKey", accessKey);
	}

	/** {@inheritDoc} */
	@Override
	public void setAutocapitalize(final String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setDraggable(final boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setHidden(final boolean hidden) {
		setAttribute("hidden", String.valueOf(hidden));
	}

	/** {@inheritDoc} */
	@Override
	public void setSpellcheck(final boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setTranslate(final boolean translate) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void click() {
		MouseEvent event = new MouseEvent(
				new JPanel(), MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
				0, 0, 0, 1, false, 1);
		HtmlController.getInstance().onMouseClick(this, event, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onblur.</p>
	 */
	@Override
	public Function getOnblur() {
		return getFunction(this, "blur");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onclick.</p>
	 */
	@Override
	public Function getOnclick() {
		return getFunction(this, "click");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field oncontextmenu.</p>
	 */
	@Override
	public Function getOncontextmenu() {
		return getFunction(this, "contextmenu");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field ondblclick.</p>
	 */
	@Override
	public Function getOndblclick() {
		return getFunction(this, "dblclick");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onfocus.</p>
	 */
	@Override
	public Function getOnfocus() {
		return getFunction(this, "focus");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onfocus.</p>
	 */
	@Override
	public Function getOnfullscreenchange() {
		return getFunction(this, "focus");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onkeydown.</p>
	 */
	@Override
	public Function getOnkeydown() {
		return getFunction(this, "keydown");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onkeypress.</p>
	 */
	@Override
	public Function getOnkeypress() {
		return getFunction(this, "keypress");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onkeyup.</p>
	 */
	@Override
	public Function getOnkeyup() {
		return getFunction(this, "keyup");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmousedown.</p>
	 */
	@Override
	public Function getOnmousedown() {
		return getFunction(this, "mousedown");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmousemove.</p>
	 */
	@Override
	public Function getOnmousemove() {
		return getFunction(this, "mousemove");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmouseout.</p>
	 */
	@Override
	public Function getOnmouseout() {
		return getFunction(this, "mouseout");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmouseover.</p>
	 */
	@Override
	public Function getOnmouseover() {
		return getFunction(this, "mouseover");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmouseup.</p>
	 */
	@Override
	public Function getOnmouseup() {
		return getFunction(this, "mouseup");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onchange.</p>
	 */
	@Override
	public Function getOnchange() {
		return getFunction(this, "change");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnabort() {
		return getFunction(this, "abort");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationcancel() {
		return getFunction(this, "animationcancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationend() {
		return getFunction(this, "animationend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationiteration() {
		return getFunction(this, "animationiteration");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationstart() {
		return getFunction(this, "animationstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnauxclick() {
		return getFunction(this, "auxclick");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncancel() {
		return getFunction(this, "cancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncanplay() {

		return getFunction(this, "canplay");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncanplaythrough() {
		return getFunction(this, "canplaythrough");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnclose() {
		return getFunction(this, "close");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncuechange() {
		return getFunction(this, "cuechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndrag() {
		return getFunction(this, "drag");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragend() {
		return getFunction(this, "dragend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragenter() {
		return getFunction(this, "dragenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragexit() {
		return getFunction(this, "dragexit");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragleave() {
		return getFunction(this, "dragleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragover() {
		return getFunction(this, "dragover");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragstart() {
		return getFunction(this, "dragstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndrop() {
		return getFunction(this, "drop");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndurationchange() {
		return getFunction(this, "durationchange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnemptied() {
		return getFunction(this, "emptied");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnended() {
		return getFunction(this, "ended");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnerror() {
		return getFunction(this, "error");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnfocusin() {
		return getFunction(this, "focusin");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnfocusout() {
		return getFunction(this, "focusout");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOngotpointercapture() {
		return getFunction(this, "gotpointercapture");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOninput() {
		return getFunction(this, "input");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOninvalid() {
		return getFunction(this, "invalid");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnload() {
		return getFunction(this, "load");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadeddata() {
		return getFunction(this, "loadeddata");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadedmetadata() {
		return getFunction(this, "loadedmetadata");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadend() {
		return getFunction(this, "loadend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadstart() {
		return getFunction(this, "loadstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnlostpointercapture() {
		return getFunction(this, "lostpointercapture");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnmouseenter() {
		return getFunction(this, "mouseenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnmouseleave() {
		return getFunction(this, "mouseleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpause() {
		return getFunction(this, "pause");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnplay() {
		return getFunction(this, "play");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnplaying() {
		return getFunction(this, "playing");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointercancel() {
		return getFunction(this, "pointercancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerdown() {
		return getFunction(this, "pointerdown");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerenter() {
		return getFunction(this, "pointerenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerleave() {
		return getFunction(this, "pointerleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointermove() {
		return getFunction(this, "pointermove");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerout() {
		return getFunction(this, "pointerout");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerover() {
		return getFunction(this, "pointerover");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerup() {
		return getFunction(this, "pointerup");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnprogress() {
		return getFunction(this, "progress");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnratechange() {
		return getFunction(this, "ratechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnreset() {
		return getFunction(this, "reset");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnresize() {
		return getFunction(this, "resize");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnscroll() {
		return getFunction(this, "scroll");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsecuritypolicyviolation() {
		return getFunction(this, "securitypolicyviolation");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnseeked() {
		return getFunction(this, "seeked");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnseeking() {
		return getFunction(this, "seeking");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselect() {
		return getFunction(this, "select");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselectionchange() {
		return getFunction(this, "selectionchange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselectstart() {
		return getFunction(this, "selectstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnstalled() {
		return getFunction(this, "stalled");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsubmit() {
		return getFunction(this, "submit");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsuspend() {
		return getFunction(this, "suspend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntimeupdate() {
		return getFunction(this, "timeupdate");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntoggle() {
		return getFunction(this, "toggle");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchcancel() {
		return getFunction(this, "touchcancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchend() {
		return getFunction(this, "touchend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchmove() {
		return getFunction(this, "touchmove");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchstart() {
		return getFunction(this, "touchstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitioncancel() {
		return getFunction(this, "transitioncancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionend() {
		return getFunction(this, "transitionend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionrun() {
		return getFunction(this, "transitionrun");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionstart() {
		return getFunction(this, "transitionstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnvolumechange() {
		return getFunction(this, "volumechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnwaiting() {
		return getFunction(this, "waiting");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnwheel() {
		return getFunction(this, "wheel");
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocus(final Function onfocus) {
		addEventListener("focus", onfocus);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnblur(final Function onblur) {
		addEventListener("blur", onblur);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnclick(final Function onclick) {
		addEventListener("click", onclick);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndblclick(final Function ondblclick) {
		addEventListener("dblclick", ondblclick);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmousedown(final Function onmousedown) {
		addEventListener("mousedown", onmousedown);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseup(final Function onmouseup) {
		addEventListener("mouseup", onmouseup);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseover(final Function onmouseover) {
		addEventListener("mouseover", onmouseover);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmousemove(final Function onmousemove) {
		addEventListener("mousemove", onmousemove);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseout(final Function onmouseout) {
		addEventListener("mouseout", onmouseout);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeypress(final Function onkeypress) {
		addEventListener("keypress", onkeypress);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeydown(final Function onkeydown) {
		addEventListener("keydown", onkeydown);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeyup(final Function onkeyup) {
		addEventListener("keyup", onkeyup);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncontextmenu(final Function oncontextmenu) {
		addEventListener("contextmenu", oncontextmenu);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnchange(final Function onchange) {
		addEventListener("change", onchange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnabort(final Function onabort) {
		addEventListener("abort", onabort);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnwaiting(final Function onwaiting) {
		addEventListener("waiting", onwaiting);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnvolumechange(final Function onvolumechange) {
		addEventListener("volumechange", onvolumechange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionstart(final Function ontransitionstart) {
		addEventListener("transitionstart", ontransitionstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionrun(final Function ontransitionrun) {
		addEventListener("transitionrun", ontransitionrun);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionend(final Function ontransitionend) {
		addEventListener("transitionend", ontransitionend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitioncancel(final Function ontransitioncancel) {
		addEventListener("transitioncancel", ontransitioncancel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchstart(final Function ontouchstart) {
		addEventListener("touchstart", ontouchstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntoggle(final Function ontoggle) {
		addEventListener("toggle", ontoggle);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchmove(final Function ontouchmove) {
		addEventListener("touchmove", ontouchmove);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchend(final Function ontouchend) {
		addEventListener("touchend", ontouchend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchcancel(final Function ontouchcancel) {
		addEventListener("touchcancel", ontouchcancel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntimeupdate(final Function ontimeupdate) {
		addEventListener("timeupdate", ontimeupdate);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsubmit(final Function onsubmit) {
		addEventListener("submit", onsubmit);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnstalled(final Function onstalled) {
		addEventListener("stalled", onstalled);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselectstart(final Function onselectstart) {
		addEventListener("selectstart", onselectstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselectionchange(final Function onselectionchange) {
		addEventListener("selectionchange", onselectionchange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselect(final Function onselect) {
		addEventListener("select", onselect);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnseeking(final Function onseeking) {
		addEventListener("seeking", onseeking);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnseeked(final Function onseeked) {
		addEventListener("seeked", onseeked);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsecuritypolicyviolation(final Function onsecuritypolicyviolation) {
		addEventListener("securitypolicyviolation", onsecuritypolicyviolation);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnscroll(final Function onscroll) {
		addEventListener("scroll", onscroll);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnresize(final Function onresize) {
		addEventListener("resize", onresize);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnreset(final Function onreset) {
		addEventListener("reset", onreset);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnratechange(final Function onratechange) {
		addEventListener("ratechange", onratechange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnprogress(final Function onprogress) {
		addEventListener("progress", onprogress);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerup(final Function onpointerup) {
		addEventListener("pointerup", onpointerup);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerover(final Function onpointerover) {
		addEventListener("pointerover", onpointerover);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerout(final Function onpointerout) {
		addEventListener("pointerout", onpointerout);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnwheel(final Function onwheel) {
		addEventListener("wheel", onwheel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsuspend(final Function onsuspend) {
		addEventListener("suspend", onsuspend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointermove(final Function onpointermove) {
		addEventListener("pointermove", onpointermove);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerleave(final Function onpointerleave) {
		addEventListener("pointerleave", onpointerleave);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerenter(final Function onpointerenter) {
		addEventListener("pointerenter", onpointerenter);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerdown(final Function onpointerdown) {
		addEventListener("pointerdown", onpointerdown);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointercancel(final Function onpointercancel) {
		addEventListener("pointercancel", onpointercancel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnplaying(final Function onplaying) {
		addEventListener("playing", onplaying);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnplay(final Function onplay) {
		addEventListener("play", onplay);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpause(final Function onpause) {
		addEventListener("pause", onpause);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseleave(final Function onmouseleave) {
		addEventListener("mouseleave", onmouseleave);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseenter(final Function onmouseenter) {
		addEventListener("mouseenter", onmouseenter);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnauxclick(final Function onauxclick) {
		addEventListener("auxclick", onauxclick);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnlostpointercapture(final Function onlostpointercapture) {
		addEventListener("lostpointercapture", onlostpointercapture);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadstart(final Function onloadstart) {
		addEventListener("loadstart", onloadstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadend(final Function onloadend) {
		addEventListener("loadend", onloadend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadedmetadata(final Function onloadedmetadata) {
		addEventListener("loadedmetadata", onloadedmetadata);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadeddata(final Function onloadeddata) {
		addEventListener("loadeddata", onloadeddata);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnload(final Function onload) {
		addEventListener("load", onload);
	}

	/** {@inheritDoc} */
	@Override
	public void setOninvalid(final Function oninvalid) {
		addEventListener("invalid", oninvalid);
	}

	/** {@inheritDoc} */
	@Override
	public void setOninput(final Function oninput) {
		addEventListener("input", oninput);
	}

	/** {@inheritDoc} */
	@Override
	public void setOngotpointercapture(final Function ongotpointercapture) {
		addEventListener("gotpointercapture", ongotpointercapture);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocusout(final Function onfocusout) {
		addEventListener("focusout", onfocusout);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocusin(final Function onfocusin) {
		addEventListener("focusin", onfocusin);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnerror(final Function onerror) {
		addEventListener("error", onerror);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnended(final Function onended) {
		addEventListener("ended", onended);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnemptied(final Function onemptied) {
		addEventListener("emptied", onemptied);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndurationchange(final Function ondurationchange) {
		addEventListener("durationchange", ondurationchange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndrop(final Function ondrop) {
		addEventListener("drop", ondrop);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragstart(final Function ondragstart) {
		addEventListener("dragstart", ondragstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragover(final Function ondragover) {
		addEventListener("dragover", ondragover);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragleave(final Function ondragleave) {
		addEventListener("dragleave", ondragleave);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragexit(final Function ondragexit) {
		addEventListener("dragexit", ondragexit);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragenter(final Function ondragenter) {
		addEventListener("dragenter", ondragenter);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragend(final Function ondragend) {
		addEventListener("dragend", ondragend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndrag(final Function ondrag) {
		addEventListener("drag", ondrag);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncuechange(final Function oncuechange) {
		addEventListener("cuechange", oncuechange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnclose(final Function onclose) {
		addEventListener("close", onclose);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncanplaythrough(final Function oncanplaythrough) {
		addEventListener("canplaythrough", oncanplaythrough);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncanplay(final Function oncanplay) {
		addEventListener("canplay", oncanplay);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationstart(final Function onanimationstart) {
		addEventListener("animationstart", onanimationstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationiteration(final Function onanimationiteration) {
		addEventListener("animationiteration", onanimationiteration);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationend(final Function onanimationend) {
		addEventListener("animationend", onanimationend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationcancel(final Function onanimationcancel) {
		addEventListener("animationcancel", onanimationcancel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncancel(final Function oncancel) {
		addEventListener("cancel", oncancel);
	}

	/**
	 * <p>setOnoffline.</p>
	 *
	 * @param onoffline a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnoffline(final Function onoffline) {
		// TODO Auto-generated method stub
	}

	/**
	 * <p>findStyleDeclarations.</p>
	 *
	 * @param elementName a {@link java.lang.String} object.
	 * @param classes an array of {@link java.lang.String} objects.
	 * @param mouseOver a {@link java.lang.Boolean } object.
	 * @return a {@link java.util.List} object.
	 */
	public final List<CSSStyleSheetImpl.SelectorEntry> findStyleDeclarations(final String elementName, final String[] classes, final boolean mouseOver) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;

		if (doc == null) {
			return new ArrayList<>();
		}
		final StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
		ssa.setDoc(doc);
		final List<CSSStyleSheetImpl.SelectorEntry> list = ssa.getActiveStyleDeclarations(this, elementName, classes, mouseOver);
		hasMouseOver = ssa.isMouseOver();
		return list;
	}

	/**
	 * Adds style sheet declarations applicable to this element. A properties object
	 * is created if necessary when the one passed is null.
	 * @param mouseOver a {@link java.lang.Boolean } object.
	 * @return a {@link CSSStyleDeclaration} object.
	 */
	private CSSStyleDeclaration addStyleSheetDeclarations(final boolean mouseOver, final String elementName) {

		final CSSStyleDeclarationImpl localStyleDeclarationState = new CSSStyleDeclarationImpl(this, true);
		final String classNames = getClassName();
		final String[] classNameArray = Strings.isNotBlank(classNames) ? Strings.split(classNames) : null;

		final List<CSSStyleSheetImpl.SelectorEntry> matchingRules = findStyleDeclarations(elementName, classNameArray, mouseOver);
		for (final CSSStyleSheetImpl.SelectorEntry entry : matchingRules) {
			localStyleDeclarationState.getProperties().addAll(entry.getRule().getStyle().getProperties());
		}
		return propertyValueProcessed(localStyleDeclarationState);
	}

	private CSSStyleDeclarationImpl propertyValueProcessed(final CSSStyleDeclarationImpl localStyleDeclarationState) {
		try {
			final List<PropertyCssInfo> properties3 = new ArrayList<>();
			final List<Property> properties = localStyleDeclarationState.getProperties();
			properties.forEach(prop -> {
				final CSSValueImpl propertyValue = prop.getValue();
				properties3.add(new PropertyCssInfo(prop.getName(), propertyValue != null ? propertyValue.getCssText() : null, prop.isImportant()));
			});

			properties3.forEach(prop -> localStyleDeclarationState.setPropertyValueProcessed(prop.getName(), prop.getValue(), prop.isImportant()));

			return localStyleDeclarationState;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLElement]";
	}
}
