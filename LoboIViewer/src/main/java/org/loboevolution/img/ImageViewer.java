/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;


/**
 * A general purpose image viewer component. It contains a scroll pane and
 * manages the size of the image in accordance with the
 * {@link #setResizeStrategy(ResizeStrategy) resize strategy} property. It can
 * also provide a popup menu containing generally useful viewing controls and
 * functions; this default popup can be disabled by passing
 * {@code defaultPopupMenu = null} to the constructor.
 * <p>
 * The Swing component that can be added to the GUI is obtained by calling
 * {@link #getComponent()}.
 * <p>
 * ImageViewer supports {@link #setStatusBar(StatusBar) status bars}: arbitrary
 * components that can be added to the viewer and are displayed below the image.
 * <p>
 * {@link #addOverlay Overlays} can also be added to the viewer; for details,
 * see the the documentation of the {@link org.loboevolution.img.Overlay} class.
 *
 * @see StatusBar
 * @see Overlay
 * Author Kaz Csaba
 *
 */
public final class ImageViewer {
	private final LayeredImageView view;
	private final ImageComponent theImage;
	private final JScrollPane scroller;
	private final JPanel panel;
	private StatusBar statusBar;
	private boolean statusBarVisible = false;
	private final PropertyChangeSupport propertyChangeSupport;
	private Synchronizer synchronizer;
	private final JPopupMenu popup;

	/**
	 * Creates a new image viewer. Initially it will be empty, and it will have a
	 * default popup menu.
	 */
	public ImageViewer() {
		this(null);
	}

	/**
	 * Creates a new image viewer displaying the specified image. TThe viewer will
	 * have a default popup menu.
	 *
	 * @param image
	 *            the image to display; if {@code null} then no image is displayed
	 */
	public ImageViewer(final BufferedImage image) {
		this(image, true);
	}

	/**
	 * Creates a new image viewer displaying the specified image.
	 *
	 * @param image
	 *            the image to display; if null then no image is
	 *            displayed
	 * @param defaultPopupMenu
	 *            if {@code true}, then a default popup menu will be created and
	 *            registered for the viewer
	 * @see #setImage(BufferedImage)
	 */
	public ImageViewer(final BufferedImage image, final boolean defaultPopupMenu) {
		propertyChangeSupport = new PropertyChangeSupport(this);
		panel = new JPanel(new FlowLayout());
		scroller = new JScrollPane() {
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected JViewport createViewport() {
				return new JViewport() {

					@Serial
					private static final long serialVersionUID = 1L;

					@Override
					protected LayoutManager createLayoutManager() {
						return new CustomViewportLayout(ImageViewer.this);
					}

					@Override
					public Dimension getMaximumSize() {
						return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
					}

				};
			}

			@Override
			public boolean isValidateRoot() {
				return false;
			}

		};
		synchronizer = new Synchronizer(this);
		theImage = new ImageComponent(this, propertyChangeSupport);
		view = new LayeredImageView(theImage);
		scroller.setViewportView(view.getComponent());
		theImage.setImage(image);

		panel.add(scroller);

		setStatusBar(new DefaultStatusBar());
		popup = new DefaultViewerPopup(ImageViewer.this);

		if (defaultPopupMenu) {
			theImage.addMouseListener(new ImageMouseAdapter(popup, panel) );
		}
	}

	/**
	 * Sets the status bar component for this image viewer. The new status bar is
	 * made visible only if the statusBarVisible property is true. If
	 * statusBar is null this method removes any existing
	 * status bar.
	 *
	 * @param statusBar
	 *            the new status bar component to set
	 * @throws java.lang.IllegalArgumentException
	 *             if the status bar has already been added to a different image
	 *             viewer
	 */
	public void setStatusBar(final StatusBar statusBar) {
		if (statusBar == this.statusBar)
			return;
		if (statusBar.getImageViewer() != null)
			throw new IllegalArgumentException("Status bar already added to an image viewer");
		final StatusBar oldStatusBar = this.statusBar;
		if (oldStatusBar != null) {
			panel.remove(oldStatusBar.getComponent());
			oldStatusBar.setImageViewer(null);
		}
		this.statusBar = statusBar;
		if (this.statusBar != null) {
			this.statusBar.setImageViewer(this);
			if (statusBarVisible) {
				panel.add(this.statusBar.getComponent(), BorderLayout.SOUTH);
				panel.revalidate();
				panel.repaint();
			}
		}
		propertyChangeSupport.firePropertyChange("statusBar", oldStatusBar, statusBar);
	}

