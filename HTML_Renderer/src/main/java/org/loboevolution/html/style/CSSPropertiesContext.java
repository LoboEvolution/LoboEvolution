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
package org.loboevolution.html.style;

/**
 * The Interface CSSPropertiesContext.
 */
public interface CSSPropertiesContext {

	/**
	 * Inform look invalid.
	 */
	void informLookInvalid();

	/**
	 * Inform size invalid.
	 */
	void informSizeInvalid();

	/**
	 * Inform position invalid.
	 */
	void informPositionInvalid();

	/**
	 * Inform layout invalid.
	 */
	void informLayoutInvalid();

	/**
	 * Inform invalid.
	 */
	void informInvalid();

	/**
	 * Gets the parent style.
	 *
	 * @return the parent style
	 */
	AbstractCSSProperties getParentStyle();

	/**
	 * Gets the document base uri.
	 *
	 * @return the document base uri
	 */
	String getDocumentBaseURI();
}
