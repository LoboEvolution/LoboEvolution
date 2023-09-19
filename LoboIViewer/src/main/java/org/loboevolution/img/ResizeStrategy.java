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

package org.loboevolution.img;

/**
 * Strategy for resizing an image inside a component.
 *
 * Author Kazo Csaba
 *
 */
/*
 * These constants are referenced in the following places: -
 * ImageComponent.getImageTransform() - CustomViewportLayout.layoutComponent() -
 * ImageViewer.createPopup() -
 * LayeredImageView.ScrollableLayeredPane.getScrollableTracksViewportXxx()
 */
public enum ResizeStrategy {
	/** The image is displayed in its original size. */
	NO_RESIZE,
	/** If the image doesn't fit in the component, it is shrunk to the best fit. */
	SHRINK_TO_FIT,
	/**
	 * Shrink or enlarge the image to optimally fit the component (keeping aspect
	 * ratio).
	 */
	RESIZE_TO_FIT,
	/** Custom fixed zoom */
	CUSTOM_ZOOM
}
