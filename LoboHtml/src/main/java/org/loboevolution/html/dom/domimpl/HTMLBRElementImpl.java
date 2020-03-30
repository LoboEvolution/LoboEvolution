package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLBRElement;

public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
	public HTMLBRElementImpl(String name) {
		super(name);
	}

	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		buffer.append("\r\n");
		super.appendInnerTextImpl(buffer);
	}

	@Override
	public String getClear() {
		return getAttribute("clear");
	}

	@Override
	public void setClear(String clear) {
		setAttribute("clear", clear);
	}
}
