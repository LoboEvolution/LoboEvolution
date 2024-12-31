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
package com.jtattoo.plaf.mcwin;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseButtonUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>McWinButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinButtonUI extends BaseButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new McWinButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g, final AbstractButton b) {
		if (b.getParent() instanceof JToolBar) {
			b.setContentAreaFilled(true);
		}
		if (!b.isContentAreaFilled() || b.getParent() instanceof JMenuBar) {
			return;
		}

		final int width = b.getWidth();
		final int height = b.getHeight();

		if (!(b.isBorderPainted() && b.getBorder() instanceof UIResource) || b.getParent() instanceof JToolBar) {
			super.paintBackground(g, b);
			if (b.getParent() instanceof JToolBar) {
				g.setColor(Color.lightGray);
				g.drawRect(0, 0, width - 2, height - 1);
				g.setColor(AbstractLookAndFeel.getTheme().getToolbarBackgroundColor());
				g.drawLine(width - 1, 0, width - 1, height - 1);
			}
			return;
		}

		final ButtonModel model = b.getModel();
		final Graphics2D g2D = (Graphics2D) g;
		final Composite composite = g2D.getComposite();
		final Object savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color[] colors = AbstractLookAndFeel.getTheme().getButtonColors();
		if (b.isEnabled()) {
			final Color background = b.getBackground();
			if (background instanceof ColorUIResource) {
				if (model.isPressed() && model.isArmed()) {
					colors = AbstractLookAndFeel.getTheme().getPressedColors();
				} else if (b.isRolloverEnabled() && model.isRollover()) {
					colors = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else {
					if (b.getRootPane() != null && b.equals(b.getRootPane().getDefaultButton())) {
						if (JTattooUtilities.isFrameActive(b)) {
							if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && b.hasFocus()) {
								colors = AbstractLookAndFeel.getTheme().getFocusColors();
							} else {
								if (AbstractLookAndFeel.getTheme().isBrightMode()) {
									colors = new Color[AbstractLookAndFeel.getTheme().getSelectedColors().length];
									for (int i = 0; i < colors.length; i++) {
										colors[i] = ColorHelper
												.brighter(AbstractLookAndFeel.getTheme().getSelectedColors()[i], 50);
									}
								} else {
									colors = AbstractLookAndFeel.getTheme().getSelectedColors();
								}
							}
						}
					} else {
						if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && b.hasFocus()) {
							colors = AbstractLookAndFeel.getTheme().getFocusColors();
						}
					}
				}
			} else { // backgound != ColorUIResource
				if (model.isPressed() && model.isArmed()) {
					colors = ColorHelper.createColorArr(ColorHelper.darker(background, 30),
							ColorHelper.darker(background, 10), 20);
				} else {
					if (b.isRolloverEnabled() && model.isRollover()) {
						colors = ColorHelper.createColorArr(ColorHelper.brighter(background, 50),
								ColorHelper.brighter(background, 10), 20);
					} else {
						colors = ColorHelper.createColorArr(ColorHelper.brighter(background, 30),
								ColorHelper.darker(background, 10), 20);
					}
				}
			}
		} else { // disabled
			colors = AbstractLookAndFeel.getTheme().getDisabledColors();
		}

		if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()
				|| (width < 64 || height < 16) && (b.getText() == null || b.getText().length() == 0)) {
			JTattooUtilities.fillHorGradient(g, colors, 0, 0, width - 1, height - 1);
			final Color frameColor = colors[colors.length / 2];
			g2D.setColor(ColorHelper.darker(frameColor, 25));
			g2D.drawRect(0, 0, width - 1, height - 1);
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g2D.setComposite(alpha);
			g2D.setColor(Color.white);
			g2D.drawRect(1, 1, width - 3, height - 3);
		} else {
			final int d = height - 2;
			final Color frameColor = colors[colors.length / 2];

			final Shape savedClip = g.getClip();
			final Area clipArea = new Area(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, d, d));
			if (savedClip != null) {
				clipArea.intersect(new Area(savedClip));
			}
			g2D.setClip(clipArea);
			JTattooUtilities.fillHorGradient(g, colors, 0, 0, width - 1, height - 1);
			g2D.setClip(savedClip);

			g2D.setColor(ColorHelper.darker(frameColor, 25));
			g2D.drawRoundRect(0, 0, width - 1, height - 1, d, d);

			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g2D.setComposite(alpha);
			g2D.setColor(Color.white);
			g2D.drawRoundRect(1, 1, width - 3, height - 3, d - 2, d - 2);

		}
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRenderingHint);
		g2D.setComposite(composite);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintFocus(final Graphics g, final AbstractButton b, final Rectangle viewRect, final Rectangle textRect,
                              final Rectangle iconRect) {
		final Graphics2D g2D = (Graphics2D) g;
		final int width = b.getWidth();
		final int height = b.getHeight();
		if (AbstractLookAndFeel.getTheme().doDrawSquareButtons() || !b.isContentAreaFilled()
				|| !(b.getBorder() instanceof UIResource)
				|| (width < 64 || height < 16) && (b.getText() == null || b.getText().length() == 0)) {
			g.setColor(AbstractLookAndFeel.getFocusColor());
			BasicGraphicsUtils.drawDashedRect(g, 4, 3, width - 8, height - 6);
		} else {
			final Object savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.setColor(AbstractLookAndFeel.getFocusColor());
			final int d = b.getHeight() - 4;
			g2D.drawRoundRect(2, 2, b.getWidth() - 5, b.getHeight() - 5, d, d);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRenderingHint);
		}
	}

} // end of class McWinButtonUI
