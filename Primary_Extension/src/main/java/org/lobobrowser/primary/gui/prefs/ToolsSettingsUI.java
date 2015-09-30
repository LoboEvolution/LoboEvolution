/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.prefs;

import java.awt.Component;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.lobobrowser.primary.gui.AbstractItemEditor;
import org.lobobrowser.primary.gui.ItemEditorFactory;
import org.lobobrowser.primary.gui.ItemListControl;
import org.lobobrowser.primary.gui.SwingTasks;
import org.lobobrowser.primary.settings.SearchEngine;
import org.lobobrowser.primary.settings.ToolsSettings;

/**
 * The Class ToolsSettingsUI.
 */
public class ToolsSettingsUI extends AbstractSettingsUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The settings. */
	private final ToolsSettings settings = ToolsSettings.getInstance();

	/** The search engine list control. */
	private final ItemListControl<SearchEngine> searchEngineListControl;

	/**
	 * Instantiates a new tools settings ui.
	 */
	public ToolsSettingsUI() {
		ItemEditorFactory<SearchEngine> factory = new ItemEditorFactory<SearchEngine>() {
			@Override
			public AbstractItemEditor<SearchEngine> createItemEditor() {
				return new SearchEngineEditor();
			}
		};
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
	 * org.lobobrowser.primary.gui.prefs.AbstractSettingsUI#restoreDefaults()
	 */
	@Override
	public void restoreDefaults() {
		this.settings.restoreDefaults();
		this.loadSettings();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.primary.gui.prefs.AbstractSettingsUI#save()
	 */
	@Override
	public void save() {
		ToolsSettings settings = this.settings;
		Collection<SearchEngine> items = this.searchEngineListControl.getItems();
		settings.setSearchEngines(items);
		settings.save();
	}

	/**
	 * Load settings.
	 */
	private void loadSettings() {
		ToolsSettings settings = this.settings;
		this.searchEngineListControl.setItems(settings.getSearchEngines());
	}
}
