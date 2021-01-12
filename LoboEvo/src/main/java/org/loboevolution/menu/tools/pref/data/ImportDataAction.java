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

package org.loboevolution.menu.tools.pref.data;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jtattoo.plaf.lobo.LoboCheckBox;

/**
 * <p>ImportDataAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ImportDataAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final String action;

	/** The chrome panel. */
	private final LoboCheckBox chrome;

	/** The mozilla panel. */
	private final LoboCheckBox mozilla;

	/**
	 * <p>Constructor for ImportDataAction.</p>
	 *
	 * @param chrome a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param mozilla a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param action a {@link java.lang.String} object.
	 */
	public ImportDataAction(LoboCheckBox chrome, LoboCheckBox mozilla, String action) {
		this.chrome = chrome;
		this.mozilla = mozilla;
		this.action = action;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent evt) {
		switch (this.action) {
		case "HISTORY":
			importHistory();
			break;
		case "BOOKMARKS":
			importBookmarks();
			break;
		case "COOKIES":
			importCookie();
			break;

		default:
			break;
		}
	}

	private void importBookmarks() {
		if (this.mozilla.isSelected()) {
			MozilaFirefoxData.importBookmark();
		}

		if (this.chrome.isSelected()) {
			GoogleChromeData.importBookmark();
		}
	}

	private void importCookie() {
		if (this.mozilla.isSelected()) {
			MozilaFirefoxData.importCookie();
		}

		if (this.chrome.isSelected()) {
			GoogleChromeData.importCookie();
		}
	}

	private void importHistory() {
		if (this.mozilla.isSelected()) {
			MozilaFirefoxData.importHistory();
		}

		if (this.chrome.isSelected()) {
			GoogleChromeData.importHistory();
		}
	}
}
