package org.lobo.menu.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobo.component.BrowserFrame;
import org.lobo.component.ToolBar;
import org.lobo.http.CookieManager;

public class CookiePageAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public CookiePageAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final ToolBar toolbar = this.frame.getToolbar();
		final CookieWindow cookie = new CookieWindow(CookieManager.getCookieList(toolbar.getAddressBar().getText()));
		cookie.setSize(new Dimension(600, 400));
		cookie.setLocationByPlatform(true);
		cookie.setVisible(true);
	}
}
