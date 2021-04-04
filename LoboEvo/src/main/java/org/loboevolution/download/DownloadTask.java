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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;

import javax.swing.*;

/**
 * Execute file download in a background thread and update the progress.
 *
 * Author www.codejava.net
 *
 */
public class DownloadTask extends SwingWorker<Void, Void> {

    private final DownloadWindow action;

    private final File destinationFile;

    /**
     * <p>Constructor for DownloadTask.</p>
     *
     * @param action a {@link org.loboevolution.download.DownloadWindow} object.
     * @param destinationFile a {@link java.io.File} object.
     */
    public DownloadTask(DownloadWindow action, File destinationFile) {
        this.action = action;
        this.destinationFile = destinationFile;
    }

    /**
     * {@inheritDoc}
     *
     * Executed in background thread
     */
    @Override
    protected Void doInBackground() {
        try {
            String fileName = "";
            long downloadBaseTimestamp = System.currentTimeMillis();
            URLConnection httpConn = this.action.getHttpConn();
            String disposition = httpConn.getHeaderField("Content-Disposition");
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10, disposition.length() - 1);
                }
            } else {
                String fileURL = action.getUrl().getFile();
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            }

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = destinationFile.getPath() + "\\" + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            long totalBytesRead = 0;
            int percentCompleted;
            long fileSize = httpConn.getContentLength();

            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                long elapsed = System.currentTimeMillis() - downloadBaseTimestamp;
                if (elapsed > 0) {
                    double transferRate = (double) fileSize / elapsed;
                    action.getTransferRateField().setValue(TimingDowload.round1(transferRate) + " Kb/sec");
                } else {
                    action.getTransferRateField().setValue("N/A");
                }

                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                percentCompleted = (int) (totalBytesRead * 100 / fileSize);
                setProgress(percentCompleted);
                action.getTransferSizeField().setValue(TimingDowload.getSizeText(totalBytesRead));
            }

            outputStream.close();
            inputStream.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(action, "Error downloading file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            setProgress(0);
            cancel(true);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * Executed in Swing's event dispatching thread
     */
    @Override
    protected void done() {
        if (!isCancelled()) {
            JOptionPane.showMessageDialog(action,
                    "File has been downloaded successfully!", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
