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
package org.loboevolution.html.dom.canvas;

import java.awt.image.BufferedImage;

import org.loboevolution.html.dom.ImageData;
import org.mozilla.javascript.typedarrays.NativeArrayBuffer;
import org.mozilla.javascript.typedarrays.NativeUint8ClampedArray;

/**
 * <p>ImageDataImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ImageDataImpl implements ImageData {

	private int sx;
	
	private int sy;
	
	private int width;
	
	private int height;
	
	private BufferedImage image;

	/**
	 * <p>Constructor for ImageDataImpl.</p>
	 *
	 * @param image a {@link java.awt.image.BufferedImage} object.
	 * @param sx a int.
	 * @param sy a int.
	 * @param sw a int.
	 * @param sh a int.
	 */
	public ImageDataImpl(BufferedImage image, int sx, int sy, int sw, int sh) {
		this.image = image;
		this.sx = sx;
		this.sy = sy;
		this.width = sw;
		this.height = sh;
	}

	/** {@inheritDoc} */
	@Override
	public Object getData() {
		byte[] bytes = getBytes(width, height, sx, sy);
		final NativeArrayBuffer arrayBuffer = new NativeArrayBuffer(bytes.length);
        System.arraycopy(bytes, 0, arrayBuffer.getBuffer(), 0, bytes.length);
        return new NativeUint8ClampedArray(arrayBuffer, 0, bytes.length);
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

	private byte[] getBytes(final int width, final int height, final int sx, final int sy) {
		final byte[] array = new byte[width * height * 4];
		int index = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final int color = image.getRGB(sx + x, sy + y);
				array[index++] = (byte) ((color & 0xff0000) >> 16);
				array[index++] = (byte) ((color & 0xff00) >> 8);
				array[index++] = (byte) (color & 0xff);
				array[index++] = (byte) ((color & 0xff000000) >>> 24);
			}
		}
		return array;
	}
}
