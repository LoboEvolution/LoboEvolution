package org.loboevolution.primary.clientlets.img;

import java.applet.Applet;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

/**
 * The component that displays the image itself.
 * 
 * @author Kazó Csaba
 */
class ImageComponent extends JComponent {
	private ResizeStrategy resizeStrategy = ResizeStrategy.SHRINK_TO_FIT;
	private BufferedImage image;
	private boolean pixelatedZoom = false;
	private Object interpolationType = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
	private double zoomFactor = 1;
	private final List<ImageMouseMotionListener> moveListeners = new ArrayList<ImageMouseMotionListener>(4);
	private final List<ImageMouseClickListener> clickListeners = new ArrayList<ImageMouseClickListener>(4);
	private final MouseEventTranslator mouseEventTranslator = new MouseEventTranslator();
	private final PaintManager paintManager = new PaintManager();

	/*
	 * Handles repositioning the scroll pane when the image is resized so that the
	 * same area remains visible.
	 */
	class Rescroller {
		private Point2D preparedCenter = null;

		void prepare() {
			if (image != null && hasSize()) {
				Rectangle viewRect = viewer.getScrollPane().getViewport().getViewRect();
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
				Dimension viewSize = viewer.getScrollPane().getViewport().getExtentSize();
				getImageTransform().transform(preparedCenter, preparedCenter);
				Rectangle view = new Rectangle((int) Math.round(preparedCenter.getX() - viewSize.width / 2.0),
						(int) Math.round(preparedCenter.getY() - viewSize.height / 2.0), viewSize.width,
						viewSize.height);
				preparedCenter = null;
				scrollRectToVisible(view);
			}
		}

	}

	private Rescroller rescroller = new Rescroller();

