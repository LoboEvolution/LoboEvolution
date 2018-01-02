/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.w3c.html.DOMSettableTokenList;

/**
 * The Class DOMSettableTokenListImpl.
 */
public class DOMSettableTokenListImpl extends DOMTokenListImpl implements DOMSettableTokenList {

	/**
	 * Instantiates a new DOM settable token list impl.
	 *
	 * @param element
	 *            the element
	 * @param itemValue
	 *            the item value
	 */
	public DOMSettableTokenListImpl(HTMLElementImpl element, String itemValue) {
		super(element, itemValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.DOMSettableTokenList#getValue()
	 */
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.DOMSettableTokenList#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub

	}
}
