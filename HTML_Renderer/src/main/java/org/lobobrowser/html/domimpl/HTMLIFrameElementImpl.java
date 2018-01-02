/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.html.domimpl;

import java.net.MalformedURLException;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.dombl.FrameNode;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.renderstate.IFrameRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.w3c.html.DOMSettableTokenList;
import org.lobobrowser.w3c.html.HTMLIFrameElement;
import org.w3c.dom.Document;

/**
 * The Class HTMLIFrameElementImpl.
 */
public class HTMLIFrameElementImpl extends HTMLAbstractUIElement implements HTMLIFrameElement, FrameNode {

	/** The browser frame. */
	private volatile BrowserFrame browserFrame;

	/**
	 * Instantiates a new HTMLI frame element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLIFrameElementImpl(String name) {
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
		if (frame != null) {
			String src = this.getAttribute(SRC);
			if (src != null) {
				try {
					frame.loadURL(this.getFullURL(src));
				} catch (MalformedURLException mfu) {
					logger.error("setBrowserFrame(): Unable to navigate to src.", mfu);
				}
			}
		}
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
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getContentDocument()
	 */
	@Override
	public Document getContentDocument() {
		// TODO: Domain-based security
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getFrameBorder()
	 */
	@Override
	public String getFrameBorder() {
		return this.getAttribute(FRAMEBORDER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getHeight()
	 */
	@Override
	public String getHeight() {
		return this.getAttribute(HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getLongDesc()
	 */
	@Override
	public String getLongDesc() {
		return this.getAttribute(LONGDESC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getMarginHeight()
	 */
	@Override
	public String getMarginHeight() {
		return this.getAttribute(FRAMEBORDER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getMarginWidth()
	 */
	@Override
	public String getMarginWidth() {
		return this.getAttribute(MARGINWIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getName()
	 */
	@Override
	public String getName() {
		return this.getAttribute(NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getScrolling()
	 */
	@Override
	public String getScrolling() {
		return this.getAttribute(SCROLLING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getSrc()
	 */
	@Override
	public String getSrc() {
		return this.getAttribute(SRC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.getAttribute(WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLIFrameElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#setFrameBorder(java.lang.
	 * String)
	 */
	@Override
	public void setFrameBorder(String frameBorder) {
		this.setAttribute(FRAMEBORDER, frameBorder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLIFrameElement#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		this.setAttribute(HEIGHT, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLIFrameElement#setLongDesc(java.lang.String)
	 */
	@Override
	public void setLongDesc(String longDesc) {
		this.setAttribute(LONGDESC, longDesc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLIFrameElement#setMarginHeight(java.lang.
	 * String)
	 */
	@Override
	public void setMarginHeight(String marginHeight) {
		this.setAttribute(FRAMEBORDER, marginHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#setMarginWidth(java.lang.
	 * String)
	 */
	@Override
	public void setMarginWidth(String marginWidth) {
		this.setAttribute(MARGINWIDTH, marginWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.setAttribute(NAME, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLIFrameElement#setScrolling(java.lang.String)
	 */
	@Override
	public void setScrolling(String scrolling) {
		this.setAttribute(SCROLLING, scrolling);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLIFrameElement#setSrc(java.lang.String)
	 */
	@Override
	public void setSrc(String src) {
		this.setAttribute(SRC, src);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLIFrameElement#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		this.setAttribute(WIDTH, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.HTMLAbstractUIElement#assignAttributeField(
	 * java .lang.String, java.lang.String)
	 */
	@Override
	protected void assignAttributeField(String normalName, String value) {
		if (SRC.equals(normalName)) {
			BrowserFrame frame = this.browserFrame;
			if (frame != null) {
				try {
					frame.loadURL(this.getFullURL(value));
				} catch (MalformedURLException mfu) {
					logger.error("assignAttributeField(): Unable to navigate to src.", mfu);
				}
			}
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new IFrameRenderState(prevRenderState, this);
	}

	@Override
	public String getSrcdoc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSrcdoc(String srcdoc) {
		// TODO Auto-generated method stub

	}

	@Override
	public DOMSettableTokenList getSandbox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSandbox(String sandbox) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getSeamless() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSeamless(boolean seamless) {
		// TODO Auto-generated method stub

	}
}
