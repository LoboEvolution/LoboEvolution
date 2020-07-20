package com.jtattoo.plaf.lobo;

import java.awt.Color;

import javax.swing.JRadioButton;

public class LoboRadioButton extends JRadioButton {
	
private static final long serialVersionUID = 1L;
	
	/** The color background. */
	private final Color COLOR_BACKGROUND = new Color(37, 51, 61);
	
	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);
	
	public LoboRadioButton(String name) {
		super(name);
		setBackground(COLOR_BACKGROUND);
		setForeground(COLOR_TEXT);
	}

}
