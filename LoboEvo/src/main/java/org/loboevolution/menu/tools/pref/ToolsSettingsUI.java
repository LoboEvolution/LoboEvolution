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

package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

import org.loboevolution.gui.ItemEditorFactory;
import org.loboevolution.gui.SwingTasks;
import org.loboevolution.menu.tools.pref.data.ImportDataAction;
import org.loboevolution.menu.tools.pref.search.ItemListControl;
import org.loboevolution.menu.tools.pref.search.SearchEngineEditor;
import org.loboevolution.store.GeneralStore;
import org.loboevolution.store.SearchEngineStore;
import org.loboevolution.store.ToolsStore;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboCheckBox;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboPanel;

/**
 * The Class ToolsSettingsUI.
 *
 *
 *
 */
public class ToolsSettingsUI extends AbstractSettingsUI {

	private static final String BOOKMARKS = "BOOKMARKS";

	private static final String COOKIES = "COOKIES";

	private static final String HISTORY = "HISTORY";

	private static final long serialVersionUID = 1L;

	/** The chrome bookmark panel. */
	private LoboCheckBox chromeBookmarkPanel;

	/** The chrome history panel. */
	private LoboCheckBox chromeHistoryPanel;

	/** The chrome panel. */
	private LoboCheckBox chromePanel;

	/** The mozilla bookmark panel. */
	private LoboCheckBox mozillaBookmarkPanel;

	/** The mozilla history panel. */
	private LoboCheckBox mozillaHistoryPanel;

	/** The mozilla panel. */
	private LoboCheckBox mozillaPanel;

	/** The search engine list control. */
	private ItemListControl<SearchEngineStore> searchEngineListControl;
	

	/**
	 * Instantiates a new tools settings ui.
	 */
	public ToolsSettingsUI() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {

		final ItemEditorFactory<SearchEngineStore> factory = SearchEngineEditor::new;
		this.searchEngineListControl = new ItemListControl<>(factory);
		this.searchEngineListControl.setEditorCaption("Please enter search engine information below.");
		
		this.mozillaHistoryPanel = new LoboCheckBox("Mozilla Firefox");
		this.chromeHistoryPanel = new LoboCheckBox("Google Chrome");

		final LoboButton historyButton = new LoboButton();
		historyButton.setAction(new ImportDataAction(this.mozillaHistoryPanel, this.chromeHistoryPanel, HISTORY));
		historyButton.setText("Import History");

		this.mozillaBookmarkPanel = new LoboCheckBox("Mozilla Firefox");
		this.chromeBookmarkPanel = new LoboCheckBox("Google Chrome");

		final LoboButton bookmarkButton = new LoboButton();
		bookmarkButton.setAction(new ImportDataAction(this.mozillaBookmarkPanel, this.chromeBookmarkPanel, BOOKMARKS));
		bookmarkButton.setText("Import Bookmarks");

		this.mozillaPanel = new LoboCheckBox("Mozilla Firefox");
		this.chromePanel = new LoboCheckBox("Google Chrome");

		final LoboButton importButton = new LoboButton();
		importButton.setAction(new ImportDataAction(this.mozillaPanel, this.chromePanel, COOKIES));
		importButton.setText("Import Cookies");

		this.add(getSearchEnginePane());
		this.add(getBox("Bookmark", mozillaBookmarkPanel, chromeBookmarkPanel, bookmarkButton));
		this.add(getBox("Cookies", mozillaPanel, chromePanel, importButton));
		this.add(getBox("History", mozillaHistoryPanel, chromeHistoryPanel, historyButton));
		this.add(SwingTasks.createVerticalFill());
		loadSettings();

	}

	/**
	 * Gets the box.
	 *
	 * @return the box
	 */
	private Component getBox(String title, LoboCheckBox mozillaPanel, LoboCheckBox chromePanel ,LoboButton button) {
		final LoboPanel groupBox = new LoboPanel(title);
		groupBox.setPreferredSize(new Dimension(420,75));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		
		final LoboPanel browserBox = new LoboPanel();
		browserBox.setLayout(new BoxLayout(browserBox, BoxLayout.X_AXIS));
		browserBox.add(mozillaPanel);
		browserBox.add(chromePanel);
		
		final LoboPanel buttonsPanel = new LoboPanel("");
		buttonsPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(Box.createHorizontalGlue());
		buttonsPanel.add(button);
		
		groupBox.add(browserBox);
		groupBox.add(buttonsPanel);
		return groupBox;
	}

	/**
	 * Gets the search engine pane.
	 *
	 * @return the search engine pane
	 */
	private Component getSearchEnginePane() {
		final LoboPanel groupBox = new LoboPanel("Search");
		groupBox.setPreferredSize(new Dimension(420, 50));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.X_AXIS));
		final LoboLabel pagesLabel = new LoboLabel("Engines:");
		groupBox.add(pagesLabel);
		groupBox.add(this.searchEngineListControl);
		return groupBox;
	}

	/**
	 * Load settings.
	 */
	private void loadSettings() {
		final ToolsStore settings = new ToolsStore();
		final GeneralStore genSettings = GeneralStore.getNetwork();
		if (genSettings.isNavigation()) {
			this.searchEngineListControl.setItems(settings.getSearchEngines());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void restoreDefaults() {
		final ToolsStore settings = new ToolsStore();
		settings.restoreDefaults();
		loadSettings();
	}

	/** {@inheritDoc} */
	@Override
	public void save() {
		final ToolsStore settings = new ToolsStore();
		final GeneralStore genSettings = GeneralStore.getNetwork();
		final Collection<SearchEngineStore> items = this.searchEngineListControl.getItems();
		settings.deleteSearchEngine();
		int i = 0;

		if (genSettings.isNavigation()) {
			for (final SearchEngineStore searchEngineStore : items) {
				settings.insertSearch(searchEngineStore.getName(), searchEngineStore.getDescription(), searchEngineStore.getBaseUrl(),
						searchEngineStore.getQueryParameter(), i == 0);
				i++;
			}
		}
	}
}
