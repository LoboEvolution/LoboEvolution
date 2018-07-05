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
 * Created on Jan 28, 2006
 */
package org.loboevolution.html.domimpl;

import org.loboevolution.html.BrowserFrame;
import org.loboevolution.html.dombl.FrameNode;
import org.loboevolution.html.js.object.Window;
import org.loboevolution.w3c.html.HTMLFrameElement;
import org.w3c.dom.Document;

/**
 * The Class HTMLFrameElementImpl.
 */
public class HTMLFrameElementImpl extends HTMLElementImpl implements HTMLFrameElement, FrameNode {

	/** The browser frame. */
	private volatile BrowserFrame browserFrame;
	
	/** The no resize. */
	private boolean noResize;

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
	 * org.loboevolution.html.dombl.FrameNode#setBrowserFrame(org.loboevolution.html
	 * .BrowserFrame)
	 */
	@Override
	public void setBrowserFrame(BrowserFrame frame) {
		this.browserFrame = frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.FrameNode#getBrowserFrame()
	 */
	@Override
	public BrowserFrame getBrowserFrame() {
		return this.browserFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getFrameBorder()
	 */
	@Override
	public String getFrameBorder() {
		return this.getAttribute(FRAMEBORDER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#setFrameBorder(java.lang.
	 * String)
	 */
	@Override
	public void setFrameBorder(String frameBorder) {
		this.setAttribute(FRAMEBORDER, frameBorder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getLongDesc()
	 */
	@Override
	public String getLongDesc() {
		return this.getAttribute(LONGDESC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLFrameElement#setLongDesc(java.lang.String)
	 */
	@Override
	public void setLongDesc(String longDesc) {
		this.setAttribute(LONGDESC, longDesc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getMarginHeight()
	 */
	@Override
	public String getMarginHeight() {
		return this.getAttribute(MARGINHEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#setMarginHeight(java.lang.
	 * String)
	 */
	@Override
	public void setMarginHeight(String marginHeight) {
		this.setAttribute(MARGINHEIGHT, marginHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getMarginWidth()
	 */
	@Override
	public String getMarginWidth() {
		return this.getAttribute(MARGINWIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#setMarginWidth(java.lang.
	 * String)
	 */
	@Override
	public void setMarginWidth(String marginWidth) {
		this.setAttribute(MARGINWIDTH, marginWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getName()
	 */
	@Override
	public String getName() {
		return this.getAttribute(NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.setAttribute(NAME, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getNoResize()
	 */
	@Override
	public boolean getNoResize() {
		return this.noResize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#setNoResize(boolean)
	 */
	@Override
	public void setNoResize(boolean noResize) {
		this.noResize = noResize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getScrolling()
	 */
	@Override
	public String getScrolling() {
		return this.getAttribute(SCROLLING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLFrameElement#setScrolling(java.lang.String)
	 */
	@Override
	public void setScrolling(String scrolling) {
		this.setAttribute(SCROLLING, scrolling);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getSrc()
	 */
	@Override
	public String getSrc() {
		return this.getAttribute(SRC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#setSrc(java.lang.String)
	 */
	@Override
	public void setSrc(String src) {
		this.setAttribute(SRC, src);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameElement#getContentDocument()
	 */
	@Override
	public Document getContentDocument() {
		BrowserFrame frame = this.browserFrame;
		if (frame == null) {
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
