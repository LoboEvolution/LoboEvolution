/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.loboevolution.primary.gui.prefs;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.loboevolution.primary.action.ImportDataAction;
import org.loboevolution.primary.gui.AbstractSettingsUI;
import org.loboevolution.primary.gui.CheckBoxPanel;
import org.loboevolution.primary.gui.FormPanel;
import org.loboevolution.primary.gui.SwingTasks;
import org.loboevolution.primary.gui.item.ItemEditorFactory;
import org.loboevolution.primary.gui.item.ItemListControl;
import org.loboevolution.primary.settings.ToolsSettings;
import org.loboevolution.settings.GeneralSettings;
import org.loboevolution.settings.SearchEngine;

/**
 * The Class ToolsSettingsUI.
 */
public class ToolsSettingsUI extends AbstractSettingsUI {

	private static final long serialVersionUID = 1L;
	
	private static final String HISTORY = "HISTORY"; 
	
	private static final String BOOKMARKS = "BOOKMARKS";
	
	private static final String COOKIES = "COOKIES";

	/** The search engine list control. */
	private ItemListControl<SearchEngine> searchEngineListControl;
	
	/** The mozilla panel. */
	private CheckBoxPanel mozillaPanel;
	
	/** The chrome panel. */
	private CheckBoxPanel chromePanel;
	
	/** The mozilla bookmark panel. */
	private CheckBoxPanel mozillaBookmarkPanel;
	
	/** The chrome bookmark panel. */
	private CheckBoxPanel chromeBookmarkPanel;
	
	/** The mozilla history panel. */
	private CheckBoxPanel mozillaHistoryPanel;
	
	/** The chrome history panel. */
	private CheckBoxPanel chromeHistoryPanel;
		
	/** The import button. */
	private JButton importButton;
	
	/** The bookmark button. */
	private JButton bookmarkButton;
	
	/** The history button. */
	private JButton historyButton;
	
