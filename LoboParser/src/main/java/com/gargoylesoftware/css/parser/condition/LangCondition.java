/*
 * Copyright (c) 2019-2020 Ronald Brill.
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
import com.gargoylesoftware.css.parser.Locator;

/**
 *
 * @author Ronald Brill
 */
public class LangCondition extends AbstractLocatable implements Condition, Serializable {

    private String lang_;

    /**
     * Ctor.
     * @param lang the language
     * @param locator the locato
     */
    public LangCondition(final String lang, final Locator locator) {
        lang_ = lang;
        setLocator(locator);
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.LANG_CONDITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalName() {
        return null;
    }

    /**
     * @return the language
     */
    @Override
    public String getValue() {
        return lang_;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(":lang(");

        final String lang = getValue();
        if (null != lang) {
            result.append(lang);
        }

        result.append(")");
        return result.toString();
    }
}
