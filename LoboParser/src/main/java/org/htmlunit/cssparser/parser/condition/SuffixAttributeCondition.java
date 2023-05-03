/*
 * Copyright (c) 2019-2023 Ronald Brill.
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
package org.htmlunit.cssparser.parser.condition;


/**
 * <p>SuffixAttributeCondition class.</p>
 *
 * @author Ronald Brill
 *
 */
public class SuffixAttributeCondition extends AttributeCondition {

    /**
     * Ctor.
     * @param localName the local value
     * @param value the value
     * @param caseInSensitive null if not set, true/false for i/s
     */
    public SuffixAttributeCondition(final String localName, final String value, final Boolean caseInSensitive) {
        super(localName, value, caseInSensitive);
    }

    /** {@inheritDoc} */
    @Override
    public ConditionType getConditionType() {
        return ConditionType.SUFFIX_ATTRIBUTE_CONDITION; //for now
    }

    /** {@inheritDoc} */
    @Override
    public String getOperator() {
        return "$=";
    }
}
