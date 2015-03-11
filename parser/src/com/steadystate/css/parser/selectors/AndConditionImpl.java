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

import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;

import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;


/**
 * The Class AndConditionImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class AndConditionImpl extends LocatableImpl implements CombinatorCondition, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3180583860092672742L;

    /** The first condition_. */
    private Condition firstCondition_;
    
    /** The second condition_. */
    private Condition secondCondition_;

    /**
     * Sets the first condition.
     *
     * @param c1 the new first condition
     */
    public void setFirstCondition(final Condition c1) {
        firstCondition_ = c1;
        if (c1 instanceof Locatable) {
            setLocator(((Locatable) c1).getLocator());
        }
        else if (c1 == null) {
            setLocator(null);
        }
    }

    /**
     * Sets the second condition.
     *
     * @param c2 the new second condition
     */
    public void setSecondCondition(final Condition c2) {
        secondCondition_ = c2;
    }

    /**
     * Instantiates a new and condition impl.
     *
     * @param c1 the c1
     * @param c2 the c2
     */
    public AndConditionImpl(final Condition c1, final Condition c2) {
        setFirstCondition(c1);
        setSecondCondition(c2);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Condition#getConditionType()
     */
    public short getConditionType() {
        return Condition.SAC_AND_CONDITION;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.CombinatorCondition#getFirstCondition()
     */
    public Condition getFirstCondition() {
        return firstCondition_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.CombinatorCondition#getSecondCondition()
     */
    public Condition getSecondCondition() {
        return secondCondition_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getFirstCondition().toString() + getSecondCondition().toString();
    }
}
