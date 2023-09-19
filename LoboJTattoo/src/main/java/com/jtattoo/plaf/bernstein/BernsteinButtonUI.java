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

package com.jtattoo.plaf.bernstein;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseButtonUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>BernsteinButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BernsteinButtonUI extends BaseButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BernsteinButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(Graphics g, AbstractButton b) {
		if (!b.isContentAreaFilled() || b.getParent() instanceof JMenuBar) {
			return;
		}

		int width = b.getWidth();
		int height = b.getHeight();

		ButtonModel model = b.getModel();
		Color[] colors = AbstractLookAndFeel.getTheme().getButtonColors();
		if (b.isEnabled()) {
			Color background = b.getBackground();
			if (background instanceof ColorUIResource) {
				if (model.isPressed() && model.isArmed()) {
					colors = AbstractLookAndFeel.getTheme().getPressedColors();
				} else if (b.isRolloverEnabled() && model.isRollover()) {
					colors = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && b.hasFocus()) {
					colors = AbstractLookAndFeel.getTheme().getFocusColors();
				} else if (JTattooUtilities.isFrameActive(b) && b.getRootPane() != null
						&& b.equals(b.getRootPane().getDefaultButton())) {
					colors = AbstractLookAndFeel.getTheme().getSelectedColors();
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

		if (b.isBorderPainted() && b.getBorder() != null) {
			Insets insets = b.getBorder().getBorderInsets(b);
			int x = insets.left > 0 ? 1 : 0;
			int y = insets.top > 0 ? 1 : 0;
			int w = insets.right > 0 ? width - 1 : width;
			int h = insets.bottom > 0 ? height - 1 : height;
			JTattooUtilities.fillHorGradient(g, colors, x, y, w - x, h - y);
		} else {
			JTattooUtilities.fillHorGradient(g, colors, 0, 0, width, height);
		}
	}

} // end of class BernsteinButtonUI
