package com.jtattoo.plaf.lobo;

import javax.swing.JRadioButton;

/**
 * <p>LoboRadioButton class.</p>
 *
 *
 *
 */
public class LoboRadioButton extends JRadioButton implements LoboLookAndFeel {
	
private static final long serialVersionUID = 1L;
		
	/**
	 * <p>Constructor for LoboRadioButton.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public LoboRadioButton(String name) {
		super(name);
		setBackground(background());
		setForeground(foreground());
	}
}
