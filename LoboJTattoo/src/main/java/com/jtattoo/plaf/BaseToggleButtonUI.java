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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicToggleButtonUI;
import javax.swing.text.View;

/**
 * <p>BaseToggleButtonUI class.</p>
 *
 *
 *
 */
public class BaseToggleButtonUI extends BasicToggleButtonUI {

	private static final Rectangle viewRect = new Rectangle();
	private static final Rectangle textRect = new Rectangle();
	private static final Rectangle iconRect = new Rectangle();

	/** {@inheritDoc} */
	public static ComponentUI createUI(JComponent b) {
		return new BaseToggleButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected BasicButtonListener createButtonListener(AbstractButton b) {
		return new BaseButtonListener(b);
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults(AbstractButton b) {
		super.installDefaults(b);
		b.setOpaque(false);
		b.setRolloverEnabled(true);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		Graphics2D g2D = (Graphics2D) g;

		AbstractButton b = (AbstractButton) c;
		g.setFont(b.getFont());
		FontMetrics fm = JTattooUtilities.getFontMetrics(b, g, b.getFont());
		Insets insets = c.getInsets();

		viewRect.x = insets.left;
		viewRect.y = insets.top;
		viewRect.width = b.getWidth() - (insets.right + viewRect.x);
		viewRect.height = b.getHeight() - (insets.bottom + viewRect.y);

		textRect.x = textRect.y = textRect.width = textRect.height = 0;
		iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

		int iconTextGap = b.getIconTextGap();

		String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b.getIcon(), b.getVerticalAlignment(),
				b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect,
				iconRect, textRect, b.getText() == null ? 0 : iconTextGap);

		paintBackground(g, b);

		if (b.getIcon() != null) {
			if (!b.isEnabled()) {
				Composite savedComposite = g2D.getComposite();
				AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g2D.setComposite(alpha);
				paintIcon(g, c, iconRect);
				g2D.setComposite(savedComposite);
			} else {
				paintIcon(g, c, iconRect);
			}
		}

		if (text != null && !text.equals("") && textRect != null) {
			View v = (View) c.getClientProperty(BasicHTML.propertyKey);
			if (v != null) {
				Object savedRenderingHint = null;
				if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
					savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
					g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
				}
				v.paint(g, textRect);
				if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
					g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
				}
			} else {
				paintText(g, b, textRect, text);
			}
		}

		if (b.isFocusPainted() && b.hasFocus()) {
			paintFocus(g, b, viewRect, textRect, iconRect);
		}
	}

	/**
	 * <p>paintBackground.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param b a {@link javax.swing.AbstractButton} object.
	 */
	protected void paintBackground(Graphics g, AbstractButton b) {
		if (!b.isContentAreaFilled() || b.getParent() instanceof JMenuBar) {
			return;
		}

		int width = b.getWidth();
		int height = b.getHeight();

		ButtonModel model = b.getModel();
		Color[] colors;
		if (b.isEnabled()) {
			Color background = b.getBackground();
			if (background instanceof ColorUIResource) {
				if (b.isRolloverEnabled() && model.isRollover()) {
					colors = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else if (model.isPressed() && model.isArmed() || model.isSelected()) {
					colors = AbstractLookAndFeel.getTheme().getPressedColors();
				} else {
					if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && b.hasFocus()) {
						colors = AbstractLookAndFeel.getTheme().getFocusColors();
					} else {
						colors = AbstractLookAndFeel.getTheme().getButtonColors();
					}
				}
			} else {
				if (model.isPressed() && model.isArmed()) {
					colors = ColorHelper.createColorArr(ColorHelper.darker(background, 30),
							ColorHelper.darker(background, 10), 20);
				} else if (b.isRolloverEnabled() && model.isRollover()) {
					if (model.isSelected()) {
						colors = ColorHelper.createColorArr(ColorHelper.darker(background, 20), background, 20);
					} else {
						colors = ColorHelper.createColorArr(ColorHelper.brighter(background, 50),
								ColorHelper.brighter(background, 10), 20);
					}
				} else if (model.isSelected()) {
					colors = ColorHelper.createColorArr(ColorHelper.darker(background, 40),
							ColorHelper.darker(background, 20), 20);
				} else {
					colors = ColorHelper.createColorArr(ColorHelper.brighter(background, 30),
							ColorHelper.darker(background, 10), 20);
				}
			}
		} else { // disabled
			colors = AbstractLookAndFeel.getTheme().getDisabledColors();
		}
		JTattooUtilities.fillHorGradient(g, colors, 1, 1, width - 2, height - 2);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect,
			Rectangle iconRect) {
		g.setColor(AbstractLookAndFeel.getFocusColor());
		BasicGraphicsUtils.drawDashedRect(g, 4, 3, b.getWidth() - 8, b.getHeight() - 6);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
		ButtonModel model = b.getModel();
		FontMetrics fm = JTattooUtilities.getFontMetrics(b, g, b.getFont());
		int mnemIndex = b.getDisplayedMnemonicIndex();
		if (model.isEnabled()) {
			Color foreground = b.getForeground();
			Color background = b.getBackground();
			int offs = 0;
			if (model.isArmed() && model.isPressed() || model.isSelected()) {
				offs = 1;
				if (foreground instanceof ColorUIResource && background instanceof ColorUIResource) {
					foreground = AbstractLookAndFeel.getTheme().getPressedForegroundColor();
				}
			}
			if (model.isRollover()) {
				if (foreground instanceof ColorUIResource && background instanceof ColorUIResource) {
					foreground = AbstractLookAndFeel.getTheme().getRolloverForegroundColor();
				}
			}
			Object sc = b.getClientProperty("shadowColor");
			if (sc instanceof Color) {
				g.setColor((Color) sc);
				JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs,
						textRect.y + fm.getAscent() * offs + 1);
			}
			g.setColor(foreground);
			JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs,
					textRect.y + fm.getAscent() + offs);
		} else {
			g.setColor(Color.white);
			JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + 1,
					textRect.y + fm.getAscent() + 1);
			g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
			JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallDefaults(AbstractButton b) {
		super.uninstallDefaults(b);
		b.setOpaque(true);
		b.setRolloverEnabled(false);
	}

} // end of class BaseToggleButtonUI
