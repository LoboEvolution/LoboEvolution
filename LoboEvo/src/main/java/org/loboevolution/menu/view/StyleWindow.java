/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.view;

import com.jtattoo.plaf.lobo.LoboCheckBox;
import com.jtattoo.plaf.lobo.LoboPanel;
import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.StyleStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>StyleWindow class.</p>
 *
 *
 *
 */
public class StyleWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ClearstyleWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public StyleWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		JScrollPane spViewallItems = new JScrollPane();
		spViewallItems.setBorder(null);
		spViewallItems.setViewportView(getStyleBox(frame));
		add(spViewallItems);
	}

	private Component getStyleBox(BrowserFrame frame) {
		final LoboPanel styleBox = new LoboPanel("Choice Style");
		styleBox.setLayout(new BoxLayout(styleBox, BoxLayout.Y_AXIS));
		List<String> list = createListModel(frame) ;
		list.forEach( name -> styleBox.add(getCheckBox(frame, name)));
		return styleBox;
	}

	private Component getCheckBox(BrowserFrame frame, String name) {
		LoboCheckBox checkbox = new LoboCheckBox(name);
		checkbox.setActionCommand(name);
		checkbox.setToolTipText("Open current site with style " + name);
		ChangeListener changeListener = changeEvent -> {
			AbstractButton abstractButton = (AbstractButton) changeEvent.getSource();
			ButtonModel buttonModel = abstractButton.getModel();
			if (buttonModel.isPressed() && buttonModel.isSelected()) {
				String fullURL = frame.getToolbar().getAddressBar().getText();
				StyleStore style = new StyleStore();
				style.selectStyle(buttonModel.getActionCommand());

				final IBrowserPanel panel = frame.getPanel();
				final DnDTabbedPane tabbedPane = panel.getTabbedPane();
				tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
				final int indexPanel = tabbedPane.getSelectedIndex();
				HtmlPanel htmlPanel = HtmlPanel.createHtmlPanel(panel, fullURL);
				final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
				final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
				tabbedPane.remove(indexPanel);
				tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
				NavigationManager.insertHistory(fullURL, title, indexPanel);
				panel.getScroll().getViewport().add(tabbedPane);

				dispose();
				setVisible(false);
			}
		};
		checkbox.addChangeListener(changeListener);
		return checkbox;
	}

	private List<String> createListModel(BrowserFrame frame) {
		List<String> listModel = new ArrayList<>();
		StyleStore style = new StyleStore();
		String url = frame.getToolbar().getAddressBar().getText();
		listModel.addAll(style.getStylesAll(url));
		return listModel;
	}
}