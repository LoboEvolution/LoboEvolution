package org.lobobrowser.html.dom.domimpl;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.dom.HTMLIFrameElement;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.style.IFrameRenderState;
import org.lobobrowser.html.style.RenderState;
import org.w3c.dom.Document;

public class HTMLIFrameElementImpl extends HTMLAbstractUIElement implements HTMLIFrameElement, FrameNode {
	private volatile BrowserFrame browserFrame;

	public HTMLIFrameElementImpl(String name) {
		super(name);
	}

	@Override
	protected void assignAttributeField(String normalName, String value) {
		if ("src".equals(normalName)) {
			final BrowserFrame frame = this.browserFrame;
			if (frame != null) {
				try {
					frame.loadURL(getFullURL(value));
				} catch (final java.net.MalformedURLException mfu) {
					this.warn("assignAttributeField(): Unable to navigate to src.", mfu);
				}
			}
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new IFrameRenderState(prevRenderState, this);
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public BrowserFrame getBrowserFrame() {
		return this.browserFrame;
	}

	@Override
	public Document getContentDocument() {
		// TODO: Domain-based security
		final BrowserFrame frame = this.browserFrame;
		if (frame == null) {
			// Not loaded yet
			return null;
		}
		return frame.getContentDocument();
	}

	public Window getContentWindow() {
		final BrowserFrame frame = this.browserFrame;
		if (frame == null) {
			// Not loaded yet
			return null;
		}
		return Window.getWindow(frame.getHtmlRendererContext());
	}

	@Override
	public String getFrameBorder() {
		return getAttribute("frameborder");
	}

	@Override
	public String getHeight() {
		return getAttribute("height");
	}

	@Override
	public String getLongDesc() {
		return getAttribute("longdesc");
	}

	@Override
	public String getMarginHeight() {
		return getAttribute("marginheight");
	}

	@Override
	public String getMarginWidth() {
		return getAttribute("marginwidth");
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}

	@Override
	public String getScrolling() {
		return getAttribute("scrolling");
	}

	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public void setBrowserFrame(BrowserFrame frame) {
		this.browserFrame = frame;
		if (frame != null) {
			final String src = getAttribute("src");
			if (src != null) {
				try {
					frame.loadURL(getFullURL(src));
				} catch (final java.net.MalformedURLException mfu) {
					this.warn("setBrowserFrame(): Unable to navigate to src.", mfu);
				}
			}
		}
	}

	@Override
	public void setFrameBorder(String frameBorder) {
		setAttribute("frameborder", frameBorder);
	}

	@Override
	public void setHeight(String height) {
		setAttribute("height", height);
	}

	@Override
	public void setLongDesc(String longDesc) {
		setAttribute("longdesc", longDesc);
	}

	@Override
	public void setMarginHeight(String marginHeight) {
		setAttribute("marginHeight", marginHeight);
	}

	@Override
	public void setMarginWidth(String marginWidth) {
		setAttribute("marginWidth", marginWidth);
	}

	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	@Override
	public void setScrolling(String scrolling) {
		setAttribute("scrolling", scrolling);
	}

	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}

	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