	/**
	 * Returns the status bar currently associated with this viewer.
	 *
	 * @return the current status bar, or {@code null} if the viewer has no status
	 *         bar
	 */
	public StatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * Sets whether the status bar is visible. The status bar is hidden by default.
	 *
	 * @param statusBarVisible
	 *            true, if the status bar should be visible; false otherwise
	 */
	public void setStatusBarVisible(final boolean statusBarVisible) {
		if (this.statusBarVisible == statusBarVisible)
			return;
		if (statusBar != null) {
			if (statusBarVisible)
				panel.add(statusBar.getComponent(), BorderLayout.SOUTH);
			else
				panel.remove(statusBar.getComponent());
			panel.revalidate();
			panel.repaint();
		}
		final boolean prev = this.statusBarVisible;
		this.statusBarVisible = statusBarVisible;
		synchronizer.statusBarVisibilityChanged(this);
		propertyChangeSupport.firePropertyChange("statusBarVisible", prev, statusBarVisible);
	}

	/**
	 * Returns whether the status bar is set to be visible. The status bar is hidden
	 * by default.
	 *
	 * @return the statusBarVisible property
	 */
	public boolean isStatusBarVisible() {
		return statusBarVisible;
	}

	/**
	 * Returns the image viewer component that can be displayed.
	 *
	 * @return the image viewer component
	 */
	public JComponent getComponent() {
		return panel;
	}

	/**
	 * Sets the image displayed by the viewer. If the argument is the same object as
	 * the image currently being displayed, then this method will trigger a refresh.
	 * If you modify the image shown by the viewer, use this function to notify the
	 * component and cause it to update.
	 *
	 * @param image
	 *            the new image to display; if null then no image is
	 *            displayed
	 */
	public void setImage(final BufferedImage image) {
		theImage.setImage(image);
	}

	/**
	 * Returns the currently displayed image.
	 *
	 * @return the current image, or null if no image is displayed
	 */
	public BufferedImage getImage() {
		return theImage.getImage();
	}

	/**
	 * Sets the resize strategy this viewer should use. The default is
	 * {@link org.loboevolution.img.ResizeStrategy#SHRINK_TO_FIT}.
	 *
	 * @param resizeStrategy
	 *            the new resize strategy
	 */
	public void setResizeStrategy(final ResizeStrategy resizeStrategy) {
		theImage.setResizeStrategy(resizeStrategy);
	}

	/**
	 * Returns the current resize strategy. The default is
	 * {@link org.loboevolution.img.ResizeStrategy#SHRINK_TO_FIT}.
	 *
	 * @return the current resize strategy
	 */
	public ResizeStrategy getResizeStrategy() {
		return theImage.getResizeStrategy();
	}

	/**
	 * Sets whether the image should be resized with nearest neighbor interpolation
	 * when it is expanded. The default is {@code false}.
	 *
	 * @param pixelatedZoom
	 *            the new value of the pixelatedZoom property
	 */
	public void setPixelatedZoom(final boolean pixelatedZoom) {
		theImage.setPixelatedZoom(pixelatedZoom);
	}

	/**
	 * Returns the current pixelated zoom setting. The default is {@code false}.
	 *
	 * @return the current pixelated zoom setting
	 */
	public boolean isPixelatedZoom() {
		return theImage.isPixelatedZoom();
	}

	/**
	 * Returns the current interpolation type. The default is
	 * {@link java.awt.RenderingHints#VALUE_INTERPOLATION_BICUBIC}.
	 *
	 * @return the interpolation type
	 * @see #setInterpolationType(Object)
	 */
	public Object getInterpolationType() {
		return theImage.getInterpolationType();
	}

	/**
	 * Sets the interpolation type to use when resizing images. See
	 * {@link java.awt.RenderingHints#KEY_INTERPOLATION} for details. The default
	 * value is {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC}.
	 * <p>
	 * The allowed values are:
	 * <ul>
	 * <li>{@link java.awt.RenderingHints#VALUE_INTERPOLATION_NEAREST_NEIGHBOR}
	 * <li>{@link java.awt.RenderingHints#VALUE_INTERPOLATION_BILINEAR}
	 * <li>{@link java.awt.RenderingHints#VALUE_INTERPOLATION_BICUBIC} (default)
	 * </ul>
	 * Changing the interpolation type to bilinear or nearest neighbor improves
	 * painting performance when the image needs to be resized.
	 * <p>
	 * Note: when the {@code pixelatedZoom} property is set to true and the image is
	 * enlarged, then the nearest neighbor method is used regardless of this
	 * setting.
	 *
	 * @param type
	 *            the interpolation type to use when resizing images
	 * @throws java.lang.IllegalArgumentException
	 *             if the parameter is not one of the allowed values
	 */
	public void setInterpolationType(final Object type) {
		theImage.setInterpolationType(type);
	}

