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

package com.jtattoo.plaf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 * <p>BaseMenuItemUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseMenuItemUI extends BasicMenuItemUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseMenuItemUI();
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		c.setOpaque(false);
	}

	/**
	 * <p>paintBackground.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link javax.swing.JComponent} object.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	protected void paintBackground(final Graphics g, final JComponent c, final int x, final int y, final int w, final int h) {
		final JMenuItem mi = (JMenuItem) c;
		Color backColor = mi.getBackground();
		if (backColor == null || backColor instanceof UIResource) {
			backColor = AbstractLookAndFeel.getMenuBackgroundColor();
		}

		final ButtonModel model = mi.getModel();
		if (model.isArmed() || model.isRollover() || c instanceof JMenu && model.isSelected()) {
			g.setColor(AbstractLookAndFeel.getMenuSelectionBackgroundColor());
			g.fillRect(x, y, w, h);
			g.setColor(AbstractLookAndFeel.getMenuSelectionForegroundColor());
		} else if (!AbstractLookAndFeel.getTheme().isMenuOpaque()) {
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					AbstractLookAndFeel.getTheme().getMenuAlpha());
			g2D.setComposite(alpha);
			g2D.setColor(backColor);
			g2D.fillRect(x, y, w, h);
			g2D.setComposite(savedComposite);
			g.setColor(AbstractLookAndFeel.getMenuForegroundColor());
		} else {
			g.setColor(backColor);
			g.fillRect(x, y, w, h);
			g.setColor(AbstractLookAndFeel.getMenuForegroundColor());
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g, final JMenuItem menuItem, final Color bgColor) {
		if (menuItem.isOpaque()) {
			final int w = menuItem.getWidth();
			final int h = menuItem.getHeight();
			paintBackground(g, menuItem, 0, 0, w, h);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final JMenuItem menuItem, final Rectangle textRect, final String text) {
		final ButtonModel model = menuItem.getModel();
		Color foreColor = menuItem.getForeground();
		if (model.isArmed() || model.isRollover()) {
			foreColor = AbstractLookAndFeel.getMenuSelectionForegroundColor();
		} else if (foreColor == null || foreColor instanceof UIResource) {
			foreColor = AbstractLookAndFeel.getMenuForegroundColor();
		}
		final Graphics2D g2D = (Graphics2D) g;
		Object savedRenderingHint = null;
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
		}
		g2D.setColor(foreColor);
		super.paintText(g, menuItem, textRect, text);
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallUI(final JComponent c) {
		c.setOpaque(true);
		super.uninstallUI(c);
	}

	/** {@inheritDoc} */
	@Override
	public void update(final Graphics g, final JComponent c) {
		paintBackground(g, c, 0, 0, c.getWidth(), c.getHeight());
		paint(g, c);
	}

} // end of class BaseMenuItemUI
