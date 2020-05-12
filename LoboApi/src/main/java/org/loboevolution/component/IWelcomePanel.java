package org.loboevolution.component;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * <p>IWelcomePanel interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface IWelcomePanel {

	/**
	 * <p>setSize.</p>
	 *
	 * @param dim a {@link java.awt.Dimension} object.
	 */
	void setSize(Dimension dim);

	/**
	 * <p>setPreferredSize.</p>
	 *
	 * @param dim a {@link java.awt.Dimension} object.
	 */
	void setPreferredSize(Dimension dim);

	/**
	 * <p>repaint.</p>
	 */
	void repaint();
	
	/**
	 * <p>setBackground.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	void setBackground(Color color);
	
	/**
	 * <p>getWelocome.</p>
	 *
	 * @return a {@link javax.swing.JPanel} object.
	 */
	JPanel getWelocome();
}
