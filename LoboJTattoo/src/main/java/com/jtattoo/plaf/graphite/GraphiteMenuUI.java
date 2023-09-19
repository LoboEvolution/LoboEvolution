/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package com.jtattoo.plaf.graphite;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseMenuUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>GraphiteMenuUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class GraphiteMenuUI extends BaseMenuUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new GraphiteMenuUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(Graphics g, JComponent c, int x, int y, int w, int h) {
		JMenuItem b = (JMenuItem) c;
		ButtonModel model = b.getModel();
		if (c.getParent() instanceof JMenuBar) {
			if (model.isRollover() || model.isArmed() || c instanceof JMenu && model.isSelected()) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getMenuSelectionColors(), x, y, w,
						h);
			}
			if (model.isRollover() && !model.isSelected()) {
				Color[] colArr = AbstractLookAndFeel.getTheme().getMenuSelectionColors();
				Color frameColor = ColorHelper.darker(colArr[colArr.length - 1], 5);
				g.setColor(frameColor);
				g.drawRect(x, y, w - 1, h - 1);
			}
		} else {
			if (model.isArmed() || c instanceof JMenu && model.isSelected()) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getMenuSelectionColors(), x, y, w,
						h);
			} else if (!AbstractLookAndFeel.getTheme().isMenuOpaque()) {
				Graphics2D g2D = (Graphics2D) g;
				Composite composite = g2D.getComposite();
				AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						AbstractLookAndFeel.getTheme().getMenuAlpha());
				g2D.setComposite(alpha);
				g.setColor(AbstractLookAndFeel.getMenuBackgroundColor());
				g.fillRect(x, y, w, h);
				g2D.setComposite(composite);
			} else {
				g.setColor(AbstractLookAndFeel.getMenuBackgroundColor());
				g.fillRect(x, y, w, h);
			}
		}
		if (menuItem.isSelected() && menuItem.isArmed()) {
			g.setColor(AbstractLookAndFeel.getMenuSelectionForegroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getMenuForegroundColor());
		}
	}

} // end of class GraphiteMenuUI
