/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseButtonUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>TextureButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureButtonUI extends BaseButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TextureButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g, final AbstractButton b) {
		if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
			super.paintBackground(g, b);
		} else {
			final Graphics2D g2D = (Graphics2D) g;
			final Shape savedClip = g.getClip();
			if (b.getBorder() != null && b.isBorderPainted() && b.getBorder() instanceof UIResource) {
				final int w = b.getWidth();
				final int h = b.getHeight();
				final Area clipArea = new Area(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, 6, 6));
				clipArea.intersect(new Area(savedClip));
				g2D.setClip(clipArea);
			}
			super.paintBackground(g, b);
			g2D.setClip(savedClip);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintFocus(final Graphics g, final AbstractButton b, final Rectangle viewRect, final Rectangle textRect,
                              final Rectangle iconRect) {
		if (!AbstractLookAndFeel.getTheme().doShowFocusFrame()) {
			g.setColor(AbstractLookAndFeel.getFocusColor());
			BasicGraphicsUtils.drawDashedRect(g, 3, 2, b.getWidth() - 6, b.getHeight() - 5);
			BasicGraphicsUtils.drawDashedRect(g, 4, 3, b.getWidth() - 8, b.getHeight() - 7);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintIcon(final Graphics g, final JComponent c, final Rectangle iconRect) {
		final AbstractButton b = (AbstractButton) c;
		final Graphics2D g2D = (Graphics2D) g;
		final Composite savedComposite = g2D.getComposite();
		if (!b.isContentAreaFilled()) {
			if (!b.isEnabled()) {
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
				g2D.setComposite(alpha);
			} else {
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
				g2D.setComposite(alpha);
			}
		}
		super.paintIcon(g, c, iconRect);
		g2D.setComposite(savedComposite);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final AbstractButton b, final Rectangle textRect, final String text) {
		final Graphics2D g2D = (Graphics2D) g;
		final Composite savedComposite = g2D.getComposite();
		final ButtonModel model = b.getModel();
		final FontMetrics fm = JTattooUtilities.getFontMetrics(b, g, b.getFont());
		final int mnemIndex = b.getDisplayedMnemonicIndex();

		if (model.isEnabled()) {
			int offs = 0;
			if (model.isArmed() && model.isPressed()) {
				offs = 1;
			}
			Color foreground = b.getForeground();
			final Color background = b.getBackground();
			if (background instanceof ColorUIResource) {
				if (model.isPressed() && model.isArmed()) {
					foreground = AbstractLookAndFeel.getTheme().getPressedForegroundColor();
				} else if (model.isRollover()) {
					foreground = AbstractLookAndFeel.getTheme().getRolloverForegroundColor();
				}
			}
			if (AbstractLookAndFeel.getTheme().isTextShadowOn() && ColorHelper.getGrayValue(foreground) > 164) {
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
				g2D.setComposite(alpha);
				g.setColor(Color.black);
				JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs,
						textRect.y + offs + fm.getAscent() + 1);
				g2D.setComposite(savedComposite);
			} else {
				if (!(model.isPressed() && model.isArmed())) {
					final Object sc = b.getClientProperty("shadowColor");
					if (sc instanceof Color) {
						g.setColor((Color) sc);
						JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + 1,
								textRect.y + 1 + fm.getAscent());
					}
				}
			}
			g.setColor(foreground);
			JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs,
					textRect.y + offs + fm.getAscent());
		} else {
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
			g2D.setComposite(alpha);
			Color fc = b.getForeground();
			if (ColorHelper.getGrayValue(fc) > 164) {
				fc = ColorHelper.brighter(AbstractLookAndFeel.getDisabledForegroundColor(), 40);
				g.setColor(Color.black);
			} else {
				fc = AbstractLookAndFeel.getDisabledForegroundColor();
				g.setColor(Color.white);
			}
			JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x,
					textRect.y + 1 + fm.getAscent());
			g2D.setComposite(savedComposite);
			g.setColor(fc);
			JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
		}
	}

} // end of class TextureButtonUI
