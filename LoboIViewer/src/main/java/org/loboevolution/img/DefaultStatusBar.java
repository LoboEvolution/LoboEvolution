/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
package org.loboevolution.img;

/**
 * A default status bar implementation that displays the current mouse position
 * (in pixel coordinates) and the colour of the pixel under the cursor.
 *
 * @author Kazo Csaba
 * @version $Id: $Id
 */
public class DefaultStatusBar extends PixelInfoStatusBar implements ImageMouseMotionListener {

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(ImageMouseEvent e) {
		setPixel(e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(ImageMouseEvent e) {
		setPixel(-1, -1);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseEntered(ImageMouseEvent e) {
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDragged(ImageMouseEvent e) {
	}

	/** {@inheritDoc} */
	@Override
	protected void register(ImageViewer viewer) {
		super.register(viewer);
		viewer.addImageMouseMotionListener(this);
	}

	/** {@inheritDoc} */
	@Override
	protected void unregister(ImageViewer viewer) {
		super.unregister(viewer);
		viewer.removeImageMouseMotionListener(this);
	}

}
