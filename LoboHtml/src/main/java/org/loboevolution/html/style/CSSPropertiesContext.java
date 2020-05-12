/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html.style;

/**
 * <p>CSSPropertiesContext interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface CSSPropertiesContext {
	/**
	 * <p>getDocumentBaseURI.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDocumentBaseURI();

	/**
	 * <p>getParentStyle.</p>
	 *
	 * @return a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 */
	AbstractCSSProperties getParentStyle();

	/**
	 * <p>informInvalid.</p>
	 */
	void informInvalid();

	/**
	 * <p>informLayoutInvalid.</p>
	 */
	void informLayoutInvalid();

	/**
	 * <p>informLookInvalid.</p>
	 */
	void informLookInvalid();

	/**
	 * <p>informPositionInvalid.</p>
	 */
	void informPositionInvalid();

	/**
	 * <p>informSizeInvalid.</p>
	 */
	void informSizeInvalid();
}
