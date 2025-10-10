/*
 * Copyright (c) 2019-2024 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.htmlunit.cssparser.parser.condition;

import java.io.Serializable;

import org.htmlunit.cssparser.parser.AbstractLocatable;
import org.htmlunit.cssparser.parser.Locator;
import org.htmlunit.cssparser.parser.selector.SelectorList;

/**
 * :where condition.
 *
 * @author Ronald Brill
 */
public class WherePseudoClassCondition extends AbstractLocatable implements Condition, Serializable {

    private final SelectorList selectors_;

    /**
     * Ctor.
     * @param selectors the selector list
     * @param locator the locator
     */
    public WherePseudoClassCondition(final SelectorList selectors, final Locator locator) {
        selectors_ = selectors;
        setLocator(locator);
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.WHERE_PSEUDO_CLASS_CONDITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalName() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return selectors_.toString();
    }

    /**
     * @return the list of selectors
     */
    public SelectorList getSelectors() {
        return selectors_;
    }

    @Override
    public String toString() {
        return ":where(" + getValue() + ")";
    }
}
