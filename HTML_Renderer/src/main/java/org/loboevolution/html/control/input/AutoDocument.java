/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.control.input;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class AutoDocument extends PlainDocument {

	private JAutoTextField autoText;

	public AutoDocument(JAutoTextField autoText) {
		this.autoText = autoText;
	}

	private static final long serialVersionUID = 1L;

	public void replace(int i, int j, String s, AttributeSet attributeset) throws BadLocationException {
		super.remove(i, j);
		insertString(i, s, attributeset);
	}

	public void insertString(int i, String s, AttributeSet attributeset) throws BadLocationException {
		if (s == null || "".equals(s))
			return;
		String s1 = getText(0, i);
		String s2 = autoText.getMatch(s1 + s);
		int j = (i + s.length()) - 1;
		if (autoText.isStrict() && s2 == null) {
			s2 = autoText.getMatch(s1);
			j--;
		} else if (!autoText.isStrict() && s2 == null) {
			super.insertString(i, s, attributeset);
			return;
		}
		if (autoText.getAutoComboBox() != null && s2 != null)
			autoText.getAutoComboBox().setSelectedValue(s2);
		super.remove(0, getLength());
		super.insertString(0, s2, attributeset);
		autoText.setSelectionStart(j + 1);
		autoText.setSelectionEnd(getLength());
	}

	public void remove(int i, int j) throws BadLocationException {
		int k = autoText.getSelectionStart();
		if (k > 0)
			k--;
		String s = autoText.getMatch(getText(0, k));
		if (!autoText.isStrict() && s == null) {
			super.remove(i, j);
		} else {
			super.remove(0, getLength());
			super.insertString(0, s, null);
		}
		if (autoText.getAutoComboBox() != null && s != null)
			autoText.getAutoComboBox().setSelectedValue(s);
		try {
			autoText.setSelectionStart(k);
			autoText.setSelectionEnd(getLength());
		} catch (Exception exception) {
		}
	}

}
