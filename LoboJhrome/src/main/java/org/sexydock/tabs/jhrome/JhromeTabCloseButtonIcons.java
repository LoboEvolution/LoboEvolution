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
