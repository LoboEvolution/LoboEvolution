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
 * The Class PrefixAttributeConditionImpl.
 *
 * @author Ahmed Ashour
 */
public class PrefixAttributeConditionImpl extends LocatableImpl implements AttributeCondition, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6899059242714875011L;

    /** The local name_. */
    private String localName_;
    
    /** The value_. */
    private String value_;
    
    /** The specified_. */
    private boolean specified_;

    /**
     * Sets the local name.
     *
     * @param localName the new local name
     */
    public void setLocalName(final String localName) {
        localName_ = localName;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(final String value) {
        value_ = value;
    }

    /**
     * Sets the specified.
     *
     * @param specified the new specified
     */
    public void setSpecified(final boolean specified) {
        specified_ = specified;
    }

    /**
     * Instantiates a new prefix attribute condition impl.
     *
     * @param localName the local name
     * @param value the value
     * @param specified the specified
     */
    public PrefixAttributeConditionImpl(final String localName, final String value, final boolean specified) {
        setLocalName(localName);
        setValue(value);
        setSpecified(specified);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Condition#getConditionType()
     */
    public short getConditionType() {
        return Condition.SAC_ATTRIBUTE_CONDITION; //for now
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
        return localName_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.AttributeCondition#getSpecified()
     */
    public boolean getSpecified() {
        return specified_;
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
            return "[" + getLocalName() + "^=\"" + value + "\"]";
        }
        return "[" + getLocalName() + "]";
    }
}
