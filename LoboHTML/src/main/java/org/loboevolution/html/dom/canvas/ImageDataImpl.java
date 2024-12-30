/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.loboevolution.html.dom.ImageData;

import java.awt.image.BufferedImage;

/**
 * <p>ImageDataImpl class.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class ImageDataImpl implements ImageData {

	private int width;
	
	private int height;

	private Object data;


	/**
	 * <p>Constructor for ImageDataImpl.</p>
	 *
	 * @param image a {@link java.awt.image.BufferedImage} object.
	 * @param sw a {@link java.lang.Integer} object.
	 * @param sh a {@link java.lang.Integer} object.
	 */
	public ImageDataImpl(final BufferedImage image, final int sw, final int sh) {
		this.data = image;
		this.width = sw;
		this.height = sh;
	}
}
