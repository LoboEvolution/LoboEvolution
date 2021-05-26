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
package org.loboevolution.menu.tools.screen;

import org.loboevolution.component.BrowserFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CapturePane extends JPanel {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(CapturePane.class.getName());

    private Rectangle captureBounds;
    private Point clickPoint;
    private PreviewFrame preview;

    public CapturePane(BrowserFrame frame) {
        setOpaque(false);
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    System.exit(0);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                clickPoint = e.getPoint();
                captureBounds = null;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clickPoint = null;
                takeScreenShot(frame);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point dragPoint = e.getPoint();
                int x = Math.min(clickPoint.x, dragPoint.x);
                int y = Math.min(clickPoint.y, dragPoint.y);
                int width = Math.max(clickPoint.x - dragPoint.x, dragPoint.x - clickPoint.x);
                int height = Math.max(clickPoint.y - dragPoint.y, dragPoint.y - clickPoint.y);
                captureBounds = new Rectangle(x, y, width, height);
                repaint();
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    /**
     * Takes a screenshot using awt.robot to use native screenshot command
     */
    private void takeScreenShot(BrowserFrame frame) {
        try {
            Robot r = new Robot();
            BufferedImage img = r.createScreenCapture(captureBounds);
            displayPreview(img, frame);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * hides this frame and brings up the preview frame to display picture
     *
     * @param img
     */
    private void displayPreview(BufferedImage img, BrowserFrame frame) {
        if (preview == null) {
            preview = new PreviewFrame(this, frame);
        }
        setVisible(false);
        preview.setImage(new ImageIcon(img));
        preview.setVisible(true);
    }

    /**
     * Overrides frame painting, draws the rectangle cut out frame to show area of screenshot
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(255, 255, 255, 128));

        Area fill = new Area(new Rectangle(new Point(0, 0), getSize()));
        if (captureBounds != null) {
            fill.subtract(new Area(captureBounds));
        }
        g2d.fill(fill);
        if (captureBounds != null) {
            g2d.setColor(Color.RED);
            g2d.draw(captureBounds);
        }
        g2d.dispose();
    }
}