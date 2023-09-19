/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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