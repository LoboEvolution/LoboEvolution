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

package com.jtattoo.plaf.base;

import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * <p>BaseSplitPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseSplitPaneUI extends BasicSplitPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseSplitPaneUI();
	}

	protected PropertyChangeListener myPropertyChangeListener = null;

	/** {@inheritDoc} */
	@Override
	public BasicSplitPaneDivider createDefaultDivider() {
		return new BaseSplitPaneDivider(this);
	}

	/** {@inheritDoc} */
	@Override
	protected void installListeners() {
		super.installListeners();
		myPropertyChangeListener = evt -> {
			if ("flatMode".equals(evt.getPropertyName()) && evt.getNewValue() instanceof Boolean) {
				((BaseSplitPaneDivider) getDivider()).setFlatMode((Boolean) evt.getNewValue());
			}
		};
		getSplitPane().addPropertyChangeListener(myPropertyChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallListeners() {
		super.uninstallListeners();
		getSplitPane().removePropertyChangeListener(myPropertyChangeListener);
	}

} // end of class BaseSplitPaneUI
