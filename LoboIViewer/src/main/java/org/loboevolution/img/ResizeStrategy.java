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
