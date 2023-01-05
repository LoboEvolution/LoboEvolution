/*
 * Copyright (c) 2019-2021 Ronald Brill.
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
package com.gargoylesoftware.css.parser.condition;

import java.io.Serializable;

import com.gargoylesoftware.css.parser.AbstractLocatable;

/**
 * @author Ronald Brill
 */
public class AttributeCondition extends AbstractLocatable implements Condition, Serializable {

    private final String localName_;
    private final String value_;
    private final Boolean caseInSensitive_;

    /**
     * Ctor.
     * @param localName the local value
     * @param value the value
     * @param caseInSensitive null if not set, true/false for i/s
     */
    public AttributeCondition(final String localName, final String value, final Boolean caseInSensitive) {
        localName_ = localName;
        value_ = value;
        caseInSensitive_ = caseInSensitive;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.ATTRIBUTE_CONDITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalName() {
        return localName_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value_;
    }

    /**
     * @return true if the caseInsensitive option was set
     */
    public boolean isCaseInSensitive() {
        return caseInSensitive_ != null && caseInSensitive_.booleanValue();
    }

    public String getOperator() {
        return "=";
    }

    @Override
    public String toString() {
        String insensitive = "";
        if (caseInSensitive_ != null) {
            if (caseInSensitive_.booleanValue()) {
                insensitive = " i";
            }
            else {
                insensitive = " s";
            }
        }

        final String value = getValue();
        if (value != null) {
            return "[" + getLocalName() + getOperator() + "\"" + value + "\"" + insensitive + "]";
        }
        return "[" + getLocalName() + "" + insensitive + "]";
    }
}
