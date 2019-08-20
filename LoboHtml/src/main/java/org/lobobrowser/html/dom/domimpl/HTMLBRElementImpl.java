package org.lobobrowser.html.dom.domimpl;

import org.lobobrowser.html.dom.HTMLBRElement;

public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
	public HTMLBRElementImpl(String name) {
		super(name);
	}

	@Override
	protected void appendInnerTextImpl(StringBuffer buffer) {
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
