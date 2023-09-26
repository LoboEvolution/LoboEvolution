/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.menu.file;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

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
 *
 *
 */
public class SaveFileAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for SaveFileAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public SaveFileAction(final BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
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

			try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				final String source = HttpNetwork.getSource(toolbar.getAddressBar().getText());
				baos.write(source.getBytes());
				final OutputStream ops = Files.newOutputStream(selectedFile.toPath());
				baos.writeTo(ops);
				baos.flush();
			} catch (final Exception e1) {
				e1.printStackTrace();
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
	private File getSelectedFileWithExtension(final JFileChooser c) {
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
