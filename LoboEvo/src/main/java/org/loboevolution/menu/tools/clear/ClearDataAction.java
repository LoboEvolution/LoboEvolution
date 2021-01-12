/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.menu.tools.clear;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jtattoo.plaf.lobo.LoboCheckBox;

/**
 * <p>ClearDataAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ClearDataAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/** The bookmark . */
	private final LoboCheckBox bookmark;

	/** The cache . */
	private final LoboCheckBox cache;

	/** The cookie . */
	private final LoboCheckBox cookie;

	/** The navigation . */
	private final LoboCheckBox navigation;

	/**
	 * <p>Constructor for ClearDataAction.</p>
	 *
	 * @param cache a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param cookie a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param navigation a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param bookmark a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 */
	public ClearDataAction(LoboCheckBox cache, LoboCheckBox cookie, LoboCheckBox navigation,
			LoboCheckBox bookmark) {
		this.cache = cache;
		this.cookie = cookie;
		this.navigation = navigation;
		this.bookmark = bookmark;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {

		final ClearHistory ch = new ClearHistory();

		if (this.cache.isSelected()) {
			ch.clearCache();
		}

		if (this.cookie.isSelected()) {
			ch.clearCookies();
		}

		if (this.navigation.isSelected()) {
			ch.clearNavigation();
		}

		if (this.bookmark.isSelected()) {
			ch.clearBookmarks();
		}
	}
}
