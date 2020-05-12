/*
 * Copyright (c) 2019 Ronald Brill.
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
 * <p>BeginHyphenAttributeCondition class.</p>
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public class BeginHyphenAttributeCondition extends AbstractLocatable implements Condition, Serializable {

	private static final long serialVersionUID = 1L;
	private final String localName_;
    private final String value_;

    /**
     * Ctor.
     *
     * @param localName the local name
     * @param value the value
     */
    public BeginHyphenAttributeCondition(final String localName, final String value) {
        localName_ = localName;
        value_ = value;
    }

    /** {@inheritDoc} */
    @Override
    public ConditionType getConditionType() {
        return ConditionType.BEGIN_HYPHEN_ATTRIBUTE_CONDITION;
    }

    /** {@inheritDoc} */
    @Override
    public String getLocalName() {
        return localName_;
    }

    /** {@inheritDoc} */
    @Override
    public String getValue() {
        return value_;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        final String value = getValue();
        if (value != null) {
            return "[" + getLocalName() + "|=\"" + value + "\"]";
        }
        return "[" + getLocalName() + "]";
    }
}

