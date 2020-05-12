/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
package org.loboevolution.html.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.laf.IconFactory;
import org.loboevolution.net.HttpNetwork;

/**
 * The Class HtmlContextMenu.
 *
 * @author utente
 * @version $Id: $Id
 */
public class HtmlContextMenu {

	/** The element. */
	private HTMLElement element;

	/** The context. */
	private HtmlRendererContext context;

	/** The search. */
	private static final String SEARCH = "/org/lobo/image/search.png";

	/** The copy. */
	private static final String COPY = "/org/lobo/image/copy.png";

	/** The save. */
	private static final String SAVE = "/org/lobo/image/save.png";

	/** The back. */
	private static final String BACK = "/org/lobo/image/back.png";

	/** The relod. */
	private static final String RELOD = "/org/lobo/image/reload.png";

	/** The forward. */
	private static final String FORWARD = "/org/lobo/image/forward.png";

	/**
	 * Instantiates a new html context menu.
	 *
	 * @param element
	 *            the element
	 * @param context
	 *            the context
	 */
	public HtmlContextMenu(HTMLElement element, HtmlRendererContext context) {
		this.element = element;
		this.context = context;
	}

	/**
	 * Popup menu image.
	 *
	 * @return the j popup menu
	 */
	public JPopupMenu popupMenuImage() {

		JPopupMenu popupMenu = new JPopupMenu();
		final HTMLImageElementImpl img = (HTMLImageElementImpl) element;
		final String href = img.getSrc();
		JMenuItem menuItem = new JMenuItem("View image");
		menuItem.setIcon(IconFactory.getInstance().getIcon(SEARCH));
		menuItem.addActionListener(e -> {
			if (href.contains(";base64,")) {
				final String base64 = href.split(";base64,")[1];
				byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
				try (InputStream stream = new ByteArrayInputStream(decodedBytes);) {
					context.openImageViewer(href, stream);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					context.openImageViewer(img.getFullURL(href));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		popupMenu.add(menuItem);

		JMenuItem copyImageURL = new JMenuItem("Copy Image URL");
		copyImageURL.setIcon(IconFactory.getInstance().getIcon(COPY));
		copyImageURL.addActionListener(e -> {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			String clip = "";
			if (!href.contains(";base64,")) {
				try {
					clip = img.getFullURL(href).toExternalForm();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				clip = href;
			}
			clipboard.setContents(new StringSelection(clip), null);
		});
		popupMenu.add(copyImageURL);

		JMenuItem saveImage = new JMenuItem("Save Image");
		saveImage.setIcon(IconFactory.getInstance().getIcon(SAVE));
		saveImage.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileChooser.setAcceptAllFileFilterUsed(true);
			int returnValue = fileChooser.showSaveDialog(context.getHtmlPanel());
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = getSelectedFileWithExtension(fileChooser);
				if (selectedFile.exists()) {
					int response = JOptionPane.showConfirmDialog(null, "Overwrite existing file?", "Confirm Overwrite",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				BufferedImage image = null;
				try {

					if (!href.contains(";base64,")) {
						URL srcUrl = img.getFullURL(href);
						int dot = srcUrl.toExternalForm().lastIndexOf('.');
						String ext = srcUrl.toExternalForm().substring(dot + 1);
						image = ImageIO.read(srcUrl);
						ImageIO.write(image, ext, selectedFile);
					} else {
						final String base64 = href.split(";base64,")[1];
						byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
						try (InputStream stream = new ByteArrayInputStream(decodedBytes);) {
							image = ImageIO.read(stream);
							ImageIO.write(image, href, selectedFile);
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		popupMenu.add(saveImage);
		return popupMenu;
	}

	/**
	 * Popup menu link.
	 *
	 * @return the j popup menu
	 */
	public JPopupMenu popupMenuLink() {

		JPopupMenu popupMenu = new JPopupMenu();
		HTMLLinkElementImpl link = (HTMLLinkElementImpl) element;
		JMenuItem menuItem = new JMenuItem("Open link in new tab");
		menuItem.setIcon(IconFactory.getInstance().getIcon(SEARCH));
		menuItem.addActionListener(e -> {
			try {
				final URL url = new URL(link.getAbsoluteHref());
				context.linkClicked(url, true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		popupMenu.add(menuItem);
		JMenuItem copyLinkURL = new JMenuItem("Copy link URL");
		copyLinkURL.setIcon(IconFactory.getInstance().getIcon(COPY));
		copyLinkURL.addActionListener(e -> {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(link.getAbsoluteHref()), null);
		});
		popupMenu.add(copyLinkURL);
		JMenuItem saveImage = new JMenuItem("Save destination");
		saveImage.setIcon(IconFactory.getInstance().getIcon(SAVE));
		saveImage.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileChooser.setAcceptAllFileFilterUsed(true);
			int returnValue = fileChooser.showSaveDialog(context.getHtmlPanel());
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = getSelectedFileWithExtension(fileChooser);
				if (selectedFile.exists()) {
					int response = JOptionPane.showConfirmDialog(null, "Overwrite existing file?", "Confirm Overwrite",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				BufferedImage image = null;

				try {
					int dot = link.getAbsoluteHref().lastIndexOf(".");
					String ext = link.getAbsoluteHref().substring(dot + 1);
					image = ImageIO.read(new URL(link.getAbsoluteHref()));
					ImageIO.write(image, ext, selectedFile);
				} catch (Exception e1) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					try {
						baos.write(HttpNetwork.getSource(link.getAbsoluteHref()).getBytes());
						OutputStream ops = new FileOutputStream(selectedFile);
						baos.writeTo(ops);
						baos.flush();
					} catch (Exception e2) {
						e1.printStackTrace();
					} finally {
						try {
							baos.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});
		popupMenu.add(saveImage);
		return popupMenu;
	}

	/**
	 * Popup menu abstract ui.
	 *
	 * @return the j popup menu
	 */
	public JPopupMenu popupMenuAbstractUI() {

		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem menuBack = new JMenuItem("Back");
		menuBack.setIcon(IconFactory.getInstance().getIcon(BACK));
		menuBack.addActionListener(e -> {
			context.back();
		});

		if (Strings.isNotBlank(context.getPreviousURL())) {
			menuBack.setEnabled(true);
		} else {
			menuBack.setEnabled(false);
		}

		popupMenu.add(menuBack);

		JMenuItem menuReload = new JMenuItem("Reload");
		menuReload.setIcon(IconFactory.getInstance().getIcon(RELOD));
		menuReload.addActionListener(e -> {
			context.reload();
		});
		popupMenu.add(menuReload);

		JMenuItem menuForward = new JMenuItem("Forward");
		menuForward.setIcon(IconFactory.getInstance().getIcon(FORWARD));
		menuForward.addActionListener(e -> {
			context.forward();
		});

		if (Strings.isNotBlank(context.getNextURL())) {
			menuForward.setEnabled(true);
		} else {
			menuForward.setEnabled(false);
		}
		popupMenu.add(menuForward);

		return popupMenu;

	}

	/**
	 * Gets the selected file with extension.
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
			for (String ext : exts) {
				if (nameLower.endsWith('.' + ext.toLowerCase())) {
					return file;
				}
			}
			file = new File(file.toString() + exts[0]);
		}
		return file;
	}
}
