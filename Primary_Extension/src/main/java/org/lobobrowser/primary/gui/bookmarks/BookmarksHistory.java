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
package org.lobobrowser.primary.gui.bookmarks;

import java.io.IOException;
import java.io.Serializable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.primary.ext.BaseHistory;
import org.lobobrowser.primary.info.BookmarkInfo;
import org.lobobrowser.store.StorageManager;

/**
 * The Class BookmarksHistory.
 */
public class BookmarksHistory extends BaseHistory<BookmarkInfo>implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2257845000200000300L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(BookmarksHistory.class);

	/** The Constant instance. */
	private static final BookmarksHistory instance;

	static {
		BookmarksHistory ins = null;
		try {
			ins = (BookmarksHistory) StorageManager.getInstance()
					.retrieveSettings(BookmarksHistory.class.getSimpleName(), BookmarksHistory.class.getClassLoader());
		} catch (Exception err) {
			logger.error("Unable to retrieve settings.", err);
		}
		if (ins == null) {
			ins = new BookmarksHistory();
		}
		instance = ins;
	}

	/**
	 * Instantiates a new bookmarks history.
	 */
	private BookmarksHistory() {
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static BookmarksHistory getInstance() {
		return instance;
	}

	/**
	 * Save.
	 */
	public void save() {
		synchronized (this) {
			try {
				StorageManager.getInstance().saveSettings(this.getClass().getSimpleName(), this);
			} catch (IOException ioe) {
				logger.error("Unable to save settings: " + this.getClass().getSimpleName(), ioe);
			}
		}
	}
}
