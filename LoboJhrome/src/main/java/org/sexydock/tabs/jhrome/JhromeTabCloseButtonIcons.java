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

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class JhromeTabCloseButtonIcons {
    private static Icon normalIcon;
    private static Icon rolloverIcon;
    private static Icon pressedIcon;

    public static Icon getJhromeNormalIcon() {
        if (normalIcon == null) {
            normalIcon = createJhromeNormalIcon();
        }
        return normalIcon;
    }

    public static Icon getJhromeRolloverIcon() {
        if (rolloverIcon == null) {
            rolloverIcon = createJhromeRolloverIcon();
        }
        return rolloverIcon;
    }

    public static Icon getJhromePressedIcon() {
        if (pressedIcon == null) {
            pressedIcon = createJhromePressedIcon();
        }
        return pressedIcon;
    }

    public static ImageIcon createJhromeNormalIcon() {
        return createIcon(12, 2.9f, new Color(174, 174, 174), new BasicStroke(1.4f), null);
    }

    public static ImageIcon createJhromeRolloverIcon() {
        return createIcon(12, 2.5f, new Color(249, 235, 235), new BasicStroke(1.2f), new Color(193, 53, 53));
    }

    public static ImageIcon createJhromePressedIcon() {
        return createIcon(12, 2.5f, new Color(237, 233, 233), new BasicStroke(1.2f), new Color(67, 30, 32));
    }

    public static ImageIcon createIcon(int size, float xLength, Color xColor, Stroke xStroke, Color circleColor) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) image.getGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (circleColor != null) {
            g2.setColor(circleColor);
            g2.fillOval(0, 0, size, size);
        }

        g2.setColor(xColor);
        g2.setStroke(xStroke);

        int size2 = (int) Math.ceil(size / 2.0);

        g2.draw(new Line2D.Float(size2 - xLength, size2 - xLength, size2 + xLength, size2 + xLength));
        g2.draw(new Line2D.Float(size2 - xLength, size2 + xLength, size2 + xLength, size2 - xLength));

        return new ImageIcon(image);
    }
}
