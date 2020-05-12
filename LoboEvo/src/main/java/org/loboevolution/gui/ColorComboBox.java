package org.loboevolution.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * <p>ColorComboBox class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ColorComboBox extends JComboBox<Object> {

	class ColorComboRenderer extends JPanel implements ListCellRenderer<Object> {
		private static final long serialVersionUID = 1L;
		protected Color m_c = Color.black;

		public ColorComboRenderer() {
			super();
			setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, Color.white), new LineBorder(Color.black)));
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object obj, int row, boolean sel,
				boolean hasFocus) {
			if (obj instanceof Color) {
				this.m_c = (Color) obj;
			}
			return this;
		}

		@Override
		public void paint(Graphics g) {
			setBackground(this.m_c);
			super.paint(g);
		}

	}

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ColorComboBox.</p>
	 */
	public ColorComboBox() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		final int[] values = new int[] { 0, 128, 192, 255 };
		for (final int value : values) {
			for (final int value2 : values) {
				for (final int value3 : values) {
					final Color c = new Color(value, value2, value3);
					addItem(c);
				}
			}
		}
		setRenderer(new ColorComboRenderer());
	}

}
