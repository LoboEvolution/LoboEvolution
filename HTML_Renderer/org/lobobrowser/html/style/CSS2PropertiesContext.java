/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.style;


/**
 * The Interface CSS2PropertiesContext.
 */
public interface CSS2PropertiesContext {
	
	/**
	 * Inform look invalid.
	 */
	public void informLookInvalid();

	/**
	 * Inform size invalid.
	 */
	public void informSizeInvalid();

	/**
	 * Inform position invalid.
	 */
	public void informPositionInvalid();

	/**
	 * Inform layout invalid.
	 */
	public void informLayoutInvalid();

	/**
	 * Inform invalid.
	 */
	public void informInvalid();

	/**
	 * Gets the parent style.
	 *
	 * @return the parent style
	 */
	public AbstractCSS2Properties getParentStyle();

	/**
	 * Gets the document base uri.
	 *
	 * @return the document base uri
	 */
	public String getDocumentBaseURI();
}
