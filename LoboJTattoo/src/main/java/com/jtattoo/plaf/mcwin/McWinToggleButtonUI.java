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
package com.jtattoo.plaf.mcwin;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseToggleButtonUI;

/**
 * <p>McWinToggleButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinToggleButtonUI extends BaseToggleButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new McWinToggleButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g, final AbstractButton b) {
		if (!b.isContentAreaFilled() || b.getParent() instanceof JMenuBar) {
			return;
		}
		super.paintBackground(g, b);
		final int width = b.getWidth();
		final int height = b.getHeight();
		final Graphics2D g2D = (Graphics2D) g;
		final Composite composite = g2D.getComposite();
		g2D.setColor(Color.lightGray);
		g2D.drawRect(0, 0, width - 2, height - 1);
		final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2D.setComposite(alpha);
		g2D.setColor(Color.white);
		g2D.drawLine(width - 1, 0, width - 1, height - 1);
		g2D.setComposite(composite);
	}

} // end of class McWinToggleButtonUI