	/**
	 * Instantiates a new tools settings ui.
	 */
	public ToolsSettingsUI() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {

		ItemEditorFactory<SearchEngine> factory = () -> new SearchEngineEditor();
		this.searchEngineListControl = new ItemListControl<SearchEngine>(factory);
		this.searchEngineListControl.setEditorCaption("Please enter search engine information below.");

		FormPanel historyPanel = new FormPanel();
		historyPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.mozillaHistoryPanel = new CheckBoxPanel("Mozilla Firefox", historyPanel);
		this.chromeHistoryPanel = new CheckBoxPanel("Google GoogleChrome", historyPanel);

		JButton historyButton = new JButton();
		historyButton.setAction(new ImportDataAction(this.mozillaHistoryPanel,this.chromeHistoryPanel, HISTORY));
		historyButton.setText("Import History");
		this.historyButton = historyButton;

		FormPanel bookmarkPanel = new FormPanel();
		bookmarkPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.mozillaBookmarkPanel = new CheckBoxPanel("Mozilla Firefox", bookmarkPanel);
		this.chromeBookmarkPanel = new CheckBoxPanel("Google GoogleChrome", bookmarkPanel);

		JButton bookmarkButton = new JButton();
		bookmarkButton.setAction(new ImportDataAction(this.mozillaBookmarkPanel, this.chromeBookmarkPanel, BOOKMARKS));
		bookmarkButton.setText("Import Bookmarks");
		this.bookmarkButton = bookmarkButton;
		

		FormPanel importPanel = new FormPanel();
		importPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.mozillaPanel = new CheckBoxPanel("Mozilla Firefox", importPanel);
		this.chromePanel = new CheckBoxPanel("Google GoogleChrome", importPanel);

		JButton importButton = new JButton();
		importButton.setAction(new ImportDataAction(this.mozillaPanel, this.chromePanel, COOKIES));
		importButton.setText("Import Cookies");
		this.importButton = importButton;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.getSearchEnginePane());
		this.add(SwingTasks.createVerticalFill());
		this.add(this.getHistoryBox());
		this.add(SwingTasks.createVerticalFill());
		this.add(getBookmarksBox());
		this.add(SwingTasks.createVerticalFill());
		this.add(getCookisBox());
		this.loadSettings();

	}

	/**
	 * Gets the search engine pane.
	 *
	 * @return the search engine pane
	 */
	private Component getSearchEnginePane() {
		Box innerBox = new Box(BoxLayout.X_AXIS);
		innerBox.add(new JLabel("Search Engines:"));
		innerBox.add(this.searchEngineListControl);
		Box groupBox = SwingTasks.createGroupBox(BoxLayout.Y_AXIS, "Search");
		groupBox.add(innerBox);
		return groupBox;
	}
	
	/**
	 * Gets the history box.
	 *
	 * @return the history box
	 */
	private Component getHistoryBox() {
		JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "History"));
		groupBox.add(this.getMozillaHistoryPanel());
		groupBox.add(this.getChromeHistoryPanel());
		groupBox.add(this.getHistoryButton());
		return groupBox;
	}
		
	/**
	 * Gets the bookmarks box.
	 *
	 * @return the bookmarks box
	 */
	private Component getBookmarksBox() {
		JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Bookmarks"));
		groupBox.add(this.getMozillaBookmarkPanel());
		groupBox.add(this.getChromeBookmarkPanel());
		groupBox.add(this.getBookmarkButton());
		return groupBox;
	}
	
	/**
	 * Gets the cookies box.
	 *
	 * @return the cookies box
	 */
	private Component getCookisBox() {
		JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Cookies"));
		groupBox.add(this.getMozillaPanel());
		groupBox.add(this.getChromePanel());
		groupBox.add(this.getImportButton());
		return groupBox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.primary.gui.prefs.AbstractSettingsUI#restoreDefaults()
	 */
	@Override
	public void restoreDefaults() {
		ToolsSettings settings = new ToolsSettings();
		settings.restoreDefaults();
		this.loadSettings();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.primary.gui.prefs.AbstractSettingsUI#save()
	 */
	@Override
	public void save() {
		ToolsSettings settings = new ToolsSettings();
		GeneralSettings genSettings = GeneralSettings.getNetwork();
		Collection<SearchEngine> items = this.searchEngineListControl.getItems();
		settings.deleteSearchEngine();
		int i = 0;

		if (genSettings.isNavigation()) {
			for (SearchEngine searchEngine : items) {
				settings.insertSearch(searchEngine.getName(), searchEngine.getDescription(), searchEngine.getBaseUrl(),
						searchEngine.getQueryParameter(), i == 0 ? true : false);
				i++;
			}
		}
	}
	
	/**
	 * Load settings.
	 */
	private void loadSettings() {
		ToolsSettings settings = new ToolsSettings();
		GeneralSettings genSettings = GeneralSettings.getNetwork();
		if (genSettings.isNavigation()) {
			this.searchEngineListControl.setItems(settings.getSearchEngines());
		}
	}
	
	/**
	 * @return the mozillaPanel
	 */
	public CheckBoxPanel getMozillaPanel() {
		return mozillaPanel;
	}

	/**
	 * @return the chromePanel
	 */
	public CheckBoxPanel getChromePanel() {
		return chromePanel;
	}
	
	/**
	 * @return the importButton
	 */
	public JButton getImportButton() {
		return importButton;
	}

	/**
	 * @return the mozillaBookmarkPanel
	 */
	public CheckBoxPanel getMozillaBookmarkPanel() {
		return mozillaBookmarkPanel;
	}

	/**
	 * @return the chromeBookmarkPanel
	 */
	public CheckBoxPanel getChromeBookmarkPanel() {
		return chromeBookmarkPanel;
	}

	public JButton getBookmarkButton() {
		return bookmarkButton;
	}

	/**
	 * @return the mozillaHistoryPanel
	 */
	public CheckBoxPanel getMozillaHistoryPanel() {
		return mozillaHistoryPanel;
	}

	/**
	 * @return the chromeHistoryPanel
	 */
	public CheckBoxPanel getChromeHistoryPanel() {
		return chromeHistoryPanel;
	}

	/**
	 * @return the historyButton
	 */
	public JButton getHistoryButton() {
		return historyButton;
	}
}
