package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLHRElement;


/**
 * The Class HTMLHRElementImpl.
 */
public class HTMLHRElementImpl extends HTMLAbstractUIElement implements
		HTMLHRElement {
	
	/**
	 * Instantiates a new HTMLHR element impl.
	 *
	 * @param name the name
	 */
	public HTMLHRElementImpl(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#getAlign()
	 */
	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#getNoShade()
	 */
	public boolean getNoShade() {
		return HtmlAttributeProperties.NOSHADE.equalsIgnoreCase(this.getAttribute(HtmlAttributeProperties.NOSHADE));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#getSize()
	 */
	public String getSize() {
		return this.getAttribute(HtmlAttributeProperties.SIZE);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#getWidth()
	 */
	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#setAlign(java.lang.String)
	 */
	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#setNoShade(boolean)
	 */
	public void setNoShade(boolean noShade) {
		this.setAttribute(HtmlAttributeProperties.NOSHADE, noShade ? HtmlAttributeProperties.NOSHADE : null);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#setSize(java.lang.String)
	 */
	public void setSize(String size) {
		this.setAttribute(HtmlAttributeProperties.SIZE, size);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#setWidth(java.lang.String)
	 */
	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#getColor()
	 */
	@Override
	public String getColor() {
		return this.getAttribute(HtmlAttributeProperties.COLOR);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLHRElement#setColor(java.lang.String)
	 */
	@Override
	public void setColor(String color) {
		this.setAttribute(HtmlAttributeProperties.COLOR, color);
		
	}
}
