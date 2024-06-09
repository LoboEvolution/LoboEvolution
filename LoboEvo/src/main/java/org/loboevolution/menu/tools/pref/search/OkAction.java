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

package org.loboevolution.menu.tools.pref.search;

import java.awt.event.ActionEvent;
import java.io.Serial;

import javax.swing.AbstractAction;

/**
 * The Class OkAction.
 *
 * @param <T> a T object.
 *
 *
 */
public class OkAction<T> extends AbstractAction {

	/** The Constant serialVersionUID. */
	@Serial
    private static final long serialVersionUID = 1L;

	private final transient ItemEditor<T> item;

	/**
	 * <p>Constructor for OkAction.</p>
	 *
	 * @param item a {@link org.loboevolution.menu.tools.pref.search.ItemEditor} object.
	 */
	public OkAction(final ItemEditor<T> item) {
		this.item = item;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		this.item.getEditor().validateItem();
		this.item.setResultingItem(this.item.getEditor().getItem());
		this.item.dispose();
	}
}
