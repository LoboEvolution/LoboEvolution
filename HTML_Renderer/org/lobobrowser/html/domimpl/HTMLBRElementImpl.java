package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLBRElement;

public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
	public HTMLBRElementImpl(String name) {
		super(name);
	}

	public String getClear() {
		return this.getAttribute(HtmlAttributeProperties.CLEAR);
	}

	public void setClear(String clear) {
		this.setAttribute(HtmlAttributeProperties.CLEAR, clear);
	}

	protected void appendInnerTextImpl(StringBuffer buffer) {
		buffer.append("\r\n");
		super.appendInnerTextImpl(buffer);
	}
}
