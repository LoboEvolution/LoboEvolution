package org.lobobrowser.html.dom.domimpl;

import org.lobobrowser.html.dom.HTMLHRElement;

public class HTMLHRElementImpl extends HTMLAbstractUIElement implements HTMLHRElement {
	public HTMLHRElementImpl(String name) {
		super(name);
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public boolean getNoShade() {
		return "noshade".equalsIgnoreCase(getAttribute("noshade"));
	}

	@Override
	public String getSize() {
		return getAttribute("size");
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
	public void setNoShade(boolean noShade) {
		setAttribute("noshade", noShade ? "noshade" : null);
	}

	@Override
	public void setSize(String size) {
		setAttribute("size", size);
	}

	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
