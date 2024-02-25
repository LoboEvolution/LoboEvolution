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

package com.jtattoo.plaf.lobo;

import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.LAFColorType;
import org.loboevolution.store.laf.LAFSettings;

import java.awt.*;

/**
 * <p>LoboBackground class.</p>
 */
public class LoboBackground {

    private final LAFSettings laf;
    private final ColorFactory colorFactory;

    public LoboBackground(){
        laf = new LAFSettings().getInstance();
        colorFactory = ColorFactory.getInstance();
    }

    /**
     * <p>getBackground.</p>
     *
     * @return a {@link java.awt.Color} object.
     */
    public Color getBackground() {

        final Color color;
        if (laf.isBlackWhite() || laf.isNoire() || laf.isHiFi() || laf.isTexture()
                || laf.isGraphite() || laf.isAcryl()) {
            color = colorFactory.getColor(LAFColorType.BACKGROUND_BLACK_WHITE);
        } else if (laf.isWhiteBlack() || laf.isAluminium() || laf.isBernstein() || laf.isFast()
                || laf.isMcWin() || laf.isMint() || laf.isSmart()) {
            color = colorFactory.getColor(LAFColorType.BACKGROUND_WHITE_BLACK);
        } else {
            color = colorFactory.getColor(LAFColorType.BACKGROUND_MODERN);
        }
        return color;
    }

    /**
     * <p>getForeground.</p>
     *
     * @return a {@link java.awt.Color} object.
     */
    public Color getForeground() {
        final Color color;

        if (laf.isBlackWhite() || laf.isNoire() || laf.isHiFi() || laf.isTexture()
                || laf.isGraphite() || laf.isAcryl()) {
            color = colorFactory.getColor(LAFColorType.FOREGROUND_BLACK_WHITE);
        } else if (laf.isWhiteBlack() || laf.isAluminium() || laf.isBernstein() || laf.isFast()
                || laf.isMcWin() || laf.isMint() || laf.isSmart()) {
            color = colorFactory.getColor(LAFColorType.FOREGROUND_WHITE_BLACK);
        } else {
            color = colorFactory.getColor(LAFColorType.FOREGROUND_MODERN);
        }

        return color;
    }

    /**
     * <p>getInteractive.</p>
     *
     * @return a {@link java.awt.Color} object.
     */
    public Color getInteractive() {
        final Color color;

        if (laf.isBlackWhite() || laf.isNoire() || laf.isHiFi() || laf.isTexture()
                || laf.isGraphite() || laf.isAcryl()) {
            color = new Color(32, 32, 32);
        } else if (laf.isWhiteBlack() || laf.isAluminium() || laf.isBernstein() || laf.isFast()
                || laf.isMcWin() || laf.isMint() || laf.isSmart()) {
            color = new Color(255,255,240).darker();
        } else {
            color = new Color(37, 51, 61).darker();
        }

        return color;
    }
}
