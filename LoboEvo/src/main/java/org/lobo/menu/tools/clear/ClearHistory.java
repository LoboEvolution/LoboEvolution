package org.lobo.menu.tools.clear;

import java.io.File;

import org.lobo.http.CookieManager;
import org.lobo.http.NavigationHistory;
import org.lobo.menu.bookmarks.BookmarksHistory;
import org.lobo.menu.tools.pref.ToolsSettings;
import org.lobo.store.SQLiteCommon;

public class ClearHistory {

	/**
	 * Clear Bookmarks.
	 */
	protected void clearBookmarks() {
		final BookmarksHistory hist = new BookmarksHistory();
		hist.deleteBookmarks();
	}

	/**
	 * Clear Cache.
	 */
	protected void clearCache() {
		final File cacheHome = new File(SQLiteCommon.getCacheStore());
		deleteRecursive(cacheHome);
		cacheHome.mkdir();
	}

	/**
	 * Clear Cookies
	 */
	protected void clearCookies() {
		CookieManager.deleteCookies();
	}

	/**
	 * Clear Bookmarks.
	 */
	protected void clearNavigation() {
		final ToolsSettings ts = new ToolsSettings();
		ts.deleteSearchEngine();
		final NavigationHistory hist = new NavigationHistory();
		hist.deleteHost();

	}

	/**
	 * Delete recursive.
	 *
	 * @param rootDir the root dir
	 */
	private void deleteRecursive(File rootDir) {

		final File[] c = rootDir.listFiles();
		for (final File file : c) {
			if (file.isDirectory()) {
				deleteRecursive(file);
				file.delete();
			} else {
				file.delete();
			}
		}
	}
}
