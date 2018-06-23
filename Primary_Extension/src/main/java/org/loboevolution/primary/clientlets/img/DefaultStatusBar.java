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
package org.loboevolution.primary.clientlets.img;

/**
 * A default status bar implementation that displays the current mouse position
 * (in pixel coordinates) and the colour of the pixel under the cursor.
 * 
 * @author Kazo Csaba
 */
public class DefaultStatusBar extends PixelInfoStatusBar implements ImageMouseMotionListener {

	@Override
	public void mouseMoved(ImageMouseEvent e) {
		setPixel(e.getX(), e.getY());
	}

	@Override
	public void mouseExited(ImageMouseEvent e) {
		setPixel(-1, -1);
	}

	@Override
	public void mouseEntered(ImageMouseEvent e) {
	}

	@Override
	public void mouseDragged(ImageMouseEvent e) {
	}

	@Override
	protected void register(ImageViewer viewer) {
		super.register(viewer);
		viewer.addImageMouseMotionListener(this);
	}

	@Override
	protected void unregister(ImageViewer viewer) {
		super.unregister(viewer);
		viewer.removeImageMouseMotionListener(this);
	}

}
