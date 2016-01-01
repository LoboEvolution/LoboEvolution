/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
 * The Interface HTMLImageElement.
 */
public interface HTMLImageElement extends HTMLElement {
	
	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	// HTMLImageElement
	public String getAlt();

	/**
	 * Sets the alt.
	 *
	 * @param alt
	 *            the new alt
	 */
	public void setAlt(String alt);

	/**
	 * Gets the src.
	 *
	 * @return the src
	 */
	public String getSrc();

	/**
	 * Sets the src.
	 *
	 * @param src
	 *            the new src
	 */
	public void setSrc(String src);

	/**
	 * Gets the cross origin.
	 *
	 * @return the cross origin
	 */
	public String getCrossOrigin();

	/**
	 * Sets the cross origin.
	 *
	 * @param crossOrigin
	 *            the new cross origin
	 */
	public void setCrossOrigin(String crossOrigin);

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
	 * Gets the checks if is map.
	 *
	 * @return the checks if is map
	 */
	public boolean getIsMap();

	/**
	 * Sets the checks if is map.
	 *
	 * @param isMap
	 *            the new checks if is map
	 */
	public void setIsMap(boolean isMap);

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(int width);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(int height);

	/**
	 * Gets the natural width.
	 *
	 * @return the natural width
	 */
	public int getNaturalWidth();

	/**
	 * Gets the natural height.
	 *
	 * @return the natural height
	 */
	public int getNaturalHeight();

	/**
	 * Gets the complete.
	 *
	 * @return the complete
	 */
	public boolean getComplete();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	// HTMLImageElement-16
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name);

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	public String getAlign();

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align);

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
	 * Gets the long desc.
	 *
	 * @return the long desc
	 */
	public String getLongDesc();

	/**
	 * Sets the long desc.
	 *
	 * @param longDesc
	 *            the new long desc
	 */
	public void setLongDesc(String longDesc);

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
