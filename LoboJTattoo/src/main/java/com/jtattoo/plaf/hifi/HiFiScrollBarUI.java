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
package com.jtattoo.plaf.hifi;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.XPScrollBarUI;

/**
 * <p>HiFiScrollBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiScrollBarUI extends XPScrollBarUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new HiFiScrollBarUI();
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createDecreaseButton(final int orientation) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			return super.createDecreaseButton(orientation);
		} else {
			return new HiFiScrollButton(orientation, scrollBarWidth);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createIncreaseButton(final int orientation) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			return super.createIncreaseButton(orientation);
		} else {
			return new HiFiScrollButton(orientation, scrollBarWidth);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected Color getFrameColor() {
		final Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 8);
		if (isDragging) {
			return ColorHelper.darker(frameColor, 8);
		} else if (isRollover) {
			return ColorHelper.brighter(frameColor, 16);
		} else {
			return frameColor;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void installDefaults() {
		super.installDefaults();
		final Color[] colors = AbstractLookAndFeel.getTheme().getThumbColors();
		rolloverColors = new Color[colors.length];
		dragColors = new Color[colors.length];
		for (int i = 0; i < colors.length; i++) {
			rolloverColors[i] = ColorHelper.brighter(colors[i], 8);
			dragColors[i] = ColorHelper.darker(colors[i], 8);
		}
	}

} // end of class HiFiScrollBarUI
