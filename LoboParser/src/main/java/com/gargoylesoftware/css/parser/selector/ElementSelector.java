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
package com.gargoylesoftware.css.parser.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.gargoylesoftware.css.parser.Locator;
import com.gargoylesoftware.css.parser.condition.Condition;

/**
 * <p>ElementSelector class.</p>
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public class ElementSelector extends AbstractSelector implements SimpleSelector {
	
	private static final long serialVersionUID = 1L;
	private final String localName_;
    private final String localNameLC_;
    private List<Condition> conditions_;

    /**
     * Ctor.
     *
     * @param localName the local name
     * @param locator the locator
     */
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

    /** {@inheritDoc} */
    @Override
    public SelectorType getSelectorType() {
        return SelectorType.ELEMENT_NODE_SELECTOR;
    }

    /** {@inheritDoc} */
    @Override
    public SimpleSelector getSimpleSelector() {
        return this;
    }

    /**
     * <p>getLocalName.</p>
     *
     * @return the local name
     */
    public String getLocalName() {
        return localName_;
    }

    /**
     * <p>getLocalNameLowerCase.</p>
     *
     * @return the local name in lowercase
     */
    public String getLocalNameLowerCase() {
        return localNameLC_;
    }

    /**
     * <p>getElementName.</p>
     *
     * @return the element name
     */
    public String getElementName() {
        final String localeName = getLocalName();
        if (localeName == null) {
            return "*";
        }
        return localeName;
    }

    /**
     * <p>getConditions.</p>
     *
     * @return the conditions
     */
    public List<Condition> getConditions() {
        return conditions_;
    }

    /**
     * Add a condition.
     *
     * @param condition the condition to be added
     */
    public void addCondition(final Condition condition) {
        if (conditions_ == null) {
            conditions_ = new ArrayList<>();
        }
        conditions_.add(condition);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        final StringBuffer result = new StringBuffer();
        result.append(getElementName());

        if (conditions_ != null) {
            for (Condition condition : conditions_) {
                result.append(condition);
            }
        }
        return result.toString();
    }
}
