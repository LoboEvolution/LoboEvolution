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
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseScrollBarUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>FastScrollBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastScrollBarUI extends BaseScrollBarUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new FastScrollBarUI();
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createDecreaseButton(final int orientation) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			return super.createDecreaseButton(orientation);
		} else {
			return new FastScrollButton(orientation, scrollBarWidth);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createIncreaseButton(final int orientation) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			return super.createDecreaseButton(orientation);
		} else {
			return new FastScrollButton(orientation, scrollBarWidth);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintThumb(final Graphics g, final JComponent c, final Rectangle thumbBounds) {
		if (!c.isEnabled()) {
			return;
		}

		g.translate(thumbBounds.x, thumbBounds.y);

		Color backColor = AbstractLookAndFeel.getTheme().getControlBackgroundColor();
		if (!JTattooUtilities.isActive(c)) {
			backColor = ColorHelper.brighter(backColor, 50);
		}
		final Color frameColorHi = ColorHelper.brighter(backColor, 40);
		final Color frameColorLo = ColorHelper.darker(backColor, 30);
		g.setColor(backColor);
		g.fillRect(1, 1, thumbBounds.width - 1, thumbBounds.height - 1);
		g.setColor(frameColorLo);
		g.drawRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 1);
		g.setColor(frameColorHi);
		g.drawLine(1, 1, thumbBounds.width - 2, 1);
		g.drawLine(1, 1, 1, thumbBounds.height - 2);
		g.translate(-thumbBounds.x, -thumbBounds.y);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintTrack(final Graphics g, final JComponent c, final Rectangle trackBounds) {
		g.setColor(AbstractLookAndFeel.getControlColorLight());
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	}

} // end of class FastScrollBarUI
