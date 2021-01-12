/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

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
