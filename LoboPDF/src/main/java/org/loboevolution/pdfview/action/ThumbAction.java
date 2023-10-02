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

package org.loboevolution.pdfview.action;

import org.loboevolution.pdf.PDFViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The Class ThumbAction.
 */
public class ThumbAction extends AbstractAction implements PropertyChangeListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private final PDFViewer dialog;
    /**
     * The is open.
     */
    private boolean isOpen = true;

    /**
     * Instantiates a new thumb action.
     *
     * @param dialog a {@link org.loboevolution.pdf.PDFViewer} object.
     */
    public ThumbAction(final PDFViewer dialog) {
        super("Hide thumbnails");
        this.dialog = dialog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        final int v = (Integer) evt.getNewValue();
        if (v <= 1) {
            isOpen = false;
            putValue(ACTION_COMMAND_KEY, "Show thumbnails");
            putValue(NAME, "Show thumbnails");
        } else {
            isOpen = true;
            putValue(ACTION_COMMAND_KEY, "Hide thumbnails");
            putValue(NAME, "Hide thumbnails");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent evt) {
        dialog.doThumbs(!isOpen);
    }
}
