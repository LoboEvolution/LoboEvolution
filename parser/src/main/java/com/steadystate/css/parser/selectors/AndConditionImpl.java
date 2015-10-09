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

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class AndConditionImpl extends LocatableImpl implements CombinatorCondition, CSSFormatable, Serializable {

    private static final long serialVersionUID = -3180583860092672742L;

    private Condition firstCondition_;
    private Condition secondCondition_;

    public void setFirstCondition(final Condition c1) {
        firstCondition_ = c1;
        if (c1 instanceof Locatable) {
            setLocator(((Locatable) c1).getLocator());
        }
        else if (c1 == null) {
            setLocator(null);
        }
    }

    public void setSecondCondition(final Condition c2) {
        secondCondition_ = c2;
    }

    public AndConditionImpl(final Condition c1, final Condition c2) {
        setFirstCondition(c1);
        setSecondCondition(c2);
    }

    public short getConditionType() {
        return Condition.SAC_AND_CONDITION;
    }

    public Condition getFirstCondition() {
        return firstCondition_;
    }

    public Condition getSecondCondition() {
        return secondCondition_;
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final StringBuilder sb = new StringBuilder();

        Condition cond = getFirstCondition();
        if (null != cond) {
            sb.append(((CSSFormatable) cond).getCssText(format));
        }

        cond = getSecondCondition();
        if (null != cond) {
            sb.append(((CSSFormatable) cond).getCssText(format));
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
