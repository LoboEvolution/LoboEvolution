/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.ext;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;

import org.lobobrowser.gui.ConsoleModel;
import org.lobobrowser.primary.settings.SearchEngine;
import org.lobobrowser.primary.settings.ToolsSettings;
import org.lobobrowser.request.ClientletRequestHandler;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorProgressEvent;
import org.lobobrowser.ua.NavigatorWindow;
import org.lobobrowser.ua.NavigatorWindowEvent;
import org.lobobrowser.ua.NavigatorWindowListener;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.util.Timing;

public class ComponentSource implements NavigatorWindowListener {
	private static final Logger logger = Logger.getLogger(ComponentSource.class
			.getName());
	private static final int PREFERRED_MAX_MENU_SIZE = 20;

	private final NavigatorWindow window;
	private final AddressField addressField;
	private final JLabel statusMessageComponent;
	private final ProgressBar progressBar;
	private final JMenu recentBookmarksMenu;
	private final JMenu taggedBookmarksMenu;
	private final JMenu backMoreMenu;
	private final JMenu forwardMoreMenu;
	private final JMenu recentHostsMenu;
	private final JMenu searchersMenu;
	private final JButton searchButton;
	private final ActionPool actionPool;
	private final SearchPageSource searchPageSource;

	public ComponentSource(final NavigatorWindow window) {
		super();
		this.actionPool = new ActionPool(this, window);
		this.searchPageSource = new SearchPageSource(this.actionPool);
		this.window = window;
		this.addressField = new AddressField(this);
		this.progressBar = new ProgressBar();
		this.statusMessageComponent = new JLabel();
		this.searchButton = this.getSearchButton();
		this.updateSearchButtonTooltip();
		JMenu bookmarksMenu = new JMenu("Recent Bookmarks");
		this.recentBookmarksMenu = bookmarksMenu;
		bookmarksMenu.setMnemonic('R');
		bookmarksMenu.addMenuListener(new MenuAdapter() {
			public void menuSelected(MenuEvent e) {
				populateRecentBookmarks();
			}
		});
		JMenu taggedBookmarksMenu = new JMenu("Tagged Bookmarks");
		this.taggedBookmarksMenu = taggedBookmarksMenu;
		taggedBookmarksMenu.setMnemonic('T');
		taggedBookmarksMenu.setToolTipText("Shows up to "
				+ PREFERRED_MAX_MENU_SIZE + " tags with up to "
				+ PREFERRED_MAX_MENU_SIZE + " recent bookmarks each.");
		taggedBookmarksMenu.addMenuListener(new MenuAdapter() {
			public void menuSelected(MenuEvent e) {
				populateTaggedBookmarks();
			}
		});
		JMenu backMoreMenu = new JMenu();
		// BackMoreAction only used for enabling
		backMoreMenu.setAction(actionPool.backMoreAction);
		backMoreMenu.addMenuListener(new MenuAdapter() {
			public void menuSelected(MenuEvent e) {
				populateBackMore();
			}
		});
		this.backMoreMenu = backMoreMenu;
		backMoreMenu.setText("Back To");
		JMenu forwardMoreMenu = new JMenu();
		// ForwardMoreAction only used for enabling
		forwardMoreMenu.setAction(actionPool.forwardMoreAction);
		forwardMoreMenu.addMenuListener(new MenuAdapter() {
			public void menuSelected(MenuEvent e) {
				populateForwardMore();
			}
		});
		this.forwardMoreMenu = forwardMoreMenu;
		forwardMoreMenu.setText("Forward To");
		JMenu recentHostsMenu = new JMenu();
		recentHostsMenu.addMenuListener(new MenuAdapter() {
			public void menuSelected(MenuEvent e) {
				populateRecentHosts();
			}
		});
		this.recentHostsMenu = recentHostsMenu;
		recentHostsMenu.setAction(this.actionPool.recentHostsAction);
		recentHostsMenu.setText("Recent Hosts");
		JMenu searchersMenu = new JMenu();
		searchersMenu.addMenuListener(new MenuAdapter() {
			public void menuSelected(MenuEvent e) {
				populateSearchers();
			}
		});
		this.searchersMenu = searchersMenu;
		searchersMenu.setText("Searching With");
		searchersMenu
				.setToolTipText("Select the search engine that is used by the Search button in the address bar.");
	}

