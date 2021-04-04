package com.jtattoo.plaf.lobo;

import javax.swing.JSeparator;

/**
 * <p>LoboSeparator class.</p>
 *
 *
 *
 */
public class LoboSeparator extends JSeparator implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for LoboSeparator.</p>
	 */
	public LoboSeparator() {
		setBackground(background());
		setForeground(foreground());
	}
}
