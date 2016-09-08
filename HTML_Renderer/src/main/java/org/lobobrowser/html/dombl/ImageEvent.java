/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.dombl;

import java.awt.Image;
import java.util.EventObject;

/**
 * The Class ImageEvent.
 */
public class ImageEvent extends EventObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1101831272894733276L;

	/** The image. */
	public final Image image;

	/**
	 * Instantiates a new image event.
	 *
	 * @param source
	 *            the source
	 * @param image
	 *            the image
	 */
	public ImageEvent(Object source, Image image) {
		super(source);
		this.image = image;
	}
}
