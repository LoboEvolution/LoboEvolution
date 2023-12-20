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
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;

/**
 * <p>TextureMenuBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureMenuBarUI extends BasicMenuBarUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent x) {
		return new TextureMenuBarUI();
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		if (c instanceof JMenuBar) {
			c.setBorder(BaseBorders.getMenuBarBorder());
			((JMenuBar) c).setBorderPainted(true);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		TextureUtils.fillComponent(g, c, TextureUtils.MENUBAR_TEXTURE_TYPE);

		if (AbstractLookAndFeel.getTheme().isDarkTexture()) {
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g2D.setComposite(alpha);
			g2D.setColor(Color.black);
			g2D.drawLine(0, 0, c.getWidth() - 1, 0);
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g2D.setComposite(alpha);
			g2D.setColor(Color.white);
			g2D.drawLine(0, c.getHeight() - 1, c.getWidth() - 1, c.getHeight() - 1);
			g2D.setComposite(savedComposite);
		}
	}

} // end of class TextureMenuBarUI
