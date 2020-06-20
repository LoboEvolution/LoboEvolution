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
