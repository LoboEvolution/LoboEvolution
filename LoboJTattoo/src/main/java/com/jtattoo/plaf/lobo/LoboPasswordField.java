package com.jtattoo.plaf.lobo;

import java.awt.Color;

import javax.swing.JPasswordField;

public class LoboPasswordField extends JPasswordField implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;
	
	public LoboPasswordField() {
		final Color fground = foreground();
		final Color bkg = background();
		setBackground(bkg);
		setCaretColor(fground);
		setForeground(fground);
		setEchoChar('*');
	}
}
