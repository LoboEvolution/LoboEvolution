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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;


/**
 * The component that displays the image itself.
 * Author Kaz Csaba
 */
class ImageComponent extends JComponent {
	
	@Serial
	private static final long serialVersionUID = 1L;
	private transient ResizeStrategy resizeStrategy = ResizeStrategy.SHRINK_TO_FIT;
	private transient BufferedImage image;
	private boolean pixelatedZoom = false;
	private transient Object interpolationType = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
	private double zoomFactor = 1;
	private final transient List<ImageMouseMotionListener> moveListeners = new ArrayList<>(4);
	private final transient List<ImageMouseClickListener> clickListeners = new ArrayList<>(4);
	private final transient MouseEventTranslator mouseEventTranslator = new MouseEventTranslator(this);
	private final transient PaintManager paintManager = new PaintManager(this);
	private final transient Rescroller rescroller = new Rescroller();
	private final PropertyChangeSupport propertyChangeSupport;
	private final transient ImageViewer viewer;

	/*
	 * Handles repositioning the scroll pane when the image is resized so that the
	 * same area remains visible.
	 */
	class Rescroller {
		private Point2D preparedCenter = null;

		public void prepare() {
			if (image != null && hasSize()) {
				final Rectangle viewRect = getViewer().getScrollPane().getViewport().getViewRect();
				preparedCenter = new Point2D.Double(viewRect.getCenterX(), viewRect.getCenterY());
				try {
					getImageTransform().inverseTransform(preparedCenter, preparedCenter);
				} catch (final NoninvertibleTransformException e) {
					throw new Error(e);
				}
			}
		}

		public void rescroll() {
			if (preparedCenter != null) {
				final Dimension viewSize = getViewer().getScrollPane().getViewport().getExtentSize();
				getImageTransform().transform(preparedCenter, preparedCenter);
				final Rectangle view = new Rectangle((int) Math.round(preparedCenter.getX() - viewSize.width / 2.0),
						(int) Math.round(preparedCenter.getY() - viewSize.height / 2.0), viewSize.width,
						viewSize.height);
				preparedCenter = null;
				scrollRectToVisible(view);
			}
		}

	}
	
	/**
	 * <p>Constructor for ImageComponent.</p>
	 *
	 * @param viewer a {@link org.loboevolution.img.ImageViewer} object.
	 * @param propertyChangeSupport a {@link java.beans.PropertyChangeSupport} object.
	 */
	public ImageComponent(final ImageViewer viewer, final PropertyChangeSupport propertyChangeSupport) {
		this.viewer = viewer;
		this.propertyChangeSupport = propertyChangeSupport;
		mouseEventTranslator.register(this);
		setOpaque(true);
		viewer.getScrollPane().getViewport().addChangeListener(e -> {
			/*
			 * Here the viewer might not have a size yet, because we might be before the
			 * first layout. But that's alright. As soon as we get our size, the viewport
			 * will send another state change.
			 */
			if (hasSize())
				mouseEventTranslator.correctionalFire();
		});
	}

