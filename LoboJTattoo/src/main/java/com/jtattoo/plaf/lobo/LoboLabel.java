package com.jtattoo.plaf.lobo;

import javax.swing.JLabel;

/**
 * <p>LoboLabel class.</p>
 *
 *
 *
 */
public class LoboLabel extends JLabel implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>Constructor for LoboLabel.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public LoboLabel(String name) {
		super(name);
		setForeground(foreground());
	}
}
