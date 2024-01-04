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

package org.loboevolution.menu.tools.pref;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.init.GuiInit;

/**
 * The Class OkAction.
 */
@Slf4j
public class OkCancelAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private final transient PreferenceWindow prefer;

	/**
	 * <p>Constructor for OkCancelAction.</p>
	 *
	 * @param prefer a {@link org.loboevolution.menu.tools.pref.PreferenceWindow} object.
	 */
	public OkCancelAction(final PreferenceWindow prefer) {
		this.prefer = prefer;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		try {
			this.prefer.dispose();
			this.prefer.getFrame().dispose();
			GuiInit.initLookAndFeel();
			GuiInit.createAndShowGui();
		} catch (final Exception ex) {
			log.error(ex.getMessage(), ex);
		}

	}
}
