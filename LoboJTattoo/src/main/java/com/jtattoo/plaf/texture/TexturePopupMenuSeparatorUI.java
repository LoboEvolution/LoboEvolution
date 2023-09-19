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

package com.jtattoo.plaf.texture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

import com.jtattoo.plaf.AbstractLookAndFeel;

/**
 * <p>TexturePopupMenuSeparatorUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TexturePopupMenuSeparatorUI extends BasicSeparatorUI {

	private static final Dimension PREF_SIZE = new Dimension(8, 8);
	private static final Color[] BORDER_COLORS = new Color[] { Color.black, new Color(164, 164, 164),
			new Color(48, 48, 48), new Color(128, 128, 128) };

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TexturePopupMenuSeparatorUI();
	}

	private void drawLine(Graphics g, int y, int w, Color color) {
		g.setColor(color);
		int dw = 3;
		int dx = 2;
		int x = dx;
		while (x < w) {
			g.drawLine(x, y, x + dw - 1, y);
			x += dx + dw;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		return PREF_SIZE;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		TextureUtils.fillComponent(g, c, TextureUtils.MENUBAR_TEXTURE_TYPE);
		if (AbstractLookAndFeel.getTheme().getTextureSet().equals("Default")) {
			Graphics2D g2D = (Graphics2D) g;
			Composite savedComposite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g2D.setComposite(alpha);
			int w = c.getWidth();
			drawLine(g, 2, w, BORDER_COLORS[0]);
			drawLine(g, 3, w, BORDER_COLORS[1]);
			drawLine(g, 4, w, BORDER_COLORS[2]);
			drawLine(g, 5, w, BORDER_COLORS[3]);
			g2D.setComposite(savedComposite);
		} else {
			Graphics2D g2D = (Graphics2D) g;
			Composite savedComposite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g2D.setComposite(alpha);
			if (AbstractLookAndFeel.getTheme().isDarkTexture()) {
				g.setColor(Color.black);
			} else {
				g.setColor(Color.gray);
			}
			g.drawLine(1, 3, c.getWidth() - 2, 3);
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			g2D.setComposite(alpha);
			g.setColor(Color.white);
			g.drawLine(1, 4, c.getWidth() - 2, 4);
			g2D.setComposite(savedComposite);
		}
	}

} // TexturePopupMenuSeparatorUI
