package org.lobo.menu.bookmarks;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobo.component.BrowserFrame;

public class ShowBookmarksAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	private final Integer num;

	public ShowBookmarksAction(BrowserFrame frame, Integer num) {
		this.frame = frame;
		this.num = num;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final ShowBookmarksWindow bookmark = new ShowBookmarksWindow(this.frame, this.num);
		bookmark.setSize(new Dimension(600, 400));
		bookmark.setLocationByPlatform(true);
		bookmark.setVisible(true);
	}

}
