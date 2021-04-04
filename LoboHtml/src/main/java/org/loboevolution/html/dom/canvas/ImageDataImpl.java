/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.canvas;

import java.awt.image.BufferedImage;

import org.loboevolution.html.dom.ImageData;

/**
 * <p>ImageDataImpl class.</p>
 *
 *
 *
 */
public class ImageDataImpl implements ImageData {

	private final int width;
	
	private final int height;
	
	private final BufferedImage image;

	/**
	 * <p>Constructor for ImageDataImpl.</p>
	 *
	 * @param image a {@link java.awt.image.BufferedImage} object.
	 * @param sw a int.
	 * @param sh a int.
	 */
	public ImageDataImpl(BufferedImage image, int sw, int sh) {
		this.image = image;
		this.width = sw;
		this.height = sh;
	}

	/** {@inheritDoc} */
	@Override
	public Object getData() {
		return image;
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		return height;
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		return width;
	}
}
