package org.loboevolution.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class Button extends JButton {

	private static final long serialVersionUID = 1L;

	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);

	public Button() {
		setForeground(COLOR_TEXT);
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorder(new LineBorder(COLOR_TEXT));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}
