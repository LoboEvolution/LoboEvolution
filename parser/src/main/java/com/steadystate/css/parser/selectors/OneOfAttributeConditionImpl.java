/*
 * Copyright (C) 1999-2016 David Schweinsberg.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public class OneOfAttributeConditionImpl extends LocatableImpl
                implements AttributeCondition, CSSFormatable, Serializable {

    private static final long serialVersionUID = -1371164446179830634L;

    private String localName_;
    private String value_;
    private boolean specified_;

    public void setLocalName(final String localName) {
        localName_ = localName;
    }

    public void setValue(final String value) {
        value_ = value;
    }

    public void setSpecified(final boolean specified) {
        specified_ = specified;
    }

    public OneOfAttributeConditionImpl(final String localName, final String value, final boolean specified) {
        setLocalName(localName);
        setValue(value);
        setSpecified(specified);
    }

    public short getConditionType() {
        return Condition.SAC_ONE_OF_ATTRIBUTE_CONDITION;
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
            return "[" + getLocalName() + "~=\"" + value + "\"]";
        }
        return "[" + getLocalName() + "]";
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
