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
package com.jtattoo.plaf.texture;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRadioButtonMenuItemUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>TextureRadioButtonMenuItemUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureRadioButtonMenuItemUI extends BaseRadioButtonMenuItemUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TextureRadioButtonMenuItemUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g, final JComponent c, final int x, final int y, final int w, final int h) {
		final JMenuItem mi = (JMenuItem) c;
		final ButtonModel model = mi.getModel();
		if (model.isArmed() || c instanceof JMenu && model.isSelected()) {
			TextureUtils.fillComponent(g, c, TextureUtils.ROLLOVER_TEXTURE_TYPE);
		} else {
			if (!(mi.getBackground() instanceof ColorUIResource)) {
				super.paintBackground(g, c, x, y, w, h);
			} else {
				TextureUtils.fillComponent(g, c, TextureUtils.MENUBAR_TEXTURE_TYPE);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final JMenuItem menuItem, final Rectangle textRect, final String text) {
		if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
			super.paintText(g, menuItem, textRect, text);
			return;
		}
		final ButtonModel model = menuItem.getModel();
		final FontMetrics fm = JTattooUtilities.getFontMetrics(menuItem, g, menuItem.getFont());
		final int mnemIndex = menuItem.getDisplayedMnemonicIndex();
		if (!menuItem.isArmed()) {
			g.setColor(Color.black);
			JTattooUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x,
					textRect.y + fm.getAscent() - 1);
		}
		if (!model.isEnabled()) {
			// *** paint the text disabled
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getDisabledForegroundColor(), 40));
		} else {
			// *** paint the text normally
			if (menuItem.isArmed()) {
				g.setColor(AbstractLookAndFeel.getMenuSelectionForegroundColor());
			} else {
				Color foreColor = menuItem.getForeground();
				if (foreColor instanceof UIResource) {
					foreColor = AbstractLookAndFeel.getMenuForegroundColor();
				}
				g.setColor(foreColor);
			}
		}
		JTattooUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x,
				textRect.y + fm.getAscent());
	}

} // end of class TextureRadioButtonMenuItemUI
