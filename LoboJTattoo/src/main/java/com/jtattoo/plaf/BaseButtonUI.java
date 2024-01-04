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

package com.jtattoo.plaf;

import org.loboevolution.common.Strings;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

/**
 * <p>BaseButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseButtonUI extends BasicButtonUI {

	/** Constant viewRect */
	protected static final Rectangle viewRect = new Rectangle();
	/** Constant textRect */
	protected static final Rectangle textRect = new Rectangle();
	/** Constant iconRect */
	protected static final Rectangle iconRect = new Rectangle();
	/** Constant defaultColors */
	protected static Color[] defaultColors = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected BasicButtonListener createButtonListener(final AbstractButton b) {
		return new BaseButtonListener(b);
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults(final AbstractButton b) {
		super.installDefaults(b);
		b.setOpaque(false);
		b.setRolloverEnabled(true);
	}

	/** {@inheritDoc} */
	@Override
	protected void installKeyboardActions(final AbstractButton b) {
		super.installKeyboardActions(b);
		final InputMap im = (InputMap) UIManager.get("Button.focusInputMap");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
		final Color[] cArr = AbstractLookAndFeel.getTheme().getButtonColors();
		defaultColors = new Color[cArr.length];
		for (int i = 0; i < cArr.length; i++) {
			defaultColors[i] = ColorHelper.brighter(cArr[i], 20);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final Graphics2D g2D = (Graphics2D) g;

		final AbstractButton b = (AbstractButton) c;
		final Font f = c.getFont();
		g.setFont(f);
		final FontMetrics fm = JTattooUtilities.getFontMetrics(b, g, b.getFont());
		final Insets insets = c.getInsets();

		viewRect.x = insets.left;
		viewRect.y = insets.top;
		viewRect.width = b.getWidth() - (insets.right + viewRect.x);
		viewRect.height = b.getHeight() - (insets.bottom + viewRect.y);

		textRect.x = textRect.y = textRect.width = textRect.height = 0;
		iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

		final int iconTextGap = b.getIconTextGap();
		final String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b.getIcon(), b.getVerticalAlignment(),
				b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect,
				iconRect, textRect, b.getText() == null ? 0 : iconTextGap);

		paintBackground(g, b);

		if (b.getIcon() != null) {
			if (!b.isEnabled()) {
				final Composite savedComposite = g2D.getComposite();
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g2D.setComposite(alpha);
				paintIcon(g, c, iconRect);
				g2D.setComposite(savedComposite);
			} else {
				if (b.getModel().isPressed() && b.getModel().isRollover()) {
					iconRect.x++;
					iconRect.y++;
				}
				paintIcon(g, c, iconRect);
			}
		}

		if (Strings.isNotBlank(text)) {
			final View v = (View) c.getClientProperty(BasicHTML.propertyKey);
			if (v != null) {
				Object savedRenderingHint = null;
				if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
					savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
					g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
	protected void paintBackground(final Graphics g, final AbstractButton b) {
		if (!b.isContentAreaFilled() || b.getParent() instanceof JMenuBar) {
			return;
		}

		final int width = b.getWidth();
		final int height = b.getHeight();

		final ButtonModel model = b.getModel();
		Color[] colors = AbstractLookAndFeel.getTheme().getButtonColors();
		if (b.isEnabled()) {
			final Color background = b.getBackground();
			if (background instanceof ColorUIResource) {
				if (model.isPressed() && model.isArmed()) {
					colors = AbstractLookAndFeel.getTheme().getPressedColors();
				} else if (b.isRolloverEnabled() && model.isRollover()) {
					colors = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && b.hasFocus()) {
					colors = AbstractLookAndFeel.getTheme().getFocusColors();
				} else if (JTattooUtilities.isFrameActive(b) && b.getRootPane() != null
						&& b.equals(b.getRootPane().getDefaultButton())) {
					colors = defaultColors;
				}
			} else {
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

		if (b.isBorderPainted() && b.getBorder() != null && !(b.getBorder() instanceof EmptyBorder)) {
			final Insets insets = b.getBorder().getBorderInsets(b);
			final int x = insets.left > 0 ? 1 : 0;
			final int y = insets.top > 0 ? 1 : 0;
			final int w = insets.right > 0 ? width - 1 : width;
			final int h = insets.bottom > 0 ? height - 1 : height;
			JTattooUtilities.fillHorGradient(g, colors, x, y, w - x, h - y);
		} else {
			JTattooUtilities.fillHorGradient(g, colors, 0, 0, width, height);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintFocus(final Graphics g, final AbstractButton b, final Rectangle viewRect, final Rectangle textRect,
							  final Rectangle iconRect) {
		g.setColor(AbstractLookAndFeel.getFocusColor());
		BasicGraphicsUtils.drawDashedRect(g, 4, 3, b.getWidth() - 8, b.getHeight() - 6);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final AbstractButton b, final Rectangle textRect, final String text) {
		final ButtonModel model = b.getModel();
		final FontMetrics fm = JTattooUtilities.getFontMetrics(b, g, b.getFont());
		final int mnemIndex = b.getDisplayedMnemonicIndex();

		if (model.isEnabled()) {
			final Color foreground = b.getForeground();
			final Color background = b.getBackground();
			int offs = 0;
			if (model.isArmed() && model.isPressed()) {
				offs = 1;
			}
			if (!(model.isPressed() && model.isArmed())) {
				final Object sc = b.getClientProperty("shadowColor");
				if (sc instanceof Color) {
					g.setColor((Color) sc);
					JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + 1,
							textRect.y + 1 + fm.getAscent());
				}
			}
			if (background instanceof ColorUIResource) {
				if (model.isPressed() && model.isArmed()) {
					g.setColor(AbstractLookAndFeel.getTheme().getPressedForegroundColor());
				} else if (model.isRollover()) {
					g.setColor(AbstractLookAndFeel.getTheme().getRolloverForegroundColor());
				} else {
					g.setColor(foreground);
				}
			} else {
				g.setColor(foreground);
			}
			JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs,
					textRect.y + offs + fm.getAscent());
		} else {
			if (ColorHelper.getGrayValue(b.getForeground()) < 128) {
				g.setColor(Color.white);
				JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + 1,
						textRect.y + 1 + fm.getAscent());
			}
			g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
			JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallDefaults(final AbstractButton b) {
		super.uninstallDefaults(b);
		b.setOpaque(true);
		b.setRolloverEnabled(false);
	}

} // end of class BaseButtonUI
