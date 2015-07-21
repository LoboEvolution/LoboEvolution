/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.ext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.primary.info.BookmarkInfo;
import org.lobobrowser.store.StorageManager;

/**
 * The Class BookmarksHistory.
 */
public class BookmarksHistory extends BaseHistory<BookmarkInfo> implements
java.io.Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2257845000200000300L;

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(BookmarksHistory.class.getName());

    /** The Constant instance. */
    private static final BookmarksHistory instance;

    static {
        BookmarksHistory ins = null;
        try {
            ins = (BookmarksHistory) StorageManager.getInstance()
                    .retrieveSettings(BookmarksHistory.class.getSimpleName(),
                            BookmarksHistory.class.getClassLoader());
        } catch (Exception err) {
            logger.log(Level.WARNING, "Unable to retrieve settings.", err);
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
     * Gets the single instance of BookmarksHistory.
     *
     * @return single instance of BookmarksHistory
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
                StorageManager.getInstance().saveSettings(
                        this.getClass().getSimpleName(), this);
            } catch (IOException ioe) {
                logger.log(Level.WARNING, "Unable to save settings: "
                        + this.getClass().getSimpleName(), ioe);
            }
        }
    }
}
