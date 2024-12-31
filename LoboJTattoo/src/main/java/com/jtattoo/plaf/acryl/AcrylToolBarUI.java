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

package com.jtattoo.plaf.acryl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.AbstractToolBarUI;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AcrylToolBarUI class.</p>s
 */
public class AcrylToolBarUI extends AbstractToolBarUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AcrylToolBarUI();
	}

	/** {@inheritDoc} */
	@Override
	public Border getNonRolloverBorder() {
		return BaseBorders.getToolButtonBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getRolloverBorder() {
		return AcrylBorders.getRolloverToolButtonBorder();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isButtonOpaque() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final int w = c.getWidth();
		final int h = c.getHeight();
		JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getToolBarColors(), 0, 0, w, h - 2);
		if (toolBar.getOrientation() == SwingConstants.HORIZONTAL && isToolbarDecorated() && isToolBarUnderMenubar()) {
			g.setColor(Color.white);
			g.drawLine(0, 0, w, 0);
			g.drawLine(0, h - 2, w, h - 2);
			g.setColor(ColorHelper.darker(AbstractLookAndFeel.getToolbarColorDark(), 10));
			g.drawLine(0, 1, w, 1);
			g.drawLine(0, h - 1, w, h - 1);
		}
	}

} // end of class AcrylToolBarUI
