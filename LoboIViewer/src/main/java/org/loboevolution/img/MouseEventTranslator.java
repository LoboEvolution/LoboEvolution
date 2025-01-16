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

import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.MouseInputListener;

/**
 * Helper class that generates ImageMouseEvents by translating normal mouse
 * events onto the image. */
public class MouseEventTranslator implements MouseInputListener, PropertyChangeListener {
	/** This flag is true if the mouse cursor is inside the bounds of the image. */
	private boolean on = false;
	/**
	 * The last position reported. This is used to avoid multiple successive image
	 * mouse motion events with the same position.
	 */
	private Point lastPosition = null;
	
	private final ImageComponent ic;
	
	/**
	 * <p>Constructor for MouseEventTranslator.</p>
	 *
	 * @param ic a {@link org.loboevolution.img.ImageComponent} object.
	 */
	public MouseEventTranslator(final ImageComponent ic) {
		this.ic = ic;
	}

	/**
	 * Sets up this translator.
	 *
	 * @param ic a {@link org.loboevolution.img.ImageComponent} object.
	 */
	protected void register(final ImageComponent ic) {
		ic.addMouseListener(this);
		ic.addMouseMotionListener(this);
		ic.getPropertyChangeSupport().addPropertyChangeListener(this);
		ic.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(final ComponentEvent e) {
				correctionalFire();
			}

		});
	}

	private void handleMouseAt(Point position, final MouseEvent event) {
		if (ic.getImage() == null) {
			if (on) {
				on = false;
				fireMouseExit();
			}
		} else {
			if (position != null)
				position = ic.pointToPixel(position);
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

	/** {@inheritDoc} */
	@Override
	public void mouseClicked(final MouseEvent e) {
		if (ic.getImage() == null || !on)
			return;
		final Point p = ic.pointToPixel(e.getPoint());
		if (p != null) {
			fireMouseClickedAtPixel(p.x, p.y, e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseEntered(final MouseEvent e) {
		if (ic.getImage() != null) {
			final Point p = ic.pointToPixel(e.getPoint());
			if (p != null) {
				on = true;
				fireMouseEnter(p.x, p.y, e);
				fireMouseAtPixel(p.x, p.y, e);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(final MouseEvent e) {
		if (on) {
			on = false;
			fireMouseExit();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(final MouseEvent e) {
		handleMouseAt(e.getPoint(), e);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDragged(final MouseEvent e) {
		if (ic.getImage() == null)
			return;
		final Point p = ic.pointToPixel(e.getPoint(), false);
		fireMouseDrag(p.x, p.y, e);
	}

	/** {@inheritDoc} */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if ("image".equals(evt.getPropertyName()) || "resizeStrategy".equals(evt.getPropertyName())
				|| (ic.getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM
						&& "zoomFactor".equals(evt.getPropertyName()))) {
			correctionalFire();
		}
	}

	/**
	 * Fires a motion event based on the current cursor position. Use this method if
	 * something other than mouse motion changed where the cursor is relative to the
	 * image.
	 */
	protected void correctionalFire() {
		/*
		 * We use our parent, LayeredImageView, to locate the mouse. If the viewer has
		 * an overlay, then ImageComponent.getMousePosition will return null because the
		 * mouse is over the overlay and not the image component.
		 */
		handleMouseAt(ic.getParent().getMousePosition(true), null);
	}

	private void fireMouseAtPixel(final int x, final int y, final MouseEvent ev) {
		ImageMouseEvent e = null;
		for (final ImageMouseMotionListener imageMouseMoveListener : ic.getMoveListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), x, y, ev);
			imageMouseMoveListener.mouseMoved(e);
		}
	}

	private void fireMouseClickedAtPixel(final int x, final int y, final MouseEvent ev) {
		ImageMouseEvent e = null;
		for (final ImageMouseClickListener imageMouseClickListener : ic.getClickListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), x, y, ev);
			imageMouseClickListener.mouseClicked(e);
		}
	}

	private void fireMouseEnter(final int x, final int y, final MouseEvent ev) {
		ImageMouseEvent e = null;
		for (final ImageMouseMotionListener imageMouseMoveListener : ic.getMoveListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), x, y, ev);
			imageMouseMoveListener.mouseEntered(e);
		}
	}

	private void fireMouseExit() {
		ImageMouseEvent e = null;
		for (final ImageMouseMotionListener imageMouseMoveListener : ic.getMoveListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), -1, -1, null);
			imageMouseMoveListener.mouseExited(e);
		}
	}

	private void fireMouseDrag(final int x, final int y, final MouseEvent ev) {
		ImageMouseEvent e = null;
		for (final ImageMouseMotionListener imageMouseMoveListener : ic.getMoveListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), x, y, ev);
			imageMouseMoveListener.mouseDragged(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mousePressed(final MouseEvent e) {
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(final MouseEvent e) {
	}
}
