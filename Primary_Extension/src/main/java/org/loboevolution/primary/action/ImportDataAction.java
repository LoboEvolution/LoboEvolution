/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.primary.gui.CheckBoxPanel;
import org.loboevolution.primary.settings.GoogleChromeData;
import org.loboevolution.primary.settings.MozilaFirefoxData;

public class ImportDataAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/** The mozilla panel. */
	private final CheckBoxPanel mozillaPanel;

	/** The chrome panel. */
	private final CheckBoxPanel chromePanel;

	private String action;

	public ImportDataAction(CheckBoxPanel mozillaPanel, CheckBoxPanel chromePanel, String action) {
		this.mozillaPanel = mozillaPanel;
		this.chromePanel = chromePanel;
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (action) {
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

	private void importHistory() {
		if (mozillaPanel.isSelected()) {
			MozilaFirefoxData.importHistory();
		}

		if (chromePanel.isSelected()) {
			GoogleChromeData.importHistory();
		}
	}

	private void importBookmarks() {
		if (mozillaPanel.isSelected()) {
			MozilaFirefoxData.importBookmark();
		}

		if (chromePanel.isSelected()) {
			GoogleChromeData.importBookmark();
		}
	}

	private void importCookie() {
		if (mozillaPanel.isSelected()) {
			MozilaFirefoxData.importCookie();
		}

		if (chromePanel.isSelected()) {
			GoogleChromeData.importCookie();
		}
	}
}
