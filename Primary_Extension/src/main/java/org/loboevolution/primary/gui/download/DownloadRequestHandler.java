package org.loboevolution.primary.gui.download;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.clientlet.ClientletException;
import org.loboevolution.clientlet.ClientletRequest;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.request.AbstractRequestHandler;
import org.loboevolution.ua.ProgressType;
import org.loboevolution.util.OS;
import org.loboevolution.util.Timing;

/**
 * The Class DownloadRequestHandler.
 */
public class DownloadRequestHandler extends AbstractRequestHandler {

	private static final Logger logger = LogManager.getLogger(DownloadRequestHandler.class);

	/** The file. */
	private final File file;

	/** The download done. */
	private boolean downloadDone = false;

	/** The last progress update. */
	private long lastProgressUpdate = 0;

	/** The last timestamp. */
	private long lastTimestamp;

	/** The last progress value. */
	private long lastProgressValue;

	/** The last transfer rate. */
	private double lastTransferRate = Double.NaN;

	private DownloadDialog download;

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
	public DownloadRequestHandler(ClientletRequest request, Component dialogComponent, File file,
			DownloadDialog download) {
		super(request, dialogComponent);
		this.file = file;
		this.download = download;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.AbstractRequestHandler#handleException(org.
	 * loboevolution .clientlet.ClientletResponse, java.lang.Throwable)
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
	 * @see org.loboevolution.request.AbstractRequestHandler#handleProgress(org.
	 * loboevolution .ua.ProgressType, java.net.URL, java.lang.String, int, int)
	 */
	@Override
	public void handleProgress(ProgressType progressType, URL url, String method, int value, int max) {
		if (!downloadDone) {
			long timestamp = System.currentTimeMillis();
			if (timestamp - lastProgressUpdate > 1000) {
				updateProgressSafe(progressType, value, max);
				lastProgressUpdate = timestamp;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.AbstractRequestHandler#processResponse(org.
	 * loboevolution .clientlet.ClientletResponse)
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
					if (isCancelled()) {
						throw new IOException("cancelled");
					}
					totalRead += numRead;
					out.write(buffer, 0, numRead);
				}
				downloadDone = true;
				doneWithDownloadSafe(totalRead);
			} finally {
				in.close();
			}
		} finally {
			out.close();
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
		if (download.getRequestHandler() != null) {
			JOptionPane.showMessageDialog(download, "An error occurred while trying to download the file.");
			download.dispose();
		}
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
		download.setRequestHandler(null);

		download.setTitle(download.getDestinationField().getValue());
		download.getTimeLeftField().setCaption("Download time:");
		long elapsed = System.currentTimeMillis() - download.getDownloadBaseTimestamp();
		download.getTimeLeftField().setValue(Timing.getElapsedText(elapsed));

		String sizeText = download.getSizeText(totalSize);
		download.getTransferSizeField().setValue(sizeText);
		download.getSizeField().setValue(sizeText);

		if (elapsed > 0) {
			double transferRate = (double) totalSize / elapsed;
			download.getTransferRateField().setValue(download.round1(transferRate) + " Kb/sec");
		} else {
			download.getTransferRateField().setValue("N/A");
		}

		download.getProgressBar().setIndeterminate(false);
		download.getProgressBar().setStringPainted(true);
		download.getProgressBar().setValue(100);
		download.getProgressBar().setMaximum(100);
		download.getProgressBar().setString("Done.");

		download.getCloseButton().setText("Close");

		if (OS.supportsLaunchPath()) {
			download.getSaveButton().setVisible(false);
			download.getOpenFolderButton().setVisible(true);
			download.getOpenButton().setVisible(true);
			download.getOpenButton().revalidate();
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
		String sizeText = download.getSizeText(value);
		download.getTransferSizeField().setValue(sizeText);

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
			download.getTransferRateField().setValue(download.round1(newTransferRate) + " Kb/sec");
			int cl = download.getKnownContentLength();
			if (cl > 0 && newTransferRate > 0) {
				download.getTimeLeftField().setValue(Timing.getElapsedText((long) ((cl - value) / newTransferRate)));
			}
		}
		lastTimestamp = newTimestamp;
		lastProgressValue = value;
		lastTransferRate = newTransferRate;

		JProgressBar pb = download.getProgressBar();
		if (progressType == ProgressType.CONNECTING) {
			pb.setIndeterminate(true);
			pb.setStringPainted(true);
			pb.setString("Connecting...");
			download.setTitle(download.getDestinationField().getValue() + ": Connecting...");
		} else if (max <= 0) {
			pb.setIndeterminate(true);
			pb.setStringPainted(false);
			download.setTitle(sizeText + " " + download.getDestinationField().getValue());
		} else {
			int percent = value * 100 / max;
			pb.setIndeterminate(false);
			pb.setStringPainted(true);
			pb.setMaximum(max);
			pb.setValue(value);
			String percentText = percent + "%";
			pb.setString(percentText);
			download.setTitle(percentText + " " + download.getDestinationField().getValue());
		}
	}
}
