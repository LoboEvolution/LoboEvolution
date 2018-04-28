/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.loboevolution.primary.clientlets.img;

import java.applet.Applet;
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The component that displays the image itself.
 * 
 * @author Kazó Csaba
 */
class ImageComponent extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private transient ResizeStrategy resizeStrategy = ResizeStrategy.SHRINK_TO_FIT;
	private transient BufferedImage image;
	private boolean pixelatedZoom = false;
	private transient Object interpolationType = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
	private double zoomFactor = 1;
	private final transient List<ImageMouseMotionListener> moveListeners = new ArrayList<ImageMouseMotionListener>(4);
	private final transient List<ImageMouseClickListener> clickListeners = new ArrayList<ImageMouseClickListener>(4);
	private final transient MouseEventTranslator mouseEventTranslator = new MouseEventTranslator(this);
	private final transient PaintManager paintManager = new PaintManager(this);
	private Rescroller rescroller = new Rescroller();
	private final PropertyChangeSupport propertyChangeSupport;
	private final transient ImageViewer viewer;

	/*
	 * Handles repositioning the scroll pane when the image is resized so that the
	 * same area remains visible.
	 */
	class Rescroller {
		private Point2D preparedCenter = null;

		void prepare() {
			if (image != null && hasSize()) {
				Rectangle viewRect = getViewer().getScrollPane().getViewport().getViewRect();
				preparedCenter = new Point2D.Double(viewRect.getCenterX(), viewRect.getCenterY());
				try {
					getImageTransform().inverseTransform(preparedCenter, preparedCenter);
				} catch (NoninvertibleTransformException e) {
					throw new Error(e);
				}
			}
		}

		void rescroll() {
			if (preparedCenter != null) {
				Dimension viewSize = getViewer().getScrollPane().getViewport().getExtentSize();
				getImageTransform().transform(preparedCenter, preparedCenter);
				Rectangle view = new Rectangle((int) Math.round(preparedCenter.getX() - viewSize.width / 2.0),
						(int) Math.round(preparedCenter.getY() - viewSize.height / 2.0), viewSize.width,
						viewSize.height);
				preparedCenter = null;
				scrollRectToVisible(view);
			}
		}

	}
	
	public ImageComponent(ImageViewer viewer, PropertyChangeSupport propertyChangeSupport) {
		this.viewer = viewer;
		this.propertyChangeSupport = propertyChangeSupport;
		mouseEventTranslator.register(this);
		setOpaque(true);
		viewer.getScrollPane().getViewport().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				/*
				 * Here the viewer might not have a size yet, because we might be before the
				 * first layout. But that's alright. As soon as we get our size, the viewport
				 * will send another state change.
				 */
				if (hasSize())
					mouseEventTranslator.correctionalFire();
			}
		});
	}

	private boolean hasSize() {
		return getWidth() > 0 && getHeight() > 0;
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public void addImageMouseMoveListener(ImageMouseMotionListener l) {
		if (l != null)
			getMoveListeners().add(l);
	}

	public void removeImageMouseMoveListener(ImageMouseMotionListener l) {
		if (l != null)
			getMoveListeners().remove(l);
	}

	public void addImageMouseClickListener(ImageMouseClickListener l) {
		if (l != null)
			getClickListeners().add(l);
	}

	public void removeImageMouseClickListener(ImageMouseClickListener l) {
		if (l != null)
			getClickListeners().remove(l);
	}

	public void setImage(BufferedImage newImage) {
		BufferedImage oldImage = image;
		image = newImage;
		paintManager.notifyChanged();
		if (oldImage != newImage && (oldImage == null || newImage == null || oldImage.getWidth() != newImage.getWidth()
				|| oldImage.getHeight() != newImage.getHeight()))
			revalidate();
		repaint();
		getPropertyChangeSupport().firePropertyChange("image", oldImage, newImage);
	}

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
			if (c instanceof Window || c instanceof Applet) {
				break;
			}
		}

		if (c == null)
			return;

		root.validate();
		repaint();
	}

	public void setResizeStrategy(ResizeStrategy resizeStrategy) {
		if (resizeStrategy == this.resizeStrategy)
			return;
		rescroller.prepare();
		ResizeStrategy oldResizeStrategy = this.resizeStrategy;
		this.resizeStrategy = resizeStrategy;
		boolean canRescroll = getViewer().getSynchronizer().resizeStrategyChangedCanIRescroll(getViewer());
		resizeNow();

		if (canRescroll) {
			rescroller.rescroll();
			getViewer().getSynchronizer().doneRescrolling(getViewer());
		}
		getPropertyChangeSupport().firePropertyChange("resizeStrategy", oldResizeStrategy, resizeStrategy);
	}

	public ResizeStrategy getResizeStrategy() {
		return resizeStrategy;
	}

	public void setInterpolationType(Object type) {
		if (interpolationType == type)
			return;
		if (type != RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
				&& type != RenderingHints.VALUE_INTERPOLATION_BILINEAR
				&& type != RenderingHints.VALUE_INTERPOLATION_BICUBIC)
			throw new IllegalArgumentException("Invalid interpolation type; use one of the RenderingHints constants");
		Object old = this.interpolationType;
		this.interpolationType = type;
		getViewer().getSynchronizer().interpolationTypeChanged(getViewer());
		paintManager.notifyChanged();
		repaint();
		getPropertyChangeSupport().firePropertyChange("interpolationType", old, type);
	}

	public Object getInterpolationType() {
		return interpolationType;
	}

	public void setPixelatedZoom(boolean pixelatedZoom) {
		if (pixelatedZoom == this.pixelatedZoom)
			return;
		this.pixelatedZoom = pixelatedZoom;
		getViewer().getSynchronizer().pixelatedZoomChanged(getViewer());
		paintManager.notifyChanged();
		repaint();
		getPropertyChangeSupport().firePropertyChange("pixelatedZoom", !pixelatedZoom, pixelatedZoom);
	}

	public boolean isPixelatedZoom() {
		return pixelatedZoom;
	}

	/** Returns the zoom factor used when resize strategy is CUSTOM_ZOOM. */
	public double getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * Sets the zoom factor to use when the resize strategy is CUSTOM_ZOOM.
	 * <p>
	 * Note that calling this function does not change the current resize strategy.
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code newZoomFactor} is not a positive number
	 */
	public void setZoomFactor(double newZoomFactor) {
		if (zoomFactor == newZoomFactor)
			return;
		if (newZoomFactor <= 0 || Double.isInfinite(newZoomFactor) || Double.isNaN(newZoomFactor))
			throw new IllegalArgumentException("Invalid zoom factor: " + newZoomFactor);
		if (getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM) {
			rescroller.prepare();
		}
		double oldZoomFactor = zoomFactor;
		zoomFactor = newZoomFactor;
		boolean canRescroll = getViewer().getSynchronizer().zoomFactorChangedCanIRescroll(getViewer());
		if (getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM) {
			resizeNow();
			// do not rescroll if we're following another viewer; the scrolling will be
			// synchronized later
			if (canRescroll) {
				rescroller.rescroll();
				getViewer().getSynchronizer().doneRescrolling(getViewer());
			}
		} else {
			// no rescrolling is necessary, actually
			if (canRescroll)
				getViewer().getSynchronizer().doneRescrolling(getViewer());
		}
		getPropertyChangeSupport().firePropertyChange("zoomFactor", oldZoomFactor, newZoomFactor);
	}

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
	 * @return the corresponding image pixel, or <code>null</code> if the point is
	 *         outside the image
	 */
	public Point pointToPixel(Point p) {
		return pointToPixel(p, true);
	}

	/**
	 * Returns the image pixel corresponding to the given point. If the
	 * <code>clipToImage</code> parameter is <code>false</code>, then the function
	 * will return an appropriately positioned pixel on an infinite plane, even if
	 * the point is outside the image bounds. If <code>clipToImage</code> is
	 * <code>true</code> then the function will return <code>null</code> for such
	 * positions, and any non-null return value will be a valid image pixel.
	 * 
	 * @param p
	 *            a point in component coordinate system
	 * @param clipToImage
	 *            whether the function should return <code>null</code> for positions
	 *            outside the image bounds
	 * @return the corresponding image pixel
	 * @throws IllegalStateException
	 *             if there is no image set or if the size of the viewer is 0 (for
	 *             example because it is not in a visible component)
	 */
	public Point pointToPixel(Point p, boolean clipToImage) {
		Point2D.Double fp = new Point2D.Double(p.x + .5, p.y + .5);
		try {
			getImageTransform().inverseTransform(fp, fp);
		} catch (NoninvertibleTransformException ex) {
			throw new Error("Image transformation not invertible");
		}
		p.x = (int) Math.floor(fp.x);
		p.y = (int) Math.floor(fp.y);
		if (clipToImage && (p.x < 0 || p.y < 0 || p.x >= image.getWidth() || p.y >= image.getHeight())) {
			return null;
		}
		return p;
	}

	@Override
	protected void paintComponent(Graphics g) {
		paintManager.paintComponent(g);
	}

	/**
	 * Returns the transformation that is applied to the image. Most commonly the
	 * transformation is the concatenation of a uniform scale and a translation.
	 * <p>
	 * The <code>AffineTransform</code> instance returned by this method should not
	 * be modified.
	 * 
	 * @return the transformation applied to the image before painting
	 * @throws IllegalStateException
	 *             if there is no image set or if the size of the viewer is 0 (for
	 *             example because it is not in a visible component)
	 */
	public AffineTransform getImageTransform() {
		if (getImage() == null)
			throw new IllegalStateException("No image");
		if (!hasSize())
			throw new IllegalStateException("Viewer size is zero");
		double currentZoom;
		switch (resizeStrategy) {
		case NO_RESIZE:
			currentZoom = 1;
			break;
		case SHRINK_TO_FIT:
			currentZoom = Math.min(getSizeRatio(), 1);
			break;
		case RESIZE_TO_FIT:
			currentZoom = getSizeRatio();
			break;
		case CUSTOM_ZOOM:
			currentZoom = zoomFactor;
			break;
		default:
			throw new Error("Unhandled resize strategy");
		}
		AffineTransform tr = new AffineTransform();
		tr.setToTranslation((getWidth() - image.getWidth() * currentZoom) / 2.0,
				(getHeight() - image.getHeight() * currentZoom) / 2.0);
		tr.scale(currentZoom, currentZoom);
		return tr;
	}

	private double getSizeRatio() {
		return Math.min(getWidth() / (double) image.getWidth(), getHeight() / (double) image.getHeight());
	}

	/**
	 * @return the propertyChangeSupport
	 */
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	/**
	 * @return the viewer
	 */
	public ImageViewer getViewer() {
		return viewer;
	}

	/**
	 * @return the moveListeners
	 */
	public List<ImageMouseMotionListener> getMoveListeners() {
		return moveListeners;
	}

	/**
	 * @return the clickListeners
	 */
	public List<ImageMouseClickListener> getClickListeners() {
		return clickListeners;
	}
}
