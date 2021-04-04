package com.jtattoo.plaf.lobo;

import java.awt.Color;

import javax.swing.JPasswordField;

/**
 * <p>LoboPasswordField class.</p>
 *
 *
 *
 */
public class LoboPasswordField extends JPasswordField implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>Constructor for LoboPasswordField.</p>
	 */
	public LoboPasswordField() {
		final Color fground = foreground();
		final Color bkg = background();
		setBackground(bkg);
		setCaretColor(fground);
		setForeground(fground);
		setEchoChar('*');
	}
}
