package org.loboevolution.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * <p>FontLabel class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class FontLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for FontLabel.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
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
