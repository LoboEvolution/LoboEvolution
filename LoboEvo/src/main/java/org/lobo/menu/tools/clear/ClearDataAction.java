package org.lobo.menu.tools.clear;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobo.gui.CheckBoxPanel;

public class ClearDataAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/** The bookmark panel. */
	private final CheckBoxPanel bookmarkPanel;

	/** The cache panel. */
	private final CheckBoxPanel cachePanel;

	/** The cookie panel. */
	private final CheckBoxPanel cookiePanel;

	/** The navigation panel. */
	private final CheckBoxPanel navigationPanel;

	public ClearDataAction(CheckBoxPanel cachePanel, CheckBoxPanel cookiePanel, CheckBoxPanel navigationPanel,
			CheckBoxPanel bookmarkPanel) {
		this.cachePanel = cachePanel;
		this.cookiePanel = cookiePanel;
		this.navigationPanel = navigationPanel;
		this.bookmarkPanel = bookmarkPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		final ClearHistory ch = new ClearHistory();

		if (this.cachePanel.isSelected()) {
			ch.clearCache();
		}

		if (this.cookiePanel.isSelected()) {
			ch.clearCookies();
		}

		if (this.navigationPanel.isSelected()) {
			ch.clearNavigation();
		}

		if (this.bookmarkPanel.isSelected()) {
			ch.clearBookmarks();
		}
	}
}
