/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.gui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.util.Urls;

/**
 * This class stores navigation back/forward state for a frame.
 * <p>
 * Note: This class is not thread safe on its own.
 */
public class NavigationEngine {

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(NavigationEngine.class.getName());

    /** The history. */
    private final ArrayList<NavigationEntry> history = new ArrayList<NavigationEntry>();

    /** The current index. */
    private int currentIndex = -1;

    /** Gets the current entry.
	 *
	 * @return the current entry
	 */
    public NavigationEntry getCurrentEntry() {
        try {
            return this.history.get(this.currentIndex);
        } catch (IndexOutOfBoundsException iob) {
            return null;
        }
    }

    /**
     * Adds the navigation entry.
     *
     * @param entry
     *            the entry
     */
    public void addNavigationEntry(NavigationEntry entry) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("addNavigationEntry(): entry=" + entry);
        }
        int newIndex = this.currentIndex + 1;
        if (newIndex == this.history.size()) {
            this.history.add(entry);
        } else {
            if (newIndex > this.history.size()) {
                throw new IllegalStateException("currentIndex="
                        + this.currentIndex + ",size=" + this.history.size());
            }
            this.history.set(newIndex, entry);
        }
        this.currentIndex = newIndex;
        int nextIndex = newIndex + 1;
        while (nextIndex < this.history.size()) {
            this.history.remove(nextIndex);
        }
    }

    /**
     * Checks for next.
     *
     * @return true, if successful
     */
    public boolean hasNext() {
        return (this.currentIndex + 1) < this.history.size();
    }

    /**
     * Checks for prev.
     *
     * @return true, if successful
     */
    public boolean hasPrev() {
        return this.currentIndex > 0;
    }

    /**
     * Checks for next with get.
     *
     * @return true, if successful
     */
    public boolean hasNextWithGET() {
        return this.hasNewEntryWithGET(+1);
    }

    /**
     * Checks for prev with get.
     *
     * @return true, if successful
     */
    public boolean hasPrevWithGET() {
        return this.hasNewEntryWithGET(-1);
    }

    /**
     * Checks for new entry with get.
     *
     * @param offset
     *            the offset
     * @return true, if successful
     */
    public boolean hasNewEntryWithGET(int offset) {
        int nextIndex = this.currentIndex;
        for (;;) {
            nextIndex += offset;
            if ((nextIndex >= 0) && (nextIndex < this.history.size())) {
                NavigationEntry entry = this.history.get(nextIndex);
                if ("GET".equals(entry.getMethod())) {
                    return true;
                } else {
                    continue;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Back.
     *
     * @return the navigation entry
     */
    public NavigationEntry back() {
        return this.move(-1);
    }

    /**
     * Forward.
     *
     * @return the navigation entry
     */
    public NavigationEntry forward() {
        return this.move(+1);
    }

    /**
     * Move.
     *
     * @param offset
     *            the offset
     * @return the navigation entry
     */
    public NavigationEntry move(int offset) {
        int nextIndex = this.currentIndex + offset;
        if ((nextIndex >= 0) && (nextIndex < this.history.size())) {
            this.currentIndex = nextIndex;
            return this.history.get(this.currentIndex);
        } else {
            return null;
        }
    }

    /**
     * Move to.
     *
     * @param entry
     *            the entry
     * @return true, if successful
     */
    public boolean moveTo(NavigationEntry entry) {
        int newIndex = this.history.indexOf(entry);
        if (newIndex == -1) {
            return false;
        }
        this.currentIndex = newIndex;
        return true;
    }

    /** Gets the forward navigation entries.
	 *
	 * @return the forward navigation entries
	 */
    public NavigationEntry[] getForwardNavigationEntries() {
        ArrayList<NavigationEntry> entries = new ArrayList<NavigationEntry>();
        int index = this.currentIndex + 1;
        int size = this.history.size();
        while (index < size) {
            entries.add(this.history.get(index));
            index++;
        }
        return entries.toArray(new NavigationEntry[0]);
    }

    /** Gets the back navigation entries.
	 *
	 * @return the back navigation entries
	 */
    public NavigationEntry[] getBackNavigationEntries() {
        ArrayList<NavigationEntry> entries = new ArrayList<NavigationEntry>();
        int index = this.currentIndex - 1;
        while (index >= 0) {
            entries.add(this.history.get(index));
            index--;
        }
        return entries.toArray(new NavigationEntry[0]);
    }

    /**
     * Find entry.
     *
     * @param absoluteURL
     *            the absolute url
     * @return the navigation entry
     */
    public NavigationEntry findEntry(String absoluteURL) {
        try {
            URL targetURL = Urls.guessURL(absoluteURL);
            for (NavigationEntry entry : this.history) {
                if (Urls.sameNoRefURL(targetURL, entry.getUrl())) {
                    return entry;
                }
            }
            return null;
        } catch (MalformedURLException mfu) {
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "findEntry(): URL is malformed: "
                        + absoluteURL, mfu);
            }
            return null;
        }
    }

    /** Gets the length.
	 *
	 * @return the length
	 */
    public int getLength() {
        return this.history.size();
    }
}
