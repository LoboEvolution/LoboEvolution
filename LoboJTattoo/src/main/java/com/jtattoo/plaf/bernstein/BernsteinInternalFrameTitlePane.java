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
package com.jtattoo.plaf.bernstein;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>BernsteinInternalFrameTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BernsteinInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for BernsteinInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public BernsteinInternalFrameTitlePane(final JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(final Graphics g) {
		BernsteinUtils.fillComponent(g, this);
		final Graphics2D g2D = (Graphics2D) g;
		final Composite composite = g2D.getComposite();
		final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2D.setComposite(alpha);
		JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDefaultColors(), 0, 0, getWidth(),
				getHeight());
		g2D.setComposite(composite);
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
		} else {
			g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveBorderColor());
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Graphics g) {
	}

	/** {@inheritDoc} */
	@Override
	public void paintPalette(final Graphics g) {
		BernsteinUtils.fillComponent(g, this);
		final Graphics2D g2D = (Graphics2D) g;
		final Composite composite = g2D.getComposite();
		final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2D.setComposite(alpha);
		JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDefaultColors(), 0, 0, getWidth(),
				getHeight());
		g2D.setComposite(composite);
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
		} else {
			g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveBorderColor());
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

} // end of class BernsteinInternalFrameTitlePane
