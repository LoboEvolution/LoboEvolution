package org.loboevolution.html.dom.domimpl;

import java.awt.Dimension;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.FrameControl;
import org.loboevolution.html.dom.HTMLIFrameElement;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.renderstate.IFrameRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.w3c.dom.Document;

public class HTMLIFrameElementImpl extends HTMLAbstractUIElement implements HTMLIFrameElement {

	public HTMLIFrameElementImpl(String name) {
		super(name);
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
	public Document getContentDocument() {
		return null;
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

	public void draw(FrameControl frameControl) {
		final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(getSrc());
		if(Strings.isNotBlank(getWidth()) && Strings.isNotBlank(getHeight())) {
			hpanel.setPreferredSize(new Dimension(Integer.parseInt(getWidth()), Integer.parseInt(getHeight())));
		}
		frameControl.add(hpanel);		
	}
}
