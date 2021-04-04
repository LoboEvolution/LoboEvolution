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

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboLookAndFeel;
import org.loboevolution.component.IDownload;
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
 *
 *
 *
 */
public class DownloadWindow extends JFrame implements IDownload, LoboLookAndFeel {

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
    public DownloadWindow(){}

    /**
     * <p>Constructor for DownloadWindow.</p>
     *
     * @param url a {@link java.net.URL} object.
     * @throws java.io.IOException if any.
     */
    public DownloadWindow(URL url) throws IOException {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.setBackground(background());
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/download.png"));
        setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        setBounds(100, 100, 450, 300);

        this.url = url;
        httpConn = url.openConnection();

        FormPanel topFormPanel = new FormPanel();
        FormField documentField = new FormField(FieldType.TEXT, false);
        FormField sizeField = new FormField(FieldType.TEXT, false);
        FormField mimeTypeField = new FormField(FieldType.TEXT, false);

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

        String urlStr = url.toExternalForm();
        String fileName = urlStr.substring(urlStr.lastIndexOf('/')+1);
        documentField.setValue(fileName);
        documentField.setToolTip(url.toExternalForm());
        mimeTypeField.setValue(httpConn.getContentType());
        sizeField.setValue(TimingDowload.getSizeText(httpConn.getContentLength()));
        String estTimeText = TimingDowload.getElapsedText(httpConn.getContentLength());
        this.timeLeftField.setValue(estTimeText);

        Box rootPanel = new Box(BoxLayout.Y_AXIS);
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

        Dimension topPanelPs = topFormPanel.getPreferredSize();
        topFormPanel.setPreferredSize(new Dimension(400, topPanelPs.height));

        contentPane.add(rootPanel);

    }

    private Component getButtonsPanel() {
        JButton saveButton = this.saveButton;
        saveButton.setAction(new DownloadFileAction(this));
        saveButton.setText("Download File");
        saveButton.setToolTipText("You must select a file before download begins.");

        JButton closeButton = this.closeButton;
        closeButton.setAction(new CloseDownloadAction(this));
        closeButton.setText("Cancel");

        JButton openFolderButton = this.openFolderButton;
        openFolderButton.setAction(new ChoiceFolderDownloadAction(this));
        openFolderButton.setText("Choice Folder");

        Box box = new Box(BoxLayout.X_AXIS);
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
    public void downloadFile(URL url) {
        try {
            DownloadWindow d = new DownloadWindow(url);
            d.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * <p>downloadFile.</p>
     *
     * @param url a {@link java.lang.String} object.
     */
    public void downloadFile(String url)  {
    	try {
            DownloadWindow d = new DownloadWindow(new URL(url));
            d.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void downloadFile(InputStream inputStream) {
        try {
            final String PREFIX = "stream2file";
            final String SUFFIX = ".tmp";
            final File tempFile = File.createTempFile(PREFIX, SUFFIX);
            copyInputStreamToFile(inputStream, tempFile);
            DownloadWindow d = new DownloadWindow(tempFile.toURI().toURL());
            d.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }

    /**
     * <p>Getter for the field <code>destinationFile</code>.</p>
     *
     * @return a {@link java.io.File} object.
     */
    public File getDestinationFile() {
        return destinationFile;
    }

    /**
     * <p>Setter for the field <code>destinationFile</code>.</p>
     *
     * @param destinationFile a {@link java.io.File} object.
     */
    public void setDestinationFile(File destinationFile) {
        this.destinationFile = destinationFile;
    }

    /**
     * <p>Getter for the field <code>httpConn</code>.</p>
     *
     * @return a {@link java.net.URLConnection} object.
     */
    public URLConnection getHttpConn() {
        return httpConn;
    }

    /**
     * <p>Getter for the field <code>url</code>.</p>
     *
     * @return a {@link java.net.URL} object.
     */
    public URL getUrl() {
        return url;
    }

    /**
     * <p>Getter for the field <code>progressBar</code>.</p>
     *
     * @return a {@link javax.swing.JProgressBar} object.
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * <p>Getter for the field <code>transferRateField</code>.</p>
     *
     * @return a {@link org.loboevolution.gui.FormField} object.
     */
    public FormField getTransferRateField() {
        return transferRateField;
    }

    /**
     * <p>Getter for the field <code>transferSizeField</code>.</p>
     *
     * @return a {@link org.loboevolution.gui.FormField} object.
     */
    public FormField getTransferSizeField() {
        return transferSizeField;
    }
}

