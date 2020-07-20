package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BoxLayout;

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
 * @author utente
 * @version $Id: $Id
 */
public class ToolsSettingsUI extends AbstractSettingsUI {

	private static final String BOOKMARKS = "BOOKMARKS";

	private static final String COOKIES = "COOKIES";

	private static final String HISTORY = "HISTORY";

	private static final long serialVersionUID = 1L;

	/** The bookmark button. */
	private LoboButton bookmarkButton;

	/** The chrome bookmark panel. */
	private LoboCheckBox chromeBookmarkPanel;

	/** The chrome history panel. */
	private LoboCheckBox chromeHistoryPanel;

	/** The chrome panel. */
	private LoboCheckBox chromePanel;

	/** The history button. */
	private LoboButton historyButton;

	/** The import button. */
	private LoboButton importButton;

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

		final ItemEditorFactory<SearchEngineStore> factory = () -> new SearchEngineEditor();
		this.searchEngineListControl = new ItemListControl<SearchEngineStore>(factory);
		this.searchEngineListControl.setEditorCaption("Please enter search engine information below.");
		
		this.mozillaHistoryPanel = new LoboCheckBox("Mozilla Firefox");
		this.chromeHistoryPanel = new LoboCheckBox("Google Chrome");

		final LoboButton historyButton = new LoboButton();
		historyButton.setAction(new ImportDataAction(this.mozillaHistoryPanel, this.chromeHistoryPanel, HISTORY));
		historyButton.setText("Import History");
		this.historyButton = historyButton;

		this.mozillaBookmarkPanel = new LoboCheckBox("Mozilla Firefox");
		this.chromeBookmarkPanel = new LoboCheckBox("Google Chrome");

		final LoboButton bookmarkButton = new LoboButton();
		bookmarkButton.setAction(new ImportDataAction(this.mozillaBookmarkPanel, this.chromeBookmarkPanel, BOOKMARKS));
		bookmarkButton.setText("Import Bookmarks");
		this.bookmarkButton = bookmarkButton;

		this.mozillaPanel = new LoboCheckBox("Mozilla Firefox");
		this.chromePanel = new LoboCheckBox("Google Chrome");

		final LoboButton importButton = new LoboButton();
		importButton.setAction(new ImportDataAction(this.mozillaPanel, this.chromePanel, COOKIES));
		importButton.setText("Import Cookies");
		this.importButton = importButton;

		this.add(getSearchEnginePane());
		this.add(getHistoryBox());
		this.add(getBookmarksBox());
		this.add(getCookisBox());
		this.add(SwingTasks.createVerticalFill());
		loadSettings();

	}

	/**
	 * Gets the bookmarks box.
	 *
	 * @return the bookmarks box
	 */
	private Component getBookmarksBox() {
		final LoboPanel groupBox = new LoboPanel("Bookmarks");
		groupBox.setPreferredSize(new Dimension(420, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(mozillaBookmarkPanel);
		groupBox.add(chromeBookmarkPanel);
		groupBox.add(bookmarkButton);
		return groupBox;
	}

	/**
	 * Gets the cookies box.
	 *
	 * @return the cookies box
	 */
	private Component getCookisBox() {
		final LoboPanel groupBox = new LoboPanel("Cookies");
		groupBox.setPreferredSize(new Dimension(420, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(mozillaPanel);
		groupBox.add(chromePanel);
		groupBox.add(importButton);
		return groupBox;
	}

	/**
	 * Gets the history box.
	 *
	 * @return the history box
	 */
	private Component getHistoryBox() {
		final LoboPanel groupBox = new LoboPanel("History");
		groupBox.setPreferredSize(new Dimension(420, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(mozillaHistoryPanel);
		groupBox.add(chromeHistoryPanel);
		groupBox.add(historyButton);
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
						searchEngineStore.getQueryParameter(), i == 0 ? true : false);
				i++;
			}
		}
	}
}
