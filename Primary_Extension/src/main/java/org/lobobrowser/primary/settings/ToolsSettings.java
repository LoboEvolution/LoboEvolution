/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.settings;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.store.StorageManager;

/**
 * The Class ToolsSettings.
 */
public class ToolsSettings implements Serializable {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ToolsSettings.class);

	/** The Constant instance. */
	private static final ToolsSettings instance;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500006000800L;

	/** The search engines. */
	private Collection<SearchEngine> searchEngines;

	/** The selected search engine. */
	private SearchEngine selectedSearchEngine;

	/** The download directory. */
	private File downloadDirectory;

	/** The open file directory. */
	private File openFileDirectory;

	static {
		ToolsSettings ins = null;
		try {
			ins = (ToolsSettings) StorageManager.getInstance().retrieveSettings(ToolsSettings.class.getSimpleName(),
					ToolsSettings.class.getClassLoader());
		} catch (Exception err) {
			logger.error("getInstance(): Unable to retrieve settings.", err);
		}
		if (ins == null) {
			ins = new ToolsSettings();
		}
		instance = ins;
	}

	/**
	 * Instantiates a new tools settings.
	 */
	private ToolsSettings() {
		this.restoreDefaults();
	}

	/**
	 * Restore defaults.
	 */
	public void restoreDefaults() {
		List<SearchEngine> searchEngines = this.getDefaultSearchEngines();
		this.searchEngines = searchEngines;
		this.selectedSearchEngine = searchEngines.get(0);
		String userHome = System.getProperty("user.home");
		File userHomeDirectory = new File(userHome);
		this.downloadDirectory = new File(userHomeDirectory, "download");
		if (!this.downloadDirectory.exists()) {
			this.downloadDirectory.mkdirs();
		}
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static ToolsSettings getInstance() {
		return instance;
	}

	/**
	 * Gets the default search engines.
	 *
	 * @return the default search engines
	 */
	private List<SearchEngine> getDefaultSearchEngines() {
		List<SearchEngine> searchEngines = new ArrayList<SearchEngine>();
		searchEngines.add(this.googleWebSearch());
		searchEngines.add(this.yahooWebSearch());
		searchEngines.add(this.wikipediaSearch());
		searchEngines.add(this.bingSearch());
		return searchEngines;
	}

	/**
	 * Google web search.
	 *
	 * @return the search engine
	 */
	private SearchEngine googleWebSearch() {
		return new SearchEngine("Google Web Search", "Google's main search engine.", "http://google.com/search", "q");
	}

	/**
	 * Yahoo web search.
	 *
	 * @return the search engine
	 */
	private SearchEngine yahooWebSearch() {
		return new SearchEngine("Yahoo! Web Search", "Yahoo's web search engine.", "http://search.yahoo.com/search",
				"p");
	}

	/**
	 * Wikipedia search.
	 *
	 * @return the search engine
	 */
	private SearchEngine wikipediaSearch() {
		return new SearchEngine("Wikipedia", "English Wikipedia article search.",
				"http://en.wikipedia.org/wiki/Special:Search", "search");
	}

	/**
	 * Bing search.
	 *
	 * @return the search engine
	 */
	private SearchEngine bingSearch() {
		return new SearchEngine("Bing Search", "Bing web search engine.", "http://www.bing.com/search?q", "q");
	}

	/**
	 * Save.
	 */
	public void save() {
		try {
			StorageManager.getInstance().saveSettings(this.getClass().getSimpleName(), this);
		} catch (IOException ioe) {
			logger.error("Unable to save settings: " + this.getClass().getSimpleName() + ".", ioe);
		}
	}

	/**
	 * Gets the search engines.
	 *
	 * @return the search engines
	 */
	public Collection<SearchEngine> getSearchEngines() {
		return this.searchEngines;
	}

	/**
	 * Sets the search engines.
	 *
	 * @param searchEngines
	 *            the new search engines
	 */
	public void setSearchEngines(Collection<SearchEngine> searchEngines) {
		this.searchEngines = searchEngines;
	}

	/**
	 * Gets the selected search engine.
	 *
	 * @return the selected search engine
	 */
	public SearchEngine getSelectedSearchEngine() {
		return selectedSearchEngine;
	}

	/**
	 * Sets the selected search engine.
	 *
	 * @param selectedSearchEngine
	 *            the new selected search engine
	 */
	public void setSelectedSearchEngine(SearchEngine selectedSearchEngine) {
		this.selectedSearchEngine = selectedSearchEngine;
	}

	/**
	 * Gets the download directory.
	 *
	 * @return the download directory
	 */
	public File getDownloadDirectory() {
		return downloadDirectory;
	}

	/**
	 * Sets the download directory.
	 *
	 * @param downloadDirectory
	 *            the new download directory
	 */
	public void setDownloadDirectory(File downloadDirectory) {
		this.downloadDirectory = downloadDirectory;
	}

	/**
	 * Gets the open file directory.
	 *
	 * @return the open file directory
	 */
	public File getOpenFileDirectory() {
		return openFileDirectory;
	}

	/**
	 * Sets the open file directory.
	 *
	 * @param openFileDirectory
	 *            the new open file directory
	 */
	public void setOpenFileDirectory(File openFileDirectory) {
		this.openFileDirectory = openFileDirectory;
	}
}
