/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
/*
 * Created on Jun 6, 2005
 */
package org.lobobrowser.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The Class History.
 *
 * @author J. H. S.
 */
public class History implements java.io.Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2257845000800300100L;
    /** The history sequence. */
    private transient ArrayList<String> historySequence;
    /** The history sorted set. */
    private final SortedSet<String> historySortedSet = new TreeSet<String>();
    /** The history map. */
    private final Map<String, TimedEntry> historyMap = new HashMap<String, TimedEntry>();
    /** The history timed set. */
    private final SortedSet<TimedEntry> historyTimedSet = new TreeSet<TimedEntry>();
    /** The sequence capacity. */
    private int sequenceCapacity;
    /** The common entries capacity. */
    private int commonEntriesCapacity;
    /** The sequence index. */
    private transient int sequenceIndex;
    
    /**
     * Instantiates a new history.
     *
     * @param sequenceCapacity
     *            the sequence capacity
     * @param commonEntriesCapacity
     *            the common entries capacity
     */
    public History(int sequenceCapacity, int commonEntriesCapacity) {
        super();
        this.historySequence = new ArrayList<String>();
        this.sequenceIndex = -1;
        this.sequenceCapacity = sequenceCapacity;
        this.commonEntriesCapacity = commonEntriesCapacity;
    }
    
    /**
     * Read object.
     *
     * @param in
     *            the in
     * @throws ClassNotFoundException
     *             the class not found exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void readObject(ObjectInputStream in)
            throws ClassNotFoundException, IOException {
        this.historySequence = new ArrayList<String>();
        this.sequenceIndex = -1;
        in.defaultReadObject();
    }
    
    /** Gets the common entries capacity.
	 *
	 * @return the common entries capacity
	 */
    public int getCommonEntriesCapacity() {
        return commonEntriesCapacity;
    }
    
    /** Sets the common entries capacity.
	 *
	 * @param commonEntriesCapacity
	 *            the new common entries capacity
	 */
    public void setCommonEntriesCapacity(int commonEntriesCapacity) {
        this.commonEntriesCapacity = commonEntriesCapacity;
    }
    
    /** Gets the sequence capacity.
	 *
	 * @return the sequence capacity
	 */
    public int getSequenceCapacity() {
        return sequenceCapacity;
    }
    
    /** Sets the sequence capacity.
	 *
	 * @param sequenceCapacity
	 *            the new sequence capacity
	 */
    public void setSequenceCapacity(int sequenceCapacity) {
        this.sequenceCapacity = sequenceCapacity;
    }
    
    /** Gets the current item.
	 *
	 * @return the current item
	 */
    public String getCurrentItem() {
        if (this.sequenceIndex >= 0) {
            return this.historySequence.get(this.sequenceIndex);
        } else {
            return null;
        }
    }
    
    /**
     * Back.
     *
     * @return the string
     */
    public String back() {
        if (this.sequenceIndex > 0) {
            this.sequenceIndex--;
            return this.getCurrentItem();
        } else {
            return null;
        }
    }
    
    /**
     * Forward.
     *
     * @return the string
     */
    public String forward() {
        if ((this.sequenceIndex + 1) < this.historySequence.size()) {
            this.sequenceIndex++;
            return this.getCurrentItem();
        } else {
            return null;
        }
    }
    
    /**
     * Gets the recent items.
     *
     * @param maxNumItems
     *            the max num items
     * @return the recent items
     */
    public Collection<String> getRecentItems(int maxNumItems) {
        Collection<String> items = new LinkedList<String>();
        Iterator<TimedEntry> i = this.historyTimedSet.iterator();
        int count = 0;
        while (i.hasNext() && (count++ < maxNumItems)) {
            TimedEntry entry = i.next();
            items.add(entry.value);
        }
        return items;
    }
    
    /**
     * Gets the head match items.
     *
     * @param item
     *            the item
     * @param maxNumItems
     *            the max num items
     * @return the head match items
     */
    public Collection<String> getHeadMatchItems(String item, int maxNumItems) {
        Object[] array = this.historySortedSet.toArray();
        int idx = Arrays.binarySearch(array, item);
        int startIdx = idx >= 0 ? idx : (-idx - 1);
        int count = 0;
        Collection<String> items = new LinkedList<String>();
        for (int i = startIdx; (i < array.length)
                && (count++ < maxNumItems); i++) {
            String potentialItem = (String) array[i];
            if (potentialItem.startsWith(item)) {
                items.add(potentialItem);
            } else {
                break;
            }
        }
        return items;
    }
    
    /**
     * Adds the as recent only.
     *
     * @param item
     *            the item
     */
    public void addAsRecentOnly(String item) {
        TimedEntry entry = this.historyMap.get(item);
        if (entry != null) {
            this.historyTimedSet.remove(entry);
            entry.touch();
            this.historyTimedSet.add(entry);
        } else {
            entry = new TimedEntry(item);
            this.historyTimedSet.add(entry);
            this.historyMap.put(item, entry);
            this.historySortedSet.add(item);
            if (this.historyTimedSet.size() > this.commonEntriesCapacity) {
                // Most outdated goes last
                TimedEntry entryToRemove = this.historyTimedSet.last();
                this.historyMap.remove(entryToRemove.value);
                this.historySortedSet.remove(entryToRemove.value);
                this.historyTimedSet.remove(entryToRemove);
            }
        }
    }
    
    /**
     * Adds the item.
     *
     * @param item
     *            the item
     * @param updateAsRecent
     *            the update as recent
     */
    public void addItem(String item, boolean updateAsRecent) {
        int newIndex = this.sequenceIndex + 1;
        while (newIndex >= this.historySequence.size()) {
            this.historySequence.add(null);
        }
        this.historySequence.set(newIndex, item);
        this.sequenceIndex = newIndex;
        int expectedSize = newIndex + 1;
        while (this.historySequence.size() > expectedSize) {
            this.historySequence.remove(expectedSize);
        }
        while (this.historySequence.size() > this.sequenceCapacity) {
            this.historySequence.remove(0);
            this.sequenceIndex--;
        }
        if (updateAsRecent) {
            this.addAsRecentOnly(item);
        }
    }
}
