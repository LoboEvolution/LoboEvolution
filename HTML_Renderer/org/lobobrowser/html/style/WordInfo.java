/*
 * GNU LESSER GENERAL private LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General private License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General private License for more
 * details. You should have received a copy of the GNU Lesser General private
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.lobobrowser.html.style;

import java.awt.FontMetrics;

/**
 * The Class WordInfo.
 */
public class WordInfo {

    /** The font metrics. */
    private FontMetrics fontMetrics;

    /** The descent. */
    private int descent;

    /** The ascent plus leading. */
    private int ascentPlusLeading;

    /** The width. */
    private int width;

    /** The height. */
    private int height;

    /**
     * Gets the font metrics.
     *
     * @return the font metrics
     */
    public FontMetrics getFontMetrics() {
        return fontMetrics;
    }

    /**
     * Sets the font metrics.
     *
     * @param fontMetrics
     *            the new font metrics
     */
    public void setFontMetrics(FontMetrics fontMetrics) {
        this.fontMetrics = fontMetrics;
    }

    /**
     * Gets the descent.
     *
     * @return the descent
     */
    public int getDescent() {
        return descent;
    }

    /**
     * Sets the descent.
     *
     * @param descent
     *            the new descent
     */
    public void setDescent(int descent) {
        this.descent = descent;
    }

    /**
     * Gets the ascent plus leading.
     *
     * @return the ascent plus leading
     */
    public int getAscentPlusLeading() {
        return ascentPlusLeading;
    }

    /**
     * Sets the ascent plus leading.
     *
     * @param ascentPlusLeading
     *            the new ascent plus leading
     */
    public void setAscentPlusLeading(int ascentPlusLeading) {
        this.ascentPlusLeading = ascentPlusLeading;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width.
     *
     * @param width
     *            the new width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height
     *            the new height
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
