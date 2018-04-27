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

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.loboevolution.clientlet.ClientletRequest;
import org.loboevolution.primary.settings.ToolsSettings;
import org.loboevolution.request.ClientletRequestImpl;
import org.loboevolution.request.RequestHandler;
import org.loboevolution.ua.RequestType;

public class SaveAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private DownloadDialog download;

	public SaveAction(DownloadDialog download) {
		this.download = download;
	}

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

	/**
	 * Select file.
	 */
	private void selectFile() {
		String path = download.getUrl().getPath();
		int lastSlashIdx = path.lastIndexOf('/');
		String tentativeName = lastSlashIdx == -1 ? path : path.substring(lastSlashIdx + 1);
		JFileChooser chooser = new JFileChooser();
		ToolsSettings settings = ToolsSettings.getInstance();
		File directory = settings.getDownloadDirectory();
		if (directory != null) {
			File selectedFile = new File(directory, tentativeName);
			chooser.setSelectedFile(selectedFile);
		}
		if (chooser.showSaveDialog(download) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (file.exists()
					&& JOptionPane.showConfirmDialog(download, "The file exists. Are you sure you want to overwrite it?",
							"Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			settings.setDownloadDirectory(file.getParentFile());
			settings.save();
			startDownload(chooser.getSelectedFile());
		}
	}

	/**
	 * Start download.
	 *
	 * @param file
	 *            the file
	 */
	private void startDownload(File file) {
		download.getSaveButton().setEnabled(false);

		download.getTimeLeftField().setCaption("Time left:");

		download.getDestinationField().setValue(file.getName());
		download.getDestinationField().setToolTip(file.getAbsolutePath());

		download.getBottomFormPanel().setEnabled(true);
		download.getBottomFormPanel().revalidate();

		ClientletRequest request = new ClientletRequestImpl(download.getUrl(), RequestType.DOWNLOAD);
		RequestHandler handler = new DownloadRequestHandler(request, download, file, download);

		download.setDestinationFile(file);
		download.setRequestHandler(handler);
		download.setDownloadBaseTimestamp(System.currentTimeMillis());

		Thread t = new Thread(new DownloadRunnable(handler, download), "Download:" + download.getUrl().toExternalForm());
		t.setDaemon(true);
		t.start();
	}	
}