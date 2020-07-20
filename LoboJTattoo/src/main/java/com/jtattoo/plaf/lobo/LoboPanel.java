package com.jtattoo.plaf.lobo;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class LoboPanel extends JPanel implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

	public LoboPanel() {
		setBackground(background());
	}
	
	public LoboPanel(String title) {
		setBackground(background());
		TitledBorder border = new TitledBorder(new LineBorder(foreground()), title);
		border.setTitleColor(foreground());
		setBorder(border);
	}

	public LoboPanel(LayoutManager layout, String title) {
		super(layout);
		setBackground(background());
		TitledBorder border = new TitledBorder(new LineBorder(foreground()), title);
		border.setTitleColor(foreground());
		setBorder(border);
	}
}
