package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLHRElement;

public class HTMLHRElementImpl extends HTMLAbstractUIElement implements
		HTMLHRElement {
	public HTMLHRElementImpl(String name) {
		super(name);
	}

	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	public boolean getNoShade() {
		return HtmlAttributeProperties.NOSHADE.equalsIgnoreCase(this.getAttribute(HtmlAttributeProperties.NOSHADE));
	}

	public String getSize() {
		return this.getAttribute(HtmlAttributeProperties.SIZE);
	}

	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	public void setNoShade(boolean noShade) {
		this.setAttribute(HtmlAttributeProperties.NOSHADE, noShade ? HtmlAttributeProperties.NOSHADE : null);
	}

	public void setSize(String size) {
		this.setAttribute(HtmlAttributeProperties.SIZE, size);
	}

	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}

	@Override
	public String getColor() {
		return this.getAttribute(HtmlAttributeProperties.COLOR);
	}

	@Override
	public void setColor(String color) {
		this.setAttribute(HtmlAttributeProperties.COLOR, color);
		
	}
}
