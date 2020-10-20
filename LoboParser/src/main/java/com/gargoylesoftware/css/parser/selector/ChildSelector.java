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
package com.gargoylesoftware.css.parser.selector;

/**
 * @author Ronald Brill
 */
public class ChildSelector extends AbstractSelector {

    private Selector ancestorSelector_;
    private SimpleSelector simpleSelector_;

    /**
     * Ctor.
     * @param ancestorSelector the ancestor selector
     * @param simpleSelector the simple selector
     */
    public ChildSelector(final Selector ancestorSelector, final SimpleSelector simpleSelector) {
        ancestorSelector_ = ancestorSelector;
        if (ancestorSelector != null) {
            setLocator(ancestorSelector.getLocator());
        }

        simpleSelector_ = simpleSelector;
    }

    @Override
    public SelectorType getSelectorType() {
        return SelectorType.CHILD_SELECTOR;
    }

    /**
     * @return the ancestor selector
     */
    public Selector getAncestorSelector() {
        return ancestorSelector_;
    }

    @Override
    public SimpleSelector getSimpleSelector() {
        return simpleSelector_;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (null != ancestorSelector_) {
            sb.append(ancestorSelector_.toString());
        }

        sb.append(" > ");

        if (null != simpleSelector_) {
            sb.append(simpleSelector_.toString());
        }

        return sb.toString();
    }
}
