package com.jtattoo.plaf.lobo;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class LoboButton extends JButton implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

	public LoboButton() {
		setForeground(foreground());
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorder(new LineBorder(foreground()));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}