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
package org.lobo.img;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLayeredPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;

public class ScrollableLayeredPane extends JLayeredPane implements Scrollable {
	
	private static final long serialVersionUID = 1L;
	private transient LayeredImageView layeredImageView;
	

	public ScrollableLayeredPane(LayeredImageView layeredImageView) {
		this.layeredImageView = layeredImageView;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 10;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 50;
	}

	/*
	 * The getScrollableTracksViewportXxx functions below are used by
	 * javax.swing.ScrollPaneLayout to determine whether the scroll bars should be
	 * visible; so these need to be implemented.
	 */
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.SHRINK_TO_FIT
				|| layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.RESIZE_TO_FIT;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.SHRINK_TO_FIT
				|| layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.RESIZE_TO_FIT;
	}

	/*
	 * The getPreferredScrollableViewportSize does not seem to be used.
	 */
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		if (layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.NO_RESIZE)
			return getPreferredSize();
		else
			return ((JViewport) javax.swing.SwingUtilities.getAncestorOfClass(JViewport.class, this)).getSize();
	}
}