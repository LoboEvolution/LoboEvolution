package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.FrameNode;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.style.IFrameRenderState;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.w3c.HTMLIFrameElement;
import org.w3c.dom.Document;

public class HTMLIFrameElementImpl extends HTMLAbstractUIElement implements
		HTMLIFrameElement, FrameNode {
	private volatile BrowserFrame browserFrame;

	public HTMLIFrameElementImpl(String name) {
		super(name);
	}

	public void setBrowserFrame(BrowserFrame frame) {
		this.browserFrame = frame;
		if (frame != null) {
			String src = this.getAttribute(HtmlAttributeProperties.SRC);
			if (src != null) {
				try {
					frame.loadURL(this.getFullURL(src));
				} catch (java.net.MalformedURLException mfu) {
					this.warn("setBrowserFrame(): Unable to navigate to src.",
							mfu);
				}
			}
		}
	}

	public BrowserFrame getBrowserFrame() {
		return this.browserFrame;
	}

	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	public Document getContentDocument() {
		// TODO: Domain-based security
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

	public String getFrameBorder() {
		return this.getAttribute(HtmlAttributeProperties.FRAMEBORDER);
	}

	public String getHeight() {
		return this.getAttribute(HtmlAttributeProperties.HEIGHT);
	}

	public String getLongDesc() {
		return this.getAttribute(HtmlAttributeProperties.LONGDESC);
	}

	public String getMarginHeight() {
		return this.getAttribute(HtmlAttributeProperties.FRAMEBORDER);
	}

	public String getMarginWidth() {
		return this.getAttribute(HtmlAttributeProperties.MARGINWIDTH);
	}

	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	public String getScrolling() {
		return this.getAttribute(HtmlAttributeProperties.SCROLLING);
	}

	public String getSrc() {
		return this.getAttribute(HtmlAttributeProperties.SRC);
	}

	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	public void setFrameBorder(String frameBorder) {
		this.setAttribute(HtmlAttributeProperties.FRAMEBORDER, frameBorder);
	}

	public void setHeight(String height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, height);
	}

	public void setLongDesc(String longDesc) {
		this.setAttribute(HtmlAttributeProperties.LONGDESC, longDesc);
	}

	public void setMarginHeight(String marginHeight) {
		this.setAttribute(HtmlAttributeProperties.FRAMEBORDER, marginHeight);
	}

	public void setMarginWidth(String marginWidth) {
		this.setAttribute(HtmlAttributeProperties.MARGINWIDTH, marginWidth);
	}

	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	public void setScrolling(String scrolling) {
		this.setAttribute(HtmlAttributeProperties.SCROLLING, scrolling);
	}

	public void setSrc(String src) {
		this.setAttribute(HtmlAttributeProperties.SRC, src);
	}

	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}

	protected void assignAttributeField(String normalName, String value) {
		if (HtmlAttributeProperties.SRC.equals(normalName)) {
			BrowserFrame frame = this.browserFrame;
			if (frame != null) {
				try {
					frame.loadURL(this.getFullURL(value));
				} catch (java.net.MalformedURLException mfu) {
					this.warn(
							"assignAttributeField(): Unable to navigate to src.",
							mfu);
				}
			}
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new IFrameRenderState(prevRenderState, this);
	}
}
