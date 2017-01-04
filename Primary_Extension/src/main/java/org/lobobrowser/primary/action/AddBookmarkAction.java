/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;
import java.net.URL;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.primary.gui.bookmarks.AddBookmarkDialog;
import org.lobobrowser.primary.gui.bookmarks.BookmarksHistory;
import org.lobobrowser.primary.info.BookmarkInfo;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class AddBookmarkAction.
 */
public class AddBookmarkAction extends ActionPool {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private NavigatorWindow window;

	/**
	 * Instantiates a new adds the bookmark action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public AddBookmarkAction(ComponentSource componentSource, NavigatorWindow window) {
		super(componentSource, window);
		this.window = window;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		addBookmark();
	}

	/**
	 * Adds the bookmark.
	 */
	public void addBookmark() {
		NavigationEntry entry = window.getCurrentNavigationEntry();
		if (entry != null) {
			URL url = entry.getUrl();
			BookmarksHistory history = BookmarksHistory.getInstance();
			BookmarkInfo existingInfo = history.getExistingInfo(url.toExternalForm());
			if (existingInfo == null) {
				existingInfo = new BookmarkInfo();
				existingInfo.setUrl(url);
				existingInfo.setTitle(entry.getTitle());
				existingInfo.setDescription(entry.getDescription());
			}
			java.awt.Window awtWindow = window.getAwtWindow();
			if (!(awtWindow instanceof java.awt.Frame)) {
				throw new IllegalStateException("Bookmaks dialog only supported when an AWT Frame is available.");
			}
			AddBookmarkDialog dialog = new AddBookmarkDialog((java.awt.Frame) awtWindow, true, existingInfo);
			dialog.setTitle("Add/Edit Bookmark");
			dialog.setLocationByPlatform(true);
			// dialog.setLocationRelativeTo(window.getAwtFrame());
			dialog.setResizable(false);
			dialog.pack();
			dialog.setVisible(true);
			BookmarkInfo info = dialog.getBookmarkInfo();
			if (info != null) {
				history.addAsRecent(info.getUrl(), info);
				history.save();
			}
		}
	}

}
