package com.jtattoo.plaf.lobo;

import javax.swing.JRadioButton;

public class LoboRadioButton extends JRadioButton implements LoboLookAndFeel {
	
private static final long serialVersionUID = 1L;
		
	public LoboRadioButton(String name) {
		super(name);
		setBackground(background());
		setForeground(foreground());
	}
}
