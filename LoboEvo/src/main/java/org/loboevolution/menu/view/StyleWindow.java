/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.menu.view;

import com.jtattoo.plaf.lobo.LoboCheckBox;
import com.jtattoo.plaf.lobo.LoboPanel;
import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.ITabbedPane;
import org.loboevolution.component.NavigatorFrame;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.StyleStore;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>StyleWindow class.</p>
 */
public class StyleWindow extends JFrame {

	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ClearstyleWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public StyleWindow(final BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(final BrowserFrame frame) {
		final JScrollPane spViewallItems = new JScrollPane();
		spViewallItems.setBorder(null);
		spViewallItems.setViewportView(getStyleBox(frame));
		add(spViewallItems);
	}

	private Component getStyleBox(final BrowserFrame frame) {
		final LoboPanel styleBox = new LoboPanel("Choice Style");
		styleBox.setLayout(new BoxLayout(styleBox, BoxLayout.Y_AXIS));
		final List<String> list = createListModel(frame) ;
		list.forEach( name -> styleBox.add(getCheckBox(frame, name)));
		return styleBox;
	}

	private Component getCheckBox(final BrowserFrame frame, final String name) {
		final LoboCheckBox checkbox = new LoboCheckBox(name);
		checkbox.setActionCommand(name);
		checkbox.setToolTipText("Open current site with style " + name);
		final ChangeListener changeListener = changeEvent -> {
			final AbstractButton abstractButton = (AbstractButton) changeEvent.getSource();
			final ButtonModel buttonModel = abstractButton.getModel();
			if (buttonModel.isPressed() && buttonModel.isSelected()) {
				final String fullURL = frame.getToolbar().getAddressBar().getText();
				final StyleStore style = new StyleStore();
				style.selectStyle(buttonModel.getActionCommand());

				final IBrowserPanel panel = frame.getPanel();
				final ITabbedPane tabbedPane = panel.getTabbedPane();
				tabbedPane.setComponentPopupMenu(panel);
				final int indexPanel = tabbedPane.getSelectedIndex();
				final HtmlPanel htmlPanel = NavigatorFrame.createHtmlPanel(panel, fullURL);
				final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
				final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
				tabbedPane.remove(indexPanel);
				tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
				NavigationManager.insertHistory(fullURL, title, indexPanel);
				panel.getScroll().getViewport().add((Component) tabbedPane);

				dispose();
				setVisible(false);
			}
		};
		checkbox.addChangeListener(changeListener);
		return checkbox;
	}

	private List<String> createListModel(final BrowserFrame frame) {
        final StyleStore style = new StyleStore();
		final String url = frame.getToolbar().getAddressBar().getText();
        return new ArrayList<>(style.getStylesAll(url));
	}
}