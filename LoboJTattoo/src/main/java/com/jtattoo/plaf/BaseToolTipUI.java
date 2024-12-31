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
package com.jtattoo.plaf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolTipUI;

/**
 * <p>BaseToolTipUI class.</p>
 *
 * Author Michael Hagen, Daniel Raedel
 *
 */
public class BaseToolTipUI extends MetalToolTipUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseToolTipUI();
	}

	private boolean fancyLayout = false;

	private ComponentListener popupWindowListener = null;

	/** {@inheritDoc} */
	@Override
	protected void installListeners(final JComponent c) {
		super.installListeners(c);

		// We must set the popup window to opaque because it is cached and reused within
		// the PopupFactory
		popupWindowListener = new ComponentAdapter() {

			@Override
			public void componentHidden(final ComponentEvent e) {
				final Window window = (Window) e.getComponent();
				DecorationHelper.setTranslucentWindow(window, false);
				window.removeComponentListener(popupWindowListener);
			}
		};
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		final int borderSize = AbstractLookAndFeel.getTheme().getTooltipBorderSize();
		final int shadowSize = AbstractLookAndFeel.getTheme().getTooltipShadowSize();
		fancyLayout = DecorationHelper.isTranslucentWindowSupported()
				&& ToolTipManager.sharedInstance().isLightWeightPopupEnabled();
		if (fancyLayout) {
			c.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize + shadowSize, borderSize + shadowSize,
					borderSize + shadowSize));
			c.setOpaque(false);
			final Container parent = c.getParent();
			if (parent instanceof JPanel) {
				((JPanel) c.getParent()).setOpaque(false);
			}
		} else {
			c.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final Graphics2D g2D = (Graphics2D) g;
		final Composite savedComposit = g2D.getComposite();
		final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final int borderSize = AbstractLookAndFeel.getTheme().getTooltipBorderSize();
		final int shadowSize = AbstractLookAndFeel.getTheme().getTooltipShadowSize();

		final int w = c.getWidth();
		final int h = c.getHeight();
		final Color backColor = AbstractLookAndFeel.getTheme().getTooltipBackgroundColor();

		// We can't draw the fancyLayout if popup is medium weight
		boolean mediumWeight = false;
		Container parent = c.getParent();
		while (parent != null) {
			if (parent.getClass().getName().indexOf("MediumWeight") > 0) {
				mediumWeight = true;
				break;
			}
			parent = parent.getParent();
		}

		// Paint the tooltip with a shadow border
		if (!mediumWeight && fancyLayout && shadowSize > 0) {
			parent = c.getParent();
			while (parent != null) {
				if (parent.getClass().getName().indexOf("HeavyWeightWindow") > 0 && parent instanceof Window) {
					// Make the popup transparent
					final Window window = (Window) parent;
					// Add a component listener to revert this operation if popup is closed
					window.addComponentListener(popupWindowListener);
					DecorationHelper.setTranslucentWindow(window, true);
					break;
				}
				parent = parent.getParent();
			}
			// draw the shadow
			g2D.setColor(AbstractLookAndFeel.getTheme().getShadowColor());
			final float[] composites = { 0.01f, 0.02f, 0.04f, 0.06f, 0.08f, 0.12f };
			final int shadowOffset = AbstractLookAndFeel.getTheme().isTooltipCastShadow() ? shadowSize : 0;
			for (int i = 0; i < shadowSize; i++) {
				g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						composites[i >= composites.length ? composites.length - 1 : i]));
				g2D.fillRoundRect(i + shadowOffset, borderSize + i, w - 2 * i - shadowOffset, h - borderSize - 2 * i,
						12 - i, 12 - i);

			}
			g2D.setComposite(savedComposit);

			// Draw background with borders
			if (ColorHelper.getGrayValue(backColor) < 128) {
				g2D.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getBackgroundColor(), 20));
			} else {
				g2D.setColor(Color.white);

			}
			// g2D.fillRoundRect(shadowSize, 0, w - (2 * shadowSize) - 1, h - shadowSize -
			// 1, 6, 6);
			g2D.fillRoundRect(shadowSize, 0, w - 2 * shadowSize - 1, h - shadowSize - 1, shadowSize, shadowSize);
			g2D.setColor(ColorHelper.darker(backColor, 40));
			// g2D.drawRoundRect(shadowSize, 0, w - (2 * shadowSize) - 1, h - shadowSize -
			// 1, 6, 6);
			g2D.drawRoundRect(shadowSize, 0, w - 2 * shadowSize - 1, h - shadowSize - 1, shadowSize, shadowSize);
			g2D.setColor(ColorHelper.darker(backColor, 10));
			g2D.drawRect(borderSize + shadowSize - 1, borderSize - 1, w - 2 * borderSize - 2 * shadowSize + 1,
					h - 2 * borderSize - shadowSize + 1);

			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
			// Draw the text. This must be done within an offscreen image because of a bug
			// in the jdk, wich causes ugly antialiased font rendering when background is
			// transparent and popup is heavy weight.
			final BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			final Graphics2D big = bi.createGraphics();
			big.setClip(0, 0, w, h);
			final Paint savedPaint = big.getPaint();
			final Color cHi;
			final Color cLo;
			if (ColorHelper.getGrayValue(backColor) < 128) {
				cHi = ColorHelper.brighter(backColor, 10);
				cLo = ColorHelper.darker(backColor, 20);
			} else {
				cHi = ColorHelper.brighter(backColor, 40);
				cLo = ColorHelper.darker(backColor, 5);
			}
			big.setPaint(new GradientPaint(0, borderSize, cHi, 0, h - 2 * borderSize - shadowSize, cLo));
			big.fillRect(borderSize + shadowSize, borderSize, w - 2 * borderSize - 2 * shadowSize,
					h - 2 * borderSize - shadowSize);

			big.setPaint(savedPaint);

			if (c instanceof JToolTip) {
				final JToolTip tip = (JToolTip) c;
				if (tip.getComponent() != null && tip.getComponent().isEnabled()) {
					c.setForeground(AbstractLookAndFeel.getTheme().getTooltipForegroundColor());
				} else {
					c.setForeground(AbstractLookAndFeel.getTheme().getDisabledForegroundColor());
				}
			}
			super.paint(big, c);
			g2D.setClip(borderSize + shadowSize, borderSize, w - 2 * borderSize - 2 * shadowSize,
					h - 2 * borderSize - shadowSize);
			g2D.drawImage(bi, 0, 0, null);

		} else {
			// Draw background with borders
			if (ColorHelper.getGrayValue(backColor) < 128) {
				g2D.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getBackgroundColor(), 20));
			} else {
				g2D.setColor(Color.white);
			}
			g2D.fillRect(0, 0, w, h);
			g2D.setColor(ColorHelper.darker(backColor, 40));
			g2D.drawRect(0, 0, w - 1, h - 1);
			g2D.setColor(ColorHelper.darker(backColor, 10));
			g2D.drawRect(borderSize - 1, borderSize - 1, w - (2 * borderSize - 1), h - (2 * borderSize - 1));
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);

			final Paint savedPaint = g2D.getPaint();
			final Color cHi;
			final Color cLo;
			if (ColorHelper.getGrayValue(backColor) < 128) {
				cHi = ColorHelper.brighter(backColor, 10);
				cLo = ColorHelper.darker(backColor, 20);
			} else {
				cHi = ColorHelper.brighter(backColor, 40);
				cLo = ColorHelper.darker(backColor, 5);
			}
			g2D.setPaint(new GradientPaint(0, borderSize, cHi, 0, h - 2 * borderSize, cLo));
			g2D.fillRect(borderSize, borderSize, w - 2 * borderSize, h - 2 * borderSize);
			g2D.setPaint(savedPaint);

			super.paint(g, c);
		}
	}

} // end of class BaseToolTipUI
