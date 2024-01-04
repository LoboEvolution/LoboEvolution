/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package com.jtattoo.plaf.lobo;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * <p>LoboTree class.</p>
 *
 *
 *
 */
public class LoboTree extends JTree implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for LoboTree.</p>
	 */
	public LoboTree() {
		setBackground(background());
		setForeground(foreground());
		setCellRenderer(new TreeCellRenderer());
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(final Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		if (getSelectionCount() > 0) {
			g.setColor(foreground());
			for (final int i : getSelectionRows()) {
				final Rectangle r = getRowBounds(i);
				g.fillRect(r.x, r.y, getWidth() - r.x, r.height);
			}
		}
		super.paintComponent(g);
		if (getLeadSelectionPath() != null) {
			final Rectangle r = getRowBounds(getRowForPath(getLeadSelectionPath()));
			g.setColor(hasFocus() ? foreground() : null);
			g.drawRect(r.x, r.y, getWidth() - r.x - 1, r.height - 1);
		}
	}

	class TreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selected, final boolean expanded,
                                                      final boolean leaf, final int row, final boolean hasFocus) {
			final JLabel l = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, false);
			l.setBackground(background());
			l.setForeground(foreground());
			l.setOpaque(true);
			return l;
		}
	}
}
