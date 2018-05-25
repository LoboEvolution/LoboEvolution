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
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.loboevolution.primary.gui.AbstractSettingsUI;
import org.loboevolution.primary.gui.SwingTasks;
import org.loboevolution.primary.gui.item.ItemEditorFactory;
import org.loboevolution.primary.gui.item.ItemListControl;
import org.loboevolution.primary.settings.ToolsSettings;
import org.loboevolution.settings.SearchEngine;

/**
 * The Class ToolsSettingsUI.
 */
public class ToolsSettingsUI extends AbstractSettingsUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/** The search engine list control. */
	private final ItemListControl<SearchEngine> searchEngineListControl;

	/**
	 * Instantiates a new tools settings ui.
	 */
	public ToolsSettingsUI() {
		ItemEditorFactory<SearchEngine> factory = () -> new SearchEngineEditor();
		this.searchEngineListControl = new ItemListControl<SearchEngine>(factory);
		this.searchEngineListControl.setEditorCaption("Please enter search engine information below.");
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.getSearchEnginePane());
		this.add(SwingTasks.createVerticalFill());
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
		Collection<SearchEngine> items = this.searchEngineListControl.getItems();
		settings.deleteSearchEngine();
		int i = 0;
		for (SearchEngine searchEngine : items) {
			settings.insertSearch(searchEngine.getName(), searchEngine.getDescription(), searchEngine.getBaseUrl(),
					searchEngine.getQueryParameter(), i == 0 ? true : false);
			i++;	
		}
	}

	/**
	 * Load settings.
	 */
	private void loadSettings() {
		ToolsSettings settings = new ToolsSettings();
		this.searchEngineListControl.setItems(settings.getSearchEngines());
	}
}
