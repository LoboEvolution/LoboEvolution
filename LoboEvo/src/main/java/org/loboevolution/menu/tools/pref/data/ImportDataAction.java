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

package org.loboevolution.menu.tools.pref.data;

import java.awt.event.ActionEvent;
import java.io.Serial;

import javax.swing.AbstractAction;

import com.jtattoo.plaf.lobo.LoboCheckBox;

/**
 * <p>ImportDataAction class.</p>
 *
 *
 *
 */
public class ImportDataAction extends AbstractAction {

	@Serial
    private static final long serialVersionUID = 1L;

	private final String action;

	/** The chrome panel. */
	private final LoboCheckBox chrome;

	/** The mozilla panel. */
	private final LoboCheckBox mozilla;

	/**
	 * <p>Constructor for ImportDataAction.</p>
	 *
	 * @param chrome a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param mozilla a {@link com.jtattoo.plaf.lobo.LoboCheckBox} object.
	 * @param action a {@link java.lang.String} object.
	 */
	public ImportDataAction(final LoboCheckBox chrome, final LoboCheckBox mozilla, final String action) {
		this.chrome = chrome;
		this.mozilla = mozilla;
		this.action = action;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		switch (this.action) {
		case "HISTORY":
			importHistory();
			break;
		case "BOOKMARKS":
			importBookmarks();
			break;
		case "COOKIES":
			importCookie();
			break;

		default:
			break;
		}
	}

	private void importBookmarks() {
		if (this.mozilla.isSelected()) {
			MozilaFirefoxData.importBookmark();
		}

		if (this.chrome.isSelected()) {
			GoogleChromeData.importBookmark();
		}
	}

	private void importCookie() {
		if (this.mozilla.isSelected()) {
			MozilaFirefoxData.importCookie();
		}

		if (this.chrome.isSelected()) {
			GoogleChromeData.importCookie();
		}
	}

	private void importHistory() {
		if (this.mozilla.isSelected()) {
			MozilaFirefoxData.importHistory();
		}

		if (this.chrome.isSelected()) {
			GoogleChromeData.importHistory();
		}
	}
}
