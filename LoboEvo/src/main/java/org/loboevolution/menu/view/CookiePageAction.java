package org.loboevolution.menu.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.http.CookieManager;

/**
 * <p>CookiePageAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CookiePageAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for CookiePageAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public CookiePageAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		final ToolBar toolbar = this.frame.getToolbar();
		final CookieWindow cookie = new CookieWindow(CookieManager.getCookieList(toolbar.getAddressBar().getText()));
		cookie.setSize(new Dimension(600, 400));
		cookie.setLocationByPlatform(true);
		cookie.setVisible(true);
	}
}
