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

import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SimpleSelector;

import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;


/**
 * The Class ConditionalSelectorImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class ConditionalSelectorImpl extends LocatableImpl implements ConditionalSelector, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7217145899707580586L;

    /** The simple selector_. */
    private SimpleSelector simpleSelector_;
    
    /** The condition_. */
    private Condition condition_;

    /**
     * Sets the simple selector.
     *
     * @param simpleSelector the new simple selector
     */
    public void setSimpleSelector(final SimpleSelector simpleSelector) {
        simpleSelector_ = simpleSelector;
        if (simpleSelector instanceof Locatable) {
            setLocator(((Locatable) simpleSelector).getLocator());
        }
        else if (simpleSelector == null) {
            setLocator(null);
        }
    }

    /**
     * Sets the condition.
     *
     * @param condition the new condition
     */
    public void setCondition(final Condition condition) {
        condition_ = condition;
        if (getLocator() == null) {
            if (condition instanceof Locatable) {
                setLocator(((Locatable) condition).getLocator());
            }
            else if (condition == null) {
                setLocator(null);
            }
        }
    }

    /**
     * Instantiates a new conditional selector impl.
     *
     * @param simpleSelector the simple selector
     * @param condition the condition
     */
    public ConditionalSelectorImpl(final SimpleSelector simpleSelector, final Condition condition) {
        setSimpleSelector(simpleSelector);
        setCondition(condition);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Selector#getSelectorType()
     */
    public short getSelectorType() {
        return Selector.SAC_CONDITIONAL_SELECTOR;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionalSelector#getSimpleSelector()
     */
    public SimpleSelector getSimpleSelector() {
        return simpleSelector_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionalSelector#getCondition()
     */
    public Condition getCondition() {
        return condition_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return simpleSelector_.toString() + condition_.toString();
    }
}
