package org.lobobrowser.html.domimpl;

import org.lobobrowser.w3c.dom.html.HTMLBRElement;

public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
	public HTMLBRElementImpl(String name) {
		super(name);
	}

	public String getClear() {
		return this.getAttribute("clear");
	}

	public void setClear(String clear) {
		this.setAttribute("clear", clear);
	}

	protected void appendInnerTextImpl(StringBuffer buffer) {
		buffer.append("\r\n");
		super.appendInnerTextImpl(buffer);
	}
}
