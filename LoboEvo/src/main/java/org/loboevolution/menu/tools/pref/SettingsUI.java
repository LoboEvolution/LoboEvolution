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

package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Dimension;
import java.io.Serial;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

import org.loboevolution.gui.ItemEditorFactory;
import org.loboevolution.gui.SwingTasks;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;
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
 */
public class SettingsUI extends AbstractToolsUI {

	private static final String BOOKMARKS = "BOOKMARKS";

	private static final String COOKIES = "COOKIES";

	private static final String HISTORY = "HISTORY";

	@Serial
    private static final long serialVersionUID = 1L;

    /** The search engine list control. */
	private ItemListControl<SearchEngineStore> searchEngineListControl;

	static final GeneralInfo genSettings = GeneralStore.getGeneralInfo();

	/**
	 * Instantiates a new tools settings ui.
	 */
	public SettingsUI() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {

		final ItemEditorFactory<SearchEngineStore> factory = SearchEngineEditor::new;
		this.searchEngineListControl = new ItemListControl<>(factory);
		this.searchEngineListControl.setEditorCaption("Please enter search engine information below.");

        LoboCheckBox mozillaHistoryPanel = new LoboCheckBox("Mozilla Firefox");
        LoboCheckBox chromeHistoryPanel = new LoboCheckBox("Google Chrome");

		final LoboButton historyButton = new LoboButton();
		historyButton.setAction(new ImportDataAction(mozillaHistoryPanel, chromeHistoryPanel, HISTORY));
		historyButton.setText("Import History");

        LoboCheckBox mozillaBookmarkPanel = new LoboCheckBox("Mozilla Firefox");
        LoboCheckBox chromeBookmarkPanel = new LoboCheckBox("Google Chrome");

		final LoboButton bookmarkButton = new LoboButton();
		bookmarkButton.setAction(new ImportDataAction(mozillaBookmarkPanel, chromeBookmarkPanel, BOOKMARKS));
		bookmarkButton.setText("Import Bookmarks");

        LoboCheckBox mozillaPanel = new LoboCheckBox("Mozilla Firefox");
        LoboCheckBox chromePanel = new LoboCheckBox("Google Chrome");

		final LoboButton importButton = new LoboButton();
		importButton.setAction(new ImportDataAction(mozillaPanel, chromePanel, COOKIES));
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
	private Component getBox(final String title, final LoboCheckBox mozillaPanel, final LoboCheckBox chromePanel , final LoboButton button) {
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
