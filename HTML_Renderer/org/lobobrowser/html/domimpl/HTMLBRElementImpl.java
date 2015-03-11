package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLBRElement;


/**
 * The Class HTMLBRElementImpl.
 */
public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
	
	/**
	 * Instantiates a new HTMLBR element impl.
	 *
	 * @param name the name
	 */
	public HTMLBRElementImpl(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLBRElement#getClear()
	 */
	public String getClear() {
		return this.getAttribute(HtmlAttributeProperties.CLEAR);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLBRElement#setClear(java.lang.String)
	 */
	public void setClear(String clear) {
		this.setAttribute(HtmlAttributeProperties.CLEAR, clear);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#appendInnerTextImpl(java.lang.StringBuffer)
	 */
	protected void appendInnerTextImpl(StringBuffer buffer) {
		buffer.append("\r\n");
		super.appendInnerTextImpl(buffer);
	}
}
