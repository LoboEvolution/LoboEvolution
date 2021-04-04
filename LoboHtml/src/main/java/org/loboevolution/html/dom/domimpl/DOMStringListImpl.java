/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.loboevolution.html.node.DOMStringList;

/**
 * <p>DOMStringListImpl class.</p>
 *
 *
 *
 */
public class DOMStringListImpl implements DOMStringList {
	private final List<String> sourceList;

	/**
	 * <p>Constructor for DOMStringListImpl.</p>
	 *
	 * @param sourceList a {@link java.util.Set} object.
	 */
	public DOMStringListImpl(Set<String> sourceList) {
		final List<String> list = new ArrayList<>(sourceList);
		this.sourceList = list;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(String str) {
		return this.sourceList.contains(str);
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.sourceList.size();
	}

	/** {@inheritDoc} */
	@Override
	public String item(int index) {
        int size = this.sourceList.size();
        if (size > index && index > -1) {
            return this.sourceList.get(index);
        } else {
            return null;
        }
	}
}
