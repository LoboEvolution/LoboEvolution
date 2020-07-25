package com.jtattoo.plaf.lobo;

import javax.swing.JCheckBox;

public class LoboCheckBox extends JCheckBox implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;
	
	public LoboCheckBox(String name) {
		super(name);
		setBackground(background());
		setForeground(foreground());
	}
}
