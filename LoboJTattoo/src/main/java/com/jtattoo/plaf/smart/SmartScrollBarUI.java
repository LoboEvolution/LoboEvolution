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
package com.jtattoo.plaf.smart;

import java.awt.Adjustable;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseScrollBarUI;

/**
 * <p>SmartScrollBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class SmartScrollBarUI extends BaseScrollBarUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new SmartScrollBarUI();
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createDecreaseButton(final int orientation) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			return super.createDecreaseButton(orientation);
		} else {
			return new SmartScrollButton(orientation, scrollBarWidth);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createIncreaseButton(final int orientation) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			return super.createIncreaseButton(orientation);
		} else {
			return new SmartScrollButton(orientation, scrollBarWidth);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintThumb(final Graphics g, final JComponent c, final Rectangle thumbBounds) {
		super.paintThumb(g, c, thumbBounds);
		if (isRollover) {
			g.setColor(AbstractLookAndFeel.getFocusColor());
			if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
				g.drawLine(thumbBounds.x + 1, thumbBounds.y + 1, thumbBounds.x + thumbBounds.width - 2,
						thumbBounds.y + 1);
				g.drawLine(thumbBounds.x + 1, thumbBounds.y + 2, thumbBounds.x + thumbBounds.width - 2,
						thumbBounds.y + 2);
			} else {
				g.drawLine(thumbBounds.x + 1, thumbBounds.y + 1, thumbBounds.x + 1,
						thumbBounds.y + thumbBounds.height - 2);
				g.drawLine(thumbBounds.x + 2, thumbBounds.y + 1, thumbBounds.x + 2,
						thumbBounds.y + thumbBounds.height - 2);
			}
		}
	}

} // end of class SmartScrollBarUI
