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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.component.BrowserFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

/**
 * <p>CapturePane class.</p>
 */
@Slf4j
public class CapturePane extends JPanel {

    private Rectangle captureBounds;
    private Point clickPoint;
    private PreviewFrame preview;

    public CapturePane(final BrowserFrame frame) {
        setOpaque(false);
        final MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    System.exit(0);
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                clickPoint = e.getPoint();
                captureBounds = null;
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                clickPoint = null;
                takeScreenShot(frame);
            }

            @Override
            public void mouseDragged(final MouseEvent e) {
                final Point dragPoint = e.getPoint();
                final int x = Math.min(clickPoint.x, dragPoint.x);
                final int y = Math.min(clickPoint.y, dragPoint.y);
                final int width = Math.max(clickPoint.x - dragPoint.x, dragPoint.x - clickPoint.x);
                final int height = Math.max(clickPoint.y - dragPoint.y, dragPoint.y - clickPoint.y);
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
    private void takeScreenShot(final BrowserFrame frame) {
        try {
            final Robot r = new Robot();
            final BufferedImage img = r.createScreenCapture(captureBounds);
            displayPreview(img, frame);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * hides this frame and brings up the preview frame to display picture
     *
     * @param imgage a {@link java.awt.image.BufferedImage} object.
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    private void displayPreview(final BufferedImage imgage, final BrowserFrame frame) {
        if (preview == null) {
            preview = new PreviewFrame(this, frame);
        }
        setVisible(false);
        preview.setImage(new ImageIcon(imgage));
        preview.setVisible(true);
    }

    /**
     * Overrides frame painting, draws the rectangle cut out frame to show area of screenshot
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(255, 255, 255, 128));

        final Area fill = new Area(new Rectangle(new Point(0, 0), getSize()));
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