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
package com.jtattoo.plaf.bernstein;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JRootPane;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRootPaneUI;
import com.jtattoo.plaf.BaseTitlePane;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>BernsteinTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BernsteinTitlePane extends BaseTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for BernsteinTitlePane.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 * @param ui a {@link com.jtattoo.plaf.BaseRootPaneUI} object.
	 */
	public BernsteinTitlePane(final JRootPane root, final BaseRootPaneUI ui) {
		super(root, ui);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(final Graphics g) {
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, null);
		} else {
			BernsteinUtils.fillComponent(g, this);
		}
		final Graphics2D g2D = (Graphics2D) g;
		final Composite composite = g2D.getComposite();
		AlphaComposite alpha = null;
		if (backgroundImage != null) {
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue);
		} else {
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		}
		g2D.setComposite(alpha);
		JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDefaultColors(), 0, 0, getWidth(),
				getHeight());
		g2D.setComposite(composite);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
		} else {
			g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveBorderColor());
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

} // end of class BernsteinTitlePane
