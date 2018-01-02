/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.primary.ext;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.util.Strings;

/**
 * The Class BaseHistory.
 *
 * @param <T>
 *            the generic type
 */
public abstract class BaseHistory<T> implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2257845020000200400L;
	
	/** The history sorted set. */
	private final SortedSet<String> historySortedSet = new TreeSet<String>();

	/** The history map. */
	private final Map<String, TimedEntry> historyMap = new HashMap<String, TimedEntry>();

	/** The history timed set. */
	private final SortedSet<TimedEntry> historyTimedSet = new TreeSet<TimedEntry>();

	/** The common entries capacity. */
	private final int commonEntriesCapacity = 1000;

	/**
	 * Instantiates a new base history.
	 */
	protected BaseHistory() {
		super();
	}

	/**
	 * Checks for recent entries.
	 *
	 * @return true, if successful
	 */
	public boolean hasRecentEntries() {
		synchronized (this) {
			return this.historyTimedSet.size() > 0;
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
		synchronized (this) {
			Collection<String> items = new LinkedList<String>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			int count = 0;
			while (i.hasNext() && count++ < maxNumItems) {
				TimedEntry entry = i.next();
				items.add(entry.value);
			}
			return items;
		}
	}

	/**
	 * Gets the recent item info.
	 *
	 * @param maxNumItems
	 *            the max num items
	 * @return the recent item info
	 */
	public Collection<T> getRecentItemInfo(int maxNumItems) {
		synchronized (this) {
			Collection<T> items = new LinkedList<T>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			int count = 0;
			while (i.hasNext() && count++ < maxNumItems) {
				TimedEntry entry = i.next();
				items.add(entry.itemInfo);
			}
			return items;
		}
	}

	/**
	 * Gets the recent host entries.
	 *
	 * @param maxNumItems
	 *            the max num items
	 * @return the recent host entries
	 */
	public Collection<HostEntry> getRecentHostEntries(int maxNumItems) {
		synchronized (this) {
			Collection<HostEntry> items = new LinkedList<HostEntry>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			Set<String> hosts = new HashSet<String>();
			while (i.hasNext()) {
				TimedEntry entry = i.next();
				String host = entry.url.getHost();
				if (Strings.isBlank(host) && !hosts.contains(host)) {

					hosts.add(host);
					if (hosts.size() >= maxNumItems) {
						break;
					}
				
					items.add(new HostEntry(host, entry.timestamp));
				}
			}
			return items;
		}
	}

	/**
	 * Gets the all entries.
	 *
	 * @return the all entries
	 */
	public Collection<HistoryEntry<T>> getAllEntries() {
		synchronized (this) {
			Collection<HistoryEntry<T>> items = new LinkedList<HistoryEntry<T>>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			while (i.hasNext()) {
				TimedEntry entry = i.next();
				items.add(new HistoryEntry<T>(entry.url, entry.timestamp, entry.itemInfo));
			}
			return items;
		}
	}

	/**
	 * Gets the recent entries.
	 *
	 * @param maxNumItems
	 *            the max num items
	 * @return the recent entries
	 */
	public Collection<HistoryEntry<T>> getRecentEntries(int maxNumItems) {
		synchronized (this) {
			Collection<HistoryEntry<T>> items = new LinkedList<HistoryEntry<T>>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			while (i.hasNext()) {
				TimedEntry entry = i.next();
				if (items.size() >= maxNumItems) {
					break;
				}
				items.add(new HistoryEntry<T>(entry.url, entry.timestamp, entry.itemInfo));
			}
			return items;
		}
	}

	/**
	 * Gets the head match items.
	 *
	 * @param itemPrefix
	 *            the item prefix
	 * @param maxNumItems
	 *            the max num items
	 * @return the head match items
	 */
	public Collection<String> getHeadMatchItems(String itemPrefix) {
		synchronized (this) {
			Object[] array = this.historySortedSet.toArray();
			Collection<String> items = new ArrayList<String>();
			for (Object element : array) {
				String potentialItem = (String) element;
				if (potentialItem.contains(itemPrefix)) {
					items.add(potentialItem);
				}
			}
			return items;
		}
	}

	/**
	 * Adds the as recent.
	 *
	 * @param url
	 *            the url
	 * @param itemInfo
	 *            the item info
	 */
	public void addAsRecent(URL url, T itemInfo) {
		String item = url.toExternalForm();
		synchronized (this) {
			TimedEntry entry = this.historyMap.get(item);
			if (entry != null) {
				this.historyTimedSet.remove(entry);
				entry.touch();
				entry.itemInfo = itemInfo;
				this.historyTimedSet.add(entry);
			} else {
				entry = new TimedEntry(url, item, itemInfo);
				this.historyTimedSet.add(entry);
				this.historyMap.put(item, entry);
				this.historySortedSet.add(item);
				while (this.historyTimedSet.size() > this.commonEntriesCapacity) {
					// Most outdated goes last
					TimedEntry entryToRemove = this.historyTimedSet.last();
					this.historyMap.remove(entryToRemove.value);
					this.historySortedSet.remove(entryToRemove.value);
					this.historyTimedSet.remove(entryToRemove);
				}
			}
		}
	}

	/**
	 * Touch.
	 *
	 * @param url
	 *            the url
	 */
	public void touch(URL url) {
		String item = url.toExternalForm();
		synchronized (this) {
			TimedEntry entry = this.historyMap.get(item);
			if (entry != null) {
				this.historyTimedSet.remove(entry);
				entry.touch();
				this.historyTimedSet.add(entry);
			}
		}
	}

	/**
	 * Gets the existing info.
	 *
	 * @param item
	 *            the item
	 * @return the existing info
	 */
	public T getExistingInfo(String item) {
		TimedEntry entry = this.historyMap.get(item);
		return entry == null ? null : entry.itemInfo;
	}

	/**
	 * The Class TimedEntry.
	 */
	private class TimedEntry implements Comparable<Object>, java.io.Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 2257845000000000200L;

		/** The timestamp. */
		private long timestamp = System.currentTimeMillis();

		/** The url. */
		private final URL url;

		/** The value. */
		private final String value;

		/** The item info. */
		private T itemInfo;

		/**
		 * Instantiates a new timed entry.
		 *
		 * @param url
		 *            the url
		 * @param textValue
		 *            the text value
		 * @param itemInfo
		 *            the item info
		 */
		public TimedEntry(URL url, String textValue, T itemInfo) {
			this.itemInfo = itemInfo;
			this.value = textValue;
			this.url = url;
		}

		/**
		 * Touch.
		 */
		public void touch() {
			this.timestamp = System.currentTimeMillis();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			TimedEntry other = (TimedEntry) obj;
			return other.value.equals(this.value);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(Object)
		 */
		@Override
		public int compareTo(Object arg0) {
			if (this.equals(arg0)) {
				return 0;
			}
			@SuppressWarnings("unchecked")
			TimedEntry other = (TimedEntry) arg0;
			long time1 = this.timestamp;
			long time2 = other.timestamp;
			if (time1 > time2) {
				// More recent goes first
				return -1;
			} else if (time2 > time1) {
				return +1;
			} else {
				return this.value.compareTo(other.value);
			}
		}
	}

}
