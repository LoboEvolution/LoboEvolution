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
/*
 * Created on Oct 9, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.DOMStringList;

/**
 * The Class DOMStringListImpl.
 */
public class DOMStringListImpl implements DOMStringList {

	/** The source list. */
	private final List<Object> sourceList;

	/**
	 * Instantiates a new DOM string list impl.
	 *
	 * @param sourceList
	 *            the source list
	 */
	public DOMStringListImpl(Collection<Object> sourceList) {
		List<Object> list = new ArrayList<Object>();
		list.addAll(sourceList);
		this.sourceList = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMStringList#item(int)
	 */
	@Override
	public String item(int index) {
		try {
			return (String) this.sourceList.get(index);
		} catch (IndexOutOfBoundsException iob) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMStringList#getLength()
	 */
	@Override
	public int getLength() {
		return this.sourceList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMStringList#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String str) {
		return this.sourceList.contains(str);
	}
}
