package org.loboevolution.menu.tools.pref.data;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.gui.CheckBox;

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
	private final CheckBox chrome;

	/** The mozilla panel. */
	private final CheckBox mozilla;

	/**
	 * <p>Constructor for ImportDataAction.</p>
	 *
	 * @param mozillaPanel a {@link org.loboevolution.gui.CheckBox} object.
	 * @param chromePanel a {@link org.loboevolution.gui.CheckBox} object.
	 * @param action a {@link java.lang.String} object.
	 */
	public ImportDataAction(CheckBox chrome, CheckBox mozilla, String action) {
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
