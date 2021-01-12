/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.menu.file;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>SaveFileAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SaveFileAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for SaveFileAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public SaveFileAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		final ToolBar toolbar = this.frame.getToolbar();
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		final FileNameExtensionFilter filterHtml = new FileNameExtensionFilter("Hyper Text Markup Language", ".html");
		final FileNameExtensionFilter filterTxt = new FileNameExtensionFilter("Text", ".txt");

		fileChooser.setFileFilter(filterHtml);
		fileChooser.setFileFilter(filterTxt);
		fileChooser.setAcceptAllFileFilterUsed(true);

		final int returnValue = fileChooser.showSaveDialog(toolbar);

		if (returnValue == JFileChooser.APPROVE_OPTION) {

			final File selectedFile = getSelectedFileWithExtension(fileChooser);
			if (selectedFile.exists()) {
				final int response = JOptionPane.showConfirmDialog(null, "Overwrite existing file?",
						"Confirm Overwrite", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				final String source = HttpNetwork.getSource(toolbar.getAddressBar().getText());
				baos.write(source.getBytes());
				final OutputStream ops = new FileOutputStream(selectedFile);
				baos.writeTo(ops);
				baos.flush();
			} catch (final Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					baos.close();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	/**
	 * Returns the selected file from a JFileChooser, including the extension from
	 * the file filter.
	 *
	 * @param c the c
	 * @return the selected file with extension
	 */
	private File getSelectedFileWithExtension(JFileChooser c) {
		File file = c.getSelectedFile();
		if (c.getFileFilter() instanceof FileNameExtensionFilter) {
			final String[] exts = ((FileNameExtensionFilter) c.getFileFilter()).getExtensions();
			final String nameLower = file.getName().toLowerCase();
			for (final String ext : exts) {
				if (nameLower.endsWith('.' + ext.toLowerCase())) {
					return file;
				}
			}
			file = new File(file.toString() + exts[0]);
		}
		return file;
	}
}
