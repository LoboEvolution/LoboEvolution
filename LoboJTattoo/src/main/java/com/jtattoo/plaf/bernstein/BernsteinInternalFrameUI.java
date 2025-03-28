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

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseInternalFrameUI;

/**
 * <p>BernsteinInternalFrameUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BernsteinInternalFrameUI extends BaseInternalFrameUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BernsteinInternalFrameUI((JInternalFrame) c);
	}

	/**
	 * <p>Constructor for BernsteinInternalFrameUI.</p>
	 *
	 * @param b a {@link javax.swing.JInternalFrame} object.
	 */
	public BernsteinInternalFrameUI(final JInternalFrame b) {
		super(b);
	}

	/** {@inheritDoc} */
	@Override
	protected JComponent createNorthPane(final JInternalFrame w) {
		titlePane = new BernsteinInternalFrameTitlePane(w);
		return titlePane;
	}

} // end of class BernsteinInternalFrameUI
