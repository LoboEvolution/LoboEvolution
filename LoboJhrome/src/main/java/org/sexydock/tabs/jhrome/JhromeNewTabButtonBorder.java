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

package org.sexydock.tabs.jhrome;

import org.sexydock.tabs.Utils;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class JhromeNewTabButtonBorder implements Border {
    public static final Attributes UNSELECTED_ATTRIBUTES = new Attributes();
    public static final Attributes ROLLOVER_ATTRIBUTES = new Attributes();
    public static final Attributes PRESSED_ATTRIBUTES = new Attributes();

    static {
        ROLLOVER_ATTRIBUTES.fillColor = new Color(255, 255, 255, 128);
        PRESSED_ATTRIBUTES.fillColor = new Color(196, 196, 196, 128);
        PRESSED_ATTRIBUTES.borderColor = JhromeTabBorderAttributes.SELECTED_BORDER.outlineColor;
    }

    public final Attributes attrs = new Attributes();
    public boolean flip;

    public void paint(final Component c, final Graphics g, final int x, final int y, final int width, final int height, final boolean paintBorder, final boolean paintBackground) {
        final Path2D path = new Path2D.Float();
        path.moveTo(x, y);
        path.lineTo(x + width - 1 - attrs.slant * 2, y);
        path.curveTo(x + width - 1 - attrs.slant, y, x + width - 1 - attrs.slant, y, x + width - 1, y + height - 1);
        path.lineTo(x + attrs.slant * 2, y + height - 1);
        path.curveTo(x + attrs.slant, y + height - 1, x + attrs.slant, y + height - 1, x, y);

        if (flip) {
            path.transform(new AffineTransform(1, 0, 0, -1, 0, height - 1));
        }

        final Graphics2D g2 = (Graphics2D) g;

        final Object prevAntialias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final Stroke prevStroke = g2.getStroke();
        final Paint prevPaint = g2.getPaint();

        if (paintBackground) {
            g2.setColor(attrs.fillColor);
            g2.fill(path);
        }

        if (paintBorder) {
            g2.setColor(attrs.borderColor);
            g2.setStroke(attrs.borderStroke);
            g2.draw(path);
        }

        g2.setPaint(prevPaint);
        g2.setStroke(prevStroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, prevAntialias);

    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        paint(c, g, x, y, width, height, true, false);
    }

    @Override
    public Insets getBorderInsets(final Component c) {
        final int i = 2;
        return new Insets(i, i + (int) attrs.slant, i, i + (int) attrs.slant);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    public static class Attributes {
        public float slant = 4.5f;

        public Color fillColor = new Color(255, 255, 255, 48);
        public Color borderColor = JhromeTabBorderAttributes.UNSELECTED_BORDER.outlineColor;
        public Stroke borderStroke = JhromeTabBorderAttributes.UNSELECTED_BORDER.outlineStroke;

        public Attributes() {

        }

        public Attributes(final Attributes other) {
            copy(other);
        }

        public void copy(final Attributes other) {
            slant = other.slant;
            fillColor = other.fillColor;
            borderColor = other.borderColor;
            borderStroke = other.borderStroke;
        }

        @Override
        public Attributes clone() {
            return new Attributes(this);
        }

        public void interpolateColors(final Attributes a, final Attributes b, final float f) {
            borderColor = Utils.interpolate(a.borderColor, b.borderColor, f);
            fillColor = Utils.interpolate(a.fillColor, b.fillColor, f);
        }
    }

}
