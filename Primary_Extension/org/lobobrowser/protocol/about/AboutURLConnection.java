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
/*
 * Created on Mar 14, 2005
 */
package org.lobobrowser.protocol.about;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.TreeSet;

import org.lobobrowser.primary.ext.BookmarkInfo;
import org.lobobrowser.primary.ext.BookmarksHistory;
import org.lobobrowser.primary.ext.HistoryEntry;
import org.lobobrowser.util.Strings;
import org.lobobrowser.util.Timing;

/**
 * @author J. H. S.
 */
public class AboutURLConnection extends URLConnection {
	public AboutURLConnection(URL url) {
		super(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	public void connect() throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getContentLength()
	 */
	public int getContentLength() {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getContentType()
	 */
	public String getContentType() {
		return "text/html";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(this.getURLText(this.getURL())
				.getBytes("UTF-8"));
	}

	private String getURLText(URL url) {
		String path = url.getPath();
		if ("blank".equalsIgnoreCase(path)) {
			return "";
		} else if ("bookmarks".equalsIgnoreCase(path)) {
			return this.getBookmarks();
		} else if ("bookmark-search".equalsIgnoreCase(path)) {
			String query = url.getQuery();
			if (query == null) {
				query = "";
			}
			try {
				String searchQuery = java.net.URLDecoder.decode(query, "UTF-8");
				return this.getBookmarks(searchQuery);
			} catch (UnsupportedEncodingException uee) {
				throw new IllegalStateException("not expected", uee);
			}
		} else if ("java-properties".equals(path)) {
			return this.getSystemProperties();
		} else {
			return "[Unknown about path: " + path + "]";
		}
	}

	private String getSystemProperties() {
		StringWriter swriter = new StringWriter();
		PrintWriter writer = new PrintWriter(swriter);
		writer.println("<html>");
		writer.println("<head><title>Java Properties</title></head>");
		writer.println("<body>");
		writer.println("<pre>");
		Properties properties = System.getProperties();
		properties.list(writer);
		writer.println("</pre>");
		writer.println("</body>");
		writer.println("</html>");
		writer.flush();
		return swriter.toString();
	}

	private String getBookmarks() {
		BookmarksHistory history = BookmarksHistory.getInstance();
		return this.getBookmarks(history.getAllEntries());
	}

	private String getBookmarks(String searchQuery) {
		// This is more of a scan. Not efficient but it does the
		// job for now considering the number of entries is limited.
		String[] keywords = Strings.split(searchQuery);
		BookmarksHistory history = BookmarksHistory.getInstance();
		Collection<HistoryEntry<BookmarkInfo>> entries = history
				.getAllEntries();
		Collection<ScoredEntry> sortedEntries = new TreeSet<ScoredEntry>();
		for (HistoryEntry<BookmarkInfo> entry : entries) {
			int matchScore = this.getMatchScore(entry.getItemInfo(), keywords);
			if (matchScore > 0) {
				sortedEntries.add(new ScoredEntry(entry, matchScore));
			}
		}
		Collection<HistoryEntry<BookmarkInfo>> finalEntries = new ArrayList<HistoryEntry<BookmarkInfo>>();
		for (ScoredEntry scoredEntry : sortedEntries) {
			finalEntries.add(scoredEntry.getHistoryEntry());
		}
		return this.getBookmarks(finalEntries);
	}

	private int getMatchScore(BookmarkInfo binfo, String[] keywords) {
		int total = 0;
		for (int i = 0; i < keywords.length; i++) {
			String keyword = keywords[i];
			int score = this.getMatchScore(binfo, keyword);
			if (score == 0) {
				return 0;
			}
			total += score;
		}
		return total;
	}

	private int getMatchScore(BookmarkInfo binfo, String keyword) {
		String keywordTL = keyword.toLowerCase();
		int score = 0;
		String urlText = binfo.getUrl().toExternalForm();
		if (urlText.contains(keyword)) {
			score += 3;
		} else if (urlText.toLowerCase().contains(keywordTL)) {
			score += 2;
		}
		String title = binfo.getTitle();
		if (title != null && title.contains(keyword)) {
			score += 8;
		} else if (title != null && title.toLowerCase().contains(keywordTL)) {
			score += 6;
		}
		String description = binfo.getDescription();
		if (description != null && description.contains(keyword)) {
			score += 3;
		} else if (description != null
				&& description.toLowerCase().contains(keywordTL)) {
			score += 2;
		}
		String[] tags = binfo.getTags();
		if (tags != null) {
			for (int i = 0; i < tags.length; i++) {
				if (tags[i].equals(keyword)) {
					score += 8;
				} else if (tags[i].toLowerCase().equals(keywordTL)) {
					score += 6;
				}
			}
		}
		return score;
	}

	private String getBookmarks(Collection<HistoryEntry<BookmarkInfo>> entries) {
		StringWriter swriter = new StringWriter();
		PrintWriter writer = new PrintWriter(swriter);
		writer.println("<html>");
		writer.println("<head>Bookmarks</head>");
		writer.println("<body>");
		if (entries.size() == 0) {
			writer.println("No bookmarks were found.");
		} else {
			writer.println("<h3>Bookmarks</h3>");
			writer.println("<ol>");
			for (HistoryEntry<BookmarkInfo> entry : entries) {
				this.writeBookmark(writer, entry);
			}
			writer.println("</ol>");
		}
		writer.println("</body>");
		writer.println("</html>");
		writer.flush();
		return swriter.toString();
	}

	private void writeBookmark(PrintWriter writer,
			HistoryEntry<BookmarkInfo> entry) {
		URL url = entry.getUrl();
		String urlText = url.toExternalForm();
		BookmarkInfo binfo = entry.getItemInfo();
		String text = binfo.getTitle();
		if (text == null || text.length() == 0) {
			text = urlText;
		}
		long elapsed = System.currentTimeMillis() - entry.getTimetstamp();
		String description = binfo.getDescription();
		if (description == null) {
			description = "";
		}
		writer.println("<LI>");
		writer.println("<DIV>");
		writer.println("<A href=\"" + urlText + "\">" + text + "</A> ("
				+ Timing.getElapsedText(elapsed) + " ago)");
		writer.println("</DIV>");
		writer.println("<DIV>");
		writer.println(description);
		writer.println("</DIV>");
		writer.println("</LI>");
	}

	private class ScoredEntry implements Comparable<Object> {
		private final HistoryEntry<BookmarkInfo> historyEntry;
		private final int score;

		public ScoredEntry(final HistoryEntry<BookmarkInfo> historyEntry,
				final int score) {
			super();
			this.historyEntry = historyEntry;
			this.score = score;
		}

		public HistoryEntry<BookmarkInfo> getHistoryEntry() {
			return historyEntry;
		}

		public int compareTo(Object o) {
			if (this == o) {
				return 0;
			}
			ScoredEntry other = (ScoredEntry) o;
			int diff = other.score - this.score;
			if (diff != 0) {
				return diff;
			}
			diff = (int) (other.historyEntry.getTimetstamp() - this.historyEntry
					.getTimetstamp());
			if (diff != 0) {
				return diff;
			}
			diff = System.identityHashCode(other)
					- System.identityHashCode(this);
			if (diff != 0) {
				return diff;
			} else {
				return System.identityHashCode(other.historyEntry)
						- System.identityHashCode(this.historyEntry);
			}
		}

		public int hashCode() {
			return this.score;
		}
	}
}
