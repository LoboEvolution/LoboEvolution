/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2014 David Schweinsberg.  All rights reserved.
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

package com.steadystate.css.parser.selectors;

import java.io.Serializable;

import org.w3c.css.sac.DescendantSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SimpleSelector;

import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class DescendantSelectorImpl extends LocatableImpl implements DescendantSelector, Serializable {

    private static final long serialVersionUID = -3620467847449531232L;

    private Selector ancestorSelector_;
    private SimpleSelector simpleSelector_;

    public void setAncestorSelector(final Selector ancestorSelector) {
        ancestorSelector_ = ancestorSelector;
        if (ancestorSelector instanceof Locatable) {
            setLocator(((Locatable) ancestorSelector).getLocator());
        }
        else if (ancestorSelector == null) {
            setLocator(null);
        }
    }

    public void setSimpleSelector(final SimpleSelector simpleSelector) {
        simpleSelector_ = simpleSelector;
    }

    public DescendantSelectorImpl(final Selector parent, final SimpleSelector simpleSelector) {
        setAncestorSelector(parent);
        setSimpleSelector(simpleSelector);
    }

    public short getSelectorType() {
        return Selector.SAC_DESCENDANT_SELECTOR;
    }

    public Selector getAncestorSelector() {
        return ancestorSelector_;
    }

    public SimpleSelector getSimpleSelector() {
        return simpleSelector_;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder(getAncestorSelector().toString());
        if (Selector.SAC_PSEUDO_ELEMENT_SELECTOR == getSimpleSelector().getSelectorType()) {
            result.append(':');
        }
        else {
            result.append(' ');
        }
        result.append(getSimpleSelector().toString());
        return result.toString();
    }
}
