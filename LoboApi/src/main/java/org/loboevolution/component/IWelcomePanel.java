package org.loboevolution.component;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public interface IWelcomePanel {

	void setSize(Dimension dim);

	void setPreferredSize(Dimension dim);

	void repaint();
	
	void setBackground(Color color);
	
	JPanel getWelocome();
}
