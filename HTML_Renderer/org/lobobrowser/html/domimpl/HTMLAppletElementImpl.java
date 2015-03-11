package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.w3c.dom.html.HTMLAppletElement;


/**
 * The Class HTMLAppletElementImpl.
 */
public class HTMLAppletElementImpl extends HTMLAbstractUIElement implements
		HTMLAppletElement {
	
	/**
	 * Instantiates a new HTML applet element impl.
	 *
	 * @param name the name
	 */
	public HTMLAppletElementImpl(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getAlign()
	 */
	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getAlt()
	 */
	public String getAlt() {
		return this.getAttribute(HtmlAttributeProperties.ALT);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getArchive()
	 */
	public String getArchive() {
		return this.getAttribute(HtmlAttributeProperties.ARCHIVE);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getCode()
	 */
	public String getCode() {
		return this.getAttribute(HtmlAttributeProperties.CODE);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getCodeBase()
	 */
	public String getCodeBase() {
		return this.getAttribute(HtmlAttributeProperties.CODEBASE);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getHeight()
	 */
	public String getHeight() {
		return this.getAttribute(HtmlAttributeProperties.HEIGHT);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getHspace()
	 */
	public String getHspace() {
		return this.getAttribute(HtmlAttributeProperties.HSPACE);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getName()
	 */
	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getObject()
	 */
	public String getObject() {
		return this.getAttribute(HtmlAttributeProperties.OBJECT);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getVspace()
	 */
	public String getVspace() {
		return this.getAttribute(HtmlAttributeProperties.VSPACE);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#getWidth()
	 */
	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setAlign(java.lang.String)
	 */
	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setAlt(java.lang.String)
	 */
	public void setAlt(String alt) {
		this.setAttribute(HtmlAttributeProperties.ALT, alt);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setArchive(java.lang.String)
	 */
	public void setArchive(String archive) {
		this.setAttribute(HtmlAttributeProperties.ARCHIVE, archive);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setCode(java.lang.String)
	 */
	public void setCode(String code) {
		this.setAttribute(HtmlAttributeProperties.CODE, code);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setCodeBase(java.lang.String)
	 */
	public void setCodeBase(String codeBase) {
		this.setAttribute(HtmlAttributeProperties.CODEBASE, codeBase);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setHeight(java.lang.String)
	 */
	public void setHeight(String height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, height);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setHspace(java.lang.String)
	 */
	public void setHspace(String hspace) {
		this.setAttribute(HtmlAttributeProperties.HSPACE, hspace);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setObject(java.lang.String)
	 */
	public void setObject(String object) {
		this.setAttribute(HtmlAttributeProperties.OBJECT, object);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setVspace(java.lang.String)
	 */
	public void setVspace(String vspace) {
		this.setAttribute(HtmlAttributeProperties.VSPACE, vspace);
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.html.HTMLAppletElement#setWidth(java.lang.String)
	 */
	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}
}
