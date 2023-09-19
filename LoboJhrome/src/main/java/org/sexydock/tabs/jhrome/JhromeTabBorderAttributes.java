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

import org.sexydock.tabs.Utils;

import java.awt.*;

public class JhromeTabBorderAttributes {
    public static final JhromeTabBorderAttributes SELECTED_BORDER = new JhromeTabBorderAttributes();
    public static final JhromeTabBorderAttributes UNSELECTED_BORDER;
    public static final JhromeTabBorderAttributes UNSELECTED_ROLLOVER_BORDER;

    static {
        UNSELECTED_BORDER = new JhromeTabBorderAttributes();
        UNSELECTED_BORDER.topColor = UNSELECTED_BORDER.bottomColor = new Color(211, 211, 222);
        UNSELECTED_BORDER.outlineColor = new Color(177, 160, 179);
        UNSELECTED_BORDER.topShadowVisible = false;
    }

    static {
        UNSELECTED_ROLLOVER_BORDER = new JhromeTabBorderAttributes();
        UNSELECTED_ROLLOVER_BORDER.topColor = UNSELECTED_ROLLOVER_BORDER.bottomColor = new Color(231, 231, 239);
        UNSELECTED_ROLLOVER_BORDER.outlineColor = new Color(154, 144, 156);
        UNSELECTED_ROLLOVER_BORDER.topShadowVisible = false;
    }

    public final Insets insets = new Insets(1, 15, 0, 15);
    public boolean topShadowVisible = true;
    public Stroke shadowStroke = new BasicStroke(2.5f);
    public Color shadowColor = new Color(55, 55, 55, 48);
    public boolean outlineVisible = true;
    public Stroke outlineStroke = new BasicStroke(1f);
    public Color outlineColor = new Color(149, 135, 150);
    public Color topColor = new Color(255, 255, 255);
    public Color bottomColor = new Color(248, 248, 248);
    public JhromeTabBorderAttributes() {
    }

    public JhromeTabBorderAttributes(JhromeTabBorderAttributes other) {
        copyAttributes(other);
    }

    @Override
    public JhromeTabBorderAttributes clone() {
        return new JhromeTabBorderAttributes(this);
    }

    public void copyAttributes(JhromeTabBorderAttributes other) {
        insets.set(other.insets.top, other.insets.left, other.insets.bottom, other.insets.right);

        topShadowVisible = other.topShadowVisible;
        shadowStroke = other.shadowStroke;
        shadowColor = other.shadowColor;

        outlineVisible = other.outlineVisible;
        outlineStroke = other.outlineStroke;
        outlineColor = other.outlineColor;

        topColor = other.topColor;
        bottomColor = other.bottomColor;
    }

    public void interpolateColors(JhromeTabBorderAttributes a, JhromeTabBorderAttributes b, float f) {
        shadowColor = Utils.interpolate(a.shadowColor, b.shadowColor, f);
        outlineColor = Utils.interpolate(a.outlineColor, b.outlineColor, f);
        topColor = Utils.interpolate(a.topColor, b.topColor, f);
        bottomColor = Utils.interpolate(a.bottomColor, b.bottomColor, f);
    }

}
