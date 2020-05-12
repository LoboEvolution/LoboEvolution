package org.loboevolution.img;

/**
 * Strategy for resizing an image inside a component.
 *
 * @author Kazo Csaba
 * @version $Id: $Id
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
