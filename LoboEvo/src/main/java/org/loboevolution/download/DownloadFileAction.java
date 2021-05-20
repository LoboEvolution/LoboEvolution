/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.download;

import org.loboevolution.store.DownloadStore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * <p>DownloadFileAction class.</p>
 *
 *
 *
 */
public class DownloadFileAction extends AbstractAction implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;

    private final DownloadWindow action;

    DownloadFileAction(DownloadWindow action) {
        this.action = action;
    }

    /** {@inheritDoc} */
    public void actionPerformed(final ActionEvent e) {
        try {
            startDownload(action.getDestinationFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void startDownload(File destinationFile) {
        DownloadTask task = new DownloadTask(action, destinationFile);
        task.addPropertyChangeListener(this);
        task.execute();

        DownloadStore store = new DownloadStore();
        store.addAsRecent(action.getUrl().toExternalForm());
    }

    /**
     * {@inheritDoc}
     *
     * Update the progress bar's state whenever the progress of download changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            action.getProgressBar().setValue(progress);
        }
    }
}
