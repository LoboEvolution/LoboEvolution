/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.common;

import java.awt.Frame;
import java.awt.Graphics;

/**
 * <p>GUITasks class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class GUITasks {
	/**
	 * <p>drawDashed.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a int.
	 * @param y1 a int.
	 * @param x2 a int.
	 * @param y2 a int.
	 * @param dashSize a int.
	 * @param gapSize a int.
	 */
	public static void drawDashed(Graphics g, int x1, int y1, int x2, int y2, int dashSize, int gapSize) {
		if (x2 < x1) {
			final int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y2 < y1) {
			final int temp = y1;
			y1 = y2;
			y2 = temp;
		}
		final int totalDash = dashSize + gapSize;
		if (y1 == y2) {
			final int virtualStartX = x1 / totalDash * totalDash;
			for (int x = virtualStartX; x < x2; x += totalDash) {
				int topX = x + dashSize;
				if (topX > x2) {
					topX = x2;
				}
				int firstX = x;
				if (firstX < x1) {
					firstX = x1;
				}
				if (firstX < topX) {
					g.drawLine(firstX, y1, topX, y1);
				}
			}
		} else if (x1 == x2) {
			final int virtualStartY = y1 / totalDash * totalDash;
			for (int y = virtualStartY; y < y2; y += totalDash) {
				int topY = y + dashSize;
				if (topY > y2) {
					topY = y2;
				}
				int firstY = y;
				if (firstY < y1) {
					firstY = y1;
				}
				if (firstY < topY) {
					g.drawLine(x1, firstY, x1, topY);
				}
			}
		} else {
			// Not supported
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * <p>getTopFrame.</p>
	 *
	 * @return a {@link java.awt.Frame} object.
	 */
	public static Frame getTopFrame() {
		final Frame[] frames = Frame.getFrames();
		for (final Frame frame : frames) {
			if (frame.getFocusOwner() != null) {
				return frame;
			}
		}
		if (frames.length > 0) {
			return frames[0];
		}
		return null;
	}
}
