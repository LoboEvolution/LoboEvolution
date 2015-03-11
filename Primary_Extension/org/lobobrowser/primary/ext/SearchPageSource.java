/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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

import javax.swing.JMenu;
import javax.swing.JMenuItem;


/**
 * The Class SearchPageSource.
 */
public class SearchPageSource {

	/** The action pool. */
	private final ActionPool actionPool;

	/**
	 * Instantiates a new search page source.
	 *
	 * @param actionPool the action pool
	 */
	SearchPageSource(ActionPool actionPool) {
		this.actionPool = actionPool;
	}

	/**
	 * Gets the search menu.
	 *
	 * @return the search menu
	 */
	public JMenu getSearchMenu() {
		JMenu searchMenu = new JMenu("Search");
		searchMenu.setMnemonic('S');
		searchMenu.add(this.getGoogleSearchMenu());
		searchMenu.add(this.getYahooSearchMenu());
		searchMenu.add(this.getBingSearchMenu());
		return searchMenu;
	}

	/**
	 * Gets the google search menu.
	 *
	 * @return the google search menu
	 */
	private JMenuItem getGoogleSearchMenu() {
		return ComponentSource.menuItem("Google",
				this.actionPool.createNavigateAction("http://google.com"));
	}

	/**
	 * Gets the yahoo search menu.
	 *
	 * @return the yahoo search menu
	 */
	private JMenuItem getYahooSearchMenu() {
		return ComponentSource
				.menuItem("Yahoo!", this.actionPool
						.createNavigateAction("http://search.yahoo.com"));
	}

	/**
	 * Gets the bing search menu.
	 *
	 * @return the bing search menu
	 */
	private JMenuItem getBingSearchMenu() {
		return ComponentSource.menuItem("Bing",
				this.actionPool.createNavigateAction("http://www.bing.com"));
	}

}
