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
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;
import java.net.URL;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.primary.gui.bookmarks.BookmarksHistory;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class BookmarkNavigateAction.
 */
public class BookmarkNavigateAction extends ActionPool {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The url. */
	private URL url;

	/** The component source. */
	private ComponentSource componentSource;

	/**
	 * Instantiates a new bookmark navigate action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public BookmarkNavigateAction(ComponentSource componentSource, NavigatorWindow window) {
		super(componentSource, window);
		this.componentSource = componentSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		BookmarksHistory.getInstance().touch(getUrl());
		componentSource.navigate(getUrl());
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

}
