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

package com.steadystate.css.parser.selectors;

import java.io.Serializable;

import org.w3c.css.sac.DescendantSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SimpleSelector;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class ChildSelectorImpl extends LocatableImpl implements DescendantSelector, CSSFormatable, Serializable {

    private static final long serialVersionUID = -5843289529637921083L;

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

    public ChildSelectorImpl(final Selector parent, final SimpleSelector simpleSelector) {
        setAncestorSelector(parent);
        setSimpleSelector(simpleSelector);
    }

    public short getSelectorType() {
        return Selector.SAC_CHILD_SELECTOR;
    }

    public Selector getAncestorSelector() {
        return ancestorSelector_;
    }

    public SimpleSelector getSimpleSelector() {
        return simpleSelector_;
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final StringBuilder sb = new StringBuilder();

        if (null != ancestorSelector_) {
            sb.append(((CSSFormatable) ancestorSelector_).getCssText(format));
        }

        sb.append(" > ");

        if (null != simpleSelector_) {
            sb.append(((CSSFormatable) simpleSelector_).getCssText(format));
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