	/**
	 * Returns the zoom factor used when resize strategy is CUSTOM_ZOOM. The default
	 * value is 1.
	 *
	 * @return the custom zoom factor
	 */
	public double getZoomFactor() {
		return theImage.getZoomFactor();
	}

	/**
	 * Sets the zoom factor to use when the resize strategy is CUSTOM_ZOOM. The
	 * default value is 1.
	 * <p>
	 * Note that calling this function does not change the current resize strategy.
	 *
	 * @param newZoomFactor
	 *            the new zoom factor for the CUSTOM_ZOOM strategy
	 * @throws java.lang.IllegalArgumentException
	 *             if {@code newZoomFactor} is not a positive number
	 */
	public void setZoomFactor(final double newZoomFactor) {
		theImage.setZoomFactor(newZoomFactor);
	}

	/**
	 * Returns the transformation that is applied to the image. Most commonly the
	 * transformation is the concatenation of a uniform scale and a translation.
	 * <p>
	 * The AffineTransform instance returned by this method should not
	 * be modified.
	 *
	 * @return the transformation applied to the image before painting
	 * @throws java.lang.IllegalStateException
	 *             if there is no image set or if the size of the viewer is 0 (for
	 *             example because it is not in a visible component)
	 */
	public AffineTransform getImageTransform() {
		return theImage.getImageTransform();
	}

	/**
	 * Adds an overlay as the specified layer.
	 *
	 * @param overlay
	 *            the overlay to add
	 * @param layer
	 *            the layer to add the overlay to; higher layers are on top of lower
	 *            layers; the image resides in layer 0
	 */
	public void addOverlay(final Overlay overlay, final int layer) {
		view.addOverlay(overlay, layer);
	}

	/**
	 * Adds an overlay to layer 1.
	 *
	 * @param overlay
	 *            the overlay to add
	 */
	public void addOverlay(final Overlay overlay) {
		addOverlay(overlay, 1);
	}

	/**
	 * Removes an overlay from the image viewer.
	 *
	 * @param overlay
	 *            the overlay to remove
	 * @throws java.lang.IllegalArgumentException
	 *             if the overlay is not in the image viewer
	 */
	public void removeOverlay(final Overlay overlay) {
		view.removeOverlay(overlay);
	}

	/**
	 * Adds the specified mouse listener to receive mouse events from the image
	 * component of this image viewer. If listener l is
	 * null, no exception is thrown and no action is performed.
	 *
	 * @param l
	 *            the mouse listener
	 */
	public void addMouseListener(final MouseListener l) {
		theImage.addMouseListener(l);
	}

	/**
	 * Removes the specified mouse listener so that it no longer receives mouse
	 * motion events from the image component of this image viewer. This method
	 * performs no function, nor does it throw an exception, if the listener
	 * specified by the argument was not previously added to this component. If
	 * listener l is null, no exception is thrown and no
	 * action is performed.
	 *
	 * @param l
	 *            the mouse motion listener
	 */
	public void removeMouseListener(final MouseListener l) {
		theImage.removeMouseListener(l);
	}

	/**
	 * Adds the specified mouse motion listener to receive mouse events from the
	 * image component of this image viewer. If listener l is
	 * null, no exception is thrown and no action is performed.
	 *
	 * @param l
	 *            the mouse listener
	 */
	public void addMouseMotionListener(final MouseMotionListener l) {
		theImage.addMouseMotionListener(l);
	}

	/**
	 * Removes the specified mouse motion listener so that it no longer receives
	 * mouse motion events from the image component of this image viewer. This
	 * method performs no function, nor does it throw an exception, if the listener
	 * specified by the argument was not previously added to this component. If
	 * listener l is null, no exception is thrown and no
	 * action is performed.
	 *
	 * @param l
	 *            the mouse motion listener
	 */
	public void removeMouseMotionListener(final MouseMotionListener l) {
		theImage.removeMouseMotionListener(l);
	}

	/**
	 * Adds the specified image mouse motion listener to this viewer. The listener
	 * is notified as the mouse moves over pixels of the image. If listener
	 * l is {@code null}, no exception is thrown and no action is
	 * performed.
	 *
	 * @param l
	 *            the image mouse motion listener
	 */
	public void addImageMouseMotionListener(final ImageMouseMotionListener l) {
		theImage.addImageMouseMoveListener(l);
	}

