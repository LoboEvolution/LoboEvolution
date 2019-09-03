package org.lobo.tab;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

public class DnDTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	private final DragGestureListener dragGestureListener = new DragGestureListenerImpl(this);

	protected int dragTabIdx = -1;

	private final DropTargetListener dropTargetListener = new DropTargetListenerImpl(this);

	private GlassPane glass;

	private int index;

	public DnDTabbedPane() {
		init();
	}

	protected int getDropIndex(Point p) {
		int idx = indexAtLocation(p.x, p.y);
		if (idx == -1 && getTabCount() > 0) {
			final Rectangle tabBounds = getBoundsAt(getTabCount() - 1);
			if (tabBounds.x + tabBounds.width <= p.x && tabBounds.y <= p.y + 1
					&& p.y <= tabBounds.y + tabBounds.height) {
				idx = getTabCount() - 1;
			}
		}
		return idx;
	}

	/**
	 * @return the glass
	 */
	protected GlassPane getGlass() {
		return this.glass == null ? new GlassPane(this) : this.glass;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	private void init() {
		setUI(new TabbedPaneUI(this));
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this.dropTargetListener, true);
		new DragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE,
				this.dragGestureListener);
		final ChangeListener changeListener = changeEvent -> {
			final JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
			setIndex(sourceTabbedPane.getSelectedIndex());
		};
		addChangeListener(changeListener);

	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}