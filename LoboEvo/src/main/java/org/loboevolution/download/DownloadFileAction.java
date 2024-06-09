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

package org.loboevolution.download;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.store.DownloadStore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.Serial;

/**
 * <p>DownloadFileAction class.</p>
 */
@Slf4j
public class DownloadFileAction extends AbstractAction implements PropertyChangeListener {

    @Serial
    private static final long serialVersionUID = 1L;

    private final DownloadWindow action;

    DownloadFileAction(final DownloadWindow action) {
        this.action = action;
    }

    /** {@inheritDoc} */
    public void actionPerformed(final ActionEvent e) {
        try {
            startDownload(action.getDestinationFile());
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void startDownload(final File destinationFile) {
        final DownloadTask task = new DownloadTask(action, destinationFile);
        task.addPropertyChangeListener(this);
        task.execute();

        final DownloadStore store = new DownloadStore();
        store.addAsRecent(action.getUrl().toExternalForm());
    }

    /**
     * {@inheritDoc}
     *
     * Update the progress bar's state whenever the progress of download changes.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            final int progress = (Integer) evt.getNewValue();
            action.getProgressBar().setValue(progress);
        }
    }
}
