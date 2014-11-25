package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.w3c.dom.html.HTMLAppletElement;

public class HTMLAppletElementImpl extends HTMLAbstractUIElement implements
		HTMLAppletElement {
	public HTMLAppletElementImpl(String name) {
		super(name);
	}

	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	public String getAlt() {
		return this.getAttribute(HtmlAttributeProperties.ALT);
	}

	public String getArchive() {
		return this.getAttribute(HtmlAttributeProperties.ARCHIVE);
	}

	public String getCode() {
		return this.getAttribute(HtmlAttributeProperties.CODE);
	}

	public String getCodeBase() {
		return this.getAttribute(HtmlAttributeProperties.CODEBASE);
	}

	public String getHeight() {
		return this.getAttribute(HtmlAttributeProperties.HEIGHT);
	}

	public String getHspace() {
		return this.getAttribute(HtmlAttributeProperties.HSPACE);
	}

	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	public String getObject() {
		return this.getAttribute(HtmlAttributeProperties.OBJECT);
	}

	public String getVspace() {
		return this.getAttribute(HtmlAttributeProperties.VSPACE);
	}

	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	public void setAlt(String alt) {
		this.setAttribute(HtmlAttributeProperties.ALT, alt);
	}

	public void setArchive(String archive) {
		this.setAttribute(HtmlAttributeProperties.ARCHIVE, archive);
	}

	public void setCode(String code) {
		this.setAttribute(HtmlAttributeProperties.CODE, code);
	}

	public void setCodeBase(String codeBase) {
		this.setAttribute(HtmlAttributeProperties.CODEBASE, codeBase);
	}

	public void setHeight(String height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, height);
	}

	public void setHspace(String hspace) {
		this.setAttribute(HtmlAttributeProperties.HSPACE, hspace);
	}

	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	public void setObject(String object) {
		this.setAttribute(HtmlAttributeProperties.OBJECT, object);
	}

	public void setVspace(String vspace) {
		this.setAttribute(HtmlAttributeProperties.VSPACE, vspace);
	}

	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}
}
