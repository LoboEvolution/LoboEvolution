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

package org.loboevolution.html.control;

import java.awt.*;
import java.util.Objects;

/**
 * <p>LayoutKey class.</p>
 */
public class LayoutKey {

    public final int availHeight;
    public final int availWidth;
    public final Font font;
    public final int whitespace;

    public LayoutKey(final int availWidth, final int availHeight, final int whitespace, final Font font) {
        this.availWidth = availWidth;
        this.availHeight = availHeight;
        this.whitespace = whitespace;
        this.font = font;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LayoutKey other)) {
            return false;
        }
        return other.availWidth == this.availWidth && other.availHeight == this.availHeight
                && other.whitespace == this.whitespace && Objects.equals(other.font, this.font);
    }

    @Override
    public int hashCode() {
        final Font font = this.font;
        return this.availWidth * 1000 + this.availHeight ^ (font == null ? 0 : font.hashCode());
    }
}
