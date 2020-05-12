package org.loboevolution.menu.tools.pref.data;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.gui.CheckBoxPanel;

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
	private final CheckBoxPanel chromePanel;

	/** The mozilla panel. */
	private final CheckBoxPanel mozillaPanel;

	/**
	 * <p>Constructor for ImportDataAction.</p>
	 *
	 * @param mozillaPanel a {@link org.loboevolution.gui.CheckBoxPanel} object.
	 * @param chromePanel a {@link org.loboevolution.gui.CheckBoxPanel} object.
	 * @param action a {@link java.lang.String} object.
	 */
	public ImportDataAction(CheckBoxPanel mozillaPanel, CheckBoxPanel chromePanel, String action) {
		this.mozillaPanel = mozillaPanel;
		this.chromePanel = chromePanel;
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
		if (this.mozillaPanel.isSelected()) {
			MozilaFirefoxData.importBookmark();
		}

		if (this.chromePanel.isSelected()) {
			GoogleChromeData.importBookmark();
		}
	}

	private void importCookie() {
		if (this.mozillaPanel.isSelected()) {
			MozilaFirefoxData.importCookie();
		}

		if (this.chromePanel.isSelected()) {
			GoogleChromeData.importCookie();
		}
	}

	private void importHistory() {
		if (this.mozillaPanel.isSelected()) {
			MozilaFirefoxData.importHistory();
		}

		if (this.chromePanel.isSelected()) {
			GoogleChromeData.importHistory();
		}
	}
}
