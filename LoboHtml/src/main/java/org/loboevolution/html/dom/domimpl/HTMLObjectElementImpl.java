/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLObjectElement;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.ValidityState;

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
	public String getAccessKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAutocapitalize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSpellcheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutocapitalize(String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDraggable(boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslate(boolean translate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHspace(double hspace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isWillValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Document getSVGDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reportValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub
		
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLObjectElement]";
	}
}
