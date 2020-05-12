package org.loboevolution.menu.crono;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

/**
 * <p>ShowRecentHostsAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ShowRecentHostsAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for ShowRecentHostsAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public ShowRecentHostsAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		final ShowRecentWindow window = new ShowRecentWindow(this.frame);
		window.setLocationByPlatform(true);
		window.setResizable(false);
		window.setSize(new Dimension(600, 400));
		window.setVisible(true);

	}

}
