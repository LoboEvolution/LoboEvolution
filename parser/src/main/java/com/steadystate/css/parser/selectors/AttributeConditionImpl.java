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

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class AttributeConditionImpl extends LocatableImpl implements AttributeCondition, CSSFormatable, Serializable {

    private static final long serialVersionUID = 9035418830958954213L;

    private String localName_;
    private String value_;
    private boolean specified_;

    public void setLocaleName(final String localName) {
        localName_ = localName;
    }

    public void setValue(final String value) {
        value_ = value;
    }

    public void setSpecified(final boolean specified) {
        specified_ = specified;
    }

    public AttributeConditionImpl(final String localName, final String value, final boolean specified) {
        setLocaleName(localName);
        setValue(value);
        setSpecified(specified);
    }

    public short getConditionType() {
        return Condition.SAC_ATTRIBUTE_CONDITION;
    }

    public String getNamespaceURI() {
        return null;
    }

    public String getLocalName() {
        return localName_;
    }

    public boolean getSpecified() {
        return specified_;
    }

    public String getValue() {
        return value_;
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final String value = getValue();
        if (value != null) {
            return "[" + getLocalName() + "=\"" + value + "\"]";
        }
        return "[" + getLocalName() + "]";
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
