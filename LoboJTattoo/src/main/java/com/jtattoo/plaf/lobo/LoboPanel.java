package com.jtattoo.plaf.lobo;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * <p>LoboPanel class.</p>
 *
 *
 *
 */
public class LoboPanel extends JPanel implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for LoboPanel.</p>
	 */
	public LoboPanel() {
		setBackground(background());
	}
	
	/**
	 * <p>Constructor for LoboPanel.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	public LoboPanel(String title) {
		setBackground(background());
		TitledBorder border = new TitledBorder(new LineBorder(foreground()), title);
		border.setTitleColor(foreground());
		setBorder(border);
	}

	/**
	 * <p>Constructor for LoboPanel.</p>
	 *
	 * @param layout a {@link java.awt.LayoutManager} object.
	 * @param title a {@link java.lang.String} object.
	 */
	public LoboPanel(LayoutManager layout, String title) {
		super(layout);
		setBackground(background());
		TitledBorder border = new TitledBorder(new LineBorder(foreground()), title);
		border.setTitleColor(foreground());
		setBorder(border);
	}
}
