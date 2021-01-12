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

package org.loboevolution.tab;

import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

/**
 * <p>DragGestureListenerImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DragGestureListenerImpl implements DragGestureListener {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(DragGestureListenerImpl.class.getName());

	private DragSourceAdapterImpl dragSourceListener = null;

	private final Image emptyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

	private final Point emptyPoint = new Point();

	private final DnDTabbedPane tab;

	private final Transferable transferable = new TransferableImpl();

	/**
	 * <p>Constructor for DragGestureListenerImpl.</p>
	 *
	 * @param tab a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	public DragGestureListenerImpl(DnDTabbedPane tab) {
		this.tab = tab;
		this.dragSourceListener = new DragSourceAdapterImpl(tab);
	}

	/** {@inheritDoc} */
	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		if (this.tab.getTabCount() < 2 || !SwingUtilities.isLeftMouseButton((MouseEvent) dge.getTriggerEvent())) {
			return;
		}

		final Point p = dge.getDragOrigin();
		this.tab.dragTabIdx = this.tab.indexAtLocation(p.x, p.y);

		if (this.tab.dragTabIdx < 0 || !this.tab.isEnabledAt(this.tab.dragTabIdx)) {
			this.tab.dragTabIdx = -1;
			return;
		}
		this.tab.setSelectedIndex(this.tab.dragTabIdx);

		this.tab.getRootPane().setGlassPane(this.tab.getGlass());

		this.tab.getGlass().createImage(dge.getComponent());

		try {
			dge.startDrag(DragSource.DefaultMoveDrop, this.emptyImage, this.emptyPoint, this.transferable,
					this.dragSourceListener);
		} catch (final InvalidDnDOperationException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
