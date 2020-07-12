package org.loboevolution.gui;

import java.awt.Color;

import javax.swing.JLabel;

public class Label extends JLabel {

	private static final long serialVersionUID = 1L;
		
	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);
	
	public Label(String name) {
		super(name);
		setForeground(COLOR_TEXT);
	}
}
