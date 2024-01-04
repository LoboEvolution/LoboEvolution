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

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Path2D;

public class JhromeContentPanelBorder implements Border {
    final int roundness = 5;
    final int thickness = 2;
    final Color outlineColor = JhromeTabBorderAttributes.SELECTED_BORDER.outlineColor;
    final Color backgroundColor = JhromeTabBorderAttributes.SELECTED_BORDER.bottomColor;

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        final Graphics2D g2 = (Graphics2D) g;

        final int inset = Math.max(thickness - 1, 0);

        final Path2D path = new Path2D.Double();
        path.moveTo(x + inset, y + height - inset - inset);
        path.lineTo(x + inset, y + inset + roundness);
        path.curveTo(x + inset, y + inset, x + inset, y + inset, x + inset + roundness, y + inset);
        path.lineTo(x + width - roundness - inset - inset, y + inset);
        path.curveTo(x + width - inset - inset, y + inset, x + width - inset - inset, y + inset, x + width - inset - inset, y + inset + roundness);
        path.lineTo(x + width - inset - inset, y + height - inset - inset);
        path.closePath();

        final Stroke prevStroke = g2.getStroke();
        final Paint prevPaint = g2.getPaint();
        final Object prevAntialias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(outlineColor);
        g2.draw(path);

        g2.setColor(backgroundColor);
        g2.fill(path);

        g2.setStroke(prevStroke);
        g2.setPaint(prevPaint);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, prevAntialias);
    }

    @Override
    public Insets getBorderInsets(final Component c) {
        return new Insets(roundness + thickness, roundness + thickness, roundness + thickness, roundness + thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        // TODO Auto-generated method stub
        return false;
    }

}
