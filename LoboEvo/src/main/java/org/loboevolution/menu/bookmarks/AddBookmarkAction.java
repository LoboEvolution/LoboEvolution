package org.loboevolution.menu.bookmarks;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;

public class AddBookmarkAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public AddBookmarkAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final ToolBar toolbar = this.frame.getToolbar();
		final AddBookmarkWindow bookmark = new AddBookmarkWindow(toolbar.getAddressBar().getText());
		bookmark.setSize(new Dimension(400, 150));
		bookmark.setLocationByPlatform(true);
		bookmark.setVisible(true);
	}
}