	public Component[] getAddressBarComponents() {
		return new Component[] { this.getBackButton(), this.window.createGap(),
				this.getForwardButton(), this.window.createGap(),
				this.getStopButton(), this.window.createGap(),
				this.getRefreshButton(), this.window.createGap(),
				this.window.createGlueComponent(this.addressField, true),
				this.window.createGap(), this.getGoButton(),
				this.window.createGap(), this.searchButton,
				this.window.createGap() };
	}

	public Component[] getStatusBarComponents() {
		return new Component[] { this.window.createGap(),
				this.getStatusMessageComponent(), this.window.createGap(),
				this.getProgressBar(), this.window.createGap() };
	}

	public JMenu getFileMenu() {
		JMenu menu = new JMenu("File");
		menu.setMnemonic('F');
		menu.add(menuItem("Save As", 'S', "ctrl S",
				this.actionPool.saveFileAction));
		menu.addSeparator();
		menu.add(menuItem(
				"Blank Window",
				'B',
				KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK
						| KeyEvent.SHIFT_MASK),
				this.actionPool.blankWindowAction));
		menu.addSeparator();
		menu.add(menuItem("Cloned Window", 'C',
				this.actionPool.clonedWindowAction));
		menu.addSeparator();
		menu.add(menuItem("File...", 'F', "ctrl O",
				this.actionPool.openFileAction));
		menu.addSeparator();
		menu.add(menuItem("Close", 'C', this.actionPool.exitAction));

