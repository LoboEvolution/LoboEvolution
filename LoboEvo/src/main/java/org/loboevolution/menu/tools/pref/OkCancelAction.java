package org.loboevolution.menu.tools.pref;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.init.GuiInit;

/**
 * The Class OkAction.
 *
 * @author utente
 * @version $Id: $Id
 */
public class OkCancelAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private transient PreferenceWindow prefer;

	/**
	 * <p>Constructor for OkCancelAction.</p>
	 *
	 * @param prefer a {@link org.loboevolution.menu.tools.pref.PreferenceWindow} object.
	 */
	public OkCancelAction(PreferenceWindow prefer) {
		this.prefer = prefer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			GuiInit.initLookAndFeel();
			this.prefer.dispose();
			this.prefer.getFrame().dispose();
			this.prefer.getFrame().setVisible(false);
			this.prefer.getFrame().setVisible(true);
		} catch (final Exception e1) {
			e1.printStackTrace();
		}

	}
}
