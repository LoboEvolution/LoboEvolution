package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLObjectElement;
import org.loboevolution.html.style.HtmlValues;
import org.w3c.dom.Document;

public class HTMLObjectElementImpl extends HTMLAbstractUIElement implements HTMLObjectElement {
	public HTMLObjectElementImpl(String name) {
		super(name);
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	public String getAlt() {
		return getAttribute("alt");
	}

	@Override
	public String getArchive() {
		return getAttribute("archive");
	}

	@Override
	public String getBorder() {
		return getAttribute("border");
	}

	@Override
	public String getCode() {
		return getAttribute("code");
	}

	@Override
	public String getCodeBase() {
		return getAttribute("codebase");
	}

	@Override
	public String getCodeType() {
		return getAttribute("codetype");
	}

	@Override
	public Document getContentDocument() {
		return getOwnerDocument();
	}

	@Override
	public String getData() {
		return getAttribute("data");
	}

	@Override
	public boolean getDeclare() {
		return "declare".equalsIgnoreCase(getAttribute("declare"));
	}

	@Override
	public HTMLFormElement getForm() {
		return (HTMLFormElement) getAncestorForJavaClass(HTMLFormElement.class);
	}

	@Override
	public String getHeight() {
		return getAttribute("height");
	}

	@Override
	public int getHspace() {
		return HtmlValues.getPixelSize(getAttribute("hspace"), null, 0);
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}

	public String getObject() {
		return getAttribute("object");
	}

	@Override
	public String getStandby() {
		return getAttribute("standby");
	}

	@Override
	public int getTabIndex() {
		return HtmlValues.getPixelSize(getAttribute("tabindex"), null, 0);
	}

	@Override
	public String getType() {
		return getAttribute("type");
	}

	@Override
	public String getUseMap() {
		return getAttribute("usemap");
	}

	@Override
	public int getVspace() {
		return HtmlValues.getPixelSize(getAttribute("vspace"), null, 0);
	}

	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	@Override
	public void setArchive(String archive) {
		setAttribute("archive", archive);
	}

	@Override
	public void setBorder(String border) {
		setAttribute("border", border);
	}

	@Override
	public void setCode(String code) {
		setAttribute("code", code);
	}

	@Override
	public void setCodeBase(String codeBase) {
		setAttribute("codebase", codeBase);
	}

	@Override
	public void setCodeType(String codeType) {
		setAttribute("codetype", codeType);
	}

	@Override
	public void setData(String data) {
		setAttribute("data", data);
	}

	@Override
	public void setDeclare(boolean declare) {
		setAttribute("declare", declare ? "declare" : null);
	}

	@Override
	public void setHeight(String height) {
		setAttribute("height", height);
	}

	@Override
	public void setHspace(int hspace) {
		setAttribute("hspace", String.valueOf(hspace));
	}

	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	public void setObject(String object) {
		setAttribute("object", object);
	}

	@Override
	public void setStandby(String standby) {
		setAttribute("standby", standby);
	}

	@Override
	public void setTabIndex(int tabIndex) {
		setAttribute("tabindex", String.valueOf(tabIndex));
	}

	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	@Override
	public void setUseMap(String useMap) {
		setAttribute("usemap", useMap);
	}

	@Override
	public void setVspace(int vspace) {
		setAttribute("vspace", String.valueOf(vspace));
	}

	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
