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

import java.util.ArrayList;
import java.util.List;

import org.w3c.css.sac.SACMediaList;

/**
 * Implementation of {@link SACMediaList}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class SACMediaListImpl extends LocatableImpl implements SACMediaList {

    private List<String> media_ = new ArrayList<String>(10);

    public int getLength() {
        return media_.size();
    }

    public String item(final int index) {
        return media_.get(index);
    }

    public void add(final String s) {
        media_.add(s);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final int len = getLength();
        for (int i = 0; i < len; i++) {
            sb.append(item(i));
            if (i < len - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
