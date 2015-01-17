/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.w3c.DOMTokenList;

public class DOMTokenListImpl implements DOMTokenList {

	private String itemValue;
	private HTMLElementImpl element;

	public DOMTokenListImpl(HTMLElementImpl element, String item) {
		this.element = element;
		this.itemValue = item;

	}

	@Override
	public int getLength() {

		if (itemValue != null) {
			return itemValue.split(" ").length;
		}

		return 0;
	}

	@Override
	public String item(int index) {
		if (itemValue != null) {
			String[] listString = itemValue.split(" ");
			for (int i = 0; i < listString.length; i++) {
				if (index == i) {
					return listString[i];

				}
			}
		}
		return null;
	}

	@Override
	public boolean contains(String token) {
		return itemValue.contains(token);
	}

	@Override
	public void add(String token) {

		if (element.getClassName() != null) {
			element.setClassName(element.getClassName() + " " + token);
		} else {
			element.setClassName(token);
		}
	}

	@Override
	public void remove(String token) {

		String[] listString = itemValue.split(" ");
		String result = "";
		for (int i = 0; i < listString.length; i++) {
			String test = listString[i];
			if (!test.equals(token))
				result += test;
		}

		element.setClassName(result);

	}

	@Override
	public boolean toggle(String token) {

		if (contains(token)) {
			remove(token);
			return false;
		} else {
			add(token);
			return true;
		}
	}

	@Override
	public boolean toggle(String token, boolean force) {

		if (force)
			add(token);
		else
			remove(token);

		return force;
	}

}
