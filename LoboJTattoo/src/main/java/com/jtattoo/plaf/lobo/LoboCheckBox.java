package com.jtattoo.plaf.lobo;

import javax.swing.JCheckBox;

/**
 * <p>LoboCheckBox class.</p>
 *
 *
 *
 */
public class LoboCheckBox extends JCheckBox implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>Constructor for LoboCheckBox.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public LoboCheckBox(String name) {
		super(name);
		setBackground(background());
		setForeground(foreground());
	}
}
