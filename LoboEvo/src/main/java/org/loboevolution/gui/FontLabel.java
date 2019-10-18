package org.loboevolution.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class FontLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public FontLabel(String text) {
		super(text, SwingConstants.CENTER);
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setBackground(Color.white);
		setForeground(Color.black);
		setOpaque(true);
		setBorder(new LineBorder(Color.black));
		setPreferredSize(new Dimension(120, 40));
	}
}
