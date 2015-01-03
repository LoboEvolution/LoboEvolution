/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
 * Created on Jan 28, 2006
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.FrameNode;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.w3c.HTMLFrameElement;
import org.w3c.dom.Document;

public class HTMLFrameElementImpl extends HTMLElementImpl implements
		HTMLFrameElement, FrameNode {
	private volatile BrowserFrame browserFrame;

	public HTMLFrameElementImpl(String name, boolean noStyleSheet) {
		super(name, noStyleSheet);
	}

	public HTMLFrameElementImpl(String name) {
		super(name);
	}

	public void setBrowserFrame(BrowserFrame frame) {
		this.browserFrame = frame;
	}

	public BrowserFrame getBrowserFrame() {
		return this.browserFrame;
	}

	public String getFrameBorder() {
		return this.getAttribute(HtmlAttributeProperties.FRAMEBORDER);
	}

	public void setFrameBorder(String frameBorder) {
		this.setAttribute(HtmlAttributeProperties.FRAMEBORDER, frameBorder);
	}

	public String getLongDesc() {
		return this.getAttribute(HtmlAttributeProperties.LONGDESC);
	}

	public void setLongDesc(String longDesc) {
		this.setAttribute(HtmlAttributeProperties.LONGDESC, longDesc);
	}

	public String getMarginHeight() {
		return this.getAttribute(HtmlAttributeProperties.MARGINHEIGHT);
	}

	public void setMarginHeight(String marginHeight) {
		this.setAttribute(HtmlAttributeProperties.MARGINHEIGHT, marginHeight);
	}

	public String getMarginWidth() {
		return this.getAttribute(HtmlAttributeProperties.MARGINWIDTH);
	}

	public void setMarginWidth(String marginWidth) {
		this.setAttribute(HtmlAttributeProperties.MARGINWIDTH, marginWidth);
	}

	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	private boolean noResize;

	public boolean getNoResize() {
		return this.noResize;
	}

	public void setNoResize(boolean noResize) {
		this.noResize = noResize;
	}

	public String getScrolling() {
		return this.getAttribute(HtmlAttributeProperties.SCROLLING);
	}

	public void setScrolling(String scrolling) {
		this.setAttribute(HtmlAttributeProperties.SCROLLING, scrolling);
	}

	public String getSrc() {
		return this.getAttribute(HtmlAttributeProperties.SRC);
	}

	public void setSrc(String src) {
		this.setAttribute(HtmlAttributeProperties.SRC, src);
	}

	public Document getContentDocument() {
		BrowserFrame frame = this.browserFrame;
		if (frame == null) {
			// Not loaded yet
			return null;
		}
		return frame.getContentDocument();
	}

	public Window getContentWindow() {
		BrowserFrame frame = this.browserFrame;
		if (frame == null) {
			// Not loaded yet
			return null;
		}
		return Window.getWindow(frame.getHtmlRendererContext());
	}

}
