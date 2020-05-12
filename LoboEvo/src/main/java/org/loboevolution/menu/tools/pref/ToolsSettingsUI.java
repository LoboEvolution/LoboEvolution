package org.loboevolution.menu.tools.pref;

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

import org.loboevolution.gui.CheckBoxPanel;
import org.loboevolution.gui.FormPanel;
import org.loboevolution.gui.ItemEditorFactory;
import org.loboevolution.gui.SwingTasks;
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
	private CheckBoxPanel chromeBookmarkPanel;

	/** The chrome history panel. */
	private CheckBoxPanel chromeHistoryPanel;

	/** The chrome panel. */
	private CheckBoxPanel chromePanel;

	/** The history button. */
	private JButton historyButton;

	/** The import button. */
	private JButton importButton;

	/** The mozilla bookmark panel. */
	private CheckBoxPanel mozillaBookmarkPanel;

	/** The mozilla history panel. */
	private CheckBoxPanel mozillaHistoryPanel;

	/** The mozilla panel. */
	private CheckBoxPanel mozillaPanel;

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

		final FormPanel historyPanel = new FormPanel();
		historyPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.mozillaHistoryPanel = new CheckBoxPanel("Mozilla Firefox", historyPanel);
		this.chromeHistoryPanel = new CheckBoxPanel("Google GoogleChrome", historyPanel);

		final JButton historyButton = new JButton();
		historyButton.setAction(new ImportDataAction(this.mozillaHistoryPanel, this.chromeHistoryPanel, HISTORY));
		historyButton.setText("Import History");
		this.historyButton = historyButton;

		final FormPanel bookmarkPanel = new FormPanel();
		bookmarkPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.mozillaBookmarkPanel = new CheckBoxPanel("Mozilla Firefox", bookmarkPanel);
		this.chromeBookmarkPanel = new CheckBoxPanel("Google GoogleChrome", bookmarkPanel);

		final JButton bookmarkButton = new JButton();
		bookmarkButton.setAction(new ImportDataAction(this.mozillaBookmarkPanel, this.chromeBookmarkPanel, BOOKMARKS));
		bookmarkButton.setText("Import Bookmarks");
		this.bookmarkButton = bookmarkButton;

		final FormPanel importPanel = new FormPanel();
		importPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.mozillaPanel = new CheckBoxPanel("Mozilla Firefox", importPanel);
		this.chromePanel = new CheckBoxPanel("Google GoogleChrome", importPanel);

		final JButton importButton = new JButton();
		importButton.setAction(new ImportDataAction(this.mozillaPanel, this.chromePanel, COOKIES));
		importButton.setText("Import Cookies");
		this.importButton = importButton;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getSearchEnginePane());
		this.add(SwingTasks.createVerticalFill());
		this.add(getHistoryBox());
		this.add(SwingTasks.createVerticalFill());
		this.add(getBookmarksBox());
		this.add(SwingTasks.createVerticalFill());
		this.add(getCookisBox());
		loadSettings();

	}

	/**
	 * <p>Getter for the field bookmarkButton.</p>
	 *
	 * @return a {@link javax.swing.JButton} object.
	 */
	public JButton getBookmarkButton() {
		return this.bookmarkButton;
	}

	/**
	 * Gets the bookmarks box.
	 *
	 * @return the bookmarks box
	 */
	private Component getBookmarksBox() {
		final JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Bookmarks"));
		groupBox.add(getMozillaBookmarkPanel());
		groupBox.add(getChromeBookmarkPanel());
		groupBox.add(getBookmarkButton());
		return groupBox;
	}

	/**
	 * <p>Getter for the field chromeBookmarkPanel.</p>
	 *
	 * @return the chromeBookmarkPanel
	 */
	public CheckBoxPanel getChromeBookmarkPanel() {
		return this.chromeBookmarkPanel;
	}

	/**
	 * <p>Getter for the field chromeHistoryPanel.</p>
	 *
	 * @return the chromeHistoryPanel
	 */
	public CheckBoxPanel getChromeHistoryPanel() {
		return this.chromeHistoryPanel;
	}

	/**
	 * <p>Getter for the field chromePanel.</p>
	 *
	 * @return the chromePanel
	 */
	public CheckBoxPanel getChromePanel() {
		return this.chromePanel;
	}

	/**
	 * Gets the cookies box.
	 *
	 * @return the cookies box
	 */
	private Component getCookisBox() {
		final JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Cookies"));
		groupBox.add(getMozillaPanel());
		groupBox.add(getChromePanel());
		groupBox.add(getImportButton());
		return groupBox;
	}

	/**
	 * Gets the history box.
	 *
	 * @return the history box
	 */
	private Component getHistoryBox() {
		final JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "History"));
		groupBox.add(getMozillaHistoryPanel());
		groupBox.add(getChromeHistoryPanel());
		groupBox.add(getHistoryButton());
		return groupBox;
	}

	/**
	 * <p>Getter for the field historyButton.</p>
	 *
	 * @return the historyButton
	 */
	public JButton getHistoryButton() {
		return this.historyButton;
	}

	/**
	 * <p>Getter for the field importButton.</p>
	 *
	 * @return the importButton
	 */
	public JButton getImportButton() {
		return this.importButton;
	}

	/**
	 * <p>Getter for the field mozillaBookmarkPanel.</p>
	 *
	 * @return the mozillaBookmarkPanel
	 */
	public CheckBoxPanel getMozillaBookmarkPanel() {
		return this.mozillaBookmarkPanel;
	}

	/**
	 * <p>Getter for the field mozillaHistoryPanel.</p>
	 *
	 * @return the mozillaHistoryPanel
	 */
	public CheckBoxPanel getMozillaHistoryPanel() {
		return this.mozillaHistoryPanel;
	}

	/**
	 * <p>Getter for the field mozillaPanel.</p>
	 *
	 * @return the mozillaPanel
	 */
	public CheckBoxPanel getMozillaPanel() {
		return this.mozillaPanel;
	}

	/**
	 * Gets the search engine pane.
	 *
	 * @return the search engine pane
	 */
	private Component getSearchEnginePane() {
		final Box innerBox = new Box(BoxLayout.X_AXIS);
		innerBox.add(new JLabel("Search Engines:"));
		innerBox.add(this.searchEngineListControl);
		final Box groupBox = SwingTasks.createGroupBox(BoxLayout.Y_AXIS, "Search");
		groupBox.add(innerBox);
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
