/*
 * Copyright (C) 1999-2017 David Schweinsberg.
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
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class PseudoClassConditionImpl extends LocatableImpl implements AttributeCondition, CSSFormatable, Serializable {

    private static final long serialVersionUID = 1798016773089155610L;

    private String value_;
    private boolean doubleColon_;

    public void setValue(final String value) {
        value_ = value;
    }

    public PseudoClassConditionImpl(final String value) {
        setValue(value);
    }

    public short getConditionType() {
        return Condition.SAC_PSEUDO_CLASS_CONDITION;
    }

    public String getNamespaceURI() {
        return null;
    }

    public String getLocalName() {
        return null;
    }

    public boolean getSpecified() {
        return true;
    }

    public String getValue() {
        return value_;
    }

    public void prefixedWithDoubleColon() {
        doubleColon_ = true;
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final String value = getValue();
        if (value == null) {
            return value;
        }
        return (doubleColon_ ? "::" : ":") + value;
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
