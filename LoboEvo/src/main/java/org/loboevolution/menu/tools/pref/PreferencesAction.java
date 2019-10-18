package org.loboevolution.menu.tools.pref;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

public class PreferencesAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public PreferencesAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final PreferenceWindow pref = new PreferenceWindow(this.frame);
		pref.setSize(new Dimension(600, 500));
		pref.setLocationByPlatform(true);
		pref.setVisible(true);
	}

}
