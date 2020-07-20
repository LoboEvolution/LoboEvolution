package com.jtattoo.plaf.lobo;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class LoboTree extends JTree implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

	public LoboTree() {
		setBackground(background());
		setForeground(foreground());
		setCellRenderer(new TreeCellRenderer());
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		if (getSelectionCount() > 0) {
			g.setColor(foreground());
			for (int i : getSelectionRows()) {
				Rectangle r = getRowBounds(i);
				g.fillRect(r.x, r.y, getWidth() - r.x, r.height);
			}
		}
		super.paintComponent(g);
		if (getLeadSelectionPath() != null) {
			Rectangle r = getRowBounds(getRowForPath(getLeadSelectionPath()));
			g.setColor(hasFocus() ? foreground() : null);
			g.drawRect(r.x, r.y, getWidth() - r.x - 1, r.height - 1);
		}
	}

	class TreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			JLabel l = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, false);
			l.setBackground(background());
			l.setForeground(foreground());
			l.setOpaque(true);
			return l;
		}
	};
}