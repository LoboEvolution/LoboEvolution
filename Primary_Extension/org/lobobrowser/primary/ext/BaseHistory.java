/*
 GNU GENERAL PUBLIC LICENSE
 Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public
 License as published by the Free Software Foundation; either
 verion 2 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 General Public License for more details.

 You should have received a copy of the GNU General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.ext;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class BaseHistory<T> implements java.io.Serializable {
	private static final long serialVersionUID = 2257845020000200400L;

	protected BaseHistory() {
		super();
	}

	private final SortedSet<String> historySortedSet = new TreeSet<String>();
	private final Map<String, TimedEntry> historyMap = new HashMap<String, TimedEntry>();
	private final SortedSet<TimedEntry> historyTimedSet = new TreeSet<TimedEntry>();

	private final int commonEntriesCapacity = 1000;

	public boolean hasRecentEntries() {
		synchronized (this) {
			return this.historyTimedSet.size() > 0;
		}
	}

	public Collection<String> getRecentItems(int maxNumItems) {
		synchronized (this) {
			Collection<String> items = new LinkedList<String>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			int count = 0;
			while (i.hasNext() && count++ < maxNumItems) {
				TimedEntry entry = (TimedEntry) i.next();
				items.add(entry.value);
			}
			return items;
		}
	}

	public Collection<T> getRecentItemInfo(int maxNumItems) {
		synchronized (this) {
			Collection<T> items = new LinkedList<T>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			int count = 0;
			while (i.hasNext() && count++ < maxNumItems) {
				TimedEntry entry = (TimedEntry) i.next();
				items.add(entry.itemInfo);
			}
			return items;
		}
	}

	public Collection<HostEntry> getRecentHostEntries(int maxNumItems) {
		synchronized (this) {
			Collection<HostEntry> items = new LinkedList<HostEntry>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			Set<String> hosts = new HashSet<String>();
			while (i.hasNext()) {
				TimedEntry entry = (TimedEntry) i.next();
				String host = entry.url.getHost();
				if (host != null && host.length() != 0) {
					if (!hosts.contains(host)) {
						hosts.add(host);
						if (hosts.size() >= maxNumItems) {
							break;
						}
						items.add(new HostEntry(host, entry.timestamp));
					}
				}
			}
			return items;
		}
	}

	public Collection<HistoryEntry<T>> getAllEntries() {
		synchronized (this) {
			Collection<HistoryEntry<T>> items = new LinkedList<HistoryEntry<T>>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			while (i.hasNext()) {
				TimedEntry entry = (TimedEntry) i.next();
				items.add(new HistoryEntry<T>(entry.url, entry.timestamp,
						entry.itemInfo));
			}
			return items;
		}
	}

	public Collection<HistoryEntry<T>> getRecentEntries(int maxNumItems) {
		synchronized (this) {
			Collection<HistoryEntry<T>> items = new LinkedList<HistoryEntry<T>>();
			Iterator<TimedEntry> i = this.historyTimedSet.iterator();
			while (i.hasNext()) {
				TimedEntry entry = (TimedEntry) i.next();
				if (items.size() >= maxNumItems) {
					break;
				}
				items.add(new HistoryEntry<T>(entry.url, entry.timestamp,
						entry.itemInfo));
			}
			return items;
		}
	}

	public Collection<String> getHeadMatchItems(String itemPrefix,
			int maxNumItems) {
		synchronized (this) {
			Object[] array = this.historySortedSet.toArray();
			int idx = Arrays.binarySearch(array, itemPrefix);
			int startIdx = idx >= 0 ? idx : (-idx - 1);
			int count = 0;
			Collection<String> items = new LinkedList<String>();
			for (int i = startIdx; i < array.length && (count++ < maxNumItems); i++) {
				String potentialItem = (String) array[i];
				if (potentialItem.startsWith(itemPrefix)) {
					items.add(potentialItem);
				} else {
					break;
				}
			}
			return items;
		}
	}

	public void addAsRecent(java.net.URL url, T itemInfo) {
		String item = url.toExternalForm();
		synchronized (this) {
			TimedEntry entry = (TimedEntry) this.historyMap.get(item);
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
					TimedEntry entryToRemove = (TimedEntry) this.historyTimedSet
							.last();
					this.historyMap.remove(entryToRemove.value);
					this.historySortedSet.remove(entryToRemove.value);
					this.historyTimedSet.remove(entryToRemove);
				}
			}
		}
	}

	public void touch(java.net.URL url) {
		String item = url.toExternalForm();
		synchronized (this) {
			TimedEntry entry = (TimedEntry) this.historyMap.get(item);
			if (entry != null) {
				this.historyTimedSet.remove(entry);
				entry.touch();
				this.historyTimedSet.add(entry);
			}
		}
	}

	public T getExistingInfo(String item) {
		TimedEntry entry = this.historyMap.get(item);
		return entry == null ? null : entry.itemInfo;
	}

	private class TimedEntry implements Comparable<Object>,
			java.io.Serializable {
		private static final long serialVersionUID = 2257845000000000200L;
		private long timestamp = System.currentTimeMillis();
		private final java.net.URL url;
		private final String value;
		private T itemInfo;

		/**
		 * @param url
		 */
		public TimedEntry(java.net.URL url, String textValue, T itemInfo) {
			this.itemInfo = itemInfo;
			this.value = textValue;
			this.url = url;
		}

		public void touch() {
			this.timestamp = System.currentTimeMillis();
		}

		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			TimedEntry other = (TimedEntry) obj;
			return other.value.equals(this.value);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
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