	/**
	 * Removes the specified image mouse motion listener so that it no longer
	 * receives mouse motion events from the image component of this image viewer.
	 * This method performs no function, nor does it throw an exception, if the
	 * listener specified by the argument was not previously added to this
	 * component. If listener l is {@code null}, no exception is thrown
	 * and no action is performed.
	 *
	 * @param l
	 *            the mouse motion listener
	 */
	public void removeImageMouseMotionListener(final ImageMouseMotionListener l) {
		theImage.removeImageMouseMoveListener(l);
	}

	/**
	 * Adds the specified image mouse listener to this viewer. The listener is
	 * notified as mouse buttons are clicked over pixels of the image. If listener
	 * l is {@code null}, no exception is thrown and no action is
	 * performed.
	 *
	 * @param l
	 *            the image mouse motion listener
	 */
	public void addImageMouseClickListener(final ImageMouseClickListener l) {
		theImage.addImageMouseClickListener(l);
	}

	/**
	 * Removes the specified image mouse listener so that it no longer receives
	 * mouse click events from the image component of this image viewer. This method
	 * performs no function, nor does it throw an exception, if the listener
	 * specified by the argument was not previously added to this component. If the
	 * listener l is {@code null}, no exception is thrown and no action
	 * is performed.
	 *
	 * @param l
	 *            the mouse motion listener
	 */
	public void removeImageMouseClickListener(final ImageMouseClickListener l) {
		theImage.removeImageMouseClickListener(l);
	}

	/**
	 * Adds a {@code PropertyChangeListener} to the listener list. The same listener
	 * object may be added more than once, and will be called as many times as it is
	 * added. If the listener is {@code null}, no exception is thrown and no action
	 * is taken.
	 *
	 * @param l
	 *            the listener to be added
	 */
	public void addPropertyChangeListener(final PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	/**
	 * Remove a {@code PropertyChangeListener} from the listener list. This removes
	 * a listener that was registered for all properties. If the listener was added
	 * more than once, it will be notified one less time after being removed. If the
	 * listener is {@code null}, or was never added, no exception is thrown and no
	 * action is taken.
	 *
	 * @param l
	 *            the listener to remove
	 */
	public void removePropertyChangeListener(final PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}

	/**
	 * Adds a {@code PropertyChangeListener} for a specific property. The listener
	 * will be invoked only when a call on firePropertyChange names that specific
	 * property. The same listener object may be added more than once. For each
	 * property, the listener will be invoked the number of times it was added for
	 * that property. If the property name or the listener is null, no exception is
	 * thrown and no action is taken.
	 *
	 * @param name
	 *            the name of the property to listen on
	 * @param l
	 *            the listener to add
	 */
	public void addPropertyChangeListener(final String name, final PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(name, l);
	}

	/**
	 * Remove a {@code PropertyChangeListener} from the listener list. This removes
	 * a PropertyChangeListener that was registered for all properties. If the
	 * listener was added more than once, it will be notified one less time after
	 * being removed. If the listener is {@code null}, or was never added, no
	 * exception is thrown and no action is taken.
	 *
	 * @param name
	 *            the name of the property that was listened on
	 * @param l
	 *            the listener to remove
	 */
	public void removePropertyChangeListener(final String name, final PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(name, l);
	}

	/**
	 * Returns the scroll pane of the image viewer.
	 *
	 * @return the scroll pane
	 */
	public JScrollPane getScrollPane() {
		return scroller;
	}

	/**
	 * <p>Getter for the field synchronizer.</p>
	 *
	 * @return a {@link org.loboevolution.img.Synchronizer} object.
	 */
	public Synchronizer getSynchronizer() {
		return synchronizer;
	}

	/**
	 * <p>Setter for the field synchronizer.</p>
	 *
	 * @param newSync a {@link org.loboevolution.img.Synchronizer} object.
	 */
	public void setSynchronizer(final Synchronizer newSync) {
		synchronizer = newSync;
	}

	/**
	 * Returns the image pixel corresponding to the given point. If the
	 * clipToImage parameter is false, then the function
	 * will return an appropriately positioned pixel on an infinite plane, even if
	 * the point is outside the image bounds. If clipToImage is
	 * true then the function will return null for such
	 * positions, and any non-null return value will be a valid image pixel.
	 *
	 * @param p
	 *            a point in component coordinate system
	 * @param clipToImage
	 *            whether the function should return null for positions
	 *            outside the image bounds
	 * @return the corresponding image pixel
	 * @throws java.lang.IllegalStateException
	 *             if there is no image set or if the size of the viewer is 0 (for
	 *             example because it is not in a visible component)
	 */
	public Point pointToPixel(final Point p, final boolean clipToImage) {
		return theImage.pointToPixel(p, clipToImage);
	}

	public JPopupMenu getPopupMenu() {
		return popup;
	}
}
