package org.loboevolution.component;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.loboevolution.common.Strings;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.img.ImageViewer;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;

public class GoAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final JTextField addressBar;

	private final IBrowserPanel panel;

	public GoAction(IBrowserPanel panel, JTextField addressBar) {
		this.panel = panel;
		this.addressBar = addressBar;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		try {
			final String url = this.addressBar.getText();
			final BufferedImage image = ImageIO.read(new URL(url));
			if (image != null) {
				goUrlImage(image, url);
			} else {
				goURl(url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void goURl(String text) {
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this.panel));
		final int indexPanel = tabbedPane.getSelectedIndex();
		final HtmlPanel htmlPanel = NavigationManager.getHtmlPanel(text, indexPanel);
		htmlPanel.setBrowserPanel(panel);

		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		this.addressBar.setText(text);
		panel.getScroll().getViewport().add(tabbedPane);

		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, text, title);
		addressBar.removeAll();
		Autocomplete.setupAutoComplete(addressBar, TabStore.getUrls());

	}

	private void goUrlImage(BufferedImage img, String fullURL) {
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		final ImageViewer viewer = new ImageViewer(img);
		final String title = "Image Viewer";
		final int indexPanel = tabbedPane.getSelectedIndex();
		JPanel jPanel = new JPanel();
		jPanel.add(viewer.getComponent());
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, viewer.getComponent(), title, indexPanel);
		this.addressBar.setText(fullURL);
		panel.getScroll().getViewport().add(tabbedPane);
		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, fullURL, title);
		addressBar.removeAll();
	}
}