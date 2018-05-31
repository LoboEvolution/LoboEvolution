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

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;

public class JAutoTextField extends JTextFieldImpl {

	private static final long serialVersionUID = 1L;

	private List<String> dataList;

	private boolean isCaseSensitive;

	private boolean isStrict;

	private JAutoComboBox autoComboBox;
	
	public JAutoTextField() {
		dataList = new ArrayList<String>();
	}

	public JAutoTextField(List<String> list) {
		isCaseSensitive = false;
		isStrict = true;
		autoComboBox = null;
		if (list == null) {
			throw new IllegalArgumentException("values can not be null");
		} else {
			dataList = list;
			init();
			return;
		}
	}

	JAutoTextField(List<String> list, JAutoComboBox b) {
		isCaseSensitive = false;
		isStrict = true;
		autoComboBox = null;
		if (list == null) {
			throw new IllegalArgumentException("values can not be null");
		} else {
			dataList = list;
			autoComboBox = b;
			init();
			return;
		}
	}

	private void init() {
		setDocument(new AutoDocument(this));
	}

	public String getMatch(String s) {
		String match = null;
		for (String s1 : dataList) {
			if (s1 != null) {
				if (!isCaseSensitive && s1.toLowerCase().startsWith(s.toLowerCase()))
					match = s1;
				else if (isCaseSensitive && s1.startsWith(s))
					match = s1;
			}
		}
		
		if(Strings.isEmpty(match)) {
			match = s;
		}

		return match;
	}

	public void replaceSelection(String s) {
		AutoDocument _lb = (AutoDocument) getDocument();
		if (_lb != null)
			try {
				int i = Math.min(getCaret().getDot(), getCaret().getMark());
				int j = Math.max(getCaret().getDot(), getCaret().getMark());
				_lb.replace(i, j - i, s, null);
			} catch (Exception exception) {
			}
	}

	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	public void setCaseSensitive(boolean flag) {
		isCaseSensitive = flag;
	}

	public boolean isStrict() {
		return isStrict;
	}

	public void setStrict(boolean flag) {
		isStrict = flag;
	}

	public List<String> getDataList() {
		return dataList;
	}

	public void setDataList(List<String> list) {
		if (list == null) {
			throw new IllegalArgumentException("values can not be null");
		} else {
			dataList = list;
			return;
		}
	}

	/**
	 * @return the autoComboBox
	 */
	public JAutoComboBox getAutoComboBox() {
		return autoComboBox;
	}

	/**
	 * @param autoComboBox the autoComboBox to set
	 */
	public void setAutoComboBox(JAutoComboBox autoComboBox) {
		this.autoComboBox = autoComboBox;
	}
}
