/**
 * 
 */
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.primary.gui.SearchDialog;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class SearchBookmarksAction.
 */
public class SearchBookmarksAction extends ActionPool {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private NavigatorWindow window;

	/**
	 * Instantiates a new search bookmarks action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public SearchBookmarksAction(ComponentSource componentSource,
			NavigatorWindow window) {
		super(componentSource, window);
		this.window = window;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.lobobrowser.primary.ext.ActionPool#actionPerformed(java.awt.event
	 * .ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		searchBookmarks();
	}

	/**
	 * Search bookmarks.
	 */
	private void searchBookmarks() {
		java.awt.Window awtWindow = window.getAwtWindow();
		if (!(awtWindow instanceof java.awt.Frame)) {
			throw new IllegalStateException(
					"Search dialog only supported when an AWT Frame is available.");
		}
		SearchDialog dialog = new SearchDialog((java.awt.Frame) awtWindow,
				true,
				"Keywords will be matched against URL, title, description and tags.");
		dialog.setTitle("Search Bookmarks");
		dialog.setLocationByPlatform(true);
		dialog.setResizable(false);
		dialog.setSize(new java.awt.Dimension(200, 100));
		dialog.setVisible(true);
		String keywordsText = dialog.getSearchKeywords();
		if (keywordsText != null) {
			try {
				window.getTopFrame().navigate(
						"about:bookmark-search?"
								+ java.net.URLEncoder.encode(keywordsText,
										"UTF-8"));
			} catch (Exception thrown) {
				throw new IllegalStateException("not expected", thrown);
			}
		}
	}

}
