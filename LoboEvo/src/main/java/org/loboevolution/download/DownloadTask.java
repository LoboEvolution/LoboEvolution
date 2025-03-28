/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;

import javax.swing.*;

/**
 * Execute file download in a background thread and update the progress.
 */
@Slf4j
public class DownloadTask extends SwingWorker<Void, Void> {

    private final DownloadWindow action;

    private final File destinationFile;

    /**
     * <p>Constructor for DownloadTask.</p>
     *
     * @param action a {@link org.loboevolution.download.DownloadWindow} object.
     * @param destinationFile a {@link java.io.File} object.
     */
    public DownloadTask(final DownloadWindow action, final File destinationFile) {
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
            final long downloadBaseTimestamp = System.currentTimeMillis();
            final URLConnection httpConn = this.action.getHttpConn();
            final String disposition = httpConn.getHeaderField("Content-Disposition");
            if (disposition != null) {
                // extracts file name from header field
                final int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10, disposition.length() - 1);
                }
            } else {
                final String fileURL = action.getUrl().getFile();
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            }

            final String saveFilePath = destinationFile.getPath() + "\\" + fileName;

            try (final InputStream inputStream = httpConn.getInputStream();
                 final FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {

                long totalBytesRead = 0;
                int percentCompleted;
                final long fileSize = httpConn.getContentLength();

                int bytesRead;
                final byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    final long elapsed = System.currentTimeMillis() - downloadBaseTimestamp;
                    if (elapsed > 0) {
                        final double transferRate = (double) fileSize / elapsed;
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
            }

        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(action, "Error downloading file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            log.error(ex.getMessage(), ex);
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
