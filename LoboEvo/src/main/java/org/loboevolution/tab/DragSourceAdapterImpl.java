/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * <p>DragSourceAdapterImpl class.</p>
 *
 *
 *
 */
public class DragSourceAdapterImpl extends DragSourceAdapter {

	private final DnDTabbedPane tab;

	/**
	 * <p>Constructor for DragSourceAdapterImpl.</p>
	 *
	 * @param tab a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	public DragSourceAdapterImpl(DnDTabbedPane tab) {
		this.tab = tab;
	}

	/** {@inheritDoc} */
	@Override
	public void dragDropEnd(DragSourceDropEvent e) {
		this.tab.getGlass().setVisible(false);
	}

	/** {@inheritDoc} */
	@Override
	public void dragEnter(DragSourceDragEvent e) {
		e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	}

	/** {@inheritDoc} */
	@Override
	public void dragExit(DragSourceEvent e) {
		final Point location = e.getLocation();
		try {
			if (this.tab.dragTabIdx > -1) {
				final Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);

				final OuterFrame frame = new OuterFrame(this.tab);

				frame.tab = this.tab.getTabComponentAt(this.tab.dragTabIdx);
				frame.comp = this.tab.getComponentAt(this.tab.dragTabIdx);
				frame.tabTitle = this.tab.getTitleAt(this.tab.dragTabIdx);
				frame.icon = this.tab.getIconAt(this.tab.dragTabIdx);
				frame.tip = this.tab.getToolTipTextAt(this.tab.dragTabIdx);
				this.tab.removeTabAt(this.tab.dragTabIdx);
				frame.setTitle(frame.tabTitle);
				frame.getContentPane().add(frame.comp);
				frame.setSize(frame.comp.getSize());
				frame.validate();
				frame.setLocation(location.x - frame.getSize().width / 2, location.y - 5);
				frame.setVisible(true);

				// keep dragging the frame while mouse button is pressed
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.mouseMove(location.x, location.y);
				robot.mousePress(InputEvent.BUTTON1_MASK);
			}
		} catch (final AWTException e1) {
			e1.printStackTrace();
		}
	}

}
