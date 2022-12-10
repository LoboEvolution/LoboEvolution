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
import java.awt.geom.Path2D;

public class JhromeContentPanelBorder implements Border {
    final int roundness = 5;
    final int thickness = 2;
    final Color outlineColor = JhromeTabBorderAttributes.SELECTED_BORDER.outlineColor;
    final Color backgroundColor = JhromeTabBorderAttributes.SELECTED_BORDER.bottomColor;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;

        int inset = Math.max(thickness - 1, 0);

        Path2D path = new Path2D.Double();
        path.moveTo(x + inset, y + height - inset - inset);
        path.lineTo(x + inset, y + inset + roundness);
        path.curveTo(x + inset, y + inset, x + inset, y + inset, x + inset + roundness, y + inset);
        path.lineTo(x + width - roundness - inset - inset, y + inset);
        path.curveTo(x + width - inset - inset, y + inset, x + width - inset - inset, y + inset, x + width - inset - inset, y + inset + roundness);
        path.lineTo(x + width - inset - inset, y + height - inset - inset);
        path.closePath();

        Stroke prevStroke = g2.getStroke();
        Paint prevPaint = g2.getPaint();
        Object prevAntialias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
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
    public Insets getBorderInsets(Component c) {
        return new Insets(roundness + thickness, roundness + thickness, roundness + thickness, roundness + thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        // TODO Auto-generated method stub
        return false;
    }

}
