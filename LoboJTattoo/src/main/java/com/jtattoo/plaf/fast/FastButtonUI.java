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
package com.jtattoo.plaf.fast;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseButtonUI;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>FastButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastButtonUI extends BaseButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new FastButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g, final AbstractButton b) {
		if (b.isContentAreaFilled() && !(b.getParent() instanceof JMenuBar)) {
			Color backColor = b.getBackground();
			final ButtonModel model = b.getModel();
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed()) {
					backColor = ColorHelper.darker(backColor, 30);
				}
			} else {
				backColor = ColorHelper.brighter(AbstractLookAndFeel.getDisabledForegroundColor(), 80);
			}
			g.setColor(backColor);
			g.fillRect(0, 0, b.getWidth(), b.getHeight());
		}
	}

} // end of FastButtonUI
