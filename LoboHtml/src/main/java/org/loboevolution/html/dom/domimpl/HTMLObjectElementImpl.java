package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLObjectElement;
import org.loboevolution.html.style.HtmlValues;
import org.w3c.dom.Document;

/**
 * <p>HTMLObjectElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLObjectElementImpl extends HTMLAbstractUIElement implements HTMLObjectElement {
	/**
	 * <p>Constructor for HTMLObjectElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLObjectElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/**
	 * <p>getAlt.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAlt() {
		return getAttribute("alt");
	}

	/** {@inheritDoc} */
	@Override
	public String getArchive() {
		return getAttribute("archive");
	}

	/** {@inheritDoc} */
	@Override
	public String getBorder() {
		return getAttribute("border");
	}

	/** {@inheritDoc} */
	@Override
	public String getCode() {
		return getAttribute("code");
	}

	/** {@inheritDoc} */
	@Override
	public String getCodeBase() {
		return getAttribute("codebase");
	}

	/** {@inheritDoc} */
	@Override
	public String getCodeType() {
		return getAttribute("codetype");
	}

	/** {@inheritDoc} */
	@Override
	public Document getContentDocument() {
		return getOwnerDocument();
	}

	/** {@inheritDoc} */
	@Override
	public String getData() {
		return getAttribute("data");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getDeclare() {
		return "declare".equalsIgnoreCase(getAttribute("declare"));
	}

	/** {@inheritDoc} */
	@Override
	public HTMLFormElement getForm() {
		return (HTMLFormElement) getAncestorForJavaClass(HTMLFormElement.class);
	}

	/** {@inheritDoc} */
	@Override
	public String getHeight() {
		return getAttribute("height");
	}

	/** {@inheritDoc} */
	@Override
	public int getHspace() {
		return HtmlValues.getPixelSize(getAttribute("hspace"), null, 0);
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}

	/**
	 * <p>getObject.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getObject() {
		return getAttribute("object");
	}

	/** {@inheritDoc} */
	@Override
	public String getStandby() {
		return getAttribute("standby");
	}

	/** {@inheritDoc} */
	@Override
	public int getTabIndex() {
		return HtmlValues.getPixelSize(getAttribute("tabindex"), null, 0);
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return getAttribute("type");
	}

	/** {@inheritDoc} */
	@Override
	public String getUseMap() {
		return getAttribute("usemap");
	}

	/** {@inheritDoc} */
	@Override
	public int getVspace() {
		return HtmlValues.getPixelSize(getAttribute("vspace"), null, 0);
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/**
	 * <p>setAlt.</p>
	 *
	 * @param alt a {@link java.lang.String} object.
	 */
	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	/** {@inheritDoc} */
	@Override
	public void setArchive(String archive) {
		setAttribute("archive", archive);
	}

	/** {@inheritDoc} */
	@Override
	public void setBorder(String border) {
		setAttribute("border", border);
	}

	/** {@inheritDoc} */
	@Override
	public void setCode(String code) {
		setAttribute("code", code);
	}

	/** {@inheritDoc} */
	@Override
	public void setCodeBase(String codeBase) {
		setAttribute("codebase", codeBase);
	}

	/** {@inheritDoc} */
	@Override
	public void setCodeType(String codeType) {
		setAttribute("codetype", codeType);
	}

	/** {@inheritDoc} */
	@Override
	public void setData(String data) {
		setAttribute("data", data);
	}

	/** {@inheritDoc} */
	@Override
	public void setDeclare(boolean declare) {
		setAttribute("declare", declare ? "declare" : null);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(String height) {
		setAttribute("height", height);
	}

	/** {@inheritDoc} */
	@Override
	public void setHspace(int hspace) {
		setAttribute("hspace", String.valueOf(hspace));
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	/**
	 * <p>setObject.</p>
	 *
	 * @param object a {@link java.lang.String} object.
	 */
	public void setObject(String object) {
		setAttribute("object", object);
	}

	/** {@inheritDoc} */
	@Override
	public void setStandby(String standby) {
		setAttribute("standby", standby);
	}

	/** {@inheritDoc} */
	@Override
	public void setTabIndex(int tabIndex) {
		setAttribute("tabindex", String.valueOf(tabIndex));
	}

	/** {@inheritDoc} */
	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	/** {@inheritDoc} */
	@Override
	public void setUseMap(String useMap) {
		setAttribute("usemap", useMap);
	}

	/** {@inheritDoc} */
	@Override
	public void setVspace(int vspace) {
		setAttribute("vspace", String.valueOf(vspace));
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
