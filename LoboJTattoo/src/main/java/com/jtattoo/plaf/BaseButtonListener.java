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

package com.jtattoo.plaf;

import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.plaf.basic.BasicButtonListener;

/**
 * <p>BaseButtonListener class.</p>
 */
public class BaseButtonListener extends BasicButtonListener {

	/**
	 * <p>Constructor for BaseButtonListener.</p>
	 *
	 * @param b a {@link javax.swing.AbstractButton} object.
	 */
	public BaseButtonListener(final AbstractButton b) {
		super(b);
	}

	/** {@inheritDoc} */
	@Override
	public void focusGained(final FocusEvent e) {
		final AbstractButton b = (AbstractButton) e.getSource();
		b.repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void focusLost(final FocusEvent e) {
		final AbstractButton b = (AbstractButton) e.getSource();
		b.repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void mouseEntered(final MouseEvent e) {
		super.mouseEntered(e);
		final AbstractButton button = (AbstractButton) e.getSource();
		button.getModel().setRollover(true);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(final MouseEvent e) {
		super.mouseExited(e);
		final AbstractButton button = (AbstractButton) e.getSource();
		button.getModel().setRollover(false);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(final MouseEvent e) {
		super.mouseReleased(e);
	}

} // end of class BaseButtonListener
