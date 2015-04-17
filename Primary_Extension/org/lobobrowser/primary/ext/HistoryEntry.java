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

import java.net.URL;

/**
 * The Class HistoryEntry.
 *
 * @param <T>
 *            the generic type
 */
public class HistoryEntry<T> {

    /** The url. */
    private final URL url;

    /** The timetstamp. */
    private final long timetstamp;

    /** The item info. */
    private final T itemInfo;

    /**
     * Instantiates a new history entry.
     *
     * @param url
     *            the url
     * @param timetstamp
     *            the timetstamp
     * @param itemInfo
     *            the item info
     */
    public HistoryEntry(final URL url, final long timetstamp, final T itemInfo) {
        super();
        this.url = url;
        this.timetstamp = timetstamp;
        this.itemInfo = itemInfo;
    }

    /**
     * Gets the item info.
     *
     * @return the item info
     */
    public T getItemInfo() {
        return itemInfo;
    }

    /**
     * Gets the timetstamp.
     *
     * @return the timetstamp
     */
    public long getTimetstamp() {
        return timetstamp;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public URL getUrl() {
        return url;
    }
}
