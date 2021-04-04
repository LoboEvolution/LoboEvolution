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

import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.MouseInputListener;

/**
 * Helper class that generates ImageMouseEvents by translating normal mouse
 * events onto the image.
 *
 *
 *
 */
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
	public MouseEventTranslator(ImageComponent ic) {
		this.ic = ic;
	}

	/**
	 * Sets up this translator.
	 *
	 * @param ic a {@link org.loboevolution.img.ImageComponent} object.
	 */
	protected void register(ImageComponent ic) {
		ic.addMouseListener(this);
		ic.addMouseMotionListener(this);
		ic.getPropertyChangeSupport().addPropertyChangeListener(this);
		ic.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				correctionalFire();
			}

		});
	}

	private void handleMouseAt(Point position, MouseEvent event) {
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
	public void mouseClicked(MouseEvent e) {
		if (ic.getImage() == null || !on)
			return;
		Point p = ic.pointToPixel(e.getPoint());
		if (p != null) {
			fireMouseClickedAtPixel(p.x, p.y, e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (ic.getImage() != null) {
			Point p = ic.pointToPixel(e.getPoint());
			if (p != null) {
				on = true;
				fireMouseEnter(p.x, p.y, e);
				fireMouseAtPixel(p.x, p.y, e);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(MouseEvent e) {
		if (on) {
			on = false;
			fireMouseExit();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(MouseEvent e) {
		handleMouseAt(e.getPoint(), e);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (ic.getImage() == null)
			return;
		Point p = ic.pointToPixel(e.getPoint(), false);
		fireMouseDrag(p.x, p.y, e);
	}

	/** {@inheritDoc} */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
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
		/**
		 * We use our parent, LayeredImageView, to locate the mouse. If the viewer has
		 * an overlay, then ImageComponent.getMousePosition will return null because the
		 * mouse is over the overlay and not the image component.
		 */
		handleMouseAt(ic.getParent().getMousePosition(true), null);
	}

	private void fireMouseAtPixel(int x, int y, MouseEvent ev) {
		ImageMouseEvent e = null;
		for (ImageMouseMotionListener imageMouseMoveListener : ic.getMoveListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), x, y, ev);
			imageMouseMoveListener.mouseMoved(e);
		}
	}

	private void fireMouseClickedAtPixel(int x, int y, MouseEvent ev) {
		ImageMouseEvent e = null;
		for (ImageMouseClickListener imageMouseClickListener : ic.getClickListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), x, y, ev);
			imageMouseClickListener.mouseClicked(e);
		}
	}

	private void fireMouseEnter(int x, int y, MouseEvent ev) {
		ImageMouseEvent e = null;
		for (ImageMouseMotionListener imageMouseMoveListener : ic.getMoveListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), x, y, ev);
			imageMouseMoveListener.mouseEntered(e);
		}
	}

	private void fireMouseExit() {
		ImageMouseEvent e = null;
		for (ImageMouseMotionListener imageMouseMoveListener : ic.getMoveListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), -1, -1, null);
			imageMouseMoveListener.mouseExited(e);
		}
	}

	private void fireMouseDrag(int x, int y, MouseEvent ev) {
		ImageMouseEvent e = null;
		for (ImageMouseMotionListener imageMouseMoveListener : ic.getMoveListeners()) {
			if (e == null)
				e = new ImageMouseEvent(ic.getViewer(), ic.getImage(), x, y, ev);
			imageMouseMoveListener.mouseDragged(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
