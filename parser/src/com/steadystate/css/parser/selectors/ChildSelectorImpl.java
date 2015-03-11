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

import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;


/**
 * The Class ChildSelectorImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class ChildSelectorImpl extends LocatableImpl implements DescendantSelector, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5843289529637921083L;

    /** The ancestor selector_. */
    private Selector ancestorSelector_;
    
    /** The simple selector_. */
    private SimpleSelector simpleSelector_;

    /**
     * Sets the ancestor selector.
     *
     * @param ancestorSelector the new ancestor selector
     */
    public void setAncestorSelector(final Selector ancestorSelector) {
        ancestorSelector_ = ancestorSelector;
        if (ancestorSelector instanceof Locatable) {
            setLocator(((Locatable) ancestorSelector).getLocator());
        }
        else if (ancestorSelector == null) {
            setLocator(null);
        }
    }

    /**
     * Sets the simple selector.
     *
     * @param simpleSelector the new simple selector
     */
    public void setSimpleSelector(final SimpleSelector simpleSelector) {
        simpleSelector_ = simpleSelector;
    }

    /**
     * Instantiates a new child selector impl.
     *
     * @param parent the parent
     * @param simpleSelector the simple selector
     */
    public ChildSelectorImpl(final Selector parent, final SimpleSelector simpleSelector) {
        setAncestorSelector(parent);
        setSimpleSelector(simpleSelector);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Selector#getSelectorType()
     */
    public short getSelectorType() {
        return Selector.SAC_CHILD_SELECTOR;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.DescendantSelector#getAncestorSelector()
     */
    public Selector getAncestorSelector() {
        return ancestorSelector_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.DescendantSelector#getSimpleSelector()
     */
    public SimpleSelector getSimpleSelector() {
        return simpleSelector_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ancestorSelector_.toString() + " > " + simpleSelector_.toString();
    }
}
