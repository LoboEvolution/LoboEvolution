package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLFormElement;
import org.lobobrowser.html.w3c.HTMLObjectElement;
import org.lobobrowser.html.w3c.ValidityState;
import org.w3c.dom.Document;

public class HTMLObjectElementImpl extends HTMLAbstractUIElement implements
		HTMLObjectElement {
	public HTMLObjectElementImpl(String name) {
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

	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	public String getObject() {
		return this.getAttribute(HtmlAttributeProperties.OBJECT);
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

	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	public void setObject(String object) {
		this.setAttribute(HtmlAttributeProperties.OBJECT, object);
	}

	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}

	public String getBorder() {
		return this.getAttribute(HtmlAttributeProperties.BORDER);
	}

	public String getCodeType() {
		return this.getAttribute(HtmlAttributeProperties.CODETYPE);
	}

	public Document getContentDocument() {
		return this.getOwnerDocument();
	}

	public String getData() {
		return this.getAttribute(HtmlAttributeProperties.DATA);
	}

	public boolean getDeclare() {
		return "declare".equalsIgnoreCase(this.getAttribute(HtmlAttributeProperties.DECLARE));
	}

	public HTMLFormElement getForm() {
		return (HTMLFormElement) this
				.getAncestorForJavaClass(HTMLFormElement.class);
	}

	public int getHspace() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.HSPACE));
		} catch (Exception err) {
			return 0;
		}
	}

	public String getStandby() {
		return this.getAttribute(HtmlAttributeProperties.STANDBY);
	}

	public int getTabIndex() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.TABINDEX));
		} catch (Exception err) {
			return 0;
		}
	}

	public String getType() {
		return this.getAttribute(HtmlAttributeProperties.TYPE);
	}

	public String getUseMap() {
		return this.getAttribute(HtmlAttributeProperties.USEMAP);
	}

	public int getVspace() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.VSPACE));
		} catch (Exception err) {
			return 0;
		}
	}

	public void setBorder(String border) {
		this.setAttribute(HtmlAttributeProperties.BORDER, border);
	}

	public void setCodeType(String codeType) {
		this.setAttribute(HtmlAttributeProperties.CODETYPE, codeType);
	}

	public void setData(String data) {
		this.setAttribute(HtmlAttributeProperties.DATA, data);
	}

	public void setDeclare(boolean declare) {
		this.setAttribute(HtmlAttributeProperties.DECLARE, declare ? "declare" : null);
	}

	public void setHspace(int hspace) {
		this.setAttribute(HtmlAttributeProperties.HSPACE, String.valueOf(hspace));
	}

	public void setStandby(String standby) {
		this.setAttribute(HtmlAttributeProperties.STANDBY, standby);
	}

	public void setTabIndex(int tabIndex) {
		this.setAttribute(HtmlAttributeProperties.TABINDEX, String.valueOf(tabIndex));
	}

	public void setType(String type) {
		this.setAttribute(HtmlAttributeProperties.TYPE, type);
	}

	public void setUseMap(String useMap) {
		this.setAttribute(HtmlAttributeProperties.USEMAP, useMap);
	}

	public void setVspace(int vspace) {
		this.setAttribute(HtmlAttributeProperties.VSPACE, String.valueOf(vspace));
	}

	@Override
	public boolean getWillValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub
		
	}
}
