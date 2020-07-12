package org.loboevolution.menu.tools.clear;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.gui.CheckBox;

/**
 * <p>ClearDataAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ClearDataAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/** The bookmark . */
	private final CheckBox bookmark;

	/** The cache . */
	private final CheckBox cache;

	/** The cookie . */
	private final CheckBox cookie;

	/** The navigation . */
	private final CheckBox navigation;

	/**
	 * <p>Constructor for ClearDataAction.</p>
	 *
	 * @param cachePanel a {@link org.loboevolution.gui.CheckBox} object.
	 * @param cookiePanel a {@link org.loboevolution.gui.CheckBox} object.
	 * @param navigationPanel a {@link org.loboevolution.gui.CheckBox} object.
	 * @param bookmarkPanel a {@link org.loboevolution.gui.CheckBox} object.
	 */
	public ClearDataAction(CheckBox cache, CheckBox cookie, CheckBox navigation,
			CheckBox bookmark) {
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
