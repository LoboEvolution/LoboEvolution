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

package org.sexydock.tabs.jhrome;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * The default (Google Chrome style) border and background for a {@code JhromeTab}.
 *
 * @author andy.edwards
 */
public class JhromeTabBorder implements Border {
    public final JhromeTabBorderAttributes attrs = new JhromeTabBorderAttributes();

    private Path2D openPath;
    private Path2D closedPath;

    private boolean flip;

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    private void updatePaths(int x, int y, int width, int height) {
        if (width < attrs.insets.left + attrs.insets.right) {
            return;
        }

        openPath = new Path2D.Double(Path2D.WIND_EVEN_ODD);

        openPath.moveTo(x, y + height - attrs.insets.bottom);
        openPath.curveTo(x + attrs.insets.left / 2, y + height - attrs.insets.bottom, x + attrs.insets.left / 2, y + attrs.insets.top, x + attrs.insets.left, y + attrs.insets.top);
        openPath.lineTo(x + width - attrs.insets.right, y + attrs.insets.top);
        openPath.curveTo(x + width - attrs.insets.right / 2, y + attrs.insets.top, x + width - attrs.insets.right / 2, y + height - attrs.insets.bottom, x + width, y + height - attrs.insets.bottom);

        if (flip) {
            openPath.transform(new AffineTransform(1, 0, 0, -1, 0, height));
        }

        closedPath = (Path2D) openPath.clone();
        closedPath.closePath();
    }

    public boolean contains(Point p) {
        if (closedPath != null) {
            return closedPath.contains(p);
        }
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (width < attrs.insets.left + attrs.insets.right) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        updatePaths(x, y, width, height);

        Object prevAntialias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke prevStroke = g2.getStroke();
        Paint prevPaint = g2.getPaint();

        if (attrs.topShadowVisible) {
            g2.setStroke(attrs.shadowStroke);
            g2.setColor(attrs.shadowColor);
            g2.draw(openPath);
        }

        g2.setPaint(new GradientPaint(0, flip ? y + height - 1 : y, attrs.topColor, 0, flip ? y : y + height - 1, attrs.bottomColor));
        g2.fill(closedPath);

        if (attrs.outlineVisible) {
            g2.setStroke(attrs.outlineStroke);
            g2.setColor(attrs.outlineColor);
            g2.draw(openPath);
        }

        g2.setPaint(prevPaint);
        g2.setStroke(prevStroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, prevAntialias);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return (Insets) attrs.insets.clone();
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
