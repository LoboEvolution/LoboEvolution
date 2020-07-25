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
	 * @param cachePanel a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param cookiePanel a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param navigationPanel a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param bookmarkPanel a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
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
