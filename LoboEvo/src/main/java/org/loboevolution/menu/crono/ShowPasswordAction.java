package org.loboevolution.menu.crono;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

/**
 * <p>ShowPasswordAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ShowPasswordAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	private final Integer num;

	/**
	 * <p>Constructor for ShowPasswordAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 * @param num a {@link java.lang.Integer} object.
	 */
	public ShowPasswordAction(BrowserFrame frame, Integer num) {
		this.frame = frame;
		this.num = num;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		final ShowPasswordWindow bookmark = new ShowPasswordWindow(this.frame, this.num);
		bookmark.setLocationByPlatform(true);
		bookmark.setVisible(true);
	}

}