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

import com.gargoylesoftware.css.parser.Locator;

/**
 * <p>PseudoElementSelector class.</p>
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public class PseudoElementSelector extends AbstractSelector implements SimpleSelector {

	private static final long serialVersionUID = 1L;
	private final String localName_;
    private final boolean doubleColon_;

    /**
     * Ctor.
     *
     * @param localName the local name
     * @param locator the locator
     * @param doubleColon double column flag
     */
    public PseudoElementSelector(final String localName, final Locator locator, final boolean doubleColon) {
        localName_ = localName;
        setLocator(locator);
        doubleColon_ = doubleColon;
    }

    /** {@inheritDoc} */
    @Override
    public SelectorType getSelectorType() {
        return SelectorType.PSEUDO_ELEMENT_SELECTOR;
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

    /** {@inheritDoc} */
    @Override
    public String toString() {
        if (localName_ == null) {
            return localName_;
        }
        return (doubleColon_ ? "::" : ":") + localName_;
    }
}
