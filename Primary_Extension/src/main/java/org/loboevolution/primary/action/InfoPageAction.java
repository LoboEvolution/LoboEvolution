package org.loboevolution.primary.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.primary.ext.ActionPool;
import org.loboevolution.primary.ext.ComponentSource;
import org.loboevolution.ua.NavigatorWindow;

public class InfoPageAction extends AbstractAction implements EnableableAction{

	private static final long serialVersionUID = 1L;
	
	/** The window. */
	private transient NavigatorWindow window;

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
	public InfoPageAction(ComponentSource componentSource, NavigatorWindow window, ActionPool action) {
		this.action = action;
		this.window = window;
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
