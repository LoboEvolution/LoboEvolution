package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import org.loboevolution.gui.CheckBox;
import org.loboevolution.gui.ItemEditorFactory;
import org.loboevolution.gui.Label;
import org.loboevolution.gui.Panel;
import org.loboevolution.menu.tools.pref.data.ImportDataAction;
import org.loboevolution.menu.tools.pref.search.ItemListControl;
import org.loboevolution.menu.tools.pref.search.SearchEngineEditor;
import org.loboevolution.store.GeneralStore;
import org.loboevolution.store.SearchEngineStore;
import org.loboevolution.store.ToolsStore;

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
	private JButton bookmarkButton;

	/** The chrome bookmark panel. */
	private CheckBox chromeBookmarkPanel;

	/** The chrome history panel. */
	private CheckBox chromeHistoryPanel;

	/** The chrome panel. */
	private CheckBox chromePanel;

	/** The history button. */
	private JButton historyButton;

	/** The import button. */
	private JButton importButton;

	/** The mozilla bookmark panel. */
	private CheckBox mozillaBookmarkPanel;

	/** The mozilla history panel. */
	private CheckBox mozillaHistoryPanel;

	/** The mozilla panel. */
	private CheckBox mozillaPanel;

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
		
		this.mozillaHistoryPanel = new CheckBox("Mozilla Firefox");
		this.chromeHistoryPanel = new CheckBox("Google Chrome");

		final JButton historyButton = new JButton();
		historyButton.setAction(new ImportDataAction(this.mozillaHistoryPanel, this.chromeHistoryPanel, HISTORY));
		historyButton.setText("Import History");
		this.historyButton = historyButton;

		this.mozillaBookmarkPanel = new CheckBox("Mozilla Firefox");
		this.chromeBookmarkPanel = new CheckBox("Google Chrome");

		final JButton bookmarkButton = new JButton();
		bookmarkButton.setAction(new ImportDataAction(this.mozillaBookmarkPanel, this.chromeBookmarkPanel, BOOKMARKS));
		bookmarkButton.setText("Import Bookmarks");
		this.bookmarkButton = bookmarkButton;

		this.mozillaPanel = new CheckBox("Mozilla Firefox");
		this.chromePanel = new CheckBox("Google Chrome");

		final JButton importButton = new JButton();
		importButton.setAction(new ImportDataAction(this.mozillaPanel, this.chromePanel, COOKIES));
		importButton.setText("Import Cookies");
		this.importButton = importButton;

		this.add(getSearchEnginePane());
		this.add(getHistoryBox());
		this.add(getBookmarksBox());
		this.add(getCookisBox());
		loadSettings();

	}

	/**
	 * Gets the bookmarks box.
	 *
	 * @return the bookmarks box
	 */
	private Component getBookmarksBox() {
		final Panel groupBox = new Panel("Bookmarks");
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
		final Panel groupBox = new Panel("Cookies");
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
		final Panel groupBox = new Panel("History");
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
		final Panel groupBox = new Panel("Search");
		groupBox.setPreferredSize(new Dimension(420, 50));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.X_AXIS));
		final Label pagesLabel = new Label("Engines:");
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
