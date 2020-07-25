package com.jtattoo.plaf.lobo;

import javax.swing.JSeparator;

public class LoboSeparator extends JSeparator implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

	public LoboSeparator() {
		setBackground(background());
		setForeground(foreground());
	}
}