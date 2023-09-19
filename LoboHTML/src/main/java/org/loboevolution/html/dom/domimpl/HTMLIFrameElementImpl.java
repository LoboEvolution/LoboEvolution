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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.control.FrameControl;
import org.loboevolution.html.dom.HTMLIFrameElement;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.html.node.DOMTokenList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.js.WindowProxy;
import org.loboevolution.html.renderstate.IFrameRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.net.UserAgent;

import java.awt.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

/**
 * <p>HTMLIFrameElementImpl class.</p>
 */
public class HTMLIFrameElementImpl extends HTMLElementImpl implements HTMLIFrameElement {

	/**
	 * <p>Constructor for HTMLIFrameElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLIFrameElementImpl(final String name) {
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

	@Override
	public WindowProxy getContentWindow() {
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
		String heightText = this.getAttribute("height");
		if (Strings.isBlank(heightText)) {
			return String.valueOf(getClientHeight());
		}
		return heightText;
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
		final String name = getAttribute("name");
		return name == null ? "" : name;
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
		String widthText = this.getAttribute("width");
		if (Strings.isBlank(widthText)) {
			return String.valueOf(getClientWidth());
		}

		return widthText;
	}

	@Override
	public int getClientHeight() {
		int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 150 : clientHeight;
	}

	@Override
	public double getOffsetHeight() {
		return getClientHeight();
	}

	@Override
	public Integer getClientWidth() {
		int clientWidth = super.getClientWidth();
		return clientWidth == 0 ? 300 : clientWidth;
	}

	@Override
	public Integer getOffsetWidth() {
		return getClientWidth();
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public String getAllow() {
		return null;
	}

	@Override
	public void setAllow(String allow) {

	}

	@Override
	public boolean isAllowFullscreen() {
		return false;
	}

	@Override
	public void setAllowFullscreen(boolean allowFullscreen) {

	}

	@Override
	public boolean isAllowPaymentRequest() {
		return false;
	}

	@Override
	public void setAllowPaymentRequest(boolean allowPaymentRequest) {

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

	@Override
	public DOMTokenList getSandbox() {
		return null;
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

	@Override
	public String getSrcdoc() {
		return null;
	}

	@Override
	public void setSrcdoc(String srcdoc) {

	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}

	@Override
	public Document getSVGDocument() {
		return null;
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param frameControl a {@link org.loboevolution.html.control.FrameControl} object.
	 */
	public void draw(FrameControl frameControl) {
		try {
			if (Strings.isBlank(getSrc())) {
				frameControl.add(new HtmlPanel());
			} else {
				HTMLDocumentImpl doc = (HTMLDocumentImpl) getDocumentNode();
				URL baseURL = new URL(doc.getBaseURI());
				URL createURL = Urls.createURL(baseURL, getSrc());
				URLConnection connection = createURL.openConnection();
				connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
				connection.getHeaderField("Set-Cookie");
				connection.connect();
				HtmlPanel panel = new HtmlPanel();
				panel.setBrowserPanel(null);
				HtmlPanel newpanel = HtmlPanel.createlocalPanel(connection, panel, doc.getHtmlRendererContext(), doc.getHtmlRendererConfig(), createURL.toString());
				String width = getWidth();
				String height = getHeight();
				if (Strings.isNotBlank(width) && Strings.isNotBlank(height)) {
					int w = HtmlValues.getPixelSize(width, doc.getRenderState(), doc.getDefaultView(), -1);
					int h = HtmlValues.getPixelSize(height, doc.getRenderState(), doc.getDefaultView(), -1);
					newpanel.setPreferredSize(new Dimension(w, h));
				}
				frameControl.add(newpanel);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLIFrameElement]";
	}
}
