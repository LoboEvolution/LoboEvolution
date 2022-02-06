/*
Copyright 2012 James Edwards

This file is part of Jhrome.

Jhrome is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jhrome is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Jhrome.  If not, see <http://www.gnu.org/licenses/>.
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
