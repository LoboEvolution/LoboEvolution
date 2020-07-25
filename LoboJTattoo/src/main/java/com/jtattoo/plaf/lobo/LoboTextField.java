package com.jtattoo.plaf.lobo;

import java.awt.Color;

import javax.swing.JTextField;

public class LoboTextField extends JTextField implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;
	
	public LoboTextField() {
		final Color fground = foreground();
		final Color bkg = background();
		setBackground(bkg);
		setCaretColor(fground);
		setForeground(fground);
	}
}
