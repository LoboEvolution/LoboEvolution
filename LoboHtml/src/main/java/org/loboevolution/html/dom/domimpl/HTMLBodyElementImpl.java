/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
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
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLBodyElementImpl extends HTMLAbstractUIElement implements HTMLBodyElement {
	/**
	 * <p>Constructor for HTMLBodyElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLBodyElementImpl(String name) {
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

	/** {@inheritDoc} */
	@Override
	public void setLink(String link) {
		setAttribute("link", link);
	}

	/**
	 * <p>setOnload.</p>
	 *
	 * @param onload a {@link org.mozilla.javascript.Function} object.
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

	@Override
	public String getAccessKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAutocapitalize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSpellcheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutocapitalize(String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDraggable(boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslate(boolean translate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnafterprint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnafterprint(Function onafterprint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnbeforeprint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnbeforeprint(Function onbeforeprint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnlanguagechange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnlanguagechange(Function onlanguagechange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnoffline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnoffline(Function onoffline) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnonline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnonline(Function ononline) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnunload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnunload(Function onunload) {
		// TODO Auto-generated method stub
		
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

	@Override
	public boolean isNoWrap() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNoWrap(boolean noWrap) {
		// TODO Auto-generated method stub
		
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLBodyElement]";
	}
}
