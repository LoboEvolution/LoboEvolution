/*
 * Copyright (c) 2018 Ronald Brill.
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
package com.gargoylesoftware.css.parser.selector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.gargoylesoftware.css.parser.Locator;
import com.gargoylesoftware.css.parser.condition.Condition;

/**
 * @author Ronald Brill
 */
public class ElementSelector extends AbstractSelector implements SimpleSelector, Serializable {

    private final String localName_;
    private final String localNameLC_;
    private List<Condition> conditions_;

    public ElementSelector(final String localName, final Locator locator) {
        localName_ = localName;
        if (localName != null) {
            localNameLC_ = localName.toLowerCase(Locale.ROOT);
        }
        else {
            localNameLC_ = null;
        }

        setLocator(locator);
    }

    @Override
    public SelectorType getSelectorType() {
        return SelectorType.ELEMENT_NODE_SELECTOR;
    }

    @Override
    public SimpleSelector getSimpleSelector() {
        return this;
    }

    public String getLocalName() {
        return localName_;
    }

    public String getLocalNameLowerCase() {
        return localNameLC_;
    }

    public String getElementName() {
        final String localeName = getLocalName();
        if (localeName == null) {
            return "*";
        }
        return localeName;
    }

    public List<Condition> getConditions() {
        return conditions_;
    }

    public void addCondition(final Condition condition) {
        if (conditions_ == null) {
            conditions_ = new ArrayList<Condition>();
        }
        conditions_.add(condition);
    }

    @Override
    public String toString() {
        String localeName = getElementName();

        // TODO use StringBuilder
        if (conditions_ != null) {
            for (Condition condition : conditions_) {
                localeName += condition.toString();
            }
        }
        return localeName;
    }
}