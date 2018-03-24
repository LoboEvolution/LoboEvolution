package org.loboevolution.primary.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.primary.ext.ActionPool;
import org.loboevolution.primary.ext.ComponentSource;

public class InfoPageAction extends AbstractAction implements EnableableAction{

	private static final long serialVersionUID = 1L;
	
	/** The action. */
	private transient ActionPool action;
	
	/** The component source. */
	private transient ComponentSource componentSource;
	
	/**
	 * Instantiates a new source action.
	 * 
	 * @param window
	 *            the window
	 * @param action
	 *            the action
	 */
	public InfoPageAction(ComponentSource componentSource, ActionPool action) {
		this.action = action;
		this.componentSource = componentSource;
	}

	@Override
	public void updateEnabling() {
		action.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		componentSource.showInfoPage();	
	}	
}
