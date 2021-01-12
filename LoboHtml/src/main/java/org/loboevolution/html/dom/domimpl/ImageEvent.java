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

import java.util.EventObject;

/**
 * <p>ImageEvent class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ImageEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final java.awt.Image image;

	/**
	 * <p>Constructor for ImageEvent.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param image a {@link java.awt.Image} object.
	 */
	public ImageEvent(Object source, java.awt.Image image) {
		super(source);
		this.image = image;
	}
}
