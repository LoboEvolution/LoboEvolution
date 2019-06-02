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

import com.gargoylesoftware.css.parser.Locator;

/**
 *
 * @author Ronald Brill
 */
public class PseudoElementSelector extends AbstractSelector implements SimpleSelector, Serializable {

    private final String localName_;
    private final boolean doubleColon_;

    public PseudoElementSelector(final String localName, final Locator locator, final boolean doubleColon) {
        localName_ = localName;
        setLocator(locator);
        doubleColon_ = doubleColon;
    }

    @Override
    public SelectorType getSelectorType() {
        return SelectorType.PSEUDO_ELEMENT_SELECTOR;
    }

    @Override
    public SimpleSelector getSimpleSelector() {
        return this;
    }

    public String getLocalName() {
        return localName_;
    }

    @Override
    public String toString() {
        if (localName_ == null) {
            return localName_;
        }
        return (doubleColon_ ? "::" : ":") + localName_;
    }
}
