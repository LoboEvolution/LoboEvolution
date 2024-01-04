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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseButtonUI;

/**
 * <p>SmartButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class SmartButtonUI extends BaseButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new SmartButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g, final AbstractButton b) {
		final int w = b.getWidth();
		final int h = b.getHeight();
		final Graphics2D g2D = (Graphics2D) g;
		final Shape savedClip = g.getClip();
		if (b.getBorder() != null && b.isBorderPainted() && b.getBorder() instanceof UIResource) {
			final Area clipArea = new Area(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, 6, 6));
			if (savedClip != null) {
				clipArea.intersect(new Area(savedClip));
			}
			g2D.setClip(clipArea);
		}
		super.paintBackground(g, b);
		if (b.isContentAreaFilled() && b.isRolloverEnabled() && b.getModel().isRollover() && b.isBorderPainted()
				&& b.getBorder() != null) {
			g.setColor(AbstractLookAndFeel.getTheme().getFocusColor());
			final Insets ins = b.getBorder().getBorderInsets(b);
			if (ins.top == 0) {
				g.drawLine(1, 0, w - 1, 0);
				g.drawLine(1, 1, w - 1, 1);
				g.drawLine(1, 2, w - 1, 2);
			} else {
				g.drawLine(1, 1, w - 1, 1);
				g.drawLine(1, 2, w - 1, 2);
				g.drawLine(1, 3, w - 1, 3);
			}
		}
		g2D.setClip(savedClip);
	}

} // end of class SmartButtonUI
