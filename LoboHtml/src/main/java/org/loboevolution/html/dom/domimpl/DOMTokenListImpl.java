/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.node.DOMTokenList;

/**
 * <p>DOMTokenListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DOMTokenListImpl implements DOMTokenList {

	private final String itemValue;

	private final ElementImpl element;

	/**
	 * <p>Constructor for DOMTokenListImpl.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.ElementImpl} object.
	 * @param item a {@link java.lang.String} object.
	 */
	public DOMTokenListImpl(ElementImpl element, String item) {
		this.element = element;
		this.itemValue = item;

	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		if (itemValue != null) {
			return itemValue.split(" ").length;
		}

		return 0;
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public boolean contains(String token) {
		return itemValue.contains(token);
	}

	/** {@inheritDoc} */
	@Override
	public void add(String token) {
		if (element.getClassName() != null) {
			element.setClassName(element.getClassName() + " " + token);
		} else {
			element.setClassName(token);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void remove(String token) {

		String[] listString = itemValue.split(" ");
		StringBuilder result = new StringBuilder();
		for (String element2 : listString) {
			String test = element2;
			if (!test.equals(token)) {
				result.append(test);
			}
		}

		element.setClassName(result.toString());

	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public boolean toggle(String token, boolean force) {

		if (force) {
			add(token);
		} else {
			remove(token);
		}

		return force;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean replace(String oldToken, String newToken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supports(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
