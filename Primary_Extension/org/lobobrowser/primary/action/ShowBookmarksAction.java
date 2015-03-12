/**
 * 
 */
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class ShowBookmarksAction.
 */
public class ShowBookmarksAction extends ActionPool {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private NavigatorWindow window;

	/**
	 * Instantiates a new show bookmarks action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public ShowBookmarksAction(ComponentSource componentSource,
			NavigatorWindow window) {
		super(componentSource, window);
		this.window = window;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			window.getTopFrame().navigate("about:bookmarks");
		} catch (MalformedURLException mfu) {
			throw new IllegalStateException("not expected", mfu);
		}
	}

}
