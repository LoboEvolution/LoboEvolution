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
import com.gargoylesoftware.css.parser.Locator;
import com.gargoylesoftware.css.parser.selector.SelectorList;

/**
 * Not contition.
 *
 * @author Ronald Brill
 */
public class NotPseudoClassCondition extends AbstractLocatable implements Condition, Serializable {

    private final SelectorList selectors_;
    private final boolean doubleColon_;

    /**
     * Ctor.
     * @param selectors the selector list
     * @param locator the locator
     * @param doubleColon true if was prefixed by double colon
     */
    public NotPseudoClassCondition(final SelectorList selectors, final Locator locator, final boolean doubleColon) {
        selectors_ = selectors;
        setLocator(locator);
        doubleColon_ = doubleColon;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.NOT_PSEUDO_CLASS_CONDITION;
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
        return (doubleColon_ ? "::" : ":") + "not(" + getValue() + ")";
    }
}
