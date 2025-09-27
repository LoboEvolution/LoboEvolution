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
package com.jtattoo.plaf.luna;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.AbstractToolBarUI;
import com.jtattoo.plaf.base.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>LunaToolBarUI class.</p>
 */
public class LunaToolBarUI extends AbstractToolBarUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new LunaToolBarUI();
	}

	/** {@inheritDoc} */
	@Override
	public Border getNonRolloverBorder() {
		return BaseBorders.getToolButtonBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getRolloverBorder() {
		return LunaBorders.getRolloverToolButtonBorder();
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
		JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getToolBarColors(), 0, 0, w, h);
		g.setColor(ColorHelper.darker(AbstractLookAndFeel.getToolbarColorDark(), 10));
		g.drawLine(0, 0, w, 0);
	}

} // end of class LunaToolBarUI
