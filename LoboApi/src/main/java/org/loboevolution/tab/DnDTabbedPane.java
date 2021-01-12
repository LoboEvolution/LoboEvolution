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

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import org.loboevolution.common.Strings;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.store.TabStore;

/**
 * <p>DnDTabbedPane class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DnDTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	private final DragGestureListener dragGestureListener = new DragGestureListenerImpl(this);

	protected int dragTabIdx = -1;

	private final DropTargetListener dropTargetListener = new DropTargetListenerImpl(this);

	private GlassPane glass;
	
	private IBrowserPanel browserPanel;

	private int index;
	
	/**
	 * <p>Constructor for DnDTabbedPane.</p>
	 *
	 * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public DnDTabbedPane(IBrowserPanel browserPanel) {
		init(browserPanel);
	}

	/**
	 * <p>getDropIndex.</p>
	 *
	 * @param p a {@link java.awt.Point} object.
	 * @return a int.
	 */
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
	 * <p>Getter for the field glass.</p>
	 *
	 * @return the glass
	 */
	protected GlassPane getGlass() {
		return this.glass == null ? new GlassPane(this) : this.glass;
	}

	/**
	 * <p>Getter for the field index.</p>
	 *
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	private void init(IBrowserPanel browserPanel) {
		setUI(new TabbedPaneUI(this));
		this.browserPanel = browserPanel;
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this.dropTargetListener, true);
		new DragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this.dragGestureListener);
		final ChangeListener changeListener = changeEvent -> {
			final JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
			IBrowserFrame browserFrame = browserPanel.getBrowserFrame();
			String uri = TabStore.getTab(sourceTabbedPane.getSelectedIndex());
			if (browserFrame.getToolbar() != null && Strings.isNotBlank(uri)) {
				browserFrame.getToolbar().getAddressBar().setText(uri);
			}
			setIndex(sourceTabbedPane.getSelectedIndex());
		};
		addChangeListener(changeListener);
	}

	/**
	 * <p>Setter for the field index.</p>
	 *
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * <p>Getter for the field browserPanel.</p>
	 *
	 * @return the browserPanel
	 */
	public IBrowserPanel getBrowserPanel() {
		return browserPanel;
	}
}
