/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.download;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;


import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.gui.DefaultWindowFactory;
import org.loboevolution.primary.gui.FieldType;
import org.loboevolution.primary.gui.FormField;
import org.loboevolution.primary.gui.FormPanel;
import org.loboevolution.request.RequestHandler;
import org.loboevolution.util.Timing;

/**
 * The Class DownloadDialog.
 */
public class DownloadDialog extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The progress bar. */
	private final JProgressBar progressBar = new JProgressBar();

	/** The bottom form panel. */
	private final transient FormPanel bottomFormPanel = new FormPanel();

	/** The top form panel. */
	private final transient FormPanel topFormPanel = new FormPanel();

	/** The document field. */
	private final transient FormField documentField = new FormField(FieldType.TEXT, false);

	/** The size field. */
	private final transient FormField sizeField = new FormField(FieldType.TEXT, false);

	/** The destination field. */
	private final transient FormField destinationField = new FormField(FieldType.TEXT, false);

	/** The time left field. */
	private final transient FormField timeLeftField = new FormField(FieldType.TEXT, false);

	/** The mime type field. */
	private final transient FormField mimeTypeField = new FormField(FieldType.TEXT, false);

	/** The transfer rate field. */
	private final transient FormField transferRateField = new FormField(FieldType.TEXT, false);

	/** The transfer size field. */
	private final transient FormField transferSizeField = new FormField(FieldType.TEXT, false);

	/** The save button. */
	private final JButton saveButton = new JButton();

	/** The close button. */
	private final JButton closeButton = new JButton();

	/** The open folder button. */
	private final JButton openFolderButton = new JButton();

	/** The open button. */
	private final JButton openButton = new JButton();

	/** The url. */
	private final URL url;

	/** The known content length. */
	private final int knownContentLength;
	
	/** The request handler. */
	private transient RequestHandler requestHandler;

	/** The destination file. */
	private File destinationFile;

	/** The download base timestamp. */
	private long downloadBaseTimestamp;
	/**
	 * Instantiates a new download dialog.
	 *
	 * @param response
	 *            the response
	 * @param url
	 *            the url
	 * @param transferSpeed
	 *            the transfer speed
	 */
	public DownloadDialog(ClientletResponse response, URL url, int transferSpeed) {
		this.url = url;
		this.setIconImage(DefaultWindowFactory.getInstance().getDefaultImageIcon().getImage());

		this.topFormPanel.setMinLabelWidth(100);
		this.getBottomFormPanel().setMinLabelWidth(100);

		this.getBottomFormPanel().setEnabled(false);

		this.documentField.setCaption("Document:");
		this.getTimeLeftField().setCaption("Estimated time:");
		this.mimeTypeField.setCaption("MIME type:");
		this.getSizeField().setCaption("Size:");
		this.getDestinationField().setCaption("File:");
		this.getTransferSizeField().setCaption("Transfer size:");
		this.getTransferRateField().setCaption("Transfer rate:");
		this.getOpenFolderButton().setVisible(false);
		this.getOpenButton().setVisible(false);

		this.documentField.setValue(url.toExternalForm());
		this.mimeTypeField.setValue(response.getMimeType());
		int cl = response.getContentLength();
		this.knownContentLength = cl;
		String sizeText = cl == -1 ? "Not known" : getSizeText(cl);
		this.getSizeField().setValue(sizeText);
		String estTimeText = transferSpeed <= 0 || cl == -1 ? "Not known" : Timing.getElapsedText(cl / transferSpeed);
		this.getTimeLeftField().setValue(estTimeText);

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout());
		Box rootPanel = new Box(BoxLayout.Y_AXIS);
		rootPanel.setBorder(new EmptyBorder(4, 8, 4, 8));
		rootPanel.add(this.getProgressBar());
		rootPanel.add(Box.createVerticalStrut(8));
		rootPanel.add(this.topFormPanel);
		rootPanel.add(Box.createVerticalStrut(8));
		rootPanel.add(this.getBottomFormPanel());
		rootPanel.add(Box.createVerticalStrut(8));
		rootPanel.add(this.getButtonsPanel());
		contentPane.add(rootPanel);

		FormPanel bfp = this.getBottomFormPanel();
		bfp.addField(this.getDestinationField());
		bfp.addField(this.getTransferRateField());
		bfp.addField(this.getTransferSizeField());

		FormPanel tfp = this.topFormPanel;
		tfp.addField(this.documentField);
		tfp.addField(this.mimeTypeField);
		tfp.addField(this.getSizeField());
		tfp.addField(this.getTimeLeftField());

		Dimension topPanelPs = this.topFormPanel.getPreferredSize();
		this.topFormPanel.setPreferredSize(new Dimension(400, topPanelPs.height));

		Dimension bottomPanelPs = this.getBottomFormPanel().getPreferredSize();
		this.getBottomFormPanel().setPreferredSize(new Dimension(400, bottomPanelPs.height));

		this.getProgressBar().setEnabled(false);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				RequestHandler rh = getRequestHandler();
				if (rh != null) {
					rh.cancel();
					// So that there's no error dialog
					setRequestHandler(null);
				}
			}
		});
	}

	/**
	 * Gets the buttons panel.
	 *
	 * @return the buttons panel
	 */
	private Component getButtonsPanel() {
		JButton saveButton = this.getSaveButton();
		saveButton.setAction(new SaveAction(this));
		saveButton.setText("Save As...");
		saveButton.setToolTipText("You must select a file before download begins.");

		JButton closeButton = this.getCloseButton();
		closeButton.setAction(new CloseAction(this));
		closeButton.setText("Cancel");

		JButton openButton = this.getOpenButton();
		openButton.setAction(new OpenAction(this));
		openButton.setText("Open");

		JButton openFolderButton = this.getOpenFolderButton();
		openFolderButton.setAction(new OpenFolderAction(this));
		openFolderButton.setText("Open Folder");

		Box box = new Box(BoxLayout.X_AXIS);
		box.add(Box.createGlue());
		box.add(openButton);
		box.add(Box.createHorizontalStrut(4));
		box.add(openFolderButton);
		box.add(Box.createHorizontalStrut(4));
		box.add(saveButton);
		box.add(Box.createHorizontalStrut(4));
		box.add(closeButton);
		return box;
	}

	/**
	 * Round1.
	 *
	 * @param value
	 *            the value
	 * @return the double
	 */
	public double round1(double value) {
		return Math.round(value * 10.0) / 10.0;
	}

	/**
	 * Gets the size text.
	 *
	 * @param numBytes
	 *            the num bytes
	 * @return the size text
	 */
	public String getSizeText(long numBytes) {
		if (numBytes < 1024) {
			return numBytes + " bytes";
		} else {
			double numK = numBytes / 1024.0;
			if (numK < 1024) {
				return round1(numK) + " Kb";
			} else {
				double numM = numK / 1024.0;
				if (numM < 1024) {
					return round1(numM) + " Mb";
				} else {
					double numG = numM / 1024.0;
					return round1(numG) + " Gb";
				}
			}
		}
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the saveButton
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * @return the timeLeftField
	 */
	public FormField getTimeLeftField() {
		return timeLeftField;
	}

	/**
	 * @return the destinationField
	 */
	public FormField getDestinationField() {
		return destinationField;
	}

	/**
	 * @return the bottomFormPanel
	 */
	public FormPanel getBottomFormPanel() {
		return bottomFormPanel;
	}

	/**
	 * @return the destinationFile
	 */
	public File getDestinationFile() {
		return destinationFile;
	}

	/**
	 * @param destinationFile the destinationFile to set
	 */
	public void setDestinationFile(File destinationFile) {
		this.destinationFile = destinationFile;
	}

	/**
	 * @return the requestHandler
	 */
	public RequestHandler getRequestHandler() {
		return requestHandler;
	}

	/**
	 * @param requestHandler the requestHandler to set
	 */
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	/**
	 * @return the downloadBaseTimestamp
	 */
	public long getDownloadBaseTimestamp() {
		return downloadBaseTimestamp;
	}

	/**
	 * @param downloadBaseTimestamp the downloadBaseTimestamp to set
	 */
	public void setDownloadBaseTimestamp(long downloadBaseTimestamp) {
		this.downloadBaseTimestamp = downloadBaseTimestamp;
	}

	/**
	 * @return the knownContentLength
	 */
	public int getKnownContentLength() {
		return knownContentLength;
	}

	/**
	 * @return the transferSizeField
	 */
	public FormField getTransferSizeField() {
		return transferSizeField;
	}

	/**
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * @return the transferRateField
	 */
	public FormField getTransferRateField() {
		return transferRateField;
	}

	/**
	 * @return the openButton
	 */
	public JButton getOpenButton() {
		return openButton;
	}

	/**
	 * @return the openFolderButton
	 */
	public JButton getOpenFolderButton() {
		return openFolderButton;
	}

	/**
	 * @return the closeButton
	 */
	public JButton getCloseButton() {
		return closeButton;
	}

	/**
	 * @return the sizeField
	 */
	public FormField getSizeField() {
		return sizeField;
	}
}
