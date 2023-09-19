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

package org.loboevolution.html.dom;

/**
 * The Interface HTMLCanvasElement.
 *
 *
 *
 */
public interface HTMLCanvasElement extends HTMLElement {
	
	/** The fill. */
	int FILL = 0;

	/** The fill rect. */
	int FILL_RECT = 1;

	/** The fill text. */
	int FILL_TEXT = 2;

	/** The circle. */
	int CIRCLE = 3;

	/** The stroke. */
	int STROKE = 4;

	/** The stroke rect. */
	int STROKE_RECT = 5;

	/** The stroke text. */
	int STROKE_TEXT = 6;

	/** The rect. */
	int RECT = 7;

	/** The image. */
	int IMAGE = 8;

	/** The image clip. */
	int IMAGE_CLIP = 9;

	/** The clear rect. */
	int CLEAR_RECT = 10;

    /**
     * Gets the width.
     *
     * @return the width
     */
    int getWidth();

    /**
     * Sets the width.
     *
     * @param width
     *            the new width
     */
    void setWidth(final int width);

    /**
     * Gets the height.
     *
     * @return the height
     */
    int getHeight();

    /**
     * Sets the height.
     *
     * @param height
     *            the new height
     */
    void setHeight(int height);

    /**
     * To data url.
     *
     * @return the string
     */
    String toDataURL();

    /**
     * To data url.
     *
     * @param type
     *            the type
     * @param args
     *            the args
     * @return the string
     */
    String toDataURL(String type, Object... args);

    /**
     * To blob.
     *
     * @param callback
     *            the callback
     */
    void toBlob(FileCallback callback);

    /**
     * To blob.
     *
     * @param callback
     *            the callback
     * @param type
     *            the type
     * @param args
     *            the args
     */
    void toBlob(FileCallback callback, String type, Object... args);

    /**
     * Gets the context.
     *
     * @param contextId
     *            the context id
     * @return the context
     */
    CanvasRenderingContext2D getContext(String contextId);
}
