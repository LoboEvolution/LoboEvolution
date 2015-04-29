/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;

/**
 * Implementation of {@link SelectorList}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class SelectorListImpl extends LocatableImpl implements SelectorList, Serializable {

    private static final long serialVersionUID = 7313376916207026333L;

    private List<Selector> selectors_ = new ArrayList<Selector>(10);

    public List<Selector> getSelectors() {
        return selectors_;
    }

    public void setSelectors(final List<Selector> selectors) {
        selectors_ = selectors;
    }

    public int getLength() {
        return selectors_.size();
    }

    public Selector item(final int index) {
        return selectors_.get(index);
    }

    public void add(final Selector sel) {
        selectors_.add(sel);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final int len = getLength();
        for (int i = 0; i < len; i++) {
            sb.append(item(i).toString());
            if (i < len - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
