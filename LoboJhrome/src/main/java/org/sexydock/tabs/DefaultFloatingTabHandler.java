/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import org.sexydock.SwingUtils;
import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DragSourceDragEvent;

public class DefaultFloatingTabHandler implements IFloatingTabHandler {
    private Window dragImageWindow = null;
    private Image dragImage = null;

    private int xOffs;
    private int yOffs;

    public void initialize(final Tab draggedTab, final Point grabPoint) {
        final JTabbedPane tabbedPane = SwingUtils.getJTabbedPaneAncestor(draggedTab);
        final JhromeTabbedPaneUI tabbedPaneUI = SwingUtils.getJTabbedPaneAncestorUI(draggedTab);

        if (tabbedPaneUI != null) {
            dragImage = tabbedPaneUI.createDragImage(draggedTab);
            switch (tabbedPane.getTabPlacement()) {
                case SwingConstants.TOP:
                    xOffs = -grabPoint.x * 3 / 4;
                    yOffs = 10;
                    break;
                case SwingConstants.BOTTOM:
                    xOffs = -grabPoint.x * 3 / 4;
                    yOffs = -dragImage.getHeight(null) - 10;
                    break;
                case SwingConstants.LEFT:
                    xOffs = 10;
                    yOffs = -grabPoint.y * 3 / 4;
                    break;
                case SwingConstants.RIGHT:
                    xOffs = -dragImage.getWidth(null) - 10;
                    yOffs = -grabPoint.y * 3 / 4;
                    break;
            }
        }
    }

    @Override
    public void onFloatingBegin(final Tab draggedTab, final Point grabPoint) {
        initialize(draggedTab, grabPoint);

        if (dragImage != null) {
            if (dragImageWindow == null) {
                dragImageWindow = new Window(null) {
                    @Override
                    public void paint(final Graphics g) {
                        final Graphics2D g2 = (Graphics2D) g;

                        if (dragImage != null) {
                            g2.drawImage(dragImage, 0, 0, null);
                        }
                    }
                };

                dragImageWindow.setBackground(new Color(0, 0, 0, 0));
            }

            dragImageWindow.setSize(dragImage.getWidth(null), dragImage.getHeight(null));
            dragImageWindow.setAlwaysOnTop(true);
        }
    }

    @Override
    public void onFloatingTabDragged(final DragSourceDragEvent dsde, final Tab draggedTab, final double grabX) {
        if (dragImageWindow != null) {
            final Point p = new Point(dsde.getX() + xOffs, dsde.getY() + yOffs);
            dragImageWindow.setLocation(p);
            dragImageWindow.setVisible(true);
        }
    }

    @Override
    public void onFloatingEnd() {
        if (dragImageWindow != null) {
            dragImageWindow.dispose();
            dragImageWindow = null;
        }
        dragImage = null;
    }
}
