package org.loboevolution.download;

import org.loboevolution.store.DownloadStore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class DownloadFileAction extends AbstractAction implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;

    private final DownloadWindow action;

    DownloadFileAction(DownloadWindow action) {
        this.action = action;
    }

    public void actionPerformed(ActionEvent e) {
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
