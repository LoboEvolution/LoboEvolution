/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
 * Created on Oct 15, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.node.DOMImplementationList;
import org.loboevolution.html.node.DOMImplementation;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>DOMImplementationImpl class.</p>
 */
public class DOMImplementationListImpl implements DOMImplementationList {

    private List<DOMImplementation> domImplementations = new ArrayList<>();

    public DOMImplementationListImpl(List<DOMImplementation> implementations) {
        this.domImplementations = implementations;
    }

    /** {@inheritDoc} */
    @Override
    public DOMImplementation item(int index) {
        final int length = getLength();
        if (index >= 0 && index < length) {
            return (DOMImplementation) domImplementations.get(index);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public int getLength() {
        return domImplementations.size();
    }

}
