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

package com.jtattoo.plaf;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Action;
import javax.swing.Icon;

/**
 * <p>BaseTitleButton class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseTitleButton extends NoFocusButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private float alpha = 1.0f;

	/**
	 * <p>Constructor for BaseTitleButton.</p>
	 *
	 * @param action a {@link javax.swing.Action} object.
	 * @param accessibleName a {@link java.lang.String} object.
	 * @param icon a {@link javax.swing.Icon} object.
	 * @param alpha a float.
	 */
	public BaseTitleButton(final Action action, final String accessibleName, final Icon icon, final float alpha) {
		setContentAreaFilled(false);
		setBorderPainted(false);
		setAction(action);
		setText(null);
		setIcon(icon);
		putClientProperty("paintActive", Boolean.TRUE);
		getAccessibleContext().setAccessibleName(accessibleName);
		this.alpha = Math.max(0.2f, alpha);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		if (JTattooUtilities.isActive(this) || alpha >= 1.0) {
			super.paint(g);
		} else {
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
			final AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2D.setComposite(alphaComposite);
			super.paint(g);
			g2D.setComposite(savedComposite);
		}
	}

} // end of class BaseTitleButton
