/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.tab;

import org.loboevolution.common.Strings;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.ITabbedPane;
import org.loboevolution.store.TabStore;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.dnd.*;

/**
 * <p>DnDTabbedPane class.</p>
 */
public class DnDTabbedPane extends JTabbedPane implements ITabbedPane {

	private static final long serialVersionUID = 1L;

	private final DragGestureListener dragGestureListener = new DragGestureListenerImpl(this);

	protected int dragTabIdx = -1;

	private final DropTargetListener dropTargetListener = new DropTargetListenerImpl(this);

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
		return new GlassPane(this);
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

	@Override
	public void setComponentPopupMenu(final IBrowserPanel panel) {
		super.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
	}

	@Override
	public void insertTab(
			final String title,
			final Icon icon,
			final Component component,
			final String tip,
			final int index) {
		super.insertTab(title, icon, component, tip, index);
	}
}
