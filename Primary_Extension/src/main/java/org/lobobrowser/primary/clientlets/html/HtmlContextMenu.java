/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.clientlets.html;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lobobrowser.html.domimpl.HTMLAnchorElementImpl;
import org.lobobrowser.html.domimpl.HTMLImageElementImpl;
import org.lobobrowser.primary.ext.IconFactory;
import org.lobobrowser.w3c.html.HTMLElement;

/**
 * The Class HtmlContextMenu.
 */
public class HtmlContextMenu {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HtmlContextMenu.class);

	/** The element. */
	private HTMLElement element;

	/** The context. */
	private HtmlRendererContextImpl context;

	/** The search. */
	private final String SEARCH = "/org/lobobrowser/images/search.png";

	/** The copy. */
	private final String COPY = "/org/lobobrowser/images/copy.png";

	/** The save. */
	private final String SAVE = "/org/lobobrowser/images/save.png";

	/** The back. */
	private final String BACK = "/org/lobobrowser/images/back.png";

	/** The relod. */
	private final String RELOD = "/org/lobobrowser/images/reload.png";

	/** The forward. */
	private final String FORWARD = "/org/lobobrowser/images/forward.png";

	/**
	 * Instantiates a new html context menu.
	 *
	 * @param element
	 *            the element
	 * @param context
	 *            the context
	 */
	public HtmlContextMenu(HTMLElement element, HtmlRendererContextImpl context) {
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
		HTMLImageElementImpl img = (HTMLImageElementImpl) element;
		URL srcUrl;
		try {
			srcUrl = img.getFullURL(img.getSrc());

			JMenuItem menuItem = new JMenuItem("View image");
			menuItem.setIcon(IconFactory.getInstance().getIcon(SEARCH));
			menuItem.addActionListener(e -> {
				context.navigate(srcUrl, null);
			});
			popupMenu.add(menuItem);

			JMenuItem copyImageURL = new JMenuItem("Copy Image URL");

			copyImageURL.setIcon(IconFactory.getInstance().getIcon(COPY));
			copyImageURL.addActionListener(e -> {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(srcUrl.toExternalForm()), null);
			});
			popupMenu.add(copyImageURL);

			JMenuItem saveImage = new JMenuItem("Save Image");

			saveImage.setIcon(IconFactory.getInstance().getIcon(SAVE));
			saveImage.addActionListener(e -> {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.setAcceptAllFileFilterUsed(true);

				int returnValue = fileChooser.showSaveDialog(context.getClientletFrame().getComponent());

				if (returnValue == JFileChooser.APPROVE_OPTION) {

					File selectedFile = getSelectedFileWithExtension(fileChooser);
					if (selectedFile.exists()) {
						int response = JOptionPane.showConfirmDialog(null, "Overwrite existing file?",
								"Confirm Overwrite", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.CANCEL_OPTION) {
							return;
						}
					}
					BufferedImage image = null;

					try {
						int dot = srcUrl.toExternalForm().lastIndexOf(".");
						String ext = srcUrl.toExternalForm().substring(dot + 1);
						image = ImageIO.read(srcUrl);
						ImageIO.write(image, ext, selectedFile);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
				}
			});
			popupMenu.add(saveImage);

		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		}
		return popupMenu;

	}

	/**
	 * Popup menu link.
	 *
	 * @return the j popup menu
	 */
	public JPopupMenu popupMenuLink() {

		JPopupMenu popupMenu = new JPopupMenu();
		HTMLAnchorElementImpl link = (HTMLAnchorElementImpl) element;
		JMenuItem menuItem = new JMenuItem("Open link in new window");
		menuItem.setIcon(IconFactory.getInstance().getIcon(SEARCH));
		menuItem.addActionListener(e -> {
			context.open(link.getAbsoluteHref(), "window", null, false);
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

			int returnValue = fileChooser.showSaveDialog(context.getClientletFrame().getComponent());

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
						baos.write(urlContent(link.getAbsoluteHref()).getBytes());
						OutputStream ops = new FileOutputStream(selectedFile);
						baos.writeTo(ops);
						baos.flush();
					} catch (IOException e2) {
						logger.error(e1.getMessage());
					} finally {
						try {
							baos.close();
						} catch (IOException e2) {
							logger.error(e1.getMessage());
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

		if (context.getPreviousURL() != null && context.getPreviousURL().length() > 0) {
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

		if (context.getNextURL() != null && context.getNextURL().length() > 0) {
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

	/**
	 * Url content.
	 *
	 * @param webPage
	 *            the web page
	 * @return the string
	 */
	private String urlContent(String webPage) {

		String result = "";
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		InputStreamReader isr = null;

		try {
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			is = urlConnection.getInputStream();
			isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];

			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				isr.close();
				is.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

}
