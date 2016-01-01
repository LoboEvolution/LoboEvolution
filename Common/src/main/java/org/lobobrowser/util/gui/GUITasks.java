/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util.gui;

import java.awt.Frame;
import java.awt.Graphics;

/**
 * The Class GUITasks.
 */
public class GUITasks {
    
    /** Gets the top frame.
	 *
	 * @return the top frame
	 */
    public static Frame getTopFrame() {
        Frame[] frames = Frame.getFrames();
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].getFocusOwner() != null) {
                return frames[i];
            }
        }
        if (frames.length > 0) {
            return frames[0];
        }
        return null;
    }
    
    /**
     * Draw dashed.
     *
     * @param g
     *            the g
     * @param x1
     *            the x1
     * @param y1
     *            the y1
     * @param x2
     *            the x2
     * @param y2
     *            the y2
     * @param dashSize
     *            the dash size
     * @param gapSize
     *            the gap size
     */
    public static void drawDashed(Graphics g, int x1, int y1, int x2, int y2,
            int dashSize, int gapSize) {
        if (x2 < x1) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y2 < y1) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        int totalDash = dashSize + gapSize;
        if (y1 == y2) {
            int virtualStartX = (x1 / totalDash) * totalDash;
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
            int virtualStartY = (y1 / totalDash) * totalDash;
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
}
