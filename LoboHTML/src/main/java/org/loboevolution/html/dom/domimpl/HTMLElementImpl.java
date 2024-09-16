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
import org.loboevolution.html.dom.HTMLElement;
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
import org.loboevolution.html.style.CSSPropertiesContext;
import org.loboevolution.html.style.ComputedCSSStyleDeclarationImpl;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.info.PropertyCssInfo;
import org.mozilla.javascript.Function;

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
		return getBoundingClientRect().getX();
	}

	/** {@inheritDoc} */
	@Override
	public int getOffsetTop() {
		return getBoundingClientRect().getY();
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
		final Node node = getParentNode();
		if (node instanceof HTMLElement) {
			final HTMLElementImpl element = (HTMLElementImpl) node;
			final ComputedCSSStyleDeclaration style = this.computedStyles;
			if (!CSSValues.STATIC.isEqual(style.getPosition())) {
				return element;
			}
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
		HtmlController.getInstance().onMouseClick(this, null, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onblur.</p>
	 */
	@Override
	public Function getOnblur() {
		return getEventTarget().getFunction(this, "blur");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onclick.</p>
	 */
	@Override
	public Function getOnclick() {
		return getEventTarget().getFunction(this, "click");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field oncontextmenu.</p>
	 */
	@Override
	public Function getOncontextmenu() {
		return getEventTarget().getFunction(this, "contextmenu");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field ondblclick.</p>
	 */
	@Override
	public Function getOndblclick() {
		return getEventTarget().getFunction(this, "dblclick");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onfocus.</p>
	 */
	@Override
	public Function getOnfocus() {
		return getEventTarget().getFunction(this, "focus");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onfocus.</p>
	 */
	@Override
	public Function getOnfullscreenchange() {
		return getEventTarget().getFunction(this, "focus");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onkeydown.</p>
	 */
	@Override
	public Function getOnkeydown() {
		return getEventTarget().getFunction(this, "keydown");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onkeypress.</p>
	 */
	@Override
	public Function getOnkeypress() {
		return getEventTarget().getFunction(this, "keypress");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onkeyup.</p>
	 */
	@Override
	public Function getOnkeyup() {
		return getEventTarget().getFunction(this, "keyup");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmousedown.</p>
	 */
	@Override
	public Function getOnmousedown() {
		return getEventTarget().getFunction(this, "mousedown");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmousemove.</p>
	 */
	@Override
	public Function getOnmousemove() {
		return getEventTarget().getFunction(this, "mousemove");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmouseout.</p>
	 */
	@Override
	public Function getOnmouseout() {
		return getEventTarget().getFunction(this, "mouseout");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmouseover.</p>
	 */
	@Override
	public Function getOnmouseover() {
		return getEventTarget().getFunction(this, "mouseover");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onmouseup.</p>
	 */
	@Override
	public Function getOnmouseup() {
		return getEventTarget().getFunction(this, "mouseup");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Getter for the field onchange.</p>
	 */
	@Override
	public Function getOnchange() {
		return getEventTarget().getFunction(this, "change");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnabort() {
		return getEventTarget().getFunction(this, "abort");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationcancel() {
		return getEventTarget().getFunction(this, "animationcancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationend() {
		return getEventTarget().getFunction(this, "animationend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationiteration() {
		return getEventTarget().getFunction(this, "animationiteration");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationstart() {
		return getEventTarget().getFunction(this, "animationstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnauxclick() {
		return getEventTarget().getFunction(this, "auxclick");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncancel() {
		return getEventTarget().getFunction(this, "cancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncanplay() {

		return getEventTarget().getFunction(this, "canplay");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncanplaythrough() {
		return getEventTarget().getFunction(this, "canplaythrough");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnclose() {
		return getEventTarget().getFunction(this, "close");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncuechange() {
		return getEventTarget().getFunction(this, "cuechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndrag() {
		return getEventTarget().getFunction(this, "drag");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragend() {
		return getEventTarget().getFunction(this, "dragend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragenter() {
		return getEventTarget().getFunction(this, "dragenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragexit() {
		return getEventTarget().getFunction(this, "dragexit");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragleave() {
		return getEventTarget().getFunction(this, "dragleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragover() {
		return getEventTarget().getFunction(this, "dragover");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragstart() {
		return getEventTarget().getFunction(this, "dragstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndrop() {
		return getEventTarget().getFunction(this, "drop");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndurationchange() {
		return getEventTarget().getFunction(this, "durationchange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnemptied() {
		return getEventTarget().getFunction(this, "emptied");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnended() {
		return getEventTarget().getFunction(this, "ended");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnerror() {
		return getEventTarget().getFunction(this, "error");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnfocusin() {
		return getEventTarget().getFunction(this, "focusin");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnfocusout() {
		return getEventTarget().getFunction(this, "focusout");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOngotpointercapture() {
		return getEventTarget().getFunction(this, "gotpointercapture");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOninput() {
		return getEventTarget().getFunction(this, "input");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOninvalid() {
		return getEventTarget().getFunction(this, "invalid");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnload() {
		return getEventTarget().getFunction(this, "load");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadeddata() {
		return getEventTarget().getFunction(this, "loadeddata");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadedmetadata() {
		return getEventTarget().getFunction(this, "loadedmetadata");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadend() {
		return getEventTarget().getFunction(this, "loadend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadstart() {
		return getEventTarget().getFunction(this, "loadstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnlostpointercapture() {
		return getEventTarget().getFunction(this, "lostpointercapture");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnmouseenter() {
		return getEventTarget().getFunction(this, "mouseenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnmouseleave() {
		return getEventTarget().getFunction(this, "mouseleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpause() {
		return getEventTarget().getFunction(this, "pause");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnplay() {
		return getEventTarget().getFunction(this, "play");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnplaying() {
		return getEventTarget().getFunction(this, "playing");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointercancel() {
		return getEventTarget().getFunction(this, "pointercancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerdown() {
		return getEventTarget().getFunction(this, "pointerdown");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerenter() {
		return getEventTarget().getFunction(this, "pointerenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerleave() {
		return getEventTarget().getFunction(this, "pointerleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointermove() {
		return getEventTarget().getFunction(this, "pointermove");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerout() {
		return getEventTarget().getFunction(this, "pointerout");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerover() {
		return getEventTarget().getFunction(this, "pointerover");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerup() {
		return getEventTarget().getFunction(this, "pointerup");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnprogress() {
		return getEventTarget().getFunction(this, "progress");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnratechange() {
		return getEventTarget().getFunction(this, "ratechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnreset() {
		return getEventTarget().getFunction(this, "reset");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnresize() {
		return getEventTarget().getFunction(this, "resize");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnscroll() {
		return getEventTarget().getFunction(this, "scroll");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsecuritypolicyviolation() {
		return getEventTarget().getFunction(this, "securitypolicyviolation");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnseeked() {
		return getEventTarget().getFunction(this, "seeked");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnseeking() {
		return getEventTarget().getFunction(this, "seeking");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselect() {
		return getEventTarget().getFunction(this, "select");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselectionchange() {
		return getEventTarget().getFunction(this, "selectionchange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselectstart() {
		return getEventTarget().getFunction(this, "selectstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnstalled() {
		return getEventTarget().getFunction(this, "stalled");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsubmit() {
		return getEventTarget().getFunction(this, "submit");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsuspend() {
		return getEventTarget().getFunction(this, "suspend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntimeupdate() {
		return getEventTarget().getFunction(this, "timeupdate");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntoggle() {
		return getEventTarget().getFunction(this, "toggle");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchcancel() {
		return getEventTarget().getFunction(this, "touchcancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchend() {
		return getEventTarget().getFunction(this, "touchend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchmove() {
		return getEventTarget().getFunction(this, "touchmove");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchstart() {
		return getEventTarget().getFunction(this, "touchstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitioncancel() {
		return getEventTarget().getFunction(this, "transitioncancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionend() {
		return getEventTarget().getFunction(this, "transitionend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionrun() {
		return getEventTarget().getFunction(this, "transitionrun");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionstart() {
		return getEventTarget().getFunction(this, "transitionstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnvolumechange() {
		return getEventTarget().getFunction(this, "volumechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnwaiting() {
		return getEventTarget().getFunction(this, "waiting");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnwheel() {
		return getEventTarget().getFunction(this, "wheel");
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocus(final Function onfocus) {
		getEventTarget().addEventListener("focus", onfocus);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnblur(final Function onblur) {
		getEventTarget().addEventListener("blur", onblur);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnclick(final Function onclick) {
		getEventTarget().addEventListener("click", onclick);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndblclick(final Function ondblclick) {
		getEventTarget().addEventListener("dblclick", ondblclick);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmousedown(final Function onmousedown) {
		getEventTarget().addEventListener("mousedown", onmousedown);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseup(final Function onmouseup) {
		getEventTarget().addEventListener("mouseup", onmouseup);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseover(final Function onmouseover) {
		getEventTarget().addEventListener("mouseover", onmouseover);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmousemove(final Function onmousemove) {
		getEventTarget().addEventListener("mousemove", onmousemove);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseout(final Function onmouseout) {
		getEventTarget().addEventListener("mouseout", onmouseout);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeypress(final Function onkeypress) {
		getEventTarget().addEventListener("keypress", onkeypress);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeydown(final Function onkeydown) {
		getEventTarget().addEventListener("keydown", onkeydown);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeyup(final Function onkeyup) {
		getEventTarget().addEventListener("keyup", onkeyup);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncontextmenu(final Function oncontextmenu) {
		getEventTarget().addEventListener("contextmenu", oncontextmenu);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnchange(final Function onchange) {
		getEventTarget().addEventListener("change", onchange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnabort(final Function onabort) {
		getEventTarget().addEventListener("abort", onabort);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnwaiting(final Function onwaiting) {
		getEventTarget().addEventListener("waiting", onwaiting);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnvolumechange(final Function onvolumechange) {
		getEventTarget().addEventListener("volumechange", onvolumechange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionstart(final Function ontransitionstart) {
		getEventTarget().addEventListener("transitionstart", ontransitionstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionrun(final Function ontransitionrun) {
		getEventTarget().addEventListener("transitionrun", ontransitionrun);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionend(final Function ontransitionend) {
		getEventTarget().addEventListener("transitionend", ontransitionend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitioncancel(final Function ontransitioncancel) {
		getEventTarget().addEventListener("transitioncancel", ontransitioncancel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchstart(final Function ontouchstart) {
		getEventTarget().addEventListener("touchstart", ontouchstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntoggle(final Function ontoggle) {
		getEventTarget().addEventListener("toggle", ontoggle);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchmove(final Function ontouchmove) {
		getEventTarget().addEventListener("touchmove", ontouchmove);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchend(final Function ontouchend) {
		getEventTarget().addEventListener("touchend", ontouchend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchcancel(final Function ontouchcancel) {
		getEventTarget().addEventListener("touchcancel", ontouchcancel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOntimeupdate(final Function ontimeupdate) {
		getEventTarget().addEventListener("timeupdate", ontimeupdate);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsubmit(final Function onsubmit) {
		getEventTarget().addEventListener("submit", onsubmit);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnstalled(final Function onstalled) {
		getEventTarget().addEventListener("stalled", onstalled);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselectstart(final Function onselectstart) {
		getEventTarget().addEventListener("selectstart", onselectstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselectionchange(final Function onselectionchange) {
		getEventTarget().addEventListener("selectionchange", onselectionchange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselect(final Function onselect) {
		getEventTarget().addEventListener("select", onselect);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnseeking(final Function onseeking) {
		getEventTarget().addEventListener("seeking", onseeking);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnseeked(final Function onseeked) {
		getEventTarget().addEventListener("seeked", onseeked);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsecuritypolicyviolation(final Function onsecuritypolicyviolation) {
		getEventTarget().addEventListener("securitypolicyviolation", onsecuritypolicyviolation);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnscroll(final Function onscroll) {
		getEventTarget().addEventListener("scroll", onscroll);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnresize(final Function onresize) {
		getEventTarget().addEventListener("resize", onresize);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnreset(final Function onreset) {
		getEventTarget().addEventListener("reset", onreset);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnratechange(final Function onratechange) {
		getEventTarget().addEventListener("ratechange", onratechange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnprogress(final Function onprogress) {
		getEventTarget().addEventListener("progress", onprogress);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerup(final Function onpointerup) {
		getEventTarget().addEventListener("pointerup", onpointerup);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerover(final Function onpointerover) {
		getEventTarget().addEventListener("pointerover", onpointerover);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerout(final Function onpointerout) {
		getEventTarget().addEventListener("pointerout", onpointerout);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnwheel(final Function onwheel) {
		getEventTarget().addEventListener("wheel", onwheel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsuspend(final Function onsuspend) {
		getEventTarget().addEventListener("suspend", onsuspend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointermove(final Function onpointermove) {
		getEventTarget().addEventListener("pointermove", onpointermove);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerleave(final Function onpointerleave) {
		getEventTarget().addEventListener("pointerleave", onpointerleave);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerenter(final Function onpointerenter) {
		getEventTarget().addEventListener("pointerenter", onpointerenter);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerdown(final Function onpointerdown) {
		getEventTarget().addEventListener("pointerdown", onpointerdown);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointercancel(final Function onpointercancel) {
		getEventTarget().addEventListener("pointercancel", onpointercancel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnplaying(final Function onplaying) {
		getEventTarget().addEventListener("playing", onplaying);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnplay(final Function onplay) {
		getEventTarget().addEventListener("play", onplay);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpause(final Function onpause) {
		getEventTarget().addEventListener("pause", onpause);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseleave(final Function onmouseleave) {
		getEventTarget().addEventListener("mouseleave", onmouseleave);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseenter(final Function onmouseenter) {
		getEventTarget().addEventListener("mouseenter", onmouseenter);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnauxclick(final Function onauxclick) {
		getEventTarget().addEventListener("auxclick", onauxclick);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnlostpointercapture(final Function onlostpointercapture) {
		getEventTarget().addEventListener("lostpointercapture", onlostpointercapture);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadstart(final Function onloadstart) {
		getEventTarget().addEventListener("loadstart", onloadstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadend(final Function onloadend) {
		getEventTarget().addEventListener("loadend", onloadend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadedmetadata(final Function onloadedmetadata) {
		getEventTarget().addEventListener("loadedmetadata", onloadedmetadata);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadeddata(final Function onloadeddata) {
		getEventTarget().addEventListener("loadeddata", onloadeddata);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnload(final Function onload) {
		getEventTarget().addEventListener("load", onload);
	}

	/** {@inheritDoc} */
	@Override
	public void setOninvalid(final Function oninvalid) {
		getEventTarget().addEventListener("invalid", oninvalid);
	}

	/** {@inheritDoc} */
	@Override
	public void setOninput(final Function oninput) {
		getEventTarget().addEventListener("input", oninput);
	}

	/** {@inheritDoc} */
	@Override
	public void setOngotpointercapture(final Function ongotpointercapture) {
		getEventTarget().addEventListener("gotpointercapture", ongotpointercapture);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocusout(final Function onfocusout) {
		getEventTarget().addEventListener("focusout", onfocusout);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocusin(final Function onfocusin) {
		getEventTarget().addEventListener("focusin", onfocusin);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnerror(final Function onerror) {
		getEventTarget().addEventListener("error", onerror);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnended(final Function onended) {
		getEventTarget().addEventListener("ended", onended);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnemptied(final Function onemptied) {
		getEventTarget().addEventListener("emptied", onemptied);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndurationchange(final Function ondurationchange) {
		getEventTarget().addEventListener("durationchange", ondurationchange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndrop(final Function ondrop) {
		getEventTarget().addEventListener("drop", ondrop);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragstart(final Function ondragstart) {
		getEventTarget().addEventListener("dragstart", ondragstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragover(final Function ondragover) {
		getEventTarget().addEventListener("dragover", ondragover);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragleave(final Function ondragleave) {
		getEventTarget().addEventListener("dragleave", ondragleave);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragexit(final Function ondragexit) {
		getEventTarget().addEventListener("dragexit", ondragexit);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragenter(final Function ondragenter) {
		getEventTarget().addEventListener("dragenter", ondragenter);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragend(final Function ondragend) {
		getEventTarget().addEventListener("dragend", ondragend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOndrag(final Function ondrag) {
		getEventTarget().addEventListener("drag", ondrag);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncuechange(final Function oncuechange) {
		getEventTarget().addEventListener("cuechange", oncuechange);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnclose(final Function onclose) {
		getEventTarget().addEventListener("close", onclose);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncanplaythrough(final Function oncanplaythrough) {
		getEventTarget().addEventListener("canplaythrough", oncanplaythrough);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncanplay(final Function oncanplay) {
		getEventTarget().addEventListener("canplay", oncanplay);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationstart(final Function onanimationstart) {
		getEventTarget().addEventListener("animationstart", onanimationstart);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationiteration(final Function onanimationiteration) {
		getEventTarget().addEventListener("animationiteration", onanimationiteration);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationend(final Function onanimationend) {
		getEventTarget().addEventListener("animationend", onanimationend);
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationcancel(final Function onanimationcancel) {
		getEventTarget().addEventListener("animationcancel", onanimationcancel);
	}

	/** {@inheritDoc} */
	@Override
	public void setOncancel(final Function oncancel) {
		getEventTarget().addEventListener("cancel", oncancel);
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
