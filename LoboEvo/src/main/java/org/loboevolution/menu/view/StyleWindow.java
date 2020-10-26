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
 * @author utente
 * @version $Id: $Id
 */
public class StyleWindow extends JFrame {

	/**
	 * <p>Constructor for ClearstyleWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public StyleWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		add(getStyleBox(frame));
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
				final HtmlPanel htmlPanel = NavigationManager.getHtmlPanel(panel, fullURL, indexPanel);

				final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
				final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
				tabbedPane.remove(indexPanel);
				tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
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