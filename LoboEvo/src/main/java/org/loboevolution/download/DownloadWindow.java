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

import com.jtattoo.plaf.lobo.LoboBackground;
import com.jtattoo.plaf.lobo.LoboButton;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.component.IDownload;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.gui.FieldType;
import org.loboevolution.gui.FormField;
import org.loboevolution.gui.FormPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>DownloadWindow class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class DownloadWindow extends JFrame implements IDownload {

    private static final long serialVersionUID = 1L;

    private final JProgressBar progressBar = new JProgressBar(0, 100);

    private final FormField transferRateField = new FormField(FieldType.TEXT, false);

    private final FormField transferSizeField = new FormField(FieldType.TEXT, false);

    protected final FormField destinationField = new FormField(FieldType.TEXT, false);

    protected final FormField timeLeftField = new FormField(FieldType.TEXT, false);

    protected final LoboButton saveButton = new LoboButton();

    private final LoboButton closeButton = new LoboButton();

    private final LoboButton openFolderButton = new LoboButton();

    private File destinationFile;

    private URLConnection httpConn = null;

    private URL url = null;

    /**
     * <p>Constructor for DownloadWindow.</p>
     */
    public DownloadWindow() {}

    /**
     * <p>Constructor for DownloadWindow.</p>
     *
     * @param url a {@link java.net.URL} object.
     * @throws java.io.IOException if any.
     */
    public DownloadWindow(final URL url) throws IOException {
        final LoboBackground lb = new LoboBackground();
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.setBackground(lb.getBackground());
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        final ImageIcon ico = new ImageIcon(DesktopConfig.getResourceFile("download.png",DesktopConfig.PATH_IMAGE));
        setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        setBounds(100, 100, 450, 300);

        this.url = url;
        httpConn = url.openConnection();

        final FormPanel topFormPanel = new FormPanel();
        final FormField documentField = new FormField(FieldType.TEXT, false);
        final FormField sizeField = new FormField(FieldType.TEXT, false);
        final FormField mimeTypeField = new FormField(FieldType.TEXT, false);

        documentField.setCaption("Document:");
        mimeTypeField.setCaption("MIME type:");
        sizeField.setCaption("Size:");

        this.timeLeftField.setCaption("Estimated time:");
        this.destinationField.setCaption("Folder Destination:");
        this.transferSizeField.setCaption("Download size:");
        this.transferRateField.setCaption("Download rate:");
        this.progressBar.setPreferredSize(new Dimension(200, 30));
        this.progressBar.setStringPainted(true);
        this.progressBar.setValue(0);

        final String urlStr = url.toExternalForm();
        final String fileName = urlStr.substring(urlStr.lastIndexOf('/')+1);
        documentField.setValue(fileName);
        documentField.setToolTip(url.toExternalForm());
        mimeTypeField.setValue(httpConn.getContentType());
        sizeField.setValue(TimingDowload.getSizeText(httpConn.getContentLength()));
        final String estTimeText = TimingDowload.getElapsedText(httpConn.getContentLength());
        this.timeLeftField.setValue(estTimeText);

        final Box rootPanel = new Box(BoxLayout.Y_AXIS);
        rootPanel.add(topFormPanel);
        rootPanel.add(Box.createVerticalStrut(8));
        rootPanel.add(this.getButtonsPanel());
        rootPanel.add(Box.createVerticalStrut(8));
        rootPanel.add(this.progressBar);

        topFormPanel.addField(documentField);
        topFormPanel.addField(mimeTypeField);
        topFormPanel.addField(sizeField);
        topFormPanel.addField(timeLeftField);
        topFormPanel.addField(this.destinationField);
        topFormPanel.addField(this.transferRateField);
        topFormPanel.addField(this.transferSizeField);

        final Dimension topPanelPs = topFormPanel.getPreferredSize();
        topFormPanel.setPreferredSize(new Dimension(400, topPanelPs.height));

        contentPane.add(rootPanel);

    }

    private Component getButtonsPanel() {
        final JButton saveButton = this.saveButton;
        saveButton.setAction(new DownloadFileAction(this));
        saveButton.setText("Download File");
        saveButton.setToolTipText("You must select a file before download begins.");

        final JButton closeButton = this.closeButton;
        closeButton.setAction(new CloseDownloadAction(this));
        closeButton.setText("Cancel");

        final JButton openFolderButton = this.openFolderButton;
        openFolderButton.setAction(new ChoiceFolderDownloadAction(this));
        openFolderButton.setText("Choice Folder");

        final Box box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalStrut(4));
        box.add(openFolderButton);
        box.add(Box.createHorizontalStrut(4));
        box.add(saveButton);
        box.add(Box.createHorizontalStrut(4));
        box.add(closeButton);
        return box;
    }

    /** {@inheritDoc} */
    @Override
    public void downloadFile(final URL url) {
        try {
            final DownloadWindow d = new DownloadWindow(url);
            d.setVisible(true);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    
    /**
     * <p>downloadFile.</p>
     *
     * @param url a {@link java.lang.String} object.
     */
    public void downloadFile(final String url)  {
    	try {
            final DownloadWindow d = new DownloadWindow(new URL(url));
            d.setVisible(true);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void downloadFile(final InputStream inputStream) {
        try {
            final String PREFIX = "stream2file";
            final String SUFFIX = ".tmp";
            final File tempFile = File.createTempFile(PREFIX, SUFFIX);
            copyInputStreamToFile(inputStream, tempFile);
            final DownloadWindow d = new DownloadWindow(tempFile.toURI().toURL());
            d.setVisible(true);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static void copyInputStreamToFile(final InputStream inputStream, final File file) throws IOException {
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }
}