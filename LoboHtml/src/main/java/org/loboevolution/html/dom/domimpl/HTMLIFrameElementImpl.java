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

package org.loboevolution.html.dom.domimpl;

import java.awt.Dimension;
import java.net.URL;
import java.util.logging.Level;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.control.FrameControl;
import org.loboevolution.html.dom.HTMLIFrameElement;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.renderstate.IFrameRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.node.DOMTokenList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.js.WindowProxy;

/**
 * <p>HTMLIFrameElementImpl class.</p>
 *
 *
 *
 */
public class HTMLIFrameElementImpl extends HTMLElementImpl implements HTMLIFrameElement {

	/**
	 * <p>Constructor for HTMLIFrameElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLIFrameElementImpl(String name) {
		super(name);
	}
	
	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new IFrameRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public Document getContentDocument() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getFrameBorder() {
		return getAttribute("frameborder");
	}

	/** {@inheritDoc} */
	@Override
	public String getHeight() {
		return getAttribute("height");
	}

	/** {@inheritDoc} */
	@Override
	public String getLongDesc() {
		return getAttribute("longdesc");
	}

	/** {@inheritDoc} */
	@Override
	public String getMarginHeight() {
		return getAttribute("marginheight");
	}

	/** {@inheritDoc} */
	@Override
	public String getMarginWidth() {
		return getAttribute("marginwidth");
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}

	/** {@inheritDoc} */
	@Override
	public String getScrolling() {
		return getAttribute("scrolling");
	}

	/** {@inheritDoc} */
	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setFrameBorder(String frameBorder) {
		setAttribute("frameborder", frameBorder);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(String height) {
		setAttribute("height", height);
	}

	/** {@inheritDoc} */
	@Override
	public void setLongDesc(String longDesc) {
		setAttribute("longdesc", longDesc);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarginHeight(String marginHeight) {
		setAttribute("marginHeight", marginHeight);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarginWidth(String marginWidth) {
		setAttribute("marginWidth", marginWidth);
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	/** {@inheritDoc} */
	@Override
	public void setScrolling(String scrolling) {
		setAttribute("scrolling", scrolling);
	}

	/** {@inheritDoc} */
	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param frameControl a {@link org.loboevolution.html.control.FrameControl} object.
	 */
	public void draw(FrameControl frameControl) {
		try {
			HTMLDocumentImpl doc = (HTMLDocumentImpl) getDocumentNode();
			URL baseURL = new URL(doc.getBaseURI());
			URL createURL = Urls.createURL(baseURL, getSrc());
			final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(null, createURL.toString());
			if (Strings.isNotBlank(getWidth()) && Strings.isNotBlank(getHeight())) {
				hpanel.setPreferredSize(new Dimension(Integer.parseInt(getWidth()), Integer.parseInt(getHeight())));
			}
			frameControl.add(hpanel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getAccessKey() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setAutocapitalize(String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setDraggable(boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setTranslate(boolean translate) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getAllow() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setAllow(String allow) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAllowFullscreen() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setAllowFullscreen(boolean allowFullscreen) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAllowPaymentRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setAllowPaymentRequest(boolean allowPaymentRequest) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public WindowProxy getContentWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public DOMTokenList getSandbox() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getSrcdoc() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setSrcdoc(String srcdoc) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public Document getSVGDocument() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLIFrameElement]";
	}
}
