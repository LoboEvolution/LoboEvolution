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

package org.loboevolution.component;

import java.awt.event.ActionEvent;
import java.io.Serial;

import javax.swing.AbstractAction;

import org.loboevolution.welcome.WelcomePanel;

/**
 * <p>HomeAction class.</p>
 */
public class HomeAction extends AbstractAction {

	@Serial
    private static final long serialVersionUID = 1L;

	private final IBrowserPanel panel;

	/**
	 * <p>Constructor for HomeAction.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public HomeAction(final IBrowserPanel panel) {
		this.panel = panel;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		final int indexPanel = this.panel.getTabbedPane().getSelectedIndex();
		this.panel.getTabbedPane().remove(indexPanel);
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(this.panel);
		tabbedPane.insertTab("Welcome", null, new WelcomePanel(panel), "Welcome", indexPanel);
		tabbedPane.setSelectedIndex(indexPanel);
	}
}
