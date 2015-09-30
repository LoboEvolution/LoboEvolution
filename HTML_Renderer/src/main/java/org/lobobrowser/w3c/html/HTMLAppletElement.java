/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;


/**
 * The Interface HTMLAppletElement.
 */
public interface HTMLAppletElement extends HTMLElement {
	
	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	// HTMLAppletElement
	public String getAlign();

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align);

	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	public String getAlt();

	/**
	 * Sets the alt.
	 *
	 * @param alt
	 *            the new alt
	 */
	public void setAlt(String alt);

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
	 * Gets the object.
	 *
	 * @return the object
	 */
	public String getObject();

	/**
	 * Sets the object.
	 *
	 * @param object
	 *            the new object
	 */
	public void setObject(String object);

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
}
