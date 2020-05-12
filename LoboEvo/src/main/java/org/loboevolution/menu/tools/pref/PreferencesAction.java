package org.loboevolution.menu.tools.pref;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

/**
 * <p>PreferencesAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class PreferencesAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for PreferencesAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public PreferencesAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		final PreferenceWindow pref = new PreferenceWindow(this.frame);
		pref.setSize(new Dimension(600, 500));
		pref.setLocationByPlatform(true);
		pref.setVisible(true);
	}

}
