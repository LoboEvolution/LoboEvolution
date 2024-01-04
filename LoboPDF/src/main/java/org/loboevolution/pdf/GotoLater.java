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
package org.loboevolution.pdf;

/**
 * Simple runnable to tell listeners that the page has changed.
 */
public class GotoLater implements Runnable {

    /**
     * The page.
     */
    private final int page;

    private final ThumbPanel thumb;

    /**
     * <p>Constructor for GotoLater.</p>
     *
     * @param pagenum a {@link java.lang.Integer} object.
     * @param thumb   a {@link org.loboevolution.pdf.ThumbPanel} object.
     */
    public GotoLater(final int pagenum, final ThumbPanel thumb) {
        page = pagenum;
        this.thumb = thumb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        if (thumb.getListener() != null) {
            thumb.getListener().gotoPage(page);
        }
    }
}
