/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLBodyElement;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.renderstate.BodyRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.mozilla.javascript.Function;

/**
 * <p>HTMLBodyElementImpl class.</p>
 */
public class HTMLBodyElementImpl extends HTMLElementImpl implements HTMLBodyElement {

	/**
	 * <p>Constructor for HTMLBodyElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLBodyElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public void assignAttributeField(final String normalName, final String value) {
		if ("onload".equals(normalName)) {
			final Function onload = getFunction(this, normalName);
			if (onload != null) {
				setOnload(onload);
			}
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return new BodyRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getBackground() {
		return getAttribute("background");
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		return getAttribute("bgcolor");
	}

	/** {@inheritDoc} */
	@Override
	public String getLink() {
		return getAttribute("link");
	}

	/**
	 * <p>getOnload.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnload() {
		final Object document = this.document;
		if (document instanceof HTMLDocument) {
			return ((HTMLDocumentImpl) document).getOnloadHandler();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getText() {
		return getAttribute("text");
	}

	/** {@inheritDoc} */
	@Override
	public String getVLink() {
		return getAttribute("vlink");
	}

	/** {@inheritDoc} */
	@Override
	public void setBackground(final String background) {
		setAttribute("background", background);
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(final String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	@Override
	public String getBgProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBgProperties(final String bgProperties) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void setLink(final String link) {
		setAttribute("link", link);
	}

	@Override
	public boolean isNoWrap() {
		return false;
	}

	@Override
	public void setNoWrap(final boolean noWrap) {

	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>setOnload.</p>
	 */
	public void setOnload(final Function onload) {
		final Object document = this.document;
		if (document instanceof HTMLDocument) {
			((HTMLDocumentImpl) document).setOnloadHandler(onload);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setOwnerDocument(final Document value) {
		super.setOwnerDocument(value);
		if (value instanceof HTMLDocument) {
			value.setBody(this);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setOwnerDocument(final Document value, final boolean deep) {
		super.setOwnerDocument(value, deep);
		if (value instanceof HTMLDocument) {
			value.setBody(this);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setText(final String text) {
		setAttribute("text", text);
	}

	/** {@inheritDoc} */
	@Override
	public void setVLink(final String vLink) {
		setAttribute("vlink", vLink);
	}

	/** {@inheritDoc} */
	@Override
	public Node appendChild(final Node newChild) {

		if (newChild instanceof HTMLHtmlElement) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append html");
		}

		if (newChild instanceof DocumentType) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append doc type");
		}

		if (newChild instanceof Document) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append doc");
		}
		return super.appendChild(newChild);
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnafterprint() {
		return getFunction(this, "afterprint");
	}

	/** {@inheritDoc} */
	@Override
	public void setOnafterprint(final Function onafterprint) {
		addEventListener("afterprint", onafterprint, false);
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnbeforeprint() {
		return getFunction(this, "afterprint");
	}

	/** {@inheritDoc} */
	@Override
	public void setOnbeforeprint(final Function onbeforeprint) {
		addEventListener("beforeprint", onbeforeprint, false);
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnlanguagechange() {
		return getFunction(this, "languagechange");
	}

	/** {@inheritDoc} */
	@Override
	public void setOnlanguagechange(final Function onlanguagechange) {
		addEventListener("languagechange", onlanguagechange, false);
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnoffline() {
		return getFunction(this, "offline");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnonline() {
		return getFunction(this, "online");
	}

	/** {@inheritDoc} */
	@Override
	public void setOnonline(final Function ononline) {
		addEventListener("online", ononline, false);
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnunload() {
		return getFunction(this, "unload");
	}

	/** {@inheritDoc} */
	@Override
	public void setOnunload(final Function onunload) {
		addEventListener("unload", onunload, false);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLBodyElement]";
	}
}
