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
package org.loboevolution.gui;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.IDownload;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.laf.IconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;

/**
 * The Class HtmlContextMenu.
 *
 *
 *
 */
public class HtmlContextMenu {

	private static final Logger logger = Logger.getLogger(HtmlContextMenu.class.getName());

	/** The element. */
	private final HTMLElement element;

	/** The context. */
	private final HtmlRendererContext context;

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
	 * @param bpanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public JPopupMenu popupMenuImage(IBrowserPanel bpanel) {

		JPopupMenu popupMenu = new JPopupMenu();
		final HTMLImageElementImpl img = (HTMLImageElementImpl) element;
		final String href = img.getSrc();
		JMenuItem menuItem = new JMenuItem("View image");
		menuItem.setIcon(IconFactory.getInstance().getIcon(SEARCH));
		menuItem.addActionListener(e -> {
			if (href.contains(";base64,")) {
				final String base64 = href.split(";base64,")[1];
				byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
				try (InputStream stream = new ByteArrayInputStream(decodedBytes)) {
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
			try {
				IDownload d = bpanel.getBrowserFrame().getDownload();
				if (!href.contains(";base64,")) {
					URL scriptURL = Urls.createURL(new URL(img.getBaseURI()), href);
					d.downloadFile(scriptURL);
				} else {
					final String base64 = href.split(";base64,")[1];
					byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
					d.downloadFile(new ByteArrayInputStream(decodedBytes));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		popupMenu.add(saveImage);
		return popupMenu;
	}

	/**
	 * Popup menu link.
	 *
	 * @return the j popup menu
	 * @param bpanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public JPopupMenu popupMenuLink(IBrowserPanel bpanel) {
		JPopupMenu popupMenu = new JPopupMenu();
		HTMLLinkElementImpl link = (HTMLLinkElementImpl) element;
		JMenuItem menuItem = new JMenuItem("Open link in new tab");
		menuItem.setIcon(IconFactory.getInstance().getIcon(SEARCH));
		menuItem.addActionListener(e -> {
			try {
				final URL url = new URL(link.getAbsoluteHref());
				context.linkClicked(url, true);
			} catch (Exception e0) {
				logger.severe(e0.getMessage());
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
			try {
				IDownload d = bpanel.getBrowserFrame().getDownload();
				d.downloadFile(new URL(link.getAbsoluteHref()));
			} catch (Exception e1) {
				e1.printStackTrace();
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
		menuBack.addActionListener(e -> context.back());

		menuBack.setEnabled(Strings.isNotBlank(context.getPreviousURL()));

		popupMenu.add(menuBack);

		JMenuItem menuReload = new JMenuItem("Reload");
		menuReload.setIcon(IconFactory.getInstance().getIcon(RELOD));
		menuReload.addActionListener(e -> context.reload());
		popupMenu.add(menuReload);

		JMenuItem menuForward = new JMenuItem("Forward");
		menuForward.setIcon(IconFactory.getInstance().getIcon(FORWARD));
		menuForward.addActionListener(e -> context.forward());

		menuForward.setEnabled(Strings.isNotBlank(context.getNextURL()));
		popupMenu.add(menuForward);

		return popupMenu;

	}
}
