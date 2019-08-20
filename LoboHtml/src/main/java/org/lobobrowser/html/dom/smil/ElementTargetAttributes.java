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

package org.lobobrowser.html.dom.smil;

/**
 * This interface define the set of animation target extensions.
 */
public interface ElementTargetAttributes {

	// attributeTypes
	public static final short ATTRIBUTE_TYPE_AUTO = 0;

	public static final short ATTRIBUTE_TYPE_CSS = 1;
	
	public static final short ATTRIBUTE_TYPE_XML = 2;
	
	/**
	 * The name of the target attribute.
	 */
	public String getAttributeName();

	public void setAttributeName(String attributeName);

	/**
	 * A code representing the value of the attributeType attribute, as defined
	 * above. Default value is <code>ATTRIBUTE_TYPE_CODE</code> .
	 */
	public short getAttributeType();

	public void setAttributeType(short attributeType);

}
