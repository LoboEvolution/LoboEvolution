package com.jtattoo.plaf.lobo;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class LoboPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/** The color background. */
	private final Color COLOR_BACKGROUND = new Color(37, 51, 61);
	
	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);
	
	public LoboPanel() {
		setBackground(COLOR_BACKGROUND);
	}
	
	public LoboPanel(String title) {
		setBackground(COLOR_BACKGROUND);
		TitledBorder border = new TitledBorder(new LineBorder(COLOR_TEXT), title);
		border.setTitleColor(COLOR_TEXT);
		setBorder(border);
	}

	public LoboPanel(LayoutManager layout, String title) {
		super(layout);
		setBackground(COLOR_BACKGROUND);
		TitledBorder border = new TitledBorder(new LineBorder(COLOR_TEXT), title);
		border.setTitleColor(COLOR_TEXT);
		setBorder(border);
	}
}
