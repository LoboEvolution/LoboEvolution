/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.w3c.html;

import org.loboevolution.html.js.object.Window;
import org.w3c.dom.Document;

/**
 * The Interface HTMLObjectElement.
 */
public interface HTMLObjectElement extends HTMLElement {

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	// HTMLObjectElement
	public String getData();

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(String data);

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type);

	/**
	 * Gets the type must match.
	 *
	 * @return the type must match
	 */
	public boolean getTypeMustMatch();

	/**
	 * Sets the type must match.
	 *
	 * @param typeMustMatch
	 *            the new type must match
	 */
	public void setTypeMustMatch(boolean typeMustMatch);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name);

	/**
	 * Gets the use map.
	 *
	 * @return the use map
	 */
	public String getUseMap();

	/**
	 * Sets the use map.
	 *
	 * @param useMap
	 *            the new use map
	 */
	public void setUseMap(String useMap);

	/**
	 * Gets the form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public String getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(String width);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public String getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(String height);

	/**
	 * Gets the content document.
	 *
	 * @return the content document
	 */
	public Document getContentDocument();

	/**
	 * Gets the content window.
	 *
	 * @return the content window
	 */
	public Window getContentWindow();

	/**
	 * Gets the will validate.
	 *
	 * @return the will validate
	 */
	public boolean getWillValidate();

	/**
	 * Gets the validity.
	 *
	 * @return the validity
	 */
	public ValidityState getValidity();

	/**
	 * Gets the validation message.
	 *
	 * @return the validation message
	 */
	public String getValidationMessage();

	/**
	 * Check validity.
	 *
	 * @return true, if successful
	 */
	public boolean checkValidity();

	/**
	 * Sets the custom validity.
	 *
	 * @param error
	 *            the new custom validity
	 */
	public void setCustomValidity(String error);

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	// HTMLObjectElement-23
	public String getAlign();

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align);

	/**
	 * Gets the archive.
	 *
	 * @return the archive
	 */
	public String getArchive();

	/**
	 * Sets the archive.
	 *
	 * @param archive
	 *            the new archive
	 */
	public void setArchive(String archive);

	/**
	 * Gets the border.
	 *
	 * @return the border
	 */
	public String getBorder();

	/**
	 * Sets the border.
	 *
	 * @param border
	 *            the new border
	 */
	public void setBorder(String border);

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode();

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the new code
	 */
	public void setCode(String code);

	/**
	 * Gets the code base.
	 *
	 * @return the code base
	 */
	public String getCodeBase();

	/**
	 * Sets the code base.
	 *
	 * @param codeBase
	 *            the new code base
	 */
	public void setCodeBase(String codeBase);

	/**
	 * Gets the code type.
	 *
	 * @return the code type
	 */
	public String getCodeType();

	/**
	 * Sets the code type.
	 *
	 * @param codeType
	 *            the new code type
	 */
	public void setCodeType(String codeType);

	/**
	 * Gets the declare.
	 *
	 * @return the declare
	 */
	public boolean getDeclare();

	/**
	 * Sets the declare.
	 *
	 * @param declare
	 *            the new declare
	 */
	public void setDeclare(boolean declare);

	/**
	 * Gets the hspace.
	 *
	 * @return the hspace
	 */
	public int getHspace();

	/**
	 * Sets the hspace.
	 *
	 * @param hspace
	 *            the new hspace
	 */
	public void setHspace(int hspace);

	/**
	 * Gets the standby.
	 *
	 * @return the standby
	 */
	public String getStandby();

	/**
	 * Sets the standby.
	 *
	 * @param standby
	 *            the new standby
	 */
	public void setStandby(String standby);

	/**
	 * Gets the vspace.
	 *
	 * @return the vspace
	 */
	public int getVspace();

	/**
	 * Sets the vspace.
	 *
	 * @param vspace
	 *            the new vspace
	 */
	public void setVspace(int vspace);
}