	private boolean hasSize() {
		return getWidth() > 0 && getHeight() > 0;
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * <p>addImageMouseMoveListener.</p>
	 *
	 * @param l a {@link org.loboevolution.img.ImageMouseMotionListener} object.
	 */
	public void addImageMouseMoveListener(final ImageMouseMotionListener l) {
		if (l != null)
			getMoveListeners().add(l);
	}

	/**
	 * <p>removeImageMouseMoveListener.</p>
	 *
	 * @param l a {@link org.loboevolution.img.ImageMouseMotionListener} object.
	 */
	public void removeImageMouseMoveListener(final ImageMouseMotionListener l) {
		if (l != null)
			getMoveListeners().remove(l);
	}

	/**
	 * <p>addImageMouseClickListener.</p>
	 *
	 * @param l a {@link org.loboevolution.img.ImageMouseClickListener} object.
	 */
	public void addImageMouseClickListener(final ImageMouseClickListener l) {
		if (l != null)
			getClickListeners().add(l);
	}

	/**
	 * <p>removeImageMouseClickListener.</p>
	 *
	 * @param l a {@link org.loboevolution.img.ImageMouseClickListener} object.
	 */
	public void removeImageMouseClickListener(final ImageMouseClickListener l) {
		if (l != null)
			getClickListeners().remove(l);
	}

	/**
	 * <p>Setter for the field image.</p>
	 *
	 * @param newImage a {@link java.awt.image.BufferedImage} object.
	 */
	public void setImage(final BufferedImage newImage) {
		final BufferedImage oldImage = image;
		image = newImage;
		resizePanel();
		paintManager.notifyChanged();
		if (!Objects.equals(oldImage, newImage) && 
			(oldImage == null || newImage == null || 
			 oldImage.getWidth() != newImage.getWidth() || oldImage.getHeight() != newImage.getHeight())) {
			revalidate();
		}
		repaint();
		getPropertyChangeSupport().firePropertyChange("image", oldImage, newImage);
	}

	/**
	 * <p>Getter for the field image.</p>
	 *
	 * @return a {@link java.awt.image.BufferedImage} object.
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Preforms all necessary actions to ensure that the viewer is resized to its
	 * proper size. It does that by invoking {@code validate()} on the viewer's
	 * validateRoot. It also issues a {@code repaint()}.
	 */
	private void resizeNow() {
		invalidate();
		// find the validate root; adapted from the package-private
		// SwingUtilities.getValidateRoot
		Container root = null;
		Container c = this;
		for (; c != null; c = c.getParent()) {
			if (!c.isDisplayable() || c instanceof CellRendererPane) {
				return;
			}
			if (c.isValidateRoot()) {
				root = c;
				break;
			}
		}

		if (root == null)
			return;

		for (; c != null; c = c.getParent()) {
			if (!c.isDisplayable() || !c.isVisible()) {
				return;
			}
			if (c instanceof Window) {
				break;
			}
		}

		if (c == null)
			return;

		root.validate();
		repaint();
	}

	/**
	 * <p>Setter for the field resizeStrategy.</p>
	 *
	 * @param resizeStrategy a {@link org.loboevolution.img.ResizeStrategy} object.
	 */
	public void setResizeStrategy(final ResizeStrategy resizeStrategy) {
		if (resizeStrategy == this.resizeStrategy)
			return;
		rescroller.prepare();
		final ResizeStrategy oldResizeStrategy = this.resizeStrategy;
		this.resizeStrategy = resizeStrategy;
		final boolean canRescroll = getViewer().getSynchronizer().resizeStrategyChangedCanIRescroll(getViewer());
		resizeNow();

		if (canRescroll) {
			rescroller.rescroll();
			getViewer().getSynchronizer().doneRescrolling(getViewer());
		}
		getPropertyChangeSupport().firePropertyChange("resizeStrategy", oldResizeStrategy, resizeStrategy);
	}

	/**
	 * <p>Getter for the field resizeStrategy.</p>
	 *
	 * @return a {@link org.loboevolution.img.ResizeStrategy} object.
	 */
	public ResizeStrategy getResizeStrategy() {
		return resizeStrategy;
	}

	/**
	 * <p>Setter for the field interpolationType.</p>
	 *
	 * @param type a {@link java.lang.Object} object.
	 */
	public void setInterpolationType(final Object type) {
		if (Objects.equals(interpolationType, type))
			return;
		if (type != RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
				&& type != RenderingHints.VALUE_INTERPOLATION_BILINEAR
				&& type != RenderingHints.VALUE_INTERPOLATION_BICUBIC)
			throw new IllegalArgumentException("Invalid interpolation type; use one of the RenderingHints constants");
		final Object old = this.interpolationType;
		this.interpolationType = type;
		getViewer().getSynchronizer().interpolationTypeChanged(getViewer());
		paintManager.notifyChanged();
		repaint();
		getPropertyChangeSupport().firePropertyChange("interpolationType", old, type);
	}

	/**
	 * <p>Getter for the field interpolationType.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getInterpolationType() {
		return interpolationType;
	}

	/**
	 * <p>Setter for the field pixelatedZoom.</p>
	 *
	 * @param pixelatedZoom a boolean.
	 */
	public void setPixelatedZoom(final boolean pixelatedZoom) {
		if (pixelatedZoom == this.pixelatedZoom)
			return;
		this.pixelatedZoom = pixelatedZoom;
		getViewer().getSynchronizer().pixelatedZoomChanged(getViewer());
		paintManager.notifyChanged();
		repaint();
		getPropertyChangeSupport().firePropertyChange("pixelatedZoom", !pixelatedZoom, pixelatedZoom);
	}

	/**
	 * <p>isPixelatedZoom.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isPixelatedZoom() {
		return pixelatedZoom;
	}

	/**
	 * Returns the zoom factor used when resize strategy is CUSTOM_ZOOM.
	 *
	 * @return a double.
	 */
	public double getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * Sets the zoom factor to use when the resize strategy is CUSTOM_ZOOM.
	 * <p>
	 * Note that calling this function does not change the current resize strategy.
	 *
	 * @throws java.lang.IllegalArgumentException
	 *             if {@code newZoomFactor} is not a positive number
	 * @param newZoomFactor a double.
	 */
	public void setZoomFactor(final double newZoomFactor) {
		if (zoomFactor == newZoomFactor)
			return;
		if (newZoomFactor <= 0 || Double.isInfinite(newZoomFactor) || Double.isNaN(newZoomFactor))
			throw new IllegalArgumentException("Invalid zoom factor: " + newZoomFactor);
		if (getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM) {
			rescroller.prepare();
		}
		final double oldZoomFactor = zoomFactor;
		zoomFactor = newZoomFactor;
		final boolean canRescroll = getViewer().getSynchronizer().zoomFactorChangedCanIRescroll(getViewer());
		if (getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM) {
			//resize the panel shrink if needed
			resizePanel();
			resizeNow();
			// do not rescroll if we're following another viewer; the scrolling will be synchronized later
			if (canRescroll) {
				rescroller.rescroll();
				viewer.getSynchronizer().doneRescrolling(viewer);
			}
		} else {
			// no rescrolling is necessary, actually
			if (canRescroll)
				viewer.getSynchronizer().doneRescrolling(viewer);
		}
		getPropertyChangeSupport().firePropertyChange("zoomFactor", oldZoomFactor, newZoomFactor);
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		if (image == null) {
			return new Dimension();
		} else if (resizeStrategy == ResizeStrategy.CUSTOM_ZOOM) {
			return new Dimension((int) Math.ceil(image.getWidth() * zoomFactor),
					(int) Math.ceil(image.getHeight() * zoomFactor));
		} else
			return new Dimension(image.getWidth(), image.getHeight());
	}

	/**
	 * Returns the image pixel that is under the given point.
	 *
	 * @param p
	 *            a point in component coordinate system
	 * @return the corresponding image pixel, or null if the point is
	 *         outside the image
	 */
	public Point pointToPixel(final Point p) {
		return pointToPixel(p, true);
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
		final Point2D.Double fp = new Point2D.Double(p.x + .5, p.y + .5);
		try {
			getImageTransform().inverseTransform(fp, fp);
		} catch (final NoninvertibleTransformException ex) {
			throw new Error("Image transformation not invertible");
		}
		p.x = (int) Math.floor(fp.x);
		p.y = (int) Math.floor(fp.y);
		if (clipToImage && (p.x < 0 || p.y < 0 || p.x >= image.getWidth() || p.y >= image.getHeight())) {
			return null;
		}
		return p;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(final Graphics g) {
		paintManager.paintComponent(g);
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
		if (getImage() == null)
			throw new IllegalStateException("No image");
		if (!hasSize())
			throw new IllegalStateException("Viewer size is zero");
		final double currentZoom = switch (resizeStrategy) {
            case NO_RESIZE -> 1;
            case SHRINK_TO_FIT -> Math.min(getSizeRatio(), 1);
            case RESIZE_TO_FIT -> getSizeRatio();
            case CUSTOM_ZOOM -> zoomFactor;
            default -> throw new Error("Unhandled resize strategy");
        };
        final AffineTransform tr = new AffineTransform();
		tr.setToTranslation((getWidth() - image.getWidth() * currentZoom) / 2.0,
				(getHeight() - image.getHeight() * currentZoom) / 2.0);
		tr.scale(currentZoom, currentZoom);
		return tr;
	}

	private double getSizeRatio() {
		return Math.min(getWidth() / (double) image.getWidth(), getHeight() / (double) image.getHeight());
	}

	protected void resizePanel() {
		if (zoomFactor <= 1.0) {
			this.viewer.getComponent().setPreferredSize(getPreferredSize());
			this.viewer.getScrollPane().setPreferredSize(getPreferredSize());
			this.viewer.getScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.viewer.getScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		} else {
			//back to image original size.
			final Dimension original = new Dimension(image.getWidth(), image.getHeight());
			this.viewer.getComponent().setPreferredSize(original);
			this.viewer.getScrollPane().setPreferredSize(original);
			this.viewer.getScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			this.viewer.getScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		}
	}

	/**
	 * <p>Getter for the field propertyChangeSupport.</p>
	 *
	 * @return the propertyChangeSupport
	 */
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	/**
	 * <p>Getter for the field viewer.</p>
	 *
	 * @return the viewer
	 */
	public ImageViewer getViewer() {
		return viewer;
	}

	/**
	 * <p>Getter for the field moveListeners.</p>
	 *
	 * @return the moveListeners
	 */
	public List<ImageMouseMotionListener> getMoveListeners() {
		return moveListeners;
	}

	/**
	 * <p>Getter for the field clickListeners.</p>
	 *
	 * @return the clickListeners
	 */
	public List<ImageMouseClickListener> getClickListeners() {
		return clickListeners;
	}
}
