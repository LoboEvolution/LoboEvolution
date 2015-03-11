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

import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.Condition;

import com.steadystate.css.parser.LocatableImpl;


/**
 * The Class IdConditionImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class IdConditionImpl extends LocatableImpl implements AttributeCondition, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5955662524656167683L;

    /** The value_. */
    private String value_;

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(final String value) {
        value_ = value;
    }

    /**
     * Instantiates a new id condition impl.
     *
     * @param value the value
     */
    public IdConditionImpl(final String value) {
        setValue(value);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Condition#getConditionType()
     */
    public short getConditionType() {
        return Condition.SAC_ID_CONDITION;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.AttributeCondition#getNamespaceURI()
     */
    public String getNamespaceURI() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.AttributeCondition#getLocalName()
     */
    public String getLocalName() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.AttributeCondition#getSpecified()
     */
    public boolean getSpecified() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.AttributeCondition#getValue()
     */
    public String getValue() {
        return value_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final String value = getValue();
        if (value != null) {
            return "#" + value;
        }
        return "#";
    }
}
