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
package org.lobobrowser.primary.screenshot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class ScreenShootFrame.
 */
public class ScreenShootFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The image. */
	private BufferedImage image;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ScreenShootFrame.class);

	public ScreenShootFrame(JPanel panelframe) {
		setTitle("Screenshot");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(700, 600));

		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(saveAction);
		mb.add(file);
		setJMenuBar(mb);

		JFrame win = (JFrame) SwingUtilities.getWindowAncestor(panelframe);
		JPanel panel = new JPanel();
		Dimension size = win.getSize();
		image = (BufferedImage) win.createImage(size.width, size.height);
		Graphics g = image.getGraphics();
		win.paint(g);
		ImageIcon pic = new ImageIcon(image);
		Image newimg = image.getScaledInstance(650, 500, Image.SCALE_SMOOTH);
		pic = new ImageIcon(newimg);
		panel.add(new JLabel(pic));
		add(panel);
		revalidate();
		repaint();
		pack();
		this.setVisible(true);
	}

	/** The save action. */
	private Action saveAction = new AbstractAction("Save") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent evt) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			FileNameExtensionFilter filterjpg = new FileNameExtensionFilter("Joint Photographic Experts Group(.jpg)",
					".jpg");
			fileChooser.setFileFilter(filterjpg);
			fileChooser.setAcceptAllFileFilterUsed(true);
			int returnValue = fileChooser.showOpenDialog(ScreenShootFrame.this);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = getSelectedFileWithExtension(fileChooser);
				if (selectedFile.exists()) {
					int response = JOptionPane.showConfirmDialog(null, "Overwrite existing file?", "Confirm Overwrite",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				try {
					ImageIO.write(image, "jpg", new File(selectedFile.getAbsolutePath()));
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	};

	/**
	 * Returns the selected file from a JFileChooser, including the extension
	 * from the file filter.
	 *
	 * @param c
	 *            the c
	 * @return the selected file with extension
	 */
	private File getSelectedFileWithExtension(JFileChooser c) {
		File file = c.getSelectedFile();
		if (c.getFileFilter() instanceof FileNameExtensionFilter) {
			String[] exts = ((FileNameExtensionFilter) c.getFileFilter()).getExtensions();
			String nameLower = file.getName().toLowerCase();
			for (String ext : exts) { // check if it already has a valid
				// extension
				if (nameLower.endsWith('.' + ext.toLowerCase())) {
					return file; // if yes, return as-is
				}
			}
			// if not, append the first extension from the selected filter
			file = new File(file.toString() + exts[0]);
		}
		return file;
	}

}
