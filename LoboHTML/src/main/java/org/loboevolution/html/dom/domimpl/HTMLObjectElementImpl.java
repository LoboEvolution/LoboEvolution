/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLObjectElement;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>HTMLObjectElementImpl class.</p>
 */
public class HTMLObjectElementImpl extends HTMLElementImpl implements HTMLObjectElement {
	/**
	 * <p>Constructor for HTMLObjectElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLObjectElementImpl(final String name) {
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
	public boolean isDeclare() {
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
	public double getHspace() {
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		return HtmlValues.getPixelSize(getAttribute("hspace"), null, doc.getDefaultView(), 0);
	}

	@Override
	public void setHspace(double hspace) {

	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		final String name = getAttribute("name");
		return name == null ? "" : name;
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
	public double getVspace() {
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		return HtmlValues.getPixelSize(getAttribute("vspace"), null, doc.getDefaultView(), 0);
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
	public void setType(String type) {
		setAttribute("type", type);
	}

	/** {@inheritDoc} */
	@Override
	public void setUseMap(String useMap) {
		setAttribute("usemap", useMap);
	}

	@Override
	public String getValidationMessage() {
		return null;
	}

	@Override
	public ValidityState getValidity() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setVspace(double vspace) {
		setAttribute("vspace", String.valueOf(vspace));
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}

	@Override
	public boolean isWillValidate() {
		return false;
	}

	@Override
	public boolean checkValidity() {
		return false;
	}

	@Override
	public Document getSVGDocument() {
		return null;
	}

	@Override
	public boolean reportValidity() {
		return false;
	}

	@Override
	public void setCustomValidity(String error) {

	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLObjectElement]";
	}
}
