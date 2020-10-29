/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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

package org.loboevolution.html.dom.smil;

/**
 * This interface define the set of animation target extensions.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ElementTargetAttributes {

	// attributeTypes
	/** Constant ATTRIBUTE_TYPE_AUTO=0 */
    short ATTRIBUTE_TYPE_AUTO = 0;

	/** Constant ATTRIBUTE_TYPE_CSS=1 */
    short ATTRIBUTE_TYPE_CSS = 1;
	
	/** Constant ATTRIBUTE_TYPE_XML=2 */
    short ATTRIBUTE_TYPE_XML = 2;
	
	/**
	 * The name of the target attribute.
	 *
	 * @return a {@link java.lang.String} object.
	 */
    String getAttributeName();

	/**
	 * <p>setAttributeName.</p>
	 *
	 * @param attributeName a {@link java.lang.String} object.
	 */
    void setAttributeName(String attributeName);

	/**
	 * A code representing the value of the attributeType attribute, as defined
	 * above. Default value is ATTRIBUTE_TYPE_CODE .
	 *
	 * @return a short.
	 */
    short getAttributeType();

	/**
	 * <p>setAttributeType.</p>
	 *
	 * @param attributeType a short.
	 */
    void setAttributeType(short attributeType);

}
