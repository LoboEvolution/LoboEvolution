/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.canvas;

import org.loboevolution.html.dom.ImageData;

import java.awt.image.BufferedImage;

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
