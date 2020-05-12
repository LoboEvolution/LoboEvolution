package org.loboevolution.menu.tools.clear;

import java.io.File;

import org.loboevolution.http.CookieManager;
import org.loboevolution.store.BookmarksStore;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.SQLiteCommon;
import org.loboevolution.store.ToolsStore;

/**
 * <p>ClearHistory class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ClearHistory {

	/**
	 * Clear Bookmarks.
	 */
	protected void clearBookmarks() {
		final BookmarksStore hist = new BookmarksStore();
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
		final ToolsStore ts = new ToolsStore();
		ts.deleteSearchEngine();
		final NavigationStore hist = new NavigationStore();
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
