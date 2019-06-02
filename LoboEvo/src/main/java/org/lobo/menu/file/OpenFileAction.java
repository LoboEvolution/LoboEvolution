package org.lobo.menu.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.lobo.component.BrowserFrame;
import org.lobo.component.BrowserPanel;
import org.lobo.http.NavigationManager;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;

public class OpenFileAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public OpenFileAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final BrowserPanel panel = this.frame.getPanel();
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		final int returnValue = fileChooser.showOpenDialog(panel);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = fileChooser.getSelectedFile();
			final int indexPanel = panel.getTabbedPane().getIndex();
			final DnDTabbedPane tabbedPane = panel.getTabbedPane();
			tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
			tabbedPane.insertTab("New Tab", null, NavigationManager.getHtmlPanel(selectedFile.toURI().toString()), null,
					indexPanel + 1);
			tabbedPane.setSelectedIndex(indexPanel + 1);
		}
	}
}
