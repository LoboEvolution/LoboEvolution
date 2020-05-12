package org.loboevolution.menu.bookmarks;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

/**
 * <p>ShowBookmarksAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ShowBookmarksAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	private final Integer num;

	/**
	 * <p>Constructor for ShowBookmarksAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 * @param num a {@link java.lang.Integer} object.
	 */
	public ShowBookmarksAction(BrowserFrame frame, Integer num) {
		this.frame = frame;
		this.num = num;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		final ShowBookmarksWindow bookmark = new ShowBookmarksWindow(this.frame, this.num);
		bookmark.setSize(new Dimension(600, 400));
		bookmark.setLocationByPlatform(true);
		bookmark.setVisible(true);
	}

}
