/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.download;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.gui.DefaultWindowFactory;
import org.lobobrowser.primary.gui.FieldType;
import org.lobobrowser.primary.gui.FormField;
import org.lobobrowser.primary.gui.FormPanel;
import org.lobobrowser.primary.settings.ToolsSettings;
import org.lobobrowser.request.AbstractRequestHandler;
import org.lobobrowser.request.ClientletRequestImpl;
import org.lobobrowser.request.RequestEngine;
import org.lobobrowser.request.RequestHandler;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.util.OS;
import org.lobobrowser.util.Timing;

/**
 * The Class DownloadDialog.
 */
public class DownloadDialog extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(DownloadDialog.class);

	/** The progress bar. */
	private final JProgressBar progressBar = new JProgressBar();

	/** The bottom form panel. */
	private final FormPanel bottomFormPanel = new FormPanel();

	/** The top form panel. */
	private final FormPanel topFormPanel = new FormPanel();

	/** The document field. */
	private final FormField documentField = new FormField(FieldType.TEXT, false);

	/** The size field. */
	private final FormField sizeField = new FormField(FieldType.TEXT, false);

	/** The destination field. */
	private final FormField destinationField = new FormField(FieldType.TEXT, false);

	/** The time left field. */
	private final FormField timeLeftField = new FormField(FieldType.TEXT, false);

	/** The mime type field. */
	private final FormField mimeTypeField = new FormField(FieldType.TEXT, false);

	/** The transfer rate field. */
	private final FormField transferRateField = new FormField(FieldType.TEXT, false);

	/** The transfer size field. */
	private final FormField transferSizeField = new FormField(FieldType.TEXT, false);

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
	private RequestHandler requestHandler;

	/** The destination file. */
	private File destinationFile;

	/** The download base timestamp. */
	private long downloadBaseTimestamp;

	/** The last timestamp. */
	private long lastTimestamp;

	/** The last progress value. */
	private long lastProgressValue;

	/** The last transfer rate. */
	private double lastTransferRate = Double.NaN;

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
		this.bottomFormPanel.setMinLabelWidth(100);

		this.bottomFormPanel.setEnabled(false);

		this.documentField.setCaption("Document:");
		this.timeLeftField.setCaption("Estimated time:");
		this.mimeTypeField.setCaption("MIME type:");
		this.sizeField.setCaption("Size:");
		this.destinationField.setCaption("File:");
		this.transferSizeField.setCaption("Transfer size:");
		this.transferRateField.setCaption("Transfer rate:");
		this.openFolderButton.setVisible(false);
		this.openButton.setVisible(false);

		this.documentField.setValue(url.toExternalForm());
		this.mimeTypeField.setValue(response.getMimeType());
		int cl = response.getContentLength();
		this.knownContentLength = cl;
		String sizeText = cl == -1 ? "Not known" : getSizeText(cl);
		this.sizeField.setValue(sizeText);
		String estTimeText = transferSpeed <= 0 || cl == -1 ? "Not known" : Timing.getElapsedText(cl / transferSpeed);
		this.timeLeftField.setValue(estTimeText);

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout());
		Box rootPanel = new Box(BoxLayout.Y_AXIS);
		rootPanel.setBorder(new EmptyBorder(4, 8, 4, 8));
		rootPanel.add(this.progressBar);
		rootPanel.add(Box.createVerticalStrut(8));
		rootPanel.add(this.topFormPanel);
		rootPanel.add(Box.createVerticalStrut(8));
		rootPanel.add(this.bottomFormPanel);
		rootPanel.add(Box.createVerticalStrut(8));
		rootPanel.add(this.getButtonsPanel());
		contentPane.add(rootPanel);

		FormPanel bfp = this.bottomFormPanel;
		bfp.addField(this.destinationField);
		bfp.addField(this.transferRateField);
		bfp.addField(this.transferSizeField);

		FormPanel tfp = this.topFormPanel;
		tfp.addField(this.documentField);
		tfp.addField(this.mimeTypeField);
		tfp.addField(this.sizeField);
		tfp.addField(this.timeLeftField);

		Dimension topPanelPs = this.topFormPanel.getPreferredSize();
		this.topFormPanel.setPreferredSize(new Dimension(400, topPanelPs.height));

		Dimension bottomPanelPs = this.bottomFormPanel.getPreferredSize();
		this.bottomFormPanel.setPreferredSize(new Dimension(400, bottomPanelPs.height));

		this.progressBar.setEnabled(false);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				RequestHandler rh = requestHandler;
				if (rh != null) {
					rh.cancel();
					// So that there's no error dialog
					requestHandler = null;
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
		JButton saveButton = this.saveButton;
		saveButton.setAction(new SaveAction());
		saveButton.setText("Save As...");
		saveButton.setToolTipText("You must select a file before download begins.");

		JButton closeButton = this.closeButton;
		closeButton.setAction(new CloseAction());
		closeButton.setText("Cancel");

		JButton openButton = this.openButton;
		openButton.setAction(new OpenAction());
		openButton.setText("Open");

		JButton openFolderButton = this.openFolderButton;
		openFolderButton.setAction(new OpenFolderAction());
		openFolderButton.setText("Open Folder");

		Box box = new Box(BoxLayout.X_AXIS);
		// box.setBorder(new BevelBorder(BevelBorder.RAISED));
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
	 * Select file.
	 */
	private void selectFile() {
		String path = this.url.getPath();
		int lastSlashIdx = path.lastIndexOf('/');
		String tentativeName = lastSlashIdx == -1 ? path : path.substring(lastSlashIdx + 1);
		JFileChooser chooser = new JFileChooser();
		ToolsSettings settings = ToolsSettings.getInstance();
		File directory = settings.getDownloadDirectory();
		if (directory != null) {
			File selectedFile = new File(directory, tentativeName);
			chooser.setSelectedFile(selectedFile);
		}
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (file.exists()
					&& JOptionPane.showConfirmDialog(this, "The file exists. Are you sure you want to overwrite it?",
							"Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			settings.setDownloadDirectory(file.getParentFile());
			settings.save();
			this.startDownload(chooser.getSelectedFile());
		}
	}

	/**
	 * Start download.
	 *
	 * @param file
	 *            the file
	 */
	private void startDownload(File file) {
		this.saveButton.setEnabled(false);

		this.timeLeftField.setCaption("Time left:");

		this.destinationField.setValue(file.getName());
		this.destinationField.setToolTip(file.getAbsolutePath());

		this.bottomFormPanel.setEnabled(true);
		this.bottomFormPanel.revalidate();

		ClientletRequest request = new ClientletRequestImpl(this.url, RequestType.DOWNLOAD);
		RequestHandler handler = new DownloadRequestHandler(request, this, file);

		this.destinationFile = file;
		this.requestHandler = handler;
		this.downloadBaseTimestamp = System.currentTimeMillis();

		Thread t = new Thread(new DownloadRunnable(handler), "Download:" + this.url.toExternalForm());
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Done with download_ safe.
	 *
	 * @param totalSize
	 *            the total size
	 */
	private void doneWithDownloadSafe(final long totalSize) {
		if (SwingUtilities.isEventDispatchThread()) {
			doneWithDownload(totalSize);
		} else {

			SwingUtilities.invokeLater(() -> doneWithDownload(totalSize));
		}
	}

	/**
	 * Done with download.
	 *
	 * @param totalSize
	 *            the total size
	 */
	private void doneWithDownload(long totalSize) {
		this.requestHandler = null;

		this.setTitle(this.destinationField.getValue());
		this.timeLeftField.setCaption("Download time:");
		long elapsed = System.currentTimeMillis() - this.downloadBaseTimestamp;
		this.timeLeftField.setValue(Timing.getElapsedText(elapsed));

		String sizeText = getSizeText(totalSize);
		this.transferSizeField.setValue(sizeText);
		this.sizeField.setValue(sizeText);

		if (elapsed > 0) {
			double transferRate = (double) totalSize / elapsed;
			this.transferRateField.setValue(round1(transferRate) + " Kb/sec");
		} else {
			this.transferRateField.setValue("N/A");
		}

		this.progressBar.setIndeterminate(false);
		this.progressBar.setStringPainted(true);
		this.progressBar.setValue(100);
		this.progressBar.setMaximum(100);
		this.progressBar.setString("Done.");

		this.closeButton.setText("Close");

		if (OS.supportsLaunchPath()) {
			this.saveButton.setVisible(false);
			this.openFolderButton.setVisible(true);
			this.openButton.setVisible(true);
			this.openButton.revalidate();
		}
	}

	/**
	 * Error in download_ safe.
	 */
	private void errorInDownloadSafe() {
		if (SwingUtilities.isEventDispatchThread()) {
			errorInDownload();
		} else {
			SwingUtilities.invokeLater(() -> errorInDownload());
		}
	}

	/**
	 * Error in download.
	 */
	private void errorInDownload() {
		if (this.requestHandler != null) {
			// If requestHandler is null, it means the download was explicitly
			// cancelled or the window closed.
			JOptionPane.showMessageDialog(this, "An error occurred while trying to download the file.");
			this.dispose();
		}
	}

	/**
	 * Round1.
	 *
	 * @param value
	 *            the value
	 * @return the double
	 */
	private static double round1(double value) {
		return Math.round(value * 10.0) / 10.0;
	}

	/**
	 * Gets the size text.
	 *
	 * @param numBytes
	 *            the num bytes
	 * @return the size text
	 */
	private static String getSizeText(long numBytes) {
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
	 * Update progress_ safe.
	 *
	 * @param progressType
	 *            the progress type
	 * @param value
	 *            the value
	 * @param max
	 *            the max
	 */
	private void updateProgressSafe(final ProgressType progressType, final int value, final int max) {
		if (SwingUtilities.isEventDispatchThread()) {
			updateProgress(progressType, value, max);
		} else {
			SwingUtilities.invokeLater(() -> updateProgress(progressType, value, max));
		}
	}

	/**
	 * Update progress.
	 *
	 * @param progressType
	 *            the progress type
	 * @param value
	 *            the value
	 * @param max
	 *            the max
	 */
	private void updateProgress(ProgressType progressType, int value, int max) {
		String sizeText = getSizeText(value);
		this.transferSizeField.setValue(sizeText);

		long newTimestamp = System.currentTimeMillis();
		double lastTransferRate = this.lastTransferRate;
		long lastProgressValue = this.lastProgressValue;
		long lastTimestamp = this.lastTimestamp;
		long elapsed = newTimestamp - lastTimestamp;
		double newTransferRate = Double.NaN;
		if (elapsed > 0) {
			newTransferRate = (value - lastProgressValue) / elapsed;
			if (!Double.isNaN(lastTransferRate)) {
				// Weighed average
				newTransferRate = (newTransferRate + lastTransferRate * 5.0) / 6.0;
			}
		}
		if (!Double.isNaN(newTransferRate)) {
			this.transferRateField.setValue(round1(newTransferRate) + " Kb/sec");
			int cl = this.knownContentLength;
			if (cl > 0 && newTransferRate > 0) {
				this.timeLeftField.setValue(Timing.getElapsedText((long) ((cl - value) / newTransferRate)));
			}
		}
		this.lastTimestamp = newTimestamp;
		this.lastProgressValue = value;
		this.lastTransferRate = newTransferRate;

		JProgressBar pb = this.progressBar;
		if (progressType == ProgressType.CONNECTING) {
			pb.setIndeterminate(true);
			pb.setStringPainted(true);
			pb.setString("Connecting...");
			this.setTitle(this.destinationField.getValue() + ": Connecting...");
		} else if (max <= 0) {
			pb.setIndeterminate(true);
			pb.setStringPainted(false);
			this.setTitle(sizeText + " " + this.destinationField.getValue());
		} else {
			int percent = value * 100 / max;
			pb.setIndeterminate(false);
			pb.setStringPainted(true);
			pb.setMaximum(max);
			pb.setValue(value);
			String percentText = percent + "%";
			pb.setString(percentText);
			this.setTitle(percentText + " " + this.destinationField.getValue());
		}
	}

	/**
	 * The Class SaveAction.
	 */
	private class SaveAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			selectFile();
		}
	}

	/**
	 * The Class OpenFolderAction.
	 */
	private class OpenFolderAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			File file = destinationFile;
			if (file != null) {
				try {
					OS.launchPath(file.getParentFile().getAbsolutePath());
				} catch (Exception thrown) {
					logger.error("Unable to open folder of file: " + file + ".", thrown);
					JOptionPane.showMessageDialog(DownloadDialog.this, "An error occurred trying to open the folder.");
				}
			}
		}
	}

	/**
	 * The Class OpenAction.
	 */
	private class OpenAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			File file = destinationFile;
			if (file != null) {
				try {
					OS.launchPath(file.getAbsolutePath());
					DownloadDialog.this.dispose();
				} catch (Exception thrown) {
					logger.error("Unable to open file: " + file + ".", thrown);
					JOptionPane.showMessageDialog(DownloadDialog.this, "An error occurred trying to open the file.");
				}
			}
		}
	}

	/**
	 * The Class CloseAction.
	 */
	private class CloseAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// windowClosedEvent takes care of cancelling download.
			DownloadDialog.this.dispose();
		}
	}

	/**
	 * The Class DownloadRunnable.
	 */
	private class DownloadRunnable implements Runnable {

		/** The handler. */
		private final RequestHandler handler;

		/**
		 * Instantiates a new download runnable.
		 *
		 * @param handler
		 *            the handler
		 */
		public DownloadRunnable(RequestHandler handler) {
			this.handler = handler;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				RequestEngine.getInstance().inlineRequest(this.handler);
			} catch (Exception err) {
				logger.log(Level.ERROR, "Unexpected error on download of [" + url.toExternalForm() + "].", err);
			}
		}
	}

	/**
	 * The Class DownloadRequestHandler.
	 */
	private class DownloadRequestHandler extends AbstractRequestHandler {

		/** The file. */
		private final File file;

		/** The download done. */
		private boolean downloadDone = false;

		/** The last progress update. */
		private long lastProgressUpdate = 0;

		/**
		 * Instantiates a new download request handler.
		 *
		 * @param request
		 *            the request
		 * @param dialogComponent
		 *            the dialog component
		 * @param file
		 *            the file
		 */
		public DownloadRequestHandler(ClientletRequest request, Component dialogComponent, File file) {
			super(request, dialogComponent);
			this.file = file;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.request.AbstractRequestHandler#handleException(org.
		 * lobobrowser .clientlet.ClientletResponse, java.lang.Throwable)
		 */
		@Override
		public boolean handleException(ClientletResponse response, Throwable exception) throws ClientletException {
			logger.error("An error occurred trying to download " + response.getResponseURL() + " to " + this.file + ".",
					exception);
			errorInDownloadSafe();
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.request.AbstractRequestHandler#handleProgress(org.
		 * lobobrowser .ua.ProgressType, java.net.URL, java.lang.String, int,
		 * int)
		 */
		@Override
		public void handleProgress(ProgressType progressType, URL url, String method, int value, int max) {
			if (!this.downloadDone) {
				long timestamp = System.currentTimeMillis();
				if (timestamp - this.lastProgressUpdate > 1000) {
					updateProgressSafe(progressType, value, max);
					this.lastProgressUpdate = timestamp;
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.request.AbstractRequestHandler#processResponse(org.
		 * lobobrowser .clientlet.ClientletResponse)
		 */
		@Override
		public void processResponse(ClientletResponse response) throws ClientletException, IOException {
			OutputStream out = new FileOutputStream(this.file);
			try {
				InputStream in = response.getInputStream();
				try {
					int totalRead = 0;
					byte[] buffer = new byte[8192];
					int numRead;
					while ((numRead = in.read(buffer)) != -1) {
						if (this.isCancelled()) {
							throw new IOException("cancelled");
						}
						totalRead += numRead;
						out.write(buffer, 0, numRead);
					}
					this.downloadDone = true;
					doneWithDownloadSafe(totalRead);
				} finally {
					in.close();
				}
			} finally {
				out.close();
			}
		}
	}
}
