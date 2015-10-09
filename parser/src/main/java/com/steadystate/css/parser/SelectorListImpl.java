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

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.selectors.ElementSelectorImpl;

/**
 * Implementation of {@link SelectorList}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class SelectorListImpl extends LocatableImpl implements SelectorList, CSSFormatable, Serializable {

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

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final int len = getLength();

        // if the selector is only asterisk we
        // have to preserve it in any case
        if (len == 1) {
            final Selector sel = item(0);
            if (sel instanceof ElementSelectorImpl
                    && format != null && format.isSuppressUniversalSelector()) {
                format.setSuppressUniversalSelector(false);
                final String res = ((ElementSelectorImpl) sel).getCssText(format);
                format.setSuppressUniversalSelector(true);
                return res;
            }
            return ((CSSFormatable) sel).getCssText(format);
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            final CSSFormatable sel = (CSSFormatable) item(i);
            sb.append(sel.getCssText(format));
            if (i < len - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