		return menu;
	}

	public JMenu getEditMenu() {
		JMenu menu = new JMenu("Edit");
		menu.setMnemonic('E');
		menu.add(menuItem("Copy", 'C', "ctrl C", this.actionPool.copyAction));
		return menu;
	}

	public JMenu getViewMenu() {
		JMenu menu = new JMenu("View");
		menu.setMnemonic('V');
		menu.add(menuItem("Page Source", 'S', this.actionPool.sourceAction));
		menu.add(menuItem("Console", 'C', this.actionPool.consoleAction));
		menu.add(this.recentHostsMenu);
		menu.add(this.searchPageSource.getSearchMenu());

		return menu;
	}

	public JMenu getBookmarksMenu() {
		JMenu menu = new JMenu("Bookmarks");
		menu.setMnemonic('B');
		menu.add(menuItem("Add Bookmark", 'A', "ctrl shift a",
				this.actionPool.addBookmarkAction));
		menu.add(this.recentBookmarksMenu);
		menu.add(this.taggedBookmarksMenu);
		menu.add(menuItem("Search Bookmarks", 'S',
				this.actionPool.searchBookmarksAction));
		menu.add(menuItem("Show All Bookmarks", 'S',
				this.actionPool.showBookmarksAction));
		return menu;
	}

	public JMenu getNavigationMenu() {
		JMenu menu = new JMenu("Navigation");
		menu.setMnemonic('N');

		menu.add(menuItem("Back", 'B', "ctrl B", this.actionPool.backAction));
		menu.add(menuItem("Forward", 'F', this.actionPool.forwardAction));
		menu.add(menuItem("Stop", 'S',
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				this.actionPool.stopAction));
		menu.add(menuItem("Reload", 'R',
				KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
				this.actionPool.reloadAction));
		menu.addSeparator();
		menu.add(this.backMoreMenu);
		menu.add(this.forwardMoreMenu);

		return menu;
	}

	public JMenu getToolsMenu() {
		JMenu menu = new JMenu("Tools");
		menu.setMnemonic('T');
		menu.add(this.searchersMenu);
		menu.add(menuItem("Preferences...", 'P',this.actionPool.preferencesAction));
		menu.add(menuItem("Clear Cache", 'M', "ctrl M", this.actionPool.clearCacheAction));
		return menu;
	}

	public JMenu getExtensionsMenu() {
		JMenu menu = new JMenu("Extensions");
		menu.setMnemonic('x');
		menu.add(menuItem("List Extensions", 'L',
				this.actionPool.listExtensionsAction));
		return menu;
	}

	public JMenu getPageServicesMenu() {
		JMenu menu = new JMenu("Page Services");
		menu.setMnemonic('P');
		menu.add(menuItem("Java",
				this.actionPool.createNavigateAction("http://www.java.com")));
		menu.addSeparator();
		menu.add(menuItem("Eclipse",
				this.actionPool.createNavigateAction("http://www.eclipse.org/")));
		menu.addSeparator();

		return menu;
	}

	public JMenu getHelpMenu() {
		JMenu menu = new JMenu("Help");
		menu.setMnemonic('H');

		menu.add(menuItem("About Lobo", 'A', this.actionPool.aboutAction));
		menu.addSeparator();
		menu.add(menuItem(
				"Project Home Page",
				this.actionPool
						.createNavigateAction("http://sourceforge.net/projects/loboevolution/")));
		menu.addSeparator();
		menu.add(menuItem(
				"Wiki",
				this.actionPool
						.createNavigateAction("http://sourceforge.net/p/loboevolution/wiki/Home/")));
		menu.add(menuItem(
				"Discussion Forum",
				this.actionPool
						.createNavigateAction("http://sourceforge.net/p/loboevolution/discussion/")));

		return menu;
	}

	private Component getBackButton() {
		JButton button = new JButton();
		button.setAction(this.actionPool.backAction);
		button.setIcon(IconFactory.getInstance().getIcon(
				"/toolbarButtonGraphics/navigation/Back16.gif"));
		button.setToolTipText("Back");
		return button;
	}

	private Component getForwardButton() {
		JButton button = new JButton();
		button.setAction(this.actionPool.forwardAction);
		button.setIcon(IconFactory.getInstance().getIcon(
				"/toolbarButtonGraphics/navigation/Forward16.gif"));
		button.setToolTipText("Forward");
		return button;
	}

	private Component getStopButton() {
		JButton button = new JButton();
		button.setAction(this.actionPool.stopAction);
		button.setIcon(IconFactory.getInstance().getIcon(
				"/toolbarButtonGraphics/general/Stop16.gif"));
		button.setToolTipText("Stop");
		return button;
	}

	private Component getRefreshButton() {
		JButton button = new JButton();
		button.setAction(this.actionPool.reloadAction);
		button.setIcon(IconFactory.getInstance().getIcon(
				"/toolbarButtonGraphics/general/Refresh16.gif"));
		button.setToolTipText("Refresh");
		return button;
	}

	private Component getGoButton() {
		JButton button = new JButton();
		button.setAction(this.actionPool.goAction);
		button.setIcon(IconFactory.getInstance().getIcon(
				"/toolbarButtonGraphics/media/Play16.gif"));
		button.setToolTipText("Navigate to URL");
		return button;
	}

	private JButton getSearchButton() {
		JButton button = new JButton();
		button.setAction(this.actionPool.searchAction);
		button.setIcon(IconFactory.getInstance().getIcon(
				"/toolbarButtonGraphics/general/Search16.gif"));
		return button;
	}

	private void updateSearchButtonTooltip() {
		JButton button = this.searchButton;
		ToolsSettings settings = ToolsSettings.getInstance();
		SearchEngine currentEngine = settings.getSelectedSearchEngine();
		String name = currentEngine == null ? "[none]" : currentEngine
				.getName();
		button.setToolTipText("<html><body>Current search engine: " + name
				+ ".</body></html>");
	}

	private Component getStatusMessageComponent() {
		return this.window.createGlueComponent(this.statusMessageComponent,
				true);
	}

	private Component getProgressBar() {
		return this.progressBar;
	}

	private String defaultStatusMessage;

	public void defaultStatusUpdated(NavigatorWindowEvent event) {
		String defaultStatus = event.getMessage();
		this.defaultStatusMessage = event.getMessage();
		if (this.statusMessage == null) {
			this.statusMessageComponent.setText(defaultStatus);
		}
	}

	/**
	 * Whether the request should be saved as a recent history entry.
	 */
	private boolean isHistoryRequest(RequestType requestType) {
		return (requestType == RequestType.ADDRESS_BAR || requestType == RequestType.CLICK);
	}

	public void documentAccessed(NavigatorWindowEvent event) {
		java.net.URL url = event.getUrl();
		if ("GET".equals(event.getMethod())
				&& isHistoryRequest(event.getRequestType())) {
			NavigationHistory.getInstance().addAsRecent(url, null);
		}
	}

	public void documentRendering(NavigatorWindowEvent event) {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("documentRendering(): event=" + event);
		}
		if (this.window.getTopFrame() == event.getNavigatorFrame()) {
			java.net.URL url = event.getUrl();
			this.addressField.setUrl(url);
			this.clearState();
			this.actionPool.updateEnabling();
		}
	}

	public void setNavigationEntry(NavigationEntry entry) {
		if (entry != null) {
			if (this.window.getTopFrame() == entry.getNavigatorFrame()) {
				java.net.URL url = entry.getUrl();
				this.addressField.setUrl(url);
				this.clearState();
				this.actionPool.updateEnabling();
			}
		} else {
			this.clearState();
			this.addressField.setUrl(null);
			this.actionPool.updateEnabling();
		}
	}

	private void clearState() {
		this.statusMessage = null;
		this.defaultStatusMessage = null;
		this.statusMessageComponent.setText("");
	}

	public void progressUpdated(NavigatorProgressEvent event) {
		if (this.window.getTopFrame() == event.getNavigatorFrame()) {
			this.progressBar.updateProgress(event.getProgressType(),
					event.getCurrentValue(), event.getMaxValue());
		}
		this.statusMessageComponent.setText(ClientletRequestHandler
				.getProgressMessage(event.getProgressType(), event.getUrl()));
	}

	private String statusMessage;

	public void statusUpdated(NavigatorWindowEvent event) {
		String status = event.getMessage();
		this.statusMessage = status;
		this.statusMessageComponent
				.setText(status == null ? this.defaultStatusMessage : status);
	}

	public Collection<String> getPotentialMatches(String urlPrefix, int max) {
		int colonIdx = urlPrefix.indexOf(':');
		String prefix;
		if (colonIdx == -1) {
			// Add http
			prefix = "http://" + urlPrefix;
		} else if (colonIdx == 1) {
			// Must be a Windows file
			prefix = "file://" + urlPrefix;
		} else {
			prefix = urlPrefix;
		}
		Collection<String> headMatches = NavigationHistory.getInstance()
				.getHeadMatchItems(prefix, max);
		if (headMatches.isEmpty()) {
			// Try www
			if (colonIdx == -1 && !urlPrefix.startsWith("www")) {
				prefix = "http://www." + urlPrefix;
				headMatches = NavigationHistory.getInstance()
						.getHeadMatchItems(prefix, max);
			}
		}
		return headMatches;
	}

	public Collection<String> getRecentLocations(int max) {
		return NavigationHistory.getInstance().getRecentItems(max);
	}

	public void navigate(String roughLocation, RequestType requestType) {
		try {
			this.window.stop();
			this.clearState();
			this.window.getTopFrame().navigate(roughLocation, requestType);
		} catch (java.net.MalformedURLException mfu) {
			ExtensionImpl.showError(this.window.getTopFrame(), null,
					new java.net.MalformedURLException(roughLocation));
		}
	}

	public void navigate(java.net.URL url) {
		this.window.stop();
		this.clearState();
		this.window.getTopFrame().navigate(url);
	}

	static JMenuItem menuItem(String title, Action action) {
		return menuItem(title, (char) 0, (KeyStroke) null, action);
	}

	static JMenuItem menuItem(String title, char mnemonic, Action action) {
		return menuItem(title, mnemonic, (KeyStroke) null, action);
	}

	static JMenuItem menuItem(String title, char mnemonic,
			KeyStroke accelerator, Action action) {
		JMenuItem item = new JMenuItem();
		item.setAction(action);
		item.setText(title);
		if (mnemonic != 0) {
			item.setMnemonic(mnemonic);
		}
		if (accelerator != null) {
			item.setAccelerator(accelerator);
		}
		return item;
	}

	static JMenuItem menuItem(String title, char mnemonic, String accelerator,
			Action action) {
		KeyStroke keyStroke = accelerator == null ? null : KeyStroke
				.getKeyStroke(accelerator);
		return menuItem(title, mnemonic, keyStroke, action);
	}

	public void populateRecentBookmarks() {
		JMenu bookmarksMenu = this.recentBookmarksMenu;
		bookmarksMenu.removeAll();
		Collection<HistoryEntry<BookmarkInfo>> historyEntries = BookmarksHistory
				.getInstance().getRecentEntries(PREFERRED_MAX_MENU_SIZE);
		for (HistoryEntry<BookmarkInfo> hentry : historyEntries) {
			BookmarkInfo binfo = hentry.getItemInfo();
			String text = binfo.getTitle();
			java.net.URL url = binfo.getUrl();
			String urlText = url.toExternalForm();
			if (text == null || text.length() == 0) {
				text = urlText;
			}
			long elapsed = System.currentTimeMillis() - hentry.getTimetstamp();
			text = text + " (" + Timing.getElapsedText(elapsed) + " ago)";
			Action action = this.actionPool.createBookmarkNavigateAction(url);
			JMenuItem menuItem = ComponentSource.menuItem(text, action);
			StringBuffer toolTipText = new StringBuffer();
			toolTipText.append("<html>");
			toolTipText.append(urlText);
			String description = binfo.getDescription();
			if (description != null && description.length() != 0) {
				toolTipText.append("<br>");
				toolTipText.append(description);
			}
			menuItem.setToolTipText(toolTipText.toString());
			bookmarksMenu.add(menuItem);
		}
	}

	public void populateTaggedBookmarks() {
		JMenu bookmarksMenu = this.taggedBookmarksMenu;
		bookmarksMenu.removeAll();
		Collection<BookmarkInfo> bookmarkInfoList = BookmarksHistory
				.getInstance().getRecentItemInfo(
						PREFERRED_MAX_MENU_SIZE * PREFERRED_MAX_MENU_SIZE);
		Map<String, JMenu> tagMenus = new HashMap<String, JMenu>();
		for (BookmarkInfo binfo : bookmarkInfoList) {
			java.net.URL url = binfo.getUrl();
			String urlText = url.toExternalForm();
			String[] tags = binfo.getTags();
			if (tags != null) {
				for (String tag : tags) {
					JMenu tagMenu = tagMenus.get(tag);
					if (tagMenu == null) {
						if (tagMenus.size() < PREFERRED_MAX_MENU_SIZE) {
							tagMenu = new JMenu(tag);
							tagMenus.put(tag, tagMenu);
							bookmarksMenu.add(tagMenu);
						}
					}
					if (tagMenu != null
							&& tagMenu.getItemCount() < PREFERRED_MAX_MENU_SIZE) {
						String text = binfo.getTitle();
						if (text == null || text.length() == 0) {
							text = urlText;
						}
						Action action = this.actionPool
								.createBookmarkNavigateAction(url);
						JMenuItem menuItem = ComponentSource.menuItem(text,
								action);
						StringBuffer toolTipText = new StringBuffer();
						toolTipText.append("<html>");
						toolTipText.append(urlText);
						String description = binfo.getDescription();
						if (description != null && description.length() != 0) {
							toolTipText.append("<br>");
							toolTipText.append(description);
						}
						menuItem.setToolTipText(toolTipText.toString());
						tagMenu.add(menuItem);
					}
				}
			}
		}
	}

	public void populateBackMore() {
		NavigationEntry[] entries = this.window.getBackNavigationEntries();
		JMenu backMoreMenu = this.backMoreMenu;
		backMoreMenu.removeAll();
		for (NavigationEntry entry : entries) {
			String method = entry.getMethod();
			if ("GET".equals(method)) {
				String title = entry.getTitle();
				java.net.URL url = entry.getUrl();
				String text = title == null || title.length() == 0 ? url
						.toExternalForm() : title;
				Action action = this.actionPool.createGoToAction(entry);
				JMenuItem menuItem = menuItem(text, action);
				menuItem.setToolTipText(url.toExternalForm());
				backMoreMenu.add(menuItem);
			}
		}
		// backMoreMenu.revalidate();
	}

	public void populateForwardMore() {
		NavigationEntry[] entries = this.window.getForwardNavigationEntries();
		JMenu forwardMoreMenu = this.forwardMoreMenu;
		forwardMoreMenu.removeAll();
		for (NavigationEntry entry : entries) {
			String method = entry.getMethod();
			if ("GET".equals(method)) {
				String title = entry.getTitle();
				java.net.URL url = entry.getUrl();
				String text = title == null || title.length() == 0 ? url
						.toExternalForm() : title;
				Action action = this.actionPool.createGoToAction(entry);
				JMenuItem menuItem = menuItem(text, action);
				menuItem.setToolTipText(url.toExternalForm());
				forwardMoreMenu.add(menuItem);
			}
		}
	}

	public boolean hasRecentEntries() {
		return NavigationHistory.getInstance().hasRecentEntries();
	}

	public void populateRecentHosts() {
		JMenu recentHostsMenu = this.recentHostsMenu;
		recentHostsMenu.removeAll();
		Collection<HostEntry> hostEntries = NavigationHistory.getInstance()
				.getRecentHostEntries(PREFERRED_MAX_MENU_SIZE);
		for (HostEntry entry : hostEntries) {
			String urlText = "http://" + entry.host;
			try {
				java.net.URL url = new java.net.URL(urlText);
				long elapsed = System.currentTimeMillis() - entry.timestamp;
				String menuText = entry.host + " ("
						+ Timing.getElapsedText(elapsed) + " ago)";
				Action action = this.actionPool.createNavigateAction(url);
				JMenuItem menuItem = menuItem(menuText, action);
				menuItem.setToolTipText(url.toExternalForm());
				recentHostsMenu.add(menuItem);
			} catch (java.net.MalformedURLException mfu) {
				logger.log(Level.WARNING, "populateRecentHosts(): Bad URL="
						+ urlText, mfu);
			}
		}
	}

	public void showSource() {
		String sourceCode = window.getTopFrame().getSourceCode();
		if (sourceCode == null) {
			sourceCode = "";
		}
		TextViewerWindow window = new TextViewerWindow();
		window.setText(sourceCode);
		window.setSize(new Dimension(600, 400));
		window.setLocationByPlatform(true);
		window.setVisible(true);
	}

	public void showConsole() {
		TextViewerWindow window = new TextViewerWindow();
		window.setScrollsOnAppends(true);
		window.setSwingDocument(ConsoleModel.getStandard());
		window.setSize(new Dimension(600, 400));
		window.setLocationByPlatform(true);
		window.setVisible(true);
	}

	public void go() {
		this.navigateOrSearch();
	}

	public void navigateOrSearch() {
		String addressText = this.addressField.getText();
		if (addressText.indexOf('.') == -1 && addressText.indexOf('/') == -1
				&& addressText.indexOf(':') == -1) {
			this.search();
		} else {
			this.navigate(addressText, RequestType.ADDRESS_BAR);
		}
	}

	public void search() {
		ToolsSettings settings = ToolsSettings.getInstance();
		SearchEngine searchEngine = settings.getSelectedSearchEngine();
		if (searchEngine != null) {
			try {
				this.navigate(searchEngine.getURL(this.getAddressBarText()));
			} catch (java.net.MalformedURLException mfu) {
				window.getTopFrame().alert("Malformed search URL.");
			}
		}
	}

	private void populateSearchers() {
		JMenu searchersMenu = this.searchersMenu;
		searchersMenu.removeAll();
		final ToolsSettings settings = ToolsSettings.getInstance();
		Collection<SearchEngine> searchEngines = settings.getSearchEngines();
		SearchEngine selectedEngine = settings.getSelectedSearchEngine();
		if (searchEngines != null) {
			for (SearchEngine se : searchEngines) {
				final SearchEngine finalSe = se;
				JRadioButtonMenuItem item = new JRadioButtonMenuItem();
				item.setAction(new AbstractAction() {

					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {
						settings.setSelectedSearchEngine(finalSe);
						settings.save();
						ComponentSource.this.updateSearchButtonTooltip();
					}
				});
				item.setSelected(se == selectedEngine);
				item.setText(se.getName());
				item.setToolTipText(se.getDescription());
				searchersMenu.add(item);
			}
		}
	}

	public String getAddressBarText() {
		return this.addressField.getText();
	}
}
