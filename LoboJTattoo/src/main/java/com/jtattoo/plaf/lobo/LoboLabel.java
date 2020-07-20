package com.jtattoo.plaf.lobo;

import javax.swing.JLabel;

public class LoboLabel extends JLabel implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;
	
	public LoboLabel(String name) {
		super(name);
		setForeground(foreground());
	}
}
