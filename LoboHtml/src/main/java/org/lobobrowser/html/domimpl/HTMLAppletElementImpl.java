package org.lobobrowser.html.domimpl;

import org.w3c.dom.html.HTMLAppletElement;

public class HTMLAppletElementImpl extends HTMLAbstractUIElement implements HTMLAppletElement {
	public HTMLAppletElementImpl(String name) {
		super(name);
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public String getAlt() {
		return getAttribute("alt");
	}

	@Override
	public String getArchive() {
		return getAttribute("archive");
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
	public String getHeight() {
		return getAttribute("height");
	}

	@Override
	public String getHspace() {
		return getAttribute("hspace");
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}

	@Override
	public String getObject() {
		return getAttribute("object");
	}

	@Override
	public String getVspace() {
		return getAttribute("vspace");
	}

	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	@Override
	public void setArchive(String archive) {
		setAttribute("archive", archive);
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
	public void setHeight(String height) {
		setAttribute("height", height);
	}

	@Override
	public void setHspace(String hspace) {
		setAttribute("hspace", hspace);
	}

	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	@Override
	public void setObject(String object) {
		setAttribute("object", object);
	}

	@Override
	public void setVspace(String vspace) {
		setAttribute("vspace", vspace);
	}

	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
