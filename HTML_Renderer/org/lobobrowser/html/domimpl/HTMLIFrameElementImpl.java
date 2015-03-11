package org.lobobrowser.html.domimpl;

import java.net.MalformedURLException;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.FrameNode;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.renderstate.IFrameRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.w3c.HTMLIFrameElement;
import org.w3c.dom.Document;


/**
 * The Class HTMLIFrameElementImpl.
 */
public class HTMLIFrameElementImpl extends HTMLAbstractUIElement implements
		HTMLIFrameElement, FrameNode {
	
	/** The browser frame. */
	private volatile BrowserFrame browserFrame;

	/**
	 * Instantiates a new HTMLI frame element impl.
	 *
	 * @param name the name
	 */
	public HTMLIFrameElementImpl(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.FrameNode#setBrowserFrame(org.lobobrowser.html.BrowserFrame)
	 */
	public void setBrowserFrame(BrowserFrame frame) {
		this.browserFrame = frame;
		if (frame != null) {
			String src = this.getAttribute(HtmlAttributeProperties.SRC);
			if (src != null) {
				try {
					frame.loadURL(this.getFullURL(src));
				} catch (MalformedURLException mfu) {
					this.warn("setBrowserFrame(): Unable to navigate to src.",
							mfu);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.FrameNode#getBrowserFrame()
	 */
	public BrowserFrame getBrowserFrame() {
		return this.browserFrame;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getAlign()
	 */
	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getContentDocument()
	 */
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
	public Window getContentWindow() {
		BrowserFrame frame = this.browserFrame;
		if (frame == null) {
			// Not loaded yet
			return null;
		}
		return Window.getWindow(frame.getHtmlRendererContext());
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getFrameBorder()
	 */
	public String getFrameBorder() {
		return this.getAttribute(HtmlAttributeProperties.FRAMEBORDER);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getHeight()
	 */
	public String getHeight() {
		return this.getAttribute(HtmlAttributeProperties.HEIGHT);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getLongDesc()
	 */
	public String getLongDesc() {
		return this.getAttribute(HtmlAttributeProperties.LONGDESC);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getMarginHeight()
	 */
	public String getMarginHeight() {
		return this.getAttribute(HtmlAttributeProperties.FRAMEBORDER);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getMarginWidth()
	 */
	public String getMarginWidth() {
		return this.getAttribute(HtmlAttributeProperties.MARGINWIDTH);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getName()
	 */
	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getScrolling()
	 */
	public String getScrolling() {
		return this.getAttribute(HtmlAttributeProperties.SCROLLING);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getSrc()
	 */
	public String getSrc() {
		return this.getAttribute(HtmlAttributeProperties.SRC);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#getWidth()
	 */
	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setAlign(java.lang.String)
	 */
	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setFrameBorder(java.lang.String)
	 */
	public void setFrameBorder(String frameBorder) {
		this.setAttribute(HtmlAttributeProperties.FRAMEBORDER, frameBorder);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setHeight(java.lang.String)
	 */
	public void setHeight(String height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, height);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setLongDesc(java.lang.String)
	 */
	public void setLongDesc(String longDesc) {
		this.setAttribute(HtmlAttributeProperties.LONGDESC, longDesc);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setMarginHeight(java.lang.String)
	 */
	public void setMarginHeight(String marginHeight) {
		this.setAttribute(HtmlAttributeProperties.FRAMEBORDER, marginHeight);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setMarginWidth(java.lang.String)
	 */
	public void setMarginWidth(String marginWidth) {
		this.setAttribute(HtmlAttributeProperties.MARGINWIDTH, marginWidth);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setScrolling(java.lang.String)
	 */
	public void setScrolling(String scrolling) {
		this.setAttribute(HtmlAttributeProperties.SCROLLING, scrolling);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setSrc(java.lang.String)
	 */
	public void setSrc(String src) {
		this.setAttribute(HtmlAttributeProperties.SRC, src);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLIFrameElement#setWidth(java.lang.String)
	 */
	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLAbstractUIElement#assignAttributeField(java.lang.String, java.lang.String)
	 */
	protected void assignAttributeField(String normalName, String value) {
		if (HtmlAttributeProperties.SRC.equals(normalName)) {
			BrowserFrame frame = this.browserFrame;
			if (frame != null) {
				try {
					frame.loadURL(this.getFullURL(value));
				} catch (MalformedURLException mfu) {
					this.warn(
							"assignAttributeField(): Unable to navigate to src.",
							mfu);
				}
			}
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser.html.renderstate.RenderState)
	 */
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new IFrameRenderState(prevRenderState, this);
	}
}
