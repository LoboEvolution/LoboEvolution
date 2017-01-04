/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
import org.lobobrowser.w3c.html.HTMLFrameElement;
import org.w3c.dom.Document;

/**
 * The Class HTMLFrameElementImpl.
 */
public class HTMLFrameElementImpl extends HTMLElementImpl implements HTMLFrameElement, FrameNode {

	/** The browser frame. */
	private volatile BrowserFrame browserFrame;

	/**
	 * Instantiates a new HTML frame element impl.
	 *
	 * @param name
	 *            the name
	 * @param noStyleSheet
	 *            the no style sheet
	 */
	public HTMLFrameElementImpl(String name, boolean noStyleSheet) {
		super(name, noStyleSheet);
	}

	/**
	 * Instantiates a new HTML frame element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLFrameElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.dombl.FrameNode#setBrowserFrame(org.lobobrowser.html
	 * .BrowserFrame)
	 */
	@Override
	public void setBrowserFrame(BrowserFrame frame) {
		this.browserFrame = frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.FrameNode#getBrowserFrame()
	 */
	@Override
	public BrowserFrame getBrowserFrame() {
		return this.browserFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getFrameBorder()
	 */
	@Override
	public String getFrameBorder() {
		return this.getAttribute(HtmlAttributeProperties.FRAMEBORDER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#setFrameBorder(java.lang.
	 * String)
	 */
	@Override
	public void setFrameBorder(String frameBorder) {
		this.setAttribute(HtmlAttributeProperties.FRAMEBORDER, frameBorder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getLongDesc()
	 */
	@Override
	public String getLongDesc() {
		return this.getAttribute(HtmlAttributeProperties.LONGDESC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLFrameElement#setLongDesc(java.lang.String)
	 */
	@Override
	public void setLongDesc(String longDesc) {
		this.setAttribute(HtmlAttributeProperties.LONGDESC, longDesc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getMarginHeight()
	 */
	@Override
	public String getMarginHeight() {
		return this.getAttribute(HtmlAttributeProperties.MARGINHEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#setMarginHeight(java.lang.
	 * String)
	 */
	@Override
	public void setMarginHeight(String marginHeight) {
		this.setAttribute(HtmlAttributeProperties.MARGINHEIGHT, marginHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getMarginWidth()
	 */
	@Override
	public String getMarginWidth() {
		return this.getAttribute(HtmlAttributeProperties.MARGINWIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#setMarginWidth(java.lang.
	 * String)
	 */
	@Override
	public void setMarginWidth(String marginWidth) {
		this.setAttribute(HtmlAttributeProperties.MARGINWIDTH, marginWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getName()
	 */
	@Override
	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	/** The no resize. */
	private boolean noResize;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getNoResize()
	 */
	@Override
	public boolean getNoResize() {
		return this.noResize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#setNoResize(boolean)
	 */
	@Override
	public void setNoResize(boolean noResize) {
		this.noResize = noResize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getScrolling()
	 */
	@Override
	public String getScrolling() {
		return this.getAttribute(HtmlAttributeProperties.SCROLLING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLFrameElement#setScrolling(java.lang.String)
	 */
	@Override
	public void setScrolling(String scrolling) {
		this.setAttribute(HtmlAttributeProperties.SCROLLING, scrolling);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getSrc()
	 */
	@Override
	public String getSrc() {
		return this.getAttribute(HtmlAttributeProperties.SRC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#setSrc(java.lang.String)
	 */
	@Override
	public void setSrc(String src) {
		this.setAttribute(HtmlAttributeProperties.SRC, src);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameElement#getContentDocument()
	 */
	@Override
	public Document getContentDocument() {
		BrowserFrame frame = this.browserFrame;
		if (frame == null) {
			// Not loaded yet
			return null;
		}
		return frame.getContentDocument();
	}

	/**
	 * Gets the content window.
	 *
	 * @return the content window
	 */
	@Override
	public Window getContentWindow() {
		BrowserFrame frame = this.browserFrame;
		if (frame == null) {
			// Not loaded yet
			return null;
		}
		return Window.getWindow(frame.getHtmlRendererContext());
	}

}