	private final PropertyChangeSupport propertyChangeSupport;
	private final ImageViewer viewer;

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
			moveListeners.add(l);
	}

	public void removeImageMouseMoveListener(ImageMouseMotionListener l) {
		if (l != null)
			moveListeners.remove(l);
	}

	public void addImageMouseClickListener(ImageMouseClickListener l) {
		if (l != null)
			clickListeners.add(l);
	}

	public void removeImageMouseClickListener(ImageMouseClickListener l) {
		if (l != null)
			clickListeners.remove(l);
	}

	public void setImage(BufferedImage newImage) {
		BufferedImage oldImage = image;
		image = newImage;
		paintManager.notifyChanged();
		if (oldImage != newImage && (oldImage == null || newImage == null || oldImage.getWidth() != newImage.getWidth()
				|| oldImage.getHeight() != newImage.getHeight()))
			revalidate();
		repaint();
		propertyChangeSupport.firePropertyChange("image", oldImage, newImage);
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
		boolean canRescroll = viewer.getSynchronizer().resizeStrategyChangedCanIRescroll(viewer);
		resizeNow();

		if (canRescroll) {
			rescroller.rescroll();
			viewer.getSynchronizer().doneRescrolling(viewer);
		}
		propertyChangeSupport.firePropertyChange("resizeStrategy", oldResizeStrategy, resizeStrategy);
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
		viewer.getSynchronizer().interpolationTypeChanged(viewer);
		paintManager.notifyChanged();
		repaint();
		propertyChangeSupport.firePropertyChange("interpolationType", old, type);
	}

	public Object getInterpolationType() {
		return interpolationType;
	}

	public void setPixelatedZoom(boolean pixelatedZoom) {
		if (pixelatedZoom == this.pixelatedZoom)
			return;
		this.pixelatedZoom = pixelatedZoom;
		viewer.getSynchronizer().pixelatedZoomChanged(viewer);
		paintManager.notifyChanged();
		repaint();
		propertyChangeSupport.firePropertyChange("pixelatedZoom", !pixelatedZoom, pixelatedZoom);
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
		boolean canRescroll = viewer.getSynchronizer().zoomFactorChangedCanIRescroll(viewer);
		if (getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM) {
			resizeNow();
			// do not rescroll if we're following another viewer; the scrolling will be
			// synchronized later
			if (canRescroll) {
				rescroller.rescroll();
				viewer.getSynchronizer().doneRescrolling(viewer);
			}
		} else {
			// no rescrolling is necessary, actually
			if (canRescroll)
				viewer.getSynchronizer().doneRescrolling(viewer);
		}
		propertyChangeSupport.firePropertyChange("zoomFactor", oldZoomFactor, newZoomFactor);
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
	 * Helper class that generates ImageMouseEvents by translating normal mouse
	 * events onto the image.
	 */
	private class MouseEventTranslator implements MouseInputListener, PropertyChangeListener {
		/** This flag is true if the mouse cursor is inside the bounds of the image. */
		private boolean on = false;
		/**
		 * The last position reported. This is used to avoid multiple successive image
		 * mouse motion events with the same position.
		 */
		private Point lastPosition = null;

		/** Sets up this translator. */
		private void register(ImageComponent ic) {
			ic.addMouseListener(this);
			ic.addMouseMotionListener(this);
			ic.propertyChangeSupport.addPropertyChangeListener(this);
			ic.addComponentListener(new ComponentAdapter() {

				@Override
				public void componentResized(ComponentEvent e) {
					correctionalFire();
				}

			});
		}

		private void handleMouseAt(Point position, MouseEvent event) {
			if (image == null) {
				if (on) {
					on = false;
					fireMouseExit();
				}
			} else {
				if (position != null)
					position = pointToPixel(position);
				if (position == null) {
					if (on) {
						on = false;
						fireMouseExit();
					}
				} else {
					if (!on) {
						on = true;
						lastPosition = null;
						fireMouseEnter(position.x, position.y, event);
					}
					if (!position.equals(lastPosition)) {
						lastPosition = position;
						fireMouseAtPixel(position.x, position.y, event);
					}
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (image == null || !on)
				return;
			Point p = pointToPixel(e.getPoint());
			if (p != null) {
				fireMouseClickedAtPixel(p.x, p.y, e);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (image != null) {
				Point p = pointToPixel(e.getPoint());
				if (p != null) {
					on = true;
					fireMouseEnter(p.x, p.y, e);
					fireMouseAtPixel(p.x, p.y, e);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (on) {
				on = false;
				fireMouseExit();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			handleMouseAt(e.getPoint(), e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (image == null)
				return;
			Point p = pointToPixel(e.getPoint(), false);
			fireMouseDrag(p.x, p.y, e);
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if ("image".equals(evt.getPropertyName()) || "resizeStrategy".equals(evt.getPropertyName())
					|| (getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM
							&& "zoomFactor".equals(evt.getPropertyName()))) {
				correctionalFire();
			}
		}

		/**
		 * Fires a motion event based on the current cursor position. Use this method if
		 * something other than mouse motion changed where the cursor is relative to the
		 * image.
		 */
		private void correctionalFire() {
			/**
			 * We use our parent, LayeredImageView, to locate the mouse. If the viewer has
			 * an overlay, then ImageComponent.getMousePosition will return null because the
			 * mouse is over the overlay and not the image component.
			 */
			handleMouseAt(getParent().getMousePosition(true), null);
		}

		private void fireMouseAtPixel(int x, int y, MouseEvent ev) {
			ImageMouseEvent e = null;
			for (ImageMouseMotionListener imageMouseMoveListener : moveListeners) {
				if (e == null)
					e = new ImageMouseEvent(viewer, image, x, y, ev);
				imageMouseMoveListener.mouseMoved(e);
			}
		}

		private void fireMouseClickedAtPixel(int x, int y, MouseEvent ev) {
			ImageMouseEvent e = null;
			for (ImageMouseClickListener imageMouseClickListener : clickListeners) {
				if (e == null)
					e = new ImageMouseEvent(viewer, image, x, y, ev);
				imageMouseClickListener.mouseClicked(e);
			}
		}

		private void fireMouseEnter(int x, int y, MouseEvent ev) {
			ImageMouseEvent e = null;
			for (ImageMouseMotionListener imageMouseMoveListener : moveListeners) {
				if (e == null)
					e = new ImageMouseEvent(viewer, image, x, y, ev);
				imageMouseMoveListener.mouseEntered(e);
			}
		}

		private void fireMouseExit() {
			ImageMouseEvent e = null;
			for (ImageMouseMotionListener imageMouseMoveListener : moveListeners) {
				if (e == null)
					e = new ImageMouseEvent(viewer, image, -1, -1, null);
				imageMouseMoveListener.mouseExited(e);
			}
		}

		private void fireMouseDrag(int x, int y, MouseEvent ev) {
			ImageMouseEvent e = null;
			for (ImageMouseMotionListener imageMouseMoveListener : moveListeners) {
				if (e == null)
					e = new ImageMouseEvent(viewer, image, x, y, ev);
				imageMouseMoveListener.mouseDragged(e);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	/**
	 * Helper class that manages the actual painting.
	 */
	private class PaintManager {
		BufferedImage cachedImage = null;
		boolean cachedImageChanged = false;
		AffineTransform cachedTransform;

		private void doPaint(Graphics2D gg, AffineTransform imageTransform) {
			gg.setColor(getBackground());
			gg.fillRect(0, 0, getWidth(), getHeight());

			gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			if (pixelatedZoom && imageTransform.getScaleX() >= 1)
				gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			else
				gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolationType);

			gg.drawImage(image, imageTransform, ImageComponent.this);
		}

		private void ensureCachedValid(AffineTransform imageTransform) {
			boolean cacheValid;

			// create the image if necessary; if the existing one is sufficiently large, use
			// it
			if (cachedImage == null || cachedImage.getWidth() < getWidth() || cachedImage.getHeight() < getHeight()) {
				cachedImage = getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
				cacheValid = false;
			} else {
				cacheValid = cachedTransform.equals(imageTransform) && !cachedImageChanged;
			}

			if (!cacheValid) {
				Graphics2D gg = cachedImage.createGraphics();
				doPaint(gg, imageTransform);
				gg.dispose();
				cachedImageChanged = false;
				cachedTransform = new AffineTransform(imageTransform);
			}
		}

		/**
		 * Called when a property which affects how the component is painted changes.
		 * This invalidates the cache and causes it to be redrawn upon the next paint
		 * request.
		 */
		public void notifyChanged() {
			cachedImageChanged = true;
		}

		public void paintComponent(Graphics g) {
			if (image == null) {
				Graphics2D gg = (Graphics2D) g.create();
				gg.setColor(getBackground());
				gg.fillRect(0, 0, getWidth(), getHeight());
				gg.dispose();
				return;
			}

			AffineTransform imageTransform = getImageTransform();

			if (imageTransform.getScaleX() < 1
					&& interpolationType != RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR) {
				/*
				 * We're shrinking the image; instead of letting the Graphics object do it every
				 * time, we do it and cache the result.
				 */
				ensureCachedValid(imageTransform);
				g.drawImage(cachedImage, 0, 0, ImageComponent.this);
			} else {
				// draw the image directly
				Graphics2D gg = (Graphics2D) g.create();
				doPaint(gg, imageTransform);
				gg.dispose();
			}

		}
	}
}
