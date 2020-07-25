package org.loboevolution.menu.bookmarks;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;

/**
 * <p>AddBookmarkAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class AddBookmarkAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for AddBookmarkAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public AddBookmarkAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		final ToolBar toolbar = this.frame.getToolbar();
		final AddBookmarkWindow bookmark = new AddBookmarkWindow(toolbar.getAddressBar().getText());
		bookmark.setLocationByPlatform(true);
		bookmark.setVisible(true);
	}
}
