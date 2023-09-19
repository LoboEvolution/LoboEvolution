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
package com.jtattoo.plaf.graphite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>GraphiteInternalFrameTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class GraphiteInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for GraphiteInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public GraphiteInternalFrameTitlePane(JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	protected boolean centerButtons() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	protected int getHorSpacing() {
		return AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn() ? 1 : 0;
	}

	/** {@inheritDoc} */
	@Override
	protected int getVerSpacing() {
		return AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn() ? 3 : 0;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		if (isActive()) {
			g.setColor(ColorHelper.darker(AbstractLookAndFeel.getWindowBorderColor(), 10));
		} else {
			g.setColor(ColorHelper.darker(AbstractLookAndFeel.getWindowInactiveBorderColor(), 10));
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(Graphics g, int x, int y, String title) {
		Graphics2D g2D = (Graphics2D) g;
		Color fc = isActive() ? AbstractLookAndFeel.getWindowTitleForegroundColor()
				: AbstractLookAndFeel.getWindowInactiveTitleForegroundColor();
		if (fc.equals(Color.white)) {
			Color bc = isActive() ? AbstractLookAndFeel.getWindowTitleColorDark()
					: AbstractLookAndFeel.getWindowInactiveTitleColorDark();
			g2D.setColor(bc);
			JTattooUtilities.drawString(frame, g, title, x - 1, y - 1);
			g2D.setColor(ColorHelper.darker(bc, 30));
			JTattooUtilities.drawString(frame, g, title, x + 1, y + 1);
		}
		g.setColor(fc);
		JTattooUtilities.drawString(frame, g, title, x, y);
	}

} // end of class GraphiteInternalFrameTitlePane
