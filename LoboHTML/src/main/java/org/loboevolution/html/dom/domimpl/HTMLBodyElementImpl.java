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
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLBodyElement;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.renderstate.BodyRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.mozilla.javascript.Function;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

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
	protected void assignAttributeField(String normalName, String value) {
		if ("onload".equals(normalName)) {
			final Function onload = getEventFunction(null, normalName);
			if (onload != null) {
				setOnload(onload);
			}
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new BodyRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getALink() {
		return getAttribute("alink");
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
		if (document instanceof HTMLDocumentImpl) {
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
	public void setALink(String aLink) {
		setAttribute("alink", aLink);
	}

	/** {@inheritDoc} */
	@Override
	public void setBackground(String background) {
		setAttribute("background", background);
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	@Override
	public String getBgProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBgProperties(String bgProperties) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void setLink(String link) {
		setAttribute("link", link);
	}

	@Override
	public boolean isNoWrap() {
		return false;
	}

	@Override
	public void setNoWrap(boolean noWrap) {

	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>setOnload.</p>
	 */
	public void setOnload(Function onload) {
		final Object document = this.document;
		if (document instanceof HTMLDocumentImpl) {
			// Note that body.onload overrides
			// WindowImpl.onload.
			((HTMLDocumentImpl) document).setOnloadHandler(onload);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setOwnerDocument(Document value) {
		super.setOwnerDocument(value);
		if (value instanceof HTMLDocument) {
			((HTMLDocument) value).setBody(this);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setOwnerDocument(Document value, boolean deep) {
		super.setOwnerDocument(value, deep);
		if (value instanceof HTMLDocument) {
			((HTMLDocument) value).setBody(this);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setText(String text) {
		setAttribute("text", text);
	}

	/** {@inheritDoc} */
	@Override
	public void setVLink(String vLink) {
		setAttribute("vlink", vLink);
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLBodyElement]";
	}
}
