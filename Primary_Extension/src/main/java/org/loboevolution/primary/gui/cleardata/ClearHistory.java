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
package org.loboevolution.primary.gui.cleardata;

import java.io.File;

import org.loboevolution.html.control.input.BaseInputTextControl;
import org.loboevolution.primary.ext.history.NavigationHistory;
import org.loboevolution.primary.gui.bookmarks.BookmarksHistory;
import org.loboevolution.primary.settings.ToolsSettings;
import org.loboevolution.request.CookieStore;

import com.loboevolution.store.SQLiteCommon;

public class ClearHistory {

	/**
     * Clear Cache.
     */
	protected void clearCache() {
		File cacheHome = new File(SQLiteCommon.getCacheStore());
		deleteRecursive(cacheHome);
        cacheHome.mkdir();
    }

	/**
     * Clear Cookies
     */
	protected void clearCookies() {
		CookieStore.deleteCookies();
	}

	/**
     * Clear Bookmarks.
     */
	protected void clearNavigation() {
		ToolsSettings ts = new ToolsSettings();
		ts.deleteSearchEngine();
		NavigationHistory.deleteHost();
		BaseInputTextControl.deleteInput();
		
	}
	
	/**
     * Clear Bookmarks.
     */
	protected void clearBookmarks() {
		BookmarksHistory.deleteBookmarks();
	}
	
	/**
     * Delete recursive.
     *
     * @param rootDir
     *            the root dir
     */
    private void deleteRecursive(File rootDir) {

        File[] c = rootDir.listFiles();
        for (File file : c) {
            if (file.isDirectory()) {
                deleteRecursive(file);
                file.delete();
            } else {
                file.delete();
            }
        }
    }
}