/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.sexydock.tabs;

import java.awt.*;

public class TabDragInfo {

    public final IFloatingTabHandler floatingTabHandler;
    public final double grabX;
    public final Tab tab;
    public final Dimension sourceWindowSize;
    private Point grabPoint;
    public TabDragInfo(final Tab tab, final Point grabPoint, final double grabX, final IFloatingTabHandler floatingTabHandler, final Dimension sourceWindowSize) {
        super();
        this.floatingTabHandler = floatingTabHandler;
        this.grabPoint = new Point(grabPoint);
        this.grabX = grabX;
        this.tab = tab;
        this.sourceWindowSize = sourceWindowSize;
    }

    public Point getGrabPoint() {
        return new Point(grabPoint);
    }

    public void setGrabPoint(final Point p) {
        grabPoint = p == null ? null : new Point(p);
    }
}