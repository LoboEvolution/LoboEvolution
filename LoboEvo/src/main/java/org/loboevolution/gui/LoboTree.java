package org.loboevolution.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class LoboTree extends JTree {

	private static final long serialVersionUID = 1L;

	/** The color background. */
	private final Color COLOR_BACKGROUND = new Color(37, 51, 61);

	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);

	public LoboTree() {
		setBackground(COLOR_BACKGROUND);
		setForeground(COLOR_TEXT);
		setCellRenderer(new TreeCellRenderer());
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		if (getSelectionCount() > 0) {
			g.setColor(COLOR_TEXT);
			for (int i : getSelectionRows()) {
				Rectangle r = getRowBounds(i);
				g.fillRect(r.x, r.y, getWidth() - r.x, r.height);
			}
		}
		super.paintComponent(g);
		if (getLeadSelectionPath() != null) {
			Rectangle r = getRowBounds(getRowForPath(getLeadSelectionPath()));
			g.setColor(hasFocus() ? COLOR_TEXT : null);
			g.drawRect(r.x, r.y, getWidth() - r.x - 1, r.height - 1);
		}
	}

	class TreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			JLabel l = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, false);
			l.setBackground(COLOR_BACKGROUND);
			l.setForeground(COLOR_TEXT);
			l.setOpaque(true);
			return l;
		}
	};
}