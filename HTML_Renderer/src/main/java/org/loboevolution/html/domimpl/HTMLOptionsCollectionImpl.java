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
package org.loboevolution.html.domimpl;

import org.loboevolution.html.dombl.DescendentHTMLCollection;
import org.loboevolution.html.domfilter.NodeFilter;
import org.loboevolution.html.domfilter.OptionFilter;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLOptionsCollection;
import org.w3c.dom.DOMException;

/**
 * The Class HTMLOptionsCollectionImpl.
 */
public class HTMLOptionsCollectionImpl extends DescendentHTMLCollection implements HTMLOptionsCollection {

	/** The Constant OPTION_FILTER. */
	public static final NodeFilter OPTION_FILTER = new OptionFilter();

	/**
	 * Instantiates a new HTML options collection impl.
	 *
	 * @param selectElement
	 *            the select element
	 */
	public HTMLOptionsCollectionImpl(HTMLElementImpl selectElement) {
		super(selectElement, OPTION_FILTER, selectElement.getTreeLock(), false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOptionsCollection#setLength(int)
	 */
	@Override
	public void setLength(int length) throws DOMException {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLOptionsCollection#add(org.loboevolution.w3c.
	 * html .HTMLElement)
	 */
	@Override
	public void add(HTMLElement element) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLOptionsCollection#add(org.loboevolution.w3c.
	 * html .HTMLElement, org.loboevolution.w3c.html.HTMLElement)
	 */
	@Override
	public void add(HTMLElement element, HTMLElement before) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLOptionsCollection#add(org.loboevolution.w3c.
	 * html .HTMLElement, int)
	 */
	@Override
	public void add(HTMLElement element, int before) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOptionsCollection#remove(int)
	 */
	@Override
	public void remove(int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelectedIndex(int selectedIndex) {
		// TODO Auto-generated method stub

	}
}
