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
package com.jtattoo.plaf.mint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseButtonUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>MintButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class MintButtonUI extends BaseButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new MintButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g, final AbstractButton b) {
		if (!b.isContentAreaFilled() || b.getParent() instanceof JMenuBar) {
			return;
		}

		if (b.getWidth() < 32 || b.getHeight() < 16 || !(b.isBorderPainted() && b.getBorder() instanceof UIResource)
				|| AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
			final ButtonModel model = b.getModel();
			Color color = AbstractLookAndFeel.getTheme().getButtonBackgroundColor();
			if (model.isPressed() && model.isArmed()) {
				color = AbstractLookAndFeel.getTheme().getSelectionBackgroundColor();
			} else if (b.isRolloverEnabled() && model.isRollover()) {
				color = AbstractLookAndFeel.getTheme().getRolloverColor();
			}
			g.setColor(color);
			g.fillRect(0, 0, b.getWidth(), b.getHeight());
			if (model.isPressed() && model.isArmed() || model.isSelected()) {
				JTattooUtilities.draw3DBorder(g, Color.lightGray, Color.white, 0, 0, b.getWidth(), b.getHeight());
			} else {
				JTattooUtilities.draw3DBorder(g, Color.white, Color.lightGray, 0, 0, b.getWidth(), b.getHeight());
			}
			return;
		}

		final Graphics2D g2D = (Graphics2D) g;
		final int width = b.getWidth() - 2;
		final int height = b.getHeight() - 2;
		final ButtonModel model = b.getModel();

		if (model.isPressed() && model.isArmed()) {
			final Color color = AbstractLookAndFeel.getTheme().getPressedBackgroundColor();
			g2D.setColor(color);
			g2D.fillRoundRect(0, 0, width, height, height, height);
			g2D.setColor(ColorHelper.darker(color, 40));
			final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.drawRoundRect(0, 0, width - 1, height - 1, height, height);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
			return;
		}

		Color[] colors = AbstractLookAndFeel.getTheme().getButtonColors();
		if (!model.isEnabled()) {
			colors = AbstractLookAndFeel.getTheme().getDisabledColors();
		} else if (b.isRolloverEnabled() && model.isRollover()) {
			final Color[] src = AbstractLookAndFeel.getTheme().getRolloverColors();
			colors = new Color[src.length];
			System.arraycopy(src, 0, colors, 0, colors.length);
			colors[colors.length - 2] = ColorHelper.darker(colors[colors.length - 2], 15);
		}

		final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Paint shadow
		final Color color = b.getParent().getBackground();
		g2D.setColor(ColorHelper.darker(color, 6));
		g2D.drawRoundRect(2, 2, width - 1, height - 1, height, height);
		g2D.setColor(ColorHelper.darker(color, 18));
		g2D.drawRoundRect(1, 1, width - 1, height - 1, height, height);
		// paint background
		int x = 0;
		int y = 0;
		int w = width;
		int h = height;
		for (int i = colors.length - 1; i >= 0; i--) {
			g2D.setColor(colors[i]);
			g2D.fillRoundRect(x, y, w, h, h, h);
			h--;
			w--;
			if ((i + 1) % 4 == 0) {
				x++;
				y++;
			}
			if (h == 0) {
				break;
			}
		}
		g2D.setColor(Color.white);
		g2D.drawRoundRect(1, 1, width - 3, height - 3, height - 2, height - 2);
		g2D.drawRoundRect(1, 1, width - 3, height - 3, height - 2, height - 2);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
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
			final int d = height - 6;
			g2D.drawRoundRect(2, 2, width - 7, height - 7, d, d);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRenderingHint);
		}
	}

} // end of class MintButtonUI
