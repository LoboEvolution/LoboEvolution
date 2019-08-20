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

import com.gargoylesoftware.css.parser.Locatable;

/**
 * @author Ronald Brill
 */
public interface Condition extends Locatable {

    /**
     * ConditionType enum.
     */
    enum ConditionType {
        ATTRIBUTE_CONDITION,
        ID_CONDITION,
        LANG_CONDITION,
        ONE_OF_ATTRIBUTE_CONDITION,
        BEGIN_HYPHEN_ATTRIBUTE_CONDITION,
        CLASS_CONDITION,
        PREFIX_ATTRIBUTE_CONDITION,
        PSEUDO_CLASS_CONDITION,
        SUBSTRING_ATTRIBUTE_CONDITION,
        SUFFIX_ATTRIBUTE_CONDITION
    }

    /**
     * @return the associated condition type
     */
    ConditionType getConditionType();

    /**
     * @return the value
     */
    String getValue();

    /**
     * @return the local name
     */
    String getLocalName();
}
