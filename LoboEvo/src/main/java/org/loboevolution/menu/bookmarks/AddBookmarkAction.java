/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.menu.bookmarks;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;

/**
 * <p>AddBookmarkAction class.</p>
 *
 *
 *
 */
public class AddBookmarkAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for AddBookmarkAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public AddBookmarkAction(final BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final ToolBar toolbar = this.frame.getToolbar();
		final AddBookmarkWindow bookmark = new AddBookmarkWindow(toolbar.getAddressBar().getText());
		bookmark.setLocationByPlatform(true);
		bookmark.setVisible(true);
	}
}